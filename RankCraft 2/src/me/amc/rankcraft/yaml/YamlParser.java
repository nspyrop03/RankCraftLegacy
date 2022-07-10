package me.amc.rankcraft.yaml;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


import me.amc.rankcraft.MainCore;

public class YamlParser {

	private FileConfiguration config;
	
	public YamlParser(File directory, String fileName, String folderName) {
		
		if(!directory.exists()) {
			directory.mkdirs();
		}
		
		File f = new File(directory+"/"+fileName);
		
		if(!f.exists()) {
			f.getParentFile().mkdirs();
		/*	
			try {
				f.createNewFile(); // added to help yaml mob files
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		*/
			MainCore.instance.saveResource(folderName+"/"+fileName, false);
		}
		
		config = new YamlConfiguration();
		
		try {
			config.load(directory+"/"+fileName);
		} catch(InvalidConfigurationException | IOException e) {
		//	e.printStackTrace();
			MainCore.instance.getLogger().log(Level.SEVERE, "Could not load "+directory+"\\"+fileName);
			MainCore.instance.getLogger().log(Level.SEVERE, "StackTrace: ");
			e.printStackTrace();
		}
	}
	
	public YamlParser(File directory, String fileName) {
		if(!directory.exists()) {
			directory.mkdirs();
		}
		
		File f = new File(directory+"/"+fileName);
		
		if(!f.exists()) {
			f.getParentFile().mkdirs();
			MainCore.instance.saveResource(fileName, false);
		}
		
		config = new YamlConfiguration();
		
		try {
			config.load(directory+"/"+fileName);
		} catch(InvalidConfigurationException | IOException e) {
		//	e.printStackTrace();
		}
	}
	
	public FileConfiguration getConfig() {
		return config;
	}
}
