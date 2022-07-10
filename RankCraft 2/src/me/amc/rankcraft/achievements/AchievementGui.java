package me.amc.rankcraft.achievements;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.gui.GUI;
import me.amc.rankcraft.utils.RCUtils;

public class AchievementGui implements Listener {

	private GUI gui;
//	public String title; // = MainCore.instance.guiLang.geta
	public String title = MainCore.instance.guiLang.getAchievementsTitle();
	
	public AchievementGui() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}
	
	public void open(Player p) {
		
		int completedAchievements = 0;
		for(Achievement a : MainCore.instance.rankCraft.achievements) {
			if(a.getCompletedHM().get(RCUtils.textedUUID(p)) == true) {
				completedAchievements++;
			}
		}
		
	//	title = MainCore.instance.guiLang.getAchievementsTitle(completedAchievements, MainCore.instance.rankCraft.achievements.size());
		
		String suffix = "("+completedAchievements+"/"+MainCore.instance.rankCraft.achievements.size()+")";
		
		gui = new GUI(title+" "+suffix, p, 9*6);
		int slot = 0;
		
		for(Achievement a : MainCore.instance.rankCraft.achievements) {
			gui.addItem(a.getCustomizedItemStack(p), slot);
			slot++;
		}
		
		gui.openInventory();
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		//if(!(event.getInventory().g).startsWith(title)) {
		if(!event.getView().getTitle().startsWith(title)) {
			return;
		}		
		
		event.setCancelled(true);
		
		if(event.getCurrentItem()==null || event.getCurrentItem().getType()==Material.AIR) {
			return;
		}
	}
	
}
