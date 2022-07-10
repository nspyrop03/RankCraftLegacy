package me.amc.rankcraft.stats;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.stats.Stats.CountMobs;
import me.amc.rankcraft.utils.RCUtils;

public class MobStats {

	private HashMap<String, Integer> zombies = new HashMap<>();
	private HashMap<String, Integer> skeletons = new HashMap<>();
	private HashMap<String, Integer> creepers = new HashMap<>();
	private HashMap<String, Integer> endermans = new HashMap<>();
	private HashMap<String, Integer> cows = new HashMap<>();
	private HashMap<String, Integer> pigs = new HashMap<>();
	private HashMap<String, Integer> horses = new HashMap<>();
	private HashMap<String, Integer> rabbits = new HashMap<>();
	private HashMap<String, Integer> villagers = new HashMap<>();
	private HashMap<String, Integer> spiders = new HashMap<>();
	private HashMap<String, Integer> slimes = new HashMap<>();
	private HashMap<String, Integer> ghasts = new HashMap<>();
	private HashMap<String, Integer> sheeps = new HashMap<>();
	private HashMap<String, Integer> chickens = new HashMap<>();

	public void setZombies(Player p, int num) {
		zombies.put(RCUtils.textedUUID(p), num);
		save();
	}

	public void setSkeletons(Player p, int num) {
		skeletons.put(RCUtils.textedUUID(p), num);
		save();
	}

	public void setCreepers(Player p, int num) {
		creepers.put(RCUtils.textedUUID(p), num);
		save();
	}

	public void setEndermans(Player p, int num) {
		endermans.put(RCUtils.textedUUID(p), num);
		save();
	}

	public void setCows(Player p, int num) {
		cows.put(RCUtils.textedUUID(p), num);
		save();
	}

	public void setPigs(Player p, int num) {
		pigs.put(RCUtils.textedUUID(p), num);
		save();
	}

	public void setHorses(Player p, int num) {
		horses.put(RCUtils.textedUUID(p), num);
		save();
	}

	public void setRabbits(Player p, int num) {
		rabbits.put(RCUtils.textedUUID(p), num);
		save();
	}

	public void setVillagers(Player p, int num) {
		villagers.put(RCUtils.textedUUID(p), num);
		save();
	}

	public void setSpiders(Player p, int num) {
		spiders.put(RCUtils.textedUUID(p), num);
		save();
	}

	public void setSlimes(Player p, int num) {
		slimes.put(RCUtils.textedUUID(p), num);
		save();
	}

	public void setGhasts(Player p, int num) {
		ghasts.put(RCUtils.textedUUID(p), num);
		save();
	}

	public void setSheeps(Player p, int num) {
		sheeps.put(RCUtils.textedUUID(p), num);
		save();
	}

	public void setChickens(Player p, int num) {
		chickens.put(RCUtils.textedUUID(p), num);
		save();
	}

	public Integer getZombies(Player p) {
		return zombies.get(RCUtils.textedUUID(p));
	}

	public Integer getSkeletons(Player p) {
		return skeletons.get(RCUtils.textedUUID(p));
	}

	public Integer getCreepers(Player p) {
		return creepers.get(RCUtils.textedUUID(p));
	}

	public Integer getEndermans(Player p) {
		return endermans.get(RCUtils.textedUUID(p));
	}

	public Integer getCows(Player p) {
		return cows.get(RCUtils.textedUUID(p));
	}

	public Integer getPigs(Player p) {
		return pigs.get(RCUtils.textedUUID(p));
	}

	public Integer getHorses(Player p) {
		return horses.get(RCUtils.textedUUID(p));
	}

	public Integer getRabbits(Player p) {
		return rabbits.get(RCUtils.textedUUID(p));
	}

	public Integer getVillagers(Player p) {
		return villagers.get(RCUtils.textedUUID(p));
	}

	public Integer getSpiders(Player p) {
		return spiders.get(RCUtils.textedUUID(p));
	}

	public Integer getSlimes(Player p) {
		return slimes.get(RCUtils.textedUUID(p));
	}

	public Integer getGhasts(Player p) {
		return ghasts.get(RCUtils.textedUUID(p));
	}

	public Integer getSheeps(Player p) {
		return sheeps.get(RCUtils.textedUUID(p));
	}

	public Integer getChickens(Player p) {
		return chickens.get(RCUtils.textedUUID(p));
	}

	public void save() {
		try {

			YamlConfiguration stats = new YamlConfiguration();
			stats.createSection("zombies", zombies);
			stats.createSection("skeletons", skeletons);
			stats.createSection("creepers", creepers);
			stats.createSection("endermans", endermans);
			stats.createSection("cows", cows);
			stats.createSection("pigs", pigs);
			stats.createSection("horses", horses);
			stats.createSection("rabbits", rabbits);
			stats.createSection("villagers", villagers);
			stats.createSection("spiders", spiders);
			stats.createSection("slimes", slimes);
			stats.createSection("ghasts", ghasts);
			stats.createSection("sheeps", sheeps);
			stats.createSection("chickens", chickens);
			stats.save(new File(RCUtils.STATS_DIRECTORY, "mobStats.yml"));

		} catch (Exception ex) {
			MainCore.instance.getLogger().log(Level.SEVERE, "[MobStatsSaver] Save Failed!", ex);
		}
	}

	public void load() {
		try {

			File stats = new File(RCUtils.STATS_DIRECTORY, "mobStats.yml");
			if (stats.exists()) {

				YamlConfiguration config = new YamlConfiguration();
				config.load(stats);

				ConfigurationSection sec = config.getConfigurationSection("zombies");
				for (String key : sec.getKeys(false)) {
					zombies.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("skeletons");
				for (String key : sec.getKeys(false)) {
					skeletons.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("creepers");
				for (String key : sec.getKeys(false)) {
					creepers.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("endermans");
				for (String key : sec.getKeys(false)) {
					endermans.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("cows");
				for (String key : sec.getKeys(false)) {
					cows.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("pigs");
				for (String key : sec.getKeys(false)) {
					pigs.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("horses");
				for (String key : sec.getKeys(false)) {
					horses.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("rabbits");
				for (String key : sec.getKeys(false)) {
					rabbits.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("villagers");
				for (String key : sec.getKeys(false)) {
					villagers.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("spiders");
				for (String key : sec.getKeys(false)) {
					spiders.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("slimes");
				for (String key : sec.getKeys(false)) {
					slimes.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("ghasts");
				for (String key : sec.getKeys(false)) {
					ghasts.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("sheeps");
				for (String key : sec.getKeys(false)) {
					sheeps.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("chickens");
				for (String key : sec.getKeys(false)) {
					chickens.put(key, sec.getInt(key));
				}

			}

		} catch (Exception ex) {
			MainCore.instance.getLogger().log(Level.SEVERE, "[MobStatsLoader] Load Failed!", ex);
		}
	}

	public void addMobKills(Player p, CountMobs mob) {
		switch (mob) {
		case Zombie:
			setZombies(p, getZombies(p) + 1);
			break;
		case Skeleton:
			setSkeletons(p, getSkeletons(p) + 1);
			break;
		case Chicken:
			setChickens(p, getChickens(p) + 1);
			break;
		case Cow:
			setCows(p, getCows(p) + 1);
			break;
		case Creeper:
			setCreepers(p, getCreepers(p) + 1);
			break;
		case Enderman:
			setEndermans(p, getEndermans(p) + 1);
			break;
		case Ghast:
			setGhasts(p, getGhasts(p) + 1);
			break;
		case Horse:
			setHorses(p, getHorses(p) + 1);
			break;
		case Pig:
			setPigs(p, getPigs(p) + 1);
			break;
		case Rabbit:
			setRabbits(p, getRabbits(p) + 1);
			break;
		case Sheep:
			setSheeps(p, getSheeps(p) + 1);
			break;
		case Slime:
			setSlimes(p, getSlimes(p) + 1);
			break;
		case Spider:
			setSpiders(p, getSpiders(p) + 1);
			break;
		case Villager:
			setVillagers(p, getVillagers(p) + 1);
			break;
		default:
			break;

		}
		MainCore.instance.scoreboard.updateBoard(p);
	}

	public void initForPlayer(Player p) {
		String uuid = RCUtils.textedUUID(p);
		
		if(!zombies.containsKey(uuid)) {
			setZombies(p, 0);
		}
		
		if(!skeletons.containsKey(uuid)) {
			setSkeletons(p, 0);
		}
		
		if(!creepers.containsKey(uuid)) {
			setCreepers(p, 0);
		}
		
		if(!endermans.containsKey(uuid)) {
			setEndermans(p, 0);
		}
		
		if(!cows.containsKey(uuid)) {
			setCows(p, 0);
		}
		
		if(!pigs.containsKey(uuid)) {
			setPigs(p, 0);
		}
		
		if(!horses.containsKey(uuid)) {
			setHorses(p, 0);
		}
		
		if(!rabbits.containsKey(uuid)) {
			setRabbits(p, 0);
		}
		
		if(!villagers.containsKey(uuid)) {
			setVillagers(p, 0);
		}
		
		if(!spiders.containsKey(uuid)) {
			setSpiders(p, 0);
		}
		
		if(!slimes.containsKey(uuid)) {
			setSlimes(p, 0);
		}
		
		if(!ghasts.containsKey(uuid)) {
			setGhasts(p, 0);
		}
		
		if(!sheeps.containsKey(uuid)) {
			setSheeps(p, 0);
		}
		
		if(!chickens.containsKey(uuid)) {
			setChickens(p, 0);
		}
	}
}
