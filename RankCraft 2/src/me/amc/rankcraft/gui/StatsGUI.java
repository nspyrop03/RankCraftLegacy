package me.amc.rankcraft.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.stats.Stats;
import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.RCUtils;

public class StatsGUI implements Listener {

	private GUI gui;
	private Stats s;
	
	private CustomItem xp;
	private CustomItem level;
	private CustomItem rank;
	private CustomItem bp;
	private CustomItem bb;
	
//	public String title = "Stats GUI";
	public String title = MainCore.instance.guiLang.getStatsTitle();
	
	
	public StatsGUI() {
		s = MainCore.instance.rankCraft.stats;
		
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}
	
	public void open(Player p) {
		initItems(p);
		gui = new GUI(title, p, 9*3);
		
		gui.addItem(xp.getItem(), 0);
		gui.addItem(level.getItem(), 1);
		gui.addItem(rank.getItem(), 2);
		gui.addItem(bp.getItem(), 7);
		gui.addItem(bb.getItem(), 8);
		
		gui.addItem(gui.close.getItem(), gui.getSize()-9);
		gui.addItem(gui.next.getItem(), gui.getSize()-1);
		
		gui.openInventory();
	}
	
	private void initItems(Player p) {
	//	xp = new CustomItem(Material.EXP_BOTTLE, ChatColor.GREEN+"Xp: "+RCUtils.round(s.getXp(p), 2)).build();
		xp = new CustomItem(Material.EXPERIENCE_BOTTLE, MainCore.instance.guiLang.getXp((float)RCUtils.round(s.getXp(p), 2))).build();
	//	level = new CustomItem(Material.IRON_INGOT, ChatColor.BLUE+"Level: "+s.getLevel(p)).build();
		level = new CustomItem(Material.IRON_INGOT, MainCore.instance.guiLang.getLevel(s.getLevel(p))).build();
	//	rank = new CustomItem(Material.GOLD_INGOT, ChatColor.GOLD+"Rank: "+s.getRank(p)).build();
		rank = new CustomItem(Material.GOLD_INGOT, MainCore.instance.guiLang.getRank(s.getRank(p))).build();
	//	bp = new CustomItem(Material.GRASS, ChatColor.GREEN+"BlocksPlaced: "+s.getBlocksPlaced(p)).build();
		bp = new CustomItem(Material.GRASS, MainCore.instance.guiLang.getBP(s.getBlocksPlaced(p))).build();
	//	bb = new CustomItem(Material.DIRT, ChatColor.RED+"BlocksBroken: "+s.getBlocksBroken(p)).build();
		bb = new CustomItem(Material.DIRT, MainCore.instance.guiLang.getBB(s.getBlocksBroken(p))).build();
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
		if(gui.checkItemTypeAndName(item, gui.next.getName(), gui.next.getType())) {
			p.closeInventory();
			MainCore.instance.rankCraft.mobStatsGUI.open(p);
		}
	}
	
	
}
