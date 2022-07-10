package me.amc.rankcraft.bosses;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import me.amc.rankcraft.utils.CustomItem;

public class BasicBoss {

	private CustomItem egg;
	private String name;
	
	public BasicBoss(String name) {
		this.name = name;
		this.egg = new CustomItem(Material.IRON_BLOCK, ChatColor.DARK_PURPLE+"Spawn "+this.name+ChatColor.DARK_PURPLE+" Boss");
		this.egg.build(true);
	}

	public CustomItem getEgg() {
		return egg;
	}

	public void setEgg(CustomItem egg) {
		this.egg = egg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
}
