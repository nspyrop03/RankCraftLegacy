package me.amc.rankcraft.rpgitem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.damage.DamageSystem;

public class ArmorCheckEvents implements Listener {
	
	public ArmorCheckEvents() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}
	
	@EventHandler
	public void playerClickInventory(InventoryClickEvent e) {
		//System.out.println("click event");
		if(e.getClickedInventory() != null) {
			if(e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
				/*
				if(e.getCurrentItem()==null || e.getCurrentItem().getType()==Material.AIR) {
					return;
				}
				*/
				//e.getWhoClicked().sendMessage("You clicked your inventory!");
				
				if(e.getWhoClicked() instanceof Player) {
					Player p = (Player) e.getWhoClicked();
					
					for(int i = 0; i <= 45; i++) {
						ItemStack item = e.getClickedInventory().getItem(i);
						if(item == null) continue;
						if(DamageSystem.isArmorPiece(item)) {
							//p.sendMessage("Armor piece at "+i+" - "+e.getRawSlot());
							if(!DamageSystem.isRpgArmor(item)) {
								RpgArmor a = DamageSystem.getRpgArmorFromDefault(item);
								p.getInventory().setItem(i, a.build().getItem());
							}
						}
					}
					/* Failure 1
					ItemStack clicked = e.getCurrentItem();
					if(DamageSystem.isArmorPiece(clicked) && DamageSystem.isRpgArmor(clicked)) {
						//p.sendMessage("Clicked "+clicked.getType()+" "+DamageSystem.isPlayerLevelForRpgArmor(p, clicked)+" "+MainCore.instance.rankCraft.stats.getLevel(p));
						
						if(!DamageSystem.isPlayerLevelForRpgArmor(p, clicked)) {
							p.sendMessage("You cannot use this armor piece!");
							//p.setItemOnCursor(new ItemStack(Material.AIR));
							e.setCancelled(true);
							System.out.println(p.getItemOnCursor().getType());
							//return;
						}
					}
					*/
					/* Failure 2 ... I hate this minecraft system
					System.out.println(p.getItemOnCursor().getType());
					if(p.getItemOnCursor() != null) {
						ItemStack cursor = p.getItemOnCursor();
						//System.out.println(cursor.getType());
						if(DamageSystem.isArmorPiece(cursor) && DamageSystem.isRpgArmor(cursor)) {
							
							if(e.getRawSlot() >= 5 && e.getRawSlot() <= 8) {
								if(!DamageSystem.isPlayerLevelForRpgArmor(p, cursor)) {
									p.sendMessage("You cannot wear this armor piece!");
									e.setCancelled(true);
								}
							}
						}
					}
					*/
					
					BukkitRunnable bRunnable = new BukkitRunnable() {
						@Override
						public void run() {
							insideRun(p);
						}
					};
					
					BukkitRunnable afterSlotCheck = new BukkitRunnable() {
						@Override
						public void run() {
							insideRun(p);
						}
					};
					
					// 3rd time... let's see if it works now
					
					//if(e.getRawSlot() >= 5 && e.getRawSlot() <= 8) {
					if(e.getSlotType() == SlotType.ARMOR) {
						//System.out.println("ARMOR SLOT?");
						
						// Wait 1 tick and then check for armors
						// bRunnable.cancel();
						try {
							
							afterSlotCheck.runTaskLater(MainCore.instance, 2L);
							//System.out.println("here");
						} catch(IllegalStateException ex) {
							insideRun(p);
							//System.out.println("there");
						}
					}
					
					if(e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
						//if(!bRunnable.isCancelled()) bRunnable.cancel();
						try {
							bRunnable.runTaskLater(MainCore.instance, 3L);
						} catch(IllegalStateException ex) {
							insideRun(p);
						}
					}
					
				}
			}
		}
	}
	
	private void insideRun(Player p) {
		for(int i = 36; i <= 39; i++) {
			ItemStack armor = p.getInventory().getItem(i);
			
			if(armor != null) {
				//System.out.println(armor.getType());
				if(DamageSystem.isArmorPiece(armor) && DamageSystem.isRpgArmor(armor)) {
					if(!DamageSystem.isPlayerLevelForRpgArmor(p, armor)) {
						//p.sendMessage("You cannot wear this armor piece!");
						p.sendMessage(MainCore.instance.language.getCannotWearArmorMessage());
						p.getInventory().setItem(i, new ItemStack(Material.AIR));
						p.getInventory().addItem(armor);
						
					}
				}
			} 
		}
	}
	
	@EventHandler
	public void playerRightClickArmor(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() != Material.AIR) {
				
				ItemStack item = p.getInventory().getItemInMainHand();
				if(DamageSystem.isArmorPiece(item) && DamageSystem.isRpgArmor(item)) {
					if(!DamageSystem.isPlayerLevelForRpgArmor(p, item)) {
						//p.sendMessage("You cannot wear this armor piece!");
						p.sendMessage(MainCore.instance.language.getCannotWearArmorMessage());
						e.setCancelled(true);
						return;
					}
				}
				
				
			}
		}
	}
	

}
