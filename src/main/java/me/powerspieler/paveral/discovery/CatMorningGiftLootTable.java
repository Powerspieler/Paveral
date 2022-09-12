package me.powerspieler.paveral.discovery;

import me.powerspieler.paveral.discovery.diaries.BedrockBreaker;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;

public class CatMorningGiftLootTable implements Listener {
    @EventHandler
    public void onCatMorningGift(EntityDropItemEvent event){
        if(event.getEntityType() == EntityType.CAT){
            if(Math.random() < 0.1){
                event.setCancelled(true);
                Location location = event.getItemDrop().getLocation();
                Discovery book = new BedrockBreaker();
                location.getWorld().dropItem(location, book.build());
            }
        }
    }
}
