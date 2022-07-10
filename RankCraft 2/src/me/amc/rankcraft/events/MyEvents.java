package me.amc.rankcraft.events;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.classes.RpgClass;
import me.amc.rankcraft.classes.RpgClassData;
import me.amc.rankcraft.customevents.LevelUpEvent;
import me.amc.rankcraft.damage.HPSystem;
import me.amc.rankcraft.stats.Gold;
import me.amc.rankcraft.utils.RCUtils;

public class MyEvents implements Listener {

	public MyEvents() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}

	@EventHandler
	public void onLevelUp(LevelUpEvent e) {
		Player p = e.getPlayer();
		int oldLvl = e.getOldLevel();
		int newLvl = e.getNewLevel();

		HPSystem s = MainCore.instance.rankCraft.hpSystem;
		Gold g = MainCore.instance.rankCraft.gold;

		
		s.increaseMaxHP(p, MainCore.instance.config.levelUpHealth);
		
		if(MainCore.instance.rankCraft.levelRewardSystem.rewards.containsKey(newLvl)) {
			
			MainCore.instance.rankCraft.levelRewardSystem.rewards.get(newLvl).sendReward(p);
			
		} else {
			g.addGold(p, MainCore.instance.config.levelUpGold);
	
			for (String str : MainCore.instance.language.getLevelUpMessages(oldLvl, newLvl,
					(float) MainCore.instance.config.levelUpHealth, MainCore.instance.config.levelUpGold)) {
				p.sendMessage(str);
			}
		}
	}

	@EventHandler
	public void onDeathSendTitle(PlayerDeathEvent e) {
		Player p = e.getEntity();

		// MainCore.instance.rankCraft.sendTitle(p, 5, 25, 8,
		// ChatColor.RED+""+ChatColor.BOLD+"YOU DIED!", ChatColor.WHITE+"Watch
		// out!!!");
		MainCore.instance.rankCraft.sendTitle(p, 5, 30, 10, MainCore.instance.language.getDeathTitle(),
				MainCore.instance.language.getDeathSubtitle());

		// System.out.println("Pe8ana");

		// for (Player p : Bukkit.getOnlinePlayers()) {

		// }

		/*
		 * if(MainCore.instance.nmsver.startsWith("v1_9_")) { if(e.getEntity()
		 * instanceof Player) { // System.out.println("v1_9"); //
		 * System.out.println(e.getEntity().getLocation()); //
		 * System.out.println(p.getInventory().getContents()); //
		 * System.out.println(p.getInventory().getArmorContents()); // for(int i
		 * = 0; i < e.getDrops().size(); i++) { //
		 * e.getEntity().getWorld().dropItem(e.getEntity().getLocation(),
		 * e.getDrops().get(i)); // } for(int i = 0; i <
		 * p.getInventory().getContents().length; i++) {
		 * if(p.getInventory().getContents()[i] != null)
		 * e.getEntity().getWorld().dropItem(e.getEntity().getLocation(),
		 * p.getInventory().getContents()[i]); }
		 * 
		 * for(int j = 0; j < p.getInventory().getArmorContents().length; j++) {
		 * if(p.getInventory().getArmorContents()[j] != null)
		 * e.getEntity().getWorld().dropItem(e.getEntity().getLocation(),
		 * p.getInventory().getArmorContents()[j]); } } }
		 */
	}

	/*
	@EventHandler
	public void onTreasureFind(PlayerFindTreasureEvent e) {
		if (MainCore.instance.config.personalTreasureChestMessage) {
			TreasureChest2 t = e.getTreasure();
			//e.getPlayer().sendMessage(MainCore.instance.language.getFindTreasure(t.getChest().getName()));
		}
	}
	*/

	private static HashMap<Player, Integer> effectCooldown = new HashMap<>();
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		effectCooldown.put(p, 1);
	}
	
	public static void cooldownCount(Player p) {
		if(effectCooldown.get(p) != null) {
			if(effectCooldown.get(p) > 0) {
				effectCooldown.put(p, effectCooldown.get(p) - 1);
			//	p.sendMessage("SecondsRemaining to earn class effect: "+effectCooldown.get(p));
			} else {
				if(RCUtils.isWorldEnabled(p.getWorld())) {
					effectCooldown.remove(p);
					for(PotionEffect pe : p.getActivePotionEffects()) {
						p.removePotionEffect(pe.getType());
					}
					
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
				
					}
				}
			}
		}
	}
	
	@EventHandler
	public void drinkMilk(PlayerItemConsumeEvent e) {
		if(e.getItem().getType().equals(Material.MILK_BUCKET)) {
			Player p = e.getPlayer();
			//p.sendMessage("Milk!");
			if (RCUtils.isWorldEnabled(p.getWorld())) {
				//for(int i = 0; i < p.getActivePotionEffects().size(); i++) 	p.sendMessage(p.getActivePotionEffects().toArray()[i].toString());
				final List<PotionEffect> EFFECTS = (List<PotionEffect>) p.getActivePotionEffects();
				//p.sendMessage("Enabled World!");
				e.setCancelled(true);
				for(PotionEffect pe : EFFECTS) {
					p.removePotionEffect(pe.getType());
				}
				try {
					RpgClass currentClass = RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p));
					/*
					if(currentClass != null) {
						e.setCancelled(true);
					}
					*/
					
					if (currentClass.getPotionEffect(RpgClassData.getLevel(currentClass, p)) != null) {
						//p.sendMessage("Applying effect!");
						p.addPotionEffect(currentClass.getPotionEffect(RpgClassData.getLevel(currentClass, p)));
					}
	
					if (currentClass.getSpecialEffects(RpgClassData.getLevel(currentClass, p)) != null) {
						for (PotionEffect effect : currentClass
								.getSpecialEffects(RpgClassData.getLevel(currentClass, p))) {
							p.addPotionEffect(effect);
						}
					}
					 
				} catch (NullPointerException ex) {
	
				}
			}
		}
	}
}
