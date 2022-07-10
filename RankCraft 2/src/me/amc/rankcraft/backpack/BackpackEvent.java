package me.amc.rankcraft.backpack;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class BackpackEvent implements Listener {

	@EventHandler
	public void onClose( InventoryCloseEvent e) {
		//if(e.getInventory().getTitle().equalsIgnoreCase(e.getPlayer().getName()+"'s backpack")) {
		if(e.getView().getTitle().equalsIgnoreCase(e.getPlayer().getName()+"'s backpack")) {
			try {
				BackpackUtils.saveInventory(e.getInventory(), (Player) e.getPlayer());
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		}
	}
}
