package me.powerspieler.paveral.items.musicpack;

import com.destroystokyo.paper.MaterialSetTag;
import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.crafting.PaveralIngredient;
import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.items.PaveralItem;
import me.powerspieler.paveral.items.helper.Enchantable;
import me.powerspieler.paveral.items.helper.ItemHoldingController;
import me.powerspieler.paveral.items.helper.TotemDisabler;
import me.powerspieler.paveral.util.Constant;
import me.powerspieler.paveral.util.MarkerDataStorage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScytheOfHarmony extends PaveralItem implements Listener, Enchantable {
    private static Component itemName(){
        return Component.text("Scythe of Harmony", NamedTextColor.DARK_PURPLE);
    }

    private static List<Component> lore(){
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Replants the crop on harvest if the seed is in your inventory", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("In addition, Farmland created with this hoe cannot be trampled", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(""));
        lore.add(Component.text("Enchantable with ", NamedTextColor.BLUE)
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Unbreaking",NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.text(", ", NamedTextColor.BLUE)
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.text("Mending", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" and ", NamedTextColor.BLUE)
                        .decoration(TextDecoration.ITALIC, false))
                .append(Component.text("Fortune", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)));
        lore.addAll(TotemDisabler.loreAddition());
        return lore;
    }

    public ScytheOfHarmony() {
        super(Material.NETHERITE_HOE, "scythe_of_harmony", Constant.ITEMTYPE, "scythe_of_harmony", itemName(), lore());
    }

    @Override
    public PaveralRecipe recipe() {
        Set<StandardIngredient> ingredients = new HashSet<>();
        ingredients.add(new StandardIngredient(Material.NETHERITE_HOE, 1));
        ingredients.add(new PaveralIngredient(Material.JIGSAW, 1, Constant.ITEMTYPE, "music_core"));
        return new PaveralRecipe(ingredients, this.build());
    }

    // --- Item Logic ---

    private static final NamespacedKey protectedFarmland = new NamespacedKey(Paveral.getPlugin(), "protected_farmland");

    @EventHandler
    private void onItemUse(PlayerInteractEvent event){
        if(ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), "scythe_of_harmony") && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null){
            Block block = event.getClickedBlock();
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(block.getType() == Material.FARMLAND){
                        Farmland farmland = (Farmland) block.getBlockData();
                        farmland.setMoisture(7);
                        block.setBlockData(farmland);

                        MarkerDataStorage.createMarker(block);
                        MarkerDataStorage.getMarkerDataContainer(block).set(protectedFarmland, PersistentDataType.BOOLEAN, true);
                    }
                }
            }.runTaskLater(Paveral.getPlugin(), 1L);
        }
    }

    @EventHandler
    private void onFarmlandJump(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        if(block != null && block.getType() == Material.FARMLAND && event.getAction() == Action.PHYSICAL){
            if(MarkerDataStorage.hasMarker(block) && MarkerDataStorage.getMarkerDataContainer(block).has(protectedFarmland)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onCropHarvest(BlockBreakEvent event){
        Player player = event.getPlayer();
        if(ItemHoldingController.checkIsHoldingPaveralItem(player, "scythe_of_harmony") && (MaterialSetTag.MAINTAINS_FARMLAND.isTagged(event.getBlock().getType()) || event.getBlock().getType() == Material.NETHER_WART)){
            Material cropType = event.getBlock().getType();
            Material seedType = convertBlockToSeed(cropType);
            PlayerInventory inventory = player.getInventory();
            if(inventory.contains(seedType)){
                ItemStack seedStack = inventory.getItem(inventory.first(seedType));
                assert seedStack != null;
                seedStack.setAmount(seedStack.getAmount() - 1);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        event.getBlock().setBlockData(Bukkit.createBlockData(cropType));
                    }
                }.runTaskLater(Paveral.getPlugin(), 1L);
            }
        }
    }

    private Material convertBlockToSeed(Material blockMaterial){
        Material seedMaterial = null;
        switch(blockMaterial){
            case PUMPKIN_STEM, ATTACHED_PUMPKIN_STEM -> seedMaterial = Material.PUMPKIN_SEEDS;
            case MELON_STEM, ATTACHED_MELON_STEM -> seedMaterial = Material.MELON_SEEDS;
            case BEETROOTS -> seedMaterial = Material.BEETROOT_SEEDS;
            case CARROTS -> seedMaterial = Material.CARROT;
            case POTATOES -> seedMaterial = Material.POTATO;
            case TORCHFLOWER_CROP, TORCHFLOWER -> seedMaterial = Material.TORCHFLOWER_SEEDS;
            case PITCHER_CROP -> seedMaterial = Material.PITCHER_POD;
            case WHEAT -> seedMaterial = Material.WHEAT_SEEDS;
            case NETHER_WART -> seedMaterial = Material.NETHER_WART;
        }
        return seedMaterial;
    }

    @Override
    @EventHandler
    public void onEnchantingAttempt(PrepareAnvilEvent event) {
        Set<Enchantment> enchants = new HashSet<>();
        enchants.add(Enchantment.EFFICIENCY);
        enchants.add(Enchantment.SILK_TOUCH);
        Enchantable.super.onEnchantingAttempt(event, keyString, enchants);
    }

    @Override
    @EventHandler
    public void onEnchantingTableAttempt(PrepareItemEnchantEvent event) {
        Enchantable.super.onEnchantingTableAttempt(event, keyString, Set.of(Enchantment.EFFICIENCY, Enchantment.SILK_TOUCH));
    }

    @Override
    @EventHandler
    public void onEnchantingTableComplete(EnchantItemEvent event) {
        Enchantable.super.onEnchantingTableComplete(event, keyString, Set.of(Enchantment.EFFICIENCY, Enchantment.SILK_TOUCH));
    }
}
