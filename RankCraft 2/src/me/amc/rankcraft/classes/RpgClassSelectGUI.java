package me.amc.rankcraft.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.customevents.UnlockClassEvent;
import me.amc.rankcraft.gui.GUI;
import me.amc.rankcraft.utils.CustomItem;

public class RpgClassSelectGUI implements Listener {

	private GUI gui;
	
	private CustomItem gladiatorItem;
	private CustomItem archerItem;
	private CustomItem ninjaItem;
	private CustomItem wizardItem;
	private CustomItem mysteriousItem;
	
	private CustomItem classPoints;
	
	public String title = "Select RpgClass GUI";
	
	public RpgClassSelectGUI() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}
	
	public void open(Player p) {
		
		initItems(p);
		gui = new GUI(title, p, 9 * 3);

		gui.addItem(gladiatorItem.getItem(), 1);
		gui.addItem(archerItem.getItem(), 3);
		gui.addItem(ninjaItem.getItem(), 5);
		gui.addItem(wizardItem.getItem(), 7);
		gui.addItem(mysteriousItem.getItem(), 13);
		
		gui.addItem(gui.close.getItem(), gui.getSize() - 1);
		gui.addItem(classPoints.getItem(), gui.getSize() - 8);
		gui.addItem(gui.previous.getItem(), gui.getSize() - 9);
		
		gui.openInventory();
	}

	private void initItems(Player p) {
		String gClick = ChatColor.YELLOW+"Click this class to unlock!";
		String gLocked = ChatColor.RED+"Locked!";
		
		if(RpgClassData.getUnlockGladiator(p)) {
			gLocked = ChatColor.GREEN+"Unlocked!";
			gClick = ChatColor.YELLOW+"Click this class to equip!";
		}
		
		classPoints = new CustomItem(Material.EXPERIENCE_BOTTLE, ChatColor.GREEN+"Your ClassPoints: "+RpgClassData.getClassPoints(p)).build();
		
		gladiatorItem = new CustomItem(Material.IRON_CHESTPLATE, MainCore.instance.rankCraft.gladiatorClass.getName());
		gladiatorItem.addLores("The strongest of all the classes!",gLocked, gClick);
		gladiatorItem.build();
		
		String aClick = ChatColor.YELLOW+"Click this class to unlock!";
		String aLocked = ChatColor.RED+"Locked!";
		
		if(RpgClassData.getUnlockArcher(p)) {
			aLocked = ChatColor.GREEN+"Unlocked!";
			aClick = ChatColor.YELLOW+"Click this class to equip!";
		}
		
		archerItem = new CustomItem(Material.BOW, MainCore.instance.rankCraft.archerClass.getName());
		archerItem.addLores("Let's shoot some arrows!",aLocked, aClick);
		archerItem.build();
		
		String nClick = ChatColor.YELLOW+"Click this class to unlock!";
		String nLocked = ChatColor.RED+"Locked!";
		
		if(RpgClassData.getUnlockNinja(p)) {
			nLocked = ChatColor.GREEN+"Unlocked!";
			nClick = ChatColor.YELLOW+"Click this class to equip!";
		}
		
		ninjaItem = new CustomItem(Material.IRON_SWORD, MainCore.instance.rankCraft.ninjaClass.getName());
		ninjaItem.addLores("The fastest of all!", nLocked, nClick);
		ninjaItem.build();
		
		String wClick = ChatColor.YELLOW+"Click this class to unlock!";
		String wLocked = ChatColor.RED+"Locked!";
		
		if(RpgClassData.getUnlockWizard(p)) {
			wLocked = ChatColor.GREEN+"Unlocked!";
			wClick = ChatColor.YELLOW+"Click this class to equip!";
		}
		
		wizardItem = new CustomItem(Material.STICK, MainCore.instance.rankCraft.wizardClass.getName());
		wizardItem.addLores("Magic is power!", wLocked, wClick);
		wizardItem.build();
		
		//String mClick = ChatColor.YELLOW+"Unlock Cost: 1 ClassPoints, 100,000 gold, ";
		List<String> mClick = new ArrayList<>();
		mClick.add(ChatColor.YELLOW+"Unlock Cost: ");
		mClick.add(ChatColor.YELLOW+"- 1 ClassPoints");
		//mClick.add(ChatColor.YELLOW+"- 100,000 gold");
		mClick.add(ChatColor.YELLOW+"- "+MainCore.instance.config.mysteriousClassGoldCost+" gold");
		mClick.add(ChatColor.YELLOW+"- You must have unlocked Gladiator,");
		mClick.add(ChatColor.YELLOW+"  Archer, Ninja, Wizard classes");
		String mLocked = ChatColor.RED+"Locked!";
		
		if(RpgClassData.getUnlockMysterious(p)) {
			mLocked = ChatColor.GREEN+"Unlocked!";
			mClick.clear();
			mClick = Arrays.asList(ChatColor.YELLOW+"Click this class to equip!");
		}
		
		mysteriousItem = new CustomItem(Material.ENDER_EYE, MainCore.instance.rankCraft.mysteriousClass.getName());
		mysteriousItem.addLores("A mix of the Gladiator, Archer,"," Ninja, Wizard classes!", mLocked);
	//	mysteriousItem.addLores(strings);
		for(String line : mClick) {
			mysteriousItem.addLores(line);
		}
		
		mysteriousItem.build();
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {

		if (!e.getView().getTitle().equalsIgnoreCase(title)) {
			return;
		}

		e.setCancelled(true);

		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
			return;
		}
		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();

		if (gui.checkItemTypeAndName(item, gui.close.getName(), gui.close.getType())) {
			p.closeInventory();
		}
		
		if(gui.checkItemTypeAndName(item, gui.previous.getName(), gui.previous.getType())) {
			p.closeInventory();
			MainCore.instance.rankCraft.rpgClassGUI.open(p);
		}
		
		if(gui.checkItemTypeAndName(item, gladiatorItem.getName(), gladiatorItem.getType())) {
			if(!RpgClassData.getUnlockGladiator(p)) {
				
				if(RpgClassData.getClassPoints(p) > 0) {
				
					RpgClassData.setClassPoints(p, RpgClassData.getClassPoints(p) - 1);
					RpgClassData.setUnlockGladiator(p, true);
					RpgClassData.setCurrentClass(p, MainCore.instance.rankCraft.gladiatorClass.getId());
					RpgClassData.save();
					
			//		p.sendMessage("You have successfully unlocked the Gladiator class!");
					p.sendMessage(MainCore.instance.language.getUnlockClass(MainCore.instance.rankCraft.gladiatorClass.getName()));
					UnlockClassEvent event = new UnlockClassEvent(p, MainCore.instance.rankCraft.gladiatorClass);
					Bukkit.getServer().getPluginManager().callEvent(event);
				
					p.closeInventory();
					MainCore.instance.rankCraft.rpgClassGUI.open(p);
					
				} else {
			//		p.sendMessage("You do not have enought ClassPoints to unlock this class!");
					p.sendMessage(MainCore.instance.language.getNotEnoughClassPoints());
				}
				
			} else {
				if(RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.gladiatorClass.getId())) {
			//		p.sendMessage("You have already equipped this class!");
					p.sendMessage(MainCore.instance.language.getAlreadyEquippedClass());
				} else {
			//		p.sendMessage("You have successfully equipped this class!");
					p.sendMessage(MainCore.instance.language.getEquipClass());
					
					RpgClassData.setCurrentClass(p, MainCore.instance.rankCraft.gladiatorClass.getId());
					RpgClassData.save();
					
					p.closeInventory();
					MainCore.instance.rankCraft.rpgClassGUI.open(p);
				}
				
			//	p.sendMessage("You have already unlocked this class!");
			}
		}
		
		if(gui.checkItemTypeAndName(item, archerItem.getName(), archerItem.getType())) {
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
					
					p.closeInventory();
					MainCore.instance.rankCraft.rpgClassGUI.open(p);
					
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
					
					p.closeInventory();
					MainCore.instance.rankCraft.rpgClassGUI.open(p);
				}
				
			
			}
		}
		
		if(gui.checkItemTypeAndName(item, ninjaItem.getName(), ninjaItem.getType())) {
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
					
					p.closeInventory();
					MainCore.instance.rankCraft.rpgClassGUI.open(p);
					
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
					
					p.closeInventory();
					MainCore.instance.rankCraft.rpgClassGUI.open(p);
				}
				
			
			}
		}
		
		if(gui.checkItemTypeAndName(item, wizardItem.getName(), wizardItem.getType())) {
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
					
					p.closeInventory();
					MainCore.instance.rankCraft.rpgClassGUI.open(p);
					
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
					
					p.closeInventory();
					MainCore.instance.rankCraft.rpgClassGUI.open(p);
				}
				
			
			}
		}
		
		if(gui.checkItemTypeAndName(item, mysteriousItem.getName(), mysteriousItem.getType())) {
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
					
					p.closeInventory();
					MainCore.instance.rankCraft.rpgClassGUI.open(p);
					
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
					
					p.closeInventory();
					MainCore.instance.rankCraft.rpgClassGUI.open(p);
				}
				
			
			}
		}
	}
}
