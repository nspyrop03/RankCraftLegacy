package me.amc.rankcraft.spells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class SpiderSpell extends Spell {

	public SpiderSpell(int power) {
		super(power, 5, "spiderspell");

		//this.setName("§4SpiderSpell");
		this.setName(MainCore.instance.itemLang.getSpellName("SpiderSpell"));
		this.setUsage(ClickType.Right_Click);
		this.setManaCost(getPower() * 10);
		this.setDescription(MainCore.instance.itemLang.getSpiderSpellDescription(getPower()*5));
		//this.addLores("MaxPower: " + this.getMaxPower(), "Power: " + this.getPower());
		//this.addLores(ChatColor.AQUA+"Throws "+getPower()*5+" web blocks");
		this.initItem();
	}

	public static List<FallingBlock> blocks = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	@Override
	public void onRightClick(PlayerInteractEvent e) {

		Player p = e.getPlayer();

		if (this.hasMana(p, this.getManaCost())) {
			double x = p.getLocation().getBlockX();
			double y = p.getLocation().getY();
			double z = p.getLocation().getBlockZ();

			World w = p.getWorld();
			Location l = new Location(w, x, y + 1, z);

			for (int i = 0; i < getPower() * 5; i++) {
				FallingBlock block = w.spawnFallingBlock(l.add(p.getLocation().getDirection()), Material.COBWEB, (byte) 0);
				blocks.add(block);
				//Bukkit.getScheduler().runTaskLater(MainCore.instance, () -> {
				//    if(block != null) block.
				//}, 100); // Time in ticks (20 ticks = 1 second)
				//w.spawnFallingBlock(l.add(p.getLocation().getDirection()), Material.COBWEB, (byte) 0);
			}

			this.removeItemFromHand(p);

			MainCore.instance.rankCraft.callUseSpellEvent(p, this);
			RCUtils.removeAndReloadMana(p, this.getManaCost());

		} else {
			this.sendNotEnoughManaMsg(p);
		}
	}
	
	

}
