package me.powerspieler.paveral.forge;

import me.powerspieler.paveral.forge.events.ForgeItemEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ForgeListener implements Listener {

    @EventHandler
    public void onIngredientDrop(ForgeItemEvent event){
        //TODO COMPLETE THIS FILE
        //TODO CREATE TUTORIAL BOOK
        //TODO FINISH ADVANCEMENTS
        Bukkit.broadcast(Component.text("EVENT FIRED!!"));
    }
}
