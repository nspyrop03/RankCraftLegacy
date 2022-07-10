package me.amc.rankcraft.commands;

import java.util.List;

import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;

public class LeaderboardAlias {

	public static class LeadersAlias extends SubCommand {

		public LeadersAlias() {
			super("leaders");
		}

		@Override
		public void execute(Player p, String[] args) {
			MainCore.instance.rankCraft.commandsList.leaderboardCommand.execute(p, args);
		}

		@Override
		public List<String> getTabCompletion(Player p, String[] args) {
			return MainCore.instance.rankCraft.commandsList.leaderboardCommand.getTabCompletion(p, args);
		}

	}
	
	public static class LbAlias extends SubCommand {

		public LbAlias() {
			super("lb");
		}

		@Override
		public void execute(Player p, String[] args) {
			MainCore.instance.rankCraft.commandsList.leaderboardCommand.execute(p, args);
		}

		@Override
		public List<String> getTabCompletion(Player p, String[] args) {
			return MainCore.instance.rankCraft.commandsList.leaderboardCommand.getTabCompletion(p, args);
		}

	}
	
}
