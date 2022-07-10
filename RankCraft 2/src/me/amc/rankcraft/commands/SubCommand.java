package me.amc.rankcraft.commands;

public abstract class SubCommand implements RCSubCmd{

	private String label;
	
	public SubCommand(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
