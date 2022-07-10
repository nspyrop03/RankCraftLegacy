package me.amc.rankcraft.mobs;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import me.amc.rankcraft.utils.RCUtils;

public class AbilityListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		
		Entity ent = e.getDamager();
		if(ent instanceof LivingEntity) {
			YamlMob ym = RCUtils.getYamlMobFromEntity((LivingEntity)ent);
			if(ym != null) {
				//System.out.println("not null");
				Random r = new Random();
				
				if(ym.hasAbility(RCUtils.ZEUS_HIT)) {
					if(r.nextInt(101) <= ym.getAbilityChances(RCUtils.ZEUS_HIT))
						for(int i = 0; i < 3; i++) 
							e.getEntity().getWorld().strikeLightning(e.getEntity().getLocation());
				}
				
				if(ym.hasAbility(RCUtils.ICE_TOUCH)) {
					if(r.nextInt(101) <= ym.getAbilityChances(RCUtils.ICE_TOUCH)) {
						if(e.getEntity() instanceof LivingEntity) {
							LivingEntity le = (LivingEntity) e.getEntity();
							le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (int)RCUtils.secondsToTicks(60), 2, true));
						}
					}
				}
				
				if(ym.hasAbility(RCUtils.POISON_STING)) {
					if(r.nextInt(101) <= ym.getAbilityChances(RCUtils.POISON_STING)) {
						if(e.getEntity() instanceof LivingEntity) {
							LivingEntity le = (LivingEntity) e.getEntity();
							le.addPotionEffect(new PotionEffect(PotionEffectType.POISON, (int)RCUtils.secondsToTicks(30), 0, true));
						}
					}
				}
				
				if(ym.hasAbility(RCUtils.WEB_ATTACK)) {
					//System.out.println("has web attack");
					if(r.nextInt(101) <= ym.getAbilityChances(RCUtils.WEB_ATTACK)) {
						//System.out.println("spawning web");
						e.getEntity().getWorld().spawnFallingBlock(e.getEntity().getLocation(), Material.COBWEB, (byte) 0);
					}
				}
				
				if(ym.hasAbility(RCUtils.MONSTER_FIST)) {
					if(r.nextInt(101) <= ym.getAbilityChances(RCUtils.MONSTER_FIST)) {
						if(e.getEntity() instanceof LivingEntity) {
							//System.out.println("HULK FIST!");
							LivingEntity le = (LivingEntity) e.getEntity();
							e.setCancelled(true);
							le.setVelocity(new Vector(le.getVelocity().getX(), 1.2, le.getVelocity().getZ()));
						}
					}
				}
				
			}
			//System.out.println("Ability: "+e.getDamage());
		}
		if(e.getEntity() instanceof LivingEntity) {
			YamlMob ym = RCUtils.getYamlMobFromEntity((LivingEntity)e.getEntity());
			if(ym != null) {
				Random r = new Random();
				if(ym.hasAbility(RCUtils.HIDDEN_POWER)) {
					if(r.nextInt(101) <= ym.getAbilityChances(RCUtils.HIDDEN_POWER)) {
						//System.out.println("hiddennnn!");
						LivingEntity le = (LivingEntity)e.getEntity();
						le.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, (int)RCUtils.secondsToTicks(10), 2, true));
					}
				}
				if(ym.hasAbility(RCUtils.MINIONS_DEFENCE)) {
					
					if(r.nextInt(101) <= ym.getAbilityChances(RCUtils.MINIONS_DEFENCE)) {
						//System.out.println("minioooons!");
						LivingEntity le = (LivingEntity)e.getEntity();
						if(ym.getType().equalsIgnoreCase("ZOMBIE")) {
							Zombie minion = (Zombie) le.getWorld().spawnEntity(le.getLocation(), EntityType.ZOMBIE);
							minion.setBaby(true);
							ym.makeMob(minion);
							minion.setCustomName(ChatColor.YELLOW+""+ChatColor.BOLD+"Minion"+ChatColor.RED+" HP: "+ym.getHealth());
							LivingEntity chicken = (LivingEntity) le.getWorld().spawnEntity(le.getLocation(), EntityType.CHICKEN);
							chicken.setPassenger(minion);
						} else if(ym.getType().equalsIgnoreCase("SKELETON")){
							Skeleton minion = (Skeleton) le.getWorld().spawnEntity(le.getLocation(), EntityType.SKELETON);
							ym.makeMob(minion);
							minion.setCustomName(ChatColor.YELLOW+""+ChatColor.BOLD+"Minion"+ChatColor.RED+" HP: "+ym.getHealth());
							LivingEntity chicken = (LivingEntity) le.getWorld().spawnEntity(le.getLocation(), EntityType.CHICKEN);
							chicken.setPassenger(minion);
						}
						
					}
				}
			}
		}
		
	}

}
