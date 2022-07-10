package me.amc.rankcraft.quests2;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class QuestSaver {

	public static void save(JavaPlugin plugin, Quest q) {
		try {

			YamlConfiguration stats = new YamlConfiguration();

			switch (q.getType()) {
			case BreakBlocks:
				stats.createSection("BlocksBroken", q.hmBlocksBroken());
				break;
			case KillMobs:
				stats.createSection("MobsKilled", q.hmMobsKilled());
				break;
			case KillPlayers:
				stats.createSection("PlayersKilled", q.hmPlayersKilled());
				break;
			case PlaceBlocks:
				stats.createSection("BlocksPlaced", q.hmBlocksPlaced());
				break;
			default:
				break;
			}

			stats.createSection("Taken", q.hmTaken());
			stats.createSection("CompletedTimes", q.hmCompletedTimes());
			stats.createSection("SecondsRemaining", q.hmSecondsRemaining());
			stats.createSection("SecondsToReTake", q.hmSecondsToReTake());

			stats.save(new File("plugins/RankCraft/quests_database/" + q.getId() + ".yml"));

		} catch (Exception ex) {
			plugin.getLogger().log(Level.SEVERE, "Save Failed !", ex);
		}
	}

	public static void load(JavaPlugin plugin, Quest q) {
		try {

			File stats = new File("plugins/RankCraft/quests_database/" + q.getId() + ".yml");
			if (stats.exists()) {

				YamlConfiguration config = new YamlConfiguration();
				config.load(stats);

				switch (q.getType()) {
				case BreakBlocks:

					ConfigurationSection bb = config.getConfigurationSection("BlocksBroken");

					for (String key : bb.getKeys(false)) {
						q.hmBlocksBroken().put(key, bb.getInt(key));
					}

					break;
				case KillMobs:

					ConfigurationSection mk = config.getConfigurationSection("MobsKilled");

					for (String key : mk.getKeys(false)) {
						q.hmMobsKilled().put(key, mk.getInt(key));
					}

					break;
				case KillPlayers:

					ConfigurationSection pk = config.getConfigurationSection("PlayersKilled");

					for (String key : pk.getKeys(false)) {
						q.hmPlayersKilled().put(key, pk.getInt(key));
					}

					break;
				case PlaceBlocks:

					ConfigurationSection bp = config.getConfigurationSection("BlocksPlaced");

					for (String key : bp.getKeys(false)) {
						q.hmBlocksPlaced().put(key, bp.getInt(key));
					}

					break;
				default:
					break;
				}

				ConfigurationSection t = config.getConfigurationSection("Taken");

				for (String key : t.getKeys(false)) {
					q.hmTaken().put(key, t.getBoolean(key));
				}

				ConfigurationSection ct = config.getConfigurationSection("CompletedTimes");

				for (String key : ct.getKeys(false)) {
					q.hmCompletedTimes().put(key, ct.getInt(key));
				}

				ConfigurationSection sr = config.getConfigurationSection("SecondsRemaining");

				for (String key : sr.getKeys(false)) {
					q.hmSecondsRemaining().put(key, sr.getInt(key));
				}

				ConfigurationSection strt = config.getConfigurationSection("SecondsToReTake");

				for (String key : strt.getKeys(false)) {
					q.hmSecondsToReTake().put(key, strt.getInt(key));
				}

			}

		} catch (Exception ex) {
			plugin.getLogger().log(Level.SEVERE, "Load Failed!", ex);
		}
	}
	
	public static void resetQuest(Quest q, Player p) {
		switch(q.getType()) {
		case BreakBlocks:
			q.hmBlocksBroken().put(RCUtils.textedUUID(p), 0);
			break;
		case KillMobs:
			q.hmMobsKilled().put(RCUtils.textedUUID(p), 0);
			break;
		case KillPlayers:
			q.hmPlayersKilled().put(RCUtils.textedUUID(p), 0);
			break;
		case PlaceBlocks:
			q.hmBlocksPlaced().put(RCUtils.textedUUID(p), 0);
			break;
		default:
			break;
		
		}
		
		q.hmTaken().put(RCUtils.textedUUID(p), false);
		q.hmSecondsRemaining().put(RCUtils.textedUUID(p), q.getSecondsToComplete());
		q.hmSecondsToReTake().put(RCUtils.textedUUID(p), q.getSecondsToReTake());
		
		QuestSaver.save(MainCore.instance, q);
	}

}
