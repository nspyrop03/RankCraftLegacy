package me.amc.rankcraft.skills;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AttackSkill extends Skill {

	private HashMap<String, Integer> pts = new HashMap<>();
	
	public AttackSkill() {
		super(800, ChatColor.RED+"Attack"+ChatColor.GOLD+"Skill", "attack.yml");
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
