package me.amc.rankcraft.spells;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import me.amc.rankcraft.MainCore;

public class SpiderSpellWebEvent implements Listener {

	@EventHandler
	public void onBlockChange(EntityChangeBlockEvent event){
		
		if(event.getEntity() instanceof FallingBlock){
            
            if(SpiderSpell.blocks.contains(event.getEntity()) && MainCore.instance.config.removeWeb) {
            	Bukkit.getScheduler().runTaskLater(MainCore.instance, () -> {
            		event.getBlock().setType(Material.AIR);
                	SpiderSpell.blocks.remove(event.getEntity());   
    			}, 20 * MainCore.instance.config.removeWebSeconds); // Time in ticks (20 ticks = 1 second)
            	
            }
            	
        }
		
	}
	
}
