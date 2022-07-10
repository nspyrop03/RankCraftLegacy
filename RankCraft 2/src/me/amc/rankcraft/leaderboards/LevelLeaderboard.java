package me.amc.rankcraft.leaderboards;

import java.util.ArrayList;
import java.util.List;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.stats.Stats;

public class LevelLeaderboard extends Leaderboard {

	private static List<Member> leaderboardMembers = new ArrayList<>();
	
	public LevelLeaderboard(String title) {
	//	super("Level Leaderboard", LeaderboardType.Level, leaderboardMembers);
		super(title, LeaderboardType.Level, leaderboardMembers);
	}

	@Override
	public void update() {
		super.update();
		
		Stats stats = MainCore.instance.rankCraft.stats;
		
		for(int k = 0; k < stats.getLevelHM().size(); k++) {
			Member m = new Member(stats.getLevelHM().keySet().toArray()[k].toString(),stats.getLevelHM().get(stats.getLevelHM().keySet().toArray()[k]),0,0,0);
			leaderboardMembers.add(m);
		}
		this.bubbleSort();
		
	}
	
}
