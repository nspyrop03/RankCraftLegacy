package me.amc.rankcraft.spells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class BombSpell extends Spell {
	
	
	private List<Integer> bullets = new ArrayList<Integer>();
	
	public BombSpell(int power) 
	{
		super(power, 3,"bombspell");
		
		//this.setName("§1BombSpell");
		this.setName(MainCore.instance.itemLang.getSpellName("BombSpell"));
		this.setUsage(ClickType.Right_Click);
		this.setManaCost(getPower() * 10);
		this.setDescription(MainCore.instance.itemLang.getBombSpellDescription(getPower()));
		//this.addLores("MaxPower: "+this.getMaxPower(), "Power: "+this.getPower());
		//this.addLores(ChatColor.AQUA+"Throws a snowball which creates",ChatColor.AQUA+" a level "+getPower()+" explosion");
		this.initItem();
	}
	
	@Override
	public void onRightClick(PlayerInteractEvent e) 
	{
		Player p = e.getPlayer();
		
	
		if(this.hasMana(p, this.getManaCost())) {
			ThrownPotion tp = p.launchProjectile(ThrownPotion.class);
		
			bullets.add(tp.getEntityId());
			this.removeItemFromHand(p);
			
			MainCore.instance.rankCraft.callUseSpellEvent(p, this);
			RCUtils.removeAndReloadMana(p, this.getManaCost());
		} else {
			this.sendNotEnoughManaMsg(p);
		}
	
	}
	
	@EventHandler
	public void onPotionHit(ProjectileHitEvent e)
	{
		if(e.getEntity() instanceof ThrownPotion)
		{
			int id = e.getEntity().getEntityId();
			
			if(bullets.contains((Integer) id))
			{
				World w = e.getEntity().getWorld();
				Location l = e.getEntity().getLocation();
				
				double x = l.getX();
				double y = l.getY();
				double z = l.getZ();
				
				bullets.remove((Integer) id);
				
				w.createExplosion(x, y, z, getPower(), false, false);
		
			}
		}
	}
}
