package me.amc.rankcraft.spells;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class FarmSpell extends Spell {

	public FarmSpell(int power) {
		super(power, 3, "farmspell");
		
		//this.setName("§2FarmSpell");
		this.setName(MainCore.instance.itemLang.getSpellName("FarmSpell"));
		this.setUsage(ClickType.Right_Click);
		this.setManaCost(getPower() * 20);
		this.setDescription(MainCore.instance.itemLang.getFarmSpellDescription(5*getPower()));
		//this.addLores("MaxPower: "+this.getMaxPower(), "Power: "+this.getPower());
		//this.addLores(ChatColor.AQUA+"Spawns "+5*getPower()+" random farm animals");
		this.initItem();
		
	}

	@Override
	public void onRightClick(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		
		if(this.hasMana(p, this.getManaCost())) {
			
			Location loc = p.getLocation().add(0, 2, 0);
			World w = p.getWorld();
			EntityType[] animals = {EntityType.CHICKEN, EntityType.COW, EntityType.PIG};
			
			for(int i = 0; i < 5*getPower(); i++) {
				w.spawnEntity(loc, animals[new Random().nextInt(animals.length)]);
			}
			
			this.removeItemFromHand(p);
			
			MainCore.instance.rankCraft.callUseSpellEvent(p, this);
			RCUtils.removeAndReloadMana(p, this.getManaCost());
			
		} else {
			this.sendNotEnoughManaMsg(p);
		}
	}
	
}
