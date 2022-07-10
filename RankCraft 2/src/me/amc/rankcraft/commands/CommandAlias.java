package me.amc.rankcraft.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class CommandAlias extends SubCommand {

	private String fullCommand = "";
	private Permission permission;
	
	public CommandAlias(String label, String fullCommand, Permission permission) {
		super(label);
		this.fullCommand = fullCommand;
		this.permission = permission;
	}

	@Override
	public void execute(Player p, String[] args) {
		
		if(p.hasPermission(permission)) {
			
			Bukkit.dispatchCommand(p, "rankcraft "+fullCommand);
			
		}
	}
	
	public String getFullCommand() {
		return this.fullCommand;
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
