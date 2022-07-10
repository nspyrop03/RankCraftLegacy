package me.amc.rankcraft.items2;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class SpecialItemsFactory {

	public IceBattleAxeEvent iceBattleAxeEvent;
	public TreeAxeEvent treeAxeEvent;
	
	public SpecialItemsFactory() {
		makeRecipes();
		createEvents();
	}
	
	private void createEvents() {
		new ExcaliburEvent();
		new ZeusSwordEvent();
		iceBattleAxeEvent = new IceBattleAxeEvent();
		treeAxeEvent = new TreeAxeEvent();
	}
	
	private void makeRecipes() {
		Excalibur excalibur = new Excalibur();
		ShapedRecipe recipe = new ShapedRecipe(RCUtils.getKeyForRecipe("excalibur"), new ItemStack(excalibur.getItem()));
		recipe.shape("E", 
					 "A", 
					 "S");
		recipe.setIngredient('E', Material.EMERALD);
		recipe.setIngredient('S', Material.STICK);
		recipe.setIngredient('A', Material.ARROW);
		MainCore.instance.getServer().addRecipe(recipe);
		
		ZeusSword zeusSword = new ZeusSword();
		ShapedRecipe recipe2 = new ShapedRecipe(RCUtils.getKeyForRecipe("zeus_sword"), zeusSword.getItem());
		recipe2.shape(" ER", 
				      " RE", 
				 	  "S  ");
		recipe2.setIngredient('S', Material.STICK);
		recipe2.setIngredient('E', Material.EMERALD_BLOCK);
		recipe2.setIngredient('R', Material.REDSTONE_BLOCK);
		MainCore.instance.getServer().addRecipe(recipe2);
	
		IceBattleAxe iceBattleAxe = new IceBattleAxe();
		ShapedRecipe recipe3 = new ShapedRecipe(RCUtils.getKeyForRecipe("ice_battle_axe"), iceBattleAxe.getItem());
		recipe3.shape("BBB", 
				 	  "BSB", 
				 	  " S ");
		recipe3.setIngredient('S', Material.STICK);
		recipe3.setIngredient('B', Material.SNOW_BLOCK);
		MainCore.instance.getServer().addRecipe(recipe3);
		
		TreeAxe treeAxe = new TreeAxe();
		ShapedRecipe recipe4 = new ShapedRecipe(RCUtils.getKeyForRecipe("tree_axe"), treeAxe.getItem());
		recipe4.shape("BBB", 
				 	 "BSB", 
				 	 " S ");
		recipe4.setIngredient('S', Material.STICK);
		recipe4.setIngredient('B', Material.OAK_SAPLING);
		MainCore.instance.getServer().addRecipe(recipe4);
	}
}
