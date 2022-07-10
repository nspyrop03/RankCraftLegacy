package me.amc.rankcraft.spells;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class HungerSpell extends Spell {

	public HungerSpell(int power) {
		super(power, 3, "hungerspell");
		//this.setName("§6HungerSpell");
		this.setName(MainCore.instance.itemLang.getSpellName("HungerSpell"));
		this.setUsage(ClickType.Entity_Hit);
		this.setManaCost(getPower() * 10);
		this.setDescription(MainCore.instance.itemLang.getHungerSpellDescription());
		//this.addLores("MaxPower: "+this.getMaxPower(), "Power: "+this.getPower());
		//this.addLores(ChatColor.AQUA+"Hit someone to add him Hunger");
		this.initItem();
	}
	
	@Override
	public void onHitEntity(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player k = (Player) e.getDamager();
	//		String name = p.getName();
			
			if(this.hasMana(k, this.getManaCost())) {
				if(AntiHungerSpell.hasAntiHunger(p)) {
			//		p.sendMessage("Your AntiHunger defense has destroyed !");
					p.sendMessage(MainCore.instance.language.getSpellAntiHungerDestroy());
			//		k.sendMessage(name+" had AntiHunger defense !");
					k.sendMessage(MainCore.instance.language.getPlayerHadAntiHunger(p));
					AntiHungerSpell.setAntiHunger(p, false);
					
				} else {
					p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, (int)RCUtils.secondsToTicks(this.getPower()*30), this.getPower()-1), true);
				}
			
				this.removeItemFromHand(k);
			
				MainCore.instance.rankCraft.antiSpellsData.save();
				
				MainCore.instance.rankCraft.callUseSpellEvent(k, this);
				RCUtils.removeAndReloadMana(k, this.getManaCost());
			} else {
				this.sendNotEnoughManaMsg(k);
			}
		
		} else if(e.getEntity() instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) e.getEntity();
			Player k = (Player) e.getDamager();
			if(this.hasMana(k, this.getManaCost())) {
				le.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, (int)RCUtils.secondsToTicks(this.getPower()*30), this.getPower()-1), true);
				this.removeItemFromHand(k);
				
				MainCore.instance.rankCraft.callUseSpellEvent(k, this);
				RCUtils.removeAndReloadMana(k, this.getManaCost());
			} else {
				this.sendNotEnoughManaMsg(k);
			}
		}
	}
	
}
