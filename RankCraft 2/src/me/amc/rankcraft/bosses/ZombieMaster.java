package me.amc.rankcraft.bosses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Chicken;
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

public class ZombieMaster extends BasicBoss implements Listener {

	private boolean placed_egg = false;
	
	public ZombieMaster() {
		super(ChatColor.DARK_GREEN+"Zombie"+ChatColor.GOLD+"Master");
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		
		Block b = e.getBlockPlaced();
		
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		ItemMeta meta = helmet.getItemMeta();
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		helmet.setItemMeta(meta);
		
		ItemStack armor = new ItemStack(Material.DIAMOND_CHESTPLATE);
		meta = armor.getItemMeta();
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		armor.setItemMeta(meta);
		
		ItemStack axe = new ItemStack(Material.IRON_AXE);
		meta = axe.getItemMeta();
		meta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
		axe.setItemMeta(meta);
		
		if(placed_egg) {
			
			b.breakNaturally(new ItemStack(Material.AIR));
			
			Zombie z = (Zombie) e.getPlayer().getWorld().spawnEntity(b.getLocation(), EntityType.ZOMBIE);
			
			//z.setMaxHealth(800);
			z.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(800);
			z.setHealth(800);
			
			z.setCustomName(this.getName()+" §4§lHP: "+z.getHealth());
			z.setCustomNameVisible(true);
			
			z.getEquipment().setHelmet(helmet);
			z.getEquipment().setChestplate(armor);
			//z.getEquipment().setItemInHand(axe);
			z.getEquipment().setItemInMainHand(axe);
			
			placed_egg = false;
			
		}
		
	}
	
//	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			Player p = e.getPlayer();
			ItemStack hand = p.getInventory().getItemInMainHand();
			
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
					
				//	Bukkit.broadcastMessage("§4§l"+p.getName()+"§e has killed a "+this.getName()+" §2§lBoss");
					Bukkit.broadcastMessage(MainCore.instance.language.getKillBoss(p, this.getName()));
					stats.addXp(p, 3000);
					
				//	p.sendMessage("§eYou have earned 3000 xp for killing "+this.getName()+" §2§lBoss");
					p.sendMessage(MainCore.instance.language.getEarnBossXp(p, this.getName(), 3000));
					
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		
		if(e.getEntity() instanceof Zombie) {
			Zombie z = (Zombie) e.getEntity();
			
			if(z.getCustomName() == null) return;
			
			if(z.getCustomName().startsWith(this.getName())) {
				z.setCustomName(this.getName()+" §4§lHP: "+RCUtils.round(z.getHealth(), 2));
				z.setCustomNameVisible(true);
			}
		}
		
		if(e.getDamager() instanceof Zombie) {
			Zombie z = (Zombie) e.getDamager();
			
			if(z.getCustomName() == null) return;
			
			if(z.getCustomName().startsWith(this.getName())) {
				
				Random r = new Random();
				
				int chances = r.nextInt(101);
				
				// Special moves
				
				if(chances <= 20) {
					// Spawn Chickens with Baby Zombies (ZombieSpawnMove)
					
					List<Zombie> zombies = new ArrayList<Zombie>();
					List<Chicken> chickens = new ArrayList<Chicken>();

					World w = z.getWorld();
					Location loc = z.getLocation();
					
					for(int i = 0; i < 4; i++) {
						Zombie m = (Zombie) w.spawnEntity(loc, EntityType.ZOMBIE);
						m.setBaby(true);
						zombies.add(m);
						Chicken c = (Chicken) w.spawnEntity(loc, EntityType.CHICKEN);
						chickens.add(c);
						
					}					
					
					for(int i = 0; i < zombies.size(); i++) {
						chickens.get(i).setPassenger(zombies.get(i));
					}
					
				//	Bukkit.broadcastMessage(this.getName()+" §eperformed the §2§lZombieSpawnMove §e!!!");
					Bukkit.broadcastMessage(MainCore.instance.language.getZombieMasterMove1());
					
				} else if(chances >= 21 && chances <= 31) {
					// Teleport Move
					
					World w = z.getWorld();
					
					double x = e.getEntity().getLocation().getX();
					double y = e.getEntity().getLocation().getY();
					double zet = e.getEntity().getLocation().getZ();
					
					
					Location loc = new Location(w, x, y, zet-2);
					
					z.teleport(loc);
					
				//	Bukkit.broadcastMessage(this.getName()+" §eperformed the §5§lTeleportMove §e!!!");
					Bukkit.broadcastMessage(MainCore.instance.language.getZombieMasterMove2());
					
				}
				
			}
		}
	}
}
