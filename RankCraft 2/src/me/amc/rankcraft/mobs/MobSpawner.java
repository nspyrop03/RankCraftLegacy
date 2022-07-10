package me.amc.rankcraft.mobs;

import java.io.File;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;

import me.amc.rankcraft.utils.CustomItem;

public class MobSpawner {

	private CustomItem spawnerItem;
	private int x, y, z;
	private String world;
	private String mob;
	private int sec;
	private int every;
	private int range;

	private File file;
	private FileConfiguration c;

	private ArmorStand name;
	private ArmorStand subName;

	public MobSpawner(String mob, int every, int range) {

		this.mob = mob;
		this.every = every;
		this.range = range;

		this.spawnerItem = new CustomItem(Material.OBSIDIAN, "Spawner:" + mob + "-" + every + "-" + range).build();

	}

	public MobSpawner(String fileName) {

		this.file = new File("plugins/RankCraft/spawners/" + fileName);
		c = YamlConfiguration.loadConfiguration(this.file);

		this.x = c.getInt("Location.x");
		this.y = c.getInt("Location.y");
		this.z = c.getInt("Location.z");
		this.world = c.getString("Location.world");
		this.mob = c.getString("Options.mob");
		this.sec = c.getInt("Options.sec");
		this.every = c.getInt("Options.every");
		this.range = c.getInt("Options.range");

	}

	public CustomItem getSpawnerItem() {
		return spawnerItem;
	}

	public void setSpawnerItem(CustomItem spawnerItem) {
		this.spawnerItem = spawnerItem;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	public int getSec() {
		return sec;
	}

	public void setSec(int sec) {
		this.sec = sec;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public FileConfiguration getC() {
		return c;
	}

	public void setC(FileConfiguration c) {
		this.c = c;
	}

	public int getEvery() {
		return every;
	}

	public void setEvery(int every) {
		this.every = every;
	}

	public void makeFile(String fileName) {
		if (this.file == null) {

			File f = new File("plugins/RankCraft/spawners/" + fileName);

			if (!f.exists()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void writeFile() {
		if (getFile() != null) {
			c = YamlConfiguration.loadConfiguration(getFile());
			getC().addDefault("Location.x", x);
			getC().addDefault("Location.y", y);
			getC().addDefault("Location.z", z);
			getC().addDefault("Location.world", world);
			getC().addDefault("Options.mob", mob);
			getC().addDefault("Options.sec", sec);
			getC().addDefault("Options.every", every);
			getC().addDefault("Options.range", range);
			getC().options().copyDefaults(true);
			save();
		}
	}

	public void save() {
		try {
			if (!(this.file == null)) {
				c.save(this.file);
				c.load(this.file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public ArmorStand getName() {
		return name;
	}

	public void setName(ArmorStand name) {
		this.name = name;
	}

	public ArmorStand getSubName() {
		return subName;
	}

	public void setSubName(ArmorStand subName) {
		this.subName = subName;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

}
