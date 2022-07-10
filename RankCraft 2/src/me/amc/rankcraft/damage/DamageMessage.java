package me.amc.rankcraft.damage;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class DamageMessage {

	private ArmorStand msg;
	private Location loc;
	private int seconds;

	public DamageMessage(String message, Location loc) {
		this.loc = loc;
		msg = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		msg.setVisible(false);
	//	msg.setCollidable(false);
		msg.setMarker(true);
		msg.setGravity(false);
		msg.setCustomName(ChatColor.RED + "" + message);
		msg.setCustomNameVisible(true);
		seconds = 2;
	}

	public ArmorStand getMsg() {
		return msg;
	}

	public void setMsg(ArmorStand msg) {
		this.msg = msg;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

}
