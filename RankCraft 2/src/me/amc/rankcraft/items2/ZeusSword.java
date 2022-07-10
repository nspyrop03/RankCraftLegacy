package me.amc.rankcraft.items2;

import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

public class ZeusSword extends SpecialItem {

	private final static String NAME = ChatColor.GOLD+""+ChatColor.BOLD+"Zeus Sword";

	private static int delay = 5;
	private static int maxFuel = 0;
	private static int maxDurability = 300;
	
	public static final String DESCRIPTION[] = {ChatColor.AQUA+""+ChatColor.ITALIC+"A sword worthy of Zeus!",
			ChatColor.YELLOW+""+ChatColor.ITALIC+"Right-Click"+ChatColor.AQUA+""+ChatColor.ITALIC+" to strike a lightning!"};
	
	public ZeusSword() {
		super(Material.IRON_SWORD, NAME, delay, maxFuel, maxDurability, ZeusSword.class.getName(), false);
		
		this.setMinDamage(30.0);
		this.setMaxDamage(50.0);
		this.setCriticalLuck(60);
		this.setLevelToUse(40);
		this.setRarity(Rarity.LEGENDARY);
		this.addDescriptionLore(DESCRIPTION);
		this.build();
		
	}

	public ZeusSword(RpgItem item) {
		super(item);
					
		this.setBasicStats(delay, maxFuel, maxDurability, false);
	}
	
}

class ZeusSwordEvent implements Listener {
	
	public ZeusSwordEvent() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}
	
	@EventHandler
	public void onUse(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();
		
		if(event.getHand() == EquipmentSlot.OFF_HAND) return;
		
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(!item.hasItemMeta()) return;
		if(!item.getItemMeta().getPersistentDataContainer().has(SpecialItem.SPECIAL_ITEM_KEY, PersistentDataType.STRING)) return;
		
		if(item.getItemMeta().getPersistentDataContainer().get(SpecialItem.SPECIAL_ITEM_KEY, PersistentDataType.STRING).equals(ZeusSword.class.getName())) {
			
			ZeusSword sword = new ZeusSword(RCUtils.getRpgItemFromNormal(item, false));
			
			if(sword.use()) {
				
				Set<Material> mats = null;
				Block b = p.getTargetBlock(mats, 100);
						
				int times = (new Random()).nextInt(5)+1;
						
				for(int i = 0; i < times; i++)
					b.getWorld().strikeLightning(b.getLocation());
						
				sword.updateItemInHand(p, ZeusSword.DESCRIPTION);
				
			} else {
				p.sendMessage(MainCore.instance.language.getWaitForAbility(sword.getDelay()-sword.getSecondsBetweenNow()));
			}
			
		}
	}
	
}
