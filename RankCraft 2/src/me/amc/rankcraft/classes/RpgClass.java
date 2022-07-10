package me.amc.rankcraft.classes;

import java.util.List;

import org.bukkit.potion.PotionEffect;

public interface RpgClass {

	public String getName();
	
	public String getId();
	
	public int getMaxLevel();
	
	public float getXpToLevelUp(int level);
	
	public PotionEffect getPotionEffect(int level);
	
	public List<PotionEffect> getSpecialEffects(int level);
	
}
