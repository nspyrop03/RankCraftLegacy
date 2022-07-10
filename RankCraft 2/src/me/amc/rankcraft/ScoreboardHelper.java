package me.amc.rankcraft;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import me.amc.rankcraft.damage.HPSystem;
import me.amc.rankcraft.stats.Stats;
import me.amc.rankcraft.utils.RCUtils;
import me.amc.rankcraft.yaml.YamlParser;
import me.clip.placeholderapi.PlaceholderAPI;

public class ScoreboardHelper {

	private ScoreboardManager manager;
	private YamlParser y;
	private FileConfiguration c;

	private String title;
	private List<String> lines = new ArrayList<>();
	private int lineSize = 12;

	private Stats stats = MainCore.instance.rankCraft.stats;
	private HPSystem hp = MainCore.instance.rankCraft.hpSystem;
	
	//private Scoreboard testBoard;

	public ScoreboardHelper(ScoreboardManager manager) {
		this.manager = manager;
		y = new YamlParser(new File("plugins/RankCraft"), "scoreboard.yml");
		c = y.getConfig();
		initVariables();
	}

	private void initVariables() {
		title = c.getString("Title").replace('&', '§');

		lines.clear();
		for (int i = 0; i < lineSize; i++) {
			if (c.getStringList("Lines").size() > i) {
				lines.add(c.getStringList("Lines").get(i));
			}
		}
		// System.out.println(lines);
		// System.out.println(c.getStringList("Lines"));
	}

	@SuppressWarnings("deprecation")
	public void createScoreboard(Player p) {
		if (RCUtils.isWorldEnabled(p.getWorld())) {
			if (MainCore.instance.config.enableScoreboard) {
				// p.sendMessage("Added scoreboard ?");
				Scoreboard board = manager.getNewScoreboard();
				// System.out.println(manager == null);
				// System.out.println(board == null);
				Objective objective = board.registerNewObjective("objective", "dummy");

				objective.setDisplaySlot(DisplaySlot.SIDEBAR);
				objective.setDisplayName(title);

				// Stats s = MainCore.instance.rankCraft.stats;
				// Gold g = MainCore.instance.rankCraft.gold;

				int blankLines = 0;
				String blankLine = " ";
				StringBuilder line = new StringBuilder();

				for (int i = 0; i < lines.size(); i++) {

					String fixedLine = lines.get(i).replace('&', '§');

					if (fixedLine.equalsIgnoreCase("{blank_line}")) {
						blankLines++;

						for (int j = 0; j < blankLines; j++) {
							line.append(blankLine);
						}

						objective.getScore(line.toString()).setScore(lineSize - i);
					} else {

						// HPSystem hpSystem =
						// MainCore.instance.rankCraft.hpSystem;

						fixedLine = fixedLine.replace("{player}", p.getName());
						/*
						 * Old code with startup bug
						 * 
						 * fixedLine = fixedLine.replace("{xp}", "" +
						 * RCUtils.round(s.getXp(p), 2)); fixedLine =
						 * fixedLine.replace("{level_up}", "" +
						 * s.getXPToLevelUP(p)); fixedLine =
						 * fixedLine.replace("{level}", "" + s.getLevel(p));
						 * fixedLine = fixedLine.replace("{rank}",
						 * s.getRank(p)); fixedLine =
						 * fixedLine.replace("{gold}", "" +
						 * RCUtils.round(g.getGold(p), 2)); fixedLine =
						 * fixedLine.replace("{mana}", "" + s.getMana(p));
						 * fixedLine = fixedLine.replace("{max_mana}", "" +
						 * s.getMaxMana(p)); fixedLine =
						 * fixedLine.replace("{blocks_placed}", "" +
						 * s.getBlocksPlaced(p)); fixedLine =
						 * fixedLine.replace("{blocks_broken}", "" +
						 * s.getBlocksBroken(p)); fixedLine =
						 * fixedLine.replace("{hp}",
						 * ""+RCUtils.round(hpSystem.getHP(p),1)); fixedLine =
						 * fixedLine.replace("{max_hp}",
						 * ""+RCUtils.round(hpSystem.getMaxHP(p), 1)); fixedLine
						 * = fixedLine.replace("{hp_bar}",
						 * hpSystem.getHealthBar(p, 20));
						 */
						/*
						fixedLine = fixedLine.replace("{xp}", getXp(p));
						fixedLine = fixedLine.replace("{level_up}", getLvlUp(p));
						fixedLine = fixedLine.replace("{level}", getLevel(p));
						fixedLine = fixedLine.replace("{rank}", getRank(p));
						fixedLine = fixedLine.replace("{gold}", getGold(p));
						fixedLine = fixedLine.replace("{mana}", getMana(p));
						fixedLine = fixedLine.replace("{max_mana}", getMaxMana(p));
						fixedLine = fixedLine.replace("{blocks_placed}", getBP(p));
						fixedLine = fixedLine.replace("{blocks_broken}", getBB(p));
						fixedLine = fixedLine.replace("{hp}", getHP(p));
						fixedLine = fixedLine.replace("{max_hp}", getMaxHP(p));
						fixedLine = fixedLine.replace("{hp_bar}", getHPBar(p));
						*/
						fixedLine = getUpdatedString(fixedLine, p);
						// fixedLine = fixedLine.replace("{blank_line}",
						// ChatColor.RED.toString());
						// System.out.println(i+" "+fixedLine);
						// System.out.println(fixedLine.length());
						// System.out.println("hp_bar:
						// "+hpSystem.getHealthBar(p,
						// 10).length());
						// System.out.println("'|' lenght: "+"|".length());
						if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {

							fixedLine = PlaceholderAPI.setPlaceholders(p, fixedLine);
							
						//	System.out.println(fixedLine);
						}
						/*
						 * if
						 * (PlaceholderAPI.setPlaceholders(p,fixedLine).length()
						 * <= 40) {
						 * objective.getScore(PlaceholderAPI.setPlaceholders(p,
						 * fixedLine)).setScore(lineSize - i); } else {
						 * fixedLine = "The line must have up to 40 characters!"
						 * ;
						 * objective.getScore(PlaceholderAPI.setPlaceholders(p,
						 * fixedLine)).setScore(lineSize - i); }
						 */

						if (fixedLine.length() <= 40) {
							objective.getScore(fixedLine).setScore(lineSize - i);
						} else {
							fixedLine = "The line must have up to 40 characters!";
							objective.getScore(fixedLine).setScore(lineSize - i);
						}

					}
					// System.out.println(lineSize - i+": "+fixedLine);
				}

				p.setScoreboard(board);
				//testBoard = board;
			}
		}
	}

	public String getUpdatedString(String fixedLine, Player p) {
		fixedLine = fixedLine.replace("{player}", p.getName());
		fixedLine = fixedLine.replace("{xp}", getXp(p));
		fixedLine = fixedLine.replace("{level_up}", getLvlUp(p));
		fixedLine = fixedLine.replace("{level}", getLevel(p));
		fixedLine = fixedLine.replace("{rank}", getRank(p));
		fixedLine = fixedLine.replace("{gold}", getGold(p));
		fixedLine = fixedLine.replace("{mana}", getMana(p));
		fixedLine = fixedLine.replace("{max_mana}", getMaxMana(p));
		fixedLine = fixedLine.replace("{blocks_placed}", getBP(p));
		fixedLine = fixedLine.replace("{blocks_broken}", getBB(p));
		fixedLine = fixedLine.replace("{hp}", getHP(p));
		fixedLine = fixedLine.replace("{max_hp}", getMaxHP(p));
		fixedLine = fixedLine.replace("{hp_bar}", getHPBar(p));
		fixedLine = fixedLine.replace("{mana_bar}", getManaBar(p));
		fixedLine = fixedLine.replace("{xp_bar}", getXpBar(p));
		fixedLine = fixedLine.replace("{hunger_bar}", getHungerBar(p));
		return fixedLine;
	}
	
	
	
	public void updateBoard(Player p) {
		if (RCUtils.isWorldEnabled(p.getWorld())) {
			if (MainCore.instance.config.enableScoreboard) {
				if (p.getScoreboard() != null) {
					Scoreboard board = p.getScoreboard();
					Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
					try {
						objective.unregister();
					} catch (Exception e) {
						MainCore.instance.sendError("Cannot unregister scoreboard object !!!");
					}
					createScoreboard(p);
				}
			}
		} /*else {
			
			if(p.getScoreboard() != null && p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
				if(p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getDisplayName().equals(title)) {
					
					p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
					
				}
					
			}
			
			
			
			if (p.getScoreboard() != null) {
				Scoreboard board = p.getScoreboard();
				Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
				try {
					
						p.setScoreboard(p.getScoreboard());
					
					
				} catch (Exception e) {
				//	MainCore.instance.sendError("Cannot unregister scoreboard object !!!");
				}
				
			}
			
		
		}*/ /*else {
		
			// scoreboard = new scoreboard
			// p.setscoreboard scoreboard
			
			Scoreboard emptyBoard = MainCore.instance.emptyBoard;
		//	if(!p.getScoreboard().equals(emptyBoard)) 
		//	if(p.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null)
		//		if(p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getDisplayName().equals(title)) {
			if(p.getScoreboard().equals(testBoard)) {
				p.setScoreboard(emptyBoard);
				System.out.println("Clearing scoreboard for "+p.getName());
			}
		}*/
	}

	public ScoreboardManager getScoreboardManager() {
		return this.manager;
	}

	private String getManaBar(Player p) {
		if(stats.areAllOk(p)) {
			return MainCore.instance.rankCraft.stats.getManaBar(p, 20);
		} else {
			return "...";
		}
	}
	
	private String getXpBar(Player p) {
		if(stats.areAllOk(p)) {
			return MainCore.instance.rankCraft.stats.getXpBar(p, 20);
		} else {
			return "...";
		}
	}
	
	private String getHungerBar(Player p) {
		if(stats.areAllOk(p)) {
			return MainCore.instance.rankCraft.stats.getHungerBar(p, 20);
		} else {
			return "...";
		}
	}
	
	private String getXp(Player p) {
		if (stats.areAllOk(p)) {
			return "" + RCUtils.round(stats.getXp(p), 1);
		} else {
			return "...";
		}
	}

	private String getLvlUp(Player p) {
		if (stats.areAllOk(p)) {
			return "" + stats.getXPToLevelUP(p);
		} else {
			return "...";
		}
	}

	private String getLevel(Player p) {
		if (stats.areAllOk(p)) {
			return "" + stats.getLevel(p);
		} else {
			return "...";
		}
	}

	private String getRank(Player p) {
		if (stats.areAllOk(p)) {
			return "" + stats.getRank(p);
		} else {
			return "...";
		}
	}

	private String getBB(Player p) {
		if (stats.areAllOk(p)) {
			return "" + stats.getBlocksBroken(p);
		} else {
			return "...";
		}
	}

	private String getBP(Player p) {
		if (stats.areAllOk(p)) {
			return "" + stats.getBlocksPlaced(p);
		} else {
			return "...";
		}
	}

	private String getMana(Player p) {
		if (stats.areAllOk(p)) {
			return "" + RCUtils.round(stats.getMana(p), 1);
		} else {
			return "...";
		}
	}

	private String getMaxMana(Player p) {
		if (stats.areAllOk(p)) {
			return "" + RCUtils.round(stats.getMaxMana(p), 1);
		} else {
			return "...";
		}
	}

	private String getGold(Player p) {
		if (MainCore.instance.rankCraft.gold.areAllOk(p) || MainCore.instance.config.useVault) {
			return "" + RCUtils.round(MainCore.instance.rankCraft.gold.getGold(p), 1);
		} else {
			return "...";
		}
	}

	private String getHP(Player p) {
		if (hp.areAllOk(p)) {
			if(MainCore.instance.config.enableHPSystem) {
				return "" + RCUtils.round(hp.getHP(p), 1);
			} else {
				return "§4HPSystem disabled!";
			}
		} else {
			return "...";
		}
	}

	private String getMaxHP(Player p) {
		if (hp.areAllOk(p)) {
			if(MainCore.instance.config.enableHPSystem) {
				return "" + RCUtils.round(hp.getMaxHP(p), 1);
			} else {
				return "§4HPSystem disabled!";
			}
		} else {
			return "...";
		}
	}

	private String getHPBar(Player p) {
		if (hp.areAllOk(p)) {
			if(MainCore.instance.config.enableHPSystem) {
				return hp.getHealthBar(p, 20);
			} else {
				return "§4HPSystem disabled!";
			}
		} else {
			return "...";
		}
	}
	
	
}
