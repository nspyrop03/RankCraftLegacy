package me.amc.rankcraft.suits;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public interface Suit {

	public ItemStack getHelmet();
	
	public ItemStack getChestplate();
	
	public ItemStack getLeggings();
	
	public ItemStack getBoots();
	
	public List<PotionEffect> getFullEffects();
	
}
