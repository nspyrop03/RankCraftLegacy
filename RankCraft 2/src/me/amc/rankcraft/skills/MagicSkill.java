package me.amc.rankcraft.skills;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MagicSkill extends Skill {

	private HashMap<String, Integer> pts = new HashMap<>();
	
	public MagicSkill() {
		super(500, ChatColor.BLUE+"Magic"+ChatColor.GOLD+"Skill", "magic.yml");
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
