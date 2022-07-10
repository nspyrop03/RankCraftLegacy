package me.amc.rankcraft.events;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.backpack.BackpackUtils;
import me.amc.rankcraft.utils.RCUtils;

public class PlayerDieBackpackEvent implements Listener {

	@EventHandler
	public void onDie(PlayerDeathEvent e) {
		boolean keep = MainCore.instance.config.keepBackpack;

		if(RCUtils.isWorldEnabled(e.getEntity().getWorld())) {
		
			if (keep == false) {
				try {
	
					Inventory inv = Bukkit.createInventory(e.getEntity(), BackpackUtils.getLinesFromFile(e.getEntity())*9);
					
					for (int i = 0; i < BackpackUtils.getBackpackItems(e.getEntity()).size(); i++) {
						if (BackpackUtils.getBackpackItems(e.getEntity()).get(i) != null) {
							e.getEntity().getWorld().dropItem(e.getEntity().getLocation(),
									BackpackUtils.getBackpackItems(e.getEntity()).get(i));
						}
						
					}
	
					BackpackUtils.getBackpackItems(e.getEntity()).clear();
					BackpackUtils.saveInventory(inv, e.getEntity());
	
				} catch (IOException ex) {
					ex.printStackTrace();
				}
	
			}
			
			// Hard Mode Options
			boolean resetXp = MainCore.instance.config.resetXp;
			boolean resetSkills = MainCore.instance.config.resetSkills;
			boolean resetHp = MainCore.instance.config.resetHp;
			boolean resetGold = MainCore.instance.config.resetGold;
			
			if(resetXp) {
				MainCore.instance.rankCraft.stats.setXp(e.getEntity(), 0);
				MainCore.instance.rankCraft.stats.setLevel(e.getEntity(), 0);
				MainCore.instance.rankCraft.stats.checkLevelAndRank(e.getEntity());
				MainCore.instance.rankCraft.stats.save();
			}
			
			if(resetSkills) {
				MainCore.instance.rankCraft.defenseSkill.setPoints(e.getEntity(), 1);
				MainCore.instance.rankCraft.attackSkill.setPoints(e.getEntity(), 1);
				MainCore.instance.rankCraft.magicSkill.setPoints(e.getEntity(), 1);
				MainCore.instance.rankCraft.stats.setMaxManaLevel(e.getEntity(), 10);
				MainCore.instance.rankCraft.stats.save();
			}
			
			if(resetGold) {
				MainCore.instance.rankCraft.gold.setGold(e.getEntity(), 0);
				MainCore.instance.rankCraft.gold.save();
			}
			
			if(resetHp) {
				//MainCore.instance.rankCraft.hpSystem.setMaxHP(e.getEntity(), 20.0);
				MainCore.instance.rankCraft.hpSystem.setMaxHP(e.getEntity(), MainCore.instance.config.startingHP);
				//MainCore.instance.rankCraft.hpSystem.setHP(e.getEntity(), 20.0);
				MainCore.instance.rankCraft.hpSystem.save();
			}
		
		}
	}

}
