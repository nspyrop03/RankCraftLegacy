package me.amc.rankcraft.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.PermissionList;
import me.amc.rankcraft.lang.Language;
import me.amc.rankcraft.treasures2.TreasureChest2;
import me.amc.rankcraft.treasures2.TreasureChest2.Rarity;
import me.amc.rankcraft.utils.RCUtils;

public class TreasureCommand extends SubCommand {

	public TreasureCommand() {
		super("treasure");
	}

	@Override
	public void execute(Player p, String[] args) {
		
		PermissionList pl = MainCore.instance.permList;
		Language lang = MainCore.instance.language;
		
		if(p.hasPermission(pl.treasure_permission)) {
			if(args.length == 5) {
				
				// Command: /rankcraft treasure make <TreasureName> <Id> <Rarity>
				if(args[1].equalsIgnoreCase("make")) {
					String name = args[2];
					String fileName = args[3];
					String rarity = args[4];
					File f = new File(RCUtils.TREASURES2_DIRECTORY+"/"+fileName+".yml");
					
					if(f.exists()) {
						//p.sendMessage("You have already created a treasure with the same Id!");
						p.sendMessage(lang.getTreasures2SameId());
						return;
					}
					
					//p.sendMessage("Making treasure...");
					p.sendMessage(lang.getTreasures2Creating());
				
					
					//name = name.replace('&', '§');
					//name = name.replace('_', ' ');
					
					new TreasureChest2(name, fileName, Rarity.valueOf(rarity.toUpperCase()));
					
					Inventory temp = Bukkit.createInventory(p, InventoryType.CHEST, "Treasure Maker: "+fileName);
					p.openInventory(temp);
					/*
					File f = new File(RCUtils.TREASURES2_DIRECTORY+"/"+fileName+".treasure");
					
					FileConfiguration c = YamlConfiguration.loadConfiguration(f);
					List<ItemStack> items = new ArrayList<>();
					for (ItemStack it : p.getInventory().getContents()) {
						items.add(it);
					}
					c.set("backpack.items", items);
					try {
						c.save(f);
					} catch (IOException e) {
						MainCore.instance.sendError("[Treasures2] Oups, something went wrong!");
					}
					*/
				}
			} else if(args.length == 3) {
				// rankcraft treasure edit <Id>
				if(args[1].equalsIgnoreCase("edit")) {
					String fileName = args[2];
					File f = new File(RCUtils.TREASURES2_DIRECTORY+"/"+fileName+".yml");
					
					if(!f.exists()) {
						//p.sendMessage("This treasure does not exist!");
						p.sendMessage(lang.getTreasures2NotExist());
						return;
					}
					
					TreasureChest2 tc2 = new TreasureChest2("Testing...", fileName, Rarity.COMMON);
					Inventory temp = Bukkit.createInventory(p, InventoryType.CHEST, "Treasure Editor: "+fileName);
					//temp.setContents(tc2.getTreasureItems());
					//System.out.println(tc2 == null);
					for(int i = 0; i < tc2.getTreasureItems().size(); i++) {
						temp.setItem(i, tc2.getTreasureItems().get(i));
					}
					
					p.openInventory(temp);
					
				}
			} else if(args.length == 2) {
				// rankcraft treasure list
				if(args[1].equalsIgnoreCase("list")) {
					p.sendMessage(lang.getTreasures2ListTitle());
					for(TreasureChest2 tc2 : MainCore.instance.rankCraft.treasureChests) {
						//p.sendMessage(tc2.getName()+ChatColor.WHITE+" ("+tc2.getFile().getName()+")");
						p.sendMessage(lang.getTreasures2ListLine(tc2.getName(), tc2.getFile().getName()));
					}
				}
			}
		} else {
			pl.sendNotPermissionMessage(p);
		}
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		if(args.length == 2) {
			
			String[] c = {"make", "edit", "list"};
			return Arrays.asList(c);
			
		} else if(args.length == 3) {
			
			if(args[1].equalsIgnoreCase("edit")) {
				List<String> ids = new ArrayList<>();
				for(TreasureChest2 tc2 : MainCore.instance.rankCraft.treasureChests) 
					ids.add(tc2.getId());
				return ids;
			} else if(args[1].equalsIgnoreCase("make")) {
				return Arrays.asList("<name>");
			}
			
		} else if(args.length == 4) {
			if(args[1].equalsIgnoreCase("make")) {
				return Arrays.asList("<id>");
			}
		} else if(args.length == 5) {
			if(args[1].equalsIgnoreCase("make")) {
				String[] c = {Rarity.COMMON.name(), Rarity.RARE.name(), Rarity.EPIC.name()};
				return Arrays.asList(c);
			}
		}
		
		return null;
	}
}
