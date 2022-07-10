package me.amc.rankcraft.spells;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class AntiSpellsData {

	private File dir = RCUtils.SPELLS_DB_DIRECTORY;
	private MainCore plugin;
	
	public AntiSpellsData() {
		plugin = MainCore.instance;
		load();
	}
	
	public void initForPlayer(Player p) {
		if(!AntiHungerSpell.inAntiHunger(p)) {
			AntiHungerSpell.setAntiHunger(p, false);
		}
		
		if(!AntiPoisonSpell.inAntiPoison(p)) {
			AntiPoisonSpell.setAntiPoison(p, false);
		}
	}
	
	public void save()  {
		try {
			
			YamlConfiguration stats = new YamlConfiguration();
			stats.createSection("AntiPoison", AntiPoisonSpell.antiPoison);
			stats.save(new File(dir+"/antipoison.yml"));
			
			stats = new YamlConfiguration();
			stats.createSection("AntiHunger", AntiHungerSpell.antiHunger);
			stats.save(new File(dir+"/antihunger.yml"));
			
		} catch(Exception ex) {
			plugin.getLogger().log(Level.SEVERE, "[SpellsDatabase] Save Failed!", ex);
		}
	}
	
	public void load() {
		try {
			YamlConfiguration config = new YamlConfiguration();
			
			File stats = new File(dir+"/antipoison.yml");
			if(stats.exists()) {
				config.load(stats);
				
				ConfigurationSection sec = config.getConfigurationSection("AntiPoison");
				for(String key : sec.getKeys(false)) {
					AntiPoisonSpell.antiPoison.put(key, sec.getBoolean(key));
				}
			} 
			
			stats = new File(dir+"/antihunger.yml");
			if(stats.exists()) {
				config.load(stats);
				
				ConfigurationSection sec = config.getConfigurationSection("AntiHunger");
				for(String key : sec.getKeys(false)) {
					AntiHungerSpell.antiHunger.put(key, sec.getBoolean(key));
				}
			}
			
			
		} catch(Exception ex) {
			plugin.getLogger().log(Level.SEVERE, "[SpellsDatabase] Load Failed!", ex);
		}
	}
	
}
