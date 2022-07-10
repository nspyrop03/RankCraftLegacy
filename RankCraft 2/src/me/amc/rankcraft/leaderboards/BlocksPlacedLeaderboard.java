package me.amc.rankcraft.leaderboards;

import java.util.ArrayList;
import java.util.List;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.stats.Stats;

public class BlocksPlacedLeaderboard extends Leaderboard{

	private static List<Member> leaderboardMembers = new ArrayList<>();
	
	public BlocksPlacedLeaderboard() {
	//	super("Blocks Placed Leaderboard", LeaderboardType.BlocksPlaced, leaderboardMembers);
		super(MainCore.instance.language.getBPTitle(), LeaderboardType.BlocksPlaced, leaderboardMembers);
	}
	
	@Override
	public void update() {
		super.update();
		
		Stats stats = MainCore.instance.rankCraft.stats;
		
		for(int k = 0; k < stats.getBpHM().size(); k++) {
			Member m = new Member(stats.getBpHM().keySet().toArray()[k].toString(), 0, 0, stats.getBpHM().get(stats.getBpHM().keySet().toArray()[k]),0);
			leaderboardMembers.add(m);
		}
		this.bubbleSort();
	}

}
