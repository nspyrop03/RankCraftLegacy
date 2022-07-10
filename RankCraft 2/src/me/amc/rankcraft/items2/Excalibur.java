package me.amc.rankcraft.items2;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.rpgitem.Rarity;
import me.amc.rankcraft.rpgitem.RpgItem;
import me.amc.rankcraft.utils.RCUtils;

public class Excalibur extends SpecialItem {

	private final static String NAME = ChatColor.BLUE+""+ChatColor.BOLD+"Excalibur";

	private static int delay = 5;
	private static int maxFuel = 0;
	private static int maxDurability = 200;
	
	public Excalibur() {
		super(Material.DIAMOND_SWORD, NAME, delay, maxFuel, maxDurability, Excalibur.class.getName(), false);
		
		this.setMinDamage(8.0);
		this.setMaxDamage(12.0);
		this.setCriticalLuck(30);
		this.enchant(Enchantment.DAMAGE_ALL, 5);
		this.setLevelToUse(20);
		this.setRarity(Rarity.EPIC);
		this.build();
		
	}

	//@SuppressWarnings("unchecked")
	public Excalibur(RpgItem item) {
		super(item);
					
		this.setBasicStats(delay, maxFuel, maxDurability, false);
		//this.build();
	}
	
}

class ExcaliburEvent implements Listener {
	
	public ExcaliburEvent() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();
		
		if(event.getHand() == EquipmentSlot.OFF_HAND) return;
		
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(!item.hasItemMeta()) return;
		if(!item.getItemMeta().getPersistentDataContainer().has(SpecialItem.SPECIAL_ITEM_KEY, PersistentDataType.STRING)) return;
		
		if(item.getItemMeta().getPersistentDataContainer().get(SpecialItem.SPECIAL_ITEM_KEY, PersistentDataType.STRING).equals(Excalibur.class.getName())) {
			//System.out.println("Found excalibur");
			
			Excalibur sword = new Excalibur(RCUtils.getRpgItemFromNormal(item, false));
			
			//p.sendMessage("Basic stats of excalibur: "+sword.getDelay()+" "+sword.getMaxDurability()+" "+sword.getMaxFuel());
			//System.out.println(sword.getSecondsBetweenNow());
			
			//System.out.println(item.getItemMeta().getPersistentDataContainer().has(SpecialItem.DURABILITY_KEY, PersistentDataType.INTEGER)
			//		+" vs "+sword.getMeta().getPersistentDataContainer().has(SpecialItem.DURABILITY_KEY, PersistentDataType.INTEGER));
			
			if(sword.use()) {
				Arrow a = (Arrow) p.launchProjectile(Arrow.class);
				a.setFireTicks(20*60);
				
				sword.updateItemInHand(p, null);
			} else {
				p.sendMessage(MainCore.instance.language.getWaitForAbility(sword.getDelay()-sword.getSecondsBetweenNow()));
			}
			
		}
		
	}
	
}