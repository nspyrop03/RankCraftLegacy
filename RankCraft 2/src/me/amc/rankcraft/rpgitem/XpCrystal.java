package me.amc.rankcraft.rpgitem;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.RCUtils;

public class XpCrystal implements Listener {

	private CrystalSize size;
	private String name;
	private CustomItem item;
	private float xp;
	
	public XpCrystal(CrystalSize size, String name) {
		this.size = size;
		this.name = name;
		this.item = new CustomItem(Material.DIAMOND, this.name);
		switch(this.size) {
		case Small:
			this.xp = CrystalSize.Small.getPower();
			break;
		case Medium:
			this.xp = CrystalSize.Medium.getPower();
			break;
		case Large:
			this.xp = CrystalSize.Large.getPower();
			break;
		default:
			this.xp = CrystalSize.Custom.getPower();
			break;
		}
		this.item.addLores(ChatColor.GREEN+"Right-Click to gain "+this.xp+" xp!");
		this.item.build();
	}
	
	public XpCrystal(CrystalSize size) {
		this(size, "");
	}
	
	public XpCrystal customCrystal(float customXp) {
		this.xp = customXp;
		this.item.addLores(ChatColor.GREEN+"Right-Click to gain "+this.xp+" xp!");
		this.item.build();
		return this;
	}

	public CrystalSize getSize() {
		return size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CustomItem getItem() {
		return item;
	}

	public float getXp() {
		return xp;
	}

	public void setXp(float xp) {
		this.xp = xp;
	}
	
//	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack hand = p.getInventory().getItemInMainHand();//p.getItemOnCursor();
		
		if(!(hand.getType() == item.getType())) return;
		if(!hand.hasItemMeta()) return;
		if(!hand.getItemMeta().hasDisplayName()) return;
		//System.out.println("test xp 1");
		//if(hand.getItemMeta().getDisplayName().equalsIgnoreCase(name)) {
		List<String> lores = hand.getItemMeta().getLore();
		for(String s : lores) {
			if(s.equalsIgnoreCase(ChatColor.GREEN+"Right-Click to gain "+this.xp+" xp!")) {
				RCUtils.removeItemFromHand(p);
				MainCore.instance.rankCraft.stats.addXp(p, xp);
		//		p.sendMessage(ChatColor.GREEN+"+ "+xp+" xp");
				p.sendMessage(MainCore.instance.language.getEarnXp(xp));
			}
		}
			//}
			
	}
	
}
