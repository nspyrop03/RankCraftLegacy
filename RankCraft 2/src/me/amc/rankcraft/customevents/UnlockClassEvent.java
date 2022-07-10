package me.amc.rankcraft.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.amc.rankcraft.classes.RpgClass;

public class UnlockClassEvent extends Event implements Cancellable{

	private static final HandlerList handlers = new HandlerList();

	private Player player;
	private RpgClass rpgClass;
    private boolean cancelled;

    public UnlockClassEvent(Player player, RpgClass rpgClass) {
       this.player = player;
       this.rpgClass = rpgClass;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

	public Player getPlayer() {
		return player;
	}

	public RpgClass getRpgClass() {
		return rpgClass;
	}
    
	
}
