package me.amc.rankcraft.yaml;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class MobsYaml {

	private YamlParser y;
	private FileConfiguration c;
	
	public MobsYaml() {
		y = new YamlParser(new File("plugins/RankCraft"), "mobs.yml");
		c = y.getConfig();
	}
	
	public float getXP(String mob) {
		return (float)c.getDouble(mob+".Xp");
	}
	
	public float getGold(String mob) {
		return (float)c.getDouble(mob+".Gold");
	}
	
	public boolean getIfCanDropGold(String mob) {
		return c.getBoolean(mob+".DropGold");
	}
	
	public boolean getIfCanDropSpell(String mob) {
		return c.getBoolean(mob+".DropSpell");
	}
}
