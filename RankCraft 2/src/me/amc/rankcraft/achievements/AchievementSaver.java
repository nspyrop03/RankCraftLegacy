package me.amc.rankcraft.achievements;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public class AchievementSaver {

	public static void save(JavaPlugin plugin, Achievement a) {
		try {

			YamlConfiguration stats = new YamlConfiguration();

			switch (a.getType()) {
			case KillMobs:
				stats.createSection("MobsKilled", a.getKillMobsAmtHM());
				break;
			case KillPlayers:
				stats.createSection("PlayersKilled", a.getKillPlayersAmtHM());
				break;
			case BreakBlocks:
				stats.createSection("BlocksBroken", a.getBlocksBrokenHM());
				break;
			case PlaceBlocks:
				stats.createSection("BlocksPlaced", a.getBlocksPlacedHM());
				break;
			case Special:
				stats.createSection("Points", a.getSpecialPointsHM());
				break;
			default:
				break;
			}

			stats.createSection("IsCompleted", a.getCompletedHM());
		

			stats.save(new File("plugins/RankCraft/achievements/" + a.getName() + ".yml"));

		} catch (Exception ex) {
			plugin.getLogger().log(Level.SEVERE, "Save Failed !", ex);
		}
	}

	public static void load(JavaPlugin plugin, Achievement a) {
		try {

			File stats = new File("plugins/RankCraft/achievements/" + a.getName() + ".yml");
			if (stats.exists()) {

				YamlConfiguration config = new YamlConfiguration();
				config.load(stats);

				switch (a.getType()) {

				case KillMobs:

					ConfigurationSection mk = config.getConfigurationSection("MobsKilled");

					for (String key : mk.getKeys(false)) {
						a.getKillMobsAmtHM().put(key, mk.getInt(key));
					}

					break;
				case KillPlayers:

					ConfigurationSection pk = config.getConfigurationSection("PlayersKilled");

					for (String key : pk.getKeys(false)) {
						a.getKillPlayersAmtHM().put(key, pk.getInt(key));
					}

					break;

				case BreakBlocks:
					
					ConfigurationSection bb = config.getConfigurationSection("BlocksBroken");

					for (String key : bb.getKeys(false)) {
						a.getBlocksBrokenHM().put(key, bb.getInt(key));
					}
					
					break;
					
				case PlaceBlocks:
					
					ConfigurationSection bp = config.getConfigurationSection("BlocksPlaced");

					for (String key : bp.getKeys(false)) {
						a.getBlocksPlacedHM().put(key, bp.getInt(key));
					}
					
					break;
					
				case Special:
					
					ConfigurationSection s = config.getConfigurationSection("Points");
					for(String key : s.getKeys(false)) {
						a.getSpecialPointsHM().put(key, s.getInt(key));
					}
					
					break;
				
				default:
					break;
				}

				ConfigurationSection ct = config.getConfigurationSection("IsCompleted");

				for (String key : ct.getKeys(false)) {
					a.getCompletedHM().put(key, ct.getBoolean(key));
				}

			}

		} catch (Exception ex) {
			plugin.getLogger().log(Level.SEVERE, "Load Failed!", ex);
		}
	}
	
}
