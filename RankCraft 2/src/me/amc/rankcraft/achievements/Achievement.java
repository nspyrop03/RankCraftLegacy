package me.amc.rankcraft.achievements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.lang.GuiLanguage;
import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.RCUtils;
import me.amc.rankcraft.utils.Reward;

public abstract class Achievement implements AchievementMaps{

	public enum Type {
		BreakBlocks,
		PlaceBlocks,
		KillMobs,
		KillPlayers,
		Special
	}
	
	private String name;
	private Reward reward;
	private CustomItem guiItem;
	private Type type;
	
	// If type is KillMobs
	private int killMobsAmt;
	private EntityType killMobsType;
	
	// If type is KillPlayers
	private int killPlayersAmt;
	
	// If type is BreakBlocks
	private int breakBlocksAmt;
	private Material breakBlocksType;
	
	// If type is PlaceBlocks
	private int placeBlocksAmt;
	private Material placeBlocksType;
	
	// If type is Special
	private String specialMission;
	private int specialPoints;
	
	public Achievement(String name, Type type) {
		this.name = name;
		this.type = type;
	}
	
	public Achievement(String name) {
		this.name = name;
	}
	
	public Achievement(Type type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Reward getReward() {
		return reward;
	}

	public void setReward(Reward reward) {
		this.reward = reward;
	}

	public CustomItem getGuiItem() {
		return guiItem;
	}

	public void setGuiItem(CustomItem guiItem) {
		this.guiItem = guiItem;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getKillMobsAmt() {
		return killMobsAmt;
	}

	public void setKillMobsAmt(int killMobsAmt) {
		this.killMobsAmt = killMobsAmt;
	}

	public EntityType getKillMobsType() {
		return killMobsType;
	}

	public void setKillMobsType(EntityType killMobsType) {
		this.killMobsType = killMobsType;
	}

	public int getKillPlayersAmt() {
		return killPlayersAmt;
	}

	public void setKillPlayersAmt(int killPlayersAmt) {
		this.killPlayersAmt = killPlayersAmt;
	}
	
	public int getBreakBlocksAmt() {
		return breakBlocksAmt;
	}

	public void setBreakBlocksAmt(int breakBlocksAmt) {
		this.breakBlocksAmt = breakBlocksAmt;
	}

	public Material getBreakBlocksType() {
		return breakBlocksType;
	}

	public void setBreakBlocksType(Material breakBlocksType) {
		this.breakBlocksType = breakBlocksType;
	}

	public int getPlaceBlocksAmt() {
		return placeBlocksAmt;
	}

	public void setPlaceBlocksAmt(int placeBlocksAmt) {
		this.placeBlocksAmt = placeBlocksAmt;
	}

	public Material getPlaceBlocksType() {
		return placeBlocksType;
	}

	public void setPlaceBlocksType(Material placeBlocksType) {
		this.placeBlocksType = placeBlocksType;
	}

	public void completeFor(Player p) {
		if(this.getCompletedHM().get(RCUtils.textedUUID(p)) == false) {
			this.getCompletedHM().put(RCUtils.textedUUID(p), true);
			RCUtils.giveReward(reward, p);
		//	Bukkit.broadcastMessage(p.getName()+" has just completed the achievement "+name+" !");
			Bukkit.broadcastMessage(MainCore.instance.language.getCompleteAchievement(p, name));
			AchievementSaver.save(MainCore.instance, this);
		}
	}
	
	public void addMobKills(Player p, int kills) {
		if(this.getCompletedHM().get(RCUtils.textedUUID(p)) == false) {
			this.getKillMobsAmtHM().put(RCUtils.textedUUID(p), this.getKillMobsAmtHM().get(RCUtils.textedUUID(p)) + kills);
			AchievementSaver.save(MainCore.instance, this);
		}
	}
	
	public void addPlayerKills(Player p, int kills) {
		if(this.getCompletedHM().get(RCUtils.textedUUID(p)) == false) {
			this.getKillPlayersAmtHM().put(RCUtils.textedUUID(p), this.getKillPlayersAmtHM().get(RCUtils.textedUUID(p)) + kills);
			AchievementSaver.save(MainCore.instance, this);
		}
	}
	
	public int getMobsKilledBy(Player p) {
		return this.getKillMobsAmtHM().get(RCUtils.textedUUID(p));
	}
	
	public int getPlayersKilledBy(Player p) {
		return this.getKillPlayersAmtHM().get(RCUtils.textedUUID(p));
	}
	
	public void addPlaceBlock(Player p, int blocks) {
		if(this.getCompletedHM().get(RCUtils.textedUUID(p)) == false) {
			this.getBlocksPlacedHM().put(RCUtils.textedUUID(p), this.getBlocksPlacedHM().get(RCUtils.textedUUID(p)) + blocks);
			AchievementSaver.save(MainCore.instance, this);
		}
	}
	
	public void addBreakBlock(Player p, int blocks) {
		if(this.getCompletedHM().get(RCUtils.textedUUID(p)) == false) {
			this.getBlocksBrokenHM().put(RCUtils.textedUUID(p), this.getBlocksBrokenHM().get(RCUtils.textedUUID(p)) + blocks);
			AchievementSaver.save(MainCore.instance, this);
		}
	}
	
	public int getBlocksBrokenBy(Player p) {
		return this.getBlocksBrokenHM().get(RCUtils.textedUUID(p));
	}
	
	public int getBlocksPlacedBy(Player p) {
		return this.getBlocksPlacedHM().get(RCUtils.textedUUID(p));
	}
	
	public String getSpecialMission() {
		return specialMission;
	}

	public void setSpecialMission(String specialMission) {
		this.specialMission = specialMission;
	}
	
	public int getSpecialPointsBy(Player p) {
		return this.getSpecialPointsHM().get(RCUtils.textedUUID(p));
	}
	
	public void addSpecialPoints(Player p, int points) {
		if(this.getCompletedHM().get(RCUtils.textedUUID(p)) == false) {
			this.getSpecialPointsHM().put(RCUtils.textedUUID(p), getSpecialPointsBy(p) + points);
			AchievementSaver.save(MainCore.instance, this);
		}
	}

	public int getSpecialPoints() {
		return specialPoints;
	}

	public void setSpecialPoints(int specialPoints) {
		this.specialPoints = specialPoints;
	}

	public void initFor(Player p) {
		String uuid = RCUtils.textedUUID(p);
		
		if(!this.getCompletedHM().containsKey(uuid)) {
			this.getCompletedHM().put(uuid, false);
		}
		switch(type) {
		case KillMobs:
			if(!this.getKillMobsAmtHM().containsKey(uuid)) {
				this.getKillMobsAmtHM().put(uuid, 0);
			}
			break;
		case KillPlayers:
			if(!this.getKillPlayersAmtHM().containsKey(uuid)) {
				this.getKillPlayersAmtHM().put(uuid, 0);
			}
			break;
		case BreakBlocks:
			if(!this.getBlocksBrokenHM().containsKey(uuid)) {
				this.getBlocksBrokenHM().put(uuid, 0);
			}
			break;
		case PlaceBlocks:
			if(!this.getBlocksPlacedHM().containsKey(uuid)) {
				this.getBlocksPlacedHM().put(uuid, 0);
			}
			break;
		case Special:
			if(!this.getSpecialPointsHM().containsKey(uuid)) {
				this.getSpecialPointsHM().put(uuid, 0);
			}
			break;
		default:
			break;
		
		}
		
		
		AchievementSaver.save(MainCore.instance, this);
		
	}
	
	public ItemStack getCustomizedItemStack(Player p) {
		GuiLanguage lang = MainCore.instance.guiLang;
		//CustomItem item = new CustomItem(Material.PAPER);
		boolean finished = this.getCompletedHM().get(RCUtils.textedUUID(p));
		CustomItem item;
		
		if(!finished) {
			item = new CustomItem(Material.COAL);
		} else {
			item = new CustomItem(Material.DIAMOND);
		//	item.addLores(ChatColor.GREEN+""+ChatColor.ITALIC+"Completed!");
			item.addLores(lang.getAchievementCompleted());
		}
		
		item.setName(name);
		switch(type) {
		case KillMobs:
		//	item.addLores(ChatColor.YELLOW+"You must kill "+killMobsAmt+" "+killMobsType.toString()+" to earn",ChatColor.YELLOW+"this achievement.");
		//	item.addLores("§eKill §4"+killMobsAmt+" §e"+killMobsType.toString()+".");
			item.addLores(lang.getAchievementKillMobs(killMobsAmt, killMobsType.toString()));
			item.addLores(ChatColor.RED+""+this.getMobsKilledBy(p)+" / "+killMobsAmt);
			item.addLores(ChatColor.GOLD+"Progression: ["+getProgressionBar(this.getMobsKilledBy(p), killMobsAmt)+ChatColor.GOLD+"]");
			
			break;
		case KillPlayers:
		//	item.addLores(ChatColor.YELLOW+"You must kill "+killPlayersAmt+" players to earn",ChatColor.YELLOW+"this achievement. ");
		//	item.addLores("§eKill §4"+killPlayersAmt+"§ePlayer(s).");
			item.addLores(lang.getAchievementKillPlayers(killPlayersAmt));
			item.addLores(ChatColor.RED+""+this.getPlayersKilledBy(p)+" / "+killPlayersAmt);

			item.addLores(ChatColor.GOLD+"Progression: ["+getProgressionBar(this.getPlayersKilledBy(p), killPlayersAmt)+ChatColor.GOLD+"]");
			break;
		case BreakBlocks:
		//	item.addLores(ChatColor.YELLOW+"You must break "+breakBlocksAmt+" "+breakBlocksType.toString()+" to earn",ChatColor.YELLOW+"this achievement.");
		//	item.addLores("§eBreak §4"+breakBlocksAmt+" §e"+breakBlocksType.toString()+".");
			item.addLores(lang.getAchievementBreakBlocks(breakBlocksAmt, breakBlocksType.toString()));
			item.addLores(ChatColor.RED+""+this.getBlocksBrokenBy(p)+" / "+breakBlocksAmt);

			item.addLores(ChatColor.GOLD+"Progression: ["+getProgressionBar(this.getBlocksBrokenBy(p), breakBlocksAmt)+ChatColor.GOLD+"]");
			break;
		case PlaceBlocks:
		//	item.addLores(ChatColor.YELLOW+"You must place "+placeBlocksAmt+" "+placeBlocksType.toString()+" to earn",ChatColor.YELLOW+"this achievement.");
		//	item.addLores("§ePlace §4"+placeBlocksAmt+" §e"+placeBlocksType.toString()+".");
			item.addLores(lang.getAchievementPlaceBlocks(placeBlocksAmt, placeBlocksType.toString()));
			item.addLores(ChatColor.RED+""+this.getBlocksPlacedBy(p)+" / "+placeBlocksAmt);

			item.addLores(ChatColor.GOLD+"Progression: ["+getProgressionBar(this.getBlocksPlacedBy(p), placeBlocksAmt)+ChatColor.GOLD+"]");
			break;
		case Special:
		//	item.addLores(ChatColor.YELLOW+"You must "+specialMission+" to earn",ChatColor.YELLOW+"this achievement.");
			item.addLores(specialMission);
			item.addLores(ChatColor.RED+""+this.getSpecialPointsBy(p)+" / "+specialPoints);

			item.addLores(ChatColor.GOLD+"Progression: ["+getProgressionBar(this.getSpecialPointsBy(p), specialPoints)+ChatColor.GOLD+"]");
			break;
		default:
			break;
	
		
		}
		
		
		
		item.addLores(" ");
	/*
		item.addLores("§7Rewards:");
		item.addLores("§7- §a"+reward.getXp()+" xp");
		item.addLores("§7- §6"+reward.getGold()+" gold");
	*/	
		for(String s : lang.getAchievementRewardLore(reward.getXp(), reward.getGold())) {
			item.addLores(s);
		}
		
		
		//return item.build(this.getCompletedHM().get(RCUtils.textedUUID(p))).getItem();
		return item.build(finished).getItem();
	}
	
	public String getProgressionBar(int points, int requiredPoints) {
		StringBuilder sb = new StringBuilder();
		int total_bars = 20;
		
		String b = "|";
		String g = "|";
		
		// Calculate blue bars
		int x = (points * total_bars) / requiredPoints;

		// Append blue bars to string builder
		sb.append(ChatColor.GREEN.toString());
		for(int i = 0; i < x; i++) {
			sb.append(b);

		}

		// Apped gray bars to string builder
		sb.append(ChatColor.GRAY.toString());
		for(int i = 0; i < (total_bars-x); i++) {
			sb.append(g);

		}

		// Make the bar
		String progressionBar = sb.toString();

		
		return progressionBar;
	}
	
}