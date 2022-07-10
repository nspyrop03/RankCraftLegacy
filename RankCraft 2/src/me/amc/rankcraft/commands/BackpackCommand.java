package me.amc.rankcraft.commands;

import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.backpack.BackpackUtils;

public class BackpackCommand extends SubCommand {

	public BackpackCommand() {
		super("backpack");
	}

	@Override
	public void execute(Player p, String[] args) {
		if (p.hasPermission(MainCore.instance.permList.backpack_permission)) {
			try {
				Inventory inv = Bukkit.createInventory(p, BackpackUtils.getLinesFromFile(p) * 9,
						BackpackUtils.getTitle(p));

				int slot = 0;
				for (ItemStack item : BackpackUtils.getBackpackItems(p)) {
					inv.setItem(slot, item);
					slot++;
				}

				p.openInventory(inv);

			} catch (IllegalArgumentException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}

		} else {
			MainCore.instance.permList.sendNotPermissionMessage(p);
		}

	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
