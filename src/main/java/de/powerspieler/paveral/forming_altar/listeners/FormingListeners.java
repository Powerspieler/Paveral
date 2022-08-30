package de.powerspieler.paveral.forming_altar.listeners;

import de.powerspieler.paveral.forming_altar.events.FormingItemOnAltar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public class FormingListeners implements Listener {

    @EventHandler
    public void onIngredientDrop(FormingItemOnAltar event){
        Bukkit.broadcast(Component.text("" + event.getItem().getItemStack() + " dropped on Valid Altar!"));

    }
}
