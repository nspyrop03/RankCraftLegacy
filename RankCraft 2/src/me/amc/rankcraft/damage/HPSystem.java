package me.amc.rankcraft.damage;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class HPSystem {

	private JavaPlugin plugin;

	// String = UUID
	private HashMap<String, Double> hp = new HashMap<String, Double>();
	// String = UUID
	private HashMap<String, Double> maxHP = new HashMap<String, Double>();

	private String fileName = "hp_database";

	public HPSystem(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public void setMaxHP(Player p, double max) {
		this.maxHP.put(RCUtils.textedUUID(p), max);
		this.save();
	}

	public double getMaxHP(Player p) {
		return this.maxHP.get(RCUtils.textedUUID(p));
	}

	public void setHP(Player p, double hp) {
		if (hp <= getMaxHP(p)) {
			this.hp.put(RCUtils.textedUUID(p), hp);
		} else {
			this.hp.put(RCUtils.textedUUID(p), getMaxHP(p));
		}
		this.save();
	}

	public double getHP(Player p) {
		return this.hp.get(RCUtils.textedUUID(p));
	}

	public void increaseMaxHP(Player p, double increasement) {
		setMaxHP(p, getMaxHP(p) + increasement);
	}

	public void addHP(Player p, double hp) {
		double todoHP = getHP(p) + hp;

		if (todoHP <= getMaxHP(p)) {
			setHP(p, todoHP);
		} else {
			setHP(p, getMaxHP(p));
		}
	}

	public void removeHP(Player p, double hp) {
		setHP(p, getHP(p) - hp);

	//	if (getHP(p) <= 0) {
	//		p.setHealth(0);
	//	}
		
	}

	public void fillHP(Player p) {
		setHP(p, getMaxHP(p));
	}

	public void initHPSystem(Player p) {
		if (!this.maxHP.containsKey(RCUtils.textedUUID(p))) {
			//setMaxHP(p, 20.0);
			setMaxHP(p, MainCore.instance.config.startingHP);
		}
		if (!this.hp.containsKey(RCUtils.textedUUID(p))) {
			setHP(p, getMaxHP(p));
		}
	}

	public void load() {
		try {

			File stats = new File(plugin.getDataFolder(), fileName);
			if (stats.exists()) {

				YamlConfiguration config = new YamlConfiguration();
				config.load(stats);

				ConfigurationSection secHP = config.getConfigurationSection("hp");
				ConfigurationSection secMaxHP = config.getConfigurationSection("maxHP");

				for (String key : secHP.getKeys(false)) {
					hp.put(key, secHP.getDouble(key));
				}

				for (String key : secMaxHP.getKeys(false)) {
					maxHP.put(key, secMaxHP.getDouble(key));
				}

			}

		} catch (Exception ex) {
			plugin.getLogger().log(Level.SEVERE, "[HPSystem] Load Failed !", ex);
		}
	}

	public void save() {
		try {

			YamlConfiguration stats = new YamlConfiguration();
			stats.createSection("hp", hp);
			stats.createSection("maxHP", maxHP);

			stats.save(new File(plugin.getDataFolder(), fileName));

		} catch (Exception ex) {
			plugin.getLogger().log(Level.SEVERE, "[HPSystem] Save Failed !", ex);
		}
	}

	public String getHealthBar(Player p, int hpBars) {
		StringBuilder b = new StringBuilder();
	//	System.out.println("b length 0: "+b.length());
		String r = "|";
		String g = "|";
		int hpInt = (int)getHP(p);
		int maxHPInt = (int)getMaxHP(p);
		
		int x = (hpInt * hpBars) / maxHPInt;
	//	System.out.println("x: "+x);
		b.append(ChatColor.RED.toString());
		for(int i = 0; i < x; i++) {
			b.append(r);
	//		System.out.println("1st i: "+i);
		}
	//	System.out.println("b lenght 1: "+b.length());
		b.append(ChatColor.GRAY.toString());
		for(int i = 0; i < (hpBars-x); i++) {
			b.append(g);
	//		System.out.println("2nd i: "+i);
		}
	//	System.out.println("b lenght 2: "+b.length());
		
		String healthBar = b.toString();
	//	System.out.println("b lenght check: "+b.length());
		
		return healthBar;
	}
	
	public boolean areAllOk(Player p) {
		String uuid = RCUtils.textedUUID(p);
		if(hp.containsKey(uuid) && maxHP.containsKey(uuid)) {
			return true;
		} else {
			return false;
		}
	}
}
