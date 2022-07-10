package me.amc.rankcraft.mobs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class YamlMob {

	//private YamlParser y;
	private FileConfiguration c;
	private String fileName;

	private String name;
	private String type; // (SKELETON/ZOMBIE) \\
	// private List<ItemStack> inventory = new ArrayList<>();
	private List<ItemStack> drops = new ArrayList<>();
	private int level;
	// private float hp;

	private String displayName;

	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack leggings;
	private ItemStack boots;
	private ItemStack hand;
	
	private boolean defaultSpawn;
	
	private boolean finished;
	
	private File f;

	private List<String> abilities = new ArrayList<>();
	
	public static final List<String> VALID_ABILITIES = new ArrayList<>(Arrays.asList(
			RCUtils.ZEUS_HIT, RCUtils.ICE_TOUCH, RCUtils.POISON_STING,
			RCUtils.HIDDEN_POWER, RCUtils.MINIONS_DEFENCE, RCUtils.WEB_ATTACK,
			RCUtils.MONSTER_FIST));
	
	private List<String> biomes = new ArrayList<>();
	
	private double health;
	
	@SuppressWarnings("unchecked")
	public YamlMob(String fileName) {
		this.fileName = fileName;
		
		File f = new File("plugins/RankCraft/mobs/"+fileName);
		this.f = f;
		if(!f.exists()) {
			try {
				f.createNewFile();
				if(!fileName.equals("DEFAULT_ZOMBIE") || !fileName.equals("DEFAULT_SLEKETON")) {
					createFile();
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		
		//y = new YamlParser(new File("plugins/RankCraft/mobs"), fileName, "mobs");
		
		c = YamlConfiguration.loadConfiguration(new File("plugins/RankCraft/mobs/"+fileName));
		
		
		
		name = c.getString("Name");
		name = name.replace('&', '§');
		name = name.replace('_', ' ');
		// type = c.getString("Type");

		if (c.getString("Type").equals("zombie")) {
			type = "ZOMBIE";
		} else if (c.getString("Type").equals("skeleton")) {
			type = "SKELETON";
		} else {
			type = "ZOMBIE";
		}

		// inventory = ((List<ItemStack>)c.getList("Inventory"));
		drops = ((List<ItemStack>) c.getList("Drops"));
		level = c.getInt("Level");
		// hp = (float)c.getDouble("HP");

		helmet = c.getItemStack("Inventory.helmet");
		chestplate = c.getItemStack("Inventory.chestplate");
		leggings = c.getItemStack("Inventory.leggings");
		boots = c.getItemStack("Inventory.boots");
		hand = c.getItemStack("Inventory.hand");
		
		defaultSpawn = c.getBoolean("DefaultSpawn");
		
		finished = c.getBoolean("Finished");

		if(c.contains("Abilities")) abilities = c.getStringList("Abilities");
		if(c.contains("Biomes")) biomes = c.getStringList("Biomes");
		
		if(c.contains("Health")) health = c.getDouble("Health");
		else health = 20.0;
		
		// displayName = ChatColor.YELLOW+"L"+level+" "+name+ChatColor.RED+"
		// HP:"+hp;
		
		//displayName = name + ChatColor.YELLOW + ChatColor.BOLD + " L." + ChatColor.RED + ChatColor.BOLD + level;
		displayName = ChatColor.YELLOW+"L."+level+ChatColor.WHITE+" "+name+ChatColor.RED+" HP:"+RCUtils.round(health, 1);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/*
	 * public List<ItemStack> getInventory() { return inventory; }
	 * 
	 * public void setInventory(List<ItemStack> inventory) { this.inventory =
	 * inventory; }
	 */
	
	public List<ItemStack> getDrops() {
		return drops;
	}

	public void setDrops(List<ItemStack> drops) {
		this.drops = drops;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getHealth() {
		return RCUtils.round(health, 1);
	}
	
	public void setHealth(double hp) {
		this.health = hp;
	}
	
	/*
	 * public float getHp() { return hp; }
	 * 
	 * public void setHp(float hp) { this.hp = hp; }
	 */
	
	public String getDisplayName() {
		return displayName;
	}

	public ItemStack getHelmet() {
		return helmet;
	}

	public void setHelmet(ItemStack helmet) {
		this.helmet = helmet;
	}

	public ItemStack getChestplate() {
		return chestplate;
	}

	public void setChestplate(ItemStack chestplate) {
		this.chestplate = chestplate;
	}

	public ItemStack getLeggings() {
		return leggings;
	}

	public void setLeggings(ItemStack leggings) {
		this.leggings = leggings;
	}

	public ItemStack getBoots() {
		return boots;
	}

	public void setBoots(ItemStack boots) {
		this.boots = boots;
	}

	public ItemStack getHand() {
		return hand;
	}

	public void setHand(ItemStack hand) {
		this.hand = hand;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isDefaultSpawn() {
		return defaultSpawn;
	}

	public void setDefaultSpawn(boolean defaultSpawn) {
		this.defaultSpawn = defaultSpawn;
	}

	public String getFileName() {
		return fileName;
	}

//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//	}

	public void save() {
		try {
			
			c.save(new File("plugins/RankCraft/mobs/" + fileName));
			c.load(new File("plugins/RankCraft/mobs/" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
	
//	@SuppressWarnings("static-access")
	public void createFile()  {
		
		List<String> lines = new ArrayList<>();
		File example = new File("plugins/RankCraft/mobs/DEFAULT_ZOMBIE.yml");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(example.getPath()));
			String line = null;
		
			while((line=reader.readLine()) != null) {
				lines.add(line);
			}
			
			reader.close();
			
			FileWriter writer = new FileWriter(f);
			for(String s : lines) {
				writer.write(s+"\n");
			}
			
			writer.flush();
			writer.close();
			
	//		c.set("Name", null);
			
	//		save();
		
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	/*		
		c.set("Name", "Nameless");
		c.set("Type", "zombie");
		c.set("Inventory.helmet", new ItemStack(Material.AIR));
		c.set("Inventory.chestplate", new ItemStack(Material.AIR));
		c.set("Inventory.leggings", new ItemStack(Material.AIR));
		c.set("Inventory.boots", new ItemStack(Material.AIR));
		c.set("Inventory.hand", new ItemStack(Material.AIR));
		c.set("Level", 1);
		c.set("Drops", new ArrayList<>());
		c.set("DefaultSpawn", "false");
		save();
	*/
	/*	
		c.createSection("Name");
		c.set("Name", "nameless");
	//	c.createPath(c.getConfigurationSection("Name"), "nameless");
		
		c.createSection("Type");
		c.set("Type", "zombie");
	//	c.createPath(c.getConfigurationSection("Type"), "zombie");
		
		c.createSection("Inventory.helmet");
	//	c.createPath(c.getConfigurationSection("Invetnory.helmet"), new ItemStack(Material.AIR));
		c.set("Inventory.helmet", new ItemStack(Material.AIR));
		c.createSection("Inventory.chestplate");
		c.set("Inventory.chestplate", new ItemStack(Material.AIR));
		c.createSection("Inventory.leggings");
		c.set("Inventory.leggings", new ItemStack(Material.AIR));
		c.createSection("Inventory.boots");
		c.set("Inventory.boots", new ItemStack(Material.AIR));
		c.createSection("Inventory.hand");
		c.set("Inventory.hand", new ItemStack(Material.AIR));
	//	c.createSection("Inventory.helmet");
		
		c.createSection("Level");
		c.set("Level", "1");
		
		c.createSection("Drops");
		c.set("Drops", new ArrayList<>());
		
		c.createSection("DefaultSpawn");
		c.set("DefaultSpawn", "false");
		
		save();
		
	*/
		
		
		
	}

	public void makeMob(LivingEntity e) {
		e.setCustomName(displayName);
		e.setCustomNameVisible(MainCore.instance.config.alwaysDisplayName);
		e.getEquipment().setBoots(boots);
		e.getEquipment().setChestplate(chestplate);
		e.getEquipment().setHelmet(helmet);
		e.getEquipment().setLeggings(leggings);
		//e.getEquipment().setItemInHand(hand);
		e.getEquipment().setItemInMainHand(hand);
	}

	public FileConfiguration getC() {
		return c;
	}

	public void setC(FileConfiguration c) {
		this.c = c;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public List<String> getAbilities() {
		return abilities;
	}

	public List<String> getAbilitiesNames() {
		List<String> names = new ArrayList<>();
		for(String s : abilities) {
			names.add(s.split(":")[0]);
		}
		return names;
	}
	
	public void setAbilities(List<String> abilities) {
		this.abilities = abilities;
	}
	
	public void addAbility(String a) {
		this.abilities.add(a);
	}
	
	public boolean hasAbility(String a) {
		if(VALID_ABILITIES.contains(a)) {
			for(String s : abilities) {
				if(s.startsWith(a)) 
					return true;
			}
		}
		return false;
	}
	
	public int getAbilityChances(String a) {
		if(hasAbility(a)) {
			for(String s : abilities) {
				if(s.startsWith(a)) {
					String parts[] = s.split(":");
					int c = Integer.parseInt(parts[1]);
					return c;
				}
			}
		}
		return 0;
	}
	
	public boolean isValidAbility(String a) {
		return VALID_ABILITIES.contains(a);
	}
	
	public void setBiomes(List<String> b) {
		this.biomes = b;
	}
	
	public List<String> getStringBiomes() {
		return biomes;
	}
	
	public List<Biome> getBiomes() {
		List<Biome> b = new ArrayList<>(); 
		for(String s : biomes) {
			if(s.equalsIgnoreCase("all"))
				return new ArrayList<Biome>(Arrays.asList(Biome.values()));
			b.add(Biome.valueOf(s.toUpperCase()));
			
		}
		return b;
	}

	public void addBiome(String s) {
		if(s.equalsIgnoreCase("all") || Biome.valueOf(s.toUpperCase()) != null) {
			biomes.add(s.toUpperCase());
		}
	}
	
	
	
}
