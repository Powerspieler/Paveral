package me.powerspieler.paveral.forge.events;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ForgeItemEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Item item;
    private final Location forge;

    public ForgeItemEvent(Item item, Location forge) {
        this.item = item;
        this.forge = forge;
    }

    public Item getItem() {
        return item;
    }

    public Location getForge() {
        return forge;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
