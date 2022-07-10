package me.amc.rankcraft.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.PermissionList;
import me.amc.rankcraft.lang.Language;
import me.amc.rankcraft.utils.CustomItem;

public class SetLootableCommand extends SubCommand {

	public SetLootableCommand() {
		super("setlootable");
	}

	@Override
	public void execute(Player p, String[] args) {
		
		PermissionList pl = MainCore.instance.permList;
		Language lang = MainCore.instance.language;
		
		//System.out.println(p.getItemInHand().getType().toString());
		
		ItemStack hand = p.getInventory().getItemInMainHand();
		
		if(p.hasPermission(pl.setLootable_permission)) {
			if(hand != null && hand.getType() != Material.AIR) {
				CustomItem item = new CustomItem(hand);
				boolean addLootable = true;
				
				for(String line : item.getLores()) {
					if(line.equals(ChatColor.GOLD+"Lootable")) {
						addLootable = false;
					}
				}
				
				if(addLootable) {
					item.addLores(ChatColor.GOLD+"Lootable");
				} else {
					//p.sendMessage("This item is already lootable!");
					p.sendMessage(lang.getAlreadyLootable());
				}
				
				//RCUtils.removeItemFromHand(p);
				//p.setItemInHand(item.build().getItem());
				p.getInventory().setItemInMainHand(item.build().getItem());
				
			} else {
				//p.sendMessage(ChatColor.RED+"You don't hold any item!");
				p.sendMessage(lang.getEmptyHand());
			}
		} else {
			pl.sendNotPermissionMessage(p);
		}
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}
}
