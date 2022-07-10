package me.amc.rankcraft.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.rpgitem.CrystalSize;
import me.amc.rankcraft.rpgitem.ManaCrystal;
import me.amc.rankcraft.rpgitem.XpCrystal;
import me.amc.rankcraft.utils.RCUtils;

public class CrystalCraftEvent implements Listener {

	private List<ItemStack> crystals = new ArrayList<>();
	
	public CrystalCraftEvent() {
		crystals.clear();
		crystals.add(new XpCrystal(CrystalSize.Small, RCUtils.XPCRYSTAL_SMALL_NAME).getItem().getItem());
		crystals.add(new XpCrystal(CrystalSize.Medium, RCUtils.XPCRYSTAL_MEDIUM_NAME).getItem().getItem());
		crystals.add(new XpCrystal(CrystalSize.Large, RCUtils.XPCRYSTAL_LARGE_NAME).getItem().getItem());
		crystals.add(new ManaCrystal(CrystalSize.Small, RCUtils.MANACRYSTAL_SMALL_NAME).getItem().getItem());
		crystals.add(new ManaCrystal(CrystalSize.Medium, RCUtils.MANACRYSTAL_MEDIUM_NAME).getItem().getItem());
		crystals.add(new ManaCrystal(CrystalSize.Large, RCUtils.MANACRYSTAL_LARGE_NAME).getItem().getItem());
		
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}
	
	@EventHandler
	public void onCraft(PrepareItemCraftEvent e) {
		CraftingInventory inv = e.getInventory();
		
		for(ItemStack item : inv.getMatrix()) {
			for(ItemStack c : crystals) {
				if(item != null && c != null) {
					if(item.isSimilar(c)) {
						inv.setResult(null);
					}
				}
			}
		}
		
	}
	
}
