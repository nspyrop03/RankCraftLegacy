package me.amc.rankcraft.lang;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

import me.amc.rankcraft.yaml.YamlParser;

public class LangYaml {

	private YamlParser y;
	private FileConfiguration c;
	
	public LangYaml(String fileName, String folderName) {
		y = new YamlParser(new File("plugins/RankCraft/"+folderName), fileName, folderName);
		c = y.getConfig();
	}
	
	public String getMessage(String path) {
		//System.out.println("{"+c.+"}"+path+" exists = "+c.contains(path));
		return c.getString(path).replace('&', '§');
	}
	
	public FileConfiguration getConfig() {
		return this.c;
	}
	
	public String getMessageWithPrefix(String path) {
		return c.getString("Prefix").replace('&', '§')+"§f "+c.getString(path).replace('&', '§');
	}
}
