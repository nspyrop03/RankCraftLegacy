package me.amc.rankcraft.utils;

public class Reward {

	private float xp;
	private float gold;
	private CustomItem item;
	
	public Reward(float xp) {
		this.xp = xp;
	}
	
	public Reward(float xp, float gold) {
		this.xp = xp;
		this.gold = gold;
	}
	
	public Reward(float xp, float gold, CustomItem item) {
		this.xp = xp;
		this.gold = gold;
		this.item = item;
	}

	public float getXp() {
		return xp;
	}

	public void setXp(float xp) {
		this.xp = xp;
	}

	public float getGold() {
		return gold;
	}

	public void setGold(float gold) {
		this.gold = gold;
	}

	public CustomItem getItem() {
		return item;
	}

	public void setItem(CustomItem item) {
		this.item = item;
	}
	
}
