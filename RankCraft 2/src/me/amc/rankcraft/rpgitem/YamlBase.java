package me.amc.rankcraft.rpgitem;

public class YamlBase {
	
	protected int minLevel;
	protected String fileName;
	protected String name;
	
	public YamlBase(String fileName, String name, int minLevel) {
		this.fileName = fileName;
		this.name = name;
		this.minLevel = minLevel;
	}

	public int getMinLevel() {
		return minLevel;
	}

	public String getFileName() {
		return fileName;
	}

	public String getName() {
		return name;
	}
	
}
