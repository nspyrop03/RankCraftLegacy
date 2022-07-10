package me.amc.rankcraft.suits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import me.amc.rankcraft.utils.CustomItem;

public class GladiatorSuit implements Suit {

	private CustomItem helmet;
	private CustomItem chestplate;
	private CustomItem leggings;
	private CustomItem boots;
	private List<PotionEffect> effects = new ArrayList<>();
	
	public GladiatorSuit() {

		helmet = new CustomItem(Material.IRON_HELMET, ChatColor.AQUA+"Gladiator Helmet");
		helmet.addLores(ChatColor.DARK_GREEN+"Part of Gladiator Suit ",ChatColor.GRAY+"Adds Speed II effect when is fully worn!");
		helmet.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
		helmet.build();
		
		chestplate = new CustomItem(Material.LEATHER_CHESTPLATE, ChatColor.AQUA+"Gladiator Chestplate");
	}
	
	@Override
	public ItemStack getHelmet() {
		return helmet.getItem();
		
	}

	@Override
	public ItemStack getChestplate() {
		// TODO Auto-generated method stub
		return chestplate.build().getItem();
	}

	@Override
	public ItemStack getLeggings() {
		// TODO Auto-generated method stub
		return leggings.build().getItem();
	}

	@Override
	public ItemStack getBoots() {
		// TODO Auto-generated method stub
		return boots.build().getItem();
	}

	@Override
	public List<PotionEffect> getFullEffects() {
		// TODO Auto-generated method stub
		return effects;
	}

	
	
}
