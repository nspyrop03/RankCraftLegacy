package me.amc.rankcraft.achievements;

import java.util.HashMap;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.Reward;

public class AchievementFindTreasures extends Achievement {

	private HashMap<String, Integer> p = new HashMap<>();
	private HashMap<String, Boolean> c = new HashMap<>();

	public AchievementFindTreasures(String name, int points, Reward r) {
		super(name, Type.Special);
		this.setSpecialPoints(points);
		this.setReward(r);
		//this.setSpecialMission("§eFind §4"+points+" §etreasures.");
		this.setSpecialMission(MainCore.instance.guiLang.getAchievementTreasure(points));
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
