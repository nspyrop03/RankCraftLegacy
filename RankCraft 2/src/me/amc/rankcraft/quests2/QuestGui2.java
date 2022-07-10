package me.amc.rankcraft.quests2;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.gui.GUI;
import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.RCUtils;

public class QuestGui2 implements Listener {

	private GUI gui;

	private CustomItem placeQuests;
	private CustomItem breakQuests;
	private CustomItem killMobsQuests;
	private CustomItem killPlayersQuests;
	private CustomItem back;
	
//	private String title = "Quests Menu";
	private String title = MainCore.instance.guiLang.getQuestsTitle();
	
	public QuestGui2() {
		initItems();
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}

	public ItemStack getPersonalizedQuestItem(Quest q, Player p) {
		CustomItem qItem = new CustomItem(Material.PAPER, q.getName());

		int lastCT = q.hmCompletedTimes().get(p.getUniqueId().toString()) - 1;

		qItem.removeLores(ChatColor.YELLOW + "Completed Times: " + ChatColor.RED + lastCT);
		qItem.removeLores(ChatColor.YELLOW + "Completed Times: " + ChatColor.RED
				+ q.hmCompletedTimes().get(p.getUniqueId().toString()));
		qItem.addLores(ChatColor.YELLOW + "Completed Times: " + ChatColor.RED
				+ q.hmCompletedTimes().get(p.getUniqueId().toString()));

		qItem.removeIfStart(ChatColor.YELLOW + "You can take this quest again in");
		qItem.addLores(ChatColor.YELLOW + "You can take this quest again in " + ChatColor.RED
				+ q.hmSecondsToReTake().get(p.getUniqueId().toString()) + ChatColor.YELLOW + " seconds.");

		qItem.removeIfStart(ChatColor.YELLOW + "Seconds Remaining: ");
		qItem.addLores(ChatColor.YELLOW + "Seconds Remaining: " + ChatColor.RED
				+ q.hmSecondsRemaining().get(p.getUniqueId().toString()));

		switch(q.getType()) {
		case BreakBlocks:
			qItem.removeIfStart(ChatColor.YELLOW+"You have to break ");
			qItem.addLores(ChatColor.YELLOW+"You have to break "+ChatColor.RED+""+q.getBreakBlockAmount()+" "+q.getBreakBlocksType()+ChatColor.YELLOW+" blocks !");
			break;
		case KillMobs:
			qItem.removeIfStart(ChatColor.YELLOW+"You have to kill ");
			qItem.addLores(ChatColor.YELLOW+"You have to kill "+ChatColor.RED+""+q.getKillEntityAmount()+" "+q.getKillEntityType()+ChatColor.YELLOW+" mobs !");
			break;
		case KillPlayers:
			qItem.removeIfStart(ChatColor.YELLOW+"You have to kill ");
			qItem.addLores(ChatColor.YELLOW+"You have to kill "+ChatColor.RED+""+q.getKillPlayerAmount()+" "+ChatColor.YELLOW+" players !");
			break;
		case PlaceBlocks:
			qItem.removeIfStart(ChatColor.YELLOW+"You have to place ");
			qItem.addLores(ChatColor.YELLOW+"You have to place "+ChatColor.RED+""+q.getPlaceBlockAmount()+" "+q.getPlaceBlocksType()+ChatColor.YELLOW+" blocks !");
			break;
		default:
			qItem.addLores(ChatColor.DARK_RED+"Unknown type of quest! Please do not select it !");
			break;
		}
		
		qItem.build(q.hmTaken().get(p.getUniqueId().toString()));
		return qItem.getItem();
	}

	private void initItems() {
	//	placeQuests = new CustomItem(Material.GRASS, "Place Blocks Quests");
		placeQuests = new CustomItem(Material.GRASS, MainCore.instance.guiLang.getPlaceTitleQ());
		placeQuests.build(false);

	//	breakQuests = new CustomItem(Material.GOLD_ORE, "Break Blocks Quests");
		breakQuests = new CustomItem(Material.GOLD_ORE, MainCore.instance.guiLang.getBreakTitleQ());
		breakQuests.build(false);

	//	killMobsQuests = new CustomItem(Material.ROTTEN_FLESH, "Kill Mobs Quests");
		killMobsQuests = new CustomItem(Material.ROTTEN_FLESH, MainCore.instance.guiLang.getKillMTitleQ());
		killMobsQuests.build(false);

	//	killPlayersQuests = new CustomItem(Material.DIAMOND_CHESTPLATE, "Kill Players Quests");
		killPlayersQuests = new CustomItem(Material.DIAMOND_CHESTPLATE, MainCore.instance.guiLang.getKillPTitleQ());
		killPlayersQuests.build(false);

	//	back = new CustomItem(Material.ARROW, ChatColor.RED + "Back to Quests Menu");
		back = new CustomItem(Material.ARROW, ChatColor.RED + MainCore.instance.guiLang.getBackToMenu());
		back.build(false);
	}

	public void open(Player p) {
		gui = new GUI(title, p, 9*3);

		gui.addItem(placeQuests.getItem(), 10);
		gui.addItem(breakQuests.getItem(), 12);
		gui.addItem(killMobsQuests.getItem(), 14);
		gui.addItem(killPlayersQuests.getItem(), 16);
		gui.addItem(gui.close.getItem(), gui.getSize()-1);
		
		gui.openInventory();
		
	}

	public void openPlaceBlocks(Player p) {
		gui = new GUI(placeQuests.getName(), p, 9*6);
		int slot = 0;
		for(Quest q : MainCore.instance.rankCraft.quests2) {
			if(q.getType() == Quest.QuestType.PlaceBlocks) {
				gui.addItem(getPersonalizedQuestItem(q, p), slot);
				slot++;
			}
		}
		gui.addItem(gui.close.getItem(), gui.getSize()-1);
		gui.addItem(gui.previous.getItem(), gui.getSize()-9);
		gui.openInventory();
	}

	public void openBreakBlocks(Player p) {
		gui = new GUI(breakQuests.getName(), p, 9*6);
		int slot = 0;
		for(Quest q : MainCore.instance.rankCraft.quests2) {
			if(q.getType() == Quest.QuestType.BreakBlocks) {
				gui.addItem(getPersonalizedQuestItem(q, p), slot);
				slot++;
			}
		}
		gui.addItem(gui.close.getItem(), gui.getSize()-1);
		gui.addItem(gui.previous.getItem(), gui.getSize()-9);
		gui.openInventory();
	}

	public void openKillMobs(Player p) {
		gui = new GUI(killMobsQuests.getName(), p, 9*6);
		int slot = 0;
		for(Quest q : MainCore.instance.rankCraft.quests2) {
			if(q.getType() == Quest.QuestType.KillMobs) {
				gui.addItem(getPersonalizedQuestItem(q, p), slot);
				slot++;
			}
		}
		gui.addItem(gui.close.getItem(), gui.getSize()-1);
		gui.addItem(gui.previous.getItem(), gui.getSize()-9);
		gui.openInventory();
	}

	public void openKillPlayers(Player p) {
		gui = new GUI(killPlayersQuests.getName(), p, 9*6);
		int slot = 0;
		for(Quest q : MainCore.instance.rankCraft.quests2) {
			if(q.getType() == Quest.QuestType.KillPlayers) {
				gui.addItem(getPersonalizedQuestItem(q, p), slot);
				slot++;
			}
		}
		gui.addItem(gui.close.getItem(), gui.getSize()-1);
		gui.addItem(gui.previous.getItem(), gui.getSize()-9);
		gui.openInventory();
	}

	@EventHandler
	public void onClickInventory(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		ItemStack current = e.getCurrentItem();

	//	switch (ChatColor.stripColor(e.getInventory().getName())) {

	//	case "Quests Menu":
	//	case placeQuests.getName():
		if(e.getView().getTitle().equals(MainCore.instance.guiLang.getQuestsTitle())) {
			e.setCancelled(true);

			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
				return;
			}

			if (this.hasItemNameAndType(current, gui.close.getName(),
					gui.close.getType())) {
				p.closeInventory();
			}

			if (this.hasItemNameAndType(current, placeQuests.getName(), placeQuests.getType())) {
				p.closeInventory();
				openPlaceBlocks(p);
			}

			if (this.hasItemNameAndType(current, breakQuests.getName(), breakQuests.getType())) {
				p.closeInventory();
				openBreakBlocks(p);
			}

			if (this.hasItemNameAndType(current, killMobsQuests.getName(), killMobsQuests.getType())) {
				p.closeInventory();
				openKillMobs(p);
			}

			if (this.hasItemNameAndType(current, killPlayersQuests.getName(), killPlayersQuests.getType())) {
				p.closeInventory();
				openKillPlayers(p);
			}

		//	break;
		} else if(e.getView().getTitle().equals(MainCore.instance.guiLang.getPlaceTitleQ())) {
	//	case "Place Blocks Quests":

			e.setCancelled(true);

			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
				return;
			}

			if (this.hasItemNameAndType(current, gui.previous.getName(),
					gui.previous.getType())) {
				p.closeInventory();
				open(p);
			}

			for (Quest q : MainCore.instance.rankCraft.quests2) {
				if (this.hasItemNameAndType(current, getPersonalizedQuestItem(q, p).getItemMeta().getDisplayName(),
						getPersonalizedQuestItem(q, p).getType())) {
					if (q.getName().equals(getPersonalizedQuestItem(q, p).getItemMeta().getDisplayName())) {
						if (q.getType() == Quest.QuestType.PlaceBlocks) {
							RCUtils.giveQuest(q, p);
						}
					}
				}
			}
			
			if (this.hasItemNameAndType(current, gui.close.getName(),
					gui.close.getType())) {
				p.closeInventory();
			}

		//	break;
		} else if(e.getView().getTitle().equals(MainCore.instance.guiLang.getBreakTitleQ())) {
	//	case "Break Blocks Quests":
			e.setCancelled(true);

			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
				return;
			}

			if (this.hasItemNameAndType(current, gui.previous.getName(),
					gui.previous.getType())) {
				p.closeInventory();
				open(p);
			}

			for (Quest q : MainCore.instance.rankCraft.quests2) {
				if (this.hasItemNameAndType(current, getPersonalizedQuestItem(q, p).getItemMeta().getDisplayName(),
						getPersonalizedQuestItem(q, p).getType())) {
					if (q.getName().equals(getPersonalizedQuestItem(q, p).getItemMeta().getDisplayName())) {
						if (q.getType() == Quest.QuestType.BreakBlocks) {
							RCUtils.giveQuest(q, p);
						}
					}
				}
			}
			
			if (this.hasItemNameAndType(current, gui.close.getName(),
					gui.close.getType())) {
				p.closeInventory();
			}
			
	//		break;
		} else if(e.getView().getTitle().equals(MainCore.instance.guiLang.getKillMTitleQ())) {
	//	case "Kill Mobs Quests":
			e.setCancelled(true);

			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
				return;
			}

			if (this.hasItemNameAndType(current, gui.previous.getName(),
					gui.previous.getType())) {
				p.closeInventory();
				open(p);
			}

			for (Quest q : MainCore.instance.rankCraft.quests2) {
				if (this.hasItemNameAndType(current, getPersonalizedQuestItem(q, p).getItemMeta().getDisplayName(),
						getPersonalizedQuestItem(q, p).getType())) {
					if (q.getName().equals(getPersonalizedQuestItem(q, p).getItemMeta().getDisplayName())) {
						if (q.getType() == Quest.QuestType.KillMobs) {
							RCUtils.giveQuest(q, p);
						}
					}
				}
			}
			
			if (this.hasItemNameAndType(current, gui.close.getName(),
					gui.close.getType())) {
				p.closeInventory();
			}
			
		//	break;
		} else if(e.getView().getTitle().equals(MainCore.instance.guiLang.getKillPTitleQ())) {
	//	case "Kill Players Quests":
			e.setCancelled(true);

			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
				return;
			}

			if (this.hasItemNameAndType(current, gui.previous.getName(),
					gui.previous.getType())) {
				p.closeInventory();
				open(p);
			}

			for (Quest q : MainCore.instance.rankCraft.quests2) {
				if (this.hasItemNameAndType(current, getPersonalizedQuestItem(q, p).getItemMeta().getDisplayName(),
						getPersonalizedQuestItem(q, p).getType())) {
					if (q.getName().equals(getPersonalizedQuestItem(q, p).getItemMeta().getDisplayName())) {
						if (q.getType() == Quest.QuestType.KillPlayers) {
							RCUtils.giveQuest(q, p);
						}
					}
				}
			}
			
			if (this.hasItemNameAndType(current, gui.close.getName(),
					gui.close.getType())) {
				p.closeInventory();
			}
			
		//	break;
		}
	//	default:
	//		break;
	//	}
	}

	public boolean hasItemThisName(ItemStack item, String name) {
		if (item.hasItemMeta()) {
			if (item.getItemMeta().hasDisplayName()) {
				if (item.getItemMeta().getDisplayName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean isItemOfThisType(ItemStack item, Material type) {
		if (item.getType() == type) {
			return true;
		}
		return false;
	}

	public boolean hasItemNameAndType(ItemStack item, String name, Material type) {
		if (hasItemThisName(item, name) && isItemOfThisType(item, type)) {
			return true;
		}
		return false;
	}
}
