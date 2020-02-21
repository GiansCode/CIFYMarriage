package com.codeitforyou.marriage.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class CIFYMarriageEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    private UUID player1;
    private UUID player2;
    private Long time;

    public CIFYMarriageEvent(UUID player1, UUID player2, Long time) {
        this.player1 = player1;
        this.player2 = player2;
        this.time = time;
    }

    public UUID getFirstPartner() {
        return player1;
    }

    public UUID getSecondPartner() {
        return player2;
    }

    public Long getTime() {
        return time;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
