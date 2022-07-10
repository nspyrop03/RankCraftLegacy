package me.amc.rankcraft.achievements;

import java.util.HashMap;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.Reward;

public class AchievementBlockBreaker extends Achievement {

	private HashMap<String, Integer> p = new HashMap<>();
	private HashMap<String, Boolean> c = new HashMap<>();
	
	public AchievementBlockBreaker(String name, int blocks, Reward r) {
		super(name, Type.Special);
		this.setSpecialPoints(blocks);
		this.setReward(r);
	//	this.setSpecialMission("§eBreak §4"+blocks+" §eblocks.");
		this.setSpecialMission(MainCore.instance.guiLang.getAchievementBlockBreaker(blocks));
	}

	@Override
	public HashMap<String, Integer> getKillMobsAmtHM() {
		
		return null;
	}

	@Override
	public HashMap<String, Integer> getKillPlayersAmtHM() {
		
		return null;
	}

	@Override
	public HashMap<String, Boolean> getCompletedHM() {
		
		return c;
	}

	@Override
	public HashMap<String, Integer> getBlocksPlacedHM() {
		
		return null;
	}

	@Override
	public HashMap<String, Integer> getBlocksBrokenHM() {
		
		return null;
	}

	@Override
	public HashMap<String, Integer> getSpecialPointsHM() {
		
		return p;
	}

	
}
