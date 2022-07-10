package me.amc.rankcraft.events;

import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.damage.HPSystem;
import me.amc.rankcraft.utils.RCUtils;

public class HPEvents implements Listener {

	
	
	@EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onReSpawn(PlayerRespawnEvent e) {
		
		if(RCUtils.isWorldEnabled(e.getPlayer().getWorld())) {
		
			HPSystem s = MainCore.instance.rankCraft.hpSystem;
			s.fillHP(e.getPlayer());
		
			

			
			
		}
	}
	
	/*
	@EventHandler (ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onDeath(PlayerDeathEvent e) {
	//	Player dead = e.getEntity();
	
		if(dead.getKiller() instanceof Player) {
			Player killer = (Player) dead.getKiller();
			e.setDeathMessage(dead.getName()+" was killed by "+killer.getName());
		} else {
			e.setDeathMessage(dead.getName()+" died !");
		}
	
	//	e.setDeathMessage("ok ");
	}
	*/
	
	@EventHandler
	public void onChangeWorld(PlayerChangedWorldEvent e) {
		
		if(!RCUtils.isWorldEnabled(e.getPlayer().getWorld())) {
			
			e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
			//e.getPlayer().setMaxHealth(20);
			e.getPlayer().setHealth(e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
			
			
		//	Player p = e.getPlayer();
			/*
			if (p.getScoreboard() != null) {
				Scoreboard board = p.getScoreboard();
				Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
				try {
					objective.unregister();
				} catch (Exception ex) {
					MainCore.instance.sendError("Cannot unregister scoreboard object !!!");
				}
				p.setScoreboard(MainCore.instance.emptyBoard);
			}
			*/
		//	p.setScoreboard(MainCore.instance.emptyBoard);
		} /*else {
			MainCore.instance.scoreboard.updateBoard(e.getPlayer());
		}*/
		
	}
}
