package me.powerspieler.paveral.disassemble.events;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DisassembleItemEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Item item;
    private final Location table;

    public DisassembleItemEvent(Item item, Location table) {
        this.item = item;
        this.table = table;
    }

    public Item getItem() {
        return item;
    }

    public Location getTable() {
        return table;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
