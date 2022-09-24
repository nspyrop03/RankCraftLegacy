package me.amc.rankcraft.rpgitem;

import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import me.amc.rankcraft.utils.CustomItem;

public class RpgMaterial extends CustomItem {

	private Rarity rarity;
	private int customModelId;
	
	public RpgMaterial(Material type) {
		super(type);
	}

	public RpgMaterial(Material type, Rarity rarity, int customModelId) {
		this(type);
		this.rarity = rarity;
		this.addLores("Rarity: "+rarity);
		
		this.customModelId = customModelId;
	}
	
	@Override
	public CustomItem build() {
		super.build();
		
		ItemMeta meta = this.getItem().getItemMeta();
		meta.setCustomModelData(this.customModelId);
		this.getItem().setItemMeta(meta);
		
		return this;
	}
	
	public Rarity getRarity() {
		return this.rarity;
	}
	
}
