package me.amc.rankcraft.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.packs.BlockPack;
import me.amc.rankcraft.packs.ItemPack;
import me.amc.rankcraft.packs.MixedPack;
import me.amc.rankcraft.packs.Pack.PackLevel;
import me.amc.rankcraft.utils.RCUtils;

public class BronzePacks implements Listener {

	private GUI gui;
//	public String title = "Bronze Pack Shop";
	public String title = MainCore.instance.guiLang.getBronzePackTitle();
	
	private ItemStack blackGlass = new ItemStack(Material.GLASS_PANE, 1, (short)15);
	
	private BlockPack b = new BlockPack(PackLevel.Bronze);
	private ItemPack i = new ItemPack(PackLevel.Bronze);
	private MixedPack m = new MixedPack(PackLevel.Bronze);
	
	public BronzePacks() {		
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}
	
	public void open(Player p) {
		initItems();
		
		gui = new GUI(title, p, 9*3);
		gui = new GUI(title, p, 9*3);
		
		for(int i = 0; i < 9; i++) {
			gui.addItem(blackGlass, i);
		}
		gui.addItem(blackGlass, 9);
		gui.addItem(blackGlass, 17);
		for(int j = 18; j < gui.getSize(); j++) {
			gui.addItem(blackGlass, j);
		}

		gui.addItem(b.getItem().getItem(), 11);
		gui.addItem(i.getItem().getItem(), 13);
		gui.addItem(m.getItem().getItem(), 15);
		
		gui.addItem(gui.close.getItem(), gui.getSize()-1);
		gui.addItem(gui.previous.getItem(), gui.getSize()-9);
		
		gui.openInventory();
	}
	
	private void initItems() {
		b.getItem().removeIfStart(ChatColor.GREEN+"Costs: ");
		b.getItem().addLores(ChatColor.GREEN+"Costs: "+b.getCost());
		b.getItem().build();
		
		i.getItem().removeIfStart(ChatColor.GREEN+"Costs: ");
		i.getItem().addLores(ChatColor.GREEN+"Costs: "+i.getCost());
		i.getItem().build();
		
		m.getItem().removeIfStart(ChatColor.GREEN+"Costs: ");
		m.getItem().addLores(ChatColor.GREEN+"Costs: "+m.getCost());
		m.getItem().build();
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
		
		if(gui.checkItemTypeAndName(item, gui.previous.getName(), gui.previous.getType())) {
			p.closeInventory();
			MainCore.instance.rankCraft.packShop.open(p);
		}
		
		if(gui.checkItemTypeAndName(item, b.getName(), b.getItem().getType())) {
			RCUtils.buyPack(p, b);
		}
		
		if(gui.checkItemTypeAndName(item, i.getName(), i.getItem().getType())) {
			RCUtils.buyPack(p, i);
		}
		
		if(gui.checkItemTypeAndName(item, m.getName(), m.getItem().getType())) {
			RCUtils.buyPack(p, m);
		}
	}
}
