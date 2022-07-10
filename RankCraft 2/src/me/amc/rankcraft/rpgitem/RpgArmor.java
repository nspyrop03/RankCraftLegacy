package me.amc.rankcraft.rpgitem;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.RCUtils;

public class RpgArmor {

	private CustomItem customItem;
	private double defense;
	private Rarity rarity;
	private int minLevel;
	
	public RpgArmor(Material mat) {
		customItem = new CustomItem(mat);
	}
	
	public RpgArmor(Material mat, double defense, Rarity rarity, int minLevel) {
		this(mat);
		this.defense = defense;
		this.rarity = rarity;
		this.minLevel = minLevel;
	}

	public CustomItem getCustomItem() {
		return customItem;
	}

	public void setCustomItem(CustomItem customItem) {
		this.customItem = customItem;
	}

	public double getDefense() {
		return defense;
	}

	public void setDefense(double defense) {
		this.defense = defense;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}
	
	public CustomItem build() {
		String stats = ChatColor.DARK_GRAY+"ArmorStats: "+RCUtils.round(defense,1)+","+minLevel;
		/*
		customItem.removeIfStart(ChatColor.GRAY+"Armor: ");
		customItem.removeIfStart(ChatColor.GRAY+"Rarity: ");
		customItem.removeIfStart(ChatColor.GRAY+"LevelToUse: ");
		customItem.removeIfStart(" ");
		customItem.removeIfStart(ChatColor.DARK_GRAY+"ArmorStats: ");
		customItem.addLores(
				ChatColor.GRAY+"Armor: "+ChatColor.BLUE+""+RCUtils.round(defense,1),
				ChatColor.GRAY+"Rarity: "+rarity.getColor()+""+rarity,
				ChatColor.GRAY+"LevelToUse: "+ChatColor.YELLOW+""+minLevel,
				" ",
				stats);
		 *
		 */
		customItem.setLores(MainCore.instance.language.getRpgArmorLore(defense, rarity, minLevel));
		customItem.addLores(" ");
		customItem.addLores(stats);
		
		customItem.buildWithNoFlags();
		return customItem;
	}
	
}
