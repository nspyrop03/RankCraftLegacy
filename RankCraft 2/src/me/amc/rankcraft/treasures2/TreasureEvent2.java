package me.amc.rankcraft.treasures2;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.customevents.PlayerFindTreasureEvent;
import me.amc.rankcraft.lang.Language;
import me.amc.rankcraft.utils.RCUtils;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

public class TreasureEvent2 implements Listener {
	
	Language lang = MainCore.instance.language;

	public TreasureEvent2() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}
	
	@EventHandler
	public void onBreakBlock(BlockBreakEvent e) {
		
		if(MainCore.instance.config.enableTreasureChest && !MainCore.instance.rankCraft.blockStatsEvents.isInSavedBlocks(e.getPlayer(), e.getBlock())) {
			
			if(!RCUtils.isWorldEnabled(e.getBlock().getWorld()))
				return;
			
			Player p = e.getPlayer();
			Block block = e.getBlock();
			Random r = new Random();
			
			for(int i = 0; i < MainCore.instance.config.noTreasureMaterials.size(); i++) {
				if(block.getType().equals(MainCore.instance.config.noTreasureMaterials.get(i))) return;
			}
			List<TreasureChest2> common = MainCore.instance.rankCraft.getCommonTreasures();
			List<TreasureChest2> rare = MainCore.instance.rankCraft.getRareTreasures();
			List<TreasureChest2> epic = MainCore.instance.rankCraft.getEpicTreasures();
			
			if(MainCore.instance.getWorldGuard() != null && MainCore.instance.getWorldEdit() != null) {
				if(!canBuild(p, e.getBlock().getLocation())) {
					return;
				}
			}
			if(Bukkit.getPluginManager().isPluginEnabled("GriefPrevention")) {
				String noBuildReason = GriefPrevention.instance.allowBreak(p, block, block.getLocation());
				if(noBuildReason != null) {
					return;
				}
			}
			
			if(e.getBlock().getType() == Material.AIR) { // Fix bug with RandomTeleport plugin
				//System.out.println("Ok??");
				return;
			}
			
			int k = r.nextInt(MainCore.instance.config.treasure2PerBlocks+1);
			
			int n = MainCore.instance.config.commonChances;
			int s = MainCore.instance.config.rareChances;
			int u = MainCore.instance.config.epicChances;
			
			boolean sendM = MainCore.instance.config.treasure2FoundMessage;
			//if(r.nextInt(101) <= 50) {
				
			//	int ch = r.nextInt(101);
				
				//if(ch <= 60) {
			//p.sendMessage(k+"§0/§a"+n+"§0/§1"+s+"§0/§6"+u);
			
			if (k <= n) {
					// Common treasure
				if(common.size() > 0) {
					TreasureChest2 tc2 = common.get(r.nextInt(common.size()));
					tc2.giveToPlayer(p, 1);
						//p.sendMessage("Common Treasure!");
					
					PlayerFindTreasureEvent event = new PlayerFindTreasureEvent(p, tc2);
					Bukkit.getServer().getPluginManager().callEvent(event);
					
					if(sendM) p.sendMessage(lang.getTreasures2FoundTreasure(tc2.getName()));
						
				}
				//} else if(ch >= 61 && ch <= 90) {
			} else if (k >= n + 1 && k <= n + s) {
					// Rare treasure
				if(rare.size() > 0) {
					TreasureChest2 tc2 = rare.get(r.nextInt(rare.size()));
					tc2.giveToPlayer(p, 1);
						//p.sendMessage("Rare Treasure!");
					PlayerFindTreasureEvent event = new PlayerFindTreasureEvent(p, tc2);
					Bukkit.getServer().getPluginManager().callEvent(event);
					if(sendM) p.sendMessage(lang.getTreasures2FoundTreasure(tc2.getName()));
				}
				//} else {
			} else if (k >= n + s + 1 && k <= n + s + u) {
					// Epic Treasure
				if(epic.size() > 0) {
					TreasureChest2 tc2 = epic.get(r.nextInt(epic.size()));
					tc2.giveToPlayer(p, 1);
						//p.sendMessage("Epic Treasure!");
					PlayerFindTreasureEvent event = new PlayerFindTreasureEvent(p, tc2);
					Bukkit.getServer().getPluginManager().callEvent(event);
					if(sendM) p.sendMessage(lang.getTreasures2FoundTreasure(tc2.getName()));
				}
			}
				
			//System.out.println("Block event for treasure!");
				
			//}
			
			
		}
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {

		Player p = e.getPlayer();
		ItemStack is = p.getInventory().getItemInMainHand();//p.getItemOnCursor();

		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if (!(is.getType() == Material.CHEST))
				return;
			if (!is.hasItemMeta())
				return;
			if (!is.getItemMeta().hasDisplayName())
				return;
			/*
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if(isTreasureChest(is)) {
					e.setCancelled(true);
				}
			}
			*/
			if(isTreasureChest(is)) {
				
				TreasureChest2 tc2 = MainCore.instance.rankCraft.getTreasureByItem(is);
				tc2.openForPlayer(p);
				
				e.setCancelled(true);
			}
		}
	}
	
	
	private boolean isTreasureChest(ItemStack item) {
		for(TreasureChest2 tc2 : MainCore.instance.rankCraft.treasureChests) {
			if(item.isSimilar(tc2.build().getItem())) {
				return true;
			}
		}
		return false;
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
