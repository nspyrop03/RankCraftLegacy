package me.amc.rankcraft.events;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.RankCraft;
import me.amc.rankcraft.achievements.Achievement;
import me.amc.rankcraft.backpack.BackpackUtils;
import me.amc.rankcraft.classes.RpgClass;
import me.amc.rankcraft.classes.RpgClassData;
import me.amc.rankcraft.commands.ToggleCommand;
import me.amc.rankcraft.quests2.Quest;
import me.amc.rankcraft.utils.RCUtils;

public class PlayerJoinOnServer implements Listener {

	public PlayerJoinOnServer() {
		
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	
	}
	
	@EventHandler /*(priority = EventPriority.LOWEST)*/
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		RankCraft r = MainCore.instance.rankCraft;
		
		// Initialize stats for player
		r.stats.initForPlayer(p);
		r.gold.initForPlayer(p);
		r.attackSkill.initForPlayer(p);
		r.defenseSkill.initForPlayer(p);
		r.magicSkill.initForPlayer(p);
		r.mobStats.initForPlayer(p);
		r.antiSpellsData.initForPlayer(p);
		r.hpSystem.initHPSystem(p);
		
		for(Quest q : MainCore.instance.rankCraft.quests2) {
			q.initForPlayer(p, MainCore.instance);
		}
		
		for(Achievement a : MainCore.instance.rankCraft.achievements) {
			a.initFor(p);
		}
		
		r.initRpgItems(p);
		
		
		
	//	r.addBackpack(p);
	//	System.out.println(r.backpacks);
		BackpackUtils.initFor(p);
		
		if(!RpgClassData.unlockGladiator.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setUnlockGladiator(p, false);
		}
		
		if(!RpgClassData.gladiatorLevel.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setGladiatorLevel(p, 1);
		}
		
		if(!RpgClassData.gladiatorXp.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setGladiatorXp(p, 0.0D);
		}
		
		if(!RpgClassData.currentClass.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setCurrentClass(p, "null");
		}
		
		if(!RpgClassData.classPoints.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setClassPoints(p, 1);
		}
		
		if(!RpgClassData.unlockArcher.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setUnlockArcher(p, false);
		}
		
		if(!RpgClassData.archerLevel.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setArcherLevel(p, 1);
		}
		
		if(!RpgClassData.archerXp.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setArcherXp(p, 0.0D);
		}
		
		if(!RpgClassData.unlockNinja.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setUnlockNinja(p, false);
		}
		
		if(!RpgClassData.ninjaLevel.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setNinjaLevel(p, 1);
		}
		
		if(!RpgClassData.ninjaXp.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setNinjaXp(p, 0.0D);
		}
		
		if(!RpgClassData.unlockWizard.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setUnlockWizard(p, false);
		}
		
		if(!RpgClassData.wizardLevel.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setWizardLevel(p, 1);
		}
		
		if(!RpgClassData.wizardXp.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setWizardXp(p, 0.0D);
		}
		
		if(!RpgClassData.unlockMysterious.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setUnlockMysterious(p, false);
		}
		
		if(!RpgClassData.mysteriousLevel.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setMysteriousLevel(p, 1);
		}
		
		if(!RpgClassData.mysteriousXp.containsKey(RCUtils.textedUUID(p))) {
			RpgClassData.setMysteriousXp(p, 0.0D);
		}
		RpgClassData.save();
		
	//	MainCore.instance.rankCraft.rpgClassGUI.open(p);
		
		if(RCUtils.isWorldEnabled(p.getWorld())) {
		//	System.out.println("Apo edo");
			if(RpgClassData.getClassPoints(p) > 0) {
		//		p.sendMessage("You have "+RpgClassData.getClassPoints(p)+" class points! Type '/rc selectclass' to unlock a new class!");
				p.sendMessage(MainCore.instance.language.getSendClassPoints(RpgClassData.getClassPoints(p)));
			}
			
			for(PotionEffect pe : p.getActivePotionEffects()) {
				p.removePotionEffect(pe.getType());
			}
			
		//	System.out.println(MainCore.instance.rankCraft.mysteriousClass == null);
		//	System.out.println(MainCore.instance.rankCraft.mysteriousClass.getSpecialEffects(RpgClassData.getMysteriousLevel(p)) == null);
			
			try {
				RpgClass currentClass = RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p));
				if(currentClass.getPotionEffect(RpgClassData.getLevel(currentClass, p)) != null) {
					p.addPotionEffect(currentClass.getPotionEffect(RpgClassData.getLevel(currentClass, p)));
				}
				
		//		System.out.println(currentClass.getId());
		//		System.out.println(currentClass.getPotionEffect(RpgClassData.getLevel(currentClass, p)) == null);
				
				if(currentClass.getSpecialEffects(RpgClassData.getLevel(currentClass, p)) != null) {
					for(PotionEffect effect : currentClass.getSpecialEffects(RpgClassData.getLevel(currentClass, p))) {
						p.addPotionEffect(effect);
					}
				}
				
			} catch(NullPointerException ex) {
		//		ex.printStackTrace();
			}
			if(!ToggleCommand.disabledPlayers.contains(RCUtils.textedUUID(p))) {
				MainCore.instance.scoreboard.createScoreboard(p);
				MainCore.instance.scoreboard.updateBoard(p);
			} else {
				MainCore.instance.scoreboard.createScoreboard(p);
				p.setScoreboard(MainCore.instance.scoreboard.getScoreboardManager().getNewScoreboard());
			}
			
		} else {
			for(PotionEffect pe : p.getActivePotionEffects()) {
				p.removePotionEffect(pe.getType());
			}
			
			p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
			
			//p.setMaxHealth(20);
			//p.setHealth(p.getMaxHealth());
			
			p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
		}
		
		if(MainCore.instance.config.disableClassicHearts) {
			p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
			p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
		}
		
		/*
		for(int i = 0; i < PlaceholderAPI.getCustomPlaceholders().size(); i++) {
			String t = ""+PlaceholderAPI.getCustomPlaceholders().keySet().toArray()[i];
			p.sendMessage(t+" = "+PlaceholderAPI.replacePlaceholders(p, t));
		}
		*/
	}
	
}
