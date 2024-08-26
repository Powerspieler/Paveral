package me.powerspieler.paveral.items.musicpack;

import me.powerspieler.paveral.crafting.PaveralRecipe;
import me.powerspieler.paveral.items.PaveralItem;
import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ResonatingPickaxe extends PaveralItem implements Listener {
    private static Component itemName(){
        return Component.text("Resonating Pickaxe", NamedTextColor.DARK_PURPLE);
    }

    private static List<Component> lore(){
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Resonating Pickaxe"));
        lore.add(Component.text("Lore"));
        return lore;
    }

    public ResonatingPickaxe() {
        super(Material.NETHERITE_PICKAXE, 0, Constant.ITEMTYPE, "resonating_pickaxe", itemName(), lore());
    }

    @Override
    public PaveralRecipe recipe() {
        return null;
    }

    @Override
    public ItemStack build() {
        return super.build();
    }


    @EventHandler
    private void onBlockBreak(BlockBreakEvent event){
        event.getBlock().getRelative(BlockFace.DOWN).breakNaturally(event.getPlayer().getInventory().getItemInMainHand(), true, true);
    }



}
