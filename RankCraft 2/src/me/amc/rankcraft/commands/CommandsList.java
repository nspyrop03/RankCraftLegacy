package me.amc.rankcraft.commands;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.PermissionList;
import me.amc.rankcraft.RankCraft;
import me.amc.rankcraft.commands.LeaderboardAlias.LbAlias;
import me.amc.rankcraft.commands.LeaderboardAlias.LeadersAlias;

public class CommandsList {

	private PermissionList perms = MainCore.instance.permList;
	
//	private List<String> leaderboardAlias = new ArrayList<>();
	
	public GiveCommand giveCommand;
	public GuiCommand guiCommand;
	public SetRewardCommand setRewardCommand;
	public BackpackCommand backpackCommand;
	public MobCommand mobCommand;
	public SpawnCommand spawnCommand;
	public LeaderboardCommand leaderboardCommand;
	
	public LeaderboardAlias.LeadersAlias leadersAlias;
	public LeaderboardAlias.LbAlias lbAlias;
	
	public HelpCommand helpCommand;
	
	public TreasureCommand treasureCommand;
	public EnchantCommand enchantCommand;
	public SetLootableCommand setLootableCommand;
	
	public ToggleCommand toggleCommand;
	
	public ItemCommand itemCommand;
	
	private CommandAlias shop;
	private CommandAlias stats;
	private CommandAlias skills;
	private CommandAlias mobstats;
	private CommandAlias achievements;
	private CommandAlias backpackshop;
	private CommandAlias bpshop;
	private CommandAlias quests;
	private CommandAlias packshop;
	private CommandAlias bp;
	
	private CommandAlias classes;
	//private CommandAlias selectClass;
	
	
	private SelectClassAlias selectClassAlias;
	
//	private CommandAlias leaders;
//	private CommandAlias lb;
	
	public CommandsList() {
	/*	
		leaderboardAlias.clear();
		leaderboardAlias.add("level");
		leaderboardAlias.add("blocksplaced");
		leaderboardAlias.add("bp");
		leaderboardAlias.add("blocksbroken");
		leaderboardAlias.add("bb");
	*/
		giveCommand = new GiveCommand();
		MainCore.instance.rankCraft.registerSubCommand(giveCommand);
		
		guiCommand = new GuiCommand();
		MainCore.instance.rankCraft.registerSubCommand(guiCommand);
		
		setRewardCommand = new SetRewardCommand();
		MainCore.instance.rankCraft.registerSubCommand(setRewardCommand);
		
		backpackCommand = new BackpackCommand();
		MainCore.instance.rankCraft.registerSubCommand(backpackCommand);
	
		mobCommand = new MobCommand();
		MainCore.instance.rankCraft.registerSubCommand(mobCommand);
		
		spawnCommand = new SpawnCommand();
		MainCore.instance.rankCraft.registerSubCommand(spawnCommand);
		
		leaderboardCommand = new LeaderboardCommand();
		MainCore.instance.rankCraft.registerSubCommand(leaderboardCommand);
		
		helpCommand = new HelpCommand();
		MainCore.instance.rankCraft.registerSubCommand(helpCommand);
		
		treasureCommand = new TreasureCommand();
		MainCore.instance.rankCraft.registerSubCommand(treasureCommand);
		
		enchantCommand = new EnchantCommand();
		MainCore.instance.rankCraft.registerSubCommand(enchantCommand);
		
		setLootableCommand = new SetLootableCommand();
		MainCore.instance.rankCraft.registerSubCommand(setLootableCommand);
		
		selectClassAlias = new SelectClassAlias();
		MainCore.instance.rankCraft.registerSubCommand(selectClassAlias);
		
		toggleCommand = new ToggleCommand();
		MainCore.instance.rankCraft.registerSubCommand(toggleCommand);
		
		itemCommand = new ItemCommand();
		MainCore.instance.rankCraft.registerSubCommand(itemCommand);
		
		makeAliases();
		initCommandDescriptions();
	}
	
	private void makeAliases() {
		shop = new CommandAlias("shop", "opengui shop", perms.shop_permission);
		register(shop);
		stats = new CommandAlias("stats", "opengui stats", perms.stats_permission);
		register(stats);
		skills = new CommandAlias("skills", "opengui skills", perms.skills_permission);
		register(skills);
		mobstats = new CommandAlias("mobstats", "opengui mobstats", perms.mobStats_permission);
		register(mobstats);
		achievements = new CommandAlias("achievements", "opengui achievements", perms.achievements_permission);
		register(achievements);
		backpackshop = new CommandAlias("backpackshop", "opengui backpackshop", perms.backpackshop_permission);
		register(backpackshop);
		bpshop = new CommandAlias("bpshop", "opengui backpackshop", perms.backpackshop_permission);
		register(bpshop);
		quests = new CommandAlias("quests", "opengui quests", perms.quests_permission);
		register(quests);
		packshop = new CommandAlias("packshop", "opengui packshop", perms.packshop_permission);
		register(packshop);
		bp = new CommandAlias("bp", "backpack", perms.backpack_permission);
		register(bp);
		
		classes = new CommandAlias("classes", "opengui classes", perms.classes_permission);
		register(classes);
		//selectClass = new CommandAlias("selectclass", "opengui selectclass", perms.classes_permission);
		//register(selectClass);
		
		leadersAlias = new LeadersAlias();
		MainCore.instance.rankCraft.registerSubCommand(leadersAlias);
		lbAlias = new LbAlias();
		MainCore.instance.rankCraft.registerSubCommand(lbAlias);
	/*	
		leaders = new CommandAlias("leaders", leaderboardCommand.getLabel()+" level", perms.leaderboard_permission);
		register(leaders);
		leaders = new CommandAlias("leaders", leaderboardCommand.getLabel()+" blocksbroken", perms.leaderboard_permission);
		register(leaders);
		leaders = new CommandAlias("leaders", leaderboardCommand.getLabel()+" blocksplaced", perms.leaderboard_permission);
		register(leaders);
		lb = new CommandAlias("lb", leaderboardCommand.getLabel()+" level", perms.leaderboard_permission);
		register(lb);
		lb = new CommandAlias("lb", leaderboardCommand.getLabel()+" blocksbroken", perms.leaderboard_permission);
		register(lb);
		lb = new CommandAlias("lb", leaderboardCommand.getLabel()+" blocksplaced", perms.leaderboard_permission);
		register(lb);
	*/
		/*
		for(int i = 0; i < leaderboardAlias.size(); i++) {
			leaders = new CommandAlias("leaders", leaderboardCommand.getLabel()+" "+leaderboardAlias.get(i), perms.leaderboard_permission);
			register(leaders);
			leaders = null;
			
			lb = new CommandAlias("lb", leaderboardCommand.getLabel()+" "+leaderboardAlias.get(i), perms.leaderboard_permission);
			register(lb);
			lb = null;
		//	System.out.println(leaders.getLabel()+" -> "+leaders.getFullCommand());
		//	System.out.println(lb.getLabel()+" -> "+lb.getFullCommand());
		}
		*/
	}
	
	private void initCommandDescriptions() {
		RankCraft rankCraft = MainCore.instance.rankCraft;
		rankCraft.addCommandDescription("give treasure <normal/super/ultra> <amount>", "Gives you treasures", perms.giveTreasure_permission, true);
		rankCraft.addCommandDescription("stats", "Opens Stats GUI", perms.stats_permission, true);
		rankCraft.addCommandDescription("mobstats", "Opens MobStats GUI", perms.mobStats_permission, true);
		rankCraft.addCommandDescription("give gold <amount>", "Gives you gold", perms.giveGold_permission, true);
		rankCraft.addCommandDescription("give xpcrystal <small/medium/large> <amount>", "Gives you XpCrystals", perms.giveXpCrystal_permission, true);
		rankCraft.addCommandDescription("give manacrystal <small/medium/large> <amount>", "Gives you ManaCrystals", perms.giveManaCrystal_permission, true);
		rankCraft.addCommandDescription("give yamlweapons", "Gives you the weapons from the files", perms.giveYamlWeapons_permission, true);
		rankCraft.addCommandDescription("give blockpack <bronze/silver/gold> <amount>", "Gives you BlockPacks", perms.giveBlockpack_permission, true);
		rankCraft.addCommandDescription("give itempack <bronze/silver/gold> <amount>", "Gives you ItemPacks", perms.giveItempack_permission, true);
		rankCraft.addCommandDescription("give mixedpack <bronze/silver/gold> <amount>", "Gives you MixedPacks", perms.giveMixedpack_permission, true);
		rankCraft.addCommandDescription("packshop", "Opens the PackShop", perms.packshop_permission, true);
		rankCraft.addCommandDescription("give spells", "Gives you all the spells", perms.giveSpells_permission, true);
		rankCraft.addCommandDescription("skills", "Opens Skills GUI", perms.skills_permission, true);
		rankCraft.addCommandDescription("quests", "Opens Quests GUI", perms.quests_permission, true);
		rankCraft.addCommandDescription("setreward <quest> <xp> <gold>", "Sets the reward for the specific quest", perms.setReward_permission, true);
		rankCraft.addCommandDescription("achievements", "Opens Achievements GUI", perms.achievements_permission, true);
		rankCraft.addCommandDescription("give skeletonwizard egg", "Gives you the SkeletonWizard's Egg", perms.giveSkeletonWizardEgg_permission, true);
		rankCraft.addCommandDescription("give zombiewarrior egg", "Gives you the ZombieWarrior Egg", perms.giveZombieWarriorEgg_permission, true);
		rankCraft.addCommandDescription("give zombiemaster egg", "Gives you the ZombieMaster Egg", perms.giveZombieMasterEgg_permission, true);
		rankCraft.addCommandDescription("backpack", "Opens your backpack", perms.backpack_permission, true);
		rankCraft.addCommandDescription("backpackshop", "Opens BackpackShop GUI", perms.backpackshop_permission, true);
		rankCraft.addCommandDescription("shop", "Opens the General Shop", perms.shop_permission, true);
		rankCraft.addCommandDescription("give mobspawner <mob> <sec> <range>", "Gives you a custom mob spawner", perms.giveMobSpawner_permission, true);
		rankCraft.addCommandDescription("spawn <mob> <times>", "Spawns <times> times the custom mob <mob>", perms.mobSpawn_permission, true);
		rankCraft.addCommandDescription("mob create <fileName> <zombie/skeleton>", "Creates a new custom mob", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("mob setArmors <mob>", "Sets <mob>'s armors and weapon", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("mob setDrops <mob>", "Sets <mob>'s drops", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("mob setLevel <mob>", "Sets <mob>'s level", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("mob finish <mobs>", "Finishes <mob>'s creation", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("mob setName <mob> <name>", "Sets <mob>'s name", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("mob setCanSpawn <mob> <true/false>", "Sets whether the <mob> can spawn by default", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("classes", "Opens RpgClasses GUI", perms.classes_permission, true);
		rankCraft.addCommandDescription("selectclass", "Opens Select RpgClass GUI", perms.classes_permission, true);
		rankCraft.addCommandDescription("leaderboard level", "Opens the level leaderboard", perms.leaderboard_permission, true);
		rankCraft.addCommandDescription("leaderboard blocksbroken", "Opens the blocks broken leaderbard", perms.leaderboard_permission, true);
		rankCraft.addCommandDescription("leaderboard blockplaced", "Opens the blocks placed leaderboard", perms.leaderboard_permission, true);
		rankCraft.addCommandDescription("help <page>", "Help Command", null, false);
		rankCraft.addCommandDescription("treasure make <TreasureName> <Id> <Rarity>", "Makes a new type of TreasureChest", perms.treasure_permission, true);
		rankCraft.addCommandDescription("treasure edit <Id>", "Lets you edit a TreasureChest", perms.treasure_permission, true);
		rankCraft.addCommandDescription("treasure list", "Shows all successfully listed treasures", perms.treasure_permission, true);
		rankCraft.addCommandDescription("give treasure2 <id> <amount>", "Gives you TreasureChest2",  perms.giveTreasure2_permission, true);
		rankCraft.addCommandDescription("enchant <enchantment> <level>", "Enchants the item that you are holding", perms.enchant_permission, true);
		rankCraft.addCommandDescription("setlootable", "The item does not changes when found from a treasure", perms.setLootable_permission, true);
		rankCraft.addCommandDescription("give spell <spell-name> <spell-level> <amount>", "Gives you the specific spell", perms.giveSpecificSpell_permission, true);
		rankCraft.addCommandDescription("give yamlweapon <file-name>", "Gives you the specific weapon", perms.giveSpecificWeapon_permission, true);
		rankCraft.addCommandDescription("selectclass <player>", "Opens the SelectClass GUI to player", perms.selectClassForOthers_permission, true);
		rankCraft.addCommandDescription("selectclass <class-name>", "Selects a RpgClass", perms.classes_permission, true);
		rankCraft.addCommandDescription("mob addability <mob> <ability> <chance percentage>", "Adds <ability> to <mob>", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("mob rmvability <mob> <ability>", "Removes <ability> from <mob>", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("toggle scoreboard", "Shows/hides RankCraft scoreboard", perms.toggleScoreboard_permission, true);
		rankCraft.addCommandDescription("toggle scoreboard <player>", "Shows/hides RankCraft scoreboard for <player>", perms.toggleScoreboardOthers_permission, true);
		rankCraft.addCommandDescription("mob addbiome <mob> <biome>", "Makes mob spawn on specific biome", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("mob rmvbiome <mob> <biome>", "Removes spawn biome from mob", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("mob create <mob> <name> <level>", "Creates mob and opens MobCreator", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("mob create <mob> <name> <level> <hp>", "Creates mob and opens MobCreator", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("mob edit <mob>", "Opens MobCreator for mob", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("mob edit <mob> <stage>", "Opens MobCreator for mob at <stage> GUI", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("mob sethp <mob> <hp>", "Set Hp of mob", perms.mobCreate_permission, true);
		rankCraft.addCommandDescription("item create <id> <name> <minLevel>", "Opens Item Creator", perms.itemCreate_permission, true);
		rankCraft.addCommandDescription("item edit <id>", "Opens Item Creator to edit item", perms.itemCreate_permission, true);
		rankCraft.addCommandDescription("item edit <id> <stage>", "Opens Item Creator on specific stage", perms.itemCreate_permission, true);
		rankCraft.addCommandDescription("give yamlarmors", "Gives you all armors from files", perms.giveYamlArmors_permission, true);
		rankCraft.addCommandDescription("give yamlarmor <filename>", "Gives you specific armor from file", perms.giveSpecificYamlArmor_permission, true);
		rankCraft.addCommandDescription("give gold <amount> <player>", "Gives <amount> of gold to <player> as an item", perms.giveGold_permission, true);
		rankCraft.addCommandDescription("give spell <spell-name> <level> <amount> <player>", "Gives specific spell to player", perms.giveSpells_permission, true);
		rankCraft.addCommandDescription("give classpoints <points>", "Gives you <points> ClassPoints", perms.giveClasspoints_permission, true);
		rankCraft.addCommandDescription("give classpoints <points> <player>", "Gives <points> ClassPoints to <player>", perms.giveClasspoints_permission, true);
	}
	
	private void register(CommandAlias alias) {
		MainCore.instance.rankCraft.registerSubCommand(alias);
	}
}
