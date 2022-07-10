package me.amc.rankcraft.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.PermissionList;
import me.amc.rankcraft.lang.Language;

public class EnchantCommand extends SubCommand {

	public EnchantCommand() {
		super("enchant");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(Player p, String[] args) {
		
		PermissionList pl = MainCore.instance.permList;
		Language lang = MainCore.instance.language;
		
		if(p.hasPermission(pl.enchant_permission)) {
			if(args.length == 3) {
				// rc enchant <enchantment> <level>
				String enchantment = args[1].toUpperCase();
				int level = 0;
				try {
					level = Integer.parseInt(args[2]);
				} catch(Exception ex) {
					//p.sendMessage(ChatColor.DARK_RED+"Second argument must be a number!");
					p.sendMessage(lang.getSecondArgument());
					return;
				}
				if(Enchantment.getByName(enchantment) != null) { 
					if(p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() != Material.AIR) {
						p.getInventory().getItemInMainHand().addUnsafeEnchantment(Enchantment.getByName(enchantment), level);
					} else {
						//p.sendMessage(ChatColor.DARK_RED+"You must be holding an item!");
						p.sendMessage(lang.getEmptyHand());
					}
				} else {
					//p.sendMessage(ChatColor.DARK_RED+"Invalid Enchantment!");
					p.sendMessage(lang.getInvalidEnchantment());
				}
				
			}
		} else {
			pl.sendNotPermissionMessage(p);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		if(args.length == 2) {
			List<String> names = new ArrayList<>();
			for(Enchantment e : Enchantment.values()) names.add(e.getName());
			return names;
		} else if(args.length == 3) {
			return Arrays.asList("<level>");
		}
		return null;
	}
	
	
}
