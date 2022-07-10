package me.amc.rankcraft.mobs;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.CustomItem;

public class ArmorLists {

	public enum ArmorType {
		HELMET, CHESTPLATE, LEGGINGS, BOOTS;
	}

	public Material[] levelHelmets_1_3 = { Material.LEATHER_HELMET, Material.GOLDEN_HELMET };
	public Material[] levelHelmets_4_6 = { Material.CHAINMAIL_HELMET, Material.GOLDEN_HELMET };
	public Material[] levelHelmets_7_9 = { Material.CHAINMAIL_HELMET, Material.IRON_HELMET };
	public Material[] levelHelmets_10 = { Material.DIAMOND_HELMET, Material.IRON_HELMET };
	public Material[] levelChestplates_1_3 = { Material.LEATHER_CHESTPLATE, Material.GOLDEN_CHESTPLATE };
	public Material[] levelChestplates_4_6 = { Material.CHAINMAIL_CHESTPLATE, Material.GOLDEN_CHESTPLATE };
	public Material[] levelChestplates_7_9 = { Material.CHAINMAIL_CHESTPLATE, Material.IRON_CHESTPLATE };
	public Material[] levelChestplates_10 = { Material.DIAMOND_CHESTPLATE, Material.IRON_CHESTPLATE };
	public Material[] levelLeggings_1_3 = { Material.LEATHER_LEGGINGS, Material.GOLDEN_LEGGINGS };
	public Material[] levelLeggings_4_6 = { Material.CHAINMAIL_LEGGINGS, Material.GOLDEN_LEGGINGS };
	public Material[] levelLeggings_7_9 = { Material.CHAINMAIL_LEGGINGS, Material.IRON_LEGGINGS };
	public Material[] levelLeggings_10 = { Material.DIAMOND_LEGGINGS, Material.IRON_LEGGINGS };
	public Material[] levelBoots_1_3 = { Material.LEATHER_BOOTS, Material.GOLDEN_BOOTS };
	public Material[] levelBoots_4_6 = { Material.CHAINMAIL_BOOTS, Material.GOLDEN_BOOTS };
	public Material[] levelBoots_7_9 = { Material.CHAINMAIL_BOOTS, Material.IRON_BOOTS };
	public Material[] levelBoots_10 = { Material.DIAMOND_BOOTS, Material.IRON_BOOTS };

	public ArmorLists() {
		if (MainCore.instance.config.startupDebug) {
			System.out.println("Initializing armors for leveled mobs...");
		}
	}

	public CustomItem makeRandomArmor(int maxEnchant, ArmorType type, int level, int enchantChances1_3,
			int enchantChances4_6, int enchantChances7_9, int enchantChances10) {
		ItemStack stack;
		ItemMeta meta;
		Random random = new Random();

		switch (type) {
		case BOOTS:
			if (level <= 3) {
				stack = new ItemStack(levelBoots_1_3[random.nextInt(levelBoots_1_3.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances1_3) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			} else if (level >= 4 && level <= 6) {
				stack = new ItemStack(levelBoots_4_6[random.nextInt(levelBoots_4_6.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances4_6) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			} else if (level >= 7 && level <= 9) {
				stack = new ItemStack(levelBoots_7_9[random.nextInt(levelBoots_7_9.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances7_9) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			} else {
				stack = new ItemStack(levelBoots_10[random.nextInt(levelBoots_10.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances10) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			}
			break;
		case CHESTPLATE:
			if (level <= 3) {
				stack = new ItemStack(levelChestplates_1_3[random.nextInt(levelChestplates_1_3.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances1_3) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			} else if (level >= 4 && level <= 6) {
				stack = new ItemStack(levelChestplates_4_6[random.nextInt(levelChestplates_4_6.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances4_6) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			} else if (level >= 7 && level <= 9) {
				stack = new ItemStack(levelChestplates_7_9[random.nextInt(levelChestplates_7_9.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances7_9) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			} else {
				stack = new ItemStack(levelChestplates_10[random.nextInt(levelChestplates_10.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances10) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			}
			break;
		case HELMET:
			if (level <= 3) {
				stack = new ItemStack(levelHelmets_1_3[random.nextInt(levelHelmets_1_3.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances1_3) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			} else if (level >= 4 && level <= 6) {
				stack = new ItemStack(levelHelmets_4_6[random.nextInt(levelHelmets_4_6.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances4_6) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			} else if (level >= 7 && level <= 9) {
				stack = new ItemStack(levelHelmets_7_9[random.nextInt(levelHelmets_7_9.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances7_9) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			} else {
				stack = new ItemStack(levelHelmets_10[random.nextInt(levelHelmets_10.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances10) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			}
			break;
		case LEGGINGS:
			if (level <= 3) {
				stack = new ItemStack(levelLeggings_1_3[random.nextInt(levelLeggings_1_3.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances1_3) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			} else if (level >= 4 && level <= 6) {
				stack = new ItemStack(levelLeggings_4_6[random.nextInt(levelLeggings_4_6.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances4_6) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			} else if (level >= 7 && level <= 9) {
				stack = new ItemStack(levelLeggings_7_9[random.nextInt(levelLeggings_7_9.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances7_9) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			} else {
				stack = new ItemStack(levelLeggings_10[random.nextInt(levelLeggings_10.length)]);
				meta = stack.getItemMeta();
				if (random.nextInt(enchantChances10) <= 100) {
					meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, random.nextInt(maxEnchant) + 1, true);
				}
				stack.setItemMeta(meta);
			}
			break;
		default:
			stack = new ItemStack(Material.BARRIER);
			System.out.println("Error on making random armor for a zombie of level " + level + ". Max level = 10");
			break;

		}

		return new CustomItem(stack);
	}
}
