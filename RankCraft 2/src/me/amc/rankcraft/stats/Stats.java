package me.amc.rankcraft.stats;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.customevents.LevelUpEvent;
import me.amc.rankcraft.utils.RCUtils;
import me.amc.rankcraft.yaml.MobsYaml;

public class Stats {

	public enum CountBlocks {
		Placed, Broken
	}

	public enum CountMobs {
		Zombie, Skeleton, Creeper, Enderman, Cow, Pig, Horse, Rabbit, Villager, Spider, Slime, Ghast, Sheep, Chicken
	}

	/*
	 * Included: - Xp - Level - Rank - BlocksPlaced - BlocksBroken - Mana - Max
	 * Mana - Max Mana Level - Kills - Deaths
	 */

	// All HashMaps save the UUID of the player not the name

	private HashMap<String, Float> xp = new HashMap<>();
	private HashMap<String, Integer> level = new HashMap<>();
	private HashMap<String, String> rank = new HashMap<>();

	private HashMap<String, Integer> bp = new HashMap<>(); // BlocksPlaced
	private HashMap<String, Integer> bb = new HashMap<>(); // BlocksBroken

	private HashMap<String, Float> mana = new HashMap<>();
	private HashMap<String, Float> maxMana = new HashMap<>();
	private HashMap<String, Integer> maxManaLevel = new HashMap<>();

	private HashMap<String, Integer> kills = new HashMap<>();
	private HashMap<String, Integer> deaths = new HashMap<>();

	private RankSystem rankSystem;

	public Stats() {
		rankSystem = new RankSystem();
	}
	
	public void setXp(Player p, float numXp) {
		xp.put(RCUtils.textedUUID(p), numXp);
		save();
		MainCore.instance.scoreboard.updateBoard(p);
		
	}

	public void setLevel(Player p, int numLevel) {
		level.put(RCUtils.textedUUID(p), numLevel);
		save();
		MainCore.instance.rankCraft.updateLeaderboards();
	}

	public void setRank(Player p) {
		rank.put(RCUtils.textedUUID(p), rankSystem.getRankBasedOnLevel(getLevel(p)));
		save();
	}

	public float getXp(Player p) {
		return xp.get(RCUtils.textedUUID(p));
	}

	public int getLevel(Player p) {
		return level.get(RCUtils.textedUUID(p));
	}

	public String getRank(Player p) {
		return rank.get(RCUtils.textedUUID(p));
	}

	public void setBlocksPlaced(Player p, int bsp) {
		bp.put(RCUtils.textedUUID(p), bsp);
		save();
		MainCore.instance.rankCraft.updateLeaderboards();
	}

	public void setBlocksBroken(Player p, int bsb) {
		bb.put(RCUtils.textedUUID(p), bsb);
		save();
		MainCore.instance.rankCraft.updateLeaderboards();
	}

	public int getBlocksBroken(Player p) {
		return bb.get(RCUtils.textedUUID(p));
	}

	public int getBlocksPlaced(Player p) {
		return bp.get(RCUtils.textedUUID(p));
	}

	public void setMana(Player p, float m) {
		if (m <= getMaxMana(p)) {
			mana.put(RCUtils.textedUUID(p), m);
		} else {
			mana.put(RCUtils.textedUUID(p), getMaxMana(p));
		}
		save();
	}

	public void setMaxManaLevel(Player p, int level) {
		maxManaLevel.put(RCUtils.textedUUID(p), level);
		setMaxMana(p);
		setMana(p, getMaxMana(p));
		save();
	}

	public void setMaxMana(Player p) {
		maxMana.put(RCUtils.textedUUID(p), (float) (getMaxManaLevel(p) * 10));
		save();
	}

	public float getMana(Player p) {
		return mana.get(RCUtils.textedUUID(p));
	}

	public int getMaxManaLevel(Player p) {
		return maxManaLevel.get(RCUtils.textedUUID(p));
	}

	public float getMaxMana(Player p) {
		return maxMana.get(RCUtils.textedUUID(p));
	}

	public boolean canAddMana(Player p) {
		return getMana(p) < getMaxMana(p);
	}
	
	public void addMana(Player p, float m) {
		setMana(p, getMana(p) + m);
	}

	public void removeMana(Player p, float m) {
		setMana(p, getMana(p) - m);
	}

	public void addBlockXp(CountBlocks block, Player p) {
		switch (block) {
		case Broken:
			addXp(p, MainCore.instance.config.xpOnBreak);
			break;
		case Placed:
			addXp(p, MainCore.instance.config.xpOnPlace);
			break;
		default:
			break;

		}
	}

	public void checkLevelAndRank(Player p) {
		int old_level = getLevel(p);

		//setLevel(p, (int)(  getXp(p) / ( 100 + (getLevel(p)+1) * 5 ) ));
		if(getXp(p) >= getXPToLevelUP(p)) {
			setLevel(p, getLevel(p)+1);
		}
		
		int current_level = getLevel(p);
		setRank(p);

		if (old_level != current_level && old_level < current_level) {
			LevelUpEvent event = new LevelUpEvent(p, old_level, current_level);
			Bukkit.getServer().getPluginManager().callEvent(event);
		}

	}

	public void addXp(Player p, float amount) {
		setXp(p, getXp(p) + amount);
		checkLevelAndRank(p);
	}

	public void countBlock(CountBlocks block, Player p) {
		switch (block) {
		case Broken:
			setBlocksBroken(p, getBlocksBroken(p) + 1);
			break;
		case Placed:
			setBlocksPlaced(p, getBlocksPlaced(p) + 1);
			break;
		default:
			break;

		}
	}

	public void addMobXp(CountMobs mob, Player p) {
		MobsYaml y = MainCore.instance.rankCraft.mobsYaml;
		switch (mob) {
		case Cow:
			addXp(p, y.getXP(mob.toString()));
			break;
		case Creeper:
			addXp(p, y.getXP(mob.toString()));
			break;
		case Enderman:
			addXp(p, y.getXP(mob.toString()));
			break;
		case Ghast:
			addXp(p, y.getXP(mob.toString()));
			break;
		case Horse:
			addXp(p, y.getXP(mob.toString()));
			break;
		case Pig:
			addXp(p, y.getXP(mob.toString()));
			break;
		case Rabbit:
			addXp(p, y.getXP(mob.toString()));
			break;
		case Skeleton:
			addXp(p, y.getXP(mob.toString()));
			break;
		case Slime:
			addXp(p, y.getXP(mob.toString()));
			break;
		case Spider:
			addXp(p, y.getXP(mob.toString()));
			break;
		case Villager:
			addXp(p, y.getXP(mob.toString()));
			break;
		case Zombie:
			addXp(p, y.getXP(mob.toString()));
			break;
		case Chicken:
			addXp(p, y.getXP(mob.toString()));
			break;
		case Sheep:
			addXp(p, y.getXP(mob.toString()));
			break;
		default:
			break;

		}
	}

	public float getXPToLevelUP(Player p) {
		float xpToLevelUp = (getLevel(p) + 1) * (100.0f + (getLevel(p)+1)*5);
		return xpToLevelUp;

	}
	
	private float getXpToReachCurrentLevel(Player p) {
		return (getLevel(p) * (100 + getLevel(p)*5));
	}

	public void setKills(Player p, int k) {
		kills.put(RCUtils.textedUUID(p), k);
		save();
	}

	public int getKills(Player p) {
		return kills.get(RCUtils.textedUUID(p));
	}

	public void addKills(Player p, int k) {
		setKills(p, getKills(p) + k);
	}

	public void setDeaths(Player p, int d) {
		deaths.put(RCUtils.textedUUID(p), d);
		save();
	}

	public int getDeaths(Player p) {
		return deaths.get(RCUtils.textedUUID(p));
	}

	public void addDeaths(Player p, int d) {
		setDeaths(p, getDeaths(p) + d);
	}

	public int getKillDeathRatio(Player p) {
		int ratio = getKills(p) / getDeaths(p);
		return ratio;
	}

	// Init all the HashMaps for the specific player
	public void initForPlayer(Player p) {
		String uuid = RCUtils.textedUUID(p);
		if (!xp.containsKey(uuid)) {
			setXp(p, 0);
		}
		if (!level.containsKey(uuid)) {
			setLevel(p, 0);
		}
		if (!rank.containsKey(uuid)) {
			setRank(p);
		}
		if (!maxManaLevel.containsKey(uuid)) {
			setMaxManaLevel(p, 10);
		}
		if (!maxMana.containsKey(uuid)) {
			setMaxMana(p);
		}
		if (!mana.containsKey(uuid)) {
			setMana(p, getMaxMana(p));
		}
		if (!bp.containsKey(uuid)) {
			setBlocksPlaced(p, 0);
		}
		if (!bb.containsKey(uuid)) {
			setBlocksBroken(p, 0);
		}
		if (!kills.containsKey(uuid)) {
			setKills(p, 0);
		}
		if (!deaths.containsKey(uuid)) {
			setDeaths(p, 0);
		}
	}

	public void save() {

		try {

			YamlConfiguration stats = new YamlConfiguration();
			stats.createSection("xp", xp);
			stats.createSection("level", level);
			stats.createSection("rank", rank);
			stats.createSection("mana", mana);
			stats.createSection("maxMana", maxMana);
			stats.createSection("maxManaLevel", maxManaLevel);
			stats.createSection("bp", bp);
			stats.createSection("bb", bb);
			stats.createSection("kills", kills);
			stats.createSection("deaths", deaths);
			stats.save(new File(RCUtils.STATS_DIRECTORY, "stats.yml"));

		} catch (Exception ex) {
			MainCore.instance.getLogger().log(Level.SEVERE, "[StatsSaver] Save Failed!", ex);
		}

	}

	public void load() {
		try {

			File stats = new File(RCUtils.STATS_DIRECTORY, "stats.yml");
			if (stats.exists()) {

				YamlConfiguration config = new YamlConfiguration();
				config.load(stats);

				ConfigurationSection sec = config.getConfigurationSection("xp");
				for (String key : sec.getKeys(false)) {
					xp.put(key, (float) sec.getInt(key));
				}

				sec = config.getConfigurationSection("level");
				for (String key : sec.getKeys(false)) {
					level.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("rank");
				for (String key : sec.getKeys(false)) {
					rank.put(key, sec.getString(key));
				}

				sec = config.getConfigurationSection("mana");
				for (String key : sec.getKeys(false)) {
					mana.put(key, (float) sec.getInt(key));
				}

				sec = config.getConfigurationSection("maxMana");
				for (String key : sec.getKeys(false)) {
					maxMana.put(key, (float) sec.getInt(key));
				}

				sec = config.getConfigurationSection("maxManaLevel");
				for (String key : sec.getKeys(false)) {
					maxManaLevel.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("bp");
				for (String key : sec.getKeys(false)) {
					bp.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("bb");
				for (String key : sec.getKeys(false)) {
					bb.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("kills");
				for (String key : sec.getKeys(false)) {
					kills.put(key, sec.getInt(key));
				}

				sec = config.getConfigurationSection("deaths");
				for (String key : sec.getKeys(false)) {
					deaths.put(key, sec.getInt(key));
				}

			}

		} catch (Exception ex) {
			MainCore.instance.getLogger().log(Level.SEVERE, "[StatsLoader] Load Failed!", ex);
		}
	}

	public boolean areAllOk(Player p) {
		String uuid = RCUtils.textedUUID(p);
		if (xp.containsKey(uuid) && level.containsKey(uuid) && rank.containsKey(uuid) && bp.containsKey(uuid)
				&& bb.containsKey(uuid) && mana.containsKey(uuid) && maxMana.containsKey(uuid)
				&& maxManaLevel.containsKey(uuid)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getManaBar(Player p, int manaBars) {
		if(mana.containsKey(RCUtils.textedUUID(p))) {
		StringBuilder sb = new StringBuilder();

		String b = "|";
		String g = "|";
		int manaInt = (int)getMana(p);
		int maxManaInt = (int)getMaxMana(p);
		
		// Calculate blue bars
		int x = (manaInt * manaBars) / maxManaInt;

		// Append blue bars to string builder
		sb.append(ChatColor.BLUE.toString());
		for(int i = 0; i < x; i++) {
			sb.append(b);

		}

		// Apped gray bars to string builder
		sb.append(ChatColor.GRAY.toString());
		for(int i = 0; i < (manaBars-x); i++) {
			sb.append(g);

		}

		// Make the bar
		String manaBar = sb.toString();

		
		return manaBar;
		} else {
			return "nan";
		}
	}

	public String getXpBar(Player p, int xpBars) {
		if(xp.containsKey(RCUtils.textedUUID(p))) {
		StringBuilder sb = new StringBuilder();
		String x = "|";
		String g = "|";
		int xpInt = (int)(getXp(p)-getXpToReachCurrentLevel(p));
		int maxXpInt = (int)(getXPToLevelUP(p)-getXpToReachCurrentLevel(p));
		
		int y = (xpInt * xpBars) / maxXpInt;
		
		sb.append(ChatColor.GREEN.toString());
		for(int i = 0; i < y; i++) {
			sb.append(x);
		}
		
		sb.append(ChatColor.GRAY.toString());
		for(int i = 0; i < (xpBars-y); i++) {
			sb.append(g);
		}
		
		String xpBar = sb.toString();
		
		return xpBar;
		} else {
			return "nan";
		}
	}

	public String getHungerBar(Player p, int hBars) {
		StringBuilder sb = new StringBuilder();
		String x = "|";
		String g = "|";
		int xpInt = (int)p.getFoodLevel();
		int maxXpInt = 20;
		
		int y = (xpInt * hBars) / maxXpInt;
		
		sb.append(ChatColor.GOLD.toString());
		for(int i = 0; i < y; i++) {
			sb.append(x);
		}
		
		sb.append(ChatColor.GRAY.toString());
		for(int i = 0; i < (hBars-y); i++) {
			sb.append(g);
		}
		
		String xpBar = sb.toString();
		
		return xpBar;
	}
	
	
	// Getters and setters for HashMaps made for Leaderboard uses!
	
	public HashMap<String, Integer> getLevelHM() {
		return level;
	}

	public void setLevelHM(HashMap<String, Integer> level) {
		this.level = level;
	}

	public HashMap<String, Integer> getBpHM() {
		return bp;
	}

	public void setBpHM(HashMap<String, Integer> bp) {
		this.bp = bp;
	}

	public HashMap<String, Integer> getBbHM() {
		return bb;
	}

	public void setBbHM(HashMap<String, Integer> bb) {
		this.bb = bb;
	}
	
	
}
