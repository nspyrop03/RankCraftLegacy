package me.amc.rankcraft.rpgitem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.amc.rankcraft.MainCore;

public class YamlMaterial {

	private FileConfiguration c;
	private File file;
	
	private String fileName;
	private String displayName;
	private String material;
	private String rarity;
	
	private List<String> recipe;
	
	private int customModelId;
	
	private boolean hasRecipe;
	
	public YamlMaterial(String fileName, String displayName, Material mat, int customModelId) {
		this.fileName = fileName;
		this.displayName = displayName;
		this.customModelId = customModelId;
		this.material = mat.toString();
		
		File f = new File("plugins/RankCraft/items/"+this.fileName);
		this.c = YamlConfiguration.loadConfiguration(f);
		
		this.file = f;
		
		if(!file.exists()) {
			try {
				f.createNewFile();
				createFile();
			} catch (IOException e) {
				MainCore.instance.getLogger().log(Level.SEVERE, "Cound not make "+fileName+"! Please restart your server!");
			}
		} else {
			material = c.getString("Material");
			displayName = c.getString("Name");
			displayName = displayName.replace('&', '§');
			displayName = displayName.replace('_', ' ');
			rarity = c.getString("Rarity");
			hasRecipe = c.getBoolean("HasRecipe");
			recipe = c.getStringList("Recipe");
		}
	}
	
	private void createFile() {
		c.createSection("Material");
		c.set("Material", "BONE");
		c.createSection("Name");
		c.set("Name", "NULL");
		c.createSection("Rarity");
		c.set("Rarity", Rarity.COMMON.toString());
		c.createSection("HasRecipe");
		c.set("HasRecipe", false);
		c.createSection("Recipe");
		c.set("Recipe", new ArrayList<String>());
		save();
	}
	
	public void save() {
		try {
			c.save(file);
			c.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public String getFileName() {
		return fileName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getCustomModelId() {
		return customModelId;
	}
	
	public Material getMaterial() {
		return Material.getMaterial(material.toUpperCase());
	}
	
	public boolean isHasRecipe() {
		return hasRecipe;
	}

	public List<String> getRecipe() {
		return recipe;
	}
	
	public Rarity getRarity() {
		return Rarity.valueOf(rarity.toUpperCase());
	}
	
	public List<Material> getRecipeMaterials() {
		List<Material> mats = new ArrayList<>();
		
		for(int i = 0; i < 3; i++) {
			String[] line = getRecipe().get(i).split(",");
			for(int j = 0; j < 3; j++) {
				mats.add(Material.getMaterial(line[j]));
			}
		}
		
		return mats;
	}
	
	/*
	public void createRecipe() {
		if(hasRecipe) {
			YamlArmorRecipe yar = new YamlArmorRecipe(this);
			if(yar.getRecipe() != null) 
				MainCore.instance.getServer().addRecipe(yar.getRecipe());
		}
	}
	*/
}
