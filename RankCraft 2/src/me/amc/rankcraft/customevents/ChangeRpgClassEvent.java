package me.amc.rankcraft.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.amc.rankcraft.classes.RpgClass;

public class ChangeRpgClassEvent extends Event implements Cancellable{

	private static final HandlerList handlers = new HandlerList();
	
	private boolean cancelled;
	private Player player;
	private RpgClass oldClass;
	private RpgClass newClass;
	
	
	public ChangeRpgClassEvent(Player player, RpgClass oldClass, RpgClass newClass) {
		this.player = player;
		this.oldClass = oldClass;
		this.newClass = newClass;
	}

	public Player getPlayer() {
		return player;
	}

	public RpgClass getOldClass() {
		return oldClass;
	}

	public RpgClass getNewClass() {
		return newClass;
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
