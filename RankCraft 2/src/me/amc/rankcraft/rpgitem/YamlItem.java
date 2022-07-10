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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;

public class YamlItem extends YamlBase/*extends RpgItem*/{

	//private YamlParser y;
	private FileConfiguration config;
	//private String weapon;
	
	private String material;
	private double minDamage;
	private double maxDamage;
	private int criticalLuck;
	private String rarity;
	private List<String> enchantments;
	private boolean hasRecipe;
	private boolean isSwordRecipe;
	private List<String> recipe;
	private boolean fromCreator;
	private ItemStack itemFromCreator;

	private File file;
	
	public YamlItem(String fileName) {	
		super(fileName, "", 0);
		//weapon = name;
		
		// New code for file auto creation
		File file = new File("plugins/RankCraft/weapons/"+fileName+".weapon");
		this.file = file;
		config = YamlConfiguration.loadConfiguration(file);
		
		if(!file.exists()) {
			try {
				file.createNewFile();
				createFile(config);
			} catch (IOException e) {
				MainCore.instance.getLogger().log(Level.WARNING, name+"'s file cound not be created! Please restart your server!");
			}
		}
		////////////////////\\\\\\\\\\\\\\\\\\\\
		
		//y = new YamlParser(new File("plugins/RankCraft/weapons"), name+".weapon", "weapons");
		//config = y.getConfig();

		material = config.getString("Material");
		name = config.getString("Name");
		name = name.replace('&', '§');
		name = name.replace('_', ' ');
		minDamage = config.getDouble("MinDamage");
		maxDamage = config.getDouble("MaxDamage");
		criticalLuck = config.getInt("CriticalLuck");
		minLevel = config.getInt("MinLevel");
		rarity = config.getString("Rarity");
		hasRecipe = config.getBoolean("HasRecipe");
		isSwordRecipe = config.getBoolean("IsSwordRecipe");
		enchantments = config.getStringList("Enchantments");
		recipe = config.getStringList("Recipe");
		fromCreator = config.getBoolean("FromCreator");
		itemFromCreator = config.getItemStack("ItemFromCreator");
		
		/*
		this.setMaterial(Material.valueOf(config.getString("Material")));
		this.setDisplayName(config.getString("Name").replace('&', '§'));
		
		this.setItem(new ItemStack(this.getMaterial()));
		this.setMeta(getItem().getItemMeta());
		
		this.setMinDamage(config.getDouble("MinDamage"));
		this.setMaxDamage(config.getDouble("MaxDamage"));
		this.setCriticalLuck(config.getInt("CriticalLuck"));
		this.setRarity(Rarity.valueOf((config.getString("Rarity"))));
		this.setLevelToUse(config.getInt("MinLevel"));
		
	//	System.out.println(config.getStringList("Enchantments") == null);
	//	System.out.println(config.getStringList("Enchantments"));
	//	System.out.println(getMeta() == null);
		
		for(int i = 0; i < config.getStringList("Enchantments").size(); i++) {
			String[] parts = config.getStringList("Enchantments").get(i).split(":");
	//		System.out.println(parts[0]);
	//		System.out.println(parts[1]);
			this.enchant(Enchantment.getByName(parts[0]), Integer.parseInt(parts[1]));
		}

		this.build();
		 */
	}
	
	private void createFile(FileConfiguration config) {
		config.createSection("Material");
		config.set("Material", "STONE_SWORD");
		config.createSection("Name");
		config.set("Name", "Nameless");
		config.createSection("MinDamage");
		config.set("MinDamage", 0.0);
		config.createSection("MaxDamage");
		config.set("MaxDamage", 0.0);
		config.createSection("CriticalLuck");
		config.set("CriticalLuck", 0);
		config.createSection("Rarity");
		config.set("Rarity", Rarity.COMMON.name());
		config.createSection("MinLevel");
		config.set("MinLevel", 0);
		config.createSection("HasRecipe");
		config.set("HasRecipe", false);
		config.createSection("IsSwordRecipe");
		config.set("IsSwordRecipe", false);
		config.createSection("Recipe");
		config.set("Recipe", new ArrayList<String>());
		config.createSection("Enchantments");
		config.set("Enchantments", new ArrayList<String>());
		config.createSection("FromCreator");
		config.set("FromCreator", false);
		config.createSection("ItemFromCreator");
		config.set("ItemFromCreator", new ItemStack(Material.AIR));
		save();
	}
	
	public void save() {
		try {
			config.save(file);
			config.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public RpgItem getBuiltRpgItem() {
		RpgItem r;
		if(fromCreator) {
			r = new RpgItem(itemFromCreator);
		} else {
			r = new RpgItem(Material.getMaterial(material));
			for(String s : enchantments) {
				r.enchant(Enchantment.getByName(s.split(":")[0]), Integer.parseInt(s.split(":")[1]));
			}
		}
		r.setDisplayName(name);
		r.setMinDamage(minDamage);
		r.setMaxDamage(maxDamage);
		r.setCriticalLuck(criticalLuck);
		r.setRarity(Rarity.valueOf(rarity.toUpperCase()));
		r.setLevelToUse(minLevel);
		r.build();
		return r;
	}
	
	public void createRecipe() {
		YamlRecipe recipe = new YamlRecipe(getBuiltRpgItem().getItem(), config, fileName);
		//if(YamlRecipe.itemHasRecipe) {
		//System.out.println("eee "+recipe.isItemHasRecipe());
		//System.out.println("name: "+fileName);
		//System.out.println(config == null);
		if(recipe.isItemHasRecipe()) {
			if(recipe != null && recipe.getRecipe() != null) {
				
				//if(MainCore.instance.getServer().getRecipesFor(getBuiltRpgItem().getItem()).isEmpty()) // check if duplicate recipe
				try {
					MainCore.instance.getServer().addRecipe(recipe.getRecipe());
				} catch(Exception ex) {
					
				}
				
				
			}
		}
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
	
	public List<Material> getSwordRecipeMaterials() {
		List<Material> mats = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
			mats.add(Material.getMaterial(getRecipe().get(i)));
		}
		return mats;
	}
	
	public void setName(String name) {
		this.name = name.replace('&', '§').replace('_', ' ');
		config.set("Name", name);
	}

	public String getName() {
		return name;
	}
	
	public void setMinLevel(int l) {
		this.minLevel = l;
		config.set("MinLevel", l);
	}
	
	public int getMinLevel() {
		return minLevel;
	}
	
	public FileConfiguration getConfig() {
		return config;
	}

	public void setConfig(FileConfiguration config) {
		this.config = config;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(Material mat) {
		this.material = mat.name();
		config.set("Material", mat.name());
	}

	public double getMinDamage() {
		return minDamage;
	}

	public void setMinDamage(double minDamage) {
		if(minDamage <0.0) minDamage = 0.0;
		this.minDamage = minDamage;
		config.set("MinDamage", minDamage);
	}

	public double getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(double maxDamage) {
		if(maxDamage<0.0) maxDamage = 0.0;
		this.maxDamage = maxDamage;
		config.set("MaxDamage", maxDamage);
	}

	public int getCriticalLuck() {
		return criticalLuck;
	}

	public void setCriticalLuck(int criticalLuck) {
		if(criticalLuck <0) criticalLuck = 0;
		this.criticalLuck = criticalLuck;
		config.set("CriticalLuck", criticalLuck);
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(Rarity r) {
		this.rarity = r.name();
		config.set("Rarity", r.name());
		
	}

	public List<String> getEnchantments() {
		return enchantments;
	}

	public void setEnchantments(List<String> enchantments) {
		this.enchantments = enchantments;
		config.set("Enchantments", enchantments);
	}

	public boolean isHasRecipe() {
		return hasRecipe;
	}

	public void setHasRecipe(boolean hasRecipe) {
		this.hasRecipe = hasRecipe;
		config.set("HasRecipe", hasRecipe);
	}

	public boolean isSwordRecipe() {
		return isSwordRecipe;
	}

	public void setSwordRecipe(boolean isSwordRecipe) {
		this.isSwordRecipe = isSwordRecipe;
		config.set("IsSwordRecipe", isSwordRecipe);
	}

	public List<String> getRecipe() {
		return recipe;
	}

	public void setRecipe(List<String> recipe) {
		this.recipe = recipe;
		config.set("Recipe", recipe);
	}

	public boolean isFromCreator() {
		return fromCreator;
	}

	public void setFromCreator(boolean fromCreator) {
		this.fromCreator = fromCreator;
		if(!config.contains("FromCreator"))
			config.createSection("FromCreator");
		config.set("FromCreator", fromCreator);
	}

	public ItemStack getItemFromCreator() {
		return itemFromCreator;
	}

	public void setItemFromCreator(ItemStack itemFromCreator) {
		this.itemFromCreator = itemFromCreator;
		if(!config.contains("ItemFromCreator"))
			config.createSection("ItemFromCreator");
		config.set("ItemFromCreator", itemFromCreator);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	
	
}
