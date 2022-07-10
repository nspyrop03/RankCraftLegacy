package me.amc.rankcraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.permissions.Permission;

public class CommandDescription {

	private String command;
	private String description;
	private Permission permission;
	private boolean locked = true;
	
	public CommandDescription(String command, String description, Permission permission, boolean locked) {
		this.command = ChatColor.RED+"/rankcraft "+command;
		this.description = ChatColor.GOLD+description;
		this.permission = permission;
		this.locked = locked;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
	public String getFullLine() {
		String line = command+ChatColor.GRAY+" :: "+description;
		return line;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
}
