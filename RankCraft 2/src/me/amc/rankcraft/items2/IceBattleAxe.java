package me.amc.rankcraft.items2;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import me.amc.rankcraft.rpgitem.Rarity;
import me.amc.rankcraft.rpgitem.RpgItem;

public class IceBattleAxe extends SpecialItem{

	private final static String NAME = ChatColor.WHITE+""+ChatColor.BOLD+"Ice Battle Axe";

	private static int delay = 10;
	private static int maxFuel = 0;
	private static int maxDurability = 250;
	
	public final static String DESCRIPTION[] = {ChatColor.AQUA+""+ChatColor.ITALIC+"Even a mighty Viking would be jealous of this axe!",
			ChatColor.YELLOW+""+ChatColor.ITALIC+"Right-Click"+ChatColor.AQUA+""+ChatColor.ITALIC+" to throw a special snow weapon!"};
	
	public IceBattleAxe() {
		super(Material.IRON_AXE, NAME, delay, maxFuel, maxDurability, IceBattleAxe.class.getName(), false);
		
		this.setMinDamage(40.0);
		this.setMaxDamage(65.0);
		this.setCriticalLuck(60);
		this.setLevelToUse(50);
		this.setRarity(Rarity.LEGENDARY);
		this.addDescriptionLore(DESCRIPTION);
		this.build();
		
	}

	public IceBattleAxe(RpgItem item) {
		super(item);
					
		this.setBasicStats(delay, maxFuel, maxDurability, false);
	}
	
}
