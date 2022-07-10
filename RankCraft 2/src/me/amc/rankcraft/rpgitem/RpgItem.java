package me.amc.rankcraft.rpgitem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.lang.Language;

public class RpgItem {
	private Material material;
	private List<String> lores = new ArrayList<String>();
	private ItemStack item;
	private ItemMeta meta;
	private double minDamage = 0.0;
	private double maxDamage = 0.0;
	private String displayName = "Nameless";
	private int criticalLuck = 0;
	private int amount = 1;
	private int levelToUse = 0;
	private Rarity rarity = Rarity.COMMON;
	private List<String> afterLore = new ArrayList<String>();

	public RpgItem(Material material, int amount) {
		this.material = material;
		this.amount = amount;
		this.item = new ItemStack(this.material, this.amount);
		this.meta = this.item.getItemMeta();
	}

	public RpgItem(Material material) {
		this.material = material;
		this.item = new ItemStack(this.material, 1);
		this.meta = this.item.getItemMeta();
	}

	public RpgItem(Material material, int amount, String displayName) {
		this.material = material;
		this.amount = amount;
		this.item = new ItemStack(this.material, this.amount);
		this.meta = this.item.getItemMeta();
		this.displayName = displayName;
	}

	public RpgItem(Material material, String displayName) {
		this.material = material;
		this.item = new ItemStack(this.material, 1);
		this.meta = this.item.getItemMeta();
		this.displayName = displayName;
	}
	
	public RpgItem(ItemStack item) {
		this.item = item;
		this.meta = item.getItemMeta();
		this.displayName = item.getItemMeta().getDisplayName();
		this.material = item.getType();
	}
	
	// Copy constructor
	public RpgItem(RpgItem ri) { 
		this.material = ri.getMaterial();
		this.displayName = ri.getDisplayName();
		this.lores = ri.getLores();
		this.afterLore = ri.getAfterLore();
		this.meta = ri.getMeta();
		this.rarity = ri.getRarity();
		this.minDamage = ri.getMinDamage();
		this.maxDamage = ri.getMaxDamage();
		this.criticalLuck = ri.getCriticalLuck();
		this.amount = ri.getAmount();
		this.levelToUse = ri.getLevelToUse();
		this.item = ri.getItem();
		//this.build();
	}
	
	public RpgItem() {}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public List<String> getLores() {
		return lores;
	}

	public void setLores(List<String> lores) {
		this.lores = lores;
	}

	public void addLores(String... strings) {
		for (String s : strings) {
			lores.add(s);
		}
	}

	public void removeLores(String... strings) {
		for (String s : strings) {
			if (lores.contains(s)) {
				lores.remove(s);
			} else {
				return;
			}
		}
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public ItemMeta getMeta() {
		return meta;
	}

	public void setMeta(ItemMeta meta) {
		this.meta = meta;
	}

	public double getMinDamage() {
		return minDamage;
	}

	public void setMinDamage(double minDamage) {
		this.minDamage = minDamage;
	}

	public double getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(double maxDamage) {
		this.maxDamage = maxDamage;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getCriticalLuck() {
		return criticalLuck;
	}

	public void setCriticalLuck(int criticalLuck) {
		this.criticalLuck = criticalLuck;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getLevelToUse() {
		return levelToUse;
	}

	public void setLevelToUse(int levelToUse) {
		this.levelToUse = levelToUse;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}

	public void enchant(Enchantment enchantment, int power) {
		this.meta.addEnchant(enchantment, power, true);
	}
	
	public void addAfterLore(String...strings) {
		for(String s : strings) {
			afterLore.add(s);
		}
	}
	
	public List<String> getAfterLore() {
		return afterLore;
	}
	
	public void removeLoreIfStartsWith(String start) {
		/*
		List<String> fullLore = new ArrayList<>();
		fullLore.addAll(lores);
		fullLore.addAll(afterLore);
		for (int i = 0; i < fullLore.size(); i++) {
			if (fullLore.get(i).startsWith(start)) {
				fullLore.remove(fullLore.get(i));
			}
		}
		*/
		for(int i = 0; i < lores.size(); i++) {
			if(lores.get(i).startsWith(start)) lores.remove(i);
		}
		for(int i = 0; i < afterLore.size(); i++) {
			if(afterLore.get(i).startsWith(start)) afterLore.remove(i);
		}
	}
	
	public void setAfterLore(List<String> afterLore) {
		this.afterLore = afterLore;
	}

	public void build() {
		Language lang = MainCore.instance.language;
		
		if(minDamage > maxDamage) {
			double temp = minDamage;
			minDamage = maxDamage;
			maxDamage = temp;
		}
		
		String stats = ChatColor.DARK_GRAY+"Stats: "+minDamage+","+maxDamage+","+criticalLuck+","+levelToUse;
		
		this.meta.setDisplayName(this.displayName);
		
		/*
		this.addLores("MinDamage: " + this.minDamage);
		this.addLores("MaxDamage: " + this.maxDamage);
		this.addLores("CriticalLuck: " + this.criticalLuck);
		this.addLores("Rarity: " + this.rarity);
		this.addLores("MinLevel: " + this.levelToUse);
		
		this.addLores(lang.getMinDamage()+": "+this.minDamage);
		this.addLores(lang.getMaxDamage()+": "+this.maxDamage);
		this.addLores(lang.getCriticalLuck()+": "+this.criticalLuck);
		this.addLores(lang.getRarity()+": "+this.rarity);
		this.addLores(lang.getMinLevel()+": "+this.levelToUse);
		*/
		lores.addAll(lang.getRpgItemLore(minDamage, maxDamage, criticalLuck, ""+rarity, levelToUse));
		
		lores.addAll(afterLore);
		lores.add(" ");
		lores.add(stats);
		
		this.meta.setLore(this.lores);
		
		this.meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.item.setItemMeta(this.meta);
	}
}
