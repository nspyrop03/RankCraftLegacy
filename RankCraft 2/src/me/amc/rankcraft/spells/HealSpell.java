package me.amc.rankcraft.spells;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.damage.HPSystem;
import me.amc.rankcraft.utils.RCUtils;

public class HealSpell extends Spell {
	
	public HealSpell(int power) {
		super(power, 5,"healspell");
		//this.setName("§4HealSpell");
		this.setName(MainCore.instance.itemLang.getSpellName("HealSpell"));
		this.setUsage(ClickType.Right_Click);
		this.setManaCost(getPower() * 10);
		this.setDescription(MainCore.instance.itemLang.getHealSpellDescription(getPower()*2));
		//this.addLores("MaxPower: "+this.getMaxPower(),"Power: "+this.getPower());
		//this.addLores(ChatColor.AQUA+"Heals "+getPower()*2+" HP");
		
		this.initItem();
		
	}

	@Override
	public void onRightClick(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		int healPower = getPower() * 2;
	
		if(this.hasMana(p, this.getManaCost())) {			
			HPSystem s = MainCore.instance.rankCraft.hpSystem;
			
			if(s.getHP(p) == s.getMaxHP(p)) {
			//	p.sendMessage("You cannot have more than your maximum hp");
				p.sendMessage(MainCore.instance.language.getSpellNoMoreHP());
			} else {
				s.addHP(p, healPower);
				this.removeItemFromHand(p);
				
				MainCore.instance.rankCraft.callUseSpellEvent(p, this);
				RCUtils.removeAndReloadMana(p, this.getManaCost());
				
				p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0,1,0), 20, 0.5f,0.5f,0.5f);
			}
			
		} else {
			this.sendNotEnoughManaMsg(p);
		}
		//p.getWorld().spawnParticle(Particle.HEART, p.getLocation().add(0,1,0), 20, 0.5f,0.5f,0.5f);
	}
	
	
}
