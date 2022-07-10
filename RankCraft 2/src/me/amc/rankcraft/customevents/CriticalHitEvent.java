package me.amc.rankcraft.customevents;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class CriticalHitEvent extends Event implements Cancellable{

	private static final HandlerList handlers = new HandlerList();
	
	private boolean cancelled;
	private Player player;
	private double criticalDamage;
	private ItemStack playerHolding;
	private Entity damagedEntity;
	
	public CriticalHitEvent(Player player, double criticalDamage, ItemStack playerHolding, Entity damagedEntity) {
		this.player = player;
		this.criticalDamage = criticalDamage;
		this.playerHolding = playerHolding;
		this.damagedEntity = damagedEntity;
	}
	
	public Entity getDamagedEntity() {
		return damagedEntity;
	}

	public Player getPlayer() {
		return player;
	}

	public double getCriticalDamage() {
		return criticalDamage;
	}

	public ItemStack getPlayerHolding() {
		return playerHolding;
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
