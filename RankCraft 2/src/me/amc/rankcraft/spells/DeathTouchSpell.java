package me.amc.rankcraft.spells;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.damage.HPSystem;
import me.amc.rankcraft.utils.RCUtils;

public class DeathTouchSpell extends Spell {

	
	public DeathTouchSpell(int power) {
		super(power, 1,"deathtouchspell");
		
		//this.setName("§4DeathTouchSpell");
		this.setName(MainCore.instance.itemLang.getSpellName("DeathTouchSpell"));
		this.setUsage(ClickType.Entity_Hit);
		this.setManaCost(this.getPower() * 50);
		this.setDescription(MainCore.instance.itemLang.getDeathTouchSellDescription());
		//this.addLores("MaxPower: "+this.getPower(), "Power: "+this.getPower());
		//this.addLores(ChatColor.AQUA+"Hit someone to kill him");
		
		this.initItem();
	}
	
	@Override
	public void onHitEntity(EntityDamageByEntityEvent e) {
		Player p = (Player) e.getDamager();
		HPSystem s = MainCore.instance.rankCraft.hpSystem;
		
		if(this.hasMana(p, this.getManaCost())) {
			//p.sendMessage("Has Mana");
			if(e.getEntity() instanceof Player) {
				Player d = (Player) e.getEntity();
				s.removeHP(d, s.getHP(d));
				//p.sendMessage("Hit player!");
			} else {
				e.setDamage(10000f);
				//p.sendMessage("Hit entity!"+e.getDamage());
			}
			
			this.removeItemFromHand(p);
			
			MainCore.instance.rankCraft.callUseSpellEvent(p, this);
			RCUtils.removeAndReloadMana(p, this.getManaCost());
			
		} else {
			this.sendNotEnoughManaMsg(p);
		}
	}
	
}
