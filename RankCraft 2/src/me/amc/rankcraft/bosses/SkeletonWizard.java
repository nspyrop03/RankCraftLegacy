package me.amc.rankcraft.bosses;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
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
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.mobs.EntityInventory;
import me.amc.rankcraft.stats.Stats;
import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.RCUtils;

public class SkeletonWizard extends BasicBoss implements Listener  {

	private boolean placed_egg = false;
	
	public SkeletonWizard() {
		super(ChatColor.GRAY+"Skeleton"+ChatColor.LIGHT_PURPLE+"Wizard");
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		
		Block b = e.getBlockPlaced();
		
		ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
		ItemMeta meta = helmet.getItemMeta();
		meta.addEnchant(Enchantment.DURABILITY, 10, true);
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
		helmet.setItemMeta(meta);
		
		ItemStack armor = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta lmeta = (LeatherArmorMeta) armor.getItemMeta();
		lmeta.setColor(Color.fromRGB(0, 0, 0));
		lmeta.addEnchant(Enchantment.DURABILITY, 10, true);
		lmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
		armor.setItemMeta(lmeta);
		
		ItemStack wizard_rod = new ItemStack(Material.STICK);
		meta = wizard_rod.getItemMeta();
		meta.addEnchant(Enchantment.DAMAGE_ALL, 15, true);
		meta.addEnchant(Enchantment.KNOCKBACK, 5, true);
		meta.setDisplayName("§5Wizard §6Stick");
		wizard_rod.setItemMeta(meta);
		
		if(placed_egg) {
			b.breakNaturally(new ItemStack(Material.AIR));
			
			Skeleton s = (Skeleton) b.getWorld().spawnEntity(b.getLocation(), EntityType.SKELETON);
			EntityInventory inv = new EntityInventory(s);
			
			inv.setChestplate(new CustomItem(armor).build());
			inv.setHelmet(new CustomItem(helmet).build());
			inv.setHandItem(new CustomItem(wizard_rod).build());
			
			//s.setMaxHealth(1500);
			s.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1500);
			s.setHealth(1500);
			
			s.setCustomName(this.getName()+" §4§lHP: "+s.getHealth());
			s.setCustomNameVisible(true);
			
			inv.addInventoryToEntity();
			
			placed_egg = false;
			
		}
		
	}
	
//	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = e.getPlayer();
			ItemStack hand = p.getInventory().getItemInMainHand();			
			
	//		System.out.println("truew0");
			if(hand.getType() == this.getEgg().getType()) {
				if(!hand.hasItemMeta()) return;
				
	//			System.out.println("truew1");
				if(hand.getItemMeta().getDisplayName().equals(this.getEgg().getName())) {
	//				System.out.println("truew2");
					placed_egg = true;
				}
			}
		}
	}
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if(e.getEntity() instanceof Skeleton) {
			Skeleton s = (Skeleton) e.getEntity();
			
			if(s.getCustomName() == null) return;
			
			if(s.getCustomName().startsWith(this.getName())) {
				Firework f = (Firework) s.getWorld().spawn(s.getLocation(), Firework.class);
				
				FireworkMeta fm = f.getFireworkMeta();
				
				fm.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(Type.STAR).withColor(Color.RED).withFade(Color.BLUE).build());
				fm.setPower(1);
				
				f.setFireworkMeta(fm);
				
				if(s.getKiller() instanceof Player) {
					Player p = (Player) s.getKiller();
					Stats stats = MainCore.instance.rankCraft.stats;
					
				//	Bukkit.broadcastMessage("§4§l"+p.getName()+"§e has killed a "+this.getName()+" §2§lBoss");
					Bukkit.broadcastMessage(MainCore.instance.language.getKillBoss(p, this.getName()));
					stats.addXp(p, 1500);
				//	p.sendMessage("§eYou have earned 1500 xp for killing "+this.getName()+" §2§lBoss");
					p.sendMessage(MainCore.instance.language.getEarnBossXp(p, this.getName(), 1500));
				}
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Skeleton) {
			Skeleton s = (Skeleton) e.getEntity();
			
			if(s.getCustomName() == null) return;
			
			if(s.getCustomName().startsWith(this.getName())) {
				s.setCustomName(this.getName()+" §4§lHP: "+RCUtils.round(s.getHealth(), 2));
				s.setCustomNameVisible(true);
			}
		}
		
		if(e.getDamager() instanceof Skeleton) {
			Skeleton s = (Skeleton) e.getDamager();
			
			if(s.getCustomName() == null) return;
			
			if(s.getCustomName().startsWith(this.getName())) {
				
				Random r = new Random();
				
				int chances = r.nextInt(101);
				
				// Special moves
				
				if(chances <= 15) {
					// Strike Lightning Move
					World w = e.getEntity().getWorld();
					w.strikeLightning(e.getEntity().getLocation());
					
				//	Bukkit.broadcastMessage(this.getName()+" §2§lBoss §ehas performed §b§lThe Lightining Move §eon §4§l"+e.getEntity().getName());
					Bukkit.broadcastMessage(MainCore.instance.language.getSkeletonWizardMove1());
				} else if(chances >= 16 && chances <= 35) {
					// Fire Move
					e.getEntity().setFireTicks( (int) RCUtils.secondsToTicks(60) );
					
				//	Bukkit.broadcastMessage(this.getName()+" §2§lBoss §ehas performed §c§lThe Fire Move §eon §4§l"+e.getEntity().getName());
					Bukkit.broadcastMessage(MainCore.instance.language.getSkeletonWizardMove2());
				} else if(chances >= 36 && chances <= 45) {
					// Poison Move
					if(e.getEntity() instanceof LivingEntity) {
						LivingEntity l = (LivingEntity) e.getEntity();
						l.addPotionEffect(new PotionEffect(PotionEffectType.POISON, (int) RCUtils.secondsToTicks(120), 2, true));
						
				//		Bukkit.broadcastMessage(this.getName()+" §2§lBoss §ehas performed §2§lThe Poison Move §eon §4§l"+e.getEntity().getName());
						Bukkit.broadcastMessage(MainCore.instance.language.getSkeletonWizardMove3());
					}
				}
				
				
			}
		}
		
		
	}
}
