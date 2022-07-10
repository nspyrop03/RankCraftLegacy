package me.amc.rankcraft.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.amc.rankcraft.treasures2.TreasureChest2;

public class PlayerFindTreasureEvent extends Event implements Cancellable{

	private static final HandlerList handlers = new HandlerList();
	
	private TreasureChest2 t;
	private Player p;
    private boolean cancelled;

    public PlayerFindTreasureEvent(Player p, TreasureChest2 t) {
       this.p = p;
       this.t = t;
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
	
	public TreasureChest2 getTreasure() {
		return t;
	}
}
