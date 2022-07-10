package me.amc.rankcraft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class PermissionList {

	private List<Permission> permissions = new ArrayList<>();
	
	public Permission stats_permission;
	public Permission mobStats_permission;
	public Permission backpack_permission;
	public Permission giveTreasure_permission;
	public Permission giveXpCrystal_permission;
	public Permission giveManaCrystal_permission;
	public Permission giveGold_permission;
	public Permission giveYamlWeapons_permission;
	public Permission giveBlockpack_permission;
	public Permission giveItempack_permission;
	public Permission giveMixedpack_permission;
	public Permission packshop_permission;
	public Permission giveSpells_permission;
	public Permission skills_permission;
	public Permission quests_permission;
	public Permission setReward_permission;
	public Permission achievements_permission;
	public Permission giveSkeletonWizardEgg_permission;
	public Permission giveZombieMasterEgg_permission;
	public Permission giveZombieWarriorEgg_permission;
	public Permission backpackshop_permission;
	
	public Permission shop_permission;
	
	public Permission mobCreate_permission;
	public Permission mobSpawn_permission;
	public Permission giveMobSpawner_permission;
	public Permission placeMobSpawner_permission;
	
	public Permission classes_permission;
	
	public Permission leaderboard_permission;
	
	public Permission enchant_permission;
	public Permission setLootable_permission;
	public Permission treasure_permission;
	public Permission giveTreasure2_permission;
	
	public Permission giveSpecificSpell_permission;
	public Permission giveSpecificWeapon_permission;
	
	public Permission selectClassForOthers_permission;
	
	public Permission toggleScoreboard_permission;
	public Permission toggleScoreboardOthers_permission;
	
	public Permission itemCreate_permission;
	public Permission giveYamlArmors_permission;
	public Permission giveSpecificYamlArmor_permission;
	
	public Permission autoPickupGold_permission;
	
	public Permission giveClasspoints_permission;
	
	public PermissionList() {
		init();
		
		for(Permission perm : permissions) {
			Bukkit.getServer().getPluginManager().addPermission(perm);
		}
		
	}	
	
	private void init() {
		permissions.clear();
		
		stats_permission = new Permission("rankcraft.stats");
		permissions.add(stats_permission);
		mobStats_permission = new Permission("rankcraft.mobstats");
		permissions.add(mobStats_permission);
		backpack_permission = new Permission("rankcraft.backpack");
		permissions.add(backpack_permission);
		giveTreasure_permission = new Permission("rankcraft.give.treasure");
		permissions.add(giveTreasure_permission);
		giveXpCrystal_permission = new Permission("rankcraft.give.xpcrystal");
		permissions.add(giveXpCrystal_permission);
		giveManaCrystal_permission = new Permission("rankcraft.give.manacrystal");
		permissions.add(giveManaCrystal_permission);
		giveGold_permission = new Permission("rankcraft.give.gold");
		permissions.add(giveGold_permission);
		giveYamlWeapons_permission = new Permission("rankcraft.give.yamlweapons");
		permissions.add(giveYamlWeapons_permission);
		giveBlockpack_permission = new Permission("rankcraft.give.blockpack");
		permissions.add(giveBlockpack_permission);
		giveItempack_permission = new Permission("rankcraft.give.itempack");
		permissions.add(giveItempack_permission);
		giveMixedpack_permission = new Permission("rankcraft.give.mixedpack");
		permissions.add(giveMixedpack_permission);
		packshop_permission = new Permission("rankcraft.packshop");
		permissions.add(packshop_permission);
		giveSpells_permission = new Permission("rankcraft.give.spells");
		permissions.add(giveSpells_permission);
		skills_permission = new Permission("rankcraft.skills");
		permissions.add(skills_permission);
		quests_permission = new Permission("rankcraft.quests");
		permissions.add(quests_permission);
		setReward_permission = new Permission("rankcraft.setreward");
		permissions.add(setReward_permission);
		achievements_permission = new Permission("rankcraft.achievements");
		permissions.add(achievements_permission);
		giveSkeletonWizardEgg_permission = new Permission("rankcraft.give.boss1");
		permissions.add(giveSkeletonWizardEgg_permission);
		giveZombieMasterEgg_permission = new Permission("rankcraft.give.boss2");
		permissions.add(giveZombieMasterEgg_permission);
		giveZombieWarriorEgg_permission = new Permission("rankcraft.give.boss3");
		permissions.add(giveZombieWarriorEgg_permission);
		backpackshop_permission = new Permission("rankcraft.backpackshop");
		permissions.add(backpackshop_permission);
		
		shop_permission = new Permission("rankcraft.shop");
		permissions.add(shop_permission);
		
		mobCreate_permission = new Permission("rankcraft.mob.create");
		permissions.add(mobCreate_permission);
		mobSpawn_permission = new Permission("rankcraft.mob.spawn");
		permissions.add(mobSpawn_permission);
		giveMobSpawner_permission = new Permission("rankcraft.give.mobspawner");
		permissions.add(giveMobSpawner_permission);
		placeMobSpawner_permission = new Permission("rankcraft.place.mobspawner");
		permissions.add(placeMobSpawner_permission);
		
		classes_permission = new Permission("rankcraft.classes");
		permissions.add(classes_permission);
		
		leaderboard_permission = new Permission("rankcraft.leaderboard");
		permissions.add(leaderboard_permission);
		
		enchant_permission = new Permission("rankcraft.enchant");
		permissions.add(enchant_permission);
		setLootable_permission = new Permission("rankcraft.setlootable");
		permissions.add(setLootable_permission);
		treasure_permission = new Permission("rankcraft.treasure.make");
		permissions.add(treasure_permission);
		giveTreasure2_permission = new Permission("rankcraft.give.treasure2");
		permissions.add(giveTreasure2_permission);
		
		giveSpecificSpell_permission = new Permission("rankcraft.give.specific.spell");
		permissions.add(giveSpecificSpell_permission);
		giveSpecificWeapon_permission = new Permission("rankcraft.give.specific.weapon");
		permissions.add(giveSpecificWeapon_permission);
		
		selectClassForOthers_permission = new Permission("rankcraft.selectclass.others");
		permissions.add(selectClassForOthers_permission);
		
		toggleScoreboard_permission = new Permission("rankcraft.toggle.scoreboard");
		permissions.add(toggleScoreboard_permission);
		toggleScoreboardOthers_permission = new Permission("rankcraft.toggle.scoreboard.others");
		permissions.add(toggleScoreboardOthers_permission);
		
		itemCreate_permission = new Permission("rankcraft.item.create");
		permissions.add(itemCreate_permission);
		giveYamlArmors_permission = new Permission("rankcraft.give.armors");
		permissions.add(giveYamlArmors_permission);
		giveSpecificYamlArmor_permission = new Permission("rankcraft.give.specific.armor");
		permissions.add(giveSpecificYamlArmor_permission);
		
		autoPickupGold_permission = new Permission("rankcraft.gold.autopickup");
		permissions.add(autoPickupGold_permission);
		
		giveClasspoints_permission = new Permission("rankcraft.give.classpoints");
		permissions.add(giveClasspoints_permission);
		
	}
	
	public void sendNotPermissionMessage(Player p) {
	//	p.sendMessage(ChatColor.RED+"You do not have permission to do this!");
		p.sendMessage(MainCore.instance.language.getNoPermissionMessage());
	}
}
