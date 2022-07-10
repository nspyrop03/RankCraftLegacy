package me.amc.rankcraft.customevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.amc.rankcraft.spells.Spell;

public class PlayerUseSpellEvent extends Event implements Cancellable{

	private static final HandlerList handlers = new HandlerList();
	
	private Spell s;
	private Player p;
    private boolean cancelled;

    public PlayerUseSpellEvent(Player p, Spell s) {
       this.p = p;
       this.s = s;
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
	
	public Spell getSpell() {
		return s;
	}
}
