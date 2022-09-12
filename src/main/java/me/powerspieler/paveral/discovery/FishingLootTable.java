package me.powerspieler.paveral.discovery;

import me.powerspieler.paveral.items.Items;
import me.powerspieler.paveral.items.LightStaff;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.ArrayList;
import java.util.List;

public class FishingLootTable implements Listener {
    @EventHandler
    public void onFishing(PlayerFishEvent event){
        if(event.getState() == PlayerFishEvent.State.CAUGHT_FISH){
            if(event.getCaught() instanceof Item item){
                if(Math.random() <= 0.1){
                    Items lightstaff = new LightStaff();
                    ItemStack lsStack = lightstaff.build();
                    
                    Damageable damagemeta = (Damageable) lsStack.getItemMeta();
                    damagemeta.setDamage((int) (100 - (Math.random() / 8) * 100));
                    lsStack.setItemMeta(damagemeta);
                    item.setItemStack(lsStack);
                }
                if(Math.random() <= 0.05){
                    ItemStack wetPaper = new ItemStack(Material.PAPER);
                    ItemMeta wetPaperMeta = wetPaper.getItemMeta();
                    List<Component> lore = new ArrayList<>();
                    lore.add(Component.text("Have you ever wondered if you could")
                            .decoration(TextDecoration.ITALIC, false));
                    lore.add(Component.text("enhance the documentation book on the altar?")
                            .decoration(TextDecoration.ITALIC, false));
                    wetPaperMeta.lore(lore);
                    wetPaper.setItemMeta(wetPaperMeta);

                    item.setItemStack(wetPaper);
                }
            }
        }
    }
}
