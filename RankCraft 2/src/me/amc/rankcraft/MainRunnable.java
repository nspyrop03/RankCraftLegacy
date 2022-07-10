package me.amc.rankcraft;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import me.amc.rankcraft.classes.RpgClassData;
import me.amc.rankcraft.commands.ToggleCommand;
import me.amc.rankcraft.damage.DamageMessage;
import me.amc.rankcraft.damage.DamageSystem;
import me.amc.rankcraft.damage.HPSystem;
import me.amc.rankcraft.events.MyEvents;
import me.amc.rankcraft.gui.RandomItemsShop;
import me.amc.rankcraft.mobs.MobCreator;
import me.amc.rankcraft.mobs.MobSpawner;
import me.amc.rankcraft.mobs.YamlMob;
import me.amc.rankcraft.quests2.Quest;
import me.amc.rankcraft.quests2.QuestEvents;
import me.amc.rankcraft.quests2.QuestSaver;
import me.amc.rankcraft.stats.Stats;
import me.amc.rankcraft.treasures2.TreasureChest2;
import me.amc.rankcraft.utils.RCUtils;
import me.clip.placeholderapi.PlaceholderAPI;

public class MainRunnable extends BukkitRunnable {

	private int ticks = 0;
//	@SuppressWarnings("unused")
	private int zombiesTicks = 0;
//	@SuppressWarnings("unused")
	private int zombieCirclesReload = 0;
	
	private int messageTicks = 0;
	private int outdateTicks = 0;
	
	private int boardTicks = 0;

	private int min20 = 20*60*20;
	
	private Stats stats = MainCore.instance.rankCraft.stats;
	
	@Override
	public void run() {
		ticks++;
		zombiesTicks++;
		zombieCirclesReload++;
		
		messageTicks++;
		outdateTicks++;

		boardTicks++;
		
		if (ticks >= 20) { // 1 second
			for(Player p : Bukkit.getOnlinePlayers()) {
				//updateScoreboard(p);
				updateMana(p);
				updateHP(p);
				
				updateActionBar(p);
				updateQuests(p);
				updateQuestGUIs(p);
				updateRpgItems(p);
				updateShop(p);
				
				updateEffectCooldown(p);
				
				updateOldTreasures(p);
				
				updateAddArmorsGUI(p);
				
				updateCheckArmors(p);
			}
			updateDamageMessages();
			updateMobSpawners();
			
			ticks = 0;
		}
		
		if(boardTicks >= 20*60) { // 1 minute
			for(Player p : Bukkit.getOnlinePlayers())
				updateScoreboard(p);
			boardTicks = 0;
		}
		
//      /* FOR OLD ZOMBIE CLASSES
		if (zombiesTicks >= 3) {
			updateZombieParticles();

			zombiesTicks = 0;
		}
		if(MainCore.instance.config.enablePreMadeMobs) {
			if(zombieCirclesReload >= (20*60)) { // 1 minute
				MainCore.instance.rankCraft.zombies.reloadArrayZombies10();
				zombieCirclesReload = 0;
			}
		}
		
		if(messageTicks >= 20*60*5) { // 5 minutes
			for(Player p : Bukkit.getOnlinePlayers()) {
				sendRpgClassMessage(p);
			}
			messageTicks = 0;
		}
		
		
		if(outdateTicks >= min20) { // 20 minutes
			sendOutdatedMessage();
			outdateTicks = 0;
		}
		
		updateArrowTrails();
//      */
		for(Player p : Bukkit.getOnlinePlayers()) {
			updateMinLevel(p);
			updateClassItems(p);
		}

	}
	
//	/* Moved to MainCore class
	private void updateScoreboard(Player p) {
		//for (Player p : Bukkit.getOnlinePlayers()) {
		//System.out.println("updating board for "+p.getName());
		if(!ToggleCommand.disabledPlayers.contains(RCUtils.textedUUID(p)))
			MainCore.instance.scoreboard.updateBoard(p);
		//}
	}
//	 */
	
	private void updateMana(Player p) {
		//for (Player p : Bukkit.getOnlinePlayers()) {
		if(stats.canAddMana(p)) {
			float addition = MainCore.instance.rankCraft.magicSkill.getPoints(p) / 50;
			
			stats.addMana(p, 0.5F + addition);
			updateScoreboard(p);
		}
		//}
	}

	private void updateHP(Player p) {
		//for (Player p : Bukkit.getOnlinePlayers()) {
			if(!MainCore.instance.config.enableHPSystem) return;
			
			if(RCUtils.isWorldEnabled(p.getWorld())) {
				
				HPSystem s = MainCore.instance.rankCraft.hpSystem;
	
				if (s.getHP(p) > 0 && s.getHP(p) < s.getMaxHP(p)) {
					//System.out.println(p.getName()+" t");
					s.addHP(p, 1.0);
					updateScoreboard(p);
				}
	
				if (p.getFoodLevel() <= 0 && !p.isDead()) {
					if (!p.isOp() || !(p.getGameMode() == GameMode.CREATIVE)) {
					//	p.sendMessage(ChatColor.DARK_RED + "You are losing HP because you are hungry!!! Eat something!!!");
						p.sendMessage(MainCore.instance.language.getHungerMessage());
						s.removeHP(p, 1.5);
					}
				}
					
				if(!MainCore.instance.config.disableClassicHearts) {
					//p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Math.ceil(s.getMaxHP(p) / 10.0));
					
					double base = Math.ceil(2.0f * s.getMaxHP(p) / MainCore.instance.config.fullHeartHP);
					p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(base);
					
					//p.setMaxHealth(Math.ceil(s.getMaxHP(p) / 10.0));
					
					if (s.getHP(p) > 0) {
						//p.setHealth(Math.ceil(s.getHP(p) / 10.0));
						p.setHealth(Math.ceil(2.0f * s.getHP(p) / MainCore.instance.config.fullHeartHP));
					}
					else {
						p.setHealth(0);
					}
				} else {
					try {
						if(s.getHP(p) > 0)
							p.setHealth((20d * s.getHP(p)) / s.getMaxHP(p));
						else
							p.setHealth(0);
					} catch(Exception ex) {
						MainCore.instance.getLogger().warning("It seems you have changed the option \"DisableClassicHearts\" and some players have not reconnected yet! Please stop and restart your server!");
					}
				}
			/*
				if (s.getHP(p) <= 0 && !p.isDead()) {
					List<ItemStack> items = new ArrayList<>();
					for (ItemStack it : p.getInventory().getContents()) {
						items.add(it);
					}
					for (ItemStack it2 : p.getInventory().getArmorContents()) {
						items.add(it2);
					}
	
					PlayerDeathEvent event = new PlayerDeathEvent(p, items, (int) p.getExp(), p.getName() + " died !!!");
					Bukkit.getServer().getPluginManager().callEvent(event);
				}
			 */
			
				if(s.getHP(p) <= 0) {
					
				//	new BukkitRunnable() {
	
				//		@Override
				//		public void run() {
							p.spigot().respawn();
				//		}
	
				//	}.runTaskLater(MainCore.instance, 1);
					
				}
				
			}
		//}

	
	}

	private void updateDamageMessages() {
		if (!DamageSystem.damageMsgs.isEmpty()) {
			for (int i = 0; i < DamageSystem.damageMsgs.size(); i++) {
				DamageMessage m = DamageSystem.damageMsgs.get(i);
				m.setSeconds(m.getSeconds() - 1);
				if (m.getSeconds() <= 0) {
					DamageSystem.damageMsgs.remove(m);
					m.getMsg().remove();
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void updateActionBar(Player p) {
		//for (Player p : Bukkit.getOnlinePlayers()) {
			
			if(RCUtils.isWorldEnabled(p.getWorld())) {
				if(MainCore.instance.config.enableActionbar) {
				/*
					HPSystem s = MainCore.instance.rankCraft.hpSystem;
		
					String hp = "" + RCUtils.round(s.getHP(p), 1);
					String maxHP = "" + RCUtils.round(s.getMaxHP(p), 1);
		
					StringBuilder b = new StringBuilder();
					String r = ChatColor.RED + "|";
					String g = ChatColor.GRAY + "|";
					int hpBars = 20;
					int hpInt = (int) s.getHP(p);
					int maxHPInt = (int) s.getMaxHP(p);
		
					int x = (hpInt * hpBars) / maxHPInt;
					for (int i = 0; i < x; i++) {
						b.append(r);
					}
		
					for (int i = 0; i < (hpBars - x); i++) {
						b.append(g);
					}
					String healthBar = b.toString();
		
					MainCore.instance.rankCraft.sendActionbar(p,
							ChatColor.RED + "HP: " + ChatColor.DARK_BLUE + "[" + ChatColor.RED + ChatColor.BOLD + healthBar
									+ ChatColor.DARK_BLUE + "]" + " " + ChatColor.RED + hp + "/" + maxHP);
				*/
				/*
					HPSystem hs = MainCore.instance.rankCraft.hpSystem;
					Stats s = MainCore.instance.rankCraft.stats;
					
					String healthBar = hs.getHealthBar(p, 20);
					String manaBar = s.getManaBar(p, 20);
					
					String hp = "" + RCUtils.round(hs.getHP(p), 1);
					String maxHP = "" + RCUtils.round(hs.getMaxHP(p), 1);
					
					String hpMsg = ChatColor.RED + hp + "/" + maxHP;
					String manaMsg = ChatColor.BLUE + ""+s.getMana(p)+"/"+s.getMaxMana(p);
					
					String r = ChatColor.YELLOW+"[";
					String l = ChatColor.YELLOW+"]";
				*/
				//	MainCore.instance.rankCraft.sendActionbar(p, hpMsg+" "+r+healthBar+l+" "+manaMsg+" "+r+manaBar+l);
					
					String text = MainCore.instance.config.actionbarText;
					
					if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
						text = PlaceholderAPI.setPlaceholders(p, text);
					}
					
					text = MainCore.instance.scoreboard.getUpdatedString(text, p);
					text = text.replace('&', '§');
					
					MainCore.instance.rankCraft.sendActionBar(p, text);
					
					
				}
			}
			
		//}
	}

	private void updateQuests(Player p) {
		try {

			for (Quest q : MainCore.instance.rankCraft.quests2) {
				QuestSaver.load(MainCore.instance, q);

			}

			//for (Player p : Bukkit.getOnlinePlayers()) {
				for (Quest q : MainCore.instance.rankCraft.quests2) {

					QuestEvents.checkFinishedQuest(q, p);

					if (q.hmTaken().get(RCUtils.textedUUID(p)) == true) {
						if (q.hmSecondsRemaining().get(RCUtils.textedUUID(p)) >= 1) {

							q.hmSecondsRemaining().put(RCUtils.textedUUID(p),
									q.hmSecondsRemaining().get(RCUtils.textedUUID(p)) - 1);
							// QuestSaver.save(Main.plugin, q);

							if (q.hmSecondsRemaining().get(RCUtils.textedUUID(p)) <= 5) {
							//	p.sendMessage(ChatColor.YELLOW + "Seconds remaining for quest " + q.getName()
							//			+ ChatColor.YELLOW + " : " + ChatColor.RED + ""
							//			+ q.hmSecondsRemaining().get(RCUtils.textedUUID(p)));
								p.sendMessage(MainCore.instance.language.getQuestRemainingSec(q.getName(), q.hmSecondsRemaining().get(RCUtils.textedUUID(p))));
							}
						} else {
							q.hmTaken().put(RCUtils.textedUUID(p), false);
							q.hmSecondsRemaining().put(RCUtils.textedUUID(p), q.getSecondsToComplete());
							// QuestSaver.save(Main.plugin, q);
							//p.sendMessage(q.getName() + "'s" + ChatColor.DARK_RED
							//		+ " time ended !!! You did not finish this quest ! :(");
							p.sendMessage(MainCore.instance.language.getQuestTimeEnd(q.getName()));
						}

						if (q.hmSecondsToReTake().get(RCUtils.textedUUID(p)) >= 1) {
							q.hmSecondsToReTake().put(RCUtils.textedUUID(p),
									q.hmSecondsToReTake().get(RCUtils.textedUUID(p)) - 1);
							// QuestSaver.save(Main.plugin, q);
						}
					}

					if (q.hmSecondsToReTake().get(RCUtils.textedUUID(p)) >= 1) {
						q.hmSecondsToReTake().put(RCUtils.textedUUID(p),
								q.hmSecondsToReTake().get(RCUtils.textedUUID(p)) - 1);
					}

					QuestSaver.save(MainCore.instance, q);
				}
			//}

		} catch (Exception ex) {
			MainCore.instance.getLogger().log(Level.SEVERE, "QuestRunnable Failed !!!!!", ex);
		}
	}

	private void updateQuestGUIs(Player p) {
		//for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getOpenInventory().getTitle().equals(ChatColor.stripColor("Place Blocks Quests"))) {
				MainCore.instance.rankCraft.questGui2.openPlaceBlocks(p);
			}
			if (p.getOpenInventory().getTitle().equals(ChatColor.stripColor("Break Blocks Quests"))) {
				MainCore.instance.rankCraft.questGui2.openBreakBlocks(p);
			}
			if (p.getOpenInventory().getTitle().equals(ChatColor.stripColor("Kill Mobs Quests"))) {
				MainCore.instance.rankCraft.questGui2.openKillMobs(p);
			}
			if (p.getOpenInventory().getTitle().equals(ChatColor.stripColor("Kill Players Quests"))) {
				MainCore.instance.rankCraft.questGui2.openKillPlayers(p);
			}
		//}
	}
	
	private void updateAddArmorsGUI(Player p) {
		if(p.getOpenInventory().getTitle().equals(MainCore.instance.rankCraft.mobCreator.addArmorsTitle)) {
			MobCreator mc = MainCore.instance.rankCraft.mobCreator;
			if(p.getOpenInventory().getItem(mc.helmetSlot) != null && !mc.isHelmet(p.getOpenInventory().getItem(mc.helmetSlot))) {
				final ItemStack is = p.getOpenInventory().getItem(mc.helmetSlot);
				p.getOpenInventory().setItem(mc.helmetSlot, new ItemStack(Material.AIR));
				p.getInventory().addItem(is);
			}
			if(p.getOpenInventory().getItem(mc.chestSlot) != null && !mc.isChest(p.getOpenInventory().getItem(mc.chestSlot))) {
				final ItemStack is = p.getOpenInventory().getItem(mc.chestSlot);
				p.getOpenInventory().setItem(mc.chestSlot, new ItemStack(Material.AIR));
				p.getInventory().addItem(is);
			}
			if(p.getOpenInventory().getItem(mc.legsSlot) != null && !mc.isLegs(p.getOpenInventory().getItem(mc.legsSlot))) {
				final ItemStack is = p.getOpenInventory().getItem(mc.legsSlot);
				p.getOpenInventory().setItem(mc.legsSlot, new ItemStack(Material.AIR));
				p.getInventory().addItem(is);
			}
			if(p.getOpenInventory().getItem(mc.bootsSlot) != null && !mc.isBoots(p.getOpenInventory().getItem(mc.bootsSlot))) {
				final ItemStack is = p.getOpenInventory().getItem(mc.bootsSlot);
				p.getOpenInventory().setItem(mc.bootsSlot, new ItemStack(Material.AIR));
				p.getInventory().addItem(is);
			}
		}
	}

	private void updateArrowTrails() {
		
		Particle par1 = Particle.DRIP_WATER;
		Particle par2 = Particle.FIREWORKS_SPARK;
		Particle par3 = Particle.DRIP_LAVA;
		Particle par4 = Particle.EXPLOSION_LARGE;
		if(MainCore.instance.config.enablePreMadeMobs) {
			for (Arrow a : MainCore.instance.rankCraft.skeletons.arrows1_3) {
				if (!a.isOnGround() && !a.isDead() && !(a == null)) {
					a.getWorld().spawnParticle(par1, a.getLocation(), 10);
				//	a.getWorld().playEffect(a.getLocation(), Effect.GHAST_SHOOT, 10);
				}
			}
			for (Arrow a : MainCore.instance.rankCraft.skeletons.arrows4_6) {
				if (!a.isOnGround() && !a.isDead() && !(a == null)) {
					a.getWorld().spawnParticle(par2, a.getLocation(), 10);
				//	a.getWorld().playEffect(a.getLocation(), Effect.FIREWORK_SHOOT, 10);
				}
			}
			for (Arrow a : MainCore.instance.rankCraft.skeletons.arrows7_9) {
				if (!a.isOnGround() && !a.isDead() && !(a == null)) {
					a.getWorld().spawnParticle(par3, a.getLocation(), 10);
				//	a.getWorld().playEffect(a.getLocation(), Effect.BLAZE_SHOOT, 10);
				}
			}
			for (Arrow a : MainCore.instance.rankCraft.skeletons.arrows10) {
				if (!a.isOnGround() && !a.isDead() && !(a == null)) {
					a.getWorld().spawnParticle(par4, a.getLocation(), 4);
				//	a.getWorld().playEffect(a.getLocation(), Effect.WITHER_SHOOT, 10);
				}
			}
		}
		/*
		// TreeAxe bullets' trail
		Particle par5 = Particle.SLIME;
		List<Snowball> snowballs = new ArrayList<>();
		for(int i : MainCore.instance.rankCraft.specialItems.treeAxe.bullets) {
			for(World w : Bukkit.getWorlds()) {
				for(Entity e : w.getEntities()) {
					if(e instanceof Snowball) {
						if(e.getEntityId() == i) {
							snowballs.add((Snowball) e);
						}
					}
				}
			}
		}
		
		for(Snowball ball : snowballs) {
			if(!ball.isOnGround() && !ball.isDead() && !(ball == null)) {
				ball.getWorld().spawnParticle(par5, ball.getLocation(), 5);
		//		ball.getWorld().playEffect(ball.getLocation(), Effect.ENDER_SIGNAL, 5);
			}
		}
		// IceBattleAxe bullets' trail
		Particle par6 = Particle.WATER_BUBBLE;
		List<Snowball> ice = new ArrayList<>();
		for(int i : MainCore.instance.rankCraft.specialItems.iceBattleAxe.bullets) {
			for(World w : Bukkit.getWorlds()) {
				for(Entity e : w.getEntities()) {
					if(e instanceof Snowball) {
						if(e.getEntityId() == i) {
							ice.add((Snowball)e);
						}
					}
				}
			}
		}
		for(Snowball s2 : ice) {
			if(!s2.isOnGround() && !s2.isDead() && !(s2 == null)) {
				s2.getWorld().spawnParticle(par6, s2.getLocation(), 5);
			//	s2.getWorld().playEffect(s2.getLocation(), Effect.GHAST_SHRIEK, 5);
			}
		}
		*/
		
		// New Method
		playEffectBehindBall(MainCore.instance.rankCraft.specialItemsFactory.iceBattleAxeEvent.balls, Particle.WATER_BUBBLE);
		playEffectBehindBall(MainCore.instance.rankCraft.specialItemsFactory.treeAxeEvent.balls, Particle.SLIME);
		
	}
	
	private void playEffectBehindBall(HashMap<Integer, String> map, Particle effect) {
		for(int i : map.keySet()) {
			World world = Bukkit.getWorld(map.get(i));
			for(Entity e : world.getEntities()) {
				if(e instanceof Snowball) {
					if(e.getEntityId() == i) {
						Snowball ball = (Snowball) e;
						world.spawnParticle(effect, ball.getLocation(), 5);
					}
				}
			}
		}
	}

	private void updateZombieParticles() {
		Particle z10 = Particle.FLAME;
	
		if(MainCore.instance.config.enablePreMadeMobs) {
			int R = 1;
			float x;
			float y;
			float z;
			float centreX;
			float centreZ;
	
			for (Zombie zombie : MainCore.instance.rankCraft.zombies.zombies10) {
							
				if(!zombie.getWorld().getEntities().contains(zombie)) {
					MainCore.instance.rankCraft.zombies.reloadArrayZombies10();
					return;
				}
								
				y = (float) zombie.getLocation().getY();
				centreX = (float) zombie.getLocation().getX();
				centreZ = (float) zombie.getLocation().getZ();
	
				for (int phi = 0; phi <= 360; phi += 10) {
					x = (float) (centreX + R * Math.sin(phi));
					z = (float) (centreZ + R * Math.cos(phi));
	
					zombie.getWorld().spawnParticle(z10, new Location(zombie.getWorld(), x, y, z), 1);
				//	zombie.getWorld().playEffect(new Location(zombie.getWorld(), x, y, z), Effect.BOW_FIRE, 1);
				}
	
			}
		}
	}

	private void updateRpgItems(Player p) {
	/*
		// Excalibur Sword
		for (Player p : Bukkit.getOnlinePlayers()) {
			MainCore.instance.rankCraft.excalibur.secondDown(p);
		}
		// TreeAxe
		for(Player p : Bukkit.getOnlinePlayers()) {
			MainCore.instance.rankCraft.treeAxe.secondDown(p);
		}
		// IceBattleAxe
		for(Player p : Bukkit.getOnlinePlayers()) {
			MainCore.instance.rankCraft.iceBattleAxe.secondDown(p);
		}
		// ZeusSword
		for(Player p : Bukkit.getOnlinePlayers()) {
			MainCore.instance.rankCraft.zeusSword.secondDown(p);
		}
		
	*/
	//	ItemStack hand = p.getInventory().getItemInMainHand();
		/*
		if(hand.getType() == Material.WOODEN_AXE) {
			if(hand.hasItemMeta()) {
				if(hand.getItemMeta().hasLore()) { 
					if(hand.getItemMeta().hasDisplayName()) {
						if(hand.getItemMeta().getDisplayName().startsWith(TreeAxe.NAME_WITHOUT_SEC)) {
							if(!RCUtils.hasAbilityFuelLore(hand)) {
								//p.setItemOnCursor(MainCore.instance.rankCraft.treeAxe.getItem());
								p.getInventory().setItemInMainHand(MainCore.instance.rankCraft.treeAxe.getItem());
							}
						}
					}
				}
			}
		}
		*/
		/*
		if(hand.getType() == Material.BOW) {
			if(hand.hasItemMeta()) {
				if(hand.getItemMeta().hasLore()) {
					if(hand.getItemMeta().hasDisplayName()) {
						if(hand.getItemMeta().getDisplayName().equals(DefaultCrossbow.CROSSBOW_NAME)) {
							if(!RCUtils.hasDurabilityLore(hand)) {
								//p.setItemOnCursor(MainCore.instance.rankCraft.crossbow.getItem());
								p.getInventory().setItemInMainHand(MainCore.instance.rankCraft.crossbow.getItem());
							}
						}
					}
				}
			}
		}
		*/
		
		/*
		 * 
		 * Add task for Durability and Fuel Items!!!
		 * 
		 * 
		 * 
		 */
		
		/*
		// Task for Durability Items
		for(DynamicItem item : MainCore.instance.rankCraft.dynamicItems) {
			if(hand.getType() == item.getMaterial()) {
				if(hand.hasItemMeta()) { 
					if(hand.getItemMeta().hasLore()) { 
						if(hand.getItemMeta().hasDisplayName()) {
							if(hand.getItemMeta().getDisplayName().startsWith(item.getNameWithoutSec())) {
								if(!item.hasDurabilityLore(hand)) {
									//p.setItemOnCursor(item.getItem());
									p.getInventory().setItemInMainHand(item.getItem());
									//System.out.println("no durability lore");
								}
							}
						}
					}
				}
			}
			
			for(int i = 0; i <= 35; i++) {
				ItemStack invItem = p.getInventory().getItem(i);
				if(invItem != null && invItem.getType() != Material.AIR) {
					if(invItem.hasItemMeta()) {
						if(invItem.getItemMeta().hasDisplayName()) {
							if(invItem.getItemMeta().getDisplayName().startsWith(item.getNameWithoutSec())) {
								if(item.getUseDelay() <= 0 && item.getSeconds(invItem) > 0) {
									//p.getInventory().setItem(i, item.secondDown(invItem));
									
									//p.updateInventory();
									//System.out.println("----->Pre: "+item.getSeconds(invItem));
									p.getInventory().setItem(i, item.getItemWithSecAndDurability(invItem, (item.getSeconds(invItem)-1), item.getDurability(invItem)));
									//p.setItemInHand(item.getItemWithSecAndDurability(invItem, (item.getSeconds(invItem)-1), item.getDurability(invItem)));
									//System.out.println("------->Post: "+item.getSeconds(invItem));
									item.setUseDelay(1);
								} 
								item.setUseDelay(item.getUseDelay() - 1);
								//System.out.println("second down");
							}
						}
					}
				}
			}
			
		}
		
		*/
		
		//for(Player p : Bukkit.getOnlinePlayers()) {
		//MainCore.instance.rankCraft.excalibur.secondDown(p);
		//MainCore.instance.rankCraft.treeAxe.secondDown(p);
		//MainCore.instance.rankCraft.iceBattleAxe.secondDown(p);
		//MainCore.instance.rankCraft.zeusSword.secondDown(p);
		//MainCore.instance.rankCraft.crossbow.secondDown(p);
		/*
		for(int i = 0; i <= 35; i++) {
			ItemStack item = p.getInventory().getItem(i);
			if(item != null && item.getType() != Material.AIR) {
				if (item.hasItemMeta()) {
					if (item.getItemMeta().hasDisplayName()) {
						if (item.getItemMeta().getDisplayName().startsWith(TreeAxe.NAME_WITHOUT_SEC)) {
							if(MainCore.instance.rankCraft.treeAxe.hasSecInName(item)) {	
								p.getInventory().setItem(i, MainCore.instance.rankCraft.treeAxe.secondDown(item));
							} else {
								p.getInventory().setItem(i, MainCore.instance.rankCraft.treeAxe.getItemWithSeconds(item, 0));
							}
						}
						// / *
						if(item.getItemMeta().getDisplayName().startsWith(FireCrossbow.NAME_WITHOUT_SEC)) {
							p.getInventory().setItem(i, MainCore.instance.rankCraft.fireCrossbow.secondDown(item));
						}
						// * /
					}
				}
			}
		}
		*/
		if(MainCore.instance.config.makeAutoRpg) {
			DamageSystem.makeRpgHandItem(p);
		}
		//}
			
		
	}

//	@SuppressWarnings("deprecation")
	private void updateMinLevel(Player p) {
		//for (Player p : Bukkit.getOnlinePlayers()) {
		ItemStack hand = p.getInventory().getItemInMainHand();
			if (DamageSystem.isRpgItem(hand) && hand.getType() != Material.AIR && hand != null) {
				if (!DamageSystem.isPlayerLevelForRpgItem(p, hand)) {
					
				//	p.sendMessage("Sorry, you must be at least at level "
				//			+ DamageSystem.getMinLevelOfRpgItem(p.getItemInHand()) + " to use this item!!!");
					
					p.sendMessage(MainCore.instance.language.getCannotUseWeapon(DamageSystem.getMinLevelOfRpgItem(hand)));
					
					if(p.getInventory().getHeldItemSlot() < 8) { 
						p.getInventory().setHeldItemSlot(p.getInventory().getHeldItemSlot() + 1);
					} else {
						p.getInventory().setHeldItemSlot(0);
					}
					//System.out.println(p.getItemInHand());
				}
			}
			/* No need
			if(DamageSystem.isArmorPiece(hand)) {
				if(DamageSystem.isRpgArmor(hand) && hand.getType() != Material.AIR && hand != null) {
					if(!DamageSystem.isPlayerLevelForRpgArmor(p, hand)) {
						p.sendMessage("You cannot use this armor");
						if(p.getInventory().getHeldItemSlot() < 8) { 
							p.getInventory().setHeldItemSlot(p.getInventory().getHeldItemSlot() + 1);
						} else {
							p.getInventory().setHeldItemSlot(0);
						}
					}
				}
			}
			*/
		//}
	}
	
	private void updateShop(Player p) {
		if(MainCore.instance.config.enableShop) {
			RandomItemsShop shop = MainCore.instance.rankCraft.shop;
			
			shop.updateNextRefresh();
			if(shop.getNextRefresh() <= 0) {
				//for(Player p : Bukkit.getOnlinePlayers()) {
					if(p.getOpenInventory().getTitle().equals(MainCore.instance.rankCraft.shop.title)) {
						p.closeInventory();
					}
				//}
				shop.resetNextRefresh();
				shop.setRefreshTrue();
			//	Bukkit.broadcastMessage(ChatColor.RED+"[RankCraft]"+ChatColor.GREEN+"General Shop refreshed!");
				Bukkit.broadcastMessage(MainCore.instance.language.getShopRefreshed());
			}
			
			//for(Player p : Bukkit.getOnlinePlayers()) {
				if(p.getOpenInventory().getTitle().equals(MainCore.instance.rankCraft.shop.title)) {
					MainCore.instance.rankCraft.shop.open(p);
				}
			//}
		}
	}
	
	private void updateCheckArmors(Player p) {
		if(p.getInventory().getHelmet() != null
				|| p.getInventory().getChestplate() != null
				|| p.getInventory().getLeggings() != null
				|| p.getInventory().getBoots() != null) {
			for(int i = 36; i <= 39; i++) {
				ItemStack item = p.getInventory().getItem(i);
				if(item != null) {
					if(DamageSystem.isArmorPiece(item) && DamageSystem.isRpgArmor(item)) {
						if(!DamageSystem.isPlayerLevelForRpgArmor(p, item)) {
							p.getInventory().setItem(i, new ItemStack(Material.AIR));
							p.getInventory().addItem(item);
						}
					}
				}
			}
		}
	}
	
	
	
	private void updateMobSpawners() {
		
		if(MainCore.instance.config.enableCustomMobs) {
		
			for(MobSpawner spawner : MainCore.instance.rankCraft.mobSpawners) {
				
				Location loc2 = new Location(Bukkit.getWorld(spawner.getWorld()), spawner.getX(), spawner.getY(), spawner.getZ());
				Location centre = getCenter(loc2);
				
				if(RCUtils.isPlayerNearby(centre, MainCore.instance.config.playerRange)) {
					
					double minX = spawner.getX()-spawner.getRange();
					double maxX = spawner.getX()+spawner.getRange();
					double minZ = spawner.getZ()-spawner.getRange();
					double maxZ = spawner.getZ()+spawner.getRange();
					Random random = new Random();
					
					double centreX = centre.getX();
					double centreZ = centre.getZ();
					double spawnX = minX + (maxX - minX) * random.nextDouble();
					double spawnZ = minZ + (maxZ - minZ) * random.nextDouble();
					
					int y = spawner.getY();
					
					if(spawner.getSec() > 0) {
						spawner.setSec(spawner.getSec() - 1);
						spawner.getC().set("Options.sec", spawner.getSec());
						spawner.save();
					} else if(spawner.getSec() <= 0) {
						spawner.setSec(spawner.getEvery());
						spawner.getC().set("Options.sec", spawner.getSec());
						spawner.save();
						
						World w = Bukkit.getWorld(spawner.getWorld());
						Location loc = new Location(Bukkit.getWorld(spawner.getWorld()),spawner.getX(),spawner.getY()+1,spawner.getZ());
						Location spawnLoc = new Location(Bukkit.getWorld(spawner.getWorld()), spawnX, spawner.getY()+1, spawnZ);
						YamlMob yMob = MainCore.instance.rankCraft.getYamlMobFromFileName(spawner.getMob());
						
						if(yMob != null) {
							
							if(MainCore.instance.getWorldGuard() != null && MainCore.instance.getWorldEdit() != null) {
								RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
								RegionQuery query = container.createQuery();
								ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(loc));
								//System.out.println("checking flag");
								//System.out.println(set.testState(null, Flags.MOB_SPAWNING));
								if(!set.testState(null, Flags.MOB_SPAWNING)) {
									//return;
									continue;
								}
								
							}
							
							if(!mobsOkToSpawn(loc2, yMob)) {
								//System.out.println("Spawner: Nah! "+loc2.toString());
								continue;
							}
							
							if(yMob.getType().equalsIgnoreCase("ZOMBIE")) {
								Zombie z = (Zombie) w.spawnEntity(spawnLoc, EntityType.ZOMBIE);
								yMob.makeMob(z);
								//System.out.println(z.getCustomName()+" - "+z.getLocation());
							} else if(yMob.getType().equalsIgnoreCase("SKELETON")) {
								Skeleton s = (Skeleton) w.spawnEntity(spawnLoc, EntityType.SKELETON);
								yMob.makeMob(s);
							}
							Bukkit.getWorld(spawner.getWorld()).playEffect(new Location(Bukkit.getWorld(spawner.getWorld()), centreX, y, centreZ), Effect.SMOKE, 1);
						}
						
					}
					
					
					double R = 0.5;
					
		
					for (int phi = 0; phi <= 360; phi += 50) {
						float x =  (float) (centreX + R * Math.sin(phi));
						float z =  (float) (centreZ + R * Math.cos(phi));
		
					
						Bukkit.getWorld(spawner.getWorld()).playEffect(new Location(Bukkit.getWorld(spawner.getWorld()), x, y, z), Effect.MOBSPAWNER_FLAMES, 1);
					}
				}
			}
		}
	}
	
	private boolean mobsOkToSpawn(Location loc, YamlMob mob) {
		if(MainCore.instance.config.spawnerSearch) {
			int range = MainCore.instance.config.searchRange;
			List<Entity> entities = (List<Entity>) loc.getWorld().getNearbyEntities(loc, range, range, range);
			int actualSize = 0;
			String criteria = MainCore.instance.config.searchCriteria;
			
			// Calculate the mobs based on 'criteria'
			if(criteria.equalsIgnoreCase(RCUtils.SEARCH_CRITERIA_SAMEMOB)) {
				
				for(Entity en : entities) {
					if(en instanceof LivingEntity) {
						LivingEntity len = (LivingEntity) en;

						if(RCUtils.getYamlMobFromEntity(len) != null 
								&& RCUtils.getYamlMobFromEntity(len).getDisplayName().equals(mob.getDisplayName())) 
							actualSize++;
						
					}
				}
				
			} else if(criteria.equalsIgnoreCase(RCUtils.SEARCH_CRITERIA_SAMETYPE)) {
				
				EntityType enType = EntityType.SKELETON;
				if(mob.getType().equalsIgnoreCase("ZOMBIE")) 
					enType = EntityType.ZOMBIE;
								
				for(Entity en : entities) 
					if(en.getType() == enType)
						actualSize++;
				
			} else { // search for all
				for(Entity en : entities) {
					if(en instanceof LivingEntity)
						actualSize++;
				}
			}
			
			// Check if able to spawn
			if(MainCore.instance.config.searchAmount < actualSize)
				return false;
			
		}
		return true;
	}
	
	private Location getCenter(Location loc) {
	    return new Location(loc.getWorld(),
	        getRelativeCoord(loc.getBlockX()),
	        getRelativeCoord(loc.getBlockY()),
	        getRelativeCoord(loc.getBlockZ()));
	}
	 
	private double getRelativeCoord(int i) {
	    double d = i;
	    d = d < 0 ? d - .5 : d + .5;
	    return d;
	}
	
	private void sendRpgClassMessage(Player p) {
		//for(Player p : Bukkit.getOnlinePlayers()) {
			
			if(RCUtils.isWorldEnabled(p.getWorld())) {
			
				if(RpgClassData.getClassPoints(p) > 0) {
			//		p.sendMessage("You have "+RpgClassData.getClassPoints(p)+" class points! Type '/rc selectclass' to unlock a new class!");
					
					p.sendMessage(MainCore.instance.language.getSendClassPoints(RpgClassData.getClassPoints(p)));
				}
				
			}
		//}
	}
	
	private void updateClassItems(Player p) {
		
		RankCraft r = MainCore.instance.rankCraft;
		ItemStack hand = p.getInventory().getItemInMainHand();
		//for(Player p : Bukkit.getOnlinePlayers()) {
			if(hand != null) {
				if(hand.hasItemMeta()) {
					ItemStack item = hand;
					if(item.getItemMeta().hasLore()) {
						
						for(String s : item.getItemMeta().getLore()) {
							if(s.startsWith(ChatColor.GRAY+"Class")) {
								s = ChatColor.stripColor(s);
								
								String[] parts = s.split(": ");
								
								if(r.gladiatorClass.getId().equals(parts[1]) || r.archerClass.getId().equals(parts[1]) || r.ninjaClass.getId().equals(parts[1]) 
										||	r.wizardClass.getId().equals(parts[1])) {
								
									if(RpgClassData.getCurrentClass(p).equals(r.mysteriousClass.getId())) {
										return;
									}
								}
								
								if(!RpgClassData.getCurrentClass(p).equals(parts[1])) {
									if(p.getInventory().getHeldItemSlot() < 8) {
										p.getInventory().setHeldItemSlot(p.getInventory().getHeldItemSlot() + 1);
									} else {
										p.getInventory().setHeldItemSlot(0);
									}
							//		p.sendMessage(ChatColor.RED+"Only players in "+ RCUtils.getRpgClassById(parts[1]).getName()+ChatColor.RED+" class can hold this item!");
									p.sendMessage(MainCore.instance.language.getCannotHoldItem(RCUtils.getRpgClassById(parts[1]).getName()));
								
								}
							}
						}
						
					}
				}
			}
		//}
	}
	
	private void updateEffectCooldown(Player p) {
		//for(Player p : Bukkit.getOnlinePlayers()) {
			MyEvents.cooldownCount(p);
		//}
	}
	
	private void updateOldTreasures(Player p) {
		
		ItemStack item = p.getInventory().getItemInMainHand();
		String id = "";
		
		if(item.getType() == Material.CHEST) {
			if(item.hasItemMeta()) {
				if(item.getItemMeta().hasDisplayName()) {
					String name = item.getItemMeta().getDisplayName();
					if(name.equals(RCUtils.NORMAL_TREASURE_NAME)) {
						id = "common";
					} else if(name.equals(RCUtils.SUPER_TREASURE_NAME)) {
						id = "rare";
					} else if(name.equals(RCUtils.ULTRA_TREASURE_NAME)) {
						id = "epic";
					} else {
						return;
					}
					
					int amount = item.getAmount();
					for(int i = 0; i < amount; i++) {
						RCUtils.removeItemFromHand(p);
					}
						
					TreasureChest2 tc2 = MainCore.instance.rankCraft.getTreasureById(id);
					tc2.giveToPlayer(p, amount);
				}
			}
		}
	}
	
	private void sendOutdatedMessage() {
		if(MainCore.instance.config.sendOutdateMessage && MainCore.instance.outdated)
			MainCore.instance.getServer().getConsoleSender().sendMessage(MainCore.instance.language.getPrefix()
					+"§4RankCraft is outdated! Download the update at https://www.spigotmc.org/resources/rankcraft-the-ultimate-rpg-plugin.37754/");
	}
	
}
