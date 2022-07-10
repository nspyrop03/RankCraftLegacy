package me.amc.rankcraft.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.CustomItem;

public class PacksGUI implements Listener {

	private GUI gui;
//	public String title = "Pack Shop";
	public String title = MainCore.instance.guiLang.getPackShopTitle();
	
	private ItemStack blackGlass = new ItemStack(Material.GLASS_PANE, 1, (short)15);
	private CustomItem bronzePacks;
	private CustomItem silverPacks;
	private CustomItem goldPacks;
	
	public PacksGUI() {		
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}
	
	public void open(Player p) {
		initItems(p);
		gui = new GUI(title, p, 9*3);
	
		for(int i = 0; i < 9; i++) {
			gui.addItem(blackGlass, i);
		}
		gui.addItem(blackGlass, 9);
		gui.addItem(blackGlass, 17);
		for(int j = 18; j < gui.getSize(); j++) {
			gui.addItem(blackGlass, j);
		}
		
		gui.addItem(goldPacks.getItem(), 11);
		gui.addItem(silverPacks.getItem(), 13);
		gui.addItem(bronzePacks.getItem(), 15);
		
		gui.addItem(gui.close.getItem(), gui.getSize()-1);
		//gui.addItem(gui.previous.getItem(), gui.getSize()-9);
		
		gui.openInventory();
	}
	
	private void initItems(Player p) {
	//	bronzePacks = new CustomItem(Material.FEATHER, ChatColor.GREEN+"Buy "+ChatColor.GOLD+"Bronze"+ChatColor.GREEN+" Packs").build();
		bronzePacks = new CustomItem(Material.FEATHER, MainCore.instance.guiLang.getBuyBronze()).build();
	//	silverPacks = new CustomItem(Material.IRON_INGOT, ChatColor.GREEN+"Buy "+ChatColor.GRAY+"Silver"+ChatColor.GREEN+" Packs").build();
		silverPacks = new CustomItem(Material.IRON_INGOT, MainCore.instance.guiLang.getBuySilver()).build();
	//	goldPacks = new CustomItem(Material.GOLD_INGOT, ChatColor.GREEN+"Buy "+ChatColor.YELLOW+"Gold"+ ChatColor.GREEN+" Packs").build();
		goldPacks = new CustomItem(Material.GOLD_INGOT, MainCore.instance.guiLang.getBuyGold()).build();
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {

		if(!e.getView().getTitle().equalsIgnoreCase(title)) {
			return;
		}

		e.setCancelled(true);
		
		if(e.getCurrentItem()==null || e.getCurrentItem().getType()==Material.AIR) {
			return;
		}
		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		
		if(gui.checkItemTypeAndName(item, gui.close.getName(), gui.close.getType())) {
			p.closeInventory();
		}
		
		if(gui.checkItemTypeAndName(item, bronzePacks.getName(), bronzePacks.getItem().getType())) {
			p.closeInventory();
			MainCore.instance.rankCraft.bronzePackShop.open(p);
		}
		
		if(gui.checkItemTypeAndName(item, silverPacks.getName(), silverPacks.getItem().getType())) {
			p.closeInventory();
			MainCore.instance.rankCraft.silverPackShop.open(p);
		}
		
		if(gui.checkItemTypeAndName(item, goldPacks.getName(), goldPacks.getItem().getType())) {
			p.closeInventory();
			MainCore.instance.rankCraft.goldPackShop.open(p);
		}
	}
	
}
