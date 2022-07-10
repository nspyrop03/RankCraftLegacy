package me.amc.rankcraft.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;

public class HelpCommand extends SubCommand {

	public HelpCommand() {
		super("help");
	}

	
	
	@Override
	public void execute(Player p, String[] args) {
		if(args.length == 1) {
			sendHelp(p, 1);
		} else if(args.length == 2) {
			int page = 1;
			try {
				page = Integer.parseInt(args[1]);
			} catch(Exception ex) {
				MainCore.instance.sendError("HelpCommand: There was an error!");
			}
			sendHelp(p, page);
		} else {
			sendHelp(p, 1);
		}
		
	}

	private void sendHelp(Player p, int page) {
		if(page <= 0) {
			page = 1;
		}
		List<CommandDescription> raw = MainCore.instance.rankCraft.commandDescriptions;
		List<CommandDescription> edited = new ArrayList<>();
			
		for(CommandDescription cd : raw) {
			if(cd.isLocked()) {
				if(p.hasPermission(cd.getPermission())) {
					edited.add(cd);
				}
			} else {
				edited.add(cd);
			}
		}
		int size = edited.size();
		int lastPage; // = size / 10;
		if(size % 10 == 0) {
			lastPage = size / 10;
		} else {
			lastPage = size / 10 + 1;
		}
		
		if(!(page <= lastPage)) {
			page = lastPage;
		}
		
		Collections.sort(edited, new Comparator<CommandDescription>() {
		    @Override
		    public int compare(CommandDescription s1, CommandDescription s2) {
		        return s1.getCommand().compareToIgnoreCase(s2.getCommand());
		    }
		});
			
		p.sendMessage(ChatColor.GRAY+"==========["+ChatColor.DARK_RED+"RankCraft"+ChatColor.YELLOW+" Help page ("+page+"/"+lastPage+")"+ChatColor.GRAY+"]==========");
		for(int i = 10 * (page - 1); i <= page * 10 - 1; i++) {
			if(i < size) {
				p.sendMessage(edited.get(i).getFullLine());
			} /*else {
				MainCore.instance.sendError("OutOfBounds! Fixing...");
			}*/
		}
		
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		if(args.length == 2) {
			return Arrays.asList("<page-number>");
		}
		return null;
	}
}
