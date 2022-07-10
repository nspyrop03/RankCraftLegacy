package me.amc.rankcraft.achievements;

import java.util.HashMap;

import org.bukkit.entity.EntityType;

import me.amc.rankcraft.utils.Reward;

public class AchievementKillZombies extends Achievement {

	private HashMap<String, Integer> mk = new HashMap<>();
	private HashMap<String, Boolean> c = new HashMap<>();
	
	public AchievementKillZombies(String name, int killMobsAmt, Reward r) {
		super(name, Type.KillMobs);
		this.setKillMobsAmt(killMobsAmt);
		this.setKillMobsType(EntityType.ZOMBIE);
		this.setReward(r);
	}
	
	@Override
	public HashMap<String, Integer> getKillMobsAmtHM() {
		return mk;
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
