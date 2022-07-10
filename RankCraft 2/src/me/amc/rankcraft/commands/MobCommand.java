package me.amc.rankcraft.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.PermissionList;
import me.amc.rankcraft.lang.Language;
import me.amc.rankcraft.mobs.YamlMob;
import me.amc.rankcraft.utils.RCUtils;

public class MobCommand extends SubCommand {

	public MobCommand() {
		super("mob");
	}

	@Override
	public void execute(Player p, String[] args) {
		if(MainCore.instance.config.enableCustomMobs) {
			PermissionList pls = MainCore.instance.permList;
			Language lang = MainCore.instance.language;
			
			if(p.hasPermission(pls.mobCreate_permission)) {
				
				if(args.length == 3) {
					
					
				/*	if(args[1].equalsIgnoreCase("create")) {
						YamlMob y2 = new YamlMob(args[2]+".yml");
						y2.createFile();
						y2.save();
						
						MainCore.instance.rankCraft.reloadYamlMobFiles();
						MainCore.instance.rankCraft.makeYamlMobLists();
						
					}*/ 
					
					if(args[1].equalsIgnoreCase("setArmors")) {
						YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
						
						if(y == null)
						{
							p.sendMessage(lang.getMobNotFound());
							return;
						}
						
						if(p.getInventory().getHelmet()!=null) {
							y.setHelmet(p.getInventory().getHelmet());
							y.getC().set("Inventory.helmet", y.getHelmet());
						}
						if(p.getInventory().getChestplate()!=null) {
							y.setChestplate(p.getInventory().getChestplate());
							y.getC().set("Inventory.chestplate", y.getChestplate());
						}
						if(p.getInventory().getLeggings()!=null) {
							y.setLeggings(p.getInventory().getLeggings());
							y.getC().set("Inventory.leggings", y.getLeggings());
						}
						if(p.getInventory().getBoots()!=null) {
							y.setBoots(p.getInventory().getBoots());
							y.getC().set("Inventory.boots", y.getBoots());
						}
						if(p.getInventory().getItemInMainHand()!=null) {
							y.setHand(p.getInventory().getItemInMainHand());
							y.getC().set("Inventory.hand", y.getHand());
						}
						
						y.save();
						p.sendMessage(lang.getArmorsSuc(y.getFileName()));
						
					}  else if(args[1].equalsIgnoreCase("setDrops")) {
						YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
						
						if(y == null)
						{
							p.sendMessage(lang.getMobNotFound());
							return;
						}
						
						List<ItemStack> drops = new ArrayList<>();
						for(int i = 0; i < p.getInventory().getContents().length; i++) {
							if(p.getInventory().getContents()[i] != null) {
								drops.add(p.getInventory().getContents()[i]);
							}
						}
						
						y.setDrops(drops);
						y.getC().set("Drops", y.getDrops());
						y.save();
						p.sendMessage(lang.getDropsSuc(y.getFileName()));
					} else if(args[1].equalsIgnoreCase("finish")) {
						YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
						
						if(y == null)
						{
							p.sendMessage(lang.getMobNotFound());
							return;
						}
						
						y.setFinished(true);
						y.getC().set("Finished", y.isFinished());
						y.save();
						
					//	p.sendMessage("Mob "+y.getFileName()+" finished!");
						p.sendMessage(lang.getFinishSuc(y.getFileName()));
					} else if(args[1].equalsIgnoreCase("ftest")) {
						YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
						
						if(y == null)
						{
							p.sendMessage(lang.getMobNotFound());
							return;
						}
						
						boolean b = y.getC().contains("Abilities");
						p.sendMessage(""+b);
					} else if(args[1].equalsIgnoreCase("edit")) {
						YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
						if(y == null)
						{
							p.sendMessage(lang.getMobNotFound());
							return;
						}
						MainCore.instance.rankCraft.mobCreator.openSelectType(p, y);
					}
					
					reloadYamlMobs();
					
				} else if(args.length == 4) {
				/*	if(args[1].equalsIgnoreCase("setType")) {
						YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
						String type = args[3];
						System.out.println("Raw type text: "+type);
						if(type.equalsIgnoreCase("zombie") || type.equalsIgnoreCase("skeleton")) {
							y.setType(type.toLowerCase());
							y.getC().set("Type", y.getType());
							y.save();
						} else {
							p.sendMessage("Mob's type must be 'zombie' or 'skeleton' !!!");
						}
						
					}*/ 
					if(args[1].equalsIgnoreCase("create")) { // NEW: /rc mob create <mob> <type>
						if(MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml") != null) {
							p.sendMessage("There is already a mob with the same id!");
							return;
						}
						YamlMob y2 = new YamlMob(args[2]+".yml");
						y2.createFile();				
						y2.save();
						
						String type = args[3];
						//System.out.println("Raw type text: "+type);
						if(type.equalsIgnoreCase("zombie") || type.equalsIgnoreCase("skeleton")) {
							y2.setType(type.toLowerCase());
							y2.getC().set("Type", y2.getType());
							y2.save();
						} else {
							p.sendMessage("Mob's type must be 'zombie' or 'skeleton' !!!");
						}
						
						y2.setFinished(false);
						y2.getC().set("Finished", y2.isFinished());
						y2.save();
		
						MainCore.instance.rankCraft.reloadYamlMobFiles();
						MainCore.instance.rankCraft.makeYamlMobLists();
						
						p.sendMessage(lang.getCreateSuc(y2.getFileName()));
						
					} else if(args[1].equalsIgnoreCase("setName")) {
						YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
						if(y == null)
						{
							p.sendMessage(lang.getMobNotFound());
							return;
						}
						String name = args[3].replace('&', '§');
						
						
						y.setName(name);
						y.getC().set("Name", y.getName());
						y.save();
						p.sendMessage(lang.getNameSuc(y.getFileName()));
					} else if(args[1].equalsIgnoreCase("setLevel")) {
						YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
						if(y == null)
						{
							p.sendMessage(lang.getMobNotFound());
							return;
						}
						String levelText = args[3];
						
						try {
							int level = Integer.parseInt(levelText);
							
							y.setLevel(level);
							y.getC().set("Level", y.getLevel());
							y.save();
							
							p.sendMessage(lang.getLevelSuc(y.getFileName()));
							
						} catch(NumberFormatException e) {
							p.sendMessage("Mob's level must be an integer number!!!");
						}
					} else if(args[1].equalsIgnoreCase("setCanSpawn")) {
						YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
						if(y == null)
						{
							p.sendMessage(lang.getMobNotFound());
							return;
						}
						String perm = args[3];
						
						if(perm.equalsIgnoreCase("false") || perm.equalsIgnoreCase("true")) {
							y.setDefaultSpawn(Boolean.parseBoolean(perm.toLowerCase()));
							y.getC().set("DefaultSpawn", y.isDefaultSpawn());
							y.save();
							p.sendMessage(lang.getCanSpawnSuc(y.getFileName()));
						} else {
							p.sendMessage("Use: /rc mob setCanSpawn <mob> <true/false>");
						}
					} else if(args[1].equalsIgnoreCase("rmvability")) {
						YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
						if(y == null)
						{
							p.sendMessage(lang.getMobNotFound());
							return;
						}
						String a = args[3].toLowerCase();
						
						if(y.hasAbility(a) && y.isValidAbility(a)) {
							y.getAbilities().remove(a+":"+y.getAbilityChances(a));
							//p.sendMessage("Scanning for: "+ a+":"+y.getAbilityChances(a));
							//for(int i = 0; i < y.getAbilities().size(); i++) p.sendMessage(y.getAbilities().get(i));
							/*
							for(String s : y.getAbilities()) {
								if(s.startsWith(a))
									y.getAbilities().remove(s);
							}
							*/
							y.getC().set("Abilities", y.getAbilities());
							y.save();
							
							//p.sendMessage("Removed ability "+a+" from "+y.getName());
							p.sendMessage(lang.getRemoveAbilitySuccess(a, y.getDisplayName()));
						}
					}else if(args[1].equalsIgnoreCase("addbiome")) {
						YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
						if(y == null)
						{
							p.sendMessage(lang.getMobNotFound());
							return;
						}
						String b = args[3].toUpperCase();
						if(!y.getStringBiomes().contains(b)) {
							y.addBiome(b);
							y.getC().set("Biomes", y.getStringBiomes());
							y.save();
							//p.sendMessage("Added biome "+b+"!");
							p.sendMessage(lang.getAddBiomeSuccess(b));
						}
					}else if(args[1].equalsIgnoreCase("rmvbiome")) {
						YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
						if(y == null)
						{
							p.sendMessage(lang.getMobNotFound());
							return;
						}
						String b = args[3].toUpperCase();
						if(y.getStringBiomes().contains(b)) {
							y.getStringBiomes().remove(b);
							y.getC().set("Biomes", y.getStringBiomes());
							y.save();
							//p.sendMessage("Remove biome "+ b+"!");
							p.sendMessage(lang.getRemoveBiomeSuccess(b));
						}
					} else if(args[1].equalsIgnoreCase("edit")) {
						YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
						if(y == null)
						{
							p.sendMessage(lang.getMobNotFound());
							return;
						}
						String stage = args[3];
						if(!MainCore.instance.rankCraft.mobCreator.openStage(stage, p, y)) {
							p.sendMessage("Invalid stage!");
						}
					} else if(args[1].equalsIgnoreCase("sethp")) {
						YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
						if(y == null)
						{
							p.sendMessage(lang.getMobNotFound());
							return;
						}
						double health = y.getHealth();
						try {
							health = Double.parseDouble(args[3]);
						} catch(NumberFormatException ex) {
							p.sendMessage("Invalid health");
							return;
						}
						y.setHealth(health);
						y.getC().set("Health", y.getHealth());
						y.save();
						//p.sendMessage("Set health to "+health+" successfully!");
						p.sendMessage(lang.getSetHPSuccess(health));
					}
					
					reloadYamlMobs();
				} else if(args.length == 5) {
					
					if(args[1].equalsIgnoreCase("addability")) {
						YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
						if(y == null)
						{
							p.sendMessage(lang.getMobNotFound());
							return;
						}
						String a = args[3].toLowerCase();
						int c = Integer.parseInt(args[4]);
						if(c < 0) c = 0;
						else if(c > 100) c = 100;
						
						if(!y.hasAbility(a) && y.isValidAbility(a)) {
							y.addAbility(a+":"+c);
							y.getC().set("Abilities", y.getAbilities());
							y.save();
							
							//p.sendMessage("Added ability "+a+" to "+y.getName());
							p.sendMessage(lang.getAddAbilitySuccess(a, y.getDisplayName()));
						}
						
						
					} 
					
					if(args[1].equalsIgnoreCase("create")) { // rc mob create <mob> <name> <level>
						if(MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml") != null) {
							p.sendMessage("There is already a mob with the same id!");
							return;
						}
						YamlMob y2 = new YamlMob(args[2]+".yml");
						y2.createFile();				
						y2.save();
						
						String name = args[3];//.replace('&', '§');
						int level = 0;
						try {
							level = Integer.parseInt(args[4]);
						} catch(Exception ex) {
							System.out.println("Level must be an integer!");
						}
						y2.setFinished(false);
						y2.getC().set("Finished", y2.isFinished());
						
		
						y2.setLevel(level);
						y2.getC().set("Level", y2.getLevel());
						y2.setName(name);
						y2.getC().set("Name", y2.getName());
						
						y2.save();
						
						MainCore.instance.rankCraft.reloadYamlMobFiles();
						MainCore.instance.rankCraft.makeYamlMobLists();
						
						p.sendMessage(lang.getCreateSuc(y2.getFileName()));
						
						MainCore.instance.rankCraft.mobCreator.startMobCreator(p, y2);
					}
					
					reloadYamlMobs();
				} else if(args.length == 6) { // rc mob create <mob> <name> <level> <hp>
					if(MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml") != null) {
						p.sendMessage("There is already a mob with the same id!");
						return;
					}
					YamlMob y2 = new YamlMob(args[2]+".yml");
					y2.createFile();				
					y2.save();
					
					String name = args[3];//.replace('&', '§');
					int level = 0;
					try {
						level = Integer.parseInt(args[4]);
					} catch(Exception ex) {
						System.out.println("Level must be an integer!");
					}
					y2.setFinished(false);
					y2.getC().set("Finished", y2.isFinished());
					
	
					y2.setLevel(level);
					y2.getC().set("Level", y2.getLevel());
					y2.setName(name);
					y2.getC().set("Name", y2.getName());
					
					y2.save();
					
					double health = 10.0;
					
					try {
						health = Double.parseDouble(args[5]);
					} catch(NumberFormatException ex) {
						p.sendMessage("Invalid health! Health set to "+health);
					}
					y2.setHealth(health);
					y2.getC().set("Health", y2.getHealth());
					y2.save();
					
					MainCore.instance.rankCraft.reloadYamlMobFiles();
					MainCore.instance.rankCraft.makeYamlMobLists();
					
					p.sendMessage(lang.getCreateSuc(y2.getFileName()));
					p.sendMessage(lang.getSetHPSuccess(health));
					
					MainCore.instance.rankCraft.mobCreator.startMobCreator(p, y2);
				}
			} else {
				pls.sendNotPermissionMessage(p);
			}
		} else {
			p.sendMessage(MainCore.instance.language.getDisabledCustomMobs());
		}
	}
	
	private void reloadYamlMobs() {
		MainCore.instance.rankCraft.reloadYamlMobFiles();
		MainCore.instance.rankCraft.makeYamlMobLists();
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		if(args.length == 2) {
			String[] c = {"setArmors", "setDrops", "finish", "edit", "create", "setName", "setLevel", "setCanSpawn", 
					"rmvability", "addbiome", "rmvbiome", "sethp", "addability"};
			return Arrays.asList(c);
		} else if(args.length == 3) {
			return getMobIds();
		} else if(args.length == 4) {
			
			if(args[1].equalsIgnoreCase("create")) {
				return Arrays.asList(new String[] {"zombie", "skeleton", "<name>"});
			} else if(args[1].equalsIgnoreCase("setname")) {
				return Arrays.asList("<name>");
			} else if(args[1].equalsIgnoreCase("setlevel")) {
				return Arrays.asList("<level>");
			} else if(args[1].equalsIgnoreCase("setcanspawn")) {
				return Arrays.asList(new String[] {"true", "false"});
			} else if(args[1].equalsIgnoreCase("rmvability")) {
				YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
				if(y == null) return null;
				return y.getAbilitiesNames();
			} else if(args[1].equalsIgnoreCase("addbiome")) {
				List<String> names = new ArrayList<>();
				for(Biome b : Biome.values())
					names.add(b.name());
				return names;
			} else if(args[1].equalsIgnoreCase("rmvbiome")) {
				YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[2]+".yml");
				if(y == null) return null;
				return y.getStringBiomes();
			} else if(args[1].equalsIgnoreCase("edit")) {
				return Arrays.asList(MainCore.instance.rankCraft.mobCreator.stages);
			} else if(args[1].equalsIgnoreCase("sethp")) {
				return Arrays.asList("<hp>");
			} else if(args[1].equalsIgnoreCase("addability")) {
				return YamlMob.VALID_ABILITIES;
			} 
			
		} else if(args.length == 5) {
			if(args[1].equalsIgnoreCase("addability")) {
				return Arrays.asList("<chance>");
			} else if(args[1].equalsIgnoreCase("create")) {
				return Arrays.asList("<level>");
			}
		} else if(args.length == 6) {
			if(args[1].equalsIgnoreCase("create")) {
				return Arrays.asList("<hp>");
			}
		}
		return null;
	}
	
	private List<String> getMobIds() {
		List<String> ids = new ArrayList<>();
		for(YamlMob mob : MainCore.instance.rankCraft.mobs) {
			ids.add(RCUtils.getStringWithoutExtension(mob.getFileName()));
		}
		return ids;
	}
	
}
