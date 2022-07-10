package me.amc.rankcraft.leaderboards;

import java.util.ArrayList;
import java.util.List;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.stats.Stats;

public class BlocksBrokenLeaderboard extends Leaderboard{

	private static List<Member> leaderboardMembers = new ArrayList<>();
	
	public BlocksBrokenLeaderboard() {
	//	super("Blocks Broken Leaderboard", LeaderboardType.BlocksBroken, leaderboardMembers);
		super(MainCore.instance.language.getBBTitle(), LeaderboardType.BlocksBroken, leaderboardMembers);
	}
	
	@Override
	public void update() {
		super.update();
		
		Stats stats = MainCore.instance.rankCraft.stats;
		
		for(int k = 0; k < stats.getBbHM().size(); k++) {
			Member m = new Member(stats.getBbHM().keySet().toArray()[k].toString(), 0, stats.getBbHM().get(stats.getBbHM().keySet().toArray()[k]),0,0);
			leaderboardMembers.add(m);
		}
		this.bubbleSort();
	}

}
