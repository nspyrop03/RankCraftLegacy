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
import me.amc.rankcraft.stats.Stats;
import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.RCUtils;

public class ManaCrystal implements Listener {

	private CrystalSize size;
	private String name;
	private CustomItem item;
	private int mana;

	public ManaCrystal(CrystalSize size, String name) {
		this.size = size;
		this.name = name;
		this.item = new CustomItem(Material.DIAMOND, this.name);
		switch (this.size) {
		case Small:
			this.mana = (int) CrystalSize.Small.getPower();
			break;
		case Medium:
			this.mana = (int) CrystalSize.Medium.getPower();
			break;
		case Large:
			this.mana = (int) CrystalSize.Large.getPower();
			break;
		default:
			this.mana = (int) CrystalSize.Custom.getPower();
			break;
		}
		this.item.addLores(ChatColor.GREEN + "Right-Click to gain " + this.mana + " mana!");
		this.item.build();
	}

	public ManaCrystal(CrystalSize size) {
		this(size, "");
	}

	public ManaCrystal customCrystal(int customMana) {
		this.mana = customMana;
		this.item.addLores(ChatColor.GREEN + "Right-Click to gain " + this.mana + " mana!");
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

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

//	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack hand = p.getInventory().getItemInMainHand();//p.getItemOnCursor();

		if (!(hand.getType() == item.getType()))
			return;
		if (!hand.hasItemMeta())
			return;
		if (!hand.getItemMeta().hasDisplayName())
			return;

		Stats stats = MainCore.instance.rankCraft.stats;
		// if(hand.getItemMeta().getDisplayName().equalsIgnoreCase(name)) {
		List<String> lores = hand.getItemMeta().getLore();
		for (String s : lores) {
			if (s.equalsIgnoreCase(ChatColor.GREEN + "Right-Click to gain " + this.mana + " mana!")) {
				
				if(stats.getMana(p) < stats.getMaxMana(p)) {
					RCUtils.removeItemFromHand(p);
					MainCore.instance.rankCraft.stats.addMana(p, mana);
				//	p.sendMessage(ChatColor.GREEN + "+ " + mana + " mana");
					p.sendMessage(MainCore.instance.language.getEarnMana(mana));
				} else {
				//	p.sendMessage(ChatColor.RED+"Your mana is full!");
					p.sendMessage(MainCore.instance.language.getFullMana());
				}
				// }
			}
		}
	}

}
