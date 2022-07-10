package me.amc.rankcraft.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.ConfigHelper;
import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.RankCraft;
import me.amc.rankcraft.mobs.YamlMob;
import me.amc.rankcraft.rpgitem.CrystalSize;
import me.amc.rankcraft.rpgitem.ManaCrystal;
import me.amc.rankcraft.rpgitem.XpCrystal;
import me.amc.rankcraft.rpgitem.YamlItem;
import me.amc.rankcraft.spells.AntiHungerSpell;
import me.amc.rankcraft.spells.AntiPoisonSpell;
import me.amc.rankcraft.spells.BombSpell;
import me.amc.rankcraft.spells.DeathTouchSpell;
import me.amc.rankcraft.spells.FarmSpell;
import me.amc.rankcraft.spells.FeedSpell;
import me.amc.rankcraft.spells.HealSpell;
import me.amc.rankcraft.spells.HeartsSpell;
import me.amc.rankcraft.spells.HungerSpell;
import me.amc.rankcraft.spells.LightningSpell;
import me.amc.rankcraft.spells.PoisonSpell;
import me.amc.rankcraft.spells.Spell;
import me.amc.rankcraft.spells.SpiderSpell;
import me.amc.rankcraft.stats.Gold;
import me.amc.rankcraft.stats.MobStats;
import me.amc.rankcraft.stats.Stats;
import me.amc.rankcraft.stats.Stats.CountMobs;
import me.amc.rankcraft.utils.RCUtils;
import me.amc.rankcraft.yaml.MobsYaml;

public class MobStatsEvents implements Listener {

	private Stats s;
	private MobStats m;
	private Gold g;
	private RankCraft rankCraft;
	private ConfigHelper config;

	public MobStatsEvents() {
		rankCraft = MainCore.instance.rankCraft;
		s = rankCraft.stats;
		m = rankCraft.mobStats;
		g = rankCraft.gold;

		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
		
		config = MainCore.instance.config;
	}

	@EventHandler
	public void onKill(EntityDeathEvent e) {
		LivingEntity dead = e.getEntity();
		Entity killer = dead.getKiller();
		World w = dead.getWorld();
		Location l = dead.getLocation();
		MobsYaml y = MainCore.instance.rankCraft.mobsYaml;

		
		
		if(dead instanceof Zombie || dead instanceof Skeleton) {
			
			if(!RCUtils.isWorldEnabled(w))
				return;
			
			if(RCUtils.getYamlMobFromEntity(dead) == null) {
				//MainCore.instance.getLogger().warning("Could not identify Custom Mob!");
				return;
			}
			
			YamlMob yMob = RCUtils.getYamlMobFromEntity(dead);
			
			//e.getDrops().clear();
			
			
			if(yMob != null) {
				/*
			//	e.getDrops().addAll(yMob.getDrops());
				if(!(yMob.getDrops() == null)) {
					int bound = new Random().nextInt(yMob.getDrops().size());
			//		System.out.println("bound: "+bound);
					for(int i = 0; i < bound; i++) {
						e.getDrops().add(yMob.getDrops().get(new Random().nextInt(yMob.getDrops().size())));
					}
					
					
				}
				*/
				
				if(yMob.getLevel() > 0)
					e.setDroppedExp(new Random().nextInt(yMob.getLevel())+1);
				
				if(yMob.getDrops() != null) {
					if(!yMob.getDrops().isEmpty()) {
						int drops = new Random().nextInt(4);
						for(int i = 0; i < drops; i++) {
							int r = new Random().nextInt(yMob.getDrops().size());
							ItemStack drop = yMob.getDrops().get(r).clone();
							drop.setAmount(new Random().nextInt(drop.getAmount()+1));
							e.getDrops().add(drop);
						}
					}
				}
				
				if(MainCore.instance.config.canDropRpgItem) {
					
					Random r = new Random();
					int c100 = r.nextInt(101);
					
					if(c100 < MainCore.instance.config.dropRpgItemChances) {
						
						int level = yMob.getLevel();
						int max = level + MainCore.instance.config.dropRpgItemRadius;
						int min = level - MainCore.instance.config.dropRpgItemRadius;
						List<YamlItem> inBounds = new ArrayList<YamlItem>();
						
						for(YamlItem item : MainCore.instance.rankCraft.yamlWeapons) {
							if(item.getMinLevel() >= min && item.getMinLevel() <= max) {
								inBounds.add(item);
							}
						}
						
						if(!inBounds.isEmpty()) {
							e.getDrops().add(inBounds.get(new Random().nextInt(inBounds.size())).getBuiltRpgItem().getItem());
						}
					
					}
					
				}
			}
		}
		
		if (killer instanceof Player && RCUtils.isWorldEnabled(w)) {
			Player p = (Player) killer;
			
			switch (dead.getType()) {
			case CHICKEN:
				s.addMobXp(CountMobs.Chicken, p);
				m.addMobKills(p, CountMobs.Chicken);
				if(y.getIfCanDropGold(CountMobs.Chicken.toString())) {
					w.dropItem(l, g.toItem(y.getGold(CountMobs.Chicken.toString())));
				}
				if(y.getIfCanDropSpell(CountMobs.Chicken.toString())) {
					dropSpell(w, l);
				}
				break;
			case COW:
				s.addMobXp(CountMobs.Cow, p);
				m.addMobKills(p, CountMobs.Cow);
				if(y.getIfCanDropGold(CountMobs.Cow.toString())) {
					w.dropItem(l, g.toItem(y.getGold(CountMobs.Cow.toString())));
				}
				if(y.getIfCanDropSpell(CountMobs.Cow.toString())) {
					dropSpell(w, l);
				}
				break;
			case CREEPER:
				s.addMobXp(CountMobs.Creeper, p);
				m.addMobKills(p, CountMobs.Creeper);
				if(y.getIfCanDropGold(CountMobs.Creeper.toString())) {
					w.dropItem(l, g.toItem(y.getGold(CountMobs.Creeper.toString())));
				}
				if(y.getIfCanDropSpell(CountMobs.Creeper.toString())) {
					dropSpell(w, l);
				}
				break;
			case ENDERMAN:
				s.addMobXp(CountMobs.Enderman, p);
				m.addMobKills(p, CountMobs.Enderman);
				if(y.getIfCanDropGold(CountMobs.Enderman.toString())) {
					w.dropItem(l, g.toItem(y.getGold(CountMobs.Enderman.toString())));
				}
				if(y.getIfCanDropSpell(CountMobs.Enderman.toString())) {
					dropSpell(w, l);
				}
				break;
			case GHAST:
				s.addMobXp(CountMobs.Ghast, p);
				m.addMobKills(p, CountMobs.Ghast);
				if(y.getIfCanDropGold(CountMobs.Ghast.toString())) {
					w.dropItem(l, g.toItem(y.getGold(CountMobs.Ghast.toString())));
				}
				if(y.getIfCanDropSpell(CountMobs.Ghast.toString())) {
					dropSpell(w, l);
				}
				break;
			case HORSE:
				s.addMobXp(CountMobs.Horse, p);
				m.addMobKills(p, CountMobs.Horse);
				if(y.getIfCanDropGold(CountMobs.Horse.toString())) {
					w.dropItem(l, g.toItem(y.getGold(CountMobs.Horse.toString())));
				}
				if(y.getIfCanDropSpell(CountMobs.Horse.toString())) {
					dropSpell(w, l);
				}
				break;
			case PIG:
				s.addMobXp(CountMobs.Pig, p);
				m.addMobKills(p, CountMobs.Pig);
				if(y.getIfCanDropGold(CountMobs.Pig.toString())) {
					w.dropItem(l, g.toItem(y.getGold(CountMobs.Pig.toString())));
				}
				if(y.getIfCanDropSpell(CountMobs.Pig.toString())) {
					dropSpell(w, l);
				}
				break;
			case RABBIT:
				s.addMobXp(CountMobs.Rabbit, p);
				m.addMobKills(p, CountMobs.Rabbit);
				if(y.getIfCanDropGold(CountMobs.Rabbit.toString())) {
					w.dropItem(l, g.toItem(y.getGold(CountMobs.Rabbit.toString())));
				}
				if(y.getIfCanDropSpell(CountMobs.Rabbit.toString())) {
					dropSpell(w, l);
				}
				break;
			case SHEEP:
				s.addMobXp(CountMobs.Sheep, p);
				m.addMobKills(p, CountMobs.Sheep);
				if(y.getIfCanDropGold(CountMobs.Sheep.toString())) {
					w.dropItem(l, g.toItem(y.getGold(CountMobs.Sheep.toString())));
				}
				if(y.getIfCanDropSpell(CountMobs.Sheep.toString())) {
					dropSpell(w, l);
				}
				break;
			case SKELETON:
				s.addMobXp(CountMobs.Skeleton, p);
				m.addMobKills(p, CountMobs.Skeleton);
				if(y.getIfCanDropGold(CountMobs.Skeleton.toString())) {
					w.dropItem(l, g.toItem(y.getGold(CountMobs.Skeleton.toString())));
				}
				if(y.getIfCanDropSpell(CountMobs.Skeleton.toString())) {
					dropSpell(w, l);
				}
				break;
			case SLIME:
				s.addMobXp(CountMobs.Slime, p);
				m.addMobKills(p, CountMobs.Slime);
				if(y.getIfCanDropGold(CountMobs.Slime.toString())) {
					w.dropItem(l, g.toItem(y.getGold(CountMobs.Slime.toString())));
				}
				if(y.getIfCanDropSpell(CountMobs.Slime.toString())) {
					dropSpell(w, l);
				}
				break;
			case SPIDER:
				s.addMobXp(CountMobs.Spider, p);
				m.addMobKills(p, CountMobs.Spider);
				if(y.getIfCanDropGold(CountMobs.Spider.toString())) {
					w.dropItem(l, g.toItem(y.getGold(CountMobs.Spider.toString())));
				}
				if(y.getIfCanDropSpell(CountMobs.Spider.toString())) {
					dropSpell(w, l);
				}
				break;
			case VILLAGER:
				s.addMobXp(CountMobs.Villager, p);
				m.addMobKills(p, CountMobs.Villager);
				if(y.getIfCanDropGold(CountMobs.Villager.toString())) {
					w.dropItem(l, g.toItem(y.getGold(CountMobs.Villager.toString())));
				}
				if(y.getIfCanDropSpell(CountMobs.Villager.toString())) {
					dropSpell(w, l);
				}
				break;
			case ZOMBIE:
				s.addMobXp(CountMobs.Zombie, p);
				m.addMobKills(p, CountMobs.Zombie);
				if(y.getIfCanDropGold(CountMobs.Zombie.toString())) {
					w.dropItem(l, g.toItem(y.getGold(CountMobs.Zombie.toString())));
				}
				if(y.getIfCanDropSpell(CountMobs.Zombie.toString())) {
					dropSpell(w, l);
				}
				break;
			case PLAYER:
				s.addXp(p, y.getXP("Player"));
				if(y.getIfCanDropGold("Player")) {
					w.dropItem(l, g.toItem(y.getGold("Player")));
				}
				if(y.getIfCanDropSpell("Player")) {
					dropSpell(w, l);
				}
			default:
				break;

			}
			
			
			s.checkLevelAndRank(p);
		}
	/*	
		for(int i = 0; i < e.getDrops().size(); i++) {
			if(e.getDrops().get(i) != null)
				e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), e.getDrops().get(i));
		}
	*/
		if(RCUtils.isWorldEnabled(w)) {
			MainCore.instance.rankCraft.rpgClassEvents.gladiatorEvent(e);
			MainCore.instance.rankCraft.rpgClassEvents.ninjaEvent(e);
		}
	}
	
	private void dropSpell(World w, Location l) {
		Random ran = new Random();
		Spell[] normal = new Spell[] { new LightningSpell(1), new FeedSpell(1), new FeedSpell(2), new BombSpell(1),
				new FarmSpell(1), new HungerSpell(1), new PoisonSpell(1), new SpiderSpell(1), new SpiderSpell(2),
				new HealSpell(1), new HealSpell(2) };
		Spell[] uncommon = new Spell[] { new LightningSpell(2), new BombSpell(2), new FarmSpell(2),
				new HungerSpell(2), new PoisonSpell(2), new FeedSpell(3), new FeedSpell(4), new SpiderSpell(3),
				new SpiderSpell(4), new HealSpell(3), new HealSpell(4), new AntiHungerSpell(1),
				new AntiPoisonSpell(1) };
		Spell[] epic = new Spell[] { new LightningSpell(3), new BombSpell(3), new FarmSpell(3), new HungerSpell(3),
				new PoisonSpell(3), new FeedSpell(5), new HealSpell(5), new AntiHungerSpell(1),
				new AntiPoisonSpell(1), new DeathTouchSpell(1), new HeartsSpell(1) };

		if(ran.nextInt(101) <= 100) {
			int ch = ran.nextInt(101);
			if(ch <= 50) {
				Spell spell = normal[ran.nextInt(normal.length)];
				if(config.isSpellEnabled(spell.getClass().getSimpleName())) 
					w.dropItem(l, spell.getItem());
			} else if(ch >= 51 && ch <= 80) {
				Spell spell = uncommon[ran.nextInt(uncommon.length)];
				if(config.isSpellEnabled(spell.getClass().getSimpleName()))
					w.dropItem(l, spell.getItem());
			} else {
				Spell spell = epic[ran.nextInt(epic.length)];
				if(config.isSpellEnabled(spell.getClass().getSimpleName()))
					w.dropItem(l, spell.getItem());
			}
		}
		
		if(ran.nextInt(101) <= 20) {
			int chances = ran.nextInt(101);
			if(chances <= 15) {
				w.dropItem(l, new XpCrystal(CrystalSize.Small, RCUtils.XPCRYSTAL_SMALL_NAME).getItem().getItem());
			} else if(chances >= 15 && chances <= 30){
				w.dropItem(l, new XpCrystal(CrystalSize.Medium, RCUtils.XPCRYSTAL_MEDIUM_NAME).getItem().getItem());
			} else if(chances >= 31 && chances <= 45) {
				w.dropItem(l, new XpCrystal(CrystalSize.Large, RCUtils.XPCRYSTAL_LARGE_NAME).getItem().getItem());
			} else if(chances >= 46 && chances <= 60) {
				w.dropItem(l, new ManaCrystal(CrystalSize.Small, RCUtils.MANACRYSTAL_SMALL_NAME).getItem().getItem());
			} else if(chances >= 61 && chances <= 75) {
				w.dropItem(l, new ManaCrystal(CrystalSize.Medium, RCUtils.MANACRYSTAL_MEDIUM_NAME).getItem().getItem());
			} else {
				w.dropItem(l, new ManaCrystal(CrystalSize.Large, RCUtils.MANACRYSTAL_LARGE_NAME).getItem().getItem());
			}
		}
	}

}
