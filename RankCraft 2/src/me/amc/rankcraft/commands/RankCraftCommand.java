package me.amc.rankcraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class RankCraftCommand implements TabExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		List<SubCommand> commands = new ArrayList<>();
		commands.addAll(MainCore.instance.rankCraft.getSubCommandHM().values());
		
		if(!(sender instanceof Player)) return false;
		
		Player p = (Player) sender;
		
		// System.out.println(commands);
		if(RCUtils.isWorldEnabled(p.getWorld())) {
			if(args.length > 0) {
			//	System.out.println("ok0");
				if(commands.contains(MainCore.instance.rankCraft.getSubCommandFromLabel(args[0].toLowerCase()))) {
			//		System.out.println("ok1");
			//	if(containsCaseInsensitive(MainCore.instance.rankCraft.getSubCommandFromLabel(args[0]), commands)) {
					SubCommand s = MainCore.instance.rankCraft.getSubCommandFromLabel(args[0].toLowerCase());
					s.execute(p, args);
			//		System.out.println("ok2");
				}
			} else {
				p.sendMessage(ChatColor.GOLD+"=====["+ChatColor.RED+"RankCraft"+ChatColor.GOLD+"]=====");
				p.sendMessage(ChatColor.YELLOW+"Version: "+ChatColor.GRAY+""+MainCore.instance.getDescription().getVersion());
				p.sendMessage(ChatColor.YELLOW+"Author: "+ChatColor.DARK_RED+"Arionas_MC");
				p.sendMessage(ChatColor.YELLOW+"Use: '/rankcraft help' for help");
				p.sendMessage(ChatColor.GOLD+"=====["+ChatColor.RED+"RankCraft"+ChatColor.GOLD+"]=====");
			}
		} else {
			
			p.sendMessage(MainCore.instance.language.getNotEnabledCommandInWorld());
			
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) return null;
		
		Player p = (Player) sender;
		
		if(args.length == 1) {
			return new ArrayList<>(MainCore.instance.rankCraft.commands.keySet());
		} else {
			List<SubCommand> commands = new ArrayList<>();
			commands.addAll(MainCore.instance.rankCraft.getSubCommandHM().values());
			if(commands.contains(MainCore.instance.rankCraft.getSubCommandFromLabel(args[0].toLowerCase()))) {
				SubCommand s = MainCore.instance.rankCraft.getSubCommandFromLabel(args[0].toLowerCase());
				return s.getTabCompletion(p, args);
			}
		}
		return null;
	}
	
	/*
	public boolean containsCaseInsensitive(String s, List<String> l){
	     for (String string : l){
	        if (string.equalsIgnoreCase(s)){
	            return true;
	         }
	     }
	    return false;
	  }
	*/
}
