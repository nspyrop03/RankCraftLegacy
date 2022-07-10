package me.amc.rankcraft.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.PermissionList;
import me.amc.rankcraft.RankCraft;
import me.amc.rankcraft.rpgitem.YamlArmor;
import me.amc.rankcraft.rpgitem.YamlBase;
import me.amc.rankcraft.rpgitem.YamlItem;
import me.amc.rankcraft.utils.RCUtils;

public class ItemCommand extends SubCommand {
	
	private RankCraft rankCraft;
	private PermissionList pls;
	
	public ItemCommand() {
		super("item");
		rankCraft = MainCore.instance.rankCraft;
		pls = MainCore.instance.permList;
	}

	@Override
	public void execute(Player p, String[] args) {
		if(p.hasPermission(pls.itemCreate_permission)) {
			if(args.length == 5) {
				if(args[1].equalsIgnoreCase("create")) { //rc item create <id> <name> <minLevel>
					String id = args[2];
					if(yamlFilenameExists(id)) {
						p.sendMessage(MainCore.instance.language.getPrefix()+"There already an item with the same id!");
						return;
					}
					String name = args[3];
					int minLevel = 0;
					try {
						minLevel = Integer.parseInt(args[4]);
					} catch(Exception ex) {
						return;
					}
					
					YamlBase b = new YamlBase(id, name, minLevel);
					MainCore.instance.rankCraft.itemCreator.openSelectTypeGUI(p, b);
				}
			} else if(args.length == 3) {
				if(args[1].equalsIgnoreCase("edit")) { // rc item edit <id>
					String id = args[2];
					if(yamlFilenameExists(id)) {
						if(isYamlWeapon(id)) {
							rankCraft.itemCreator.openWeaponStage(rankCraft.itemCreator.WEAPON_ITEM_STAGE, p, rankCraft.getYamlWeaponFromFileName(id));
						} else {
							rankCraft.itemCreator.openArmorStage(rankCraft.itemCreator.ARMOR_ITEM_STAGE, p, rankCraft.getYamlArmorFromFileName(id+".armor"));
						}
					} else {
						p.sendMessage(MainCore.instance.language.getPrefix()+"There is no item with the given id!");
					}
				}
			} else if(args.length == 4) {
				if(args[1].equalsIgnoreCase("edit")) { // rc item edit <id> <stage>
					String id = args[2];
					String stage = args[3];
					if(yamlFilenameExists(id)) {
						if(isYamlWeapon(id)) {
							if(stage.equalsIgnoreCase("item")) stage = rankCraft.itemCreator.WEAPON_ITEM_STAGE;
							else if(stage.equalsIgnoreCase("rarity")) stage = rankCraft.itemCreator.WEAPON_RARITY_STAGE;
							else if(stage.equalsIgnoreCase("recipe")) stage = rankCraft.itemCreator.WEAPON_RECIPE_STAGE;
							else if(stage.equalsIgnoreCase("stats")) stage = rankCraft.itemCreator.WEAPON_STATS_STAGE;
							else if(stage.equalsIgnoreCase("finish")) stage = rankCraft.itemCreator.WEAPON_FINISH_STAGE;
							else {
								p.sendMessage(MainCore.instance.language.getPrefix()+"Invalid stage!");
								return;
							}
							rankCraft.itemCreator.openWeaponStage(stage, p, rankCraft.getYamlWeaponFromFileName(id));
						} else {
							if(stage.equalsIgnoreCase("item")) stage = rankCraft.itemCreator.ARMOR_ITEM_STAGE;
							else if(stage.equalsIgnoreCase("rarity")) stage = rankCraft.itemCreator.ARMOR_RARITY_STAGE;
							else if(stage.equalsIgnoreCase("recipe")) stage = rankCraft.itemCreator.ARMOR_RECIPE_STAGE;
							else if(stage.equalsIgnoreCase("stats")) stage = rankCraft.itemCreator.ARMOR_DEFENSE_STAGE;
							else if(stage.equalsIgnoreCase("finish")) stage = rankCraft.itemCreator.ARMOR_FINISH_STAGE;
							else {
								p.sendMessage(MainCore.instance.language.getPrefix()+"Invalid stage!");
								return;
							}
							//p.sendMessage(""+(rankCraft.getYamlArmorFromFileName(id+".armor") == null));
							rankCraft.itemCreator.openArmorStage(stage, p, rankCraft.getYamlArmorFromFileName(id+".armor"));
						}
					} else {
						p.sendMessage(MainCore.instance.language.getPrefix()+"There is no item with the given id!");
					}
				}
			}
		} else {
			pls.sendNotPermissionMessage(p);
		}
		
	}
	
	private boolean yamlFilenameExists(String id) {
		for(YamlItem w : rankCraft.yamlWeapons) 
			if(w.getFileName().equals(id))
				return true;
		for(YamlArmor a : rankCraft.yamlArmors) 
			if(a.getFileName().equals(id+".armor"))
				return true;
		return false;
	}
	
	private boolean isYamlWeapon(String id) {
		for(YamlItem w : rankCraft.yamlWeapons) 
			if(w.getFileName().equals(id)) 
				return true;
		return false;
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		if(args.length == 2) {
			return Arrays.asList("create", "edit");
		} else if(args.length == 3) {
			if(args[1].equalsIgnoreCase("create")) {
				return Arrays.asList("<id>");
			} else if(args[1].equalsIgnoreCase("edit")) {
				List<String> ids = new ArrayList<>();
				for(YamlItem w : rankCraft.yamlWeapons)
					ids.add(w.getFileName());
				for(YamlArmor a : rankCraft.yamlArmors)
					ids.add(RCUtils.getStringWithoutExtension(a.getFileName()));
				return ids;
			}
		} else if(args.length == 4) {
			if(args[1].equalsIgnoreCase("create")) {
				return Arrays.asList("<name>");
			} else if(args[1].equalsIgnoreCase("edit")) {
				return Arrays.asList(new String[] {"ITEM","RARITY","RECIPE","STATS","FINISH"});
			}
		} else if(args.length == 5) {
			if(args[1].equalsIgnoreCase("create")) {
				return Arrays.asList("<level>");
			}
		}
		return null;
	}
	
}
