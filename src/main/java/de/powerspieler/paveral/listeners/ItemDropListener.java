package de.powerspieler.paveral.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropListener implements Listener {
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        if(event.getItemDrop().getItemStack().getType() == Material.STICK){
            Item item = event.getItemDrop();
            player.sendMessage("Stick gedroppt!");
            if(item.getLocation().getBlock().getRelative(0,-2,0).getType() == Material.LODESTONE){
                player.sendMessage("Stick auf Lodestone gedroppt!");
            }
        }
    }
}
