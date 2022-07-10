package me.amc.rankcraft.rpgitem;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

import me.amc.rankcraft.utils.RCUtils;

@SuppressWarnings("deprecation")
public class YamlRecipe {

	private ShapedRecipe recipe;
	private FileConfiguration config;
	private ItemStack result;
	
	private String weaponName = "nameless";
	
	//protected static boolean itemHasRecipe = false;
	private boolean itemHasRecipe = false;
	private boolean isSwordRecipe = false;
	
	public YamlRecipe(ItemStack result, FileConfiguration config, String weaponName) {
		this.result = result;
		this.recipe = new ShapedRecipe(RCUtils.getKeyForRecipe(weaponName), this.result);
		this.config = config;
		this.weaponName = weaponName;
		
		itemHasRecipe = config.getBoolean("HasRecipe");
		this.isSwordRecipe = config.getBoolean("IsSwordRecipe");
		
		if(itemHasRecipe) {

			if(!this.isSwordRecipe) {
				
				int id = 0;
				String[] letters = new String[] {"A","B","C","D","E","F","G","H","I"};
				
				String[] line1 = config.getStringList("Recipe").get(0).split(",");
				String[] line2 = config.getStringList("Recipe").get(1).split(",");
				String[] line3 = config.getStringList("Recipe").get(2).split(",");
				
				String[] line1old = config.getStringList("Recipe").get(0).split(",");
				String[] line2old = config.getStringList("Recipe").get(1).split(",");
				String[] line3old = config.getStringList("Recipe").get(2).split(",");
				
				for(int i = 0; i < line1.length; i++) {
					if(line1[i].equals("0")) {
						line1[i] = " ";
					} else {
						line1[i] = letters[id];
						id++;
					}
				}
				
				for(int i = 0; i < line2.length; i++) {
					if(line2[i].equals("0")) {
						line2[i] = " ";
					} else {
						line2[i] = letters[id];
						id++;
					}
				}
				
				for(int i = 0; i < line3.length; i++) {
					if(line3[i].equals("0")) {
						line3[i] = " ";
					} else {
						line3[i] = letters[id];
						id++;
					}
				}
				
				recipe.shape
				(
						line1[0]+line1[1]+line1[2],
						line2[0]+line2[1]+line2[2],
						line3[0]+line3[1]+line3[2]
				);
				
				for(int i = 0; i < line1old.length; i++) {
					if(line1[i] != " ") {
						recipe.setIngredient(line1[i].charAt(0), new MaterialData(Material.getMaterial(line1old[i])));
						
					}
				}
				
				for(int i = 0; i < line2old.length; i++) {
					if(line2[i] != " ") {
						recipe.setIngredient(line2[i].charAt(0), new MaterialData(Material.getMaterial(line2old[i])));
					}
				}
				
				for(int i = 0; i < line3old.length; i++) {
					if(line3[i] != " ") {
						recipe.setIngredient(line3[i].charAt(0), new MaterialData(Material.getMaterial(line3old[i])));
					}
				}
				
				
			
			} else {
				
				String[] line1 = new String[] {config.getStringList("Recipe").get(0)};
				String[] line2 = new String[] {config.getStringList("Recipe").get(1)};
				String[] line3 = new String[] {config.getStringList("Recipe").get(2)};
				
				recipe.shape("A","B","C");
				
				recipe.setIngredient('A', new MaterialData(Material.getMaterial(line1[0])));
				recipe.setIngredient('B', new MaterialData(Material.getMaterial(line2[0])));
				recipe.setIngredient('C', new MaterialData(Material.getMaterial(line3[0])));
				
			}
		}
	}

	public ShapedRecipe getRecipe() {
		return recipe;
	}

	public void setRecipe(ShapedRecipe recipe) {
		this.recipe = recipe;
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public void setConfig(FileConfiguration config) {
		this.config = config;
	}

	public ItemStack getResult() {
		return result;
	}

	public void setResult(ItemStack result) {
		this.result = result;
	}

	public String getWeaponName() {
		return weaponName;
	}

	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}

	public boolean isItemHasRecipe() {
		return itemHasRecipe;
	}

	public void setItemHasRecipe(boolean itemHasRecipe) {
		this.itemHasRecipe = itemHasRecipe;
	}

	public boolean isSwordRecipe() {
		return isSwordRecipe;
	}

	public void setSwordRecipe(boolean isSwordRecipe) {
		this.isSwordRecipe = isSwordRecipe;
	}
	
}
