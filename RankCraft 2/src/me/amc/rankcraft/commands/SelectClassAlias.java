package me.amc.rankcraft.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.PermissionList;
import me.amc.rankcraft.classes.RpgClassData;
import me.amc.rankcraft.customevents.UnlockClassEvent;
import me.amc.rankcraft.utils.RCUtils;
import me.clip.placeholderapi.PlaceholderAPI;

public class SelectClassAlias extends SubCommand {

	public SelectClassAlias() {
		super("selectclass");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(Player p, String[] args) {
		PermissionList pls = MainCore.instance.permList;
		
		
		if(args.length == 1) {
			
			if(args[0].equalsIgnoreCase("selectclass")) {
				if(p.hasPermission(pls.classes_permission)) {
					MainCore.instance.rankCraft.rpgClassSelectGUI.open(p);
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
			
			
		} else if(args.length == 2) {
						
			if(args[0].equalsIgnoreCase("selectclass")) { //rc selectclass <player>
				if(args[1].equalsIgnoreCase("gladiator") || args[1].equalsIgnoreCase("archer") || args[1].equalsIgnoreCase("wizard") || args[1].equalsIgnoreCase("ninja") || args[1].equalsIgnoreCase("mysterious")) {
					if(p.hasPermission(pls.classes_permission)) {
						
						if(args[1].equalsIgnoreCase("gladiator")) {
							if(!RpgClassData.getUnlockGladiator(p)) {
								
								if(RpgClassData.getClassPoints(p) > 0) {
								
									RpgClassData.setClassPoints(p, RpgClassData.getClassPoints(p) - 1);
									RpgClassData.setUnlockGladiator(p, true);
									RpgClassData.setCurrentClass(p, MainCore.instance.rankCraft.gladiatorClass.getId());
									RpgClassData.save();
									p.sendMessage(MainCore.instance.language.getUnlockClass(MainCore.instance.rankCraft.gladiatorClass.getName()));
									UnlockClassEvent event = new UnlockClassEvent(p, MainCore.instance.rankCraft.gladiatorClass);
									Bukkit.getServer().getPluginManager().callEvent(event);
								
									//p.closeInventory();
									//MainCore.instance.rankCraft.rpgClassGUI.open(p);
									
								} else {
									p.sendMessage(MainCore.instance.language.getNotEnoughClassPoints());
								}
								
							} else {
								if(RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.gladiatorClass.getId())) {
									p.sendMessage(MainCore.instance.language.getAlreadyEquippedClass());
								} else {
									p.sendMessage(MainCore.instance.language.getEquipClass());
									RpgClassData.setCurrentClass(p, MainCore.instance.rankCraft.gladiatorClass.getId());
									RpgClassData.save();
									//p.closeInventory();
									//MainCore.instance.rankCraft.rpgClassGUI.open(p);
								}
							}
						} else if(args[1].equalsIgnoreCase("archer")) {
							if(!RpgClassData.getUnlockArcher(p)) {
								
								if(RpgClassData.getClassPoints(p) > 0) {
								
									RpgClassData.setClassPoints(p, RpgClassData.getClassPoints(p) - 1);
									RpgClassData.setUnlockArcher(p, true);
									RpgClassData.setCurrentClass(p, MainCore.instance.rankCraft.archerClass.getId());
									RpgClassData.save();
									
							//		p.sendMessage("You have successfully unlocked the Archer class!");
									p.sendMessage(MainCore.instance.language.getUnlockClass(MainCore.instance.rankCraft.archerClass.getName()));
									UnlockClassEvent event = new UnlockClassEvent(p, MainCore.instance.rankCraft.archerClass);
									Bukkit.getServer().getPluginManager().callEvent(event);
									
									//p.closeInventory();
									//MainCore.instance.rankCraft.rpgClassGUI.open(p);
									
								} else {
							//		p.sendMessage("You do not have enought ClassPoints to unlock this class!");
									p.sendMessage(MainCore.instance.language.getNotEnoughClassPoints());
								}
								
							} else {
								if(RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.archerClass.getId())) {
							//		p.sendMessage("You have already equipped this class!");
									p.sendMessage(MainCore.instance.language.getAlreadyEquippedClass());
								} else {
							//		p.sendMessage("You have successfully equipped this class!");
									p.sendMessage(MainCore.instance.language.getEquipClass());
									RpgClassData.setCurrentClass(p, MainCore.instance.rankCraft.archerClass.getId());
									RpgClassData.save();
									
									//p.closeInventory();
									//MainCore.instance.rankCraft.rpgClassGUI.open(p);
								}
								
							
							}
						} else if(args[1].equalsIgnoreCase("wizard")) {
							if(!RpgClassData.getUnlockWizard(p)) {
								
								if(RpgClassData.getClassPoints(p) > 0) {
								
									RpgClassData.setClassPoints(p, RpgClassData.getClassPoints(p) - 1);
									RpgClassData.setUnlockWizard(p, true);
									RpgClassData.setCurrentClass(p, MainCore.instance.rankCraft.wizardClass.getId());
									RpgClassData.save();
									
									p.sendMessage("You have successfully unlocked the Wizard class!");
									p.sendMessage(MainCore.instance.language.getUnlockClass(MainCore.instance.rankCraft.wizardClass.getName()));
									UnlockClassEvent event = new UnlockClassEvent(p, MainCore.instance.rankCraft.wizardClass);
									Bukkit.getServer().getPluginManager().callEvent(event);
									
									//p.closeInventory();
									//MainCore.instance.rankCraft.rpgClassGUI.open(p);
									
								} else {
								//	p.sendMessage("You do not have enought ClassPoints to unlock this class!");
									p.sendMessage(MainCore.instance.language.getNotEnoughClassPoints());
								}
								
							} else {
								if(RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.wizardClass.getId())) {
								//	p.sendMessage("You have already equipped this class!");
									p.sendMessage(MainCore.instance.language.getAlreadyEquippedClass());
								} else {
								//	p.sendMessage("You have successfully equipped this class!");
									p.sendMessage(MainCore.instance.language.getEquipClass());
									RpgClassData.setCurrentClass(p, MainCore.instance.rankCraft.wizardClass.getId());
									RpgClassData.save();
									
									//p.closeInventory();
									//MainCore.instance.rankCraft.rpgClassGUI.open(p);
								}
								
							
							}
						} else if(args[1].equalsIgnoreCase("ninja")) {
							if(!RpgClassData.getUnlockNinja(p)) {
								
								if(RpgClassData.getClassPoints(p) > 0) {
								
									RpgClassData.setClassPoints(p, RpgClassData.getClassPoints(p) - 1);
									RpgClassData.setUnlockNinja(p, true);
									RpgClassData.setCurrentClass(p, MainCore.instance.rankCraft.ninjaClass.getId());
									RpgClassData.save();
									
									p.sendMessage("You have successfully unlocked the Ninja class!");
									p.sendMessage(MainCore.instance.language.getUnlockClass(MainCore.instance.rankCraft.ninjaClass.getName()));
									UnlockClassEvent event = new UnlockClassEvent(p, MainCore.instance.rankCraft.ninjaClass);
									Bukkit.getServer().getPluginManager().callEvent(event);
									
									//p.closeInventory();
									//MainCore.instance.rankCraft.rpgClassGUI.open(p);
									
								} else {
								//	p.sendMessage("You do not have enought ClassPoints to unlock this class!");
									p.sendMessage(MainCore.instance.language.getNotEnoughClassPoints());
								}
								
							} else {
								if(RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.ninjaClass.getId())) {
								//	p.sendMessage("You have already equipped this class!");
									p.sendMessage(MainCore.instance.language.getAlreadyEquippedClass());
								} else {
								//	p.sendMessage("You have successfully equipped this class!");
									p.sendMessage(MainCore.instance.language.getEquipClass());
									RpgClassData.setCurrentClass(p, MainCore.instance.rankCraft.ninjaClass.getId());
									RpgClassData.save();
									
									//p.closeInventory();
									//MainCore.instance.rankCraft.rpgClassGUI.open(p);
								}
								
							
							}
						} else if(args[1].equalsIgnoreCase("mysterious")) {
							if(!RpgClassData.getUnlockMysterious(p)) {
								if(RpgClassData.getClassPoints(p) > 0 && MainCore.instance.rankCraft.gold.hasGoldAmount(p, MainCore.instance.config.mysteriousClassGoldCost/*100000*/) 
										&& RpgClassData.getUnlockArcher(p) && RpgClassData.getUnlockGladiator(p) && RpgClassData.getUnlockNinja(p)
										&& RpgClassData.getUnlockWizard(p)) {
									
									RpgClassData.setClassPoints(p, RpgClassData.getClassPoints(p) - 1);
									RpgClassData.setUnlockMysterious(p, true);
									RpgClassData.setCurrentClass(p, MainCore.instance.rankCraft.mysteriousClass.getId());
									RpgClassData.save();
									
									MainCore.instance.rankCraft.gold.removeGold(p, MainCore.instance.config.mysteriousClassGoldCost/*100000*/);
									
									
									
							//		p.sendMessage("You have successfully unlocked the Mysterious class!");
									p.sendMessage(MainCore.instance.language.getUnlockClass(MainCore.instance.rankCraft.mysteriousClass.getName()));
									UnlockClassEvent event = new UnlockClassEvent(p, MainCore.instance.rankCraft.mysteriousClass);
									Bukkit.getServer().getPluginManager().callEvent(event);
									
									//p.closeInventory();
									//MainCore.instance.rankCraft.rpgClassGUI.open(p);
									
								} else {
							//		p.sendMessage("You do not have enought ClassPoints to unlock this class!");
									p.sendMessage(MainCore.instance.language.getMysteriousClassCost());
								}
								
							} else {
								if(RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.mysteriousClass.getId())) {
							//		p.sendMessage("You have already equipped this class!");
									p.sendMessage(MainCore.instance.language.getAlreadyEquippedClass());
								} else {
								//	p.sendMessage("You have successfully equipped this class!");
									p.sendMessage(MainCore.instance.language.getEquipClass());
									RpgClassData.setCurrentClass(p, MainCore.instance.rankCraft.mysteriousClass.getId());
									RpgClassData.save();
									
									//p.closeInventory();
									//MainCore.instance.rankCraft.rpgClassGUI.open(p);
								}
								
							
							}
						}
						 
						
						
					} else {
						pls.sendNotPermissionMessage(p);
					}
				} else {
					if(p.hasPermission(pls.selectClassForOthers_permission)) {
						
						Player target;
						String parameter = args[1];
						if(MainCore.instance.usesPlaceholderAPI) {
							parameter = PlaceholderAPI.setPlaceholders(p, parameter);
							//System.out.println(parameter);
						}
						for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
							if(parameter.equals(onlinePlayer.getName())) {
								target = onlinePlayer;
								MainCore.instance.rankCraft.rpgClassSelectGUI.open(target);
								return;
							}
						}
						//p.sendMessage("This player is not online or he/she does not exist!");
						p.sendMessage(MainCore.instance.language.getPlayerNotFound());
						
					} else {
						pls.sendNotPermissionMessage(p);
					}
				}
				
			}
			
			
			
		}
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		if(args.length == 2) {
			List<String> ids = new ArrayList<>();
			String[] cl = {"gladiator", "archer", "wizard", "ninja", "mysterious"};
			ids.addAll(Arrays.asList(cl));
			ids.addAll(RCUtils.getOnlinePlayerNames());
			return ids;
		}
		return null;
	}
	
}
