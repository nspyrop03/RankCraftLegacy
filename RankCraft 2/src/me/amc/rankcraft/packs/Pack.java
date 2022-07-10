package me.amc.rankcraft.packs;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.utils.CustomItem;

public class Pack implements Listener {

	public enum PackLevel {
		Bronze, Silver, Gold
	}

	public enum PackType {
		Blocks, Items, Mixed
	}

	private String name;
	private int size;
	private PackType type;
	private PackLevel level;
	private CustomItem item;
	private float cost = 0;

	public Pack(PackType type, PackLevel level) {
		this.type = type;
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public PackType getType() {
		return type;
	}

	public void setType(PackType type) {
		this.type = type;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public PackLevel getLevel() {
		return level;
	}

	public void setLevel(PackLevel level) {
		this.level = level;
	}

	public CustomItem getItem() {
		return item;
	}

	public void setItem(CustomItem item) {
		this.item = item;
	}

	public void initItem() {
		item = new CustomItem(getLevelAndMakeItem());
		item.setName(name);
		item.build();
	}

	private Material getLevelAndMakeItem() {

		Material mat;
		
		switch (level) {
		case Bronze:
			mat = Material.FEATHER;
			break;
		case Gold:
			mat = Material.GOLD_INGOT;
			break;
		case Silver:
			mat = Material.IRON_INGOT;
			break;
		default:
			mat = Material.CHEST;
			break;

		}

		return mat;
	}

	public void onRightClickPack(PlayerInteractEvent e) {
		this.onClick(e);
	}

	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = event.getPlayer();
			ItemStack hand = p.getInventory().getItemInMainHand();
		//	System.out.println("ok1");
		//	System.out.println(getItem().getType());
		//	System.out.println(getItem().getName());
			if (hand.getType() == getItem().getType()) {
		//		System.out.println("ok2");
				if (!hand.hasItemMeta())
					return;
				if (hand.getItemMeta().getDisplayName().equals(getName())) {
		//			System.out.println("ok3");
					onRightClickPack(event);
				} else {
					return;
				}
			}
		}
	}

}
