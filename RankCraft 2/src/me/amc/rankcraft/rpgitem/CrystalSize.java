package me.amc.rankcraft.rpgitem;

public enum CrystalSize {

	Small(10.0F),
	Medium(25.0F),
	Large(50.0F),
	Custom(0.0F);
	
	private final float power;
	
	CrystalSize(float power) {
		this.power = power;
	}
	
	public float getPower() {
		return this.power;
	}
}
