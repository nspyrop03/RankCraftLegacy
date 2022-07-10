package me.amc.rankcraft.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.PermissionList;

public class GuiCommand extends SubCommand {

	public GuiCommand() {
		super("opengui");
	}

	@Override
	public void execute(Player p, String[] args) {

		PermissionList pls = MainCore.instance.permList;

		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("stats")) { // rc opengui stats
				if (p.hasPermission(pls.stats_permission)) {
					MainCore.instance.rankCraft.statsGUI.open(p);
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			if (args[1].equalsIgnoreCase("mobstats")) { // rc opengui mobstats
				if (p.hasPermission(pls.mobStats_permission)) {
					MainCore.instance.rankCraft.mobStatsGUI.open(p);
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			if (args[1].equalsIgnoreCase("packshop")) { // rc opengui packshop
				if (p.hasPermission(pls.packshop_permission)) {
					MainCore.instance.rankCraft.packShop.open(p);
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			if (args[1].equalsIgnoreCase("skills")) { // rc opengui skills
				if (p.hasPermission(pls.skills_permission)) {
					MainCore.instance.rankCraft.skillsGUI.open(p);
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			if (args[1].equalsIgnoreCase("quests")) { // rc opengui quests
				if (p.hasPermission(pls.quests_permission)) {
					MainCore.instance.rankCraft.questGui2.open(p);
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			if (args[1].equalsIgnoreCase("achievements")) { // rc opengui
															// achievements
				if (p.hasPermission(pls.achievements_permission)) {
					MainCore.instance.rankCraft.achievementGui.open(p);
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			if (args[1].equalsIgnoreCase("backpackshop")) { // rc opengui
															// backpackshop
				if (p.hasPermission(pls.backpackshop_permission)) {
					MainCore.instance.rankCraft.bpShop.open(p);
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			if(args[1].equalsIgnoreCase("shop")) { //rc opengui shop
				if(MainCore.instance.config.enableShop) {
					if(p.hasPermission(pls.shop_permission)) {
						MainCore.instance.rankCraft.shop.open(p);
					} else {
						pls.sendNotPermissionMessage(p);
					}
				} else {
					p.sendMessage(MainCore.instance.language.getDisabledShop());
				}
			}
			if(args[1].equalsIgnoreCase("classes")) { //rc opengui classes
				if(p.hasPermission(pls.classes_permission)) {
					MainCore.instance.rankCraft.rpgClassGUI.open(p);
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			/*
			if(args[1].equalsIgnoreCase("selectclass")) { //rc opengui selectgui
				if(p.hasPermission(pls.classes_permission)) {
					MainCore.instance.rankCraft.rpgClassSelectGUI.open(p);
				} else {
					pls.sendNotPermissionMessage(p);
				}
				System.out.println("First command!");
			}
			*/
		} 
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		if(args.length == 2) {
			String[] options = {"stats", "mobstats", "packshop", "skills", "quests", "achievements", "shop", "classes"};
			return Arrays.asList(options);
		}
		return null;
	}

}
