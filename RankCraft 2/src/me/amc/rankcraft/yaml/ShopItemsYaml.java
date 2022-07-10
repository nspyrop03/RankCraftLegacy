package me.amc.rankcraft.yaml;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import me.amc.rankcraft.MainCore;

public class ShopItemsYaml {

	private YamlParser y;
	private FileConfiguration c;
	
	public ShopItemsYaml() {
		y = new YamlParser(MainCore.instance.getDataFolder(), "shop_config.yml");
		c = y.getConfig();
	}
	
	public List<String> getItemsList() {
		return c.getStringList("Items");
	}
	
	public FileConfiguration getConfig() {
		return c;
	}
}
