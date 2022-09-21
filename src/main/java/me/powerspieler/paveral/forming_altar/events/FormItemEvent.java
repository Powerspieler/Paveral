package me.powerspieler.paveral.forming_altar.events;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FormItemEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Item item;
    private final Location altar;

    public FormItemEvent(Item item, Location altar) {
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
