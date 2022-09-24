package me.powerspieler.paveral.discovery;

import me.powerspieler.paveral.advancements.AwardAdvancements;
import me.powerspieler.paveral.discovery.diaries.BedrockBreaker;
import org.bukkit.Location;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;

public class CatMorningGiftLootTable implements Listener {
    @EventHandler
    public void onCatMorningGift(EntityDropItemEvent event){
        if(event.getEntity() instanceof Cat cat){
            if(cat.getOwner() instanceof Player player){
                if(AwardAdvancements.isAdvancementUndone(player, "sleep_with_cat")){
                    if(Math.random() < 0.2){
                        event.setCancelled(true);
                        Location location = event.getItemDrop().getLocation();
                        Discovery book = new BedrockBreaker();
                        location.getWorld().dropItem(location, book.build());
                    }
                }
            }
        }
    }
}
