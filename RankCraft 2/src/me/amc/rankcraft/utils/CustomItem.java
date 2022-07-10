package me.amc.rankcraft.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomItem {

	private ItemStack item;
	private Material type;
	private List<String> lores = new ArrayList<String>();
	private ItemMeta meta;
	private String name;

	public CustomItem(Material type) {
		this.type = type;

		this.item = new ItemStack(this.type);
		this.meta = this.item.getItemMeta();
	}

	public CustomItem(Material type, String name) {
		this.type = type;
		this.name = name;

		this.item = new ItemStack(this.type);
		this.meta = this.item.getItemMeta();
	}

	public CustomItem(ItemStack item) {
		this.item = item;
		this.meta = item.getItemMeta();
		setName(this.meta.getDisplayName());
		if(!this.meta.hasLore()) {
			List<String> lore = new ArrayList<String>();
			setLores(lore);
			
		} else {
			setLores(this.meta.getLore());
		}
	}
	
	public CustomItem(ItemStack item, String name) {
		this.item = item;
		this.meta = item.getItemMeta();
		setName(name);
		if(!this.meta.hasLore()) {
			List<String> lore = new ArrayList<String>();
			setLores(lore);
		} else {
			setLores(this.meta.getLore());
		}
	}

	public CustomItem(Material type, int amount) {
		this.type = type;
		this.item = new ItemStack(this.type, amount);
		this.meta = this.item.getItemMeta();
	}
	
	public CustomItem(Material type, String name, String firstLore) {
		this.type = type;
		this.name = name;
		this.item = new ItemStack(this.type);
		this.meta = this.item.getItemMeta();
		this.addLores(firstLore);
	}
	
	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public Material getType() {
		return type;
	}

	public void setType(Material type) {
		this.type = type;
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
	
	public ItemMeta getItemMeta() {
		return this.meta;
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

	public void removeIfStart(String start) {
		for (int i = 0; i < lores.size(); i++) {
			if (lores.get(i).startsWith(start)) {
				lores.remove(lores.get(i));
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.replace('&', '§').replace('_', ' ');
	}

	public void enchant(Enchantment enchantment, int level) {
		this.meta.addEnchant(enchantment, level, true);
	}

	public CustomItem build(boolean isWithEnchantColor) {
		this.meta.setDisplayName(this.name);
		this.meta.setLore(this.lores);

		if (isWithEnchantColor) {
			this.meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
			this.meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}

		this.item.setItemMeta(this.meta);
		return this;
	}

	public CustomItem build() {
		this.meta.setDisplayName(this.name);
		if (!this.lores.isEmpty())
			this.meta.setLore(this.lores);
		this.item.setItemMeta(this.meta);
		return this;
	}
	
	public CustomItem buildWithNoFlags() {
		this.meta.setDisplayName(this.name);
		if (!this.lores.isEmpty())
			this.meta.setLore(this.lores);
		this.meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		this.item.setItemMeta(this.meta);
		return this;
	}

}
