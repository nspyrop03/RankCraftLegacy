package me.amc.rankcraft;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.permissions.Permission;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.PluginManager;

import me.amc.rankcraft.achievements.Achievement;
import me.amc.rankcraft.achievements.AchievementEvents;
import me.amc.rankcraft.achievements.AchievementGui;
import me.amc.rankcraft.achievements.AchievementsList;
import me.amc.rankcraft.backpack.BackpackEvent;
import me.amc.rankcraft.bosses.SkeletonWizard;
import me.amc.rankcraft.bosses.ZombieMaster;
import me.amc.rankcraft.bosses.ZombieWarrior;
import me.amc.rankcraft.classes.ArcherClass;
import me.amc.rankcraft.classes.GladiatorClass;
import me.amc.rankcraft.classes.MysteriousClass;
import me.amc.rankcraft.classes.NinjaClass;
import me.amc.rankcraft.classes.PotionEffectEvent;
import me.amc.rankcraft.classes.RpgClass;
import me.amc.rankcraft.classes.RpgClassEvents;
import me.amc.rankcraft.classes.RpgClassGUI;
import me.amc.rankcraft.classes.RpgClassSelectGUI;
import me.amc.rankcraft.classes.WeaponEvents;
import me.amc.rankcraft.classes.WizardClass;
import me.amc.rankcraft.commands.CommandDescription;
import me.amc.rankcraft.commands.CommandsList;
import me.amc.rankcraft.commands.SubCommand;
import me.amc.rankcraft.customevents.PlayerUseSpellEvent;
import me.amc.rankcraft.damage.DamageSystem;
import me.amc.rankcraft.damage.HPSystem;
import me.amc.rankcraft.events.BlockStatsEvents;
import me.amc.rankcraft.events.CrystalCraftEvent;
import me.amc.rankcraft.events.GoldEvent;
import me.amc.rankcraft.events.HPEvents;
import me.amc.rankcraft.events.MobStatsEvents;
import me.amc.rankcraft.events.MyEvents;
import me.amc.rankcraft.events.PlayerDieBackpackEvent;
import me.amc.rankcraft.events.PlayerJoinOnServer;
import me.amc.rankcraft.gui.BackpackShop;
import me.amc.rankcraft.gui.BronzePacks;
import me.amc.rankcraft.gui.GoldPacks;
import me.amc.rankcraft.gui.MobStatsGUI;
import me.amc.rankcraft.gui.PacksGUI;
import me.amc.rankcraft.gui.RandomItemsShop;
import me.amc.rankcraft.gui.SilverPacks;
import me.amc.rankcraft.gui.SkillsGUI;
import me.amc.rankcraft.gui.StatsGUI;
import me.amc.rankcraft.items2.SpecialItem;
import me.amc.rankcraft.items2.SpecialItemsFactory;
import me.amc.rankcraft.leaderboards.BlocksBrokenLeaderboard;
import me.amc.rankcraft.leaderboards.BlocksPlacedLeaderboard;
import me.amc.rankcraft.leaderboards.LevelLeaderboard;
import me.amc.rankcraft.mobs.AbilityListener;
import me.amc.rankcraft.mobs.DefaultMobSpawn;
import me.amc.rankcraft.mobs.MobCreator;
import me.amc.rankcraft.mobs.MobSpawner;
import me.amc.rankcraft.mobs.MobSpawnerListener;
import me.amc.rankcraft.mobs.Skeletons;
import me.amc.rankcraft.mobs.YamlMob;
import me.amc.rankcraft.mobs.Zombies;
import me.amc.rankcraft.packs.BlockPack;
import me.amc.rankcraft.packs.ItemPack;
import me.amc.rankcraft.packs.MixedPack;
import me.amc.rankcraft.packs.Pack.PackLevel;
import me.amc.rankcraft.quests2.Quest;
import me.amc.rankcraft.quests2.QuestEvents;
import me.amc.rankcraft.quests2.QuestGui2;
import me.amc.rankcraft.quests2.YamlQuest;
import me.amc.rankcraft.rpgitem.ArmorCheckEvents;
import me.amc.rankcraft.rpgitem.CrystalSize;
import me.amc.rankcraft.rpgitem.ItemCreator;
import me.amc.rankcraft.rpgitem.ManaCrystal;
import me.amc.rankcraft.rpgitem.XpCrystal;
import me.amc.rankcraft.rpgitem.YamlArmor;
import me.amc.rankcraft.rpgitem.YamlItem;
import me.amc.rankcraft.skills.AttackSkill;
import me.amc.rankcraft.skills.DefenseSkill;
import me.amc.rankcraft.skills.MagicSkill;
import me.amc.rankcraft.spells.AntiSpellsData;
import me.amc.rankcraft.spells.Spell;
import me.amc.rankcraft.spells.SpellCase;
import me.amc.rankcraft.spells.SpellCase.CaseType;
import me.amc.rankcraft.spells.SpellCaseEvent;
import me.amc.rankcraft.spells.SpellUtils;
import me.amc.rankcraft.stats.Gold;
import me.amc.rankcraft.stats.LevelRewardSystem;
import me.amc.rankcraft.stats.MobStats;
import me.amc.rankcraft.stats.Stats;
import me.amc.rankcraft.treasures2.TreasureChest2;
import me.amc.rankcraft.treasures2.TreasureChest2.Rarity;
import me.amc.rankcraft.treasures2.TreasureEvent2;
import me.amc.rankcraft.treasures2.TreasureMakerEvent;
import me.amc.rankcraft.utils.RCUtils;
import me.amc.rankcraft.yaml.MobsYaml;
import me.amc.rankcraft.yaml.ShopItemsYaml;
import me.amc.rankcraft.yaml.YamlParser;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class RankCraft {

	private PluginManager pm;

//	public RankSystem rankSystem;
	
	public Stats stats;
	public MobStats mobStats;
	public Gold gold;

	public AttackSkill attackSkill;
	public DefenseSkill defenseSkill;
	public MagicSkill magicSkill;

	//public SwordsYaml swordsYaml;
	//public BowsYaml bowsYaml;
	//public AxesYaml axesYaml;
	//public PickaxesYaml pickaxesYaml;

	public CommandsList commandsList;

	public StatsGUI statsGUI;
	public MobStatsGUI mobStatsGUI;
	public PacksGUI packShop;
	public BronzePacks bronzePackShop;
	public SilverPacks silverPackShop;
	public GoldPacks goldPackShop;
	public SkillsGUI skillsGUI;
	public QuestGui2 questGui2;
	public AchievementGui achievementGui;
	public BackpackShop bpShop;

	public SpellUtils spellUtils;
	public AntiSpellsData antiSpellsData;

	public HPSystem hpSystem;

	public HashMap<String, SubCommand> commands = new HashMap<>();
	// public HashMap<String, Backpack> backpacks = new HashMap<>();

	public List<YamlItem> yamlWeapons = new ArrayList<>();
	public List<Quest> quests2 = new ArrayList<>();
	public List<YamlQuest> yamlQuests = new ArrayList<>();
	public List<Achievement> achievements = new ArrayList<>();

	public AchievementsList achievementsList;

	public Zombies zombies;
	public Skeletons skeletons;

	public SkeletonWizard skeletonWizard;
	public ZombieMaster zombieMaster;
	public ZombieWarrior zombieWarrior;

	//public List<RpgItem> specialItems = new ArrayList<>();
	
	//public Excalibur excalibur;
	//public TreeAxe treeAxe;
	//public IceBattleAxe iceBattleAxe;
	//public ZeusSword zeusSword;
	//public DefaultCrossbow crossbow;
	
	public MobsYaml mobsYaml;
	
	public RandomItemsShop shop;
	
	public List<String> mobFiles = new ArrayList<>();
	public List<YamlMob> mobs = new ArrayList<>();
	public List<YamlMob> yamlZombies = new ArrayList<>();
	public List<YamlMob> yamlSkeletons = new ArrayList<>();
	
	public List<MobSpawner> mobSpawners = new ArrayList<>();

	public List<RpgClass> rpgClasses = new ArrayList<>();
	
	public RpgClassGUI rpgClassGUI;
	public RpgClassSelectGUI rpgClassSelectGUI;
	
	public GladiatorClass gladiatorClass;
	public ArcherClass archerClass;
	public NinjaClass ninjaClass;
	public WizardClass wizardClass;
	public MysteriousClass mysteriousClass;
	
	public RpgClassEvents rpgClassEvents;
	
	public LevelLeaderboard levelLeaderboard;
	public BlocksBrokenLeaderboard blocksBrokenLeaderboard;
	public BlocksPlacedLeaderboard blocksPlacedLeaderboard;
	
	public List<CommandDescription> commandDescriptions = new ArrayList<>();
	
	public List<TreasureChest2> treasureChests = new ArrayList<>();
	
	public ShopItemsYaml shopItemsYaml;
	
	//public FireCrossbow fireCrossbow;
	/*
	public List<DynamicItem> dynamicItems = new ArrayList<>();
	public DynamicExcalibur dynamicExcalibur;
	*/
	public BlockStatsEvents blockStatsEvents;
	
	private static String nmsver;
    //private static boolean useOldMethods = false;
    
    public LevelRewardSystem levelRewardSystem;
    
    public MobCreator mobCreator;
	
    public List<YamlArmor> yamlArmors = new ArrayList<>();
    public ItemCreator itemCreator;
    
    //public SpecialItems specialItems;
    public SpecialItemsFactory specialItemsFactory;
    
    @SuppressWarnings("rawtypes")
	public HashMap<NamespacedKey, PersistentDataType> specialKeyMap;
    
	public RankCraft() {
		
		specialKeyMap = new HashMap<>();
		specialKeyMap.put(SpecialItem.DURABILITY_KEY, PersistentDataType.INTEGER);
		specialKeyMap.put(SpecialItem.FUEL_KEY, PersistentDataType.INTEGER);
		specialKeyMap.put(SpecialItem.DATE_KEY, PersistentDataType.STRING);
		specialKeyMap.put(SpecialItem.SPECIAL_ITEM_KEY, PersistentDataType.STRING);

		pm = Bukkit.getServer().getPluginManager();

		hpSystem = new HPSystem(MainCore.instance);
		mobsYaml = new MobsYaml();
		shopItemsYaml = new ShopItemsYaml();

		initStats();
		initSkills();
		//initYamls();
		initYamlItems();
		
		reloadYamlArmors();

		File file = new File(RCUtils.WEAPONS_DIRECTORY + "/test01.weapon");

		if (!file.exists()) {
			file.getParentFile().mkdirs();

			MainCore.instance.saveResource("weapons/test01.weapon", false);
			yamlWeapons.add(new YamlItem("test01"));

		}

		if (!RCUtils.QUESTS_DIRECTORY.exists()) {
			RCUtils.QUESTS_DIRECTORY.mkdirs();
		}
		
		if(!new File("plugins/RankCraft/spawners").exists()) {
			new File("plugins/RankCraft/spawners").mkdirs();
		}

		saveQuestExample("placeblocks01.quest");
		saveQuestExample("breakblocks01.quest");
		saveQuestExample("killmobs01.quest");
		saveQuestExample("killplayers01.quest");
		
		makeDefaultMobFiles();

		makeYamlQuests();

		questGui2 = new QuestGui2();

		antiSpellsData = new AntiSpellsData();

		if (MainCore.instance.config.startupDebug) {
			System.out.println("# ***** Quests ***** #");
			for (Quest q : quests2) {
				System.out.println("- " + q.getId());
			}
		}

		achievementsList = new AchievementsList();
		registerAchievements();
		if (MainCore.instance.config.startupDebug) {
			System.out.println("# ***** Achievements ***** #");
			for (Achievement a : achievements) {
				System.out.println("- " + a.getName());
			}
		}

		if(MainCore.instance.config.enablePreMadeMobs) {
			zombies = new Zombies(MainCore.instance);
			skeletons = new Skeletons(MainCore.instance);
		}

		skeletonWizard = new SkeletonWizard();
		zombieMaster = new ZombieMaster();
		zombieWarrior = new ZombieWarrior();

		//specialItems.clear();
		
		//excalibur = new Excalibur();
		//specialItems.add(excalibur);
		//treeAxe = new TreeAxe();
		//specialItems.add(treeAxe);
		//iceBattleAxe = new IceBattleAxe();
		//specialItems.add(iceBattleAxe);
		/*
		zeusSword = new ZeusSword();
		specialItems.add(zeusSword);
		crossbow = new DefaultCrossbow();
		specialItems.add(crossbow);
		fireCrossbow = new FireCrossbow();
		specialItems.add(fireCrossbow);
		*/
		
		/*
		specialItems = new SpecialItems();
		
		dynamicExcalibur = new DynamicExcalibur();
		dynamicItems.add(dynamicExcalibur);
		*/
		specialItemsFactory = new SpecialItemsFactory();
		
		
		File readme = new File("plugins/RankCraft/ReadMe.txt");
		if(!readme.exists()) {
			MainCore.instance.saveResource("ReadMe.txt", true);
		}
		
		File placeholders = new File("plugins/RankCraft/placeholders.txt");
		if(!placeholders.exists()) {
			MainCore.instance.saveResource("placeholders.txt", true);
		}
		
		reloadYamlMobFiles();
		makeYamlMobLists();
		
		pm.registerEvents(new DefaultMobSpawn(), MainCore.instance);
		
		reloadMobSpawners();
		pm.registerEvents(new MobSpawnerListener(), MainCore.instance);
		
		pm.registerEvents(new AbilityListener(), MainCore.instance);
		
		initRpgClasses();
		rpgClassGUI = new RpgClassGUI();		
		rpgClassSelectGUI = new RpgClassSelectGUI();
		
		rpgClassEvents = new RpgClassEvents();
		pm.registerEvents(new PotionEffectEvent(), MainCore.instance);
		pm.registerEvents(new WeaponEvents(), MainCore.instance);
		
		//System.out.println(MainCore.instance.language.getLevelTitle() == null);
		levelLeaderboard = new LevelLeaderboard(MainCore.instance.language.getLevelTitle());
		//levelLeaderboard.update();
		blocksBrokenLeaderboard = new BlocksBrokenLeaderboard();
		blocksPlacedLeaderboard = new BlocksPlacedLeaderboard();
		
		if(!RCUtils.TREASURES2_DIRECTORY.exists()) {
			RCUtils.TREASURES2_DIRECTORY.mkdirs();
		}
		
		treasureChests.clear();
		makeDefaultTreasure2("common.yml");
		makeDefaultTreasure2("rare.yml");
		makeDefaultTreasure2("epic.yml");
		
		nmsver = Bukkit.getServer().getClass().getPackage().getName();
        nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);

        /*
        if (nmsver.equalsIgnoreCase("v1_8_R1") || nmsver.startsWith("v1_7_")) { // Not sure if 1_7 works for the protocol hack?
            useOldMethods = true;
        }
        */
		
        saveDefaultFile(RCUtils.LEVEL_REWARDS_DIRECTORY, "level_1.yml", "levels");
        levelRewardSystem = new LevelRewardSystem();
        
        mobCreator = new MobCreator();
		//initTreasureChests();
        itemCreator = new ItemCreator();
	}

	public void addGeneralRecipes() {
		addSpellCaseRecipes();
	}
	
	private void initStats() {
	//	rankSystem = new RankSystem();
	//	System.out.println("Init:"+rankSystem.getRanks());
		stats = new Stats();
		mobStats = new MobStats();
		gold = new Gold();
	}

	private void initSkills() {
		attackSkill = new AttackSkill();
		defenseSkill = new DefenseSkill();
		magicSkill = new MagicSkill();
	}

	public void loadStats() {
		stats.load();
		mobStats.load();
		gold.load();
	}

	public void saveStats() {
		stats.save();
		mobStats.save();
		gold.save();
	}

	public void loadSkills() {
		attackSkill.load();
		defenseSkill.load();
		magicSkill.load();
	}

	public void saveSkills() {
		attackSkill.save();
		defenseSkill.save();
		magicSkill.save();
	}

	// Replaced! :(
	/*
	private void initYamls() {
		swordsYaml = new SwordsYaml();
		bowsYaml = new BowsYaml();
		axesYaml = new AxesYaml();
		pickaxesYaml = new PickaxesYaml();
	}
	*/
	
	public void registerEvents() {

		//new TreasureEvent(); (Replaced by TreasureEvent2)
		new PlayerJoinOnServer();
		blockStatsEvents = new BlockStatsEvents();
		new MobStatsEvents();
		new GoldEvent();
		statsGUI = new StatsGUI();
		mobStatsGUI = new MobStatsGUI();
		pm.registerEvents(new XpCrystal(CrystalSize.Small), MainCore.instance);
		pm.registerEvents(new XpCrystal(CrystalSize.Medium), MainCore.instance);
		pm.registerEvents(new XpCrystal(CrystalSize.Large), MainCore.instance);
		pm.registerEvents(new ManaCrystal(CrystalSize.Small), MainCore.instance);
		pm.registerEvents(new ManaCrystal(CrystalSize.Medium), MainCore.instance);
		pm.registerEvents(new ManaCrystal(CrystalSize.Large), MainCore.instance);
		pm.registerEvents(new BlockPack(PackLevel.Bronze), MainCore.instance);
		pm.registerEvents(new BlockPack(PackLevel.Silver), MainCore.instance);
		pm.registerEvents(new BlockPack(PackLevel.Gold), MainCore.instance);
		pm.registerEvents(new ItemPack(PackLevel.Bronze), MainCore.instance);
		pm.registerEvents(new ItemPack(PackLevel.Silver), MainCore.instance);
		pm.registerEvents(new ItemPack(PackLevel.Gold), MainCore.instance);
		pm.registerEvents(new MixedPack(PackLevel.Bronze), MainCore.instance);
		pm.registerEvents(new MixedPack(PackLevel.Silver), MainCore.instance);
		pm.registerEvents(new MixedPack(PackLevel.Gold), MainCore.instance);
		packShop = new PacksGUI();
		bronzePackShop = new BronzePacks();
		silverPackShop = new SilverPacks();
		goldPackShop = new GoldPacks();
		new MyEvents();

		spellUtils = new SpellUtils();
		if (MainCore.instance.config.startupDebug) {
			System.out.println("# ***** Spells ***** #");
			for (Spell s : spellUtils.spells) {
				System.out.println("- " + s.getName());
			}
		}

		pm.registerEvents(new DamageSystem(), MainCore.instance);
		pm.registerEvents(new HPEvents(), MainCore.instance);

		skillsGUI = new SkillsGUI();
		pm.registerEvents(new QuestEvents(), MainCore.instance);

		achievementGui = new AchievementGui();
		// pm.registerEvents(new AchievementEvents(), MainCore.instance);
		new AchievementEvents();
		pm.registerEvents(new BackpackEvent(), MainCore.instance);
		bpShop = new BackpackShop();
		
		shop = new RandomItemsShop();
		
		pm.registerEvents(new PlayerDieBackpackEvent(), MainCore.instance);
		
		new CrystalCraftEvent();
		new TreasureMakerEvent();
		new TreasureEvent2();
		
		pm.registerEvents(new SpellCaseEvent(), MainCore.instance);
		
		new ArmorCheckEvents();
		
	}

	public void registerSubCommand(SubCommand command) {
		commands.put(command.getLabel(), command);
	}

	public SubCommand getSubCommandFromLabel(String label) {
		return commands.get(label);
	}

	public HashMap<String, SubCommand> getSubCommandHM() {
		return commands;
	}

	public void initSubCommands() {
		commandsList = new CommandsList();
	}

	public void initYamlItems() {
		if (!RCUtils.WEAPONS_DIRECTORY.exists()) {
			RCUtils.WEAPONS_DIRECTORY.mkdirs();
		}

		yamlWeapons.clear();

		for (File f : RCUtils.WEAPONS_DIRECTORY.listFiles()) {
			if (f.getName().endsWith(".weapon")) {
				//System.out.println(f.getName());
				//String[] name = f.getName().split(".");//FilenameUtils.removeExtension(f.getName());
				//System.out.println(name[0]);
				String name = f.getName().replaceFirst("[.][^.]+$", "");
				//System.out.println(name);
				YamlItem item = new YamlItem(name);
				item.createRecipe();
				yamlWeapons.add(item);
			}
		}
	}
	
	public void reloadYamlArmors() {
		if(!RCUtils.ARMORS_DIRECTORY.exists()) {
			RCUtils.ARMORS_DIRECTORY.mkdirs();
		}
		
		yamlArmors.clear();
		
		for(File f : RCUtils.ARMORS_DIRECTORY.listFiles()) {
			if(f.getName().endsWith(".armor")) {
				YamlArmor armor = new YamlArmor(f.getName());
				armor.createRecipe();
				yamlArmors.add(armor);
			}
		}
		
 	}
/*
	public void sendActionbar(Player player, String message) {
		try {
			Constructor<?> constructor = getNMSClass("PacketPlayOutChat")
					.getConstructor(getNMSClass("IChatBaseComponent"), byte.class);

			Object icbc = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
					.invoke(null, "{\"text\":\"" + message + "\"}");
			Object packet = constructor.newInstance(icbc, (byte) 2);
			Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);

			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchFieldException | InstantiationException e) {
			e.printStackTrace();
		}
	}
*/
	/*
	public void sendActionbar(Player player, String message) {
        if (!player.isOnline()) {
            return; 
        }

        if (MainCore.instance.nmsver.startsWith("v1_12_")) {
            sendActionBarPost112(player, message);
        } else {
            sendActionBarPre112(player, message);
        }
    }
	*/
	
	public void sendActionBar(Player player, String message) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
	}
	
	/*
	public void sendActionBar(Player player, String message) {
        if (!player.isOnline()) {
            return; // Player may have logged out
        }

        // Call the event, if cancelled don't send Action Bar
      
        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);
            Object packet;
            Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
            Class<?> packetClass = Class.forName("net.minecraft.server." + nmsver + ".Packet");
            if (useOldMethods) {
                Class<?> chatSerializerClass = Class.forName("net.minecraft.server." + nmsver + ".ChatSerializer");
                Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
                Method m3 = chatSerializerClass.getDeclaredMethod("a", String.class);
                Object cbc = iChatBaseComponentClass.cast(m3.invoke(chatSerializerClass, "{\"text\": \"" + message + "\"}"));
                packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(cbc, (byte) 2);
            } else {
                Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
                Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
                try {
                    Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + nmsver + ".ChatMessageType");
                    Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
                    Object chatMessageType = null;
                    for (Object obj : chatMessageTypes) {
                        if (obj.toString().equals("GAME_INFO")) {
                            chatMessageType = obj;
                        }
                    }
                    Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                    packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, chatMessageTypeClass}).newInstance(chatCompontentText, chatMessageType);
                } catch (ClassNotFoundException cnfe) {
                    Object chatCompontentText = chatComponentTextClass.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                    packet = packetPlayOutChatClass.getConstructor(new Class<?>[]{iChatBaseComponentClass, byte.class}).newInstance(chatCompontentText, (byte) 2);
                }
            }
            Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle");
            Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer);
            Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
            Object playerConnection = playerConnectionField.get(craftPlayerHandle);
            Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", packetClass);
            sendPacketMethod.invoke(playerConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */ // here
	
	private Class<?> getNMSClass(String name) {
		try {
			return Class.forName("net.minecraft.server." + getVersion() + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}
	/*
	private static void sendActionBarPost112(Player player, String message) {
        if (!player.isOnline()) {
            return; 
        }

        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + MainCore.instance.nmsver + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);
            Object ppoc;
            Class<?> c4 = Class.forName("net.minecraft.server." + MainCore.instance.nmsver + ".PacketPlayOutChat");
            Class<?> c5 = Class.forName("net.minecraft.server." + MainCore.instance.nmsver + ".Packet");
            Class<?> c2 = Class.forName("net.minecraft.server." + MainCore.instance.nmsver + ".ChatComponentText");
            Class<?> c3 = Class.forName("net.minecraft.server." + MainCore.instance.nmsver + ".IChatBaseComponent");
            Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + MainCore.instance.nmsver + ".ChatMessageType");
            Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
            Object chatMessageType = null;
            for (Object obj : chatMessageTypes) {
                if (obj.toString().equals("GAME_INFO")) {
                    chatMessageType = obj;
                }
            }
            Object o = c2.getConstructor(new Class<?>[]{String.class}).newInstance(message);
            ppoc = c4.getConstructor(new Class<?>[]{c3, chatMessageTypeClass}).newInstance(o, chatMessageType);
            Method m1 = craftPlayerClass.getDeclaredMethod("getHandle");
            Object h = m1.invoke(craftPlayer);
            Field f1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = f1.get(h);
            Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
            m5.invoke(pc, ppoc);
        } catch (Exception ex) {
            ex.printStackTrace();
            
        }
    }

    private void sendActionBarPre112(Player player, String message) {
        if (!player.isOnline()) {
            return; // Player may have logged out
        }

        try {
            Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + MainCore.instance.nmsver + ".entity.CraftPlayer");
            Object craftPlayer = craftPlayerClass.cast(player);
            Object ppoc;
            Class<?> c4 = Class.forName("net.minecraft.server." + MainCore.instance.nmsver + ".PacketPlayOutChat");
            Class<?> c5 = Class.forName("net.minecraft.server." + MainCore.instance.nmsver + ".Packet");
            if (MainCore.instance.useOldMethods) {
                Class<?> c2 = Class.forName("net.minecraft.server." + MainCore.instance.nmsver + ".ChatSerializer");
                Class<?> c3 = Class.forName("net.minecraft.server." + MainCore.instance.nmsver + ".IChatBaseComponent");
                Method m3 = c2.getDeclaredMethod("a", String.class);
                Object cbc = c3.cast(m3.invoke(c2, "{\"text\": \"" + message + "\"}"));
                ppoc = c4.getConstructor(new Class<?>[]{c3, byte.class}).newInstance(cbc, (byte) 2);
            } else {
                Class<?> c2 = Class.forName("net.minecraft.server." + MainCore.instance.nmsver + ".ChatComponentText");
                Class<?> c3 = Class.forName("net.minecraft.server." + MainCore.instance.nmsver + ".IChatBaseComponent");
                Object o = c2.getConstructor(new Class<?>[]{String.class}).newInstance(message);
                ppoc = c4.getConstructor(new Class<?>[]{c3, byte.class}).newInstance(o, (byte) 2);
            }
            Method m1 = craftPlayerClass.getDeclaredMethod("getHandle");
            Object h = m1.invoke(craftPlayer);
            Field f1 = h.getClass().getDeclaredField("playerConnection");
            Object pc = f1.get(h);
            Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
            m5.invoke(pc, ppoc);
        } catch (Exception ex) {
            ex.printStackTrace();
           
        }
    }
    */
	public void registerQuest(Quest q) {
		quests2.add(q);
	}

	private void makeYamlQuests() {
		File dir = RCUtils.QUESTS_DIRECTORY;

		for (File f : dir.listFiles()) {
			if (f.getName().endsWith(".quest")) {
				YamlQuest q = new YamlQuest(f);
				registerQuest(q);
				yamlQuests.add(q);
			}
		}
	}

	private void saveQuestExample(String name) {
		File file = new File(RCUtils.QUESTS_DIRECTORY + "/" + name);

		if (!file.exists()) {
			file.getParentFile().mkdirs();
			MainCore.instance.saveResource("quests/" + name, false);
		}
	}
	
	private void saveDefaultFile(File dir, String name, String subDirName) {
		File file = new File(dir+"/"+name);
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			MainCore.instance.saveResource(subDirName+"/"+name, false);
		}
	}

	public void registerAchievement(Achievement a) {
		achievements.add(a);
	}
	
	private void registerAchievements() {
		registerAchievement(achievementsList.killZombies1);
		registerAchievement(achievementsList.killZombies2);
		registerAchievement(achievementsList.killZombies3);
		registerAchievement(achievementsList.luckyMan1);
		registerAchievement(achievementsList.luckyMan2);
		registerAchievement(achievementsList.luckyMan3);
		registerAchievement(achievementsList.luckyMan4);
		registerAchievement(achievementsList.wizard1);
		registerAchievement(achievementsList.wizard2);
		registerAchievement(achievementsList.wizard3);
		registerAchievement(achievementsList.wizard4);
		registerAchievement(achievementsList.stoneDestoyer1);
		registerAchievement(achievementsList.stoneDestoyer2);
		registerAchievement(achievementsList.stoneDestoyer3);
		registerAchievement(achievementsList.stoneDestoyer4);
		registerAchievement(achievementsList.stoneDestoyer5);
		registerAchievement(achievementsList.ironMiner1);
		registerAchievement(achievementsList.ironMiner2);
		registerAchievement(achievementsList.ironMiner3);
		registerAchievement(achievementsList.ironMiner4);
		registerAchievement(achievementsList.ironMiner5);
		registerAchievement(achievementsList.blockBreaker1);
		registerAchievement(achievementsList.blockBreaker2);
		registerAchievement(achievementsList.blockBreaker3);
		registerAchievement(achievementsList.blockBreaker4);
		registerAchievement(achievementsList.blockBreaker5);
		registerAchievement(achievementsList.skeletonKiller1);
		registerAchievement(achievementsList.skeletonKiller2);
		registerAchievement(achievementsList.skeletonKiller3);
		registerAchievement(achievementsList.spiderKiller1);
		registerAchievement(achievementsList.spiderKiller2);
		registerAchievement(achievementsList.spiderKiller3);
		registerAchievement(achievementsList.endermanKiller1);
		registerAchievement(achievementsList.endermanKiller2);
		registerAchievement(achievementsList.endermanKiller3);
		registerAchievement(achievementsList.creeperKiller1);
		registerAchievement(achievementsList.creeperKiller2);
		registerAchievement(achievementsList.creeperKiller3);
		registerAchievement(achievementsList.excaliburMaster);
		registerAchievement(achievementsList.mysteryMaster);
		registerAchievement(achievementsList.smith1);
		registerAchievement(achievementsList.smith2);
		registerAchievement(achievementsList.smith3);
	}

	public void callUseSpellEvent(Player p, Spell s) {
		PlayerUseSpellEvent event = new PlayerUseSpellEvent(p, s);
		Bukkit.getServer().getPluginManager().callEvent(event);
	}
	
	public void loadRpgItems() {
		//ItemsDB.load();
	}

	public void initRpgItems(Player p) {
		//excalibur.initSeconds(p);
		//treeAxe.initSeconds(p);
		//iceBattleAxe.initSeconds(p);
		//zeusSword.initSeconds(p);
		//crossbow.initSeconds(p);
	//	crossbow.initUses(p);
	}
	/*
	 * public Backpack getBackpack(Player p) { return
	 * backpacks.get(RCUtils.textedUUID(p)); }
	 * 
	 * public void addBackpack(Player p) {
	 * 
	 * if(!backpacks.containsKey(RCUtils.textedUUID(p)) || getBackpack(p) ==
	 * null) { try { backpacks.put(RCUtils.textedUUID(p), new Backpack(p,
	 * Backpack.getLinesFromFile(p))); } catch (IOException e) {
	 * e.printStackTrace(); } } }
	 * 
	 * public void removeBackpack(Player p) {
	 * if(backpacks.containsKey(RCUtils.textedUUID(p))) {
	 * backpacks.remove(RCUtils.textedUUID(p)); } }
	 */
	
	public void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {

		try {
			Object e;
			Object chatTitle;
			Object chatSubtitle;
			Constructor<?> subtitleConstructor;
			Object titlePacket;
			Object subtitlePacket;

			if (title != null) {
				title = ChatColor.translateAlternateColorCodes('&', title);
				title = title.replaceAll("%player%", player.getDisplayName());
				// Times packets
				e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object) null);
				chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + title + "\"}"});
				subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[]{getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
				titlePacket = subtitleConstructor.newInstance(new Object[]{e, chatTitle, fadeIn, stay, fadeOut});
				sendPacket(player, titlePacket);

				e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get((Object) null);
				chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + title + "\"}"});
				subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[]{getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent")});
				titlePacket = subtitleConstructor.newInstance(new Object[]{e, chatTitle});
				sendPacket(player, titlePacket);
			}

			if (subtitle != null) {
				subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
				subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
				// Times packets
				e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object) null);
				chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + title + "\"}"});
				subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[]{getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
				subtitlePacket = subtitleConstructor.newInstance(new Object[]{e, chatSubtitle, fadeIn, stay, fadeOut});
				sendPacket(player, subtitlePacket);

				e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get((Object) null);
				chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + subtitle + "\"}"});
				subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[]{getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE});
				subtitlePacket = subtitleConstructor.newInstance(new Object[]{e, chatSubtitle, fadeIn, stay, fadeOut});
				sendPacket(player, subtitlePacket);
			}
		} catch (Exception var11) {
			var11.printStackTrace();
		}
	}
	
	public void sendPacket(Player player, Object packet) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void makeDefaultMobFiles() {
		File z = new File("plugins/RankCraft/mobs/DEFAULT_ZOMBIE.yml");
		if(!z.exists()) {
			z.getParentFile().mkdirs();
			MainCore.instance.saveResource("mobs/DEFAULT_ZOMBIE.yml", false);
		}
		
		File s = new File("plugins/RankCraft/mobs/DEFAULT_SKELETON.yml");
		if(!s.exists()) {
			s.getParentFile().mkdirs();
			MainCore.instance.saveResource("mobs/DEFAULT_SKELETON.yml", false);
		}
		
	}
	
	public void reloadYamlMobFiles() {
		mobFiles.clear();
		mobs.clear();
		
		for(File f : (new File("plugins/RankCraft/mobs")).listFiles()) {
			mobFiles.add(f.getName());
		}
		for(String s : mobFiles) {
			YamlMob ym = new YamlMob(s);
			mobs.add(ym);
		}
	}
	
	public void makeYamlMobLists() {
		yamlZombies.clear();
		yamlSkeletons.clear();
		
		for(YamlMob y : mobs) {
			if(y.getType().equals("ZOMBIE")) {
				yamlZombies.add(y);
			} else if(y.getType().equals("SKELETON")) {
				yamlSkeletons.add(y);
			}
		}
		
	}
	
	public YamlItem getYamlWeaponFromFileName(String fileName) {
		for(YamlItem i : yamlWeapons) {
			if(i.getFileName().equals(fileName)) {
				return i;
			}
		}
		return null;
	}
	
	public YamlArmor getYamlArmorFromFileName(String fileName) {
		for(YamlArmor i : yamlArmors) {
			if(i.getFileName().equals(fileName)) {
				return i;
			}
		}
		return null;
	}
	
	public YamlMob getYamlMobFromFileName(String fileName) {
		for(YamlMob y : mobs) {
			if(y.getFileName().equalsIgnoreCase(fileName)) {
				return y;
			}
		}
		return null;
	}
	
	public void reloadMobSpawners() {
		mobSpawners.clear();
		
		for(File f : (new File("plugins/RankCraft/spawners")).listFiles()) {
			MobSpawner spawner = new MobSpawner(f.getName());
			mobSpawners.add(spawner);
		}
	}
	
	public void initRpgClasses() {
		rpgClasses.clear();
		
		gladiatorClass = new GladiatorClass(ChatColor.GOLD+"Gladiator", 10);
		rpgClasses.add(gladiatorClass);
		
		archerClass = new ArcherClass(ChatColor.BLUE+"Archer", 10);
		rpgClasses.add(archerClass);
		
		ninjaClass = new NinjaClass(ChatColor.DARK_GRAY+"Ninja", 10);
		rpgClasses.add(ninjaClass);
		
		wizardClass = new WizardClass(ChatColor.LIGHT_PURPLE+"Wizard", 10);
		rpgClasses.add(wizardClass);
		
		mysteriousClass = new MysteriousClass(ChatColor.DARK_PURPLE+"Mysterious", 10);
		rpgClasses.add(mysteriousClass);
		
	}
	
	public void updateLeaderboards() {
		levelLeaderboard.update();
		blocksBrokenLeaderboard.update();
		blocksPlacedLeaderboard.update();
	}
	
	public void addCommandDescription(String c, String d, Permission p, boolean l) {
		commandDescriptions.add(new CommandDescription(c, d, p, l));
	}
	
	public void initTreasureChests() {
		treasureChests.clear();
		//if(treasureChests.isEmpty()) {
		//System.out.println("Initializing Treasures...");
		MainCore.instance.getLogger().info("Initializing treasures...");	
			YamlParser y;
			FileConfiguration c;
			
			for(File f : RCUtils.TREASURES2_DIRECTORY.listFiles()) {
				if(f.getName().endsWith(".yml")) {
					y = new YamlParser(RCUtils.TREASURES2_DIRECTORY, f.getName(), "treasures2");
					c = y.getConfig();
					
					String name = c.getString("Name");
					String fName = getFileNameWithoutExtension(f);
					String rarity = c.getString("Rarity");
					
					List<String> inventory = c.getStringList("items");
					//System.out.println("============");
					//System.out.println(c.getString("Name"));
					//System.out.println(getFileNameWithoutExtension(f));
					//System.out.println(c.getString("Rarity"));
					if(name != null && fName != null && rarity != null && inventory != null) {
						treasureChests.add(new TreasureChest2(name, fName, TreasureChest2.Rarity.valueOf(rarity.toUpperCase())));
						//System.out.println("Treasure made! ("+f.getName()+")");
					} /*else {
						System.out.println("Treasure NULLED! ("+f.getName()+")");
					}*/
				}
			}
			
		//System.out.println("Successfully initialized treasures: "+treasureChests.size());
		MainCore.instance.getLogger().info("Successfully initialized treasures: "+treasureChests.size());
		//} else {
		//	System.out.println("TreasureChests2 are already initialiazed!");
		//}
	}
	
	public List<TreasureChest2> getCommonTreasures() {
		List<TreasureChest2> list = new ArrayList<>();
		for(TreasureChest2 tc2 : treasureChests) {
			if(tc2.getRarity() == Rarity.COMMON) {
				list.add(tc2);
			}
		}
		return list;
	}
	
	public List<TreasureChest2> getRareTreasures() {
		List<TreasureChest2> list = new ArrayList<>();
		for(TreasureChest2 tc2 : treasureChests) {
			if(tc2.getRarity() == Rarity.RARE) {
				list.add(tc2);
			}
		}
		return list;
	}
	
	public List<TreasureChest2> getEpicTreasures() {
		List<TreasureChest2> list = new ArrayList<>();
		for(TreasureChest2 tc2 : treasureChests) {
			if(tc2.getRarity() == Rarity.EPIC) {
				list.add(tc2);
			}
		}
		return list;
	}
	
	private String getFileNameWithoutExtension(File file) {
        String fileName = "";
 
        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                fileName = name.replaceFirst("[.][^.]+$", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fileName = "";
        }
 
        return fileName;
 
    }
	
	public TreasureChest2 getTreasureByItem(ItemStack item) {
		
		for(TreasureChest2 tc2 : treasureChests) {
			if(item.isSimilar(tc2.build().getItem())) {
				return tc2;
			}
		}
		
		return null;
	}
	
	public TreasureChest2 getTreasureById(String id) {
		
		for(TreasureChest2 tc2 : treasureChests) {
			if(tc2.getFile().getName().equalsIgnoreCase(id+".yml")) {
				return tc2;
			}
		}
		
		return null;
	}
	
	private void makeDefaultTreasure2(String name) {
		File file = new File(RCUtils.TREASURES2_DIRECTORY +"/"+name);
		
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			MainCore.instance.saveResource("treasures2/" + name, false);
		}
	}
	
	private void addSpellCaseRecipes() {
		
		SpellCase ssc = new SpellCase(CaseType.Small);
		ShapedRecipe recipe = new ShapedRecipe(RCUtils.getKeyForRecipe("small_spellcase"), ssc.build().getItem());
		recipe.shape("BCB");
		recipe.setIngredient('B', Material.PAPER);
		recipe.setIngredient('C', Material.CHEST);
		MainCore.instance.getServer().addRecipe(recipe);
		
		SpellCase msc = new SpellCase(CaseType.Medium);
		ShapedRecipe m_recipe = new ShapedRecipe(RCUtils.getKeyForRecipe("medium_spellcase"), msc.build().getItem());
		m_recipe.shape(" B ",
					 "BCB",
					 " B ");
		m_recipe.setIngredient('B', Material.PAPER);
		m_recipe.setIngredient('C', Material.CHEST);
		MainCore.instance.getServer().addRecipe(m_recipe);
		//Bukkit.addRecipe(m_recipe);
		
		SpellCase lsc = new SpellCase(CaseType.Large);
		ShapedRecipe l_recipe = new ShapedRecipe(RCUtils.getKeyForRecipe("large_spellcase"), lsc.build().getItem());
		l_recipe.shape("BBB","BCB","BBB");
		l_recipe.setIngredient('B', Material.PAPER);
		l_recipe.setIngredient('C', Material.CHEST);
		MainCore.instance.getServer().addRecipe(l_recipe);
		
	}
	
	public boolean yamlFilenameExists(String id) {
		for(YamlItem w : yamlWeapons) 
			if(w.getFileName().equals(id))
				return true;
		for(YamlArmor a : yamlArmors) 
			if(a.getFileName().equals(id+".armor"))
				return true;
		return false;
	}
	
	public boolean isYamlWeapon(String id) {
		for(YamlItem w : yamlWeapons) 
			if(w.getFileName().equals(id)) 
				return true;
		return false;
	}
	
}
