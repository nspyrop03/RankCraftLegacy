package me.amc.rankcraft.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.rpgitem.YamlArmor;
import me.amc.rankcraft.rpgitem.YamlItem;
import me.clip.placeholderapi.PlaceholderAPI;

public class LevelFileReward {

	private FileConfiguration c;
	private String rewardFileName;
	
	public LevelFileReward(String fileName) {
		rewardFileName = fileName;
		File f = new File("plugins/RankCraft/levels/"+fileName);
		c = YamlConfiguration.loadConfiguration(f);
		try {
			c.load(f);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void sendReward(Player p) {
		
		// Send the messages
		for(String s : getMessages()) {
			if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				s = PlaceholderAPI.setPlaceholders(p, s);
			}
			p.sendMessage(MainCore.instance.language.getPrefix()+s.replace('&', '§'));
		}
		
		// Send title and subtitle
		String title = getTitle().replace('&', '§');
		String subTitle = getSubTitle().replace('&', '§');
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			title = PlaceholderAPI.setPlaceholders(p, title);
			subTitle = PlaceholderAPI.setPlaceholders(p, subTitle);
		}
		MainCore.instance.rankCraft.sendTitle(p, 5, 30, 10, title, subTitle);

		// Give gold
		MainCore.instance.rankCraft.gold.addGold(p, (float)getGold());
		
		// Execute Commands from console
		for(String command : getCommands()) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", p.getName()));
		}
		
		if(!getRpgItems().isEmpty()) {
			for(String s : getRpgItems()) {
				String[] parts = s.split(":");
				String name = parts[0];
				int amount = 1;
				try {
					amount = Integer.parseInt(parts[1]);
				} catch(Exception ex) {
					MainCore.instance.getLogger().log(Level.WARNING, "Amount must be a number: "+s+" ("+rewardFileName+")!");
				}
				if(MainCore.instance.rankCraft.yamlFilenameExists(name)) {
					if(MainCore.instance.rankCraft.isYamlWeapon(name)) {
						YamlItem weapon = MainCore.instance.rankCraft.getYamlWeaponFromFileName(name);
						for(int i = 0; i < amount; i++) p.getInventory().addItem(weapon.getBuiltRpgItem().getItem());
					} else {
						YamlArmor armor = MainCore.instance.rankCraft.getYamlArmorFromFileName(name+".armor");
						for(int i = 0; i < amount; i++) p.getInventory().addItem(armor.getRpgArmor().build().getItem());
					}
 				} else {
					MainCore.instance.getLogger().log(Level.WARNING, "Wrong RPGItem filename: "+name+" ("+rewardFileName+")!");
				}
			}
		}
		
	}
	
	public int getLevel() {
		return c.getInt("Level");
	}
	
	public double getGold() {
		return c.getDouble("Reward.Gold");
	}
	
	public String getTitle() {
		return c.getString("Reward.SpecialTitle");
	}
	
	public String getSubTitle() {
		return c.getString("Reward.SpecialSubTitle");
	}
	
	public List<String> getCommands() {
		return c.getStringList("Reward.CommandsToExecute");
	}
	
	public List<String> getMessages() {
		return c.getStringList("Reward.Messages");
	}
	
	public List<String> getRpgItems() {
		return c.getStringList("Reward.RpgItems");
	}
}
