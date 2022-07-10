package me.amc.rankcraft.achievements;

import java.util.HashMap;

public interface AchievementMaps {

	public HashMap<String, Integer> getKillMobsAmtHM();
	
	public HashMap<String, Integer> getKillPlayersAmtHM();
	
	public HashMap<String, Boolean> getCompletedHM();
	
	public HashMap<String, Integer> getBlocksPlacedHM();
	
	public HashMap<String, Integer> getBlocksBrokenHM();
	
	public HashMap<String, Integer> getSpecialPointsHM();
}
