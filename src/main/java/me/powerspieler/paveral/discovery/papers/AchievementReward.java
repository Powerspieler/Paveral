package me.powerspieler.paveral.discovery.papers;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class AchievementReward implements Listener {
    @EventHandler
    private void onAchievementGrant(PlayerAdvancementDoneEvent event){
        Player player = event.getPlayer();
        NamespacedKey key = event.getAdvancement().getKey();

        if(key.equals(new NamespacedKey("paveral","craft_tutorial_book_forge"))){
            player.getInventory().addItem();
        }



    }

    private void addItemToInventory(Player player, ItemStack item){
        HashMap<Integer, ItemStack> leftover = player.getInventory().addItem(item);
        if(!leftover.isEmpty()){
            for(ItemStack dropItem : leftover.values()){
                player.getWorld().dropItemNaturally(player.getLocation(), dropItem);
            }
        }
    }
}
