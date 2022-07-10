package me.amc.rankcraft.items2;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class IceBattleAxeEvent implements Listener {

	public HashMap<Integer, String> balls;
	
	public IceBattleAxeEvent() {
		balls = new HashMap<>();
		
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
		
		if(item.getItemMeta().getPersistentDataContainer().get(SpecialItem.SPECIAL_ITEM_KEY, PersistentDataType.STRING).equals(IceBattleAxe.class.getName())) {
			
			IceBattleAxe axe = new IceBattleAxe(RCUtils.getRpgItemFromNormal(item, false));
			
			if(axe.use()) {
				
				Snowball a = (Snowball) p.launchProjectile(Snowball.class);
				balls.put(a.getEntityId(), a.getWorld().getName());
				
				axe.updateItemInHand(p, IceBattleAxe.DESCRIPTION);
				
			} else {
				p.sendMessage(MainCore.instance.language.getWaitForAbility(axe.getDelay()-axe.getSecondsBetweenNow()));
			}
			
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSnowballHit(ProjectileHitEvent event) {
		if(event.getEntity() instanceof Snowball) {
			int entityId = event.getEntity().getEntityId();
			
			if(balls.containsKey((Integer) entityId)) {
				
				balls.remove((Integer) entityId);
				
				Location loc = event.getEntity().getLocation();
				Block b1 = loc.getWorld().getBlockAt(loc);
				Block b2 = b1.getRelative(BlockFace.WEST);
				Block b3 = b1.getRelative(BlockFace.EAST);
				Block b4 = b1.getRelative(BlockFace.NORTH);
				Block b5 = b1.getRelative(BlockFace.SOUTH);
				Block b6 = b1.getRelative(BlockFace.UP);
				Block[] blocks = {b1,b2,b3,b4,b5,b6};
				
				for(Block b : blocks) 
					b.getWorld().spawnFallingBlock(b.getLocation(), Material.ICE, (byte) 0);
				
			}
		}
	}
	
}
