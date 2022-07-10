package me.amc.rankcraft.gui;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.backpack.BackpackUtils;
import me.amc.rankcraft.utils.CustomItem;

public class BackpackShop implements Listener {

//	public String title = "Backpack Shop";
	public String title = MainCore.instance.guiLang.getBPShopTitle();
	
	private GUI gui;

	private CustomItem upgrade;
	private CustomItem maxLevel;
	
	private ItemStack blackGlass = new ItemStack(Material.GLASS_PANE, 1, (short) 15);

	public BackpackShop() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}

	public void open(Player p) {
		initItems(p);
		gui = new GUI(title, p, 9 * 3);

		for (int i = 0; i < 9; i++) {
			gui.addItem(blackGlass, i);
		}
		gui.addItem(blackGlass, 9);
		gui.addItem(blackGlass, 17);
		for (int j = 18; j < gui.getSize(); j++) {
			gui.addItem(blackGlass, j);
		}
		if(BackpackUtils.hasUpgrade(p)) {
			gui.addItem(upgrade.getItem(), 8 + 5);
		} else {
			gui.addItem(maxLevel.getItem(), 8 + 5);
		}
		gui.addItem(gui.close.getItem(), gui.getSize() - 1);

		gui.openInventory();
	}

	private void initItems(Player p) {
	//	upgrade = new CustomItem(Material.CHEST, ChatColor.GREEN + "Upgrade Backpack").build();
		upgrade = new CustomItem(Material.CHEST, MainCore.instance.guiLang.getUpgradeBpItem()).build();
		upgrade.addLores(ChatColor.YELLOW+"Cost: "+BackpackUtils.getUpgradeCost(p));
		upgrade.build();
		
	//	maxLevel = new CustomItem(Material.ENDER_CHEST, ChatColor.RED+"Max Backpack storage!").build();
		maxLevel = new CustomItem(Material.ENDER_CHEST, MainCore.instance.guiLang.getMaxBpItem()).build();
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
		if (gui.checkItemTypeAndName(item, upgrade.getName(), upgrade.getType())) {
			try {
				if(MainCore.instance.rankCraft.gold.hasGoldAmount(p, BackpackUtils.getUpgradeCost(p))) {
					MainCore.instance.rankCraft.gold.removeGold(p, BackpackUtils.getUpgradeCost(p));
					BackpackUtils.setLines(p, BackpackUtils.getLinesFromFile(p) + 1);
				//	p.sendMessage("Your backpack has now " + BackpackUtils.getLinesFromFile(p) + " lines of storage!");
					p.sendMessage(MainCore.instance.language.getBackpackAfterUpgrade(BackpackUtils.getLinesFromFile(p)));
					p.closeInventory();
					open(p);
				} else {
				//	p.sendMessage(ChatColor.RED+"You don't have enough money!");
					p.sendMessage(MainCore.instance.language.getNotEnoughGold());
				}
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
