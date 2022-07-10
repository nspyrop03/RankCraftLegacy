package me.amc.rankcraft.stats;

import java.io.File;
import java.util.HashMap;

import me.amc.rankcraft.utils.LevelFileReward;
import me.amc.rankcraft.utils.RCUtils;

public class LevelRewardSystem {

	private File[] files;

	// Map < Level, Reward >
	public HashMap<Integer, LevelFileReward> rewards = new HashMap<>();
	
	public LevelRewardSystem() {
		
		files = RCUtils.LEVEL_REWARDS_DIRECTORY.listFiles();
		
		for(File f : files) {
			//System.out.println(f.getName());
			LevelFileReward lfr = new LevelFileReward(f.getName());
			rewards.put(lfr.getLevel(), lfr);
			//System.out.println(rewards.containsKey(50));
			//System.out.println(""+lfr.getLevel());
		}
		
		System.out.println("[RankCraft] Loaded "+rewards.size()+" level files!");
		
	}
	
}
