package me.amc.rankcraft.gui;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.utils.CustomItem;

public class GUI {

	private Inventory inv;
	private int size;
	private String title;
	private HashMap<Integer, ItemStack> items = new HashMap<>();
	private Player owner;
	
	public CustomItem next;
	public CustomItem previous;
	public CustomItem close;
	
	public GUI(String title, Player p) {
		this.title = title;
		this.owner = p;
		initCustomItems();
	}
	
	public GUI(String title, Player p, int size) {
		this.title = title;
		this.size = size;
		this.owner = p;
		initCustomItems();
		inv = Bukkit.createInventory(p, size, title);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public void addItem(ItemStack item, int slot) {
		items.put(slot, item);
	}
	
	public HashMap<Integer, ItemStack> getItemsHM() {
		return this.items;
	}
	
	public void openInventory() {
		inv = Bukkit.createInventory(owner, size, title);
	//	System.out.println("(From gui) owner: "+owner);
	//	System.out.println("(From gui) size: "+size);
	//	System.out.println("(From gui) title: "+title);
		for(int i = 0; i < size; i++) {
			if(items.get(i) != null) {
	//			System.out.println(i);
				inv.setItem(i, items.get(i));
			}
		}
	//	System.out.println("hi1");
		owner.openInventory(inv);
	//	System.out.println("hi2");
	}
	
	private void initCustomItems() {
		next = new CustomItem(Material.ARROW, ChatColor.GREEN+"Next").build();
		previous = new CustomItem(Material.ARROW, ChatColor.GREEN+"Previous").build();
		close = new CustomItem(Material.BARRIER, ChatColor.RED+"Close").build();
	}
	
	public boolean checkItemTypeAndName(ItemStack item, String name, Material type) {
		if(!item.hasItemMeta()) return false;
		if(!item.getItemMeta().hasDisplayName()) return false;
		if(!(item.getType() == type)) return false;
		
		if(item.getItemMeta().getDisplayName().equalsIgnoreCase(name)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkItemWithCustomItem(ItemStack item, CustomItem cItem) {
		return checkItemTypeAndName(item, cItem.getName(), cItem.getType());
	}
	
	public Inventory getInventory() {
		return inv;
	}
}
