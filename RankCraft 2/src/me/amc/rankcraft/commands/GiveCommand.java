package me.amc.rankcraft.commands;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.PermissionList;
import me.amc.rankcraft.classes.RpgClassData;
import me.amc.rankcraft.mobs.MobSpawner;
import me.amc.rankcraft.mobs.YamlMob;
import me.amc.rankcraft.packs.BlockPack;
import me.amc.rankcraft.packs.ItemPack;
import me.amc.rankcraft.packs.MixedPack;
import me.amc.rankcraft.packs.Pack.PackLevel;
import me.amc.rankcraft.rpgitem.CrystalSize;
import me.amc.rankcraft.rpgitem.ManaCrystal;
import me.amc.rankcraft.rpgitem.Rarity;
import me.amc.rankcraft.rpgitem.RpgMaterial;
import me.amc.rankcraft.rpgitem.XpCrystal;
import me.amc.rankcraft.rpgitem.YamlArmor;
import me.amc.rankcraft.rpgitem.YamlItem;
import me.amc.rankcraft.spells.Spell;
import me.amc.rankcraft.treasures2.TreasureChest2;
import me.amc.rankcraft.utils.RCUtils;
import me.clip.placeholderapi.PlaceholderAPI;

public class GiveCommand extends SubCommand {

	public GiveCommand() {
		super("give");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean testOpenSign(Player p){
        try {
            Class packetClass = RCUtils.getNMSClass("PacketPlayOutOpenSignEditor");
            Class blockPositionClass = RCUtils.getNMSClass("BlockPosition");
            Constructor blockPosCon = blockPositionClass.getConstructor(new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE });
            Object blockPosition = blockPosCon.newInstance(new Object[] { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0) });
            Constructor packetCon = packetClass.getConstructor(new Class[] { blockPositionClass });
            Object packet = packetCon.newInstance(new Object[] { blockPosition });
            RCUtils.sendPacket(p, packet);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

	@SuppressWarnings("deprecation")
	@Override
	public void execute(Player p, String[] args) {
		PermissionList pls = MainCore.instance.permList;
		// 0 1 2 3 = 4
		// rc give treasure <type> <amount>
		if (args.length == 4) {
			
			if (args[1].equalsIgnoreCase("gold")) { // rc give gold <amount> <player-name>
				if (p.hasPermission(pls.giveGold_permission)) {
					float amt = 0;
					try {
						amt = Float.parseFloat(args[2]);
					} catch (Exception e) {
						p.sendMessage(ChatColor.RED + "Use: /rc give gold <amount> <player-name>");
					}
					Player target;
					String parameter = args[3];
					if(MainCore.instance.usesPlaceholderAPI) {
						parameter = PlaceholderAPI.setPlaceholders(p, parameter);
						//System.out.println(parameter);
					}
					for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
						if(parameter.equals(onlinePlayer.getName())) {
							target = onlinePlayer;
							target.getInventory().addItem(MainCore.instance.rankCraft.gold.toItem(amt));
							break;
						}
					}
					//p.getInventory().addItem(MainCore.instance.rankCraft.gold.toItem(amt));
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			
			if (args[1].equalsIgnoreCase("treasure")) {
				if (p.hasPermission(pls.giveTreasure_permission)) {
					/*
					int amt = 0;
					try {
						amt = Integer.parseInt(args[3]);
					} catch (Exception e) {
						p.sendMessage(ChatColor.RED + "Use: /rc give treasure <normal/super/ultra> <amount>");
					}
					if (args[2].equalsIgnoreCase("normal")) {
						for (int i = 0; i < amt; i++) {
							TreasureChest c = new TreasureChest(TreasureChest.ChestType.Normal);
							p.getInventory().addItem(c.getChest().getItem());
						}
					} else if (args[2].equalsIgnoreCase("super")) {
						for (int i = 0; i < amt; i++) {
							TreasureChest c = new TreasureChest(TreasureChest.ChestType.Super);
							p.getInventory().addItem(c.getChest().getItem());
						}
					} else if (args[2].equalsIgnoreCase("ultra")) {
						for (int i = 0; i < amt; i++) {
							TreasureChest c = new TreasureChest(TreasureChest.ChestType.Ultra);
							p.getInventory().addItem(c.getChest().getItem());
						}
					} else {
						p.sendMessage(ChatColor.RED + "Use: /rc give treasure <normal/super/ultra> <number>");
					}
					*/
					p.sendMessage(MainCore.instance.language.getPrefix()+"§eOld good days!");
					p.sendMessage(MainCore.instance.language.getPrefix()+"§ePlease use '/rankcraft give treasure2 <id> <amount>' instead");
					
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			// rc give xpcrystal <type> <amount> = 4
			if (args[1].equalsIgnoreCase("xpcrystal")) {
				if (p.hasPermission(pls.giveXpCrystal_permission)) {
					int amt = 0;
					try {
						amt = Integer.parseInt(args[3]);
					} catch (Exception e) {
						p.sendMessage(ChatColor.RED + "Use: /rc give xpcrystal <small/medium/large> <amount>");
					}

					CrystalSize size;
					if (args[2].equalsIgnoreCase("small")) {
						size = CrystalSize.Small;
						//XpCrystal c = new XpCrystal(size, ChatColor.BLUE + "Small XpCrystal");
						XpCrystal c = new XpCrystal(size, RCUtils.XPCRYSTAL_SMALL_NAME);
						for (int i = 0; i < amt; i++) {
							p.getInventory().addItem(c.getItem().getItem());
						}
					} else if (args[2].equalsIgnoreCase("medium")) {
						size = CrystalSize.Medium;
						//XpCrystal c = new XpCrystal(size, ChatColor.BLUE + "Medium XpCrystal");
						XpCrystal c = new XpCrystal(size, RCUtils.XPCRYSTAL_MEDIUM_NAME);
						for (int i = 0; i < amt; i++) {
							p.getInventory().addItem(c.getItem().getItem());
						}
					} else if (args[2].equalsIgnoreCase("large")) {
						size = CrystalSize.Large;
						//XpCrystal c = new XpCrystal(size, ChatColor.BLUE + "Large XpCrystal");
						XpCrystal c = new XpCrystal(size, RCUtils.XPCRYSTAL_LARGE_NAME);
						for (int i = 0; i < amt; i++) {
							p.getInventory().addItem(c.getItem().getItem());
						}
					} else {
						p.sendMessage(ChatColor.RED + "Use: /rc give xpcrystal <small/medium/large> <amount>");
						return;
					}
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			// rc give manacrystal <type> <amount>
			if (args[1].equalsIgnoreCase("manacrystal")) {
				if (p.hasPermission(pls.giveManaCrystal_permission)) {
					int amt = 0;
					try {
						amt = Integer.parseInt(args[3]);
					} catch (Exception e) {
						p.sendMessage(ChatColor.RED + "Use: /rc give manacrystal <small/medium/large> <amount>");
					}

					CrystalSize size;
					if (args[2].equalsIgnoreCase("small")) {
						size = CrystalSize.Small;
						//ManaCrystal c = new ManaCrystal(size, ChatColor.BLUE + "Small ManaCrystal");
						ManaCrystal c = new ManaCrystal(size, RCUtils.MANACRYSTAL_SMALL_NAME);
						for (int i = 0; i < amt; i++) {
							p.getInventory().addItem(c.getItem().getItem());
						}
					} else if (args[2].equalsIgnoreCase("medium")) {
						size = CrystalSize.Medium;
						//ManaCrystal c = new ManaCrystal(size, ChatColor.BLUE + "Medium ManaCrystal");
						ManaCrystal c = new ManaCrystal(size, RCUtils.MANACRYSTAL_MEDIUM_NAME);
						for (int i = 0; i < amt; i++) {
							p.getInventory().addItem(c.getItem().getItem());
						}
					} else if (args[2].equalsIgnoreCase("large")) {
						size = CrystalSize.Large;
						//ManaCrystal c = new ManaCrystal(size, ChatColor.BLUE + "Large ManaCrystal");
						ManaCrystal c = new ManaCrystal(size, RCUtils.MANACRYSTAL_LARGE_NAME);
						for (int i = 0; i < amt; i++) {
							p.getInventory().addItem(c.getItem().getItem());
						}
					} else {
						p.sendMessage(ChatColor.RED + "Use: /rc give manacrystal <small/medium/large> <amount>");
						return;
					}
				} else {
					pls.sendNotPermissionMessage(p);
				}

			}
			// rc give blockpack <level> <amount>
			if (args[1].equalsIgnoreCase("blockpack")) {
				if (p.hasPermission(pls.giveBlockpack_permission)) {
					PackLevel level;
					int amt = 1;
					try {
						amt = Integer.parseInt(args[3]);
					} catch (Exception e) {
						p.sendMessage(ChatColor.RED + " Use: /rc give blockpack <bronze/silver/gold> <amount>");
					}
					if (args[2].equalsIgnoreCase("bronze")) {
						level = PackLevel.Bronze;
						for (int i = 0; i < amt; i++) {
							BlockPack pack = new BlockPack(level);
							p.getInventory().addItem(pack.getItem().getItem());
						}
					} else if (args[2].equalsIgnoreCase("silver")) {
						level = PackLevel.Silver;
						for (int i = 0; i < amt; i++) {
							BlockPack pack = new BlockPack(level);
							p.getInventory().addItem(pack.getItem().getItem());
						}
					} else if (args[2].equalsIgnoreCase("gold")) {
						level = PackLevel.Gold;
						for (int i = 0; i < amt; i++) {
							BlockPack pack = new BlockPack(level);
							p.getInventory().addItem(pack.getItem().getItem());
						}
					} else {
						p.sendMessage(ChatColor.RED + "Use: /rc give blockpack <bronze/silver/gold> <amount>");
					}
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			// rc give itempack <level> <amount>
			if (args[1].equalsIgnoreCase("itempack")) {
				if (p.hasPermission(pls.giveItempack_permission)) {
					PackLevel level = null;
					int amt = 0;
					try {
						amt = Integer.parseInt(args[3]);
					} catch (Exception e) {
						p.sendMessage(ChatColor.RED + "Use: /rc give itempack <bronze/silver/gold> <amount>");
					}
					if (args[2].equalsIgnoreCase("bronze")) {
						level = PackLevel.Bronze;
					} else if (args[2].equalsIgnoreCase("silver")) {
						level = PackLevel.Silver;
					} else if (args[2].equalsIgnoreCase("gold")) {
						level = PackLevel.Gold;
					} else {
						p.sendMessage(ChatColor.RED + "Use: /rc give itempack <bronze/silver/gold> <amount>");
						return;
					}
					for (int i = 0; i < amt; i++) {
						ItemPack pack = new ItemPack(level);
						p.getInventory().addItem(pack.getItem().getItem());
					}
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			// rc give mixedpack <level> <amount>
			if (args[1].equalsIgnoreCase("mixedpack")) {
				if (p.hasPermission(pls.giveMixedpack_permission)) {
					PackLevel level = null;
					int amt = 0;
					try {
						amt = Integer.parseInt(args[3]);
					} catch (Exception e) {
						p.sendMessage(ChatColor.RED + "Use: /rc give mixedpack <bronze/silver/gold> <amount>");
					}
					if (args[2].equalsIgnoreCase("bronze")) {
						level = PackLevel.Bronze;
					} else if (args[2].equalsIgnoreCase("silver")) {
						level = PackLevel.Silver;
					} else if (args[2].equalsIgnoreCase("gold")) {
						level = PackLevel.Gold;
					} else {
						p.sendMessage(ChatColor.RED + "Use: /rc give mixedpack <bronze/silver/gold> <amount>");
						return;
					}
					for (int i = 0; i < amt; i++) {
						MixedPack pack = new MixedPack(level);
						p.getInventory().addItem(pack.getItem().getItem());
					}
				} else {
					pls.sendNotPermissionMessage(p);
				}
			} 
			
			// rankcraft give treasure2 <id> <amount>
			if(args[1].equalsIgnoreCase("treasure2")) {
				if(p.hasPermission(pls.giveTreasure2_permission)) {
					String id = args[2];
					int amount = Integer.parseInt(args[3]);
					File f = new File(RCUtils.TREASURES2_DIRECTORY+"/"+id+".yml");
					
					if(!f.exists()) {
						//p.sendMessage("This treasure does not exist!");
						p.sendMessage(MainCore.instance.language.getTreasures2NotExist());
						return;
					}
					
					TreasureChest2 tc2 = MainCore.instance.rankCraft.getTreasureById(id);
					tc2.giveToPlayer(p, amount);
					//System.out.println("Command to give treasure!");
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}	
			
			// rc give classpoints <amount> <player>
			if(args[1].equalsIgnoreCase("classpoints")) {
				if(p.hasPermission(pls.giveClasspoints_permission)) {
					int points = 0;
					try {
						points = Integer.parseInt(args[2]);
					} catch(NumberFormatException ex) {
						p.sendMessage(MainCore.instance.language.getPrefix()+"<points> must be an integer!");
						return;
					}
					if(points<0)
						points = 0;
					
					
					Player target;
					String parameter = args[3];
					if(MainCore.instance.usesPlaceholderAPI) {
						parameter = PlaceholderAPI.setPlaceholders(p, parameter);
					}
					for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
						if(parameter.equals(onlinePlayer.getName())) {
							target = onlinePlayer;
							RpgClassData.setClassPoints(target, RpgClassData.getClassPoints(target) + points);
							RpgClassData.save();
							if(RpgClassData.getClassPoints(target) > 0) 
								target.sendMessage(MainCore.instance.language.getSendClassPoints(RpgClassData.getClassPoints(target)));
							break;
						}
					}
					
					
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			
		} else if (args.length == 3) {
			// rc give gold <amount> = 3
			if (args[1].equalsIgnoreCase("gold")) {
				if (p.hasPermission(pls.giveGold_permission)) {
					float amt = 0;
					try {
						amt = Float.parseFloat(args[2]);
					} catch (Exception e) {
						p.sendMessage(ChatColor.RED + "Use: /rc give gold <amount>");
					}

					p.getInventory().addItem(MainCore.instance.rankCraft.gold.toItem(amt));
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			// rc give skeletonwizard egg
			if (args[1].equalsIgnoreCase("skeletonwizard")) {
				if (p.hasPermission(pls.giveSkeletonWizardEgg_permission)) {
					p.getInventory().addItem(MainCore.instance.rankCraft.skeletonWizard.getEgg().getItem());
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			// rc give zombiewarrior egg
			if (args[1].equalsIgnoreCase("zombiewarrior")) {
				if (p.hasPermission(pls.giveZombieWarriorEgg_permission)) {
					p.getInventory().addItem(MainCore.instance.rankCraft.zombieWarrior.getEgg().getItem());
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			// rc give zombiemaster egg
			if (args[1].equalsIgnoreCase("zombiemaster")) {
				if (p.hasPermission(pls.giveZombieMasterEgg_permission)) {
					p.getInventory().addItem(MainCore.instance.rankCraft.zombieMaster.getEgg().getItem());
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			// rc give yamlweapon <file-name>
			if(args[1].equalsIgnoreCase("yamlweapon")) {
				if(p.hasPermission(pls.giveSpecificWeapon_permission)) {
					String itemName = args[2];
					YamlItem item = RCUtils.getYamlItemByFileName(itemName);
					if(item == null) {
						//p.sendMessage("Invalid file name!");
						p.sendMessage(MainCore.instance.language.getSpecificWeaponError());
						return;
					}
					p.getInventory().addItem(item.getBuiltRpgItem().getItem());
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			// rc give yamlarmor <file-name>
			if(args[1].equalsIgnoreCase("yamlarmor")) {
				if(p.hasPermission(pls.giveSpecificYamlArmor_permission)) {
					String fileName = args[2];
					YamlArmor armor = MainCore.instance.rankCraft.getYamlArmorFromFileName(fileName+".armor");
					if(armor == null) {
						p.sendMessage(MainCore.instance.language.getPrefix()+"Invalid file name!");
						return;
					}
					p.getInventory().addItem(armor.getRpgArmor().build().getItem());
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			
			// rc give classpoints <points>
			if(args[1].equalsIgnoreCase("classpoints")) {
				if(p.hasPermission(pls.giveClasspoints_permission)) {
					int points = 0;
					try {
						points = Integer.parseInt(args[2]);
					} catch(NumberFormatException ex) {
						p.sendMessage(MainCore.instance.language.getPrefix()+"<points> must be an integer!");
						return;
					}
					if(points<0)
						points = 0;
					RpgClassData.setClassPoints(p, RpgClassData.getClassPoints(p) + points);
					RpgClassData.save();
					if(RpgClassData.getClassPoints(p) > 0) 
						p.sendMessage(MainCore.instance.language.getSendClassPoints(RpgClassData.getClassPoints(p)));
					
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}

		} else if (args.length == 2) {
			// rc give yamlweapons
			if (args[1].equalsIgnoreCase("yamlweapons")) {
				if (p.hasPermission(pls.giveYamlWeapons_permission)) {
					for (YamlItem item : MainCore.instance.rankCraft.yamlWeapons) {
						p.getInventory().addItem(item.getBuiltRpgItem().getItem());
					}
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			// rc give yamlarmors
			if(args[1].equalsIgnoreCase("yamlarmors")) {
				if(p.hasPermission(pls.giveYamlArmors_permission)) {
					for(YamlArmor a : MainCore.instance.rankCraft.yamlArmors) {
						p.getInventory().addItem(a.getRpgArmor().build().getItem());
					}
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			// rc give spells
			if (args[1].equalsIgnoreCase("spells")) {
				if (p.hasPermission(pls.giveSpells_permission)) {
					for (Spell s : MainCore.instance.rankCraft.spellUtils.spells) {
						p.getInventory().addItem(s.getItem());
					}
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			// rc give test
			if(args[1].equalsIgnoreCase("test")) {
				if(p.getName().equals("Arionas_MC")) {
					/*
					for (Spell s : MainCore.instance.rankCraft.spellUtils.spells) {
						//if(s instanceof EarthTameSpell) p.getInventory().addItem(s.getItem());
						p.sendMessage(s.getName()+ChatColor.WHITE+" :: "+s.getId());
					}*/
					/*
					SpellCase s = new SpellCase(CaseType.Small);
					s.addData("00102");
					s.addData("02045");
					s.addData("01205");
					s.addData("03207");
					s.addData("02813");
					SpellCase m = new SpellCase(CaseType.Medium);
					SpellCase l = new SpellCase(CaseType.Large);
					p.getInventory().addItem(s.getItem(), m.getItem(), l.getItem());
					*/
					/*
					Bat b = (Bat) p.getWorld().spawnEntity(p.getLocation(), EntityType.BAT);
					Zombie z = (Zombie) p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
					b.setPassenger(z);
					b.damage(0, p);
					*/
					//MainCore.instance.rankCraft.stats.addXp(p, - MainCore.instance.rankCraft.stats.getXp(p));
					/*
					Stats s = MainCore.instance.rankCraft.stats;
					p.sendMessage("Xp: "+s.getXp(p));
					p.sendMessage("Xp to level up: "+s.getXPToLevelUP(p));
					p.sendMessage("Level: "+s.getLevel(p));
					*/
					//b.set
					/*
					RpgArmor helmet = new RpgArmor(Material.GOLDEN_HELMET, 50, Rarity.LEGENDARY, 100);
					p.getInventory().addItem(helmet.build().getItem());
					RpgArmor chestplate = new RpgArmor(Material.GOLDEN_CHESTPLATE, 50, Rarity.LEGENDARY, 100);
					p.getInventory().addItem(chestplate.build().getItem());
					RpgArmor legs = new RpgArmor(Material.GOLDEN_LEGGINGS, 50, Rarity.LEGENDARY, 100);
					p.getInventory().addItem(legs.build().getItem());
					RpgArmor boots = new RpgArmor(Material.GOLDEN_BOOTS, 50, Rarity.LEGENDARY, 100);
					p.getInventory().addItem(boots.build().getItem());
					*/
					/*
					YamlArmor ya = new YamlArmor("test.armor");
					ya.setMaterial(Material.CHAINMAIL_CHESTPLATE);
					ya.setDefense(3.2);
					ya.setMinLevel(30);
					ya.setName("&4Test_&cArmor");
					ya.setRarity(Rarity.RARE);
					ya.setHasRecipe(true);
					ya.setRecipe(Arrays.asList("GRASS,AIR,GRASS", "GRASS,STONE,GRASS","STONE,STONE,STONE"));
					ya.save();
					MainCore.instance.rankCraft.reloadYamlArmors();
					*/
					//for(int i = 0; i < 50; i++) p.sendMessage(" ");//clear chat
					//p.sendMessage(MainCore.instance.rankCraft.stats.getXpBar(p, 20));
					//p.sendMessage(""+MainCore.instance.rankCraft.stats.getXPToLevelUP(p));
					/*
					MainCore.instance.rankCraft.hpSystem.setHP(p, 10);
					MainCore.instance.rankCraft.hpSystem.save();
					*/
					//p.getInventory().addItem(MainCore.instance.rankCraft.dynamicExcalibur.getItem());
					
					//testOpenSign(p);// it does now work
					//p.getInventory().addItem(MainCore.instance.rankCraft.specialItems.zeusSword.getItem());
					//Excalibur sword = new Excalibur();
					//p.getInventory().addItem(sword.getItem());
					
					RpgMaterial dark_bone = new RpgMaterial(Material.BONE, Rarity.COMMON, 10001);
					dark_bone.setName(ChatColor.WHITE+"Dark Bone");
					p.getInventory().addItem(dark_bone.build().getItem());
					
				}
			}
		} else if(args.length == 5) {
			// rc give spell <spell-name> <spell-level> <amount>
			if(args[1].equalsIgnoreCase("spell")) {
				if(p.hasPermission(pls.giveSpecificSpell_permission)) {
					String spellName = "";
					int spellLevel = 1;
					int amount = 1;
					try {
						spellName = args[2];
						spellLevel = Integer.parseInt(args[3]);
						amount = Integer.parseInt(args[4]);
					} catch(Exception ex) {
						p.sendMessage(ChatColor.RED+"Use: /rc give spell <spell-name> <spell-level> <amount>");
						return;
					}
											
					//Spell s = MainCore.instance.rankCraft.spellUtils.getSpellByNameWithoutLevel(spellName); old method
					
					// New method of searching for spell with nameId
					// Getting a fake spell to check for max level and then give the requested one
					Spell s = MainCore.instance.rankCraft.spellUtils.getSpellByNameId(spellName+"_1"); 
											
					if(s == null) {
						//p.sendMessage("Invalid spell name!");
						p.sendMessage(MainCore.instance.language.getSpecificSpellError());
						return;
					}
											
					if(spellLevel > s.getMaxPower()) spellLevel = s.getMaxPower();
					if(spellLevel < 1) spellLevel = 1;
											
					//String finalName = spellName+" Lvl."+spellLevel; old method
					
					// New format of nameId
					String nameId = spellName+"_"+spellLevel;
											
					for(int i = 0; i < amount; i++) {
						//p.getInventory().addItem(MainCore.instance.rankCraft.spellUtils.getSpellByName(finalName).getItem());
						p.getInventory().addItem(MainCore.instance.rankCraft.spellUtils.getSpellByNameId(nameId).getItem());
					}
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			
			if(args[1].equalsIgnoreCase("mobspawner")) {
				if(MainCore.instance.config.enableCustomMobs) {
					if(p.hasPermission(pls.giveMobSpawner_permission)) {
						String mob = args[2];
						String secText = args[3];
						String rangeText = args[4];
						
						try {
							int sec = Integer.parseInt(secText);
							int range = Integer.parseInt(rangeText);
							
							MobSpawner spawner = new MobSpawner(mob, sec, range);
							p.getInventory().addItem(spawner.getSpawnerItem().getItem());
							
						} catch(NumberFormatException e) {
							p.sendMessage("Use: /rc give mobspawner <mob> <sec> <range>");
						}
					} else {
						pls.sendNotPermissionMessage(p);
					}
				} else {
					p.sendMessage(MainCore.instance.language.getDisabledCustomMobs());
				}
			}
		} else if(args.length == 6) {
			if(args[1].equalsIgnoreCase("spell")) { // rc give spell <spell-name> <level> <amount> <player>
				if(p.hasPermission(pls.giveSpecificSpell_permission)) {
					String spellName = "";
					int spellLevel = 1;
					int amount = 1;
					try {
						spellName = args[2];
						spellLevel = Integer.parseInt(args[3]);
						amount = Integer.parseInt(args[4]);
					} catch(Exception ex) {
						p.sendMessage(ChatColor.RED+"Use: /rc give spell <spell-name> <spell-level> <amount>");
						return;
					}
											
					//Spell s = MainCore.instance.rankCraft.spellUtils.getSpellByNameWithoutLevel(spellName);
						
					// New method with nameId (check above)
					Spell s = MainCore.instance.rankCraft.spellUtils.getSpellByNameId(spellName+"_1"); // fake one to get max power
					
					if(s == null) {
						//p.sendMessage("Invalid spell name!");
						p.sendMessage(MainCore.instance.language.getSpecificSpellError());
						return;
					}
											
					if(spellLevel > s.getMaxPower()) spellLevel = s.getMaxPower();
					if(spellLevel < 1) spellLevel = 1;
											
					//String finalName = spellName+" Lvl."+spellLevel;
					String nameId = spellName+"_"+spellLevel;	
					
					for(int i = 0; i < amount; i++) {
						//p.getInventory().addItem(MainCore.instance.rankCraft.spellUtils.getSpellByName(finalName).getItem());
						Player target;
						String parameter = args[5];
						if(MainCore.instance.usesPlaceholderAPI) {
							parameter = PlaceholderAPI.setPlaceholders(p, parameter);
							//System.out.println(parameter);
						}
						for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
							if(parameter.equals(onlinePlayer.getName())) {
								target = onlinePlayer;
								//target.getInventory().addItem(MainCore.instance.rankCraft.spellUtils.getSpellByName(finalName).getItem());
								target.getInventory().addItem(MainCore.instance.rankCraft.spellUtils.getSpellByNameId(nameId).getItem());
								break;
							}
						}
					}
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
		}

	}
	
	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		if(args.length == 2) {
			String[] options = {"xpcrystal", "manacrystal", "blockpack", "itempack", "mixedpack", "treasure2", "gold",
					"skeletonwizard", "zombiewarrior", "zombiemaster", "yamlweapon", "yamlarmor", "yamlweapons", "yamlarmors",
					"spells", "spell", "mobspawner", "classpoints"};
			return Arrays.asList(options);
		} else if(args.length == 3) {
			if(args[1].equalsIgnoreCase("yamlweapon")) {
				List<String> ids = new ArrayList<>();
				for(YamlItem w : MainCore.instance.rankCraft.yamlWeapons)
					ids.add(w.getFileName());
				return ids;
			} else if(args[1].equalsIgnoreCase("yamlarmor")) {
				List<String> ids = new ArrayList<>();
				for(YamlArmor a : MainCore.instance.rankCraft.yamlArmors)
					ids.add(RCUtils.getStringWithoutExtension(a.getFileName()));
				return ids;
			} else if(args[1].equalsIgnoreCase("spell")) {
				return MainCore.instance.rankCraft.spellUtils.getSpellIdsWithoutLevels();
			} else if(args[1].equalsIgnoreCase("mobspawner")) {
				List<String> ids = new ArrayList<>();
				for(YamlMob mob : MainCore.instance.rankCraft.mobs) 
					ids.add(RCUtils.getStringWithoutExtension(mob.getFileName()));
				return ids;
			} else if(args[1].equalsIgnoreCase("xpcrystal") || args[1].equalsIgnoreCase("manacrystal")) {
				return Arrays.asList("small", "medium", "large");
			} else if(args[1].equalsIgnoreCase("blockpack") || args[1].equalsIgnoreCase("itempack") || args[1].equalsIgnoreCase("mixedpack")) {
				return Arrays.asList("bronze", "silver", "gold");
			} else if(args[1].equalsIgnoreCase("treasure2")) {
				List<String> ids = new ArrayList<>();
				for(TreasureChest2 tc2 : MainCore.instance.rankCraft.treasureChests) 
					ids.add(tc2.getId());
				return ids;
			} else if(args[1].equalsIgnoreCase("gold")) {
				return Arrays.asList("<amount>");
			} else if(args[1].equalsIgnoreCase("skeletonwizard") || args[1].equalsIgnoreCase("zombiewarrior") || args[1].equalsIgnoreCase("zombiemaster")) {
				return Arrays.asList("egg");
			} else if(args[1].equalsIgnoreCase("classpoints")) {
				return Arrays.asList("<points>");
			}
		} else if(args.length == 4) {
			if(args[1].equalsIgnoreCase("xpcrystal") || args[1].equalsIgnoreCase("manacrystal") || args[1].equalsIgnoreCase("blockpack") 
					|| args[1].equalsIgnoreCase("itempack")
					|| args[1].equalsIgnoreCase("mixedpack") || args[1].equalsIgnoreCase("treasure2")) {
				return Arrays.asList("<amount>");
			} else if(args[1].equalsIgnoreCase("spell")) {
				return Arrays.asList("<spell-level>");
			} else if(args[1].equalsIgnoreCase("mobspawner")) {
				return Arrays.asList("<sec>");
			}
		} else if(args.length == 5) {
			if(args[1].equalsIgnoreCase("spell")) {
				return Arrays.asList("<amount>");
			} else if(args[1].equalsIgnoreCase("mobspawner")) {
				return Arrays.asList("<range>");
			}
		} /*else if(args.length == 6) {
			if(args[1].equalsIgnoreCase("spell")) {
				return Arrays.asList("<player-name>");
			}
		}*/
		return null;
	}

}
