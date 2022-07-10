package me.amc.rankcraft.classes;

import java.util.List;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.amc.rankcraft.MainCore;

public class GladiatorClass implements RpgClass {

	private String name;
	private int maxLevel;
	
	private String id = "gladiator";

	public GladiatorClass(String name, int maxLevel) {
		this.name = name;
		this.maxLevel = maxLevel;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	public PotionEffect getPotionEffect(int level) {
		int amplifier = 0;

		if (level <= 5) {
			amplifier = 0;
		} else if (level >= 6 && level <= maxLevel - 1) {
			amplifier = 1;
		} else if (level == maxLevel) {
			amplifier = 2;
		}

		return new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, amplifier, false, MainCore.instance.config.displayClassParticles);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public float getXpToLevelUp(int level) {
		int goalXp = 100 + (level * 100);
		
		return goalXp;
	}

	@Override
	public List<PotionEffect> getSpecialEffects(int level) {
		return null;
	}
	
}
