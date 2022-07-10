package me.amc.rankcraft.backpack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class BackpackUtils {

	public static void saveInventory(Inventory inventory, Player player) throws IOException {
		File f = new File(RCUtils.BACKPACK_DIRECTORY, RCUtils.textedUUID(player) + ".yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(f);
		List<ItemStack> items = new ArrayList<>();
		for (ItemStack it : inventory.getContents()) {

			items.add(it);
		}
		c.set("backpack.items", items);
		c.save(f);
	}

	@SuppressWarnings("unchecked")
	public static List<ItemStack> getBackpackItems(Player player) throws IOException {

		File f = new File(RCUtils.BACKPACK_DIRECTORY, RCUtils.textedUUID(player) + ".yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(f);
		List<ItemStack> content = ((List<ItemStack>) c.get("backpack.items"));

		return content;

	}

	public static void saveLines(Player player, int lines) throws IOException {
		File f = new File(RCUtils.BACKPACK_DIRECTORY, "lines.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(f);
		c.set(RCUtils.textedUUID(player), lines);
		c.save(f);
	}

	public static int getLinesFromFile(Player p) throws IOException {
		File f = new File(RCUtils.BACKPACK_DIRECTORY, "lines.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(f);
		return c.getInt(RCUtils.textedUUID(p));
	}

	public static void setLines(Player player, int lines) {
		try {
			if (lines < 1) {
				saveLines(player, 1);
			} else if (lines >= 1 && lines <= 6) {
				saveLines(player, lines);
			} else {
				saveLines(player, 6);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getTitle(Player p) {
		return p.getName() + "'s Backpack";
	}

	public static void initFor(Player p) {
		File f = new File(RCUtils.BACKPACK_DIRECTORY, "lines.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(f);
		if (!c.contains(RCUtils.textedUUID(p))) {
			try {
				saveLines(p, 1);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File f2 = new File(RCUtils.BACKPACK_DIRECTORY, RCUtils.textedUUID(p) + ".yml");
		if (!f2.exists()) {
			System.out.println("Backpack file does not exist!");
			try {
				saveInventory(Bukkit.createInventory(p, 9), p);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static boolean hasUpgrade(Player p) {
		try {
			if (getLinesFromFile(p) < 6) {
				return true;
			} else {
				return false;
			}
		} catch (IOException ex) {
			return false;
		}
	}
	
	public static float getUpgradeCost(Player p) {
		float cost = 0;
		
		try {
		//	cost = getLinesFromFile(p) * 200;
			cost = MainCore.instance.config.getBackpackUpgradeCost(getLinesFromFile(p)+1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return cost;
	}
}
