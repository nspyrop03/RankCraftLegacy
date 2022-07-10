package me.amc.rankcraft.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.stats.MobStats;

public class MobStatsGUI implements Listener {

	private GUI gui;
	private MobStats s;

	// public String title = "MobStats GUI";
	public String title = MainCore.instance.guiLang.getMobStatsGuiTitle();

	private List<ItemStack> skulls = new ArrayList<>();

	public MobStatsGUI() {
		s = MainCore.instance.rankCraft.mobStats;

		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance);
	}

	public void open(Player p) {
		initItems(p);

		gui = new GUI(title, p, 9 * 3);

		for (int i = 0; i < skulls.size(); i++) {
			gui.addItem(skulls.get(i), i);
		}

		gui.addItem(gui.close.getItem(), gui.getSize() - 1);
		gui.addItem(gui.previous.getItem(), gui.getSize() - 9);

		gui.openInventory();
	}

	private void initItems(Player p) {
		skulls.clear();
		String[] owners = new String[] { "MHF_Zombie", "MHF_Skeleton", "MHF_Creeper", "MHF_Enderman", "MHF_Cow",
				"MHF_Pig", "MHF_Horse", "MHF_Rabbit", "MHF_Villager", "MHF_Spider", "MHF_Slime", "MHF_Ghast",
				"MHF_Sheep", "MHF_Chicken" };
		/*
		 * String[] names = new String[] {"§7ZombieKills: §c"+s.getZombies(p),
		 * "§7SkeletonKills: §c"+s.getSkeletons(p), "§7CreeperKills: §c"
		 * +s.getCreepers(p),"§7EndermanKills: §c"+s.getEndermans(p),
		 * "§7CowKills: §c"+s.getCows(p), "§7PigKills: §c"+s.getPigs(p),
		 * "§7HorseKills: §c"+s.getHorses(p),"§7RabbitKills: §c"
		 * +s.getRabbits(p), "§7VillagerKills: §c"+s.getVillagers(p),
		 * "§7SpiderKills: §c"+s.getSpiders(p),"§7SlimeKills: §c"
		 * +s.getSlimes(p), "§7GhastKills: §c"+s.getGhasts(p),"§7SheepKills: §c"
		 * +s.getSheeps(p),"§7ChickenKills: §c"+s.getChickens(p)};
		 */

		String[] names = new String[] { getName("Zombie", s.getZombies(p)), getName("Skeleton", s.getSkeletons(p)),
				getName("Creeper", s.getCreepers(p)), getName("Enderman", s.getEndermans(p)),
				getName("Cow", s.getCows(p)), getName("Pig", s.getPigs(p)), getName("Horse", s.getHorses(p)),
				getName("Rabbit", s.getRabbits(p)), getName("Villager", s.getVillagers(p)),
				getName("Spider", s.getSpiders(p)), getName("Slime", s.getSlimes(p)), getName("Ghast", s.getGhasts(p)),
				getName("Sheep", s.getSheeps(p)), getName("Chicken", s.getChickens(p)) };

		for (int i = 0; i < owners.length; i++) {
			skulls.add(getSkull(owners[i], names[i], 1));
		}
	}

	private ItemStack getSkull(String skullOwner, String displayName, int quantity) {
		ItemStack skull = new ItemStack(Material.LEGACY_SKULL_ITEM, quantity, (byte) SkullType.PLAYER.ordinal());
		SkullMeta skullMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.LEGACY_SKULL_ITEM);
		skullMeta.setOwner(skullOwner);
		if (displayName != null) {
			skullMeta.setDisplayName(ChatColor.RESET + displayName);
		}
		skull.setItemMeta(skullMeta);
		return skull;
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
		if (gui.checkItemTypeAndName(item, gui.previous.getName(), gui.previous.getType())) {
			p.closeInventory();
			MainCore.instance.rankCraft.statsGUI.open(p);
		}
	}

	private String getName(String mob, int kills) {
		return MainCore.instance.guiLang.getItemMobHead(mob, kills);
	}
}
