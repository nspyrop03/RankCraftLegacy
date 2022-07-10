package me.amc.rankcraft.bosses;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.stats.Stats;
import me.amc.rankcraft.utils.RCUtils;

public class ZombieWarrior extends BasicBoss implements Listener {

	private boolean placed_egg = false;
	
	public ZombieWarrior() {
		super(ChatColor.DARK_GREEN+"Zombie"+ChatColor.DARK_RED+"Warrior");
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Block b = e.getBlockPlaced();
	
		ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
		ItemMeta meta = helmet.getItemMeta();
		
		meta.addEnchant(Enchantment.DURABILITY, 100, true);
		helmet.setItemMeta(meta);
		
		ItemStack axe = new ItemStack(Material.DIAMOND_AXE);
		meta= axe.getItemMeta();
		meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		axe.setItemMeta(meta);
		
		ItemStack boots = new ItemStack(Material.GOLDEN_BOOTS);
		meta = boots.getItemMeta();
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
		
		boots.setItemMeta(meta);
		
		if(placed_egg) {
				b.breakNaturally(new ItemStack(Material.AIR));
				
				Zombie z = (Zombie) e.getPlayer().getWorld().spawnEntity(b.getLocation(), EntityType.ZOMBIE);
				
				
				z.getEquipment().setHelmet(helmet);
				//z.getEquipment().setItemInHand(axe);
				z.getEquipment().setItemInMainHand(axe);
				z.getEquipment().setBoots(boots);
				
				//z.getEquipment().setItemInHandDropChance(5);
				z.getEquipment().setItemInMainHandDropChance(0.5f);
				
				//z.setMaxHealth(1000);
				z.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1000);
				z.setHealth(1000);
				
				z.setCustomName(this.getName()+ChatColor.RED+ChatColor.BOLD+" HP: "+RCUtils.round(z.getHealth(), 2));
				z.setCustomNameVisible(true);
		//		e.getPlayer().sendMessage("ok3");

				placed_egg = false;

		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Zombie) {
			Zombie z = (Zombie) e.getEntity();
			if(z.getCustomName() == null) return;
			if(z.getCustomName().startsWith(this.getName())) {
				z.setCustomName(this.getName()+ChatColor.RED+ChatColor.BOLD+" HP: "+RCUtils.round(z.getHealth(), 2));
				z.setCustomNameVisible(true);
			}
				
			
		}
	}
	
//	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
			
			if(hand.getType() == this.getEgg().getType()) {
				if(!hand.hasItemMeta()) return;
				
				if(hand.getItemMeta().getDisplayName().equals(this.getEgg().getName())) {
					placed_egg = true;
				}
			}
		}
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if(e.getEntity() instanceof Zombie) {
			Zombie z = (Zombie) e.getEntity();
			if(z.getCustomName() == null) return;
			if(z.getCustomName().startsWith(this.getName())) {
				Firework f = (Firework) z.getWorld().spawn(z.getLocation(), Firework.class);
				
				FireworkMeta fm = f.getFireworkMeta();
				
				fm.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(Type.STAR).withColor(Color.RED).withFade(Color.BLUE).build());
				fm.setPower(1);
				
				f.setFireworkMeta(fm);
				
				if(z.getKiller() instanceof Player) {
					Player p = (Player) z.getKiller();
					Stats stats = MainCore.instance.rankCraft.stats;
					
					
				//	Bukkit.broadcastMessage("§4§l"+p.getName()+"§e has killed a "+this.getName()+" Boss");
					Bukkit.broadcastMessage(MainCore.instance.language.getKillBoss(p, this.getName()));
					stats.addXp(p, 1000);
					
				//	p.sendMessage("§eYou have earned 1000 xp for killing "+this.getName()+" Boss");
					p.sendMessage(MainCore.instance.language.getEarnBossXp(p, this.getName(), 1000));
				}
			}
		}
	}
	
}
