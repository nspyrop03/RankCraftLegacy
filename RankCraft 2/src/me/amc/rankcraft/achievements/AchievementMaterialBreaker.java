package me.amc.rankcraft.achievements;

import java.util.HashMap;

import org.bukkit.Material;

import me.amc.rankcraft.utils.Reward;

public class AchievementMaterialBreaker extends Achievement {

	private HashMap<String, Integer> b = new HashMap<>();
	private HashMap<String, Boolean> c = new HashMap<>();
	
	public AchievementMaterialBreaker(String name, int blocks, Material blockMaterial, Reward r) {
		super(name, Type.BreakBlocks);
		this.setBreakBlocksAmt(blocks);
		this.setBreakBlocksType(blockMaterial);
		this.setReward(r);
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
		
		return b;
	}

	@Override
	public HashMap<String, Integer> getSpecialPointsHM() {
		
		return null;
	}

	
	
}
