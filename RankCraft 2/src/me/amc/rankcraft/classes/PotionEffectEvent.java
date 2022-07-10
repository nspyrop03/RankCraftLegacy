package me.amc.rankcraft.classes;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.potion.PotionEffect;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.customevents.ChangeRpgClassEvent;
import me.amc.rankcraft.customevents.RpgClassLevelUpEvent;
import me.amc.rankcraft.utils.RCUtils;

public class PotionEffectEvent implements Listener {

	@EventHandler
	public void onChangeClass(ChangeRpgClassEvent e) {
		
		Player p = e.getPlayer();
		if(RCUtils.isWorldEnabled(p.getWorld())) {
			for(PotionEffect pe : p.getActivePotionEffects()) {
				p.removePotionEffect(pe.getType());
			}
			
			try {
				if(e.getNewClass().getPotionEffect(RpgClassData.getLevel(e.getNewClass(), p)) != null) {
					p.addPotionEffect(e.getNewClass().getPotionEffect(RpgClassData.getLevel(e.getNewClass(), p)));
				}
				if(e.getNewClass().getSpecialEffects(RpgClassData.getLevel(e.getNewClass(), p)) != null) {
					for(PotionEffect effect : e.getNewClass().getSpecialEffects(RpgClassData.getLevel(e.getNewClass(), p))) {
						p.addPotionEffect(effect);
					}
				}
				
			} catch(NullPointerException ex) {
				
			}
		}
	}
	
	@EventHandler
	public void onLevelUpClass(RpgClassLevelUpEvent e) {
		Player p = e.getPlayer();
		
		if(RCUtils.isWorldEnabled(p.getWorld())) {
			for(PotionEffect pe : p.getActivePotionEffects()) {
				p.removePotionEffect(pe.getType());
			}
			
			try {			
				if(e.getRpgClass().getPotionEffect(RpgClassData.getLevel(e.getRpgClass(), p)) != null) {
					p.addPotionEffect(e.getRpgClass().getPotionEffect(e.getNewLevel()));
				}
				
				if(e.getRpgClass().getSpecialEffects(RpgClassData.getLevel(e.getRpgClass(), p)) != null) {
					for(PotionEffect effect : e.getRpgClass().getSpecialEffects(RpgClassData.getLevel(e.getRpgClass(), p))) {
						p.addPotionEffect(effect);
					}
				}
				
			} catch(NullPointerException ex) {
				
			}
			
		//	p.sendMessage(e.getRpgClass().getName()+" leveled up! "+e.getOldLevel()+" -> "+e.getNewLevel()+"!");
			p.sendMessage(MainCore.instance.language.getLevelUpClass(e.getRpgClass().getName(), e.getOldLevel(), e.getNewLevel()));
			
			if(e.getNewLevel() == e.getRpgClass().getMaxLevel()) {
			//	p.sendMessage(ChatColor.GREEN+"You were rewarded 1 Class Point because you leveled up the"+e.getRpgClass().getName()+ "class to maximum level!");
				p.sendMessage(MainCore.instance.language.getRewardClassPoint(e.getRpgClass().getName()));
				
				RpgClassData.setClassPoints(p, RpgClassData.getClassPoints(p) + 1);
				RpgClassData.save();
			}
		}
	}
	
	@EventHandler
	public void onChangeWorld(PlayerChangedWorldEvent e) {
		
		Player p = e.getPlayer();
	//	System.out.println(p.getWorld());
		
		if(RCUtils.isWorldEnabled(p.getWorld())) {
			try {
				
				RpgClass currentClass = RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p));
				if(currentClass.getPotionEffect(RpgClassData.getLevel(currentClass, p)) != null) {
					p.addPotionEffect(currentClass.getPotionEffect(RpgClassData.getLevel(currentClass, p)));
				}
				
				if(currentClass.getSpecialEffects(RpgClassData.getLevel(currentClass, p)) != null) {
					for(PotionEffect effect : currentClass.getSpecialEffects(RpgClassData.getLevel(currentClass, p))) {
						p.addPotionEffect(effect);
					}
				}
				
			} catch(NullPointerException ex) {
		//		ex.printStackTrace();
			}
		} else {
			for(PotionEffect pe : p.getActivePotionEffects()) {
				p.removePotionEffect(pe.getType());
			}
		}
		
	}
}
