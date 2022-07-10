package me.amc.rankcraft.quests2;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.amc.rankcraft.utils.RCUtils;
import me.amc.rankcraft.utils.Reward;

public abstract class Quest implements QuestMaps {

	enum QuestType {
		PlaceBlocks, BreakBlocks, KillMobs, KillPlayers
	}

	private int secondsToReTake;
	private int secondsToComplete;
	private int minLevelToTake; // For next time :D
	private QuestType type;
	private String name;
	private String id;
	private boolean taken;

	private Material breakBlocksType;
	private Material placeBlocksType;
	private EntityType killEntityType;
	private int breakBlockAmount;
	private int placeBlockAmount;
	private int killEntityAmount;
	private int killPlayerAmount;
	
	private Reward reward;

	public Quest(QuestType type, String name) {
		this.type = type;
		this.name = name;
		this.id = this.name.toLowerCase();
		//System.out.println(this.hmTaken());
		//System.out.println(this.id);
	}
	
	public Quest() {
		//System.out.println("empty quest");
	}
	
	public void setNameAndID(String name) {
		this.name = name;
		this.id = this.name.toLowerCase();
	}

	public int getMinLevelToTake() {
		return minLevelToTake;
	}

	public void setMinLevelToTake(int minLevelToTake) {
		this.minLevelToTake = minLevelToTake;
	}

	public QuestType getType() {
		return type;
	}

	public void setType(QuestType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isTaken() {
		return taken;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}

	public int getSecondsToReTake() {
		return secondsToReTake;
	}

	public void setSecondsToReTake(int secondsToReTake) {
		this.secondsToReTake = secondsToReTake;
	}

	public int getSecondsToComplete() {
		return secondsToComplete;
	}

	public void setSecondsToComplete(int secondsToComplete) {
		this.secondsToComplete = secondsToComplete;
	}

	public Material getBreakBlocksType() {
		return breakBlocksType;
	}

	public void setBreakBlocksType(Material breakBlocksType) {
		if (type == QuestType.BreakBlocks)
			this.breakBlocksType = breakBlocksType;
	}

	public Material getPlaceBlocksType() {
		return placeBlocksType;
	}

	public void setPlaceBlocksType(Material placeBlocksType) {
		if (type == QuestType.PlaceBlocks)
			this.placeBlocksType = placeBlocksType;
	}

	public EntityType getKillEntityType() {
		return killEntityType;
	}

	public void setKillEntityType(EntityType killEntityType) {
		if (type == QuestType.KillMobs)
			this.killEntityType = killEntityType;
	}

	public int getBreakBlockAmount() {
		return breakBlockAmount;
	}

	public void setBreakBlockAmount(int breakBlockAmount) {
		if (type == QuestType.BreakBlocks)
			this.breakBlockAmount = breakBlockAmount;
	}

	public int getPlaceBlockAmount() {
		return placeBlockAmount;
	}

	public void setPlaceBlockAmount(int placeBlockAmount) {
		if (type == QuestType.PlaceBlocks)
			this.placeBlockAmount = placeBlockAmount;
	}

	public int getKillEntityAmount() {
		return killEntityAmount;
	}

	public void setKillEntityAmount(int killEntityAmount) {
		if (type == QuestType.KillMobs)
			this.killEntityAmount = killEntityAmount;
	}

	public int getKillPlayerAmount() {
		return killPlayerAmount;
	}

	public void setKillPlayerAmount(int killPlayerAmount) {
		if (type == QuestType.KillPlayers)
			this.killPlayerAmount = killPlayerAmount;
	}

	public void initForPlayer(Player p, JavaPlugin plugin) {

		QuestSaver.load(plugin, this);

		if (!this.hmCompletedTimes().containsKey(RCUtils.textedUUID(p)))
			this.hmCompletedTimes().put(RCUtils.textedUUID(p), 0);

		if (!this.hmSecondsRemaining().containsKey(RCUtils.textedUUID(p)))
			this.hmSecondsRemaining().put(RCUtils.textedUUID(p), this.secondsToComplete);

		if (!this.hmSecondsToReTake().containsKey(RCUtils.textedUUID(p)))
			this.hmSecondsToReTake().put(RCUtils.textedUUID(p), this.secondsToReTake);

		if (!this.hmTaken().containsKey(RCUtils.textedUUID(p)))
			this.hmTaken().put(RCUtils.textedUUID(p), false);

		switch (this.type) {
		case BreakBlocks:
			if (!this.hmBlocksBroken().containsKey(RCUtils.textedUUID(p)))
				this.hmBlocksBroken().put(RCUtils.textedUUID(p), 0);
			break;
		case KillMobs:
			if (!this.hmMobsKilled().containsKey(RCUtils.textedUUID(p)))
				this.hmMobsKilled().put(RCUtils.textedUUID(p), 0);
			break;
		case KillPlayers:
			if (!this.hmPlayersKilled().containsKey(RCUtils.textedUUID(p)))
				this.hmPlayersKilled().put(RCUtils.textedUUID(p), 0);
			break;
		case PlaceBlocks:
			if (!this.hmBlocksPlaced().containsKey(RCUtils.textedUUID(p)))
				this.hmBlocksPlaced().put(RCUtils.textedUUID(p), 0);
			break;
		default:
			break;

		}

		QuestSaver.save(plugin, this);

		//System.out.println(this.hmTaken());
		//System.out.println(this.id);
	}

	public Reward getReward() {
		return reward;
	}

	public void setReward(Reward reward) {
		this.reward = reward;
	}
	
	

}
