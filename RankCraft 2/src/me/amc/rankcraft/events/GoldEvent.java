package me.amc.rankcraft.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.stats.Gold;
import me.amc.rankcraft.utils.RCUtils;
import net.md_5.bungee.api.ChatColor;

public class GoldEvent implements Listener {

	private Gold g;
	
	public GoldEvent() {
		g = MainCore.instance.rankCraft.gold;
		
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}
	
//	@SuppressWarnings("deprecation")
	//@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		ItemStack is = p.getInventory().getItemInMainHand();//p.getItemOnCursor();
		//System.out.println(is.toString());
		
		
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(!is.hasItemMeta()) return;
			if(!is.getItemMeta().hasDisplayName()) return;
			//if(!(is.getType() == Material.GOLD_NUGGET)) return;
			if(!(is.getType() == MainCore.instance.config.currencyItemType)) return;
			
			String name = ChatColor.stripColor(is.getItemMeta().getDisplayName());
			//String gold = ChatColor.stripColor("Gold");
			String gold = ChatColor.stripColor(MainCore.instance.config.currencyName);
			
			if(RCUtils.isPlayerWorldEnabled(p)) {
				if(name.startsWith(gold)) {
					//System.out.println("starts with: true");
					g.addGold(p, g.getGoldFromItem(is));
					RCUtils.removeItemFromHand(p);
				}
				MainCore.instance.scoreboard.updateBoard(p);
			}
		}
		
	}
	
	@EventHandler
	public void onPickup(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			ItemStack it = e.getItem().getItemStack();
			//if(it.getType() == Material.GOLD_NUGGET) {
			if(it.getType() == MainCore.instance.config.currencyItemType) {
				if(it.hasItemMeta()) {
					if(it.getItemMeta().hasDisplayName()) {
						String name = ChatColor.stripColor(it.getItemMeta().getDisplayName());
						//String gold = ChatColor.stripColor("Gold");
						String gold = ChatColor.stripColor(MainCore.instance.config.currencyName);
						if(name.startsWith(gold) && MainCore.instance.config.autoPickupGold 
								&& p.hasPermission(MainCore.instance.permList.autoPickupGold_permission)
								&& RCUtils.isPlayerWorldEnabled(p)) {
							e.setCancelled(true);
							g.addGold(p, g.getGoldFromItem(it));
							p.sendMessage(MainCore.instance.language.getPickupGoldMessage(g.getGoldFromItem(it)));
							e.getItem().remove();
							MainCore.instance.scoreboard.updateBoard(p); 
						}
					}
				}
			}
			
		}
	}
}
