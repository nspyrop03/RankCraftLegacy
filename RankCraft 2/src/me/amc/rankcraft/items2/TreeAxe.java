package me.amc.rankcraft.items2;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import me.amc.rankcraft.rpgitem.Rarity;
import me.amc.rankcraft.rpgitem.RpgItem;

public class TreeAxe extends SpecialItem {

	private final static String NAME = ChatColor.GREEN+""+ChatColor.BOLD+"Tree Axe";

	private static int delay = 10;
	private static int maxFuel = 0;
	private static int maxDurability = 250;
	
	public final static String DESCRIPTION[] = {ChatColor.AQUA+""+ChatColor.ITALIC+"The most eco-friendly weapon!",
			ChatColor.YELLOW+""+ChatColor.ITALIC+"Right-Click"+ChatColor.AQUA+""+ChatColor.ITALIC+" to start planting trees!"};
	public TreeAxe() {
		super(Material.IRON_AXE, NAME, delay, maxFuel, maxDurability, TreeAxe.class.getName(), false);
		
		this.setMinDamage(5.0);
		this.setMaxDamage(15.0);
		this.setCriticalLuck(20);
		this.setLevelToUse(10);
		this.setRarity(Rarity.RARE);
		this.addDescriptionLore(DESCRIPTION);
		this.build();
		
	}

	public TreeAxe(RpgItem item) {
		super(item);
					
		this.setBasicStats(delay, maxFuel, maxDurability, false);
	}
	
}