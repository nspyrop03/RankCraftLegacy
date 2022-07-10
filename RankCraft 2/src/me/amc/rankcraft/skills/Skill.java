package me.amc.rankcraft.skills;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public abstract class Skill implements SkillsMap{

	private String name;
	private int maxLevel = 10;
	private String fileName;
	
	public Skill(int maxLevel, String name, String fileName) {
		this.name = name;
		this.maxLevel = maxLevel;
		this.fileName = fileName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void initForPlayer(Player p) {
		if(!getSkillPoints().containsKey(RCUtils.textedUUID(p))) {
			getSkillPoints().put(RCUtils.textedUUID(p), 0);
		}
	}
	
	public void setPoints(Player p, int pts) {
		getSkillPoints().put(RCUtils.textedUUID(p), pts);
		save();
	}
	
	public int getPoints(Player p) {
		return getSkillPoints().get(RCUtils.textedUUID(p));
	}
 	
	public void addPoints(Player p, int pts) {
		setPoints(p, getPoints(p) + pts);
	}
	
	public void removePoints(Player p, int pts) {
		setPoints(p, getPoints(p) - pts);
	}
	
	public void save() {
		
		try {
			
			YamlConfiguration stats = new YamlConfiguration();
			stats.createSection("points", getSkillPoints());
			stats.save(new File(RCUtils.SKILLS_DIRECTORY, fileName));
			
		} catch(Exception ex) {
			MainCore.instance.getLogger().log(Level.SEVERE, "[SkillsSaver] ("+name+") Save Failed!", ex);
		}
		
	}
	
	public void load() {
		try {
		
			File stats = new File(RCUtils.SKILLS_DIRECTORY, fileName);
			if(stats.exists()) {
				
				YamlConfiguration config = new YamlConfiguration();
				config.load(stats);
				
				ConfigurationSection sec = config.getConfigurationSection("points");
				
				
				for(String key : sec.getKeys(false)) {
					getSkillPoints().put(key, sec.getInt(key));
				}
				
			} 
			
			
		} catch(Exception ex) {
			MainCore.instance.getLogger().log(Level.SEVERE, "[SkillsLoader] ("+name+") Load Failed!", ex);
		}
	}
}
