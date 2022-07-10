package me.amc.rankcraft.items2;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.persistence.PersistentDataType;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.rpgitem.RpgItem;
import me.amc.rankcraft.utils.RCUtils;

public class SpecialItem extends RpgItem {

	public static final NamespacedKey DATE_KEY = new NamespacedKey(MainCore.instance, "date");
	public static final NamespacedKey FUEL_KEY = new NamespacedKey(MainCore.instance, "fuel");
	public static final NamespacedKey DURABILITY_KEY = new NamespacedKey(MainCore.instance, "durability");
	public static final NamespacedKey SPECIAL_ITEM_KEY = new NamespacedKey(MainCore.instance, "special_item");
	
	public static final String DURABILITY_PREFIX = ChatColor.GOLD+"Durability: ";
	public static final String COOLDOWN_PREFIX = ChatColor.DARK_GRAY+"Cooldown: ";
	public static final String FUEL_PREFIX = ChatColor.AQUA+"Fuel: ";
	
	private int delay;
	private int maxFuel;
	private int maxDurability;
	private boolean usesFuel;
	
	public SpecialItem(Material mat, String name, int delay, int maxFuel, int maxDurability, String className, boolean usesFuel) {
		super(mat, name);
		
		this.setBasicStats(delay, maxFuel, maxDurability, usesFuel);
		
		// Add the date key (format in RCUtils)
		LocalDateTime now = LocalDateTime.now();
		this.getMeta().getPersistentDataContainer().set(DATE_KEY, PersistentDataType.STRING, now.format(RCUtils.dateFormatter));
		
		// Add the fuel key
		this.getMeta().getPersistentDataContainer().set(FUEL_KEY, PersistentDataType.INTEGER, maxFuel);
		
		// Add the durability key
		this.getMeta().getPersistentDataContainer().set(DURABILITY_KEY, PersistentDataType.INTEGER, maxDurability);
		
		// Add the special item key
		this.getMeta().getPersistentDataContainer().set(SPECIAL_ITEM_KEY, PersistentDataType.STRING, className);
		
		this.getMeta().setUnbreakable(true);
		this.getMeta().addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES);
				
		addCooldownAfterLore();
		this.addAfterLore(DURABILITY_PREFIX+maxDurability+"/"+maxDurability);
		
	}
	
	public SpecialItem(RpgItem item) { // parse item from hand
		super(item);
	}
	
	public boolean use() {
		int d = this.getCurrentDurability();
		int f = this.getRemainingFuel();
		
		if(d <= 0 || (this.usesFuel && f <= 0)) return false;
		
		int diff = this.getSecondsBetweenNow();
				
		if(diff >= this.delay) {	
			this.setCurrentTime(LocalDateTime.now());
						
			this.getMeta().getPersistentDataContainer().set(DURABILITY_KEY, PersistentDataType.INTEGER, Math.max(d-1, 0));
			if(this.usesFuel)
				this.getMeta().getPersistentDataContainer().set(FUEL_KEY, PersistentDataType.INTEGER, Math.max(f-1,0));
			
			return true;			
		}
		 
		return false;
	}
	
	public void addDescriptionLore(String[] description) {
		this.addLores(description);
	}
	
	private void addCooldownAfterLore() {
		this.addAfterLore(COOLDOWN_PREFIX+delay);
	}
	
	public int getSecondsBetweenNow() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime itemTime = this.getCurrentTimeInKey();
		
		return Math.abs((int)ChronoUnit.SECONDS.between(now, itemTime));
	}
	
	public void addFuelAfterLore() {
		this.addAfterLore(FUEL_PREFIX+maxFuel+"/"+maxFuel);
	}
	
	public void updateFuelLore() {
		this.removeLoreIfStartsWith(FUEL_PREFIX);
		this.addAfterLore(FUEL_PREFIX+getRemainingFuel()+"/"+maxFuel);
	}
	
	public void updateDurabilityLore() {
		this.removeLoreIfStartsWith(DURABILITY_PREFIX);
		this.addAfterLore(DURABILITY_PREFIX+getCurrentDurability()+"/"+maxDurability);
	}
	
	public LocalDateTime getCurrentTimeInKey() {
		return LocalDateTime.parse(this.getMeta().getPersistentDataContainer().get(DATE_KEY, PersistentDataType.STRING), RCUtils.dateFormatter);
	}

	public void setCurrentTime(LocalDateTime time) {
		this.getMeta().getPersistentDataContainer().set(DATE_KEY, PersistentDataType.STRING, time.format(RCUtils.dateFormatter));
	}
	
	public int getRemainingFuel() {
		return this.getMeta().getPersistentDataContainer().get(FUEL_KEY, PersistentDataType.INTEGER);
	}
	
	public void setRemainingFuel(int fuel) {
		this.getMeta().getPersistentDataContainer().set(FUEL_KEY, PersistentDataType.INTEGER, fuel);
	}
	
	public int getCurrentDurability() {
		return this.getMeta().getPersistentDataContainer().get(DURABILITY_KEY, PersistentDataType.INTEGER);
	}
	
	public void setCurrentDurability(int d) {
		this.getMeta().getPersistentDataContainer().set(DURABILITY_KEY, PersistentDataType.INTEGER, d);
	}
	
	public void updateItemInHand(Player p, String[] description) {
		if(description != null) this.addLores(description);
		this.addCooldownAfterLore();
		this.updateDurabilityLore();
		if(this.usesFuel) this.updateFuelLore();
		this.build();
		p.getInventory().setItemInMainHand(this.getItem());
	}
	
	public int getDelay() {
		return this.delay;
	}
	
	public int getMaxFuel() {
		return this.maxFuel;
	}
	
	public int getMaxDurability() {
		return this.maxDurability;
	}
	
	public boolean usingFuel() {
		return this.usesFuel;
	}
	
	public void setBasicStats(int delay, int maxFuel, int maxDurability, boolean usesFuel) {
		this.delay = delay;
		this.maxDurability = maxDurability;
		this.maxFuel = maxFuel;
		this.usesFuel = usesFuel;
	}
}
