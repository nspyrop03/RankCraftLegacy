package me.amc.rankcraft.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;

public class LeaderboardCommand extends SubCommand {
	
	public LeaderboardCommand() {
		super("leaderboard");
		
	}

	@Override
	public void execute(Player p, String[] args) {
		
		if(args.length == 2) {
			
			if(args[1].equalsIgnoreCase("level")) {
				MainCore.instance.rankCraft.levelLeaderboard.sendLeaderboard(p);
			} else if(args[1].equalsIgnoreCase("blocksbroken") || args[1].equalsIgnoreCase("bb")) {
				MainCore.instance.rankCraft.blocksBrokenLeaderboard.sendLeaderboard(p);
			} else if(args[1].equalsIgnoreCase("blocksplaced") || args[1].equalsIgnoreCase("bp")) {
				MainCore.instance.rankCraft.blocksPlacedLeaderboard.sendLeaderboard(p);
			} else {
			//	p.sendMessage("Wrong Systax! 1");
				p.sendMessage(MainCore.instance.language.getWrongLeaderboard());
			}
			
		}
		
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		if(args.length == 2) {
			return Arrays.asList(new String[] {"level", "blocksbroken", "blocksplaced"});
		}
		return null;
	}

}
