package me.amc.rankcraft.mobs;

import java.io.File;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.PermissionList;
import me.amc.rankcraft.utils.RCUtils;

public class MobSpawnerListener implements Listener {

	@EventHandler
	public void place(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand(); //p.getItemInHand();
		Location loc = e.getBlock().getLocation();
		PermissionList pls = MainCore.instance.permList;
		
		if(item.getType() == Material.OBSIDIAN) {
			if(item.hasItemMeta()) {
				ItemMeta meta = item.getItemMeta();
				
				if(meta.hasDisplayName()) {
					
					String displayName = meta.getDisplayName();
					if(displayName.startsWith("Spawner")) {
						
						if(!RCUtils.isPlayerWorldEnabled(p))
							return;
						
						if(p.hasPermission(pls.placeMobSpawner_permission)) {
							String[] parts1 = displayName.split(":");
							String[] parts2 = parts1[1].split("-");
							
							String mobName = parts2[0];
							int sec = Integer.parseInt(parts2[1]);
							int range = Integer.parseInt(parts2[2]);
							
							MobSpawner spawner = new MobSpawner(mobName,sec,range);
							
							spawner.makeFile(loc.getBlockX()+""+loc.getBlockY()+""+loc.getBlockZ()+".yml");
							spawner.setFile(new File("plugins/RankCraft/spawners/"+loc.getBlockX()+""+loc.getBlockY()+""+loc.getBlockZ()+".yml"));
							spawner.writeFile();
							spawner.save();
							
							spawner.getC().set("Location.x", loc.getBlockX());
							spawner.getC().set("Location.y", loc.getBlockY());
							spawner.getC().set("Location.z", loc.getBlockZ());
							spawner.getC().set("Location.world", loc.getWorld().getName());
							spawner.getC().set("Options.mob", mobName+".yml");
							spawner.getC().set("Options.sec", sec);
							spawner.getC().set("Options.every", sec);
							spawner.getC().set("Options.range", range);
							spawner.save();
					/*		
							Location l = new Location(loc.getWorld(), spawner.getX(),spawner.getY()+1,spawner.getZ());
							
							ArmorStand name = (ArmorStand) loc.getWorld().spawnEntity(l, EntityType.ARMOR_STAND);
							name.setVisible(false);
							name.setMarker(true);
							name.setGravity(false);
							name.setCustomName("Spawns every "+sec+" seconds!");
							name.setCustomNameVisible(true);
							spawner.setName(name);
					*/	
							MainCore.instance.rankCraft.reloadMobSpawners();
						} else {
							pls.sendNotPermissionMessage(p);
						}
					}
					
				}
				
			}
		}
		
	}
	
	@EventHandler
	public void breakSpawner(BlockBreakEvent e) {
		Block b = e.getBlock();
		Location loc = b.getLocation();
	
		List<MobSpawner> ms = MainCore.instance.rankCraft.mobSpawners;
		if(ms.isEmpty()) return;
	//	System.out.println(ms);
		if(b.getType() == Material.OBSIDIAN) {
	//		System.out.println("found0");
		//	for(MobSpawner spawner : ms) {
			for(int i = 0; i < ms.size(); i++) {
				MobSpawner spawner = ms.get(i);
			//			System.out.println(spawner.getX()+","+spawner.getY()+","+spawner.getZ()+","+spawner.getWorld());
	//			System.out.println(loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ()+","+loc.getWorld().getName());
				
	//			System.out.println(spawner.getX() == loc.getBlockX());
	//			System.out.println(spawner.getY() == loc.getBlockY());
	//			System.out.println(spawner.getZ() == loc.getBlockZ());
	//			System.out.println(spawner.getWorld().equals(loc.getWorld().getName()));
				
				if(spawner.getX() == loc.getBlockX() && spawner.getY() == loc.getBlockY() 
						&& spawner.getZ() == loc.getBlockZ() && spawner.getWorld().equals(loc.getWorld().getName())) {
	//				System.out.println("found");
					MainCore.instance.rankCraft.mobSpawners.remove(spawner);
			//		spawner.getName().remove();
					File f = new File("plugins/RankCraft/spawners/"+spawner.getX()+""+spawner.getY()+""+spawner.getZ()+".yml");
					
					if(f.exists()) {
				//		System.out.println();
						f.delete();
					}
				}
			}
		}
		
		MainCore.instance.rankCraft.reloadMobSpawners();
		
	}
	
	
}
