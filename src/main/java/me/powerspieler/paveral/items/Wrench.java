package me.powerspieler.paveral.items;

import me.powerspieler.paveral.util.Constant;
import net.kyori.adventure.text.Component;
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
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Wrench implements Listener,Items {
    @Override
    public ItemStack build() {
        ItemStack wrench = new ItemStack(Material.WARPED_FUNGUS_ON_A_STICK);
        ItemMeta wrenchmeta = wrench.getItemMeta();
        wrenchmeta.getPersistentDataContainer().set(Constant.ITEMTYPE, PersistentDataType.STRING, "wrench");
        wrenchmeta.setCustomModelData(3);
        wrenchmeta.setUnbreakable(true);
        wrenchmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        wrenchmeta.displayName(Component.text("Wrench")
                .decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Open iron trapdoors")
                .decoration(TextDecoration.ITALIC, false));
        wrenchmeta.lore(lore);

        wrench.setItemMeta(wrenchmeta);
        return wrench;
    }

    @Override
    public List<ItemStack> parts() {
        List<ItemStack> parts = new ArrayList<>();
        ItemStack ironingot = new ItemStack(Material.IRON_INGOT, 4);
        parts.add(ironingot);
        return parts;
    }

    @EventHandler
    public void onPlayerRightclick(PlayerInteractEvent event){
        if(event.getItem() != null && event.getItem().hasItemMeta() && event.getItem().getItemMeta().getPersistentDataContainer().has(Constant.ITEMTYPE)){
            if(Objects.equals(event.getItem().getItemMeta().getPersistentDataContainer().get(Constant.ITEMTYPE, PersistentDataType.STRING), "wrench")){
                if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
                    if(event.getClickedBlock() != null){
                        Block block = event.getClickedBlock();
                        if(block.getType() == Material.IRON_TRAPDOOR){
                            TrapDoor td = (TrapDoor) block.getBlockData();
                            td.setOpen(!td.isOpen());
                            block.setBlockData(td);
                        }
                    }
                }
            }
        }
    }
}
