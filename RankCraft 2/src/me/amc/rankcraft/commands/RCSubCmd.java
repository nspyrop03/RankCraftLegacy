package me.amc.rankcraft.commands;

import java.util.List;

import org.bukkit.entity.Player;

public interface RCSubCmd {

	public void execute(Player p, String[] args);
	
	public List<String> getTabCompletion(Player p, String[] args);
	
}
