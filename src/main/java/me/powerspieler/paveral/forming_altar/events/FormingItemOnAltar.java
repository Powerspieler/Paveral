package me.powerspieler.paveral.forming_altar.events;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FormingItemOnAltar extends Event {
    private static final HandlerList handlers = new HandlerList();

    private Item item;
    private Location altar;

    public FormingItemOnAltar(Item item, Location altar) {
        this.item = item;
        this.altar = altar;
    }

    public Item getItem() {
        return item;
    }

    public Location getAltar() {
        return altar;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
