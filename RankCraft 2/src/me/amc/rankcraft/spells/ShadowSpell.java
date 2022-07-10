package me.amc.rankcraft.spells;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class ShadowSpell extends Spell {

	private int times = 0;
	
	public ShadowSpell(int power) {
		super(power, 3, "shadowspell");
		
		//this.setName(ChatColor.GRAY+"ShadowSpell");
		this.setName(MainCore.instance.itemLang.getSpellName("ShadowSpell"));
		this.setUsage(ClickType.Right_Click);
		this.setManaCost(this.getPower() * 10);
		this.setDescription(MainCore.instance.itemLang.getShadowSpellDescription(getPower()*5));
		//this.addLores("MaxPower: "+this.getMaxPower(),"Power: "+this.getPower(), ChatColor.GRAY+"It makes a smoke cloud around",ChatColor.GRAY+"you for "+this.getPower()*5+" seconds");
		//this.addLores(ChatColor.GRAY+"It makes a smoke cloud around",ChatColor.GRAY+"you for "+this.getPower()*5+" seconds");
		this.initItem();
		
	}

	
	@Override
	public void onRightClick(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		
		int R = 1;
		
		
		if(this.hasMana(p, this.getManaCost())) {			
			
			
			BukkitRunnable run = new BukkitRunnable() {
				float x, y, z, centreX, centreZ;
				
				@Override
				public void run() {
					y = (float) p.getLocation().getY();
					
					for(int k = 0; k < getPower()*5; k++) {
						y+=0.2F;
						
						centreX = (float) p.getLocation().getX();
						centreZ = (float) p.getLocation().getZ();
			
						for (int phi = 0; phi <= 360; phi += 10) {
							x = (float) (centreX + R * Math.sin(phi));
							z = (float) (centreZ + R * Math.cos(phi));
			
						
							p.getWorld().playEffect(new Location(p.getWorld(), x, y, z), Effect.SMOKE, 5);
						}
						
					}
					
					times++;
					if(times >= getPower()*5*20) {
						this.cancel();
						times = 0;
					}
				}
			};
			
			run.runTaskTimer(MainCore.instance, 0, 1);
			
			this.removeItemFromHand(p);
			
			MainCore.instance.rankCraft.callUseSpellEvent(p, this);
			RCUtils.removeAndReloadMana(p, this.getManaCost());
			
		} else {
			this.sendNotEnoughManaMsg(p);
		}
	
	}
	
}
