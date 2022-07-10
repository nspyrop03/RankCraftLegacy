package me.amc.rankcraft.spells;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.damage.HPSystem;
import me.amc.rankcraft.utils.RCUtils;

public class HeartsSpell extends Spell {
	
	public HeartsSpell(int power) {
		super(power, 1,"heartsspell");
		//this.setName("§4HeartsSpell");
		this.setName(MainCore.instance.itemLang.getSpellName("HeartsSpell"));
		this.setUsage(ClickType.Right_Click);
		this.setManaCost(getPower() * 50);
		this.setDescription(MainCore.instance.itemLang.getHeartsSpellDescription());
		//this.addLores(ChatColor.AQUA+"It gives you an extra 20.0HP forever!");
		this.initItem();
	}	
	
	@Override
	public void onRightClick(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();		
	
		if(this.hasMana(p, getManaCost())) {			
			
			HPSystem s = MainCore.instance.rankCraft.hpSystem;
			
			//s.increaseMaxHP(p, 20.0);
			s.increaseMaxHP(p, MainCore.instance.config.heartsMaxHP);
			s.fillHP(p);
			
			this.removeItemFromHand(p);
			
			MainCore.instance.rankCraft.callUseSpellEvent(p, this);
			RCUtils.removeAndReloadMana(p, this.getManaCost());
			
		} else {
			this.sendNotEnoughManaMsg(p);
		}
	
	}
	
}
