package me.amc.rankcraft.mobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.block.Biome;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class DefaultMobSpawn implements Listener {

	@EventHandler
	public void spawn(EntitySpawnEvent e) {
		if(RCUtils.isWorldEnabled(e.getEntity().getWorld())) {
			if(MainCore.instance.config.enableCustomMobs) {
				Biome biome = e.getLocation().getBlock().getBiome();
				if (e.getEntity() instanceof Zombie) {
					//System.out.println("x: "+e.getLocation().getBlockX()+" y: "+e.getLocation().getBlockY()+" Biome: "+biome);
					Zombie z = (Zombie) e.getEntity();
					List<YamlMob> zombies = MainCore.instance.rankCraft.yamlZombies;
					List<YamlMob> spawnable = new ArrayList<>();
					
					for(YamlMob y : zombies) {
						if(y.isDefaultSpawn() && y.isFinished() && (y.getBiomes().isEmpty() || y.getBiomes().contains(biome))) {
							spawnable.add(y);
						}
					}
					
					YamlMob zombie = spawnable.get(new Random().nextInt(spawnable.size()));
					zombie.makeMob(z);
					
					
				}
				
				if(e.getEntity() instanceof Skeleton) {
					Skeleton s = (Skeleton) e.getEntity();
					List<YamlMob> skeletons = MainCore.instance.rankCraft.yamlSkeletons;
					List<YamlMob> spawnable = new ArrayList<>();
					
					for(YamlMob y : skeletons) {
						if(y.isDefaultSpawn() && y.isFinished() && (y.getBiomes().isEmpty() || y.getBiomes().contains(biome))) {
							spawnable.add(y);
						}
					}
					
					YamlMob skeleton = spawnable.get(new Random().nextInt(spawnable.size()));
					skeleton.makeMob(s);
				}
			}
		}
	}
/*
	@EventHandler
	public void death(EntityDeathEvent e) {
		if(e.getEntity() instanceof LivingEntity) {
			if(e.getEntity() instanceof Zombie || e.getEntity() instanceof Skeleton) {
				YamlMob y = 
			}
		}
	}
*/
}
