package me.amc.rankcraft.rpgitem;

import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

import me.amc.rankcraft.utils.RCUtils;

@SuppressWarnings("deprecation")
public class YamlArmorRecipe {

	private ShapedRecipe recipe = null;
	
	public YamlArmorRecipe(YamlArmor ya) {
		this.recipe = new ShapedRecipe(RCUtils.getKeyForRecipe(ya.fileName), ya.getRpgArmor().build().getItem());
				
		if(ya.isHasRecipe()) {

			int id = 0;
			String[] letters = new String[] {"A","B","C","D","E","F","G","H","I"};
				
			String[] line1 = ya.getRecipe().get(0).split(",");
			String[] line2 = ya.getRecipe().get(1).split(",");
			String[] line3 = ya.getRecipe().get(2).split(",");
				
			String[] line1old = ya.getRecipe().get(0).split(",");
			String[] line2old = ya.getRecipe().get(1).split(",");
			String[] line3old = ya.getRecipe().get(2).split(",");
				
			for(int i = 0; i < line1.length; i++) {
				if(line1[i].equals("0") || line1[i].equalsIgnoreCase("AIR")) {
					line1[i] = " ";
				} else {
					line1[i] = letters[id];
					id++;
				}
			}
				
			for(int i = 0; i < line2.length; i++) {
				if(line2[i].equals("0") || line2[i].equalsIgnoreCase("AIR")) {
					line2[i] = " ";
				} else {
					line2[i] = letters[id];
					id++;
				}
			}
				
			for(int i = 0; i < line3.length; i++) {
				if(line3[i].equals("0") || line3[i].equalsIgnoreCase("AIR")) {
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
			
		}	
	}
	
	public ShapedRecipe getRecipe() {
		return recipe;
	}
	
}
