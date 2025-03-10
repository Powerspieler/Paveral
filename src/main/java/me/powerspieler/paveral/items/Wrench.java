package me.powerspieler.paveral.items;

import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.crafting.StandardIngredient;
import me.powerspieler.paveral.items.helper.Dismantable;
import me.powerspieler.paveral.items.helper.ItemHoldingController;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Wrench extends PaveralItem implements Listener, Dismantable {
    private static Component itemName(){
        return Component.text("Wrench");
    }

    private static List<Component> lore(){
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Open iron trapdoors")
                .decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Experimental", NamedTextColor.RED).decoration(TextDecoration.ITALIC, false));
        return lore;
    }

    public Wrench() {
        super(Material.WARPED_FUNGUS_ON_A_STICK, "wrench", Constant.ITEMTYPE, "wrench", itemName(), lore());
    }

    @Override
    protected ItemStack build() {
        ItemStack item = super.build();
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(itemMeta);
        return item;
    }

    @Override
    public PaveralRecipe recipe() {
        Set<StandardIngredient> ingredients = new HashSet<>();
        ingredients.add(new StandardIngredient(Material.IRON_INGOT, 4));
        return new PaveralRecipe(ingredients, this.build());
    }

    @Override
    public List<ItemStack> parts() {
        List<ItemStack> parts = new ArrayList<>();
        parts.add(new ItemStack(Material.IRON_INGOT, 4));
        return parts;
    }

    // --- Item Logic ---

    @EventHandler
    public void onPlayerRightclick(PlayerInteractEvent event){
        if(event.hasItem() && ItemHoldingController.checkIsHoldingPaveralItem(event.getPlayer(), keyString)){
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getClickedBlock() != null) {
                    Block block = event.getClickedBlock();
                    if (block.getType() == Material.IRON_TRAPDOOR) {
                        TrapDoor td = (TrapDoor) block.getBlockData();
                        td.setOpen(!td.isOpen());
                        block.setBlockData(td);
                    }
                }
            }
        }
    }
}
