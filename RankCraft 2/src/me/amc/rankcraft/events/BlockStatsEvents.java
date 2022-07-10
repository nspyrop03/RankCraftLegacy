package me.amc.rankcraft.events;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.stats.Stats;
import me.amc.rankcraft.stats.Stats.CountBlocks;
import me.amc.rankcraft.utils.RCUtils;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

public class BlockStatsEvents implements Listener {

	private HashMap<String, ArrayList<String>> savedBlocks = new HashMap<>();
	
	private Stats s;
	
	public BlockStatsEvents() {
		s = MainCore.instance.rankCraft.stats;
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
		savedBlocks.clear();
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block block = e.getBlock();
		
		if(MainCore.instance.getWorldGuard() != null && MainCore.instance.getWorldEdit() != null) {
			if(!canBuild(p, e.getBlock().getLocation())) {
				return;
			}
		}
		
		if(Bukkit.getPluginManager().isPluginEnabled("GriefPrevention")) {
			/*
			Claim claim = GriefPrevention.instance.dataStore.getClaimAt(p.getLocation(), false, null);
			if(claim != null) {
				if(!claim.managers.contains(p.getName())) {
					System.out.println(p.getName()+" cannot siege this!");
				}
			}
			*/
			String noBuildReason = GriefPrevention.instance.allowBreak(p, block, block.getLocation());
			if(noBuildReason != null)
			{
				
				//System.out.println(p.getName()+" cannot break blocks here!");
				return;
				
			}
		}
		
		if(e.getBlock().getType() == Material.AIR) { // Fix bug with RandomTeleport plugin
			//System.out.println("Ok??");
			return;
		}

		if(RCUtils.isWorldEnabled(e.getBlock().getWorld())) {
			if(!MainCore.instance.config.noXpMaterials.contains(e.getBlock().getType()) && !isInSavedBlocks(p, block)) {
				if(MainCore.instance.config.specialBreak.keySet().contains(e.getBlock().getType())) {
					s.addXp(p, MainCore.instance.config.specialBreak.get(e.getBlock().getType()));
				} else {
					s.addBlockXp(CountBlocks.Broken, p);
				}
			}
			
			s.countBlock(CountBlocks.Broken, p);
			s.checkLevelAndRank(p);
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Block block = e.getBlock();
		
		if(MainCore.instance.getWorldGuard() != null && MainCore.instance.getWorldEdit() != null) {
			if(!canBuild(p, e.getBlock().getLocation())) {
				return;
				
			}
		}
		
		if(Bukkit.getPluginManager().isPluginEnabled("GriefPrevention")) {
			String noBuildReason = GriefPrevention.instance.allowBuild(p, block.getLocation(), block.getType());
	        if(noBuildReason != null)
	        {
	            
	        	//System.out.println(p.getName()+" cannot place blocks here!");
	        	return;
	           
	        }
		}
		
		if(RCUtils.isWorldEnabled(e.getBlock().getWorld())) {
			if(!MainCore.instance.config.noXpMaterials.contains(e.getBlockPlaced().getType()) && !isInSavedBlocks(p, block)) {
				if(MainCore.instance.config.specialPlace.keySet().contains(e.getBlockPlaced().getType())) {
					s.addXp(p, MainCore.instance.config.specialPlace.get(e.getBlock().getType()));
				} else {
					s.addBlockXp(CountBlocks.Placed, p);
				}
			}
			
			addToSavedBlocks(p, block);
			
			s.countBlock(CountBlocks.Placed, p);
			s.checkLevelAndRank(p);
		}
	}
	
	
	private void addToSavedBlocks(Player p, Block b) {
		if(MainCore.instance.config.saveBlocks) {
			Location loc = b.getLocation();
			String s = ""+loc.getBlockX()+","+loc.getBlockY()+","+loc.getBlockZ();
			String uuid = p.getUniqueId().toString();
			if(savedBlocks.containsKey(uuid)) {
				if(!isInSavedBlocks(p, b)) {
					savedBlocks.get(uuid).add(s);
					if(savedBlocks.get(uuid).size() > MainCore.instance.config.blocksToSave) {
						savedBlocks.get(uuid).remove(0);
					}
				}
			} else {
				savedBlocks.put(uuid, new ArrayList<>());
				savedBlocks.get(uuid).add(s);
			}
		}
	}
	
	public boolean isInSavedBlocks(Player p, Block b) {
		boolean found = false;
		if(MainCore.instance.config.saveBlocks) {
			String uuid = p.getUniqueId().toString();
			Location loc = b.getLocation();
			if(savedBlocks.containsKey(uuid)) {
				for(String s : savedBlocks.get(uuid)) {
					String[] parts = s.split(",");
					int x = Integer.parseInt(parts[0]);
					int y = Integer.parseInt(parts[1]);
					int z = Integer.parseInt(parts[2]);
					if(loc.getBlockX() == x && loc.getBlockY() == y && loc.getBlockZ() == z) {
						found = true;
					}
				}
			}
		}
		return found;
	}
	
	public boolean canBuild(Player p, Location l) {
        RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(l);
        if (!hasBypass(p, l)) {
            return query.testState(loc, WorldGuardPlugin.inst().wrapPlayer(p), Flags.BUILD);
        }else {
            return true;
        }
    }


    public boolean hasBypass(Player p, Location l) {
        return WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(WorldGuardPlugin.inst().wrapPlayer(p), BukkitAdapter.adapt(l.getWorld()));
    }
	
}
