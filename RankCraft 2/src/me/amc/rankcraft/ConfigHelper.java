package me.amc.rankcraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import me.amc.rankcraft.utils.Reward;

public class ConfigHelper {

	private FileConfiguration config;
	
	// Integer variables
	/*
	public int treasurePerBlocks;
	public int normalTreasureChances;
	public int superTreasureChances;
	public int ultraTreasureChances;
	public int normalEnchantChances;
	public int superEnchantChances;
	public int ultraEnchantChances;
	public int moreEnchantChances;
	*/
	public int dropRpgItemChances;
	public int dropRpgItemRadius;
	public int shopRefresh;
	public int treasure2PerBlocks;
	public int commonChances;
	public int rareChances;
	public int epicChances;
	public int playerRange;
	public int blocksToSave;
	public int searchRange;
	public int searchAmount;
	public int removeWebSeconds;
	
	// Lists and HashMaps
	public List<Material> noXpMaterials = new ArrayList<>();
	public List<Material> noTreasureMaterials = new ArrayList<>();
	public List<String> enableOnWorlds;
	public HashMap<Material, Float> specialPlace = new HashMap<>();
	public HashMap<Material, Float> specialBreak = new HashMap<>();
	private List<String> currencyItemLore = new ArrayList<>();
	public List<String> weaponsToRpg = new ArrayList<>();
	public List<String> armorsToRpg = new ArrayList<>();
	
	// Float variables
	public float xpOnPlace;
	public float xpOnBreak;
	public float bronzeBlockPackCost;
	public float silverBlockPackCost;
	public float goldBlockPackCost;
	public float bronzeItemPackCost;
	public float silverItemPackCost;
	public float goldItemPackCost;
	public float bronzeMixedPackCost;
	public float silverMixedPackCost;
	public float goldMixedPackCost;
	public float levelUpGold;
	public float mysteriousClassGoldCost;
	public float startingHP;
	public float fullHeartHP;
	public float heartsMaxHP;
	
	// Boolean variables
	public boolean startupDebug;
	public boolean enableScoreboard;
	public boolean makeAutoRpg;
	public boolean keepBackpack;
	public boolean useVault;
	public boolean canDropRpgItem;
	public boolean enableShop;
	public boolean enableCustomMobs;
	public boolean enablePreMadeMobs;
	public boolean enableWorldList;
	public boolean enableTreasureChest;
	//public boolean personalTreasureChestMessage;
	//public boolean ultraTreasureChestMessage;
	public boolean enableActionbar;
	public boolean enableDamageSystem;
	public boolean enableHPSystem;
	public boolean treasure2FoundMessage;
	public boolean saveBlocks;
	public boolean sendOutdateMessage;
	public boolean resetXp;
	public boolean resetSkills;
	public boolean resetHp;
	public boolean resetGold;
	public boolean autoPickupGold;
	public boolean disableClassicHearts;
	public boolean alwaysDisplayName;
	public boolean displayClassParticles;
	public boolean disableSpells;
	public boolean spawnerSearch;
	public boolean removeWeb;
	public boolean displayDamage;
	
	// String variables
	public String langFile;	
	public String guiLangFile;
	public String classItemBlock;
	public String actionbarText;
	public String itemLangFile;
	public String searchCriteria;
	public String currencyName;
	public Material currencyItemType;
	
	// Double variables
	public double levelUpHealth;
	public double sharpnessFactor;
	public double minCriticalHit;
	public double maxCriticalHit;
	public double attackSkillFactor;
	public double strengthFactor;
	public double defenseSkillFactor;
	public double protectionFactor;
	
	public ConfigHelper(FileConfiguration config) {
		this.config = config;
		initVariables();
	}
	
	private void initVariables() {
		/*
		treasurePerBlocks = config.getInt("TreasurePerBlocks");
		normalTreasureChances = config.getInt("NormalTreasureChances");
		superTreasureChances = config.getInt("SuperTreasureChances");
		ultraTreasureChances = config.getInt("UltraTreasureChances");
		*/
		for(String s : config.getStringList("NoXpMaterials")) {
			noXpMaterials.add(Material.valueOf(s));
		}
		xpOnBreak = (float) config.getDouble("XpOnBreak");
		xpOnPlace = (float) config.getDouble("XpOnPlace");
		
		List<String> sBreak = config.getStringList("SpecialBreak");
		for(String s : sBreak) {
			String[] parts = s.split(":");
			String material = parts[0];
			String xp = parts[1];
			float xpFixed = xpOnBreak;
			try {
				xpFixed = (float) Double.parseDouble(xp);
			} catch(Exception e) {
				System.out.println("Error on reading "+s+" of SpecialBreak list !");
			}
			specialBreak.put(Material.valueOf(material), xpFixed);
		}
	//	System.out.println(specialBreak);
		
		List<String> sPlace = config.getStringList("SpecialPlace");
		for(String s : sPlace) {
			String[] parts = s.split(":");
			String material = parts[0];
			String xp = parts[1];
			float xpFixed = xpOnPlace;
			try {
				xpFixed = (float) Double.parseDouble(xp);
			} catch(Exception e) {
				System.out.println("Error on reading "+s+" of SpecialPlace list !");
			}
			specialPlace.put(Material.valueOf(material), xpFixed);
		}
	//	System.out.println(specialPlace);
		/*
		normalEnchantChances = config.getInt("NormalEnchantChances");
		superEnchantChances = config.getInt("SuperEnchantChances");
		ultraEnchantChances = config.getInt("UltraEnchantChances");
		moreEnchantChances = config.getInt("MoreEnchantChances");
		*/
		bronzeBlockPackCost = (float)config.getDouble("BronzeBlockPackCost");
		silverBlockPackCost = (float)config.getDouble("SilverBlockPackCost");
		goldBlockPackCost = (float)config.getDouble("GoldBlockPackCost");
		bronzeItemPackCost = (float)config.getDouble("BronzeItemPackCost");
		silverItemPackCost = (float)config.getDouble("SilverItemPackCost");
		goldItemPackCost = (float)config.getDouble("GoldItemPackCost");
		bronzeMixedPackCost = (float)config.getDouble("BronzeMixedPackCost");
		silverMixedPackCost = (float)config.getDouble("SilverMixedPackCost");
		goldMixedPackCost = (float)config.getDouble("GoldMixedPackCost");
		
		startupDebug = config.getBoolean("StartupDebug");
		
		levelUpGold = (float)config.getDouble("LevelUp.Gold");
		levelUpHealth = config.getDouble("LevelUp.Health");
		
		enableScoreboard = config.getBoolean("EnableScoreboard");
		
		langFile = config.getString("LangFile");
		
		shopRefresh = config.getInt("ShopRefresh");
		
		makeAutoRpg = config.getBoolean("MakeAutoRpg");
		
		keepBackpack = config.getBoolean("KeepBackpack");
		
		useVault = config.getBoolean("UseVault");
		
		guiLangFile = config.getString("GuiLangFile");
		
		canDropRpgItem = config.getBoolean("CanDropRpgItem");
		dropRpgItemChances = config.getInt("DropRpgItemChances");
		dropRpgItemRadius = config.getInt("DropRpgItemRadius");
		
		enableShop = config.getBoolean("EnableShop");
		enableCustomMobs = config.getBoolean("EnableCustomMobs");
		enablePreMadeMobs = config.getBoolean("EnablePreMadeMobs");
		
		enableWorldList = config.getBoolean("EnableWorldList");
		enableOnWorlds = config.getStringList("EnableOnWorlds");
		mysteriousClassGoldCost = (float) config.getDouble("MysteriousClassGoldCost");
		classItemBlock = config.getString("ClassItemBlock");
		
		enableTreasureChest = config.getBoolean("EnableTreasureChest");
		//personalTreasureChestMessage = config.getBoolean("PersonalTreasureChestMessage");
		//ultraTreasureChestMessage = config.getBoolean("UltraTreasureMessage");
		
		enableActionbar = config.getBoolean("EnableActionbar");
		actionbarText = config.getString("ActionbarText");
		
		enableDamageSystem = config.getBoolean("EnableDamageSystem");
		enableHPSystem = config.getBoolean("EnableHPSystem");
		
		treasure2PerBlocks = config.getInt("Treasure2PerBlocks");
		commonChances = config.getInt("CommonChances");
		rareChances = config.getInt("RareChances");
		epicChances = config.getInt("EpicChances");
		treasure2FoundMessage = config.getBoolean("Treasure2FoundMessage");
		
		for(String s : config.getStringList("NoTreasureMaterials")) {
			noTreasureMaterials.add(Material.valueOf(s));
		}
		
		playerRange = config.getInt("PlayerRange");
		
		blocksToSave = config.getInt("BlocksToSave");
		saveBlocks = config.getBoolean("SaveBlocks");
		
		sendOutdateMessage = config.getBoolean("SendOutdateMessage");
		
		sharpnessFactor = config.getDouble("SharpnessFactor");
		minCriticalHit = config.getDouble("MinCriticalHit");
		maxCriticalHit = config.getDouble("MaxCriticalHit");
		attackSkillFactor = config.getDouble("AttackSkillFactor");
		strengthFactor = config.getDouble("StrengthFactor");
		defenseSkillFactor = config.getDouble("DefenseSkillFactor");
		protectionFactor = config.getDouble("ProtectionFactor");
		
		resetXp = config.getBoolean("ResetXpOnDeath");
		resetSkills = config.getBoolean("ResetSkillsOnDeath");
		resetHp = config.getBoolean("ResetHpOnDeath");
		resetGold = config.getBoolean("ResetGoldOnDeath");
		
		autoPickupGold = config.getBoolean("AutoPickupGold");
		
		itemLangFile = config.getString("ItemLangFile");
		
		disableClassicHearts = config.getBoolean("DisableClassicHearts");
		
		alwaysDisplayName = config.getBoolean("AlwaysDisplayName");
		displayClassParticles = config.getBoolean("DisplayClassParticles");
		
		disableSpells = config.getBoolean("DisableSpells");
		
		spawnerSearch = config.getBoolean("SpawnerSearch");
		searchRange = config.getInt("SearchRange");
		searchCriteria = config.getString("SearchCriteria");
		searchAmount = config.getInt("SearchAmount");
		
		currencyName = config.getString("CurrencyName").replace('&', '§');
		try {
			currencyItemType = Material.getMaterial(config.getString("CurrencyItemType"));
		} catch(Exception ex) {
			currencyItemType = Material.GOLD_NUGGET;
			MainCore.instance.sendError("Invalid CurrencyTypeItem in config.yml! Setting it to default value!");
		}
		for(String s : config.getStringList("CurrencyItemLore")) {
			currencyItemLore.add(s.replace('&', '§'));
		}
		
		removeWeb = config.getBoolean("RemoveWeb");
		removeWebSeconds = config.getInt("RemoveWebSeconds");
		
		displayDamage = config.getBoolean("DisplayDamage");
		
		weaponsToRpg = config.getStringList("WeaponsToRPG");
		armorsToRpg = config.getStringList("ArmorsToRPG");
		
		startingHP = (float)config.getDouble("StartingHP");
		fullHeartHP = (float)config.getDouble("FullHeartHP");
		
		heartsMaxHP = (float)config.getDouble("HeartsMaxHP");
		
	}
	
	public boolean isSpellEnabled(String spellName) {
		if(config.get("EnabledSpells."+spellName) == null)  {
			System.out.println(spellName+ " not found!");
			return false;
		}
		return config.getBoolean("EnabledSpells."+spellName) && !disableSpells;
	}
	
	public float getBackpackUpgradeCost(int line) {
		if(line >= 2 && line <= 6) {
			return (float)config.getDouble("BackpackUpgradeCost.lines"+line);
		} else {
			return (float)config.getDouble("BackpackUpgradeCost.lines9");
		}
	}
	
	public Reward getRewardForAchievement(String a, int lvl) {
		String line = config.getString("AchievementRewards."+a+""+lvl);
		String[] parts = line.split(",");
		double xp = 0, gold = 0;
		try {
			xp = Double.parseDouble(parts[0]);
			gold = Double.parseDouble(parts[1]);
		}catch(Exception io) {
			MainCore.instance.sendError(a+""+lvl+" reward is not correctly set in configuration file!");
		}
		return new Reward((float)xp, (float)gold);
	}
	
	public int getDefaultWeaponMinLevel(Material weapon) {
		String path = "DefaultWeapons."+weapon.name()+".MinLevel";
		
		int m = 0;
		if(config.contains(path)) 
			m = config.getInt(path);
		
		return m;
	}
	
	public float getDefaultWeaponDamage(Material weapon, boolean minDamage) {
		String prefix = "Max";
		if(minDamage) prefix = "Min";
		String path = "DefaultWeapons."+weapon.name()+"."+prefix+"Damage";
		
		float m = 0f;
		if(config.contains(path))
			m = (float)config.getDouble(path);
		
		return m;
	}
	
	public float getDefaultWeaponMinDamage(Material weapon) {
		String path = "DefaultWeapons."+weapon.name()+".MinDamage";
		
		float m = 0f;
		if(config.contains(path))
			m = (float)config.getDouble(path);
		
		return m;
	}
	
	public float getDefaultWeaponMaxDamage(Material weapon) {
		String path = "DefaultWeapons."+weapon.name()+".MaxDamage";
		
		float m = 0f;
		if(config.contains(path)) 
			m = (float)config.getDouble(path);
				
		return m;
	}
	
	public double getDefaultArmorForMaterial(Material mat) {
		String path = "DefaultArmors."+mat.name()+".Armor";
		double a = 0.0;
		if(config.contains(path))
			a = config.getDouble(path);
		return a;
	}
	
	public int getDefaultArmorMinLevel(Material mat) {
		String path = "DefaultArmors."+mat.name()+".MinLevel";
		int l = 0;
		if(config.contains(path))
			l = config.getInt(path);
		return l;
	}
	
	public List<String> getCurrencyItemLore(float amount) {
		List<String> trueList = new ArrayList<>();
		for(String s : currencyItemLore) 
			trueList.add(s.replace("{Amount}", ""+amount));
		return trueList;
	}
	
	public List<Material> getMaterialWeaponsToRpg() {
		List<Material> m = new ArrayList<>();
		for(String s : weaponsToRpg) 
			m.add(Material.getMaterial(s));
		return m;
	}
	
	public List<Material> getMaterialArmorsToRpg() {
		List<Material> m = new ArrayList<>();
		for(String s : armorsToRpg) 
			m.add(Material.getMaterial(s));
		return m;
	}
	
}
