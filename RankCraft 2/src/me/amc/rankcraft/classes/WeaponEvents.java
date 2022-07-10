package me.amc.rankcraft.classes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.RCUtils;

public class WeaponEvents implements Listener {

	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if(RCUtils.isWorldEnabled(p.getWorld())) {
		
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			//	if(e.getClickedBlock().getType() == Material.DIAMOND_BLOCK) {
				if(e.getClickedBlock().getType() == Material.getMaterial(MainCore.instance.config.classItemBlock)) {
					if(p.getInventory().getItemInMainHand() != null) {
						ItemStack item = p.getInventory().getItemInMainHand();
						
						List<ItemStack> specialItemStacks = new ArrayList<>();
						
						/*
						for(RpgItem ri : MainCore.instance.rankCraft.specialItems) {
							specialItemStacks.add(ri.getItem());
						}
						*/
						
						if(!specialItemStacks.contains(item)) {
						
							if(RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.gladiatorClass.getId())) {
								
								if(item.getType() == Material.WOODEN_SWORD || item.getType() == Material.GOLDEN_SWORD || item.getType() == Material.STONE_SWORD
										|| item.getType() == Material.IRON_SWORD || item.getType() == Material.DIAMOND_SWORD) {
								
									CustomItem cItem = new CustomItem(item);
									cItem.removeIfStart(ChatColor.DARK_GRAY+"Class: ");
									cItem.addLores(ChatColor.DARK_GRAY+"Class: gladiator");
									cItem.enchant(Enchantment.DAMAGE_ALL, MainCore.instance.rankCraft.gladiatorClass.getPotionEffect(RpgClassData.getGladiatorLevel(p)).getAmplifier()+1);
									cItem.build();
									
									//p.setItemOnCursor(cItem.getItem());
									p.getInventory().setItemInMainHand(cItem.getItem());
								}
							}
							
							if(RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.archerClass.getId())) {
								if(item.getType() == Material.BOW) {
									
									CustomItem cItem = new CustomItem(item);
									cItem.removeIfStart(ChatColor.DARK_GRAY+"Class: ");
									cItem.addLores(ChatColor.DARK_GRAY+"Class: archer");
									cItem.enchant(Enchantment.ARROW_FIRE, MainCore.instance.rankCraft.archerClass.getPotionEffect(RpgClassData.getArcherLevel(p)).getAmplifier()+1);
									cItem.build();
									
									p.getInventory().setItemInMainHand(cItem.getItem());
								}
							}
						
							if(RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.ninjaClass.getId())) {
								if(item.getType() == Material.WOODEN_SWORD || item.getType() == Material.GOLDEN_SWORD || item.getType() == Material.STONE_SWORD
										|| item.getType() == Material.IRON_SWORD || item.getType() == Material.DIAMOND_SWORD) {
								
									CustomItem cItem = new CustomItem(item);
									cItem.removeIfStart(ChatColor.DARK_GRAY+"Class: ");
									cItem.addLores(ChatColor.DARK_GRAY+"Class: ninja");
									cItem.enchant(Enchantment.KNOCKBACK, MainCore.instance.rankCraft.ninjaClass.getPotionEffect(RpgClassData.getNinjaLevel(p)).getAmplifier()+1);
									cItem.build();
									
									p.getInventory().setItemInMainHand(cItem.getItem());
								
								}
							}
							
							if(RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.wizardClass.getId())) {
								if(item.getType() == Material.WOODEN_SWORD || item.getType() == Material.GOLDEN_SWORD || item.getType() == Material.STONE_SWORD
										|| item.getType() == Material.IRON_SWORD || item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.WOODEN_AXE
										|| item.getType() == Material.GOLDEN_AXE || item.getType() == Material.STONE_AXE || item.getType() == Material.IRON_AXE
										|| item.getType() == Material.DIAMOND_AXE) {
								
									CustomItem cItem = new CustomItem(item);
									cItem.removeIfStart(ChatColor.DARK_GRAY+"Class: ");
									cItem.addLores(ChatColor.DARK_GRAY+"Class: wizard");
									cItem.enchant(Enchantment.FIRE_ASPECT, MainCore.instance.rankCraft.wizardClass.getPotionEffect(RpgClassData.getWizardLevel(p)).getAmplifier()+1);
									cItem.build();
									
									p.getInventory().setItemInMainHand(cItem.getItem());
								
								}
							}
							
							if(RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.mysteriousClass.getId())) {
								
								int amplifier = MainCore.instance.rankCraft.mysteriousClass.getSpecialEffects(RpgClassData.getMysteriousLevel(p)).get(0).getAmplifier();
								
								if(item.getType() == Material.WOODEN_SWORD || item.getType() == Material.GOLDEN_SWORD || item.getType() == Material.STONE_SWORD
										|| item.getType() == Material.IRON_SWORD || item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.WOODEN_AXE
										|| item.getType() == Material.GOLDEN_AXE || item.getType() == Material.STONE_AXE || item.getType() == Material.IRON_AXE
										|| item.getType() == Material.DIAMOND_AXE) {
								
									CustomItem cItem = new CustomItem(item);
									cItem.removeIfStart(ChatColor.DARK_GRAY+"Class: ");
									cItem.addLores(ChatColor.DARK_GRAY+"Class: mysterious");
									cItem.enchant(Enchantment.FIRE_ASPECT, amplifier+1);
									cItem.enchant(Enchantment.DAMAGE_ALL, amplifier+1);
									cItem.enchant(Enchantment.KNOCKBACK, amplifier+1);
									cItem.build();
									
									p.getInventory().setItemInMainHand(cItem.getItem());
								
								}
								
								if(item.getType() == Material.BOW) {
									
									CustomItem cItem = new CustomItem(item);
									cItem.removeIfStart(ChatColor.DARK_GRAY+"Class:");
									cItem.addLores(ChatColor.DARK_GRAY+"Class: mysterious");
									cItem.enchant(Enchantment.ARROW_FIRE,amplifier+1);
									cItem.build();
									
									p.getInventory().setItemInMainHand(cItem.getItem());
								}
							}
						}
					}
				}
			}
		
		}
	}
	
}
