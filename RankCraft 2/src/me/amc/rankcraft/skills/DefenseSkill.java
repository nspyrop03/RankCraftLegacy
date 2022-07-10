package me.amc.rankcraft.skills;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DefenseSkill extends Skill {
	
	private HashMap<String, Integer> pts = new HashMap<>();
	
	public DefenseSkill() {
		super(800, ChatColor.GREEN+"Defense"+ChatColor.GOLD+"Skill", "defense.yml");
	}

	@Override
	public HashMap<String, Integer> getSkillPoints() {
		return pts;
	}
	
	@Override
	public void initForPlayer(Player p) {
		super.initForPlayer(p);
	}
}
