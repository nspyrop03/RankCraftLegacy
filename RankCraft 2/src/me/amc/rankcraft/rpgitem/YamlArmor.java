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
import me.amc.rankcraft.utils.CustomItem;

public class YamlArmor extends YamlBase {
	
	private FileConfiguration c;
	//private String fileName;
	private File file;
	
	//private String name;
	private String material;
	private double defense;
	private String rarity;
	//private int minLevel;
	private List<String> enchantments;
	private boolean hasRecipe;
	private List<String> recipe;
	private boolean fromCreator;
	private ItemStack itemFromCreator;
	
	public YamlArmor(String fileName) {
		super(fileName, "", 0);
		this.fileName = fileName;
		
		File f = new File("plugins/RankCraft/armors/"+this.fileName);
		c = YamlConfiguration.loadConfiguration(f);
		
		file = f;
		
		if(!file.exists()) {
			try {
				f.createNewFile();
				createFile();
			} catch (IOException e) {
				MainCore.instance.getLogger().log(Level.SEVERE, "Cound not make "+fileName+"! Please restart your server!");
			}
		}
				
		material = c.getString("Material");
		name = c.getString("Name");
		name = name.replace('&', '§');
		name = name.replace('_', ' ');
		defense = c.getDouble("Defense");
		rarity = c.getString("Rarity");
		minLevel = c.getInt("MinLevel");
		enchantments = c.getStringList("Enchantments");
		hasRecipe = c.getBoolean("HasRecipe");
		recipe = c.getStringList("Recipe");
		fromCreator = c.getBoolean("FromCreator");
		itemFromCreator = c.getItemStack("ItemFromCreator");
	}

	private void createFile() {
		c.createSection("Material");
		c.set("Material", "LEATHER_HELMET");
		c.createSection("Name");
		c.set("Name", "NULL");
		c.createSection("Defense");
		c.set("Defense", 0.0);
		c.createSection("Rarity");
		c.set("Rarity", Rarity.COMMON.toString());
		c.createSection("MinLevel");
		c.set("MinLevel", 0);
		c.createSection("HasRecipe");
		c.set("HasRecipe", false);
		c.createSection("Recipe");
		c.set("Recipe", new ArrayList<String>());
		c.createSection("Enchantments");
		c.set("Enchantments", new ArrayList<String>());
		c.createSection("FromCreator");
		c.set("FromCreator", false);
		c.createSection("ItemFromCreator");
		c.set("ItemFromCreator", new ItemStack(Material.AIR));
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
	
	@SuppressWarnings("deprecation")
	public RpgArmor getRpgArmor() {
		RpgArmor a = new RpgArmor(Material.valueOf(material.toUpperCase()), defense, Rarity.valueOf(rarity.toUpperCase()), minLevel);
		if(!fromCreator) {
			a.getCustomItem().setName(name);
			for(String s : enchantments) {
				a.getCustomItem().enchant(Enchantment.getByName(s.split(":")[0]), Integer.parseInt(s.split(":")[1]));
			}
		} else {
			CustomItem ci = new CustomItem(itemFromCreator);
			/*
			ci.removeIfStart(ChatColor.GRAY+"Armor: ");
			ci.removeIfStart(ChatColor.GRAY+"Rarity: ");
			ci.removeIfStart(ChatColor.GRAY+"LevelToUse: ");
			ci.removeIfStart(" ");
			ci.removeIfStart(ChatColor.DARK_GRAY+"ArmorStats: ");
			*/
			ci.setName(name);
			a.setCustomItem(ci);
		}
		return a;
	}
	
	public void createRecipe() {
		if(hasRecipe) {
			YamlArmorRecipe yar = new YamlArmorRecipe(this);
			if(yar.getRecipe() != null) 
				MainCore.instance.getServer().addRecipe(yar.getRecipe());
		}
	}
	
	public void setFromCreator(boolean b) {
		this.fromCreator = b;
		c.set("FromCreator", b);
	}
	
	public void setItemFromCreator(ItemStack item) {
		this.itemFromCreator = item;
		c.set("ItemFromCreator", item);
	}
	
	public boolean isFromCreator() {
		return fromCreator;
	}

	public ItemStack getItemFromCreator() {
		return itemFromCreator;
	}

	public void setMaterial(Material mat) {
		this.material = mat.toString();
		c.set("Material", mat.toString());
	}
	
	public void setName(String name) {
		this.name = name;
		c.set("Name", name);
	}
	
	public void setDefense(double d) {
		if(d < 0.0) d = 0.0;
		this.defense = d;
		c.set("Defense", d);
	}
	
	public void setRarity(Rarity r) {
		this.rarity = r.toString();
		c.set("Rarity", r.toString());
	}
	
	public void setMinLevel(int level) {
		this.minLevel = level;
		c.set("MinLevel", level);
	}
	
	public void setHasRecipe(boolean b) {
		this.hasRecipe = b;
		c.set("HasRecipe", b);
	}
	
	public void setRecipe(List<String> r) {
		this.recipe = r;
		c.set("Recipe", r);
	}
	
	public void setEnchantments(List<String> e) {
		this.enchantments = e;
		c.set("Enchantments", e);
	}
	
	public boolean isHasRecipe() {
		return hasRecipe;
	}

	public List<String> getRecipe() {
		return recipe;
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
	
	public FileConfiguration getC() {
		return c;
	}

	/*
	public String getFileName() {
		return fileName;
	}
	 */
	public File getFile() {
		return file;
	}
	/*
	public String getName() {
		return name;
	}
	 */
	public String getMaterial() {
		return material;
	}

	public double getDefense() {
		return defense;
	}

	public String getRarity() {
		return rarity;
	}
	/*
	public int getMinLevel() {
		return minLevel;
	}
	 */
	public List<String> getEnchantments() {
		return enchantments;
	}
}
