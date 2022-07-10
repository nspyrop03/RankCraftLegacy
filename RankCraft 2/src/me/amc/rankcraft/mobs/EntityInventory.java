package me.amc.rankcraft.mobs;

import org.bukkit.entity.LivingEntity;

import me.amc.rankcraft.utils.CustomItem;

public class EntityInventory {

	private CustomItem helmet;
	private CustomItem chestplate;
	private CustomItem leggings;
	private CustomItem boots;
	private CustomItem handItem;

	private LivingEntity entity;

	public EntityInventory(LivingEntity entity) {
		this.entity = entity;
	}

	public CustomItem getHelmet() {
		return helmet;
	}

	public void setHelmet(CustomItem helmet) {
		this.helmet = helmet;
	}

	public CustomItem getChestplate() {
		return chestplate;
	}

	public void setChestplate(CustomItem chestplate) {
		this.chestplate = chestplate;
	}

	public CustomItem getLeggings() {
		return leggings;
	}

	public void setLeggings(CustomItem leggings) {
		this.leggings = leggings;
	}

	public CustomItem getBoots() {
		return boots;
	}

	public void setBoots(CustomItem boots) {
		this.boots = boots;
	}

	public CustomItem getHandItem() {
		return handItem;
	}

	public void setHandItem(CustomItem handItem) {
		this.handItem = handItem;
	}

	public LivingEntity getEntity() {
		return entity;
	}

	public void setEntity(LivingEntity entity) {
		this.entity = entity;
	}

//	@SuppressWarnings("deprecation")
	public void addInventoryToEntity() {
		if (this.helmet != null)
			this.entity.getEquipment().setHelmet(this.helmet.getItem());
		if (this.chestplate != null)
			this.entity.getEquipment().setChestplate(this.chestplate.getItem());
		if (this.leggings != null)
			this.entity.getEquipment().setLeggings(this.leggings.getItem());
		if (this.boots != null)
			this.entity.getEquipment().setBoots(this.boots.getItem());
		if (this.handItem != null)
			this.entity.getEquipment().setItemInMainHand(this.handItem.getItem());
		//this.entity.getEquipment().setItemInHand(this.handItem.getItem());
	}
}
