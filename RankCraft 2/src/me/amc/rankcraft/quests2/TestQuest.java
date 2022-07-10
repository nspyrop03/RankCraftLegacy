package me.amc.rankcraft.quests2;

import java.util.HashMap;

import org.bukkit.Material;

import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.Reward;

public class TestQuest extends Quest {

	private HashMap<String, Integer> bp = new HashMap<>();
	private HashMap<String, Integer> sr = new HashMap<>();
	private HashMap<String, Integer> strt = new HashMap<>();
	private HashMap<String, Integer> ct = new HashMap<>();
	private HashMap<String, Boolean> t = new HashMap<>();

	public TestQuest(String name, int placeBlockAmount, int secondsToComplete, int secondsToReTake) {
		super(QuestType.PlaceBlocks, name);
		this.setPlaceBlockAmount(placeBlockAmount);
		this.setSecondsToComplete(secondsToComplete);
		this.setSecondsToReTake(secondsToReTake);
		this.setPlaceBlocksType(Material.GRASS);
		Reward r = new Reward(10.0F, 15.0F, new CustomItem(Material.GRASS, "GRASS FROM QUEST").build(true));
		this.setReward(r);

		

	}

	@Override
	public HashMap<String, Integer> hmBlocksPlaced() {
		return bp;
	}

	@Override
	public HashMap<String, Integer> hmBlocksBroken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Integer> hmMobsKilled() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Integer> hmPlayersKilled() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Integer> hmSecondsRemaining() {
		// TODO Auto-generated method stub
		return sr;
	}

	@Override
	public HashMap<String, Integer> hmSecondsToReTake() {
		// TODO Auto-generated method stub
		return strt;
	}

	@Override
	public HashMap<String, Integer> hmCompletedTimes() {
		// TODO Auto-generated method stub
		return ct;
	}

	@Override
	public HashMap<String, Boolean> hmTaken() {
		// TODO Auto-generated method stub
		return t;
	}

}
