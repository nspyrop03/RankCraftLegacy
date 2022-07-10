package me.amc.rankcraft.mobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.mobs.ArmorLists.ArmorType;
import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.RCUtils;

public class Zombies implements Listener {

	private String levelPrefix = ChatColor.YELLOW + "" + ChatColor.BOLD + "LEVEL: " + ChatColor.RED + ""
			+ ChatColor.BOLD;
	private Random random = new Random();
	private JavaPlugin plugin;

	private int enchantChances1_3 = 20;
	private int enchantChances4_6 = 35;
	private int enchantChances7_9 = 50;
	private int enchantChances10 = 80;
	private int maxEnchant1_3 = 2;
	private int maxEnchant4_6 = 3;
	private int maxEnchant7_9 = 5;
	private int maxEnchant10 = 8;
	private Material[] levelWeapons1_3 = { Material.WOODEN_AXE, Material.WOODEN_SWORD, Material.WOODEN_SHOVEL,
			Material.GOLDEN_AXE, Material.GOLDEN_SWORD };
	private Material[] levelWeapons4_6 = { Material.STONE_SWORD, Material.STONE_AXE, Material.GOLDEN_AXE,
			Material.GOLDEN_SWORD };
	private Material[] levelWeapons7_9 = { Material.STONE_SWORD, Material.STONE_AXE, Material.IRON_SWORD,
			Material.IRON_AXE, Material.GOLDEN_PICKAXE };
	private Material[] levelWeapons10 = { Material.IRON_SWORD, Material.IRON_AXE, Material.DIAMOND_SWORD,
			Material.DIAMOND_AXE };

	private ArmorLists a;

	public List<Zombie> zombies10 = new ArrayList<>();

	public Zombies(JavaPlugin plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
		a = new ArmorLists();
	}

	@EventHandler
	public void onSpawn(EntitySpawnEvent e) {
		if (e.getEntity() instanceof Zombie) {
			Zombie z = (Zombie) e.getEntity();

			if(RCUtils.isWorldEnabled(z.getWorld())) {
				if(z.getCustomName() != null) {
			//		System.out.println(z.getCustomName());
					return;
				}
				
				switch (random.nextInt(10) + 1) {
				case 1:
					customizeZombie(z, 1);
					break;
				case 2:
					customizeZombie(z, 2);
					break;
				case 3:
					customizeZombie(z, 3);
					break;
				case 4:
					customizeZombie(z, 4);
					break;
				case 5:
					customizeZombie(z, 5);
					break;
				case 6:
					customizeZombie(z, 6);
					break;
				case 7:
					customizeZombie(z, 7);
					break;
				case 8:
					customizeZombie(z, 8);
					break;
				case 9:
					customizeZombie(z, 9);
					break;
				case 10:
					customizeZombie(z, 10);
					zombies10.add(z);
					/*
					System.out.println(Main.rankCraft.mobSaver == null);
					System.out.println("added id " + z.getEntityId());
					Main.rankCraft.mobSaver.addToList(z.getEntityId());
					Main.rankCraft.mobSaver.save();
					System.out.println("zombie " + z.toString() + " added");
					*/
					break;
				default:
					break;
				}
			}
		}
	}

	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if (e.getEntity() instanceof Zombie) {
			Zombie z = (Zombie) e.getEntity();
			if(z.getCustomName() == null) {
				return;
			}
			if (z.getCustomName().startsWith(levelPrefix)) {

				String name = ChatColor.stripColor(z.getCustomName());
				String[] parts = name.split(": ");

				try {
					int level = Integer.parseInt(parts[1]);
					if (level == 10) {
						if (zombies10.contains(z)) {
							zombies10.remove(z);
					//		Main.rankCraft.mobSaver.removeFromList(z.getEntityId());
					//		Main.rankCraft.mobSaver.save();
						}
					}
				} catch (Exception ex) {
					System.out.println("Cannot parse zombie's level ! " + z.toString());
				}
			}
		}
	}

	public void spawnLeveldZombie(Location loc, int level) {
		Zombie z = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
		customizeZombie(z, level);
		
		if(level == 10) {
			zombies10.add(z);
		}
		/*
		zombies10.add(z);
		System.out.println(Main.rankCraft.mobSaver == null);
		System.out.println("added id " + z.getEntityId());
		Main.rankCraft.mobSaver.addToList(z.getEntityId());
		Main.rankCraft.mobSaver.save();
		System.out.println("zombie " + z.toString() + " added");
		*/
	}

//	@SuppressWarnings("deprecation")
	private void customizeZombie(Zombie z, int level) {
		z.setCustomName(levelPrefix + level);
		z.setCustomNameVisible(MainCore.instance.config.alwaysDisplayName);
		EntityInventory inv = makeInventory(z, level);
		inv.addInventoryToEntity();
		z.getEquipment().setBootsDropChance(0.3F);
		z.getEquipment().setChestplateDropChance(0.1F);
		z.getEquipment().setLeggingsDropChance(0.2F);
		z.getEquipment().setHelmetDropChance(0.3F);
		//z.getEquipment().setItemInHandDropChance(0.2F);
		z.getEquipment().setItemInMainHandDropChance(0.2F);
	}

	private EntityInventory makeInventory(LivingEntity entity, int level) {
		EntityInventory inv = new EntityInventory(entity);

		switch (level) {
		case 1:
			inv.setBoots(a.makeRandomArmor(maxEnchant1_3, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6,
					enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant1_3, ArmorType.CHESTPLATE, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant1_3, ArmorType.HELMET, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant1_3, ArmorType.LEGGINGS, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant1_3, level));
			break;
		case 2:
			inv.setBoots(a.makeRandomArmor(maxEnchant1_3, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6,
					enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant1_3, ArmorType.CHESTPLATE, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant1_3, ArmorType.HELMET, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant1_3, ArmorType.LEGGINGS, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant1_3, level));
			break;
		case 3:
			inv.setBoots(a.makeRandomArmor(maxEnchant1_3, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6,
					enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant1_3, ArmorType.CHESTPLATE, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant1_3, ArmorType.HELMET, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant1_3, ArmorType.LEGGINGS, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant1_3, level));
			break;
		case 4:
			inv.setBoots(a.makeRandomArmor(maxEnchant4_6, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6,
					enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant4_6, ArmorType.CHESTPLATE, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant4_6, ArmorType.HELMET, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant4_6, ArmorType.LEGGINGS, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant4_6, level));
			break;
		case 5:
			inv.setBoots(a.makeRandomArmor(maxEnchant4_6, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6,
					enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant4_6, ArmorType.CHESTPLATE, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant4_6, ArmorType.HELMET, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant4_6, ArmorType.LEGGINGS, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant4_6, level));
			break;
		case 6:
			inv.setBoots(a.makeRandomArmor(maxEnchant4_6, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6,
					enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant4_6, ArmorType.CHESTPLATE, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant4_6, ArmorType.HELMET, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant4_6, ArmorType.LEGGINGS, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant4_6, level));
			break;
		case 7:
			inv.setBoots(a.makeRandomArmor(maxEnchant7_9, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6,
					enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant7_9, ArmorType.CHESTPLATE, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant7_9, ArmorType.HELMET, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant7_9, ArmorType.LEGGINGS, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant7_9, level));
			break;
		case 8:
			inv.setBoots(a.makeRandomArmor(maxEnchant7_9, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6,
					enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant7_9, ArmorType.CHESTPLATE, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant7_9, ArmorType.HELMET, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant7_9, ArmorType.LEGGINGS, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant7_9, level));
			break;
		case 9:
			inv.setBoots(a.makeRandomArmor(maxEnchant7_9, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6,
					enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant7_9, ArmorType.CHESTPLATE, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant7_9, ArmorType.HELMET, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant7_9, ArmorType.LEGGINGS, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant7_9, level));
			break;
		case 10:
			inv.setBoots(a.makeRandomArmor(maxEnchant10, ArmorType.BOOTS, level, enchantChances1_3, enchantChances4_6,
					enchantChances7_9, enchantChances10));
			inv.setChestplate(a.makeRandomArmor(maxEnchant10, ArmorType.CHESTPLATE, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHelmet(a.makeRandomArmor(maxEnchant10, ArmorType.HELMET, level, enchantChances1_3, enchantChances4_6,
					enchantChances7_9, enchantChances10));
			inv.setLeggings(a.makeRandomArmor(maxEnchant10, ArmorType.LEGGINGS, level, enchantChances1_3,
					enchantChances4_6, enchantChances7_9, enchantChances10));
			inv.setHandItem(makeRandomWeapon(maxEnchant10, level));
			break;
		default:
			break;
		}

		return inv;
	}

	// re-write

	private CustomItem makeRandomWeapon(int maxEnchant, int level) {
		CustomItem cItem;

		switch (level) {
		case 1:
			cItem = new CustomItem(levelWeapons1_3[random.nextInt(levelWeapons1_3.length)]);
			if (random.nextInt(100) <= enchantChances1_3) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant1_3) + 1);
			}
			break;
		case 2:
			cItem = new CustomItem(levelWeapons1_3[random.nextInt(levelWeapons1_3.length)]);
			if (random.nextInt(100) <= enchantChances1_3) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant1_3) + 1);
			}
			break;
		case 3:
			cItem = new CustomItem(levelWeapons1_3[random.nextInt(levelWeapons1_3.length)]);
			if (random.nextInt(100) <= enchantChances1_3) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant1_3) + 1);
			}
			break;
		case 4:
			cItem = new CustomItem(levelWeapons4_6[random.nextInt(levelWeapons4_6.length)]);
			if (random.nextInt(100) <= enchantChances4_6) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant4_6) + 1);
			}
			break;
		case 5:
			cItem = new CustomItem(levelWeapons4_6[random.nextInt(levelWeapons4_6.length)]);
			if (random.nextInt(100) <= enchantChances4_6) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant4_6) + 1);
			}
			break;
		case 6:
			cItem = new CustomItem(levelWeapons4_6[random.nextInt(levelWeapons4_6.length)]);
			if (random.nextInt(100) <= enchantChances4_6) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant4_6) + 1);
			}
			break;
		case 7:
			cItem = new CustomItem(levelWeapons7_9[random.nextInt(levelWeapons7_9.length)]);
			if (random.nextInt(100) <= enchantChances7_9) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant7_9) + 1);
			}
			break;
		case 8:
			cItem = new CustomItem(levelWeapons7_9[random.nextInt(levelWeapons7_9.length)]);
			if (random.nextInt(100) <= enchantChances7_9) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant7_9) + 1);
			}
			break;
		case 9:
			cItem = new CustomItem(levelWeapons7_9[random.nextInt(levelWeapons7_9.length)]);
			if (random.nextInt(100) <= enchantChances7_9) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant7_9) + 1);
			}
			break;
		case 10:
			cItem = new CustomItem(levelWeapons10[random.nextInt(levelWeapons10.length)]);
			if (random.nextInt(100) <= enchantChances10) {
				cItem.enchant(Enchantment.DAMAGE_ALL, random.nextInt(maxEnchant10) + 1);
			}
			break;
		default:
			cItem = new CustomItem(Material.BARRIER);
			System.out.println("Error on making random weapon for zombie of level " + level + ". Max level = 10");
			break;
		}

		return cItem.build();
	}

	public void reloadArrayZombies10() {
		zombies10.clear();

		for (World w : Bukkit.getWorlds()) {
			for (LivingEntity e : w.getLivingEntities()) {
				if (e instanceof Zombie) {
					Zombie z = (Zombie) e;
					if (z.getCustomName() != null) {
						if (z.getCustomName().startsWith(levelPrefix)) {
							String name = ChatColor.stripColor(z.getCustomName());
							String[] parts = name.split(": ");
							try {
								int level = Integer.parseInt(parts[1]);
								if (level == 10) {
									zombies10.add(z);
								}
							} catch (Exception ex) {
								System.out.println("Cannot parse zombie's level on reloading array!");
							}
						}
					}
				}
			}
		}
	}
}
