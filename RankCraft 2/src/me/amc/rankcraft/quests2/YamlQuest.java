package me.amc.rankcraft.quests2;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.RCUtils;
import me.amc.rankcraft.utils.Reward;

public class YamlQuest extends Quest {

	private FileConfiguration config;

	private HashMap<String, Integer> mk = new HashMap<>();
	private HashMap<String, Integer> pk = new HashMap<>();
	private HashMap<String, Integer> bb = new HashMap<>();
	private HashMap<String, Integer> bp = new HashMap<>();
	private HashMap<String, Integer> sr = new HashMap<>();
	private HashMap<String, Integer> strt = new HashMap<>();
	private HashMap<String, Integer> ct = new HashMap<>();
	private HashMap<String, Boolean> t = new HashMap<>();

	private File f;

	public YamlQuest(File f) {
		this.f = f;
		String fileName = f.getName();
		if (fileName.endsWith(".quest")) {
			config = new YamlConfiguration();

			try {

				config.load(RCUtils.QUESTS_DIRECTORY + "/" + fileName);

			} catch (InvalidConfigurationException | IOException e) {
				e.printStackTrace();
			}
			makeQuest();

			if (MainCore.instance.config.startupDebug) {
				System.out.println("Successfully made quest " + this.getName() + " from file " + fileName);
			}
		}

	}

	private void makeQuest() {
		QuestType type = QuestType.valueOf(config.getString("Type"));
		String name = config.getString("Name");
		int secondsToReTake = config.getInt("SecondsToReTake");
		int secondsToComplete = config.getInt("SecondsToComplete");

		Material placeBlocks = null;
		Material breakBlocks = null;
		EntityType killMobs = null;
		int pbAmt = -1;
		int bbAmt = -1;
		int kmAmt = -1;
		int kpAmt = -1;

		this.setNameAndID(name);
		this.setType(type);
		this.setSecondsToComplete(secondsToComplete);
		this.setSecondsToReTake(secondsToReTake);

		switch (type) {
		case BreakBlocks:
			breakBlocks = Material.valueOf(config.getString("BreakBlockType"));
			bbAmt = config.getInt("BreakBlocksAmount");
			this.setBreakBlocksType(breakBlocks);
			this.setBreakBlockAmount(bbAmt);
			break;
		case KillMobs:
			killMobs = EntityType.valueOf(config.getString("KillMobType"));
			kmAmt = config.getInt("KillMobsAmount");
			this.setKillEntityAmount(kmAmt);
			this.setKillEntityType(killMobs);
			break;
		case KillPlayers:
			kpAmt = config.getInt("KillPlayersAmount");
			this.setKillPlayerAmount(kpAmt);
			break;
		case PlaceBlocks:
			placeBlocks = Material.valueOf(config.getString("PlaceBlockType"));
			pbAmt = config.getInt("PlaceBlocksAmount");
			this.setPlaceBlockAmount(pbAmt);
			this.setPlaceBlocksType(placeBlocks);
			break;
		default:
			System.out.println("ERROR: Cannot make YamlQuest " + this.getName());
			break;

		}

		if (config.contains("Reward")) {
			Reward r;
			float xp = (float) config.getDouble("Reward.xp");
			float gold = (float) config.getDouble("Reward.gold");
			if (config.getItemStack("Reward.item") != null) {
				ItemStack item = config.getItemStack("Reward.item");
				r = new Reward(xp, gold, new CustomItem(item).build());
			} else {
				r = new Reward(xp, gold);
			}
			this.setReward(r);
		}
	}

	@Override
	public HashMap<String, Integer> hmBlocksPlaced() {
		if (this.getType() == QuestType.PlaceBlocks)
			return this.bp;
		else
			return null;
	}

	@Override
	public HashMap<String, Integer> hmBlocksBroken() {
		if (this.getType() == QuestType.BreakBlocks)
			return this.bb;
		else
			return null;
	}

	@Override
	public HashMap<String, Integer> hmMobsKilled() {
		if (this.getType() == QuestType.KillMobs)
			return this.mk;
		else
			return null;
	}

	@Override
	public HashMap<String, Integer> hmPlayersKilled() {
		if (this.getType() == QuestType.KillPlayers)
			return this.pk;
		else
			return null;
	}

	@Override
	public HashMap<String, Integer> hmSecondsRemaining() {
		return this.sr;
	}

	@Override
	public HashMap<String, Integer> hmSecondsToReTake() {
		return this.strt;
	}

	@Override
	public HashMap<String, Integer> hmCompletedTimes() {
		return this.ct;
	}

	@Override
	public HashMap<String, Boolean> hmTaken() {
		return this.t;
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public File getFile() {
		return f;
	}
}
