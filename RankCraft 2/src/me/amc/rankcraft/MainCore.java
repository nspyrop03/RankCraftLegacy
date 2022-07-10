package me.amc.rankcraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import me.amc.rankcraft.achievements.Achievement;
import me.amc.rankcraft.achievements.AchievementSaver;
import me.amc.rankcraft.backpack.BackpackUtils;
import me.amc.rankcraft.classes.RpgClass;
import me.amc.rankcraft.classes.RpgClassData;
import me.amc.rankcraft.commands.RankCraftCommand;
import me.amc.rankcraft.lang.GuiLanguage;
import me.amc.rankcraft.lang.ItemLanguage;
import me.amc.rankcraft.lang.Language;
import me.amc.rankcraft.quests2.Quest;
import me.amc.rankcraft.utils.RCUtils;
import net.milkbowl.vault.economy.Economy;

public class MainCore extends JavaPlugin {

	public RankCraft rankCraft;
	public static MainCore instance;

	public ConfigHelper config;

	private ScoreboardManager manager;
	public ScoreboardHelper scoreboard;

	public PermissionList permList;

	public Language language;
	public GuiLanguage guiLang;
	public ItemLanguage itemLang;

	public Economy econ = null;

	public String nmsver;
	public boolean useOldMethods = false;

	public Scoreboard emptyBoard;
	
	//public UpdateChecker updateChecker;
	
	public boolean usesPlaceholderAPI = false;
	
	public boolean outdated = true;
	
	//private PlaceholderReplacer statsReplacer;

	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		reloadConfig();

		nmsver = Bukkit.getServer().getClass().getPackage().getName();
		nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);

		if (nmsver.equalsIgnoreCase("v1_8_R1") || nmsver.startsWith("v1_7_")) {
			useOldMethods = true;
		}

		language = new Language(config.langFile);
		guiLang = new GuiLanguage(config.guiLangFile);
		itemLang = new ItemLanguage(config.itemLangFile);

		permList = new PermissionList();

		rankCraft = new RankCraft();

		rankCraft.loadStats();
		rankCraft.loadSkills();
		rankCraft.hpSystem.load();

		RpgClassData.load();

		manager = Bukkit.getScoreboardManager();
		emptyBoard = manager.getNewScoreboard();
		scoreboard = new ScoreboardHelper(manager);

		rankCraft.initSubCommands();
		getCommand("rankcraft").setExecutor(new RankCraftCommand());

		for (Player p : Bukkit.getOnlinePlayers()) {
			for (Quest q : rankCraft.quests2) {
				q.initForPlayer(p, instance);
			}
		}

		for (Achievement a : rankCraft.achievements) {
			AchievementSaver.load(instance, a);
			for (Player p : Bukkit.getOnlinePlayers()) {
				a.initFor(p);
			}
		}

		// FOR OLD ZOMBIES CLASS
		// rankCraft.zombies.reloadArrayZombies10();

		if (config.enablePreMadeMobs) {
			rankCraft.zombies.reloadArrayZombies10();
		}

		rankCraft.loadRpgItems();
		for (Player p : Bukkit.getOnlinePlayers()) {
			rankCraft.initRpgItems(p);
			// rankCraft.addBackpack(p);
			BackpackUtils.initFor(p);
		}
		
		rankCraft.initTreasureChests();

		rankCraft.registerEvents();
		
		rankCraft.addGeneralRecipes();

		rankCraft.shop.setNextRefresh(0);

		new MainRunnable().runTaskTimer(this, 0, 1);
		/*
		 * new BukkitRunnable() {
		 * 
		 * @Override public void run() {
		 * 
		 * for (Player p : Bukkit.getOnlinePlayers()) {
		 * MainCore.instance.scoreboard.updateBoard(p); }
		 * 
		 * } }.runTaskTimerAsynchronously(this, 0, 20);
		 */
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (RCUtils.isWorldEnabled(p.getWorld())) {

				for (PotionEffect pe : p.getActivePotionEffects()) {
					p.removePotionEffect(pe.getType());
				}

				try {
					RpgClass currentClass = RCUtils.getRpgClassById(RpgClassData.getCurrentClass(p));
					if (currentClass.getPotionEffect(RpgClassData.getLevel(currentClass, p)) != null) {
						p.addPotionEffect(currentClass.getPotionEffect(RpgClassData.getLevel(currentClass, p)));
					}

					if (currentClass.getSpecialEffects(RpgClassData.getLevel(currentClass, p)) != null) {
						for (PotionEffect effect : currentClass
								.getSpecialEffects(RpgClassData.getLevel(currentClass, p))) {
							p.addPotionEffect(effect);
						}
					}

				} catch (NullPointerException ex) {

				}

			}
		}

		getLogger().info("|=======================================|");
		getLogger().info("|           RankCraft 2 enabled!        |");
		getLogger().info("|=======================================|");

		
		if (setupEconomy()) {
			getLogger().info("Vault found!");
		}

		if (getWorldGuard() != null) {
			getLogger().info("WorldGuard found!");
		}

		if (getWorldEdit() != null) {
			getLogger().info("WorldEdit found!");
		}
		
		Object object = Bukkit.getServer().getPluginManager().getPlugin("MVdWPlaceholderAPI");

		//if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
		if(object != null) {	
			getLogger().info("MVdWPlaceholderAPI found!");
			new MVdWStatsPlaceholders().init();
		
			//initMVdWPlaceholderAPI();
		}// else
		//	getLogger().info("what?");
	
		//boolean obj2 = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
		Object obj2 = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI");
		
		if (obj2 != null) {
			getLogger().info("PlaceholderAPI found!");
			usesPlaceholderAPI = true;

			new StatsPlaceholdersExpansion(this).register();
			
			//new StatsPlaceholders().init().hook();
			// for(Player p : Bukkit.getOnlinePlayers()) {
			// p.sendMessage(PlaceholderAPI.setPlaceholders(p, "Your xp:
			// %rankcraft_player_xp%"));
			// }

		}
		
		//boolean obj3 = Bukkit.getPluginManager().isPluginEnabled("GriefPrevention");
		Object obj3 = Bukkit.getServer().getPluginManager().getPlugin("GriefPrevention");
		if(obj3 != null) {
			getLogger().info("GriefPrevention found!");
		}
		
		/*
		if(isUsingFeudal()) {
			getLogger().info("Feudal found!");
		}
		*/
		String currentVersionString = getDescription().getVersion();
		//boolean update = true;
		Thread checkupdates = new Thread(){
			public void run(){
				
				URL url = null;
                try {
                    url = new URL("https://api.spigotmc.org/legacy/update.php?resource=37754");
                } catch (MalformedURLException e) {
                                    
                }
				
                URLConnection conn = null;
                try {
                    conn = url.openConnection();
                } catch (IOException e) {
                   
                }
                
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    if(br.readLine().equals(currentVersionString)) {
                    	getServer().getConsoleSender().sendMessage(language.getPrefix()+"§aRankCraft is up-to-date!");
                    	//System.out.println("[Plugin]No Updates aviable");
                        //update = false;
                    	outdated = false;
                    }else {
                       // System.out.println("[Plugin]There is an update! Download it at https://www.spigotmc.org/resources/betteradmin.65264/");
                    	getServer().getConsoleSender().sendMessage(language.getPrefix()+"§4RankCraft is outdated! Download the latest version from https://www.spigotmc.org/resources/rankcraft-the-ultimate-rpg-plugin.37754/");
                    }
                } catch (IOException e) {
                   
                }
                
                
			}
		};
		
		checkupdates.start();
		
		
		/*
		try {
			updateChecker = new UpdateChecker();
			updateChecker.checkUpdate();
		} catch(Exception ex) {
			
		}
		*/
		rankCraft.updateLeaderboards();
		
		//new TreasureChest2("Test_Treasure_Chest", "testTreasureChest", TreasureChest2.Rarity.COMMON);
	}

	@Override
	public void onDisable() {

		try {
			//rankCraft.crossbow.removeArrowsFromWorld();
			//rankCraft.fireCrossbow.removeArrowsFromWorld();
		} catch(Exception ex) {
			getLogger().warning("Could not disable RankCraft preperly!");
		}
		// rankCraft.crossbow.saveArrows();

		try {
			rankCraft.saveStats();
			rankCraft.saveSkills();
			rankCraft.hpSystem.save();
			RpgClassData.save();
		} catch(Exception ex) {
			getLogger().warning("Could not save data!!! Please restart the server!");
		}
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.closeInventory();
		}

		for (Achievement a : rankCraft.achievements) {
			AchievementSaver.save(instance, a);
		}

		getLogger().info("|=======================================|");
		getLogger().info("|          RankCraft 2 disabled!        |");
		getLogger().info("|=======================================|");

	}

	@Override
	public void reloadConfig() {
		super.reloadConfig();
		config = new ConfigHelper(this.getConfig());
		if (!setupEconomy()) {
			config.useVault = false;
		}
	}

	public void sendError(String error) {
		this.getLogger().log(Level.WARNING, error);
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public WorldGuardPlugin getWorldGuard() {
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			return null;
		}

		return (WorldGuardPlugin) plugin;
	}

	public WorldEditPlugin getWorldEdit() {
		Plugin p = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
		if (p instanceof WorldEditPlugin)
			return (WorldEditPlugin) p;
		else
			return null;
	}
	
	/*
	private boolean isUsingFeudal(){
		return Bukkit.getPluginManager().getPlugin("Feudal") != null && Bukkit.getPluginManager().getPlugin("Feudal").isEnabled();
	}
	*/
}
