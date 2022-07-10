package me.amc.rankcraft.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LevelUpEvent extends Event implements Cancellable{

	private static final HandlerList handlers = new HandlerList();
	
	private int oldLevel;
	private int newLevel;
	private Player p;
    private boolean cancelled;

    public LevelUpEvent(Player p, int oldLevel, int newLevel) {
       this.p = p;
       this.oldLevel = oldLevel;
       this.newLevel = newLevel;
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
		return p;
	}
	
	public Integer getOldLevel() {
		return oldLevel;
	}
	
	public Integer getNewLevel() {
		return newLevel;
	}
}
