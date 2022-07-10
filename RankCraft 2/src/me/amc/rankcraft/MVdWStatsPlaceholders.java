package me.amc.rankcraft;

import org.bukkit.entity.Player;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import me.amc.rankcraft.classes.RpgClassData;
import me.amc.rankcraft.utils.RCUtils;

public class MVdWStatsPlaceholders {

	public void init() {
		RankCraft rankCraft = MainCore.instance.rankCraft;

		// if
		// (MainCore.instance.getServer().getPluginManager().getPlugin("MVdWPlaceholderAPI")
		// != null) {

		PlaceholderReplacer statsReplacer = new PlaceholderReplacer() {

			@Override
			public String onPlaceholderReplace(PlaceholderReplaceEvent event) {
				
				Player p = event.getPlayer();
				//System.out.println("hi1");
				if (p != null) {
					//System.out.println("hi2");
					switch (event.getPlaceholder()) {

					case "rankcraft_player_level":
						//System.out.println(event.getPlaceholder());
						return "" + rankCraft.stats.getLevel(p);
					case "rankcraft_player_xp":
						return "" + rankCraft.stats.getXp(p);
					case "rankcraft_player_rank":
						return rankCraft.stats.getRank(p);
					case "rankcraft_player_blocksbroken":
						return "" + rankCraft.stats.getBlocksBroken(p);
					case "rankcraft_player_blocksplaced":
						return "" + rankCraft.stats.getBlocksPlaced(p);
					case "rankcraft_player_mana":
						return "" + rankCraft.stats.getMana(p);
					case "rankcraft_player_max_mana":
						return "" + rankCraft.stats.getMaxMana(p);
					case "rankcraft_player_xptolevelup":
						return "" + rankCraft.stats.getXPToLevelUP(p);
					case "rankcraft_player_gold":
						return "" + rankCraft.gold.getGold(p);
					case "rankcraft_player_hp":
						return "" + rankCraft.hpSystem.getHP(p);
					case "rankcraft_player_max_hp":
						return "" + rankCraft.hpSystem.getMaxHP(p);
					case "rankcraft_player_hpbar":
						return rankCraft.hpSystem.getHealthBar(p, 20);
					case "rankcraft_player_class":
						if (RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p)).getName() != null)
							return RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p)).getName();
						else
							return "null";
					case "rankcraft_player_class_level":
						if (RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p)) != null)
							return "" + RpgClassData.getLevel(RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p)),
									p);
						else
							return "null";
					case "rankcraft_player_class_xp":
						if (RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p)) != null)
							return "" + RpgClassData.getXp(RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p)), p);
						else
							return "null";
					case "rankcraft_player_manabar":
						return MainCore.instance.rankCraft.stats.getManaBar(p, 20);

					case "rankcraft_player_xpbar":
						return MainCore.instance.rankCraft.stats.getXpBar(p, 20);
					case "rankcraft_player_hungerbar":
						return MainCore.instance.rankCraft.stats.getHungerBar(p, 20);
					default:
						System.out.println("null1");
						return "null";
					}
					
				} else {
					System.out.println("null2");
					return "null";
				}

			}

		};
		
		//System.out.println(statsReplacer == null);
		
		try {
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_level", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_xp", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_rank", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_blocksbroken", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_blocksplaced", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_mana", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_max_mana", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_xptolevelup", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_gold", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_xp", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_max_hp", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_hpbar", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_class", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_class_level", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_class_xp", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_manabar", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_xpbar", statsReplacer);
			PlaceholderAPI.registerPlaceholder(MainCore.instance, "rankcraft_player_hungerbar", statsReplacer);
		} catch(Exception ex) {
			MainCore.instance.sendError("MVdWPlaceholderAPI placeholders had not been registered!");
		}
		//System.out.println("=====added");
		// } else {
		// return;
		// }

	}

}
