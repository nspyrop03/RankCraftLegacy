package me.amc.rankcraft.mobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.mobs.ArmorLists.ArmorType;
import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.RCUtils;

public class Skeletons implements Listener {

	public List<Arrow> arrows1_3 = new ArrayList<>();
	public List<Arrow> arrows4_6 = new ArrayList<>();
	public List<Arrow> arrows7_9 = new ArrayList<>();
	public List<Arrow> arrows10 = new ArrayList<>();
	private Material[] weapons = {Material.IRON_SWORD, Material.DIAMOND_AXE};
	private int maxEnchant1_3 = 1;
	private int maxEnchant4_6 = 2;
	private int maxEnchant7_9 = 5;
	private int maxEnchant10 = 7;
	private int enchantChances1_3 = 50;
	private int enchantChances4_6 = 45;
	private int enchantChances7_9 = 35;
	private int enchantChances10 = 20;
	private Random random = new Random();
	private ArmorLists a;
	private String levelPrefix = ChatColor.YELLOW + "" + ChatColor.BOLD + "LEVEL: " + ChatColor.RED + ""+ ChatColor.BOLD;
	private JavaPlugin plugin;
	
	public Skeletons(JavaPlugin plugin) {
		a = new ArmorLists();
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}
	
	@EventHandler
	public void onSpawn(EntitySpawnEvent e) {
		if(e.getEntity() instanceof Skeleton) {
			
			Skeleton s = (Skeleton) e.getEntity();
			if(RCUtils.isWorldEnabled(s.getWorld())) {
				if(s.getCustomName() != null) {
					return;
				}
				
				switch(random.nextInt(10) + 1) {
				case 1:
					customizeSkeleton(s, 1);
					break;
				case 2:
					customizeSkeleton(s, 2);
					break;
				case 3:
					customizeSkeleton(s, 3);
					break;
				case 4:
					customizeSkeleton(s, 4);
					break;
				case 5:
					customizeSkeleton(s, 5);
					break;
				case 6:
					customizeSkeleton(s, 6);
					break;
				case 7:
					customizeSkeleton(s, 7);
					break;
				case 8:
					customizeSkeleton(s, 8);
					break;
				case 9:
					customizeSkeleton(s, 9);
					break;
				case 10:
					customizeSkeleton(s, 10);
					break;
				default:
					break;
				}
			}
		}
	}
	
	@EventHandler
	public void onEject(ProjectileLaunchEvent e) {
		if(e.getEntity() instanceof Arrow) {
			Arrow a = (Arrow) e.getEntity();
			if(a.getShooter() instanceof Skeleton) {
				Skeleton s = (Skeleton) a.getShooter();
				if(s.getCustomName().startsWith(levelPrefix)) {
					String name = ChatColor.stripColor(s.getCustomName());
					String[] parts = name.split(": ");
					try {
						int level = Integer.parseInt(parts[1]);
						if(level >= 1 && level <= 3) {
							arrows1_3.add(a);
						} else if(level >= 4 && level <= 6) {
							arrows4_6.add(a);
						} else if(level >= 7 && level <= 9) {
							arrows7_9.add(a);
						} else {
							arrows10.add(a);
						}
					} catch(Exception ex) {
						System.out.println("Cannot parse Skeleton's level !!!");
						System.out.println("parts[0] = "+parts[0]);
						System.out.println("parts[1] = "+parts[1]);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onHit(ProjectileHitEvent e) {
		if(e.getEntity() instanceof Arrow) {
			Arrow a = (Arrow) e.getEntity();
			if(arrows1_3.contains(a)) {
				arrows1_3.remove(a);
			}
			if(arrows4_6.contains(a)) {
				arrows4_6.remove(a);
			}
			if(arrows7_9.contains(a)) {
				arrows7_9.remove(a);
			}
			if(arrows10.contains(a)) {
				arrows10.remove(a);
			}
		}
	}
	
//	@SuppressWarnings("deprecation")
	private void customizeSkeleton(Skeleton s, int level) {
		s.setCustomName(levelPrefix+level);
		s.setCustomNameVisible(MainCore.instance.config.alwaysDisplayName);
		
		EntityInventory inv = makeInventory(s, level);
		inv.addInventoryToEntity();
		
		s.getEquipment().setBootsDropChance(0.3F);
		s.getEquipment().setChestplateDropChance(0.1F);
		s.getEquipment().setLeggingsDropChance(0.2F);
		s.getEquipment().setHelmetDropChance(0.3F);
		//s.getEquipment().setItemInHandDropChance(0.2F);
		s.getEquipment().setItemInMainHandDropChance(0.2F);
	}
	
	private EntityInventory makeInventory(Skeleton s, int level) {
		EntityInventory inv = new EntityInventory(s);

		switch (level) {
		case 1:
			inv.setBoots(a.makeRandomArmor(maxEnchant1_3, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant1_3, ArmorType.CHESTPLATE, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant1_3, ArmorType.HELMET, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant1_3, ArmorType.LEGGINGS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant1_3, level));
			break;
		case 2:
			inv.setBoots(a.makeRandomArmor(maxEnchant1_3, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant1_3, ArmorType.CHESTPLATE, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant1_3, ArmorType.HELMET, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant1_3, ArmorType.LEGGINGS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant1_3, level));
			break;
		case 3:
			inv.setBoots(a.makeRandomArmor(maxEnchant1_3, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant1_3, ArmorType.CHESTPLATE, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant1_3, ArmorType.HELMET, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant1_3, ArmorType.LEGGINGS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant1_3, level));
			break;
		case 4:
			inv.setBoots(a.makeRandomArmor(maxEnchant4_6, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant4_6, ArmorType.CHESTPLATE, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant4_6, ArmorType.HELMET, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant4_6, ArmorType.LEGGINGS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant4_6, level));
			break;
		case 5:
			inv.setBoots(a.makeRandomArmor(maxEnchant4_6, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant4_6, ArmorType.CHESTPLATE, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant4_6, ArmorType.HELMET, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant4_6, ArmorType.LEGGINGS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant4_6, level));
			break;
		case 6:
			inv.setBoots(a.makeRandomArmor(maxEnchant4_6, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant4_6, ArmorType.CHESTPLATE, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant4_6, ArmorType.HELMET, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant4_6, ArmorType.LEGGINGS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant4_6, level));
			break;
		case 7:
			inv.setBoots(a.makeRandomArmor(maxEnchant7_9, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant7_9, ArmorType.CHESTPLATE, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant7_9, ArmorType.HELMET, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant7_9, ArmorType.LEGGINGS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant7_9, level));
			break;
		case 8:
			inv.setBoots(a.makeRandomArmor(maxEnchant7_9, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant7_9, ArmorType.CHESTPLATE, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant7_9, ArmorType.HELMET, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant7_9, ArmorType.LEGGINGS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant7_9, level));
			break;
		case 9:
			inv.setBoots(a.makeRandomArmor(maxEnchant7_9, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant7_9, ArmorType.CHESTPLATE, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant7_9, ArmorType.HELMET, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant7_9, ArmorType.LEGGINGS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant7_9, level));
			break;
		case 10:
			inv.setBoots(a.makeRandomArmor(maxEnchant10, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant10, ArmorType.CHESTPLATE, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant10, ArmorType.HELMET, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant10, ArmorType.LEGGINGS, level, enchantChances1_3, enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant10, level));
			break;
		default:
			break;
		}

		return inv;
	}
	
	private CustomItem makeRandomWeapon(int maxEnchant, int level) {
		CustomItem cItem;
		int rBow = random.nextInt(100);
		
		if(rBow <= 75) {
			cItem = new CustomItem(Material.BOW);
		} else {
			cItem = new CustomItem(weapons[random.nextInt(weapons.length)]);
		}
		
		switch(level) {
		case 1:
			
			if(random.nextInt(100) <= enchantChances1_3) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant1_3)+1);
			}
			break;
		case 2:
			
			if(random.nextInt(100) <= enchantChances1_3) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant1_3)+1);
			}
			break;
		case 3:
			
			if(random.nextInt(100) <= enchantChances1_3) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant1_3)+1);
			}
			break;
		case 4:
		
			if(random.nextInt(100) <= enchantChances4_6) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant4_6)+1);
			}
			break;
		case 5:
			
			if(random.nextInt(100) <= enchantChances4_6) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant4_6)+1);
			}
			break;
		case 6:
			
			if(random.nextInt(100) <= enchantChances4_6) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant4_6)+1);
			}
			break;
		case 7:
			
			if(random.nextInt(100) <= enchantChances7_9) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant7_9)+1);
			}
			break;
		case 8:
			
			if(random.nextInt(100) <= enchantChances7_9) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant7_9)+1);
			}
			break;
		case 9:
			
			if(random.nextInt(100) <= enchantChances7_9) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant7_9)+1);
			}
			break;
		case 10:
		
			if(random.nextInt(100) <= enchantChances10) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant10)+1);
			}
			break;
		default:
			cItem = new CustomItem(Material.BARRIER);
			System.out.println("Error on making random weapon for skeleton of level "+level+". Max level = 10");
			break;
		}
		
		return cItem.build();
	}
	
	
}
