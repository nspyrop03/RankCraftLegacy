package me.amc.rankcraft.classes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.gui.GUI;
import me.amc.rankcraft.utils.CustomItem;

public class RpgClassGUI implements Listener {

	private GUI gui;

	private CustomItem gladiatorItem;
	private CustomItem archerItem;
	private CustomItem ninjaItem;
	private CustomItem wizardItem;
	private CustomItem mysteriousItem;

	public String title = "RpgClasses GUI";

	public RpgClassGUI() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}

	public void open(Player p) {
		initItems(p);
		gui = new GUI(title, p, 9 * 3);

		gui.addItem(gladiatorItem.getItem(), 1);
		gui.addItem(archerItem.getItem(), 3);
		gui.addItem(ninjaItem.getItem(), 5);
		gui.addItem(wizardItem.getItem(), 7);
		gui.addItem(mysteriousItem.getItem(), 13);
		
		gui.addItem(gui.close.getItem(), gui.getSize() - 9);
		gui.addItem(gui.next.getItem(), gui.getSize() - 1);

		gui.openInventory();
	}

	private void initItems(Player p) {

		initGladiatorItem(p);
		initArcherItem(p);
		initNinjaItem(p);
		initWizardItem(p);
		initMysteriousItem(p);
		
	}

	private void initGladiatorItem(Player p) {
		int nextLevel = 0;

		if (RpgClassData.getGladiatorLevel(p) + 1 < MainCore.instance.rankCraft.gladiatorClass.getMaxLevel()) {
			nextLevel = RpgClassData.getGladiatorLevel(p) + 1;
		} else {
			nextLevel = MainCore.instance.rankCraft.gladiatorClass.getMaxLevel();
		}

		String gladiatorUnlocked = "NULL";
		if (RpgClassData.getUnlockGladiator(p)) {
			gladiatorUnlocked = ChatColor.GREEN + "Unlocked!";
		} else {
			gladiatorUnlocked = ChatColor.RED + "Locked!";
		}

		String gladiatorEquipped = "NULL";
		if (!RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.gladiatorClass.getId())) {
			gladiatorEquipped = ChatColor.RED + "Not Equipped!";
		} else {
			gladiatorEquipped = ChatColor.GREEN + "Equipped!";
		}

		gladiatorItem = new CustomItem(Material.IRON_CHESTPLATE, MainCore.instance.rankCraft.gladiatorClass.getName())
				.build();
		gladiatorItem
				.addLores(ChatColor.GRAY + "The strongest of all the classes!", ChatColor.GRAY + "Effect: Strength",
						ChatColor.BLUE+"Kill mobs to earn xp!",
						ChatColor.YELLOW + "Level: " + ChatColor.RED + "" + RpgClassData.getGladiatorLevel(p) + "/"
								+ MainCore.instance.rankCraft.gladiatorClass.getMaxLevel() + ChatColor.YELLOW + " Xp: "
								+ ChatColor.RED + "" + RpgClassData.getGladiatorXp(p) + "/"
								+ MainCore.instance.rankCraft.gladiatorClass
										.getXpToLevelUp(RpgClassData.getGladiatorLevel(p)),
				ChatColor.RED + "" + RpgClassData.getGladiatorLevel(p) + " "
						+ getXpBar(p, MainCore.instance.rankCraft.gladiatorClass, 20) + " " + ChatColor.RED + ""
						+ nextLevel, gladiatorUnlocked, gladiatorEquipped);

		gladiatorItem.build();
	}

	private void initArcherItem(Player p) {

		int nextLevel = 0;

		if (RpgClassData.getArcherLevel(p) + 1 < MainCore.instance.rankCraft.archerClass.getMaxLevel()) {
			nextLevel = RpgClassData.getArcherLevel(p) + 1;
		} else {
			nextLevel = MainCore.instance.rankCraft.archerClass.getMaxLevel();
		}

		String archerUnlocked = "NULL";
		if (RpgClassData.getUnlockArcher(p)) {
			archerUnlocked = ChatColor.GREEN + "Unlocked!";
		} else {
			archerUnlocked = ChatColor.RED + "Locked!";
		}

		String archerEquipped = "NULL";
		if (!RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.archerClass.getId())) {
			archerEquipped = ChatColor.RED + "Not Equipped!";
		} else {
			archerEquipped = ChatColor.GREEN + "Equipped!";
		}

		archerItem = new CustomItem(Material.BOW, MainCore.instance.rankCraft.archerClass.getName())
				.build();
		archerItem
				.addLores(ChatColor.GRAY + "Let's shoot some arrows!", ChatColor.GRAY + "Effect: Fire Resistance",
						ChatColor.BLUE+"Shoot arrows on mobs to earn xp!",
						ChatColor.YELLOW + "Level: " + ChatColor.RED + "" + RpgClassData.getArcherLevel(p) + "/"
								+ MainCore.instance.rankCraft.archerClass.getMaxLevel() + ChatColor.YELLOW + " Xp: "
								+ ChatColor.RED + "" + RpgClassData.getArcherXp(p) + "/"
								+ MainCore.instance.rankCraft.archerClass
										.getXpToLevelUp(RpgClassData.getArcherLevel(p)),
				ChatColor.RED + "" + RpgClassData.getArcherLevel(p) + " "
						+ getXpBar(p, MainCore.instance.rankCraft.archerClass, 20) + " " + ChatColor.RED + ""
						+ nextLevel, archerUnlocked, archerEquipped);

		archerItem.build();
	}

	private void initNinjaItem(Player p) {

		int nextLevel = 0;

		if (RpgClassData.getNinjaLevel(p) + 1 < MainCore.instance.rankCraft.ninjaClass.getMaxLevel()) {
			nextLevel = RpgClassData.getNinjaLevel(p) + 1;
		} else {
			nextLevel = MainCore.instance.rankCraft.ninjaClass.getMaxLevel();
		}

		String ninjaUnlocked = "NULL";
		if (RpgClassData.getUnlockNinja(p)) {
			ninjaUnlocked = ChatColor.GREEN + "Unlocked!";
		} else {
			ninjaUnlocked = ChatColor.RED + "Locked!";
		}

		String ninjaEquipped = "NULL";
		if (!RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.ninjaClass.getId())) {
			ninjaEquipped = ChatColor.RED + "Not Equipped!";
		} else {
			ninjaEquipped = ChatColor.GREEN + "Equipped!";
		}

		ninjaItem = new CustomItem(Material.IRON_SWORD, MainCore.instance.rankCraft.ninjaClass.getName())
				.build();
		ninjaItem
				.addLores(ChatColor.GRAY + "The fastest of all!", ChatColor.GRAY + "Effect: Speed",
						ChatColor.BLUE+"Kill mobs to earn xp!",
						ChatColor.YELLOW + "Level: " + ChatColor.RED + "" + RpgClassData.getNinjaLevel(p) + "/"
								+ MainCore.instance.rankCraft.ninjaClass.getMaxLevel() + ChatColor.YELLOW + " Xp: "
								+ ChatColor.RED + "" + RpgClassData.getNinjaXp(p) + "/"
								+ MainCore.instance.rankCraft.ninjaClass
										.getXpToLevelUp(RpgClassData.getNinjaLevel(p)),
				ChatColor.RED + "" + RpgClassData.getNinjaLevel(p) + " "
						+ getXpBar(p, MainCore.instance.rankCraft.ninjaClass, 20) + " " + ChatColor.RED + ""
						+ nextLevel, ninjaUnlocked, ninjaEquipped);

		ninjaItem.build();
	}
	
	private void initWizardItem(Player p) {

		int nextLevel = 0;

		if (RpgClassData.getWizardLevel(p) + 1 < MainCore.instance.rankCraft.wizardClass.getMaxLevel()) {
			nextLevel = RpgClassData.getWizardLevel(p) + 1;
		} else {
			nextLevel = MainCore.instance.rankCraft.wizardClass.getMaxLevel();
		}

		String wizardUnlocked = "NULL";
		if (RpgClassData.getUnlockWizard(p)) {
			wizardUnlocked = ChatColor.GREEN + "Unlocked!";
		} else {
			wizardUnlocked = ChatColor.RED + "Locked!";
		}

		String wizardEquipped = "NULL";
		if (!RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.wizardClass.getId())) {
			wizardEquipped = ChatColor.RED + "Not Equipped!";
		} else {
			wizardEquipped = ChatColor.GREEN + "Equipped!";
		}

		wizardItem = new CustomItem(Material.STICK, MainCore.instance.rankCraft.wizardClass.getName())
				.build();
		wizardItem
				.addLores(ChatColor.GRAY + "Magic is power!", ChatColor.GRAY + "Effect: Damage Resistance",
						ChatColor.BLUE+"Use spells to earn xp!",
						ChatColor.YELLOW + "Level: " + ChatColor.RED + "" + RpgClassData.getWizardLevel(p) + "/"
								+ MainCore.instance.rankCraft.wizardClass.getMaxLevel() + ChatColor.YELLOW + " Xp: "
								+ ChatColor.RED + "" + RpgClassData.getWizardXp(p) + "/"
								+ MainCore.instance.rankCraft.wizardClass
										.getXpToLevelUp(RpgClassData.getWizardLevel(p)),
				ChatColor.RED + "" + RpgClassData.getWizardLevel(p) + " "
						+ getXpBar(p, MainCore.instance.rankCraft.wizardClass, 20) + " " + ChatColor.RED + ""
						+ nextLevel, wizardUnlocked, wizardEquipped);

		wizardItem.build();
	}
	
	private void initMysteriousItem(Player p) {

		int nextLevel = 0;

		if (RpgClassData.getMysteriousLevel(p) + 1 < MainCore.instance.rankCraft.mysteriousClass.getMaxLevel()) {
			nextLevel = RpgClassData.getMysteriousLevel(p) + 1;
		} else {
			nextLevel = MainCore.instance.rankCraft.mysteriousClass.getMaxLevel();
		}

		String mysteriousUnlocked = "NULL";
		if (RpgClassData.getUnlockMysterious(p)) {
			mysteriousUnlocked = ChatColor.GREEN + "Unlocked!";
		} else {
			mysteriousUnlocked = ChatColor.RED + "Locked!";
		}

		String mysteriousEquipped = "NULL";
		if (!RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.mysteriousClass.getId())) {
			mysteriousEquipped = ChatColor.RED + "Not Equipped!";
		} else {
			mysteriousEquipped = ChatColor.GREEN + "Equipped!";
		}

		mysteriousItem = new CustomItem(Material.ENDER_EYE, MainCore.instance.rankCraft.mysteriousClass.getName())
				.build();
		mysteriousItem
				.addLores(ChatColor.GRAY + "A mix of the Gladiator, Archer,",ChatColor.GRAY+" Ninja, Wizard classes!", ChatColor.GRAY + "Effect: All the effects of the",ChatColor.GRAY+" above classes!",
						ChatColor.BLUE+"Earn xp by doing what you",ChatColor.BLUE+" do for the above classes!",
						ChatColor.YELLOW + "Level: " + ChatColor.RED + "" + RpgClassData.getMysteriousLevel(p) + "/"
								+ MainCore.instance.rankCraft.mysteriousClass.getMaxLevel() + ChatColor.YELLOW + " Xp: "
								+ ChatColor.RED + "" + RpgClassData.getMysteriousXp(p) + "/"
								+ MainCore.instance.rankCraft.mysteriousClass
										.getXpToLevelUp(RpgClassData.getMysteriousLevel(p)),
				ChatColor.RED + "" + RpgClassData.getMysteriousLevel(p) + " "
						+ getXpBar(p, MainCore.instance.rankCraft.mysteriousClass, 20) + " " + ChatColor.RED + ""
						+ nextLevel, mysteriousUnlocked, mysteriousEquipped);

		mysteriousItem.build();
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

		if (gui.checkItemTypeAndName(item, gui.close.getName(), gui.close.getType())) {
			p.closeInventory();
		}

		if(gui.checkItemTypeAndName(item, gui.next.getName(), gui.next.getType())) {
			p.closeInventory();
			MainCore.instance.rankCraft.rpgClassSelectGUI.open(p);
		}
		
	}

	public String getXpBar(Player p, RpgClass rpgClass, int bars) {
		StringBuilder b = new StringBuilder();
		b.append(ChatColor.BLUE + "[");
		String r = "|";
		String g = "|";
		int xp = (int) RpgClassData.getXp(rpgClass, p);
		// int maxLevel = rpgClass.getMaxLevel();

		int x = (xp * bars)
				/ (int) rpgClass.getXpToLevelUp(RpgClassData.getLevel(rpgClass, p));

		b.append(ChatColor.GREEN.toString());
		for (int i = 0; i < x; i++) {
			b.append(r);

		}

		b.append(ChatColor.GRAY.toString());
		for (int i = 0; i < (bars - x); i++) {
			b.append(g);

		}
		b.append(ChatColor.BLUE + "]");
		String bar = b.toString();

		return bar;
	}
}
