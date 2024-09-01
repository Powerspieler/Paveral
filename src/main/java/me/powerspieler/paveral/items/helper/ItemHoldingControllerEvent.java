package me.powerspieler.paveral.items.helper;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ItemHoldingControllerEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final String itemType;

    public ItemHoldingControllerEvent(Player player, String itemType) {
        this.player = player;
        this.itemType = itemType;
    }

    public Player getPlayer() {
        return player;
    }

    public String getItemType() {
        return itemType;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
