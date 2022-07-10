package me.amc.rankcraft.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.amc.rankcraft.classes.RpgClass;

public class RpgClassLevelUpEvent extends Event implements Cancellable{

	private static final HandlerList handlers = new HandlerList();
	
	private boolean cancelled;
	private Player player;
	private RpgClass rpgClass;
	private int newLevel;
	private int oldLevel;
	
	
	public RpgClassLevelUpEvent(Player player, RpgClass rpgClass, int oldLevel, int newLevel) {
		this.player = player;
		this.rpgClass = rpgClass;
		this.oldLevel = oldLevel;
		this.newLevel = newLevel;
	}

	public Player getPlayer() {
		return player;
	}

	public RpgClass getRpgClass() {
		return rpgClass;
	}

	public int getOldLevel() {
		return oldLevel;
	}

	public int getNewLevel() {
		return newLevel;
	}

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
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
		
	}
}
