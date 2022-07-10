package me.amc.rankcraft.treasures2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.treasures2.TreasureChest2.Rarity;
import me.amc.rankcraft.utils.RCUtils;
import me.amc.rankcraft.yaml.YamlParser;

public class TreasureMakerEvent implements Listener {
	
	public TreasureMakerEvent() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}

	@EventHandler
	public void onCloseMaker(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		Inventory inv = e.getInventory();
		
		if(e.getView().getTitle().startsWith("Treasure Maker") || e.getView().getTitle().startsWith("Treasure Editor")) {
			
			String t = e.getView().getTitle();
			String[] parts = t.split(": ");
			
			
			File f = new File(RCUtils.TREASURES2_DIRECTORY+"/"+parts[1]+".yml");
			
			
			FileConfiguration c = YamlConfiguration.loadConfiguration(f);
			List<ItemStack> items = new ArrayList<>();
			for (ItemStack it : inv.getContents()) {
				if(it == null) {
					items.add(new ItemStack(Material.AIR));
				} else {
					items.add(it);
				}
			}
			c.set("items", items);
			try {
				c.save(f);
			} catch (IOException ex) {
				MainCore.instance.sendError("[Treasures2] Oups, something went wrong!");
			}
			
			//p.sendMessage("Treasure made!");
			p.sendMessage(MainCore.instance.language.getTreasures2Complete());
			
			YamlParser y = new YamlParser(RCUtils.TREASURES2_DIRECTORY, parts[1]+".yml");
			
			TreasureChest2 t2 = new TreasureChest2(y.getConfig().getString("Name"), parts[1], Rarity.valueOf(y.getConfig().getString("Rarity").toUpperCase()));
			t2.giveToPlayer(p, 1);
			
			MainCore.instance.rankCraft.initTreasureChests();
		}
	}
	
}
