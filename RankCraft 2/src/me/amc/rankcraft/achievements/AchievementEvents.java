package me.amc.rankcraft.achievements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.classes.RpgClass;
import me.amc.rankcraft.classes.RpgClassData;
import me.amc.rankcraft.customevents.PlayerFindTreasureEvent;
import me.amc.rankcraft.customevents.PlayerUseSpellEvent;
import me.amc.rankcraft.customevents.UnlockClassEvent;
import me.amc.rankcraft.items2.Excalibur;
import me.amc.rankcraft.utils.RCUtils;

public class AchievementEvents implements Listener {
	
	private List<Material> smiths = new ArrayList<>();

	public AchievementEvents() {
		
		smiths.add(Material.IRON_AXE);
		smiths.add(Material.IRON_SHOVEL);
		smiths.add(Material.IRON_SWORD);
		smiths.add(Material.IRON_PICKAXE);
		smiths.add(Material.IRON_HOE);
		smiths.add(Material.IRON_HELMET);
		smiths.add(Material.IRON_CHESTPLATE);
		smiths.add(Material.IRON_LEGGINGS);
		smiths.add(Material.IRON_BOOTS);
		
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}

	@EventHandler
	public void achievementCall(EntityDeathEvent e) {
		LivingEntity dead = e.getEntity();
		LivingEntity killer = dead.getKiller();

		if (killer instanceof Player) {
			Player p = (Player) killer;
			
			if(RCUtils.isWorldEnabled(killer.getWorld())) {
				for (Achievement a : MainCore.instance.rankCraft.achievements) {
					
					if (a.getCompletedHM().get(RCUtils.textedUUID(p)) == false) {
						if (a.getType() == Achievement.Type.KillMobs) {
							if (dead.getType() == a.getKillMobsType()) {
								a.addMobKills(p, 1);
								if (a.getMobsKilledBy(p) >= a.getKillMobsAmt()) {
									a.completeFor(p);
								}
							}
						} else if (a.getType() == Achievement.Type.KillPlayers) {
							if (dead instanceof Player) {
								a.addPlayerKills(p, 1);
								if (a.getPlayersKilledBy(p) >= a.getKillPlayersAmt()) {
									a.completeFor(p);
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void achievementCallNew1(BlockPlaceEvent e) {
		Player p = e.getPlayer();

		if(RCUtils.isWorldEnabled(p.getWorld())) {
			for (Achievement a : MainCore.instance.rankCraft.achievements) {
				if (a.getType() == Achievement.Type.PlaceBlocks) {
					if (e.getBlockPlaced().getType() == a.getPlaceBlocksType()) { 
						a.addPlaceBlock(p, 1);
						if (a.getBlocksPlacedBy(p) >= a.getPlaceBlocksAmt()) {
							a.completeFor(p);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void achievementCallNew2(BlockBreakEvent e) {
		Player p = e.getPlayer();

		if(RCUtils.isWorldEnabled(p.getWorld())) {
			for (Achievement a : MainCore.instance.rankCraft.achievements) {
				if (a.getType() == Achievement.Type.BreakBlocks) {
					if (e.getBlock().getType() == a.getBreakBlocksType()) {
						a.addBreakBlock(p, 1);
						if (a.getBlocksBrokenBy(p) >= a.getBreakBlocksAmt()) {
							a.completeFor(p);
						}
					}
				}
				if(a.getType() == Achievement.Type.Special) {
					if(a instanceof AchievementBlockBreaker) {
						a.addSpecialPoints(p, 1);
						if(a.getSpecialPointsBy(p) >= a.getSpecialPoints()) {
							a.completeFor(p);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void initAchievements(PlayerJoinEvent e) {
		for (Achievement a : MainCore.instance.rankCraft.achievements) {
			a.initFor(e.getPlayer());
		}
	}

	@EventHandler
	public void findTreasureCall(PlayerFindTreasureEvent e) {
		Player p = e.getPlayer();

		if(RCUtils.isWorldEnabled(p.getWorld())) {
			for (Achievement a : MainCore.instance.rankCraft.achievements) {
				if (a instanceof AchievementFindTreasures) {
					a.addSpecialPoints(p, 1);
					if (a.getSpecialPointsBy(p) >= a.getSpecialPoints()) {
						a.completeFor(p);
					}
				}
			}
		}
		
	}

	@EventHandler
	public void useSpellCall(PlayerUseSpellEvent e) {
		Player p = e.getPlayer();
		if(RCUtils.isWorldEnabled(p.getWorld())) {
			for (Achievement a : MainCore.instance.rankCraft.achievements) {
				if (a instanceof AchievementUseSpells) {
					a.addSpecialPoints(p, 1);
					if (a.getSpecialPointsBy(p) >= a.getSpecialPoints()) {
						a.completeFor(p);
					}
				}
			}
			
			MainCore.instance.rankCraft.rpgClassEvents.wizardEvent(e);
		}
	}
	
	@EventHandler
	public void excaliburAchievement(CraftItemEvent e) {
		Player p = (Player)e.getWhoClicked();
	/*	
		if(e.getCurrentItem().equals(MainCore.instance.rankCraft.excalibur)) {
			for (Achievement a : MainCore.instance.rankCraft.achievements) {
				if (a instanceof AchievementExcaliburMaster) {
					a.addSpecialPoints(p, 1);
					if(a.getSpecialPointsBy(p) >= a.getSpecialPoints()) {
						a.completeFor(p);
					}
				}
			}
		}
	*/
		
	//	e.getRecipe().getResult().equals(MainCore.instance.rankCraft.excalibur.getItem())
		
	//	System.out.println("event!");
		if(RCUtils.isWorldEnabled(p.getWorld())) {
			Excalibur sword = new Excalibur();
			if(e.getRecipe().getResult().equals(sword.getItem())) {
				for (Achievement a : MainCore.instance.rankCraft.achievements) {
					if (a instanceof AchievementExcaliburMaster) {
						a.addSpecialPoints(p, 1);
						if(a.getSpecialPointsBy(p) >= a.getSpecialPoints()) {
							a.completeFor(p);
						}
					}
				}
			}
		}
	}
	
	
	
	@EventHandler
	public void smithAchievement(CraftItemEvent e) {
		Player p = (Player) e.getWhoClicked();
		if(RCUtils.isWorldEnabled(p.getWorld())) {
			if(smiths.contains(e.getRecipe().getResult().getType())) {
				for(Achievement a : MainCore.instance.rankCraft.achievements) {
					if(a instanceof AchievementSmith) {
						a.addSpecialPoints(p, 1);
						if(a.getSpecialPointsBy(p) >= a.getSpecialPoints()) {
							a.completeFor(p);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onUnlockClass(UnlockClassEvent e) {
		Player p = e.getPlayer();
		RpgClass r = e.getRpgClass();
		if(RCUtils.isWorldEnabled(p.getWorld())) {
			if(r.equals(MainCore.instance.rankCraft.mysteriousClass)) {
				for(Achievement a : MainCore.instance.rankCraft.achievements) {
					if(a instanceof AchievementMysteryMaster) {
						a.addSpecialPoints(p, 1);
						if(a.getSpecialPointsBy(p) >= a.getSpecialPoints()) {
							a.completeFor(p);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		for(Achievement a : MainCore.instance.rankCraft.achievements) {
			if(a instanceof AchievementMysteryMaster) {
				if(a.getCompletedHM() != null)
				if(a.getCompletedHM().get(RCUtils.textedUUID(p)) == false) {
					if(RpgClassData.getUnlockMysterious(p) == true) {
						a.addSpecialPoints(p, 1);
						if(a.getSpecialPointsBy(p) >= a.getSpecialPoints()) {
							a.completeFor(p);
						}
					}
				}
			}
		}
	}
	
}
