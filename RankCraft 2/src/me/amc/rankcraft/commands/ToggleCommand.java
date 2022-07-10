package me.amc.rankcraft.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class ToggleCommand extends SubCommand{

public static List<String> disabledPlayers = new ArrayList<>();
	
	public ToggleCommand() {
		super("toggle");
	}
	
	@Override
	public void execute(Player p, String[] args) {
		if(args.length == 2) { // rc toggle scoreboard
			if(args[1].equalsIgnoreCase("scoreboard")) {
				//p.sendMessage("2 args");
				if(p.hasPermission(MainCore.instance.permList.toggleScoreboard_permission)) {
					if(disabledPlayers.contains(RCUtils.textedUUID(p))) {
						disabledPlayers.remove(RCUtils.textedUUID(p));
						
					} else {
						disabledPlayers.add(RCUtils.textedUUID(p));
						p.setScoreboard(MainCore.instance.scoreboard.getScoreboardManager().getNewScoreboard());
					}
				}
			}
		} else if(args.length == 3) { // rc toggle scoreboard player
			if(args[1].equalsIgnoreCase("scoreboard")) {
				//p.sendMessage("3 args");
				if(p.hasPermission(MainCore.instance.permList.toggleScoreboardOthers_permission)) {
					Player target = MainCore.instance.getServer().getPlayer(args[2]);
					if(target != null) {
						
						if(disabledPlayers.contains(RCUtils.textedUUID(target))) {
							disabledPlayers.remove(RCUtils.textedUUID(target));
							
						} else {
							disabledPlayers.add(RCUtils.textedUUID(target));
							target.setScoreboard(MainCore.instance.scoreboard.getScoreboardManager().getNewScoreboard());
						}
						
					}
				}
			}
			
		}
		
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		
		if(args.length == 2) {
			return Arrays.asList("scoreboard");
		} else if(args.length == 3) {
			List<String> names = new ArrayList<>();
			for(Player pl : Bukkit.getOnlinePlayers())
				names.add(pl.getName());
			return names;
		}
		
		return null;
	}
	
}
