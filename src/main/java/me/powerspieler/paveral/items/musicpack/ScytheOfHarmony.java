package me.powerspieler.paveral.items.musicpack;

import com.destroystokyo.paper.MaterialSetTag;
import me.powerspieler.paveral.Paveral;
import me.powerspieler.paveral.items.ItemHoldingController;
import me.powerspieler.paveral.items.Items;
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
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScytheOfHarmony implements Listener, Items {
    @Override
    public ItemStack build() {
        ItemStack item = new ItemStack(Material.NETHERITE_HOE);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.itemName(Component.text("Scythe of Harmony", NamedTextColor.DARK_PURPLE));
        itemMeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "scythe_of_harmony");
        itemMeta.setCustomModelData(1);

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Replants the crop on harvest if the seed is in your inventory", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("In addition, Farmland created with this hoe cannot be trampled", NamedTextColor.DARK_AQUA)
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(""));
        lore.add(Component.text("Enchantable with ", NamedTextColor.BLUE)
                .decoration(TextDecoration.ITALIC, false)
                .append(Component.text("Unbreaking",NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)
                )
                .append(Component.text(", ", NamedTextColor.BLUE)
                        .decoration(TextDecoration.ITALIC, false)
                )
                .append(Component.text("Mending", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)
                )
                .append(Component.text(" and ", NamedTextColor.BLUE)
                        .decoration(TextDecoration.ITALIC, false)
                )
                .append(Component.text("Fortune", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)));
        itemMeta.lore(lore);

        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public List<ItemStack> parts() {
        return List.of();
    }

    private static final NamespacedKey protectedFarmland = new NamespacedKey(Paveral.getPlugin(), "protected_farmland");

    @EventHandler
    public void onItemUse(PlayerInteractEvent event){
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
    public void onFarmlandJump(PlayerInteractEvent event){
        Block block = event.getClickedBlock();
        if(block != null && block.getType() == Material.FARMLAND && event.getAction() == Action.PHYSICAL){
            if(MarkerDataStorage.hasMarker(block) && MarkerDataStorage.getMarkerDataContainer(block).has(protectedFarmland)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCropHarvest(BlockBreakEvent event){
        Player player = event.getPlayer();
        if(ItemHoldingController.checkIsHoldingPaveralItem(player, "scythe_of_harmony") && MaterialSetTag.MAINTAINS_FARMLAND.isTagged(event.getBlock().getType())){
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
        }
        return seedMaterial;
    }




    @EventHandler
    public void onEnchantingAttempt(PrepareAnvilEvent event) {
        if (event.getInventory().getFirstItem() != null && event.getResult() != null && event.getInventory().getFirstItem().getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE)) {
            if (Objects.equals(event.getInventory().getFirstItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "scythe_of_harmony")) {
                ItemStack result = event.getResult();
                ItemMeta resultmeta = result.getItemMeta();
                if (event.getResult().containsEnchantment(Enchantment.EFFICIENCY)) {
                    resultmeta.removeEnchant(Enchantment.EFFICIENCY);
                }
                if (event.getResult().containsEnchantment(Enchantment.SILK_TOUCH)) {
                    resultmeta.removeEnchant(Enchantment.SILK_TOUCH);
                }
                result.setItemMeta(resultmeta);
                event.setResult(result);

                if (event.getInventory().getSecondItem() != null && event.getInventory().getSecondItem().getType() == Material.ENCHANTED_BOOK) {
                    if(event.getInventory().getFirstItem().getEnchantments().equals(event.getResult().getEnchantments())){
                        event.setResult(new ItemStack(Material.AIR));
                    }
                }
            }
        }
    }
}
