package me.amc.rankcraft.achievements;

import java.util.HashMap;

import org.bukkit.entity.EntityType;

import me.amc.rankcraft.utils.Reward;

public class AchievementMobKiller extends Achievement {

	private HashMap<String, Integer> p = new HashMap<>();
	private HashMap<String, Boolean> c = new HashMap<>();

	public AchievementMobKiller(String name, EntityType mobType, int kills, Reward r) {
		super(name, Type.KillMobs);
		this.setKillMobsAmt(kills);
		this.setKillMobsType(mobType);
		this.setReward(r);
	}

	@Override
	public HashMap<String, Integer> getKillMobsAmtHM() {
		return p;
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

		return null;
	}

}
