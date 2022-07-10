package me.amc.rankcraft.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.amc.rankcraft.MainCore;

public class PlayerLeaveServerEvent implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.getPlayer().setScoreboard(MainCore.instance.scoreboard.getScoreboardManager().getNewScoreboard());
		//MainCore.instance.rankCraft.removeBackpack(e.getPlayer());
	}
	
}
