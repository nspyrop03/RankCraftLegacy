package me.amc.rankcraft.damage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.sk89q.worldguard.session.handler.GodMode;

import me.amc.rankcraft.ConfigHelper;
import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.customevents.CriticalHitEvent;
import me.amc.rankcraft.rpgitem.Rarity;
import me.amc.rankcraft.rpgitem.RpgArmor;
import me.amc.rankcraft.rpgitem.RpgItem;
import me.amc.rankcraft.skills.AttackSkill;
import me.amc.rankcraft.skills.DefenseSkill;
import me.amc.rankcraft.utils.RCUtils;

public class DamageSystem implements Listener {

	public static List<DamageMessage> damageMsgs = new ArrayList<>();

	private static ConfigHelper config;
	public DamageSystem() {
		config = MainCore.instance.config;
	}
	
//	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		
		if(!MainCore.instance.config.enableDamageSystem) {
			//System.out.println("RankCraft DamageSystem is disabled!");
			//return;
			if (e.getEntity() instanceof Player) {
				Player p = (Player) e.getEntity();
				if(MainCore.instance.config.enableHPSystem) {
					e.setDamage(0);
					HPSystem hps = MainCore.instance.rankCraft.hpSystem;
					hps.removeHP(p, e.getDamage());
				} else {
					return;
				}
			}
		}
		//System.out.println("hello from above the if");
		
		
	
		if(RCUtils.isWorldEnabled(e.getEntity().getWorld())) {
			
			if (e.getDamager() instanceof Player) {
				Player p = (Player) e.getDamager();
				
				
				e.setDamage(0);
	
				HPSystem s = MainCore.instance.rankCraft.hpSystem;
	
				double weapon_damage;
				double critical_damage;
				double a_damage;
				double defense;
	
				double enchant_damage;
				
				double potion_damage;
	
				Random r = new Random();
	
				weapon_damage = getDamageFromRpgItem(p.getInventory().getItemInMainHand());
	
				enchant_damage = getEnchantPower(p.getInventory().getItemInMainHand());
	
				if (r.nextInt(101) <= getCriticalLuckFromRpgItem(p.getInventory().getItemInMainHand())) {
					//critical_damage = r.nextInt(5 - 3) + 3;
					critical_damage = MainCore.instance.config.minCriticalHit + (MainCore.instance.config.maxCriticalHit - MainCore.instance.config.minCriticalHit) * r.nextDouble();
					critical_damage = (critical_damage / 100.0) * weapon_damage;
					
					CriticalHitEvent event = new CriticalHitEvent(p, critical_damage, p.getInventory().getItemInMainHand(), e.getEntity());
					Bukkit.getServer().getPluginManager().callEvent(event);
				} else {
					critical_damage = 0;
				}
	
				AttackSkill a = MainCore.instance.rankCraft.attackSkill;
				//a_damage = a.getPoints(p) / 10.0;
				a_damage = a.getPoints(p) * MainCore.instance.config.attackSkillFactor;
				
				if (e.getEntity() instanceof Player) { // defender is player
					Player k = (Player) e.getEntity();
					DefenseSkill d = MainCore.instance.rankCraft.defenseSkill;
					//defense = d.getPoints(k) / 8.0;
					defense = d.getPoints(k) * MainCore.instance.config.defenseSkillFactor;
					defense += getDefenseFromArmor(k);
					
				} else { // defender is entity
					defense = 0;
					if(e.getEntity() instanceof LivingEntity) {
						LivingEntity le = (LivingEntity) e.getEntity();
						try {
							defense += getDefenseFromArmor(le);
						} catch(Exception ex) {
							defense = 0;
						}
					}
				}
	
				potion_damage = getPotionEffectStrength(p);
					
				double damage = weapon_damage + enchant_damage + critical_damage + a_damage + potion_damage - defense;
				/*
				p.sendMessage("Damage Analysis:");
				p.sendMessage("Weapon_damage + enchant_damage: "+weapon_damage+" + "+enchant_damage+" = "+(weapon_damage+enchant_damage));
				p.sendMessage("Critical_damage = "+critical_damage);
				p.sendMessage("Attack Skill Damage = "+a_damage);
				p.sendMessage("PotionDamage = "+potion_damage);
				p.sendMessage("Defense = "+defense);
				p.sendMessage("Damage = "+damage);
				p.sendMessage("----------------------");
				*/
				//System.out.println("Calculated damage: "+damage);			
				
				if(MainCore.instance.getWorldGuard() != null && MainCore.instance.getWorldEdit() != null) {
					RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
					RegionQuery query = container.createQuery();
					ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(e.getEntity().getLocation()));
					
					if(!set.testState(null, Flags.MOB_DAMAGE)) {
						//System.out.println("Maybe this");
						return;
					}
					
				}
				
				if(damage < 0) damage = 0.0;
				
				if (e.getEntity() instanceof Player) { // defender is player
					
					//System.out.println("This1");
					Player d = (Player) e.getEntity();
					
					// if defender has worldguard's god mode then do not damage
					if(MainCore.instance.getWorldGuard() != null && MainCore.instance.getWorldEdit() != null) {
						LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(d);
						if(WorldGuard.getInstance().getPlatform().getSessionManager().get(localPlayer).getHandler(GodMode.class).hasGodMode(localPlayer)) {
							return;
						}
					}
					
					//d.sendMessage("You're the defender");
					if(s.getHP(d) <= 0) {
						return;
					}
					/*
					if(d.getInventory().getHelmet()!=null) {
						if(d.getInventory().getHelmet().hasItemMeta()) {
							ItemMeta meta = d.getInventory().getHelmet().getItemMeta();
							if(meta instanceof Damageable) {
								Damageable dmeta = (Damageable) meta;
								dmeta.setDamage(10);
								d.getInventory().getHelmet().setItemMeta(meta);
							}
						}
					}
					*/
					if(MainCore.instance.config.enableHPSystem) {
						//d.sendMessage("Damage got: "+damage+" from "+e.getDamager());
						s.removeHP(d, RCUtils.round(damage, 1));
					} else {
						e.setDamage(RCUtils.round(damage, 1));
					}
				} else { //defender is entity
										
					if(e.getEntity() instanceof LivingEntity) {
						//System.out.println("This2");
						LivingEntity le = (LivingEntity) e.getEntity();
						if((le instanceof Zombie || le instanceof Skeleton) && areRCMobsOn()) {
							String yName = RCUtils.stripColor(le.getCustomName());
							double health;
							try {
								health = Double.parseDouble(yName.split("HP:")[1]);
							} catch(Exception ex) {
								health = 20.0;
							}
							health -= damage;
							health = RCUtils.round(health, 1);
							if(health > 0) le.setCustomName(le.getCustomName().split("HP:")[0]+ChatColor.RED+"HP: "+health);
							else le.setCustomName(le.getCustomName().split("HP:")[0]+ChatColor.RED+"Dead");
							if(health < 0) {
								//System.out.println("Zerooo");
								//Bukkit.getServer().getPluginManager().callEvent(new EntityDeathEvent(le, new ArrayList<ItemStack>()));
								e.setDamage(100000000);
							}
						} else {
							//System.out.println("This3");
							e.setDamage(RCUtils.round(damage, 1));							
						}
					} else {
						//System.out.println("This4");
						e.setDamage(RCUtils.round(damage, 1));
					}
					
				}
	
				if(damage > 0 && config.displayDamage) {
					Location loc = e.getEntity().getLocation().add(0, 2, 0);
					DamageMessage msg = new DamageMessage("" + RCUtils.round(damage, 1), loc);
					damageMsgs.add(msg);
				}
	
			} else if(e.getDamager() instanceof LivingEntity) { // if attacker is entity
				
				//Get damager as LivingEntity
				LivingEntity damager = (LivingEntity)e.getDamager();
				
				//Negate default damage;
				e.setDamage(0f);
				
				//Calculate new damage
				HPSystem s = MainCore.instance.rankCraft.hpSystem;
				
				double weapon_damage;
				double critical_damage;
				double defense;
				double enchant_damage;
				double potion_damage;
	
				Random r = new Random();
	
				weapon_damage = getDamageFromRpgItem(damager.getEquipment().getItemInMainHand());
				enchant_damage = getEnchantPower(damager.getEquipment().getItemInMainHand());
	
				if (r.nextInt(101) <= getCriticalLuckFromRpgItem(damager.getEquipment().getItemInMainHand())) {
					//critical_damage = r.nextInt(5 - 3) + 3;
					critical_damage = MainCore.instance.config.minCriticalHit + (MainCore.instance.config.maxCriticalHit - MainCore.instance.config.minCriticalHit) * r.nextDouble();
					critical_damage = (critical_damage / 100.0) * weapon_damage;
				} else {
					critical_damage = 0;
				}
	
				if (e.getEntity() instanceof Player) { // defender is player
					Player k = (Player) e.getEntity();
					DefenseSkill d = MainCore.instance.rankCraft.defenseSkill;
					//defense = d.getPoints(k) / 8.0;
					defense = d.getPoints(k) * MainCore.instance.config.defenseSkillFactor;
					defense += getDefenseFromArmor(k);
					
				} else { // defender is entity
					defense = 0;
					if(e.getEntity() instanceof LivingEntity) {
						LivingEntity le = (LivingEntity) e.getEntity();
						try {
							defense += getDefenseFromArmor(le);
						} catch(Exception ex) {
							defense = 0;
						}
					}
				}
	
				potion_damage = getPotionEffectFromLivingEntity(damager);
					
				double damage = weapon_damage + enchant_damage + critical_damage + potion_damage - defense;
				
				// If Mob_damage is disabled do not deal damage
				if(MainCore.instance.getWorldGuard() != null && MainCore.instance.getWorldEdit() != null) {
					RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
					RegionQuery query = container.createQuery();
					ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(e.getEntity().getLocation()));
					
					if(!set.testState(null, Flags.MOB_DAMAGE)) {
						return;
					}
				}
				
				// Fix negative damage
				if(damage < 0) damage = 0f;
				
				// Deal Damage
				if (e.getEntity() instanceof Player) { // defender is player
					Player d = (Player) e.getEntity();
					/*
					d.sendMessage("Damage Analysis:");
					d.sendMessage("Weapon_damage + enchant_damage: "+weapon_damage+" + "+enchant_damage+" = "+(weapon_damage+enchant_damage));
					d.sendMessage("Critical_damage = "+critical_damage);
					d.sendMessage("PotionDamage = "+potion_damage);
					d.sendMessage("Defense = "+defense);
					d.sendMessage("Damage = "+damage);
					d.sendMessage("----------------------");
					*/
					// if defender has worldguard's god mode then do not damage
					if(MainCore.instance.getWorldGuard() != null && MainCore.instance.getWorldEdit() != null) {
						LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(d);
						if(WorldGuard.getInstance().getPlatform().getSessionManager().get(localPlayer).getHandler(GodMode.class).hasGodMode(localPlayer)) {
							return;
						}
					}
					
					if(s.getHP(d) <= 0) {
						return;
					}
					
					// Damage according to health system
					if(MainCore.instance.config.enableHPSystem) {
						s.removeHP(d, RCUtils.round(damage, 1));
						//System.out.println(damage);
					} else {
						e.setDamage(RCUtils.round(damage, 1));
					}
				} else { //defender is entity
										
					if(e.getEntity() instanceof LivingEntity) {
						LivingEntity le = (LivingEntity) e.getEntity();
						if((le instanceof Zombie || le instanceof Skeleton) && areRCMobsOn()) {
							String yName = RCUtils.stripColor(le.getCustomName());
							double health;
							try {
								health = Double.parseDouble(yName.split("HP:")[1]);
							} catch(Exception ex) {
								health = 20.0;
							}
							health -= damage;
							health = RCUtils.round(health, 1);
							if(health > 0) le.setCustomName(le.getCustomName().split("HP:")[0]+ChatColor.RED+"HP: "+health);
							else le.setCustomName(le.getCustomName().split("HP:")[0]+ChatColor.RED+"Dead");
							if(health < 0) {
								e.setDamage(100000000);
							}
						} else {
							e.setDamage(RCUtils.round(damage, 1));							
						}
					} else {
						e.setDamage(RCUtils.round(damage, 1));
					}
					
				}
	
				if(damage > 0 && config.displayDamage) {
					Location loc = e.getEntity().getLocation().add(0, 2, 0);
					DamageMessage msg = new DamageMessage("" + RCUtils.round(damage, 1), loc);
					damageMsgs.add(msg);
				}
				
			}
	
			if(config.displayDamage) {
				// For DamageMessages
				if (e.getEntity() instanceof ArmorStand) {
					ArmorStand as = (ArmorStand) e.getEntity();
					for (DamageMessage dmg : damageMsgs) {
						if (dmg.getMsg() == as) {
							e.setCancelled(true);
						} else {
							e.setCancelled(false);
						}
					}
				}
			}
			MainCore.instance.rankCraft.rpgClassEvents.archerEvent(e);
		
		}
	}
	
	private boolean areRCMobsOn() {
		return MainCore.instance.config.enableCustomMobs || MainCore.instance.config.enablePreMadeMobs;
	}

	@EventHandler
	public void onDamageOther(EntityDamageEvent e) {
		
		if(!MainCore.instance.config.enableDamageSystem) {
			//System.out.println("RankCraft DamageSystem is disabled!");
			//return;
			if (e.getEntity() instanceof Player) {
				Player p = (Player) e.getEntity();
				if(MainCore.instance.config.enableHPSystem) {
					e.setDamage(0);
					HPSystem hps = MainCore.instance.rankCraft.hpSystem;
					hps.removeHP(p, e.getDamage());
				} else {
					return;
				}
			}
		}
		
		if(RCUtils.isWorldEnabled(e.getEntity().getWorld())) {
		
			final double DAMAGE = e.getDamage();
		/*	
			if(e.isCancelled() || e.getDamage() <= 0) { 
				return;
			}
		*/
			if(MainCore.instance.getWorldGuard() != null && MainCore.instance.getWorldEdit() != null) {
				RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
				RegionQuery query = container.createQuery();
				ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(e.getEntity().getLocation()));
				
				if(!set.testState(null, Flags.MOB_DAMAGE)) {
					return;
				}
				
			}
	
			if (e.getEntity() instanceof Player) { // defender is player
				//System.out.println("Shit1");
				
				if(e.getCause() == DamageCause.ENTITY_ATTACK) { // attacker is entity
					//e.getEntity().sendMessage("Meanwhile");
					e.setDamage(0);
					return;
				}
				
				Player d = (Player) e.getEntity();
				
				if(MainCore.instance.getWorldGuard() != null && MainCore.instance.getWorldEdit() != null) {
					LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(d);
					if(WorldGuard.getInstance().getPlatform().getSessionManager().get(localPlayer).getHandler(GodMode.class).hasGodMode(localPlayer)) {
						return;
					}
				}
				
				e.setDamage(0);
				double critical = 0;
				if (new Random().nextInt(30) <= 100) {
					critical = 2;
				}
				
				if (critical > 0) {
					double x = d.getLocation().getX();
					double y = d.getLocation().getY();
					double z = d.getLocation().getZ();
	
				//	d.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, x, y, z, 3);
					d.getWorld().playEffect(new Location(d.getWorld(),x,y,z),Effect.MOBSPAWNER_FLAMES, 3);
				}
	
				//if(MainCore.instance.rankCraft.hpSystem.getHP(d) <= 0) {
				if(d.isDead()) {	
					e.setCancelled(true);
				}
				
				double damage = RCUtils.round(DAMAGE + critical - getDefenseFromArmor(d), 1);
				if(damage < 0) damage = 0.0;
				
				if(MainCore.instance.config.enableHPSystem) {
					//System.out.println("Shit2");
					MainCore.instance.rankCraft.hpSystem.removeHP(d,damage);
					//System.out.println(MainCore.instance.rankCraft.hpSystem.getHP(d));
					//System.out.println(e.getDamage());
				} else {
					//System.out.println("Shit3");
					e.setDamage(damage);
				}
				
			//	d.sendMessage("damage other");
			//	d.sendMessage("From EntityDamageEvent: " + RCUtils.round(DAMAGE + critical - getDefenseFromArmor(d), 1));
			}
		}
	}

	private static Double getDefaultWeaponDamage(ItemStack stack, boolean minDamage) {
		//System.out.println("Got damage from default weapon");
		double dmg = 0.5;
		int lvl;
		Material mat = stack.getType();
		/*
		switch (mat) {
		case WOODEN_AXE:
			dmg = 1.0;
			break;
		case GOLDEN_AXE:
			dmg = 1.2;
			break;
		case STONE_AXE:
			dmg = 1.5;
			break;
		case IRON_AXE:
			dmg = 1.7;
			break;
		case DIAMOND_AXE:
			dmg = 2.0;
			break;
		case WOODEN_SWORD:
			dmg = 1.1;
			break;
		case GOLDEN_SWORD:
			dmg = 1.4;
			break;
		case STONE_SWORD:
			dmg = 1.7;
			break;
		case IRON_SWORD:
			dmg = 2.0;
			break;
		case DIAMOND_SWORD:
			dmg = 2.5;
			break;
		default:
			dmg = 0.5;
			break;
		}
		 */
		/*
		switch(mat) {
		case WOODEN_AXE:
		case GOLDEN_AXE:		
		case STONE_AXE:		
		case IRON_AXE:		
		case DIAMOND_AXE:				
		case WOODEN_SWORD:				
		case GOLDEN_SWORD:				
		case STONE_SWORD:				
		case IRON_SWORD:				
		case DIAMOND_SWORD:
		*/
		
		if(config.getMaterialWeaponsToRpg().contains(mat)) 
			dmg = RCUtils.round(config.getDefaultWeaponDamage(mat, minDamage), 1);
		
		//	break;
	/*	
	default:
			dmg = 0.8;
			break;
		}
		*/
		
		if (stack.containsEnchantment(Enchantment.DAMAGE_ALL)) {
			lvl = stack.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
		} else {
			lvl = 0;
		}

		return dmg + (lvl * MainCore.instance.config.sharpnessFactor);//(lvl / 4);
	}

	private Double getDamageFromRpgItem(ItemStack stack) {
	//	Language lang = MainCore.instance.language;
		ItemMeta meta = stack.getItemMeta();
		double damage;

		if (!stack.hasItemMeta())
			return getDefaultWeaponDamage(stack, true);
		if (!meta.hasLore())
			return getDefaultWeaponDamage(stack, true);
		/*
		if (!meta.getLore().get(0).startsWith("Min"))
			return getDefaultWeaponDamage(stack);
		if (!meta.getLore().get(1).startsWith("Max"))
			return getDefaultWeaponDamage(stack);
		 */
	//	String minDamageString = meta.getLore().get(0);
	//	String maxDamageString = meta.getLore().get(1);
		
		//String minDamageString = "MinDamage: "+getDefaultWeaponDamage(stack);
		//String maxDamageString = "MaxDamage: "+getDefaultWeaponDamage(stack);
		String minDamageString = ""+getDefaultWeaponDamage(stack, true);
		String maxDamageString = ""+getDefaultWeaponDamage(stack, false);
		
		
		
		for(String s : meta.getLore()) {
			s = stripColor(s);	
		/*
			if(s.startsWith("MinDamage")) {
		//	if(s.startsWith(lang.getMinDamage())) {
				minDamageString = s;
			}
		//	if(s.startsWith("MaxDamage")) {
			if(s.startsWith(lang.getMaxDamage())) {
				maxDamageString = s;
			}
		*/
			
			if(s.startsWith("Stats: ")) {
				String[] parts1 = s.split(": ");
				String[] parts2 = parts1[1].split(",");
				
				minDamageString = parts2[0];
				maxDamageString = parts2[1];
			}
			
		}
		
	//	minDamageString = stripColor(minDamageString);
	//	maxDamageString = stripColor(maxDamageString);
		
	//	String[] parts1 = minDamageString.split(": ");
	//	String[] parts2 = maxDamageString.split(": ");

	//	double minDamage = Double.parseDouble(parts1[1]);
	//	double maxDamage = Double.parseDouble(parts2[1]);
		double minDamage = Double.parseDouble(minDamageString);
		double maxDamage = Double.parseDouble(maxDamageString);
		
	//	System.out.println(minDamage);
	//	System.out.println(maxDamage);
		
		Random r = new Random();

		damage = minDamage + (maxDamage - minDamage) * r.nextDouble();

		//System.out.println("Got from rpg weapon");
		
		return RCUtils.round(damage, 1);

	}
	
	public static Double getMinDamage(ItemStack item) {
		ItemMeta meta = item.getItemMeta();

		if (!item.hasItemMeta())
			return getDefaultWeaponDamage(item, true);
		if (!meta.hasLore())
			return getDefaultWeaponDamage(item, true);
		
		String minDamageString = ""+getDefaultWeaponDamage(item, true);
		
		for(String s : meta.getLore()) {
			s = stripColor(s);	
			
			if(s.startsWith("Stats: ")) {
				String[] parts1 = s.split(": ");
				String[] parts2 = parts1[1].split(",");
				
				minDamageString = parts2[0];
			}
			
		}
		
		double minDamage = Double.parseDouble(minDamageString);

		return RCUtils.round(minDamage, 1);
	}
	
	public static Double getMaxDamage(ItemStack item) {
		ItemMeta meta = item.getItemMeta();

		if (!item.hasItemMeta())
			return getDefaultWeaponDamage(item, false);
		if (!meta.hasLore())
			return getDefaultWeaponDamage(item, false);
		
		String maxDamageString = ""+getDefaultWeaponDamage(item, false);
		
		for(String s : meta.getLore()) {
			s = stripColor(s);	
			
			if(s.startsWith("Stats: ")) {
				String[] parts1 = s.split(": ");
				String[] parts2 = parts1[1].split(",");
				
				maxDamageString = parts2[1];
			}
			
		}
		
		double maxDamage = Double.parseDouble(maxDamageString);

		return RCUtils.round(maxDamage, 1);
	}

	public static Integer getCriticalLuckFromRpgItem(ItemStack stack) {
	//	Language lang = MainCore.instance.language;
		ItemMeta meta = stack.getItemMeta();
		int luck = 0;

		if (!stack.hasItemMeta())
			return 40;
		if (!meta.hasLore())
			return 40;

		if (!(meta.getLore().size() >= 2))
			return 40;
	//	if (!meta.getLore().get(2).startsWith(lang.getCriticalLuck()))
	//		return 40;
	/*
		String criticalLuckString = meta.getLore().get(2);
		criticalLuckString = stripColor(criticalLuckString);

		String[] parts = criticalLuckString.split(": ");

		int criticalLuck = Integer.parseInt(parts[1]);

		luck = criticalLuck;
	 */
		
		for(String s : meta.getLore()) {
			s = stripColor(s);
			
			if(s.startsWith("Stats: ")) {
				String[] parts1 = s.split(": ");
				String[] parts2 = parts1[1].split(",");
				luck = Integer.parseInt(parts2[2]);
			}
			
		}
		
		
		
	//	System.out.println(luck);
		return luck;
	}

	public static Double getEnchantPower(ItemStack stack) {
		double lvl;

		if (stack.containsEnchantment(Enchantment.DAMAGE_ALL)) {
			lvl = stack.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
		} else {
			lvl = 0.0;
		}

		//return lvl / 4.0;
		return lvl * MainCore.instance.config.sharpnessFactor;
	}

	public static Double getEnchantArmor(ItemStack stack) {
		double lvl;
		if(stack.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
			lvl = stack.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
		} else {
			lvl = 0;
		}
		//return lvl / 5.0;
		return lvl * MainCore.instance.config.protectionFactor;
	}
	
	private Double getDefenseFromArmor( LivingEntity p ) {
		double defense = 0.0;
		
		if(p.getEquipment().getHelmet()!=null) 
			defense += getDefenseFromItemStackWithEnchants(p.getEquipment().getHelmet());
		if(p.getEquipment().getChestplate()!=null) 
			defense += getDefenseFromItemStackWithEnchants(p.getEquipment().getChestplate());
		if(p.getEquipment().getLeggings()!=null) 
			defense += getDefenseFromItemStackWithEnchants(p.getEquipment().getLeggings());
		if(p.getEquipment().getBoots()!=null) 
			defense += getDefenseFromItemStackWithEnchants(p.getEquipment().getBoots());
		
		return defense;
	}
	
	private double getDefenseFromItemStackWithEnchants(ItemStack item) {
		double defense = 0.0;
		if(isRpgArmor(item)) {
			for(String s : item.getItemMeta().getLore()) {
				s = stripColor(s);
				if(s.startsWith("ArmorStats: ")) {
					String[] parts = s.split(":");
					try {
						defense += Double.parseDouble(parts[1].split(",")[0]);
					} catch(Exception ex) {
						MainCore.instance.getLogger().log(Level.WARNING, "Something went wrong for this armor piece...");
					}
				}
			}
		} else {
			defense += getDefaultArmorDefense(item);
		}
		defense += getEnchantArmor(item);
		return defense;
	}
	
	/*
	private Double getDefenseFromArmor(LivingEntity p) {
		double defense = 1.0;

		if (p.getEquipment().getChestplate() != null) {
			switch (p.getEquipment().getChestplate().getType()) {
			case LEATHER_CHESTPLATE:
				defense += 0.2;
				break;
			case GOLDEN_CHESTPLATE:
				defense += 0.3;
				break;
			case CHAINMAIL_CHESTPLATE:
				defense += 0.5;
				break;
			case IRON_CHESTPLATE:
				defense += 0.7;
				break;
			case DIAMOND_CHESTPLATE:
				defense += 1.0;
				break;
			default:
				break;
			}
			defense += getEnchantArmor(p.getEquipment().getChestplate());
		}

		if (p.getEquipment().getHelmet() != null) {
			switch (p.getEquipment().getHelmet().getType()) {
			case LEATHER_HELMET:
				defense += 0.2;
				break;
			case GOLDEN_HELMET:
				defense += 0.3;
				break;
			case CHAINMAIL_HELMET:
				defense += 0.5;
				break;
			case IRON_HELMET:
				defense += 0.7;
				break;
			case DIAMOND_HELMET:
				defense += 1.0;
				break;
			default:
				break;
			}
			defense += getEnchantArmor(p.getEquipment().getHelmet());
		}

		if (p.getEquipment().getLeggings() != null) {
			switch (p.getEquipment().getLeggings().getType()) {
			case LEATHER_LEGGINGS:
				defense += 0.2;
				break;
			case GOLDEN_LEGGINGS:
				defense += 0.3;
				break;
			case CHAINMAIL_LEGGINGS:
				defense += 0.5;
				break;
			case IRON_LEGGINGS:
				defense += 0.7;
				break;
			case DIAMOND_LEGGINGS:
				defense += 1.0;
				break;
			default:
				break;
			}
			defense += getEnchantArmor(p.getEquipment().getLeggings());
		}

		if (p.getEquipment().getBoots() != null) {
			switch (p.getEquipment().getBoots().getType()) {
			case LEATHER_BOOTS:
				defense += 0.2;
				break;
			case GOLDEN_BOOTS:
				defense += 0.3;
				break;
			case CHAINMAIL_BOOTS:
				defense += 0.5;
				break;
			case IRON_BOOTS:
				defense += 0.7;
				break;
			case DIAMOND_BOOTS:
				defense += 1.0;
				break;
			default:
				break;
			}
			defense += getEnchantArmor(p.getEquipment().getBoots());
		}

		
		
		return defense;
	}
	*/
	private double getPotionEffectStrength(Player p) {
		double strength = 0;
		
		for(PotionEffect pe : p.getActivePotionEffects()) {
	//		System.out.println(pe.getType());
			if(pe.getType().equals(PotionEffectType.INCREASE_DAMAGE)) {
	//			System.out.println("added strength");
				//strength = (pe.getAmplifier()+1) * 3;
				strength = (pe.getAmplifier()+1) * MainCore.instance.config.strengthFactor;
			}
		}
		
		return strength;
	}
	
	private double getPotionEffectFromLivingEntity(LivingEntity le) {
		double strength = 0;
		for(PotionEffect pe : le.getActivePotionEffects()) 
			if(pe.getType().equals(PotionEffectType.INCREASE_DAMAGE)) 
				strength = (pe.getAmplifier()+1) * MainCore.instance.config.strengthFactor;
		return strength;
	}

	public static boolean isPlayerLevelForRpgItem(Player p, ItemStack item) {
		int min = getMinLevelOfRpgItem(item);
		
		if (MainCore.instance.rankCraft.stats.getLevel(p) >= min) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isPlayerLevelForRpgArmor(Player p, ItemStack item) {
		int min = getMinLevelOfRpgArmor(item);
		//System.out.println(min);
		if(MainCore.instance.rankCraft.stats.getLevel(p) >= min)
			return true;
		else 
			return false;
	}
	
	public static int getMinLevelOfRpgArmor(ItemStack item) {
		int level = 0;
		for(String s : item.getItemMeta().getLore()) {
			s = stripColor(s);
			if(s.startsWith("ArmorStats: ")) {
				String[] parts = s.split(",");
				level = Integer.parseInt(parts[1]);
			}
		}
		return level;
	}
	
	public static int getMinLevelOfRpgItem(ItemStack item) {
	//	Language lang = MainCore.instance.language;
		int level = 0;
		if(item.hasItemMeta()) {
			if(item.getItemMeta().hasLore()) {
				for(String s : item.getItemMeta().getLore()) {
					s = stripColor(s);
					//if(s.startsWith("MinLevel")) {
				//	if(s.startsWith("MinLevel")) {
				//		s = stripColor(s);
				//		String[] parts = s.split(": ");
				//		level = Integer.parseInt(parts[1]);
					if(s.startsWith("Stats: ")) {
						
						String[] parts1 = s.split(": ");
						String[] parts2 = parts1[1].split(",");
						level = Integer.parseInt(parts2[3]);
						
					}
				}
			}
		}
	//	System.out.println(level);
		return level;
	}
	public static boolean isRpgItem(ItemStack item) {
	//	Language lang = MainCore.instance.language;
		if(item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if(meta.hasLore()) {
				for(String s : meta.getLore()) {
					s = stripColor(s);
				//	System.out.println(s);
				//	if(s.startsWith("MinDamage")) {
					if(s.startsWith("Stats: ")) {
					//	System.out.println("Yes1!");
						return true;
					}
				}
			}
		} 
		
		return false;
	}
	
	public static boolean isRpgArmor(ItemStack item) {
		if(item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if(meta.hasLore()) {
				for(String s : meta.getLore()) {
					s = stripColor(s);
					if(s.startsWith("ArmorStats: ")) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private static double getDefaultArmorDefense(ItemStack item) {
		Material mat = item.getType();
		double d = 0.1;
		
		if(config.getMaterialArmorsToRpg().contains(mat))
			d = config.getDefaultArmorForMaterial(mat);
		
		return d;
		/*
		switch(item.getType()) {
		case LEATHER_HELMET:
			return 0.5;
		case LEATHER_CHESTPLATE:
			return 1.5;
		case LEATHER_LEGGINGS:
			return 1.0;
		case LEATHER_BOOTS:
			return 0.5;
		case GOLDEN_HELMET:
			return 1.0;
		case GOLDEN_CHESTPLATE:
			return 2.5;
		case GOLDEN_LEGGINGS:
			return 1.5;
		case GOLDEN_BOOTS:
			return 0.6;
		case CHAINMAIL_HELMET:
			return 1.2;
		case CHAINMAIL_CHESTPLATE:
			return 2.7;
		case CHAINMAIL_LEGGINGS:
			return 2.0;
		case CHAINMAIL_BOOTS:
			return 0.8;
		case IRON_HELMET:
			return 1.3;
		case IRON_CHESTPLATE:
			return 3.0;
		case IRON_LEGGINGS:
			return 2.5;
		case IRON_BOOTS:
			return 1.0;
		case DIAMOND_HELMET:
			return 1.5;
		case DIAMOND_CHESTPLATE:
			return 4.0;
		case DIAMOND_LEGGINGS:
			return 3.0;
		case DIAMOND_BOOTS:
			return 1.5;
		default:
			return 0.0;
		}
		*/
	}
	
	public static RpgArmor getRpgArmorFromDefault(ItemStack item) {
		if(isArmorPiece(item)) {		
			if(!isRpgArmor(item)) { // for rpg armors
				Material mat = item.getType();
				double defense = getDefaultArmorDefense(item);
				int minLevel = 0;
				Rarity r = Rarity.COMMON;
				
					
				Map<Enchantment,Integer> enchants = item.getEnchantments();
				RpgArmor a = new RpgArmor(mat, defense, r, minLevel);
				a.getCustomItem().setName(item.getItemMeta().getDisplayName());
					
				for(int i = 0; i < enchants.size(); i++) {
					a.getCustomItem().enchant((Enchantment)enchants.keySet().toArray()[i], (int)enchants.values().toArray()[i]);
				}
					
				a.build();
				return a;
			}	
		}
		return null;
	}
	
	public static void makeRpgHandItem(Player p) {
		ItemStack item = p.getInventory().getItemInMainHand();
		if(!isRpgItem(item)) {
			Material mat = item.getType();
			double minDamage = getDefaultWeaponDamage(item, true);
			double maxDamage = getDefaultWeaponDamage(item, false);
			int criticalLuck = 0;
			//int minLevel = 1;
			Rarity rarity = Rarity.COMMON;
			
			//Material m = Material.getMaterial("WOODEN_AXE");
			/*
			switch(mat) {
			
			case WOODEN_AXE:
			case GOLDEN_AXE:		
			case STONE_AXE:		
			case IRON_AXE:		
			case DIAMOND_AXE:				
			case WOODEN_SWORD:				
			case GOLDEN_SWORD:				
			case STONE_SWORD:				
			case IRON_SWORD:				
			case DIAMOND_SWORD:
			*/
			
			if(config.getMaterialWeaponsToRpg().contains(mat)) {
				
				int minLevel = config.getDefaultWeaponMinLevel(mat);
				
				Map<Enchantment,Integer> enchants = item.getEnchantments();
				
				RpgItem r = new RpgItem(mat);
				

				if(isOldRpgItem(item)) {
					// Set the new format of the item based on its old!
					minDamage = getOldMinDamage(item);
					maxDamage = getOldMaxDamage(item);
					criticalLuck = getOldCriticalLuck(item);
					minLevel = getOldMinLevel(item);
				}
				
				r.setDisplayName(item.getItemMeta().getDisplayName());
			/*
				r.setCriticalLuck(10);
				r.setLevelToUse(0);
				r.setMaxDamage(getDefaultWeaponDamage(item));
				r.setMinDamage(getDefaultWeaponDamage(item));
			*/
				
				r.setCriticalLuck(criticalLuck);
				r.setLevelToUse(minLevel);
				r.setMinDamage(minDamage);
				r.setMaxDamage(maxDamage);
				
				r.setRarity(rarity);
				
				for(int i = 0; i < enchants.size(); i++) {
					r.enchant((Enchantment)enchants.keySet().toArray()[i], (int)enchants.values().toArray()[i]);
				}
				
				r.build();
				
				//p.setItemInHand(r.getItem());
				//p.setItemOnCursor(r.getItem());
				p.getInventory().setItemInMainHand(r.getItem());
				
			}
			/*
			default:
				break;
			}
			*/
		} 
		
		if(isArmorPiece(item)) {		
			if(!isRpgArmor(item)) { // for rpg armors
				Material mat = item.getType();
				double defense = getDefaultArmorDefense(item);
				
				int minLevel = 0;
				if(config.getMaterialArmorsToRpg().contains(mat)) {
					minLevel = config.getDefaultArmorMinLevel(mat);
					//System.out.println(minLevel);
				}
				
				Rarity r = Rarity.COMMON;
				
				Map<Enchantment,Integer> enchants = item.getEnchantments();
				RpgArmor a = new RpgArmor(mat, defense, r, minLevel);
				a.getCustomItem().setName(item.getItemMeta().getDisplayName());
					
				for(int i = 0; i < enchants.size(); i++) {
					a.getCustomItem().enchant((Enchantment)enchants.keySet().toArray()[i], (int)enchants.values().toArray()[i]);
				}
					
				p.getInventory().setItemInMainHand(a.build().getItem());
			}	
		}
		
	}
	
	public static boolean isArmorPiece(ItemStack item) {
		return config.getMaterialArmorsToRpg().contains(item.getType());
	}
	
	/*
	public static boolean isArmorPiece(ItemStack item) {
		switch(item.getType()) {
			case LEATHER_HELMET:
			case LEATHER_CHESTPLATE:
			case LEATHER_LEGGINGS:
			case LEATHER_BOOTS:
			case GOLDEN_HELMET:
			case GOLDEN_CHESTPLATE:
			case GOLDEN_LEGGINGS:
			case GOLDEN_BOOTS:
			case CHAINMAIL_HELMET:
			case CHAINMAIL_CHESTPLATE:
			case CHAINMAIL_LEGGINGS:
			case CHAINMAIL_BOOTS:
			case IRON_HELMET:
			case IRON_CHESTPLATE:
			case IRON_LEGGINGS:
			case IRON_BOOTS:
			case DIAMOND_HELMET:
			case DIAMOND_CHESTPLATE:
			case DIAMOND_LEGGINGS:
			case DIAMOND_BOOTS:
				return true;	
			default:
				return false;
		}
	}
	*/
//	private static Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf('&') + "[0-9A-FK-OR]");

	public static String stripColor(String input) {
		return ChatColor.stripColor(input);
//	    return input == null ? null : STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
	}
	
	public static boolean isOldRpgItem(ItemStack item) {
		
		if(item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if(meta.hasLore()) {
				for(String s : meta.getLore()) {
					if(s.startsWith("MinDamage: ")) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public static double getOldMinDamage(ItemStack item) {
		
		double dmg = getDefaultWeaponDamage(item, true);
		
		if(item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if(meta.hasLore()) {
				for(String s : meta.getLore()) {
					if(s.startsWith("MinDamage: ")) {
						String[] parts = s.split(": ");
						dmg = Double.parseDouble(parts[1]);
					}
				}
			}
		}
		
		return dmg;
	}
	
	public static double getOldMaxDamage(ItemStack item) {
		double dmg = getDefaultWeaponDamage(item, false);
		
		if(item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if(meta.hasLore()) {
				for(String s : meta.getLore()) {
					if(s.startsWith("MaxDamage: ")) {
						String[] parts = s.split(": ");
						dmg = Double.parseDouble(parts[1]);
					}
				}
			}
		}
		
		return dmg;
	}
	
	public static int getOldCriticalLuck(ItemStack item) {
		int luck = 0;
		
		if(item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if(meta.hasLore()) {
				for(String s : meta.getLore()) {
					if(s.startsWith("CriticalLuck: ")) {
						String[] parts = s.split(": ");
						luck = Integer.parseInt(parts[1]);
					}
				}
			}
		}
		
		return luck;
	}

	public static int getOldMinLevel(ItemStack item) {
		int lvl = 1;
		
		if(item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if(meta.hasLore()) {
				for(String s : meta.getLore()) {
					if(s.startsWith("MinLevel: ")) {
						String[] parts = s.split(": ");
						lvl = Integer.parseInt(parts[1]);
					}
				}
			}
		}
		
		return lvl;
	}
}
