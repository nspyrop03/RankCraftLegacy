package me.amc.rankcraft.spells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.spells.SpellCase.CaseType;
import me.amc.rankcraft.utils.RCUtils;

public class SpellCaseEvent implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(!(e.getView().getTitle().equalsIgnoreCase(CaseType.Small.getName()) ||
				e.getView().getTitle().equalsIgnoreCase(CaseType.Medium.getName()) ||
				e.getView().getTitle().equalsIgnoreCase(CaseType.Large.getName()))) {
			return;
		}
		
		//System.out.println("calling inv click!");
		//Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
			return;
		}
		
		if(!RCUtils.isSpell(item)) {
			e.setCancelled(true);
		} 
		
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if(!(e.getView().getTitle().equalsIgnoreCase(CaseType.Small.getName()) ||
				e.getView().getTitle().equalsIgnoreCase(CaseType.Medium.getName()) ||
				e.getView().getTitle().equalsIgnoreCase(CaseType.Large.getName()))) {
			return;
		}
		
		//System.out.println("calling close inv!");
		Player p = (Player) e.getPlayer();
		
		List<String> newIDs = new ArrayList<>();
		for(int i = 0; i < e.getInventory().getContents().length; i++) {
			ItemStack item = e.getInventory().getContents()[i];
			if(RCUtils.isSpell(item)) {
				Spell s = RCUtils.getSpellFromItemStack(item);
				newIDs.add(s.getId()+RCUtils.getAmountForID(item.getAmount()));
			}
		}
		
		//SpellCase sc = new SpellCase(p.getItemInHand()); // May throw some errors:)
		SpellCase sc = new SpellCase(RCUtils.getCaseTypeFromItemStack(p.getInventory().getItemInMainHand()));
		sc.setDataFromList(newIDs);
		//sc.build();
		//p.setItemInHand(sc.getItem());
		//p.setItemOnCursor(sc.getItem());
		p.getInventory().setItemInMainHand(sc.getItem());
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() != Material.AIR) {
				if(p.getInventory().getItemInMainHand().getType() == Material.BOOK) {
					if(p.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
						if(p.getInventory().getItemInMainHand().getAmount() == 1) {
							SpellCase sc = new SpellCase(p.getInventory().getItemInMainHand());
							//sc.addData("00102");
							if(sc.getCaseType() != null) {
								p.openInventory(sc.getInv());
							}
						} else {
							p.sendMessage(MainCore.instance.language.getPrefix()+" Please use 1 spellcase per time!");
						}
					}
				}
			}
		}
	}
	
}
