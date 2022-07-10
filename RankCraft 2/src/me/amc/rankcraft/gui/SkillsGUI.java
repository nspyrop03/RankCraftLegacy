package me.amc.rankcraft.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.skills.AttackSkill;
import me.amc.rankcraft.skills.DefenseSkill;
import me.amc.rankcraft.skills.MagicSkill;
import me.amc.rankcraft.skills.Skill;
import me.amc.rankcraft.stats.Gold;
import me.amc.rankcraft.stats.Stats;
import me.amc.rankcraft.utils.CustomItem;

public class SkillsGUI implements Listener {

	private GUI gui;

	private CustomItem maxMana;
	private CustomItem attack;
	private CustomItem defense;
	private CustomItem magic;

	// public String title = "Skills GUI";
	public String title = MainCore.instance.guiLang.getSkillsTitle();

	public SkillsGUI() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}

	public void open(Player p) {
		initItems(p);
		gui = new GUI(title, p, 9 * 3);
		gui.addItem(gui.close.getItem(), gui.getSize() - 1);
		gui.addItem(maxMana.getItem(), 9 + 1);
		gui.addItem(attack.getItem(), 9 + 3);
		gui.addItem(defense.getItem(), 9 + 5);
		gui.addItem(magic.getItem(), 9 + 7);
		gui.openInventory();
	}

	private void initItems(Player p) {
		attack = null;
		defense = null;
		magic = null;
		maxMana = null;
		
		/*
		 * attack = new CustomItem(Material.IRON_SWORD, ChatColor.GREEN +
		 * "Upgrade " + ChatColor.RED + "AttackSkill " + ChatColor.GREEN +
		 * "for " + ChatColor.GOLD +
		 * getClassicCost(MainCore.instance.rankCraft.attackSkill, p) + " Gold"
		 * ); attack.addLores(ChatColor.GRAY+"Your AttackSkill: "
		 * +ChatColor.RED+MainCore.instance.rankCraft.attackSkill.getPoints(p));
		 * defense = new CustomItem(Material.IRON_BLOCK, ChatColor.GREEN +
		 * "Upgrade " + ChatColor.YELLOW + "DefenseSkill " + ChatColor.GREEN +
		 * "for " + ChatColor.GOLD +
		 * getClassicCost(MainCore.instance.rankCraft.defenseSkill, p) + " Gold"
		 * ); defense.addLores(ChatColor.GRAY+"Your DefenseSkill: "
		 * +ChatColor.RED+MainCore.instance.rankCraft.defenseSkill.getPoints(p))
		 * ; magic = new CustomItem(Material.EXP_BOTTLE, ChatColor.GREEN +
		 * "Upgrade " + ChatColor.LIGHT_PURPLE + "MagicSkill " + ChatColor.GREEN
		 * + "for " + ChatColor.GOLD +
		 * getClassicCost(MainCore.instance.rankCraft.magicSkill, p) + " Gold");
		 * magic.addLores(ChatColor.GRAY+"Your MagicSkill: "
		 * +ChatColor.RED+MainCore.instance.rankCraft.magicSkill.getPoints(p));
		 * maxMana = new CustomItem(Material.DIAMOND, ChatColor.GREEN +
		 * "Upgrade " + ChatColor.DARK_BLUE + "MaxManaLevel " + ChatColor.GREEN
		 * + "for " + ChatColor.GOLD + getMaxManaCost(p) + " Gold");
		 * maxMana.addLores(ChatColor.GRAY+"Your MaxManaLevel: "
		 * +ChatColor.RED+MainCore.instance.rankCraft.stats.getMaxManaLevel(p));
		 */
		
		attack = new CustomItem(Material.IRON_SWORD, MainCore.instance.guiLang.getSkillsName("Attack",
				getClassicCost(MainCore.instance.rankCraft.attackSkill, p)));
		attack.addLores(MainCore.instance.guiLang.getSkillsLore("Attack",
				MainCore.instance.rankCraft.attackSkill.getPoints(p)));

		defense = new CustomItem(Material.IRON_BLOCK, MainCore.instance.guiLang.getSkillsName("Defense",
				getClassicCost(MainCore.instance.rankCraft.defenseSkill, p)));
		defense.addLores(MainCore.instance.guiLang.getSkillsLore("Defense",
				MainCore.instance.rankCraft.defenseSkill.getPoints(p)));

		magic = new CustomItem(Material.EXPERIENCE_BOTTLE, MainCore.instance.guiLang.getSkillsName("Magic",
				getClassicCost(MainCore.instance.rankCraft.magicSkill, p)));
		magic.addLores(
				MainCore.instance.guiLang.getSkillsLore("Magic", MainCore.instance.rankCraft.magicSkill.getPoints(p)));

		maxMana = new CustomItem(Material.DIAMOND,
				MainCore.instance.guiLang.getSkillsName("MaxMana", getMaxManaCost(p)));
		maxMana.addLores(MainCore.instance.guiLang.getSkillsLore("MaxMana",
				MainCore.instance.rankCraft.stats.getMaxManaLevel(p)));

		attack.build();
		defense.build();
		magic.build();
		maxMana.build();

	}

	private float getClassicCost(Skill s, Player p) {
		return (100 + s.getPoints(p) * 20);
	}

	private float getMaxManaCost(Player p) {
		Stats s = MainCore.instance.rankCraft.stats;
		float maxMana = s.getMaxMana(p);
		return (maxMana * 10);
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {

		if (!e.getView().getTitle().equalsIgnoreCase(title)) {
			return;
		}

		e.setCancelled(true);

		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
			return;
		}
		
		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		Gold g = MainCore.instance.rankCraft.gold;
		Stats s = MainCore.instance.rankCraft.stats;
		AttackSkill as = MainCore.instance.rankCraft.attackSkill;
		DefenseSkill ds = MainCore.instance.rankCraft.defenseSkill;
		MagicSkill ms = MainCore.instance.rankCraft.magicSkill;

		if (gui.checkItemTypeAndName(item, gui.close.getName(), gui.close.getType())) {
			p.closeInventory();
		}

		if (gui.checkItemTypeAndName(item, maxMana.getName(), maxMana.getType())) {
			if (g.hasGoldAmount(p, getMaxManaCost(p))) {

				g.removeGold(p, getMaxManaCost(p));
				s.setMaxManaLevel(p, s.getMaxManaLevel(p) + 1);

				successfullyUpgrade(p);
			} else {
				notEnoughtGold(p);
			}
		}

		if (gui.checkItemTypeAndName(item, attack.getName(), attack.getType())) {
			if (g.hasGoldAmount(p, getClassicCost(as, p))) {
				g.removeGold(p, getClassicCost(as, p));
				as.addPoints(p, 1);

				successfullyUpgrade(p);
			} else {
				notEnoughtGold(p);
			}
		}

		if (gui.checkItemTypeAndName(item, defense.getName(), defense.getType())) {
			if (g.hasGoldAmount(p, getClassicCost(ds, p))) {
				g.removeGold(p, getClassicCost(ds, p));
				ds.addPoints(p, 1);

				successfullyUpgrade(p);
			} else {
				notEnoughtGold(p);
			}
		}

		if (gui.checkItemTypeAndName(item, magic.getName(), magic.getType())) {
			if (g.hasGoldAmount(p, getClassicCost(ms, p))) {
				g.removeGold(p, getClassicCost(ms, p));
				ms.addPoints(p, 1);

				successfullyUpgrade(p);
			} else {
				notEnoughtGold(p);
			}
		}

	}

	private void successfullyUpgrade(Player p) {
		// p.sendMessage(ChatColor.GREEN+"The upgrade was successfull!");
		p.sendMessage(MainCore.instance.language.getSuccessfulUpgrade());
		updateInventory(p);
	}

	private void notEnoughtGold(Player p) {
		// p.sendMessage(ChatColor.RED+"You don't have enought gold!");
		p.sendMessage(MainCore.instance.language.getNotEnoughGold());
	}

	private void updateInventory(Player p) {
		p.closeInventory();
		MainCore.instance.rankCraft.skillsGUI.open(p);
	}
}
