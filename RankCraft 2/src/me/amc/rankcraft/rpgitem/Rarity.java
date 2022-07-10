package me.amc.rankcraft.rpgitem;

import org.bukkit.ChatColor;

public enum Rarity {

	COMMON(ChatColor.GREEN),
	RARE(ChatColor.BLUE),
	EPIC(ChatColor.GOLD),
	LEGENDARY(ChatColor.DARK_PURPLE);
	
	private ChatColor color;
	
	Rarity(ChatColor color) {
		this.color = color;
	}
	
	public ChatColor getColor() {
		return this.color;
	}
}
