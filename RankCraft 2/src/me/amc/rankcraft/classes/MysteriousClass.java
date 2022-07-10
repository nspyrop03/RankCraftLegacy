package me.amc.rankcraft.classes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.potion.PotionEffect;

import me.amc.rankcraft.MainCore;

public class MysteriousClass implements RpgClass {

	private String name;
	private int maxLevel;
	
	private String id = "mysterious";

	public MysteriousClass(String name, int maxLevel) {
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
		return null;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public float getXpToLevelUp(int level) {
		int goalXp = 100 + (level * 200);
		
		return goalXp;
	}
	
	@Override
	public List<PotionEffect> getSpecialEffects(int level) {		
		List<PotionEffect> effects = new ArrayList<>();
		
		effects.add(MainCore.instance.rankCraft.gladiatorClass.getPotionEffect(level));
		effects.add(MainCore.instance.rankCraft.archerClass.getPotionEffect(level));
		effects.add(MainCore.instance.rankCraft.wizardClass.getPotionEffect(level));
		effects.add(MainCore.instance.rankCraft.ninjaClass.getPotionEffect(level));
		
		return effects;
	}

	
	
}
