package me.amc.rankcraft.classes;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.customevents.ChangeRpgClassEvent;
import me.amc.rankcraft.customevents.RpgClassLevelUpEvent;
import me.amc.rankcraft.utils.RCUtils;

public class RpgClassData {

	public static HashMap<String, String> currentClass = new HashMap<>();
	public static HashMap<String, Integer> classPoints = new HashMap<>();
	
	// Gladiator Class
	public static HashMap<String, Boolean> unlockGladiator = new HashMap<>();
	public static HashMap<String, Integer> gladiatorLevel = new HashMap<>();
	public static HashMap<String, Double> gladiatorXp = new HashMap<>();
	
	// Archer Class
	public static HashMap<String, Boolean> unlockArcher = new HashMap<>();
	public static HashMap<String, Integer> archerLevel = new HashMap<>();
	public static HashMap<String, Double> archerXp = new HashMap<>();
	
	// Ninja Class
	public static HashMap<String, Boolean> unlockNinja = new HashMap<>();
	public static HashMap<String, Integer> ninjaLevel = new HashMap<>();
	public static HashMap<String, Double> ninjaXp = new HashMap<>();
	
	// Wizard Class
	public static HashMap<String, Boolean> unlockWizard = new HashMap<>();
	public static HashMap<String, Integer> wizardLevel = new HashMap<>();
	public static HashMap<String, Double> wizardXp = new HashMap<>();
	
	// Mysterious Class
	public static HashMap<String, Boolean> unlockMysterious = new HashMap<>();
	public static HashMap<String, Integer> mysteriousLevel = new HashMap<>();
	public static HashMap<String, Double> mysteriousXp = new HashMap<>();
	
	public static void setUnlockGladiator(Player p, boolean unlock) {
		unlockGladiator.put(RCUtils.textedUUID(p), unlock);
	}
	
	public static void setGladiatorLevel(Player p, int lvl) {
		
		if(lvl < MainCore.instance.rankCraft.gladiatorClass.getMaxLevel()) {
			
			if(gladiatorLevel.containsKey(RCUtils.textedUUID(p))) {
		//		System.out.println(getGladiatorLevel(p)+"    "+lvl);
				if(getGladiatorLevel(p) != lvl) {
					RpgClassLevelUpEvent event = new RpgClassLevelUpEvent(p, MainCore.instance.rankCraft.gladiatorClass, getGladiatorLevel(p), lvl);
					Bukkit.getServer().getPluginManager().callEvent(event);
	//				System.out.println("event called");
				}
			}
			gladiatorLevel.put(RCUtils.textedUUID(p), lvl);
		} else {
			
			RpgClassLevelUpEvent event = new RpgClassLevelUpEvent(p, MainCore.instance.rankCraft.gladiatorClass, getGladiatorLevel(p), lvl);
			Bukkit.getServer().getPluginManager().callEvent(event);
			
			gladiatorLevel.put(RCUtils.textedUUID(p), MainCore.instance.rankCraft.gladiatorClass.getMaxLevel());
			
		}
	}
	
	public static void setGladiatorXp(Player p, double xp) {
		if(xp < MainCore.instance.rankCraft.gladiatorClass.getXpToLevelUp(getGladiatorLevel(p))) {
			gladiatorXp.put(RCUtils.textedUUID(p), xp);
		} else {
			gladiatorXp.put(RCUtils.textedUUID(p), 0D);
			setGladiatorLevel(p, getGladiatorLevel(p) + 1);
			save();
		}
	}
	
	// ============================================ Archer ===============================================
	
	public static void setUnlockArcher(Player p, boolean unlock) {
		unlockArcher.put(RCUtils.textedUUID(p), unlock);
	}
	
	public static void setArcherLevel(Player p, int lvl) {
		
		if(lvl < MainCore.instance.rankCraft.archerClass.getMaxLevel()) {
			
			if(archerLevel.containsKey(RCUtils.textedUUID(p))) {
		//		System.out.println(getGladiatorLevel(p)+"    "+lvl);
				if(getArcherLevel(p) != lvl) {
					RpgClassLevelUpEvent event = new RpgClassLevelUpEvent(p, MainCore.instance.rankCraft.archerClass, getArcherLevel(p), lvl);
					Bukkit.getServer().getPluginManager().callEvent(event);
		//			System.out.println("event called");
				}
			}
			archerLevel.put(RCUtils.textedUUID(p), lvl);
		} else {
			
			RpgClassLevelUpEvent event = new RpgClassLevelUpEvent(p, MainCore.instance.rankCraft.archerClass, getArcherLevel(p), lvl);
			Bukkit.getServer().getPluginManager().callEvent(event);
			
			archerLevel.put(RCUtils.textedUUID(p), MainCore.instance.rankCraft.archerClass.getMaxLevel());
			
		}
	}
	
	public static void setArcherXp(Player p, double xp) {
		if(xp < MainCore.instance.rankCraft.archerClass.getXpToLevelUp(getArcherLevel(p))) {
			archerXp.put(RCUtils.textedUUID(p), xp);
		} else {
			archerXp.put(RCUtils.textedUUID(p), 0D);
			setArcherLevel(p, getArcherLevel(p) + 1);
			save();
		}
	}
	
	// ============================================================== Ninja =================================================================

	
	public static void setUnlockNinja(Player p, boolean unlock) {
		unlockNinja.put(RCUtils.textedUUID(p), unlock);
	}
	
	public static void setNinjaLevel(Player p, int lvl) {
		
		if(lvl < MainCore.instance.rankCraft.ninjaClass.getMaxLevel()) {
			
			if(ninjaLevel.containsKey(RCUtils.textedUUID(p))) {
		//		System.out.println(getGladiatorLevel(p)+"    "+lvl);
				if(getNinjaLevel(p) != lvl) {
					RpgClassLevelUpEvent event = new RpgClassLevelUpEvent(p, MainCore.instance.rankCraft.ninjaClass, getNinjaLevel(p), lvl);
					Bukkit.getServer().getPluginManager().callEvent(event);
		//			System.out.println("event called");
				}
			}
			ninjaLevel.put(RCUtils.textedUUID(p), lvl);
		} else {
			
			RpgClassLevelUpEvent event = new RpgClassLevelUpEvent(p, MainCore.instance.rankCraft.ninjaClass, getNinjaLevel(p), lvl);
			Bukkit.getServer().getPluginManager().callEvent(event);
			
			archerLevel.put(RCUtils.textedUUID(p), MainCore.instance.rankCraft.ninjaClass.getMaxLevel());
			
		}
	}
	
	public static void setNinjaXp(Player p, double xp) {
		if(xp < MainCore.instance.rankCraft.ninjaClass.getXpToLevelUp(getNinjaLevel(p))) {
			ninjaXp.put(RCUtils.textedUUID(p), xp);
		} else {
			ninjaXp.put(RCUtils.textedUUID(p), 0D);
			setNinjaLevel(p, getNinjaLevel(p) + 1);
			save();
		}
	}
	
	
	// ============================================================ Wizard ==========================================
	
	public static void setUnlockWizard(Player p, boolean unlock) {
		unlockWizard.put(RCUtils.textedUUID(p), unlock);
	}
	
	public static void setWizardLevel(Player p, int lvl) {
		
		if(lvl < MainCore.instance.rankCraft.wizardClass.getMaxLevel()) {
			
			if(wizardLevel.containsKey(RCUtils.textedUUID(p))) {
		//		System.out.println(getGladiatorLevel(p)+"    "+lvl);
				if(getWizardLevel(p) != lvl) {
					RpgClassLevelUpEvent event = new RpgClassLevelUpEvent(p, MainCore.instance.rankCraft.wizardClass, getWizardLevel(p), lvl);
					Bukkit.getServer().getPluginManager().callEvent(event);
		//			System.out.println("event called");
				}
			}
			wizardLevel.put(RCUtils.textedUUID(p), lvl);
		} else {
			
			RpgClassLevelUpEvent event = new RpgClassLevelUpEvent(p, MainCore.instance.rankCraft.wizardClass, getWizardLevel(p), lvl);
			Bukkit.getServer().getPluginManager().callEvent(event);
			
			archerLevel.put(RCUtils.textedUUID(p), MainCore.instance.rankCraft.wizardClass.getMaxLevel());
			
		}
	}
	
	public static void setWizardXp(Player p, double xp) {
		if(xp < MainCore.instance.rankCraft.wizardClass.getXpToLevelUp(getWizardLevel(p))) {
			wizardXp.put(RCUtils.textedUUID(p), xp);
		} else {
			wizardXp.put(RCUtils.textedUUID(p), 0D);
			setWizardLevel(p, getWizardLevel(p) + 1);
			save();
		}
	}
	
	// ==============================================================================================================
	
	// ===================================================[MysteriousClass]================================================
	
	public static void setUnlockMysterious(Player p, boolean unlock) {
		unlockMysterious.put(RCUtils.textedUUID(p), unlock);
	}
	
	public static void setMysteriousLevel(Player p, int lvl) {
		
		if(lvl < MainCore.instance.rankCraft.mysteriousClass.getMaxLevel()) {
			
			if(mysteriousLevel.containsKey(RCUtils.textedUUID(p))) {
		//		System.out.println(getGladiatorLevel(p)+"    "+lvl);
				if(getMysteriousLevel(p) != lvl) {
					RpgClassLevelUpEvent event = new RpgClassLevelUpEvent(p, MainCore.instance.rankCraft.mysteriousClass, getMysteriousLevel(p), lvl);
					Bukkit.getServer().getPluginManager().callEvent(event);
		//			System.out.println("event called");
				}
			}
			mysteriousLevel.put(RCUtils.textedUUID(p), lvl);
		} else {
			
			RpgClassLevelUpEvent event = new RpgClassLevelUpEvent(p, MainCore.instance.rankCraft.mysteriousClass, getMysteriousLevel(p), lvl);
			Bukkit.getServer().getPluginManager().callEvent(event);
			
			archerLevel.put(RCUtils.textedUUID(p), MainCore.instance.rankCraft.mysteriousClass.getMaxLevel());
			
		}
	}
	
	public static void setMysteriousXp(Player p, double xp) {
		if(xp < MainCore.instance.rankCraft.mysteriousClass.getXpToLevelUp(getMysteriousLevel(p))) {
			mysteriousXp.put(RCUtils.textedUUID(p), xp);
		} else {
			mysteriousXp.put(RCUtils.textedUUID(p), 0D);
			setMysteriousLevel(p, getMysteriousLevel(p) + 1);
			save();
		}
	}
	
	
	
	// ====================================================================================================================
	public static void setCurrentClass(Player p, String id) {
		
		RpgClass oldClass = RCUtils.getRpgClassById(getCurrentClass(p));
		RpgClass newClass = RCUtils.getRpgClassById(id);
		
		currentClass.put(RCUtils.textedUUID(p), id);
		
		ChangeRpgClassEvent event = new ChangeRpgClassEvent(p, oldClass, newClass);
		Bukkit.getServer().getPluginManager().callEvent(event);
		
	}
	
	public static void setClassPoints(Player p, int pts) {
		classPoints.put(RCUtils.textedUUID(p), pts);
	}
	
	public static boolean getUnlockGladiator(Player p) {
		return unlockGladiator.get(RCUtils.textedUUID(p));
	}
	
	public static int getGladiatorLevel(Player p) {
		return gladiatorLevel.get(RCUtils.textedUUID(p));
	}
	
	public static double getGladiatorXp(Player p) {
		return gladiatorXp.get(RCUtils.textedUUID(p));
	}
	
	public static boolean getUnlockArcher(Player p) {
		return unlockArcher.get(RCUtils.textedUUID(p));
	}
	
	public static int getArcherLevel(Player p) {
		return archerLevel.get(RCUtils.textedUUID(p));
	}
	
	public static double getArcherXp(Player p) {
		return archerXp.get(RCUtils.textedUUID(p));
	}
	
	public static String getCurrentClass(Player p) {
		return currentClass.get(RCUtils.textedUUID(p));
	}
	
	public static int getClassPoints(Player p) {
		return classPoints.get(RCUtils.textedUUID(p));
	}
	
	public static boolean getUnlockNinja(Player p) {
		return unlockNinja.get(RCUtils.textedUUID(p));
	}
	
	public static int getNinjaLevel(Player p) {
		return ninjaLevel.get(RCUtils.textedUUID(p));
	}
	
	public static double getNinjaXp(Player p) {
		return ninjaXp.get(RCUtils.textedUUID(p));
	}
	
	public static boolean getUnlockWizard(Player p) {
		return unlockWizard.get(RCUtils.textedUUID(p));
	}
	
	public static int getWizardLevel(Player p) {
		return wizardLevel.get(RCUtils.textedUUID(p));
	}
	
	public static double getWizardXp(Player p) {
		return wizardXp.get(RCUtils.textedUUID(p));
	}
	
	public static boolean getUnlockMysterious(Player p) {
		return unlockMysterious.get(RCUtils.textedUUID(p));
	}
	
	public static int getMysteriousLevel(Player p) {
		return mysteriousLevel.get(RCUtils.textedUUID(p));
	}
	
	public static double getMysteriousXp(Player p) {
		return mysteriousXp.get(RCUtils.textedUUID(p));
	}
	
	public static void save() {

		try {

			YamlConfiguration stats = new YamlConfiguration();
			stats.createSection("CurrentClass", currentClass);
			stats.createSection("ClassPoints", classPoints);
			stats.save(new File(RCUtils.RPGCLASSES_DIRECTORY, "info.yml"));

			YamlConfiguration gladiator = new YamlConfiguration();
			gladiator.createSection("Unlock", unlockGladiator);
			gladiator.createSection("Level", gladiatorLevel);
			gladiator.createSection("Xp", gladiatorXp);
			gladiator.save(new File(RCUtils.RPGCLASSES_DIRECTORY, "gladiator.yml"));
			
			YamlConfiguration archer = new YamlConfiguration();
			archer.createSection("Unlock", unlockArcher);
			archer.createSection("Level", archerLevel);
			archer.createSection("Xp", archerXp);
			archer.save(new File(RCUtils.RPGCLASSES_DIRECTORY, "archer.yml"));
			
			YamlConfiguration ninja = new YamlConfiguration();
			ninja.createSection("Unlock", unlockNinja);
			ninja.createSection("Level", ninjaLevel);
			ninja.createSection("Xp", ninjaXp);
			ninja.save(new File(RCUtils.RPGCLASSES_DIRECTORY, "ninja.yml"));
			
			YamlConfiguration wizard = new YamlConfiguration();
			wizard.createSection("Unlock", unlockWizard);
			wizard.createSection("Level", wizardLevel);
			wizard.createSection("Xp", wizardXp);
			wizard.save(new File(RCUtils.RPGCLASSES_DIRECTORY, "wizard.yml"));
			
			YamlConfiguration mysterious = new YamlConfiguration();
			mysterious.createSection("Unlock", unlockMysterious);
			mysterious.createSection("Level", mysteriousLevel);
			mysterious.createSection("Xp", mysteriousXp);
			mysterious.save(new File(RCUtils.RPGCLASSES_DIRECTORY, "mysterious.yml"));
			
		} catch (Exception ex) {
			MainCore.instance.getLogger().log(Level.SEVERE, "[ClassesSaver] Save Failed!", ex);
		}

	}

	public static void load() {
		try {

			File stats = new File(RCUtils.RPGCLASSES_DIRECTORY, "info.yml");
			if (stats.exists()) {

				YamlConfiguration config = new YamlConfiguration();
				config.load(stats);

				ConfigurationSection sec = config.getConfigurationSection("CurrentClass");
				for (String key : sec.getKeys(false)) {
					currentClass.put(key, sec.getString(key));
				}
				
				sec = config.getConfigurationSection("ClassPoints");
				for (String key : sec.getKeys(false)) {
					classPoints.put(key, sec.getInt(key));
				}
				
			}
			
			File gladiator = new File(RCUtils.RPGCLASSES_DIRECTORY, "gladiator.yml");
			if (gladiator.exists()) {

				YamlConfiguration config = new YamlConfiguration();
				config.load(gladiator);

				ConfigurationSection sec = config.getConfigurationSection("Unlock");
				for (String key : sec.getKeys(false)) {
					unlockGladiator.put(key, sec.getBoolean(key));
				}
				
				sec = config.getConfigurationSection("Level");
				for(String key : sec.getKeys(false)) {
					gladiatorLevel.put(key, sec.getInt(key));
				}
				
				sec = config.getConfigurationSection("Xp");
				for(String key : sec.getKeys(false)) {
					gladiatorXp.put(key, sec.getDouble(key));
				}
				
			}
			
			File archer = new File(RCUtils.RPGCLASSES_DIRECTORY, "archer.yml");
			if (archer.exists()) {

				YamlConfiguration config = new YamlConfiguration();
				config.load(archer);

				ConfigurationSection sec = config.getConfigurationSection("Unlock");
				for (String key : sec.getKeys(false)) {
					unlockArcher.put(key, sec.getBoolean(key));
				}
				
				sec = config.getConfigurationSection("Level");
				for(String key : sec.getKeys(false)) {
					archerLevel.put(key, sec.getInt(key));
				}
				
				sec = config.getConfigurationSection("Xp");
				for(String key : sec.getKeys(false)) {
					archerXp.put(key, sec.getDouble(key));
				}
				
			}
			
			File ninja = new File(RCUtils.RPGCLASSES_DIRECTORY, "ninja.yml");
			if (ninja.exists()) {

				YamlConfiguration config = new YamlConfiguration();
				config.load(ninja);

				ConfigurationSection sec = config.getConfigurationSection("Unlock");
				for (String key : sec.getKeys(false)) {
					unlockNinja.put(key, sec.getBoolean(key));
				}
				
				sec = config.getConfigurationSection("Level");
				for(String key : sec.getKeys(false)) {
					ninjaLevel.put(key, sec.getInt(key));
				}
				
				sec = config.getConfigurationSection("Xp");
				for(String key : sec.getKeys(false)) {
					ninjaXp.put(key, sec.getDouble(key));
				}
				
			}
			
			File wizard = new File(RCUtils.RPGCLASSES_DIRECTORY, "wizard.yml");
			if (wizard.exists()) {

				YamlConfiguration config = new YamlConfiguration();
				config.load(wizard);

				ConfigurationSection sec = config.getConfigurationSection("Unlock");
				for (String key : sec.getKeys(false)) {
					unlockWizard.put(key, sec.getBoolean(key));
				}
				
				sec = config.getConfigurationSection("Level");
				for(String key : sec.getKeys(false)) {
					wizardLevel.put(key, sec.getInt(key));
				}
				
				sec = config.getConfigurationSection("Xp");
				for(String key : sec.getKeys(false)) {
					wizardXp.put(key, sec.getDouble(key));
				}
				
			}
			
			File mysterious = new File(RCUtils.RPGCLASSES_DIRECTORY, "mysterious.yml");
			if (mysterious.exists()) {

				YamlConfiguration config = new YamlConfiguration();
				config.load(mysterious);

				ConfigurationSection sec = config.getConfigurationSection("Unlock");
				for (String key : sec.getKeys(false)) {
					unlockMysterious.put(key, sec.getBoolean(key));
				}
				
				sec = config.getConfigurationSection("Level");
				for(String key : sec.getKeys(false)) {
					mysteriousLevel.put(key, sec.getInt(key));
				}
				
				sec = config.getConfigurationSection("Xp");
				for(String key : sec.getKeys(false)) {
					mysteriousXp.put(key, sec.getDouble(key));
				}
				
			}

		} catch (Exception ex) {
			MainCore.instance.getLogger().log(Level.SEVERE, "[ClassesLoader] Load Failed!", ex);
		}
	}
	
	// change this every time you add a new class !!!
	public static int getLevel(RpgClass rpgClass, Player p) throws NullPointerException {
		switch(rpgClass.getId()) {
		case "gladiator":
			return getGladiatorLevel(p);
		case "archer":
			return getArcherLevel(p);
		case "ninja":
			return getNinjaLevel(p);
		case "wizard":
			return getWizardLevel(p);
		case "mysterious":
			return getMysteriousLevel(p);
		default:
			return 0;
		}
	}
	
	public static double getXp(RpgClass rpgClass, Player p) throws NullPointerException {
		switch(rpgClass.getId()) {
		case "gladiator":
			return getGladiatorXp(p);
		case "archer":
			return getArcherXp(p);
		case "ninja":
			return getNinjaXp(p);
		case "wizard":
			return getWizardXp(p);
		case "mysterious":
			return getMysteriousXp(p);
		default:
			return 0;
		}
	}
}
