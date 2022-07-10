package me.amc.rankcraft.quests2;

import java.util.HashMap;

public interface QuestMaps {

	public HashMap<String, Integer> hmBlocksPlaced();
	
	public HashMap<String, Integer> hmBlocksBroken();
	
	public HashMap<String, Integer> hmMobsKilled();
	
	public HashMap<String, Integer> hmPlayersKilled();
	
	public HashMap<String, Integer> hmSecondsRemaining();
	
	public HashMap<String, Integer> hmSecondsToReTake();
	
	public HashMap<String, Integer> hmCompletedTimes();
	
	public HashMap<String, Boolean> hmTaken();
	
}
