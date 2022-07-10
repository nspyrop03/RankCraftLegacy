package me.amc.rankcraft.rpgitem;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.damage.DamageSystem;
import me.amc.rankcraft.gui.GUI;
import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.RCUtils;

public class ItemCreator implements Listener {

	// GUIs for item, armor creator \\
	
	// Select if you want to make a RpgItem or RpgArmor
	private GUI selectTypeGUI; 
	// Add armor item GUI
	private GUI armorItemGUI;
	// Choose armor rarity
	private GUI armorRarityGUI;
	// Make armor recipe
	private GUI armorRecipeGUI;
	// Set armor defense
	private GUI armorDefenseGUI;
	// Finish/Preview
	private GUI armorFinishGUI;
	
	private GUI weaponItemGUI;
	private GUI weaponRarityGUI;
	private GUI weaponRecipeGUI;
	private GUI weaponStatsGUI;
	private GUI weaponFinishGUI;
	
	public String selectTypeTitle = "Select Item Type";
	
	public String armorItemTitle = "Add Armor Item";
	public String armorRarityTitle = "Choose Armor Rarity";
	public String armorRecipeTitle = "Make Armor Recipe";
	public String armorDefenseTitle = "Set Armor Defense";
	public String armorFinishTitle = "Armor Finish/Preview";
	
	public String weaponItemTitle = "Add Weapon Item";
	public String weaponRarityTitle = "Choose Weapon Rarity";
	public String weaponRecipeTitle = "Make WeaponRecipe";
	public String weaponStatsTitle = "Set Weapon Stats";
	public String weaponFinishTitle = "Weapon Finish/Preview";
	
	public final String SELECT_TYPE_STAGE = "TYPE";
	
	public final String ARMOR_ITEM_STAGE = "ARMOR_ITEM";
	public final String ARMOR_RARITY_STAGE = "ARMOR_RARITY";
	public final String ARMOR_RECIPE_STAGE = "ARMOR_RECIPE";
	public final String ARMOR_DEFENSE_STAGE = "ARMOR_DEFENSE";
	public final String ARMOR_FINISH_STAGE = "ARMOR_FINISH";
	
	public final String WEAPON_ITEM_STAGE = "WEAPON_ITEM";
	public final String WEAPON_RARITY_STAGE = "WEAPON_RARITY";
	public final String WEAPON_RECIPE_STAGE = "WEAPON_RECIPE";
	public final String WEAPON_STATS_STAGE = "WEAPON_STATS";
	public final String WEAPON_FINISH_STAGE = "WEAPON_FINISH";
	
	public String[] stages = {
			ARMOR_ITEM_STAGE, ARMOR_RARITY_STAGE, ARMOR_RECIPE_STAGE, ARMOR_DEFENSE_STAGE, ARMOR_FINISH_STAGE,
			WEAPON_ITEM_STAGE, WEAPON_RARITY_STAGE, WEAPON_RECIPE_STAGE, WEAPON_STATS_STAGE, WEAPON_FINISH_STAGE
	};
	
	// GUI items
	private CustomItem brownGlass;
	private CustomItem selectInfo;
	private CustomItem selectWeapon;
	private CustomItem selectArmor;
	private CustomItem commonRarity;
	private CustomItem rareRarity;
	private CustomItem epicRarity;
	private CustomItem legendaryRarity;
	private CustomItem recipeInfo;
	private CustomItem defense01;
	private CustomItem defense05;
	private CustomItem defense1;
	private CustomItem defense5;
	private CustomItem defense10;
	private CustomItem previewItem;
	private CustomItem finishItem;
	
	private CustomItem changeToSwordRecipe;
	private CustomItem minDamageInfo;
	private CustomItem[] minDamageItems = new CustomItem[5];
	private CustomItem maxDamageInfo;
	private CustomItem[] maxDamageItems = new CustomItem[5];
	private CustomItem criticalLuckInfo;
	private CustomItem[] criticalLuckItems = new CustomItem[5];
	private double[] damageN = {0.1, 0.5, 1.0, 5.0, 10.0};
	private int[] luckN = {1, 5, 10, 20, 50}; 
	private ItemStack previewWeapon;
	
	private YamlBase base;
	private YamlArmor tempArmor;
	private YamlItem tempWeapon;
	
	private int[] tableSlots = {21, 22, 23, 30, 31, 32, 39, 40, 41};
	private int[] swordTableSlots = {22, 31, 40};
	
	public ItemCreator() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance); 
		initGeneralItems();
	}
	
	public void openSelectTypeGUI(Player p, YamlBase b) {
		initSelectType();
		base = b;
		selectTypeGUI = new GUI(selectTypeTitle, p, 9*6);
		for(int i = 0; i < 9*6; i++) 
			selectTypeGUI.addItem(brownGlass.getItem(), i);
		selectTypeGUI.addItem(selectInfo.getItem(), 13);
		selectTypeGUI.addItem(selectWeapon.getItem(), 29);
		selectTypeGUI.addItem(selectArmor.getItem(), 33);
		selectTypeGUI.openInventory();
	}
	
	private void openArmorItemGUI(Player p) {
		initArmorItem();
		armorItemGUI = new GUI(armorItemTitle, p, 9*6);
		for(int i = 0; i < 9*6; i++) 
			armorItemGUI.addItem(brownGlass.getItem(), i);
		armorItemGUI.addItem(selectInfo.getItem(), 13);
		
		if(!tempArmor.isFromCreator() || (tempArmor.getItemFromCreator()==null || tempArmor.getItemFromCreator().getType() == Material.AIR))
			armorItemGUI.addItem(tempArmor.getRpgArmor().getCustomItem().getItem(), 31);
		else
			armorItemGUI.addItem(tempArmor.getItemFromCreator(), 31);
		armorItemGUI.addItem(armorItemGUI.next.getItem(), 9*6-1);
		armorItemGUI.openInventory();
	}
	
	private void openArmorRarityGUI(Player p) {
		initArmorRarity();
		armorRarityGUI = new GUI(armorRarityTitle, p, 9*6);
		for(int i = 0; i < 9*6; i++) 
			armorRarityGUI.addItem(brownGlass.getItem(), i);
		armorRarityGUI.addItem(selectInfo.getItem(), 13);
		armorRarityGUI.addItem(commonRarity.getItem(), 28);
		armorRarityGUI.addItem(rareRarity.getItem(), 30);
		armorRarityGUI.addItem(epicRarity.getItem(), 32);
		armorRarityGUI.addItem(legendaryRarity.getItem(), 34);
		armorRarityGUI.addItem(armorRarityGUI.previous.getItem(), 9*6-9);
		armorRarityGUI.addItem(armorRarityGUI.next.getItem(), 9*6-1);
		armorRarityGUI.openInventory();
	}
	
	private void openArmorRecipeGUI(Player p) {
		initArmorRecipe();
		armorRecipeGUI = new GUI(armorRecipeTitle, p, 9*6);
		for(int i = 0; i < 9*6; i++) 
			armorRecipeGUI.addItem(brownGlass.getItem(), i);
		armorRecipeGUI.addItem(recipeInfo.getItem(), 13);
		if(!tempArmor.isHasRecipe() || tempArmor.getRecipe().isEmpty()) {
			armorRecipeGUI.addItem(new ItemStack(Material.AIR), 21);
			armorRecipeGUI.addItem(new ItemStack(Material.AIR), 22);
			armorRecipeGUI.addItem(new ItemStack(Material.AIR), 23);
			armorRecipeGUI.addItem(new ItemStack(Material.AIR), 30);
			armorRecipeGUI.addItem(new ItemStack(Material.AIR), 31);
			armorRecipeGUI.addItem(new ItemStack(Material.AIR), 32);
			armorRecipeGUI.addItem(new ItemStack(Material.AIR), 39);
			armorRecipeGUI.addItem(new ItemStack(Material.AIR), 40);
			armorRecipeGUI.addItem(new ItemStack(Material.AIR), 41);
		} else {
			armorRecipeGUI.addItem(new ItemStack(tempArmor.getRecipeMaterials().get(0)), 21);
			armorRecipeGUI.addItem(new ItemStack(tempArmor.getRecipeMaterials().get(1)), 22);
			armorRecipeGUI.addItem(new ItemStack(tempArmor.getRecipeMaterials().get(2)), 23);
			armorRecipeGUI.addItem(new ItemStack(tempArmor.getRecipeMaterials().get(3)), 30);
			armorRecipeGUI.addItem(new ItemStack(tempArmor.getRecipeMaterials().get(4)), 31);
			armorRecipeGUI.addItem(new ItemStack(tempArmor.getRecipeMaterials().get(5)), 32);
			armorRecipeGUI.addItem(new ItemStack(tempArmor.getRecipeMaterials().get(6)), 39);
			armorRecipeGUI.addItem(new ItemStack(tempArmor.getRecipeMaterials().get(7)), 40);
			armorRecipeGUI.addItem(new ItemStack(tempArmor.getRecipeMaterials().get(8)), 41);
		}
		
		armorRecipeGUI.addItem(armorRecipeGUI.previous.getItem(), 9*6-9);
		armorRecipeGUI.addItem(armorRecipeGUI.next.getItem(), 9*6-1);
		armorRecipeGUI.openInventory();
	}
	
	private void openArmorDefenseGUI(Player p) {
		initArmorDefense();
		armorDefenseGUI = new GUI(armorDefenseTitle, p, 9*6);
		for(int i = 0; i < 9*6; i++) 
			armorDefenseGUI.addItem(brownGlass.getItem(), i);
		armorDefenseGUI.addItem(selectInfo.getItem(), 13);
		armorDefenseGUI.addItem(defense01.getItem(), 29);
		armorDefenseGUI.addItem(defense05.getItem(), 30);
		armorDefenseGUI.addItem(defense1.getItem(), 31);
		armorDefenseGUI.addItem(defense5.getItem(), 32);
		armorDefenseGUI.addItem(defense10.getItem(), 33);
		armorDefenseGUI.addItem(armorDefenseGUI.previous.getItem(), 9*6-9);
		armorDefenseGUI.addItem(armorDefenseGUI.next.getItem(), 9*6-1);
		armorDefenseGUI.openInventory();
	}
	
	private void openArmorFinishGUI(Player p) {
		initArmorFinish();
		armorFinishGUI = new GUI(armorFinishTitle, p, 9*6);
		for(int i = 0; i < 9*6; i++) 
			armorFinishGUI.addItem(brownGlass.getItem(), i);
		armorFinishGUI.addItem(selectInfo.getItem(), 13);
		armorFinishGUI.addItem(previewItem.getItem(), 31);
		armorFinishGUI.addItem(finishItem.getItem(), 49);
		armorFinishGUI.addItem(armorFinishGUI.previous.getItem(), 9*6-9);
		armorFinishGUI.openInventory();
	}
	
	private void initGeneralItems() {
		brownGlass = new CustomItem(Material.BROWN_STAINED_GLASS_PANE, " ").build();
	}
	
	private void initSelectType() {
		selectInfo = new CustomItem(Material.OAK_SIGN, ChatColor.AQUA+"Select Type Info", ChatColor.GREEN+"Select the type of the item.").build();
		selectWeapon = new CustomItem(Material.IRON_SWORD, ChatColor.RED+"Weapon").buildWithNoFlags();
		selectArmor = new CustomItem(Material.CHAINMAIL_CHESTPLATE, ChatColor.BLUE+"Armor").buildWithNoFlags();
	}
	
	private void initArmorItem() {
		selectInfo = new CustomItem(Material.OAK_SIGN, ChatColor.AQUA+"Info", ChatColor.GREEN+"Place the armor you want to customize.").build();
	}
	
	private void initArmorRarity() {
		//System.out.println(Rarity.COMMON.name()+" - "+tempArmor.getRarity().toUpperCase());
		selectInfo = new CustomItem(Material.OAK_SIGN, ChatColor.AQUA+"Info", ChatColor.GREEN+"Choose armor rarity.").build();
		commonRarity = new CustomItem(Material.DIRT, Rarity.COMMON.getColor()+""+ChatColor.BOLD+"Common").build(tempArmor.getRarity().toUpperCase().equals(Rarity.COMMON.name()));
		rareRarity = new CustomItem(Material.IRON_BLOCK, Rarity.RARE.getColor()+""+ChatColor.BOLD+"Rare").build(tempArmor.getRarity().toUpperCase().equals(Rarity.RARE.name()));
		epicRarity = new CustomItem(Material.GOLD_BLOCK, Rarity.EPIC.getColor()+""+ChatColor.BOLD+"Epic").build(tempArmor.getRarity().toUpperCase().equals(Rarity.EPIC.name()));
		legendaryRarity = new CustomItem(Material.EMERALD_BLOCK, Rarity.LEGENDARY.getColor()+""+ChatColor.BOLD+"Legendary").build(tempArmor.getRarity().toUpperCase().equals(Rarity.LEGENDARY.name()));
	}
	
	private void initArmorRecipe() {
		recipeInfo = new CustomItem(Material.CRAFTING_TABLE, ChatColor.AQUA+"Recipe Info", ChatColor.GREEN+"This is your crafting table.");
		recipeInfo.addLores(ChatColor.GREEN+"Place the materials to make the recipe.");
		recipeInfo.build();
	}
	
	private void initArmorDefense() {
		selectInfo = new CustomItem(Material.OAK_SIGN, ChatColor.AQUA+"Info", ChatColor.GREEN+"Set armor defense.");
		selectInfo.addLores(ChatColor.GREEN+"Left-click to add defense.", ChatColor.GREEN+"Right-click to remove defense.", ChatColor.BLUE+"Current Armor: "+RCUtils.round(tempArmor.getDefense(), 1));
		selectInfo.build();
		defense01 = new CustomItem(Material.SHIELD, ChatColor.YELLOW+"+/- 0.1 defense", ChatColor.BLUE+"Current Armor: "+RCUtils.round(tempArmor.getDefense(), 1)).build();
		defense05 = new CustomItem(Material.SHIELD, ChatColor.YELLOW+"+/- 0.5 defense", ChatColor.BLUE+"Current Armor: "+RCUtils.round(tempArmor.getDefense(), 1)).build();
		defense1 = new CustomItem(Material.SHIELD, ChatColor.YELLOW+"+/- 1.0 defense", ChatColor.BLUE+"Current Armor: "+RCUtils.round(tempArmor.getDefense(), 1)).build();
		defense5 = new CustomItem(Material.SHIELD, ChatColor.YELLOW+"+/- 5.0 defense", ChatColor.BLUE+"Current Armor: "+RCUtils.round(tempArmor.getDefense(), 1)).build();
		defense10 = new CustomItem(Material.SHIELD, ChatColor.YELLOW+"+/- 10.0 defense", ChatColor.BLUE+"Current Armor: "+RCUtils.round(tempArmor.getDefense(), 1)).build();
	}
	
	private void initArmorFinish() {
		selectInfo = new CustomItem(Material.OAK_SIGN, ChatColor.AQUA+"Armor Preview/Finish").build();
		previewItem = tempArmor.getRpgArmor().build();
		finishItem = new CustomItem(Material.EMERALD, ChatColor.GREEN+"Finish").build();
	}
	
	private void openWeaponItemGUI(Player p) {
		initWeaponItem();
		weaponItemGUI = new GUI(weaponItemTitle, p, 9*6);
		for(int i = 0; i < 9*6; i++) 
			weaponItemGUI.addItem(brownGlass.getItem(), i);
		weaponItemGUI.addItem(selectInfo.getItem(), 13);
		
		if(!tempWeapon.isFromCreator() || (tempWeapon.getItemFromCreator()==null || tempWeapon.getItemFromCreator().getType() == Material.AIR))
			weaponItemGUI.addItem(tempWeapon.getBuiltRpgItem().getItem(), 31);
		else
			weaponItemGUI.addItem(tempWeapon.getItemFromCreator(), 31);
		weaponItemGUI.addItem(weaponItemGUI.next.getItem(), 9*6-1);
		weaponItemGUI.openInventory();
	}
	
	private void openWeaponRarityGUI(Player p) {
		initWeaponRarity();
		weaponRarityGUI = new GUI(weaponRarityTitle, p, 9*6);
		for(int i = 0; i < 9*6; i++) 
			weaponRarityGUI.addItem(brownGlass.getItem(), i);
		weaponRarityGUI.addItem(selectInfo.getItem(), 13);
		weaponRarityGUI.addItem(commonRarity.getItem(), 28);
		weaponRarityGUI.addItem(rareRarity.getItem(), 30);
		weaponRarityGUI.addItem(epicRarity.getItem(), 32);
		weaponRarityGUI.addItem(legendaryRarity.getItem(), 34);
		weaponRarityGUI.addItem(weaponRarityGUI.previous.getItem(), 9*6-9);
		weaponRarityGUI.addItem(weaponRarityGUI.next.getItem(), 9*6-1);
		weaponRarityGUI.openInventory();
	}
	
	private void openWeaponRecipeGUI(Player p) {
		initWeaponRecipe();
		weaponRecipeGUI = new GUI(weaponRecipeTitle, p, 9*6);
		for(int i = 0; i < 9*6; i++) 
			weaponRecipeGUI.addItem(brownGlass.getItem(), i);
		weaponRecipeGUI.addItem(recipeInfo.getItem(), 13);
		weaponRecipeGUI.addItem(changeToSwordRecipe.getItem(), 27);
		
		if(!tempWeapon.isHasRecipe() || tempWeapon.getRecipe().isEmpty()) {
			if(!tempWeapon.isSwordRecipe()) {
				weaponRecipeGUI.addItem(new ItemStack(Material.AIR), 21);
				weaponRecipeGUI.addItem(new ItemStack(Material.AIR), 22);
				weaponRecipeGUI.addItem(new ItemStack(Material.AIR), 23);
				weaponRecipeGUI.addItem(new ItemStack(Material.AIR), 30);
				weaponRecipeGUI.addItem(new ItemStack(Material.AIR), 31);
				weaponRecipeGUI.addItem(new ItemStack(Material.AIR), 32);
				weaponRecipeGUI.addItem(new ItemStack(Material.AIR), 39);
				weaponRecipeGUI.addItem(new ItemStack(Material.AIR), 40);
				weaponRecipeGUI.addItem(new ItemStack(Material.AIR), 41);
			} else {
				weaponRecipeGUI.addItem(new ItemStack(Material.AIR), 22);
				weaponRecipeGUI.addItem(new ItemStack(Material.AIR), 31);
				weaponRecipeGUI.addItem(new ItemStack(Material.AIR), 40);
			}
		} else {
			if(!tempWeapon.isSwordRecipe()) {
				weaponRecipeGUI.addItem(new ItemStack(tempWeapon.getRecipeMaterials().get(0)), 21);
				weaponRecipeGUI.addItem(new ItemStack(tempWeapon.getRecipeMaterials().get(1)), 22);
				weaponRecipeGUI.addItem(new ItemStack(tempWeapon.getRecipeMaterials().get(2)), 23);
				weaponRecipeGUI.addItem(new ItemStack(tempWeapon.getRecipeMaterials().get(3)), 30);
				weaponRecipeGUI.addItem(new ItemStack(tempWeapon.getRecipeMaterials().get(4)), 31);
				weaponRecipeGUI.addItem(new ItemStack(tempWeapon.getRecipeMaterials().get(5)), 32);
				weaponRecipeGUI.addItem(new ItemStack(tempWeapon.getRecipeMaterials().get(6)), 39);
				weaponRecipeGUI.addItem(new ItemStack(tempWeapon.getRecipeMaterials().get(7)), 40);
				weaponRecipeGUI.addItem(new ItemStack(tempWeapon.getRecipeMaterials().get(8)), 41);
			} else {
				weaponRecipeGUI.addItem(new ItemStack(tempWeapon.getSwordRecipeMaterials().get(0)), 22);
				weaponRecipeGUI.addItem(new ItemStack(tempWeapon.getSwordRecipeMaterials().get(1)), 31);
				weaponRecipeGUI.addItem(new ItemStack(tempWeapon.getSwordRecipeMaterials().get(2)), 40);
			}
		}
		
		weaponRecipeGUI.addItem(weaponRecipeGUI.previous.getItem(), 9*6-9);
		weaponRecipeGUI.addItem(weaponRecipeGUI.next.getItem(), 9*6-1);
		weaponRecipeGUI.openInventory();
	}
	
	private void openWeaponStatsGUI(Player p) {
		initWeaponStats();
		weaponStatsGUI = new GUI(weaponStatsTitle, p, 9*6);
		for(int i = 0; i < 9*6; i++) 
			weaponStatsGUI.addItem(brownGlass.getItem(), i);
		weaponStatsGUI.addItem(minDamageInfo.getItem(), 1);
		weaponStatsGUI.addItem(maxDamageInfo.getItem(), 19);
		weaponStatsGUI.addItem(criticalLuckInfo.getItem(), 37);
		for(int i = 0; i < 5; i++) {
			weaponStatsGUI.addItem(minDamageItems[i].getItem(), 3+i);
			weaponStatsGUI.addItem(maxDamageItems[i].getItem(), 21+i);
			weaponStatsGUI.addItem(criticalLuckItems[i].getItem(), 39+i);
		}
		weaponStatsGUI.addItem(weaponStatsGUI.previous.getItem(), 9*6-9);
		weaponStatsGUI.addItem(weaponStatsGUI.next.getItem(), 9*6-1);
		weaponStatsGUI.openInventory();
	}
	
	private void openWeaponFinishGUI(Player p) {
		initWeaponFinish();
		weaponFinishGUI = new GUI(weaponFinishTitle, p, 9*6);
		for(int i = 0; i < 9*6; i++) 
			weaponFinishGUI.addItem(brownGlass.getItem(), i);
		weaponFinishGUI.addItem(selectInfo.getItem(), 13);
		weaponFinishGUI.addItem(previewWeapon, 31);
		weaponFinishGUI.addItem(finishItem.getItem(), 49);
		weaponFinishGUI.addItem(weaponFinishGUI.previous.getItem(), 9*5);
		weaponFinishGUI.openInventory();
	}
	
	private void initWeaponItem() {
		selectInfo = new CustomItem(Material.OAK_SIGN, ChatColor.AQUA+"Info", ChatColor.GREEN+"Place the weapon you want to customize.").build();
	}
	
	private void initWeaponRarity() {
		selectInfo = new CustomItem(Material.OAK_SIGN, ChatColor.AQUA+"Info", ChatColor.GREEN+"Choose weapon rarity.").build();
		commonRarity = new CustomItem(Material.DIRT, Rarity.COMMON.getColor()+""+ChatColor.BOLD+"Common").build(tempWeapon.getRarity().toUpperCase().equals(Rarity.COMMON.name()));
		rareRarity = new CustomItem(Material.IRON_BLOCK, Rarity.RARE.getColor()+""+ChatColor.BOLD+"Rare").build(tempWeapon.getRarity().toUpperCase().equals(Rarity.RARE.name()));
		epicRarity = new CustomItem(Material.GOLD_BLOCK, Rarity.EPIC.getColor()+""+ChatColor.BOLD+"Epic").build(tempWeapon.getRarity().toUpperCase().equals(Rarity.EPIC.name()));
		legendaryRarity = new CustomItem(Material.EMERALD_BLOCK, Rarity.LEGENDARY.getColor()+""+ChatColor.BOLD+"Legendary").build(tempWeapon.getRarity().toUpperCase().equals(Rarity.LEGENDARY.name()));
	}
	
	private void initWeaponRecipe() {
		initArmorRecipe();
		changeToSwordRecipe = new CustomItem(Material.WOODEN_SWORD, ChatColor.GREEN+"Change Recipe Type", ChatColor.YELLOW+"Click to change recipe type.");
		String t = "Default 3x3";
		if(tempWeapon.isSwordRecipe()) t = "Sword 1x3";
		changeToSwordRecipe.addLores(ChatColor.YELLOW+"Default 3x3 or Sword 1x3", ChatColor.YELLOW+"Current: "+ChatColor.RED+""+t);
		changeToSwordRecipe.build();
	}
	
	private void initWeaponStats() {
		minDamageInfo = new CustomItem(Material.OAK_SIGN, ChatColor.AQUA+"MinDamage", ChatColor.GREEN+"Set weapon MinDamage");
		minDamageInfo.addLores(ChatColor.GREEN+"Left-Click to add to MinDamage", ChatColor.GREEN+"Right-Click to remove from MinDamage");
		minDamageInfo.build();
		maxDamageInfo = new CustomItem(Material.OAK_SIGN, ChatColor.AQUA+"MaxDamage", ChatColor.GREEN+"Set weapon MaxDamage");
		maxDamageInfo.addLores(ChatColor.GREEN+"Left-Click to add to MaxDamage", ChatColor.GREEN+"Right-Click to remove from MaxDamage");
		maxDamageInfo.build();
		criticalLuckInfo = new CustomItem(Material.OAK_SIGN, ChatColor.AQUA+"CriticalHit", ChatColor.GREEN+"Set weapon CriticalHit percentage");
		criticalLuckInfo.addLores(ChatColor.GREEN+"Left-Click to add to CriticalHit", ChatColor.GREEN+"Right-Click to remove from CriticalHit");
		criticalLuckInfo.build();
		for(int i = 0; i < 5; i++) {
			minDamageItems[i] = new CustomItem(Material.WOODEN_SWORD, ChatColor.YELLOW+"+/-"+damageN[i]+" MinDamage", ChatColor.AQUA+"Current MinDamage: "+tempWeapon.getMinDamage()).build();
			maxDamageItems[i] = new CustomItem(Material.IRON_SWORD, ChatColor.YELLOW+"+/-"+damageN[i]+" MaxDamage", ChatColor.AQUA+"Current MaxDamage: "+tempWeapon.getMaxDamage()).build();
			criticalLuckItems[i] = new CustomItem(Material.EMERALD, ChatColor.YELLOW+"+/-"+luckN[i]+" CriticalHit", ChatColor.AQUA+"Current CriticalHit: "+tempWeapon.getCriticalLuck()+"%").build();
		}
	}
	
	private void initWeaponFinish() {
		selectInfo = new CustomItem(Material.OAK_SIGN, ChatColor.AQUA+"Weapon Preview/Finish").build();
		previewWeapon = tempWeapon.getBuiltRpgItem().getItem();
		finishItem = new CustomItem(Material.EMERALD, ChatColor.GREEN+"Finish").build();
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getView().getTitle().equals(selectTypeTitle)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			
			Player p = (Player) e.getWhoClicked();
			ItemStack clicked = e.getCurrentItem();
			if(selectTypeGUI.checkItemWithCustomItem(clicked, selectArmor)) {
				YamlArmor a = new YamlArmor(base.fileName+".armor");
				a.setName(base.name);
				a.setMinLevel(base.minLevel);
				a.save();
				//MainCore.instance.rankCraft.reloadYamlArmors();
				tempArmor = a;
				openArmorItemGUI(p);
			}
			
			// TO-DO ADD RPG WEAPON OPTION
			if(selectTypeGUI.checkItemWithCustomItem(clicked, selectWeapon)) {
				YamlItem w = new YamlItem(base.fileName);
				w.setName(base.name);
				w.setMinLevel(base.minLevel);
				w.save();
				tempWeapon = w;
				openWeaponItemGUI(p);
			}
			
		}
		
		if(e.getView().getTitle().equals(armorItemTitle)) {
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
				Player p = (Player) e.getWhoClicked();
				ItemStack item = e.getCurrentItem();
			
				if(armorItemGUI.checkItemWithCustomItem(item, brownGlass) 
						|| armorItemGUI.checkItemWithCustomItem(item, selectInfo)
						/*|| !DamageSystem.isArmorPiece(item)*/) {
					e.setCancelled(true);
					return;
				}
				
				if(e.getClickedInventory().getType() == InventoryType.PLAYER) 
					if(!DamageSystem.isArmorPiece(item))
						e.setCancelled(true);
				
				if(armorItemGUI.checkItemWithCustomItem(item, armorItemGUI.next)) {
					e.setCancelled(true);
					if(e.getClickedInventory().getItem(31) != null
							&& e.getClickedInventory().getItem(31).getType() != Material.AIR
							&& DamageSystem.isArmorPiece(e.getClickedInventory().getItem(31))) {
						//Ok to continue
						//p.sendMessage("OK to continue");
						tempArmor.setFromCreator(true);
						tempArmor.setItemFromCreator(e.getClickedInventory().getItem(31));
						tempArmor.save();
						//MainCore.instance.rankCraft.reloadYamlArmors();
						openArmorRarityGUI(p);
					}
						
				}
				
			}
		}
		
		if(e.getView().getTitle().equals(weaponItemTitle)) {
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
				Player p = (Player) e.getWhoClicked();
				ItemStack item = e.getCurrentItem();
			
				if(weaponItemGUI.checkItemWithCustomItem(item, brownGlass) 
						|| weaponItemGUI.checkItemWithCustomItem(item, selectInfo)) {
					e.setCancelled(true);
					return;
				}
				
				if(weaponItemGUI.checkItemWithCustomItem(item, weaponItemGUI.next)) {
					e.setCancelled(true);
					if(e.getClickedInventory().getItem(31) != null
							&& e.getClickedInventory().getItem(31).getType() != Material.AIR) {
						tempWeapon.setFromCreator(true);
						tempWeapon.setItemFromCreator(e.getClickedInventory().getItem(31));
						tempWeapon.save();
						openWeaponRarityGUI(p);
					}
						
				}
				
			}
		}
		
		if(e.getView().getTitle().equals(armorRarityTitle)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			Player p = (Player) e.getWhoClicked();
			ItemStack item = e.getCurrentItem();
			
			if(armorRarityGUI.checkItemWithCustomItem(item, commonRarity)) {
				tempArmor.setRarity(Rarity.COMMON);
				tempArmor.save();
				updateArmorRarity(p);
			}
			if(armorRarityGUI.checkItemWithCustomItem(item, rareRarity)) {
				tempArmor.setRarity(Rarity.RARE);
				tempArmor.save();
				updateArmorRarity(p);
			}
			if(armorRarityGUI.checkItemWithCustomItem(item, epicRarity)) {
				tempArmor.setRarity(Rarity.EPIC);
				tempArmor.save();
				updateArmorRarity(p);
			}
			if(armorRarityGUI.checkItemWithCustomItem(item, legendaryRarity)) {
				tempArmor.setRarity(Rarity.LEGENDARY);
				tempArmor.save();
				updateArmorRarity(p);
			}
			if(armorRarityGUI.checkItemWithCustomItem(item, armorRarityGUI.previous)) {
				openArmorItemGUI(p);
			}
			if(armorRarityGUI.checkItemWithCustomItem(item, armorRarityGUI.next)) {
				openArmorRecipeGUI(p);
			}
		}
		
		if(e.getView().getTitle().equals(weaponRarityTitle)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			Player p = (Player) e.getWhoClicked();
			ItemStack item = e.getCurrentItem();
			
			if(weaponRarityGUI.checkItemWithCustomItem(item, commonRarity)) {
				tempWeapon.setRarity(Rarity.COMMON);
				tempWeapon.save();
				updateWeaponRarity(p);
			}
			if(weaponRarityGUI.checkItemWithCustomItem(item, rareRarity)) {
				tempWeapon.setRarity(Rarity.RARE);
				tempWeapon.save();
				updateWeaponRarity(p);
			}
			if(weaponRarityGUI.checkItemWithCustomItem(item, epicRarity)) {
				tempWeapon.setRarity(Rarity.EPIC);
				tempWeapon.save();
				updateWeaponRarity(p);
			}
			if(weaponRarityGUI.checkItemWithCustomItem(item, legendaryRarity)) {
				tempWeapon.setRarity(Rarity.LEGENDARY);
				tempWeapon.save();
				updateWeaponRarity(p);
			}
			if(weaponRarityGUI.checkItemWithCustomItem(item, weaponRarityGUI.previous)) {
				openWeaponItemGUI(p);
			}
			if(weaponRarityGUI.checkItemWithCustomItem(item, weaponRarityGUI.next)) {
				openWeaponRecipeGUI(p);
			}
		}
		
		if(e.getView().getTitle().equals(armorRecipeTitle)) {
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
				Player p = (Player) e.getWhoClicked();
				ItemStack item = e.getCurrentItem();
				Inventory inv = e.getClickedInventory();
				
				if(armorRecipeGUI.checkItemWithCustomItem(item, brownGlass)
						|| armorRecipeGUI.checkItemWithCustomItem(item, recipeInfo)) {
					e.setCancelled(true);
					return;
				}
			
				if(armorRecipeGUI.checkItemWithCustomItem(item, armorRecipeGUI.previous)) {
					e.setCancelled(true);
					if(!isTableEmpty(e.getClickedInventory())) {
						tempArmor.setHasRecipe(true);
						tempArmor.setRecipe(getStringRecipe(inv));
					} else {
						tempArmor.setHasRecipe(false);
					}
					tempArmor.save();
					openArmorRarityGUI(p);
				}
				
				if(armorRecipeGUI.checkItemWithCustomItem(item, armorRecipeGUI.next)) {
					e.setCancelled(true);
					if(!isTableEmpty(e.getClickedInventory())) {
						tempArmor.setHasRecipe(true);
						tempArmor.setRecipe(getStringRecipe(inv));
					} else {
						tempArmor.setHasRecipe(false);
					}
					tempArmor.save();
					openArmorDefenseGUI(p);
				}
				
			}
		}
		
		if(e.getView().getTitle().equals(weaponRecipeTitle)) {
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
				Player p = (Player) e.getWhoClicked();
				ItemStack item = e.getCurrentItem();
				Inventory inv = e.getClickedInventory();
				
				if(weaponRecipeGUI.checkItemWithCustomItem(item, brownGlass)
						|| weaponRecipeGUI.checkItemWithCustomItem(item, recipeInfo)) {
					e.setCancelled(true);
					return;
				}
				
				if(weaponRecipeGUI.checkItemWithCustomItem(item, changeToSwordRecipe)) {
					tempWeapon.setSwordRecipe(!tempWeapon.isSwordRecipe());
					tempWeapon.save();
					updateWeaponRecipe(p);
				}
			
				if(weaponRecipeGUI.checkItemWithCustomItem(item, weaponRecipeGUI.previous)) {
					e.setCancelled(true);
					if(!tempWeapon.isSwordRecipe()) {
						if(!isTableEmpty(e.getClickedInventory())) {
							tempWeapon.setHasRecipe(true);
							tempWeapon.setRecipe(getStringRecipe(inv));
						} else {
							tempWeapon.setHasRecipe(false);
						}
					} else {
						if(!isSwordTableEmpty(e.getClickedInventory())) {
							tempWeapon.setHasRecipe(true);
							tempWeapon.setRecipe(getStringSwordRecipe(inv));
						} else {
							tempWeapon.setHasRecipe(false);
						}
					}
					tempWeapon.save();
					openWeaponRarityGUI(p);
				}
				
				if(weaponRecipeGUI.checkItemWithCustomItem(item, weaponRecipeGUI.next)) {
					e.setCancelled(true);
					if(!tempWeapon.isSwordRecipe()) {
						if(!isTableEmpty(e.getClickedInventory())) {
							tempWeapon.setHasRecipe(true);
							tempWeapon.setRecipe(getStringRecipe(inv));
						} else {
							tempWeapon.setHasRecipe(false);
						}
					} else {
						if(!isSwordTableEmpty(e.getClickedInventory())) {
							tempWeapon.setHasRecipe(true);
							tempWeapon.setRecipe(getStringSwordRecipe(inv));
						} else {
							tempWeapon.setHasRecipe(false);
						}
					}
					tempWeapon.save();
					openWeaponStatsGUI(p);
				}
				
			}
		}
		
		if(e.getView().getTitle().equals(armorDefenseTitle)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			Player p = (Player) e.getWhoClicked();
			ItemStack item = e.getCurrentItem();
			
			if(armorDefenseGUI.checkItemWithCustomItem(item, defense01)) {
				double a = 0.1;
				if(e.getClick() == ClickType.RIGHT) a = -0.1;
				tempArmor.setDefense(tempArmor.getDefense()+a);
				tempArmor.save();
				updateArmorDefense(p);
			}
			if(armorDefenseGUI.checkItemWithCustomItem(item, defense05)) {
				double a = 0.5;
				if(e.getClick() == ClickType.RIGHT) a = -0.5;
				tempArmor.setDefense(tempArmor.getDefense()+a);
				tempArmor.save();
				updateArmorDefense(p);
			}
			if(armorDefenseGUI.checkItemWithCustomItem(item, defense1)) {
				double a = 1.0;
				if(e.getClick() == ClickType.RIGHT) a = -1.0;
				tempArmor.setDefense(tempArmor.getDefense()+a);
				tempArmor.save();
				updateArmorDefense(p);
			}
			if(armorDefenseGUI.checkItemWithCustomItem(item, defense5)) {
				double a = 5.0;
				if(e.getClick() == ClickType.RIGHT) a = -5.0;
				tempArmor.setDefense(tempArmor.getDefense()+a);
				tempArmor.save();
				updateArmorDefense(p);
			}
			if(armorDefenseGUI.checkItemWithCustomItem(item, defense10)) {
				double a = 10.0;
				if(e.getClick() == ClickType.RIGHT) a = -10.0;
				tempArmor.setDefense(tempArmor.getDefense()+a);
				tempArmor.save();
				updateArmorDefense(p);
			}
			if(armorDefenseGUI.checkItemWithCustomItem(item, armorDefenseGUI.previous)) {
				openArmorRecipeGUI(p);
			}
			if(armorDefenseGUI.checkItemWithCustomItem(item, armorDefenseGUI.next)) {
				openArmorFinishGUI(p);
			}
		}
		
		if(e.getView().getTitle().equals(weaponStatsTitle)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			Player p = (Player) e.getWhoClicked();
			ItemStack item = e.getCurrentItem();
			
			for(int i = 0; i < 5; i++) {
				if(weaponStatsGUI.checkItemWithCustomItem(item, minDamageItems[i])) {
					double a = damageN[i];
					if(e.getClick() == ClickType.RIGHT) a = -a;
					tempWeapon.setMinDamage(tempWeapon.getMinDamage()+a);
					tempWeapon.save();
					updateWeaponStats(p);
				}
				if(weaponStatsGUI.checkItemWithCustomItem(item, maxDamageItems[i])) {
					double a = damageN[i];
					if(e.getClick() == ClickType.RIGHT) a = -a;
					tempWeapon.setMaxDamage(tempWeapon.getMaxDamage()+a);
					tempWeapon.save();
					updateWeaponStats(p);
				}
				if(weaponStatsGUI.checkItemWithCustomItem(item, criticalLuckItems[i])) {
					int a = luckN[i];
					if(e.getClick() == ClickType.RIGHT) a = -a;
					tempWeapon.setCriticalLuck(tempWeapon.getCriticalLuck()+a);
					tempWeapon.save();
					updateWeaponStats(p);
				}
			}
			
			if(weaponStatsGUI.checkItemWithCustomItem(item, weaponStatsGUI.previous)) {
				openWeaponRecipeGUI(p);
			}
			
			if(weaponStatsGUI.checkItemWithCustomItem(item, weaponStatsGUI.next)) {
				openWeaponFinishGUI(p);
			}
		}
		
		if(e.getView().getTitle().equals(armorFinishTitle)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			Player p = (Player) e.getWhoClicked();
			ItemStack item = e.getCurrentItem();
			
			if(armorFinishGUI.checkItemWithCustomItem(item, finishItem)) {
				tempArmor.save();
				MainCore.instance.rankCraft.reloadYamlArmors();
				p.closeInventory();
			}
			
			if(armorFinishGUI.checkItemWithCustomItem(item, armorFinishGUI.previous)) {
				openArmorDefenseGUI(p);
			}
		}
		
		if(e.getView().getTitle().equals(weaponFinishTitle)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
			Player p = (Player) e.getWhoClicked();
			ItemStack item = e.getCurrentItem();
			
			if(weaponFinishGUI.checkItemWithCustomItem(item, finishItem)) {
				tempWeapon.save();
				MainCore.instance.rankCraft.initYamlItems();
				p.closeInventory();
			}
			
			if(weaponFinishGUI.checkItemWithCustomItem(item, weaponFinishGUI.previous)) {
				openWeaponStatsGUI(p);
			}
		}
		
	}
	
	private List<String> getStringRecipe(Inventory inv) {
		List<String> rec = new ArrayList<>();
		for(int i = 0; i < 8; i+=3) {
			String mat1, mat2, mat3;
			if(inv.getItem(tableSlots[i])==null) mat1="AIR";
			else mat1=inv.getItem(tableSlots[i]).getType().name();
			if(inv.getItem(tableSlots[i+1])==null) mat2="AIR";
			else mat2=inv.getItem(tableSlots[i+1]).getType().name();
			if(inv.getItem(tableSlots[i+2])==null) mat3="AIR";
			else mat3=inv.getItem(tableSlots[i+2]).getType().name();
			rec.add(mat1+","+mat2+","+mat3);
		}
		return rec;
	}
	
	private List<String> getStringSwordRecipe(Inventory inv) {
		List<String> rec = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
			String mat;
			if(inv.getItem(swordTableSlots[i]) == null) mat ="AIR";
			else mat = inv.getItem(swordTableSlots[i]).getType().name();
			rec.add(mat);
		}
		return rec;
	}
	
	private boolean isSwordTableEmpty(Inventory inv) {
		for(int i : swordTableSlots) 
			if(inv.getItem(i) != null && inv.getItem(i).getType() != Material.AIR)
				return false;
		return true;
	}
	
	private boolean isTableEmpty(Inventory inv) {
		for(int i : tableSlots) 
			if(inv.getItem(i) != null && inv.getItem(i).getType() != Material.AIR)
				return false;
		return true;
	}
	
	private void updateArmorRarity(Player p) {
		p.closeInventory();
		openArmorRarityGUI(p);
	}
	
	private void updateArmorDefense(Player p) {
		p.closeInventory();
		openArmorDefenseGUI(p);
	}
	
	private void updateWeaponRarity(Player p) {
		p.closeInventory();
		openWeaponRarityGUI(p);
	}
	
	private void updateWeaponStats(Player p) {
		p.closeInventory();
		openWeaponStatsGUI(p);
	}
	
	private void updateWeaponRecipe(Player p) {
		p.closeInventory();
		openWeaponRecipeGUI(p);
	}
	
	public boolean openArmorStage(String stage, Player p, YamlArmor armor) {
		if(stage.equalsIgnoreCase(ARMOR_ITEM_STAGE)) {
			tempArmor = armor;
			openArmorItemGUI(p);
			return true;
		} else if(stage.equalsIgnoreCase(ARMOR_RARITY_STAGE)) {
			tempArmor = armor;
			openArmorRarityGUI(p);
			return true;
		} else if(stage.equalsIgnoreCase(ARMOR_RECIPE_STAGE)) {
			tempArmor = armor;
			openArmorRecipeGUI(p);
			return true;
		} else if(stage.equalsIgnoreCase(ARMOR_DEFENSE_STAGE)) {
			tempArmor = armor;
			openArmorDefenseGUI(p);
			return true;
		} else if(stage.equalsIgnoreCase(ARMOR_FINISH_STAGE)) {
			tempArmor = armor;
			openArmorFinishGUI(p);
			return true;
		}
		return false;
	}
	
	public boolean openWeaponStage(String stage, Player p, YamlItem weapon) {
		if(stage.equalsIgnoreCase(WEAPON_ITEM_STAGE)) {
			tempWeapon = weapon;
			openWeaponItemGUI(p);
			return true;
		} else if(stage.equalsIgnoreCase(WEAPON_RARITY_STAGE)) {
			tempWeapon = weapon;
			openWeaponRarityGUI(p);
			return true;
		} else if(stage.equalsIgnoreCase(WEAPON_RECIPE_STAGE)) {
			tempWeapon = weapon;
			openWeaponRecipeGUI(p);
			return true;
		} else if(stage.equalsIgnoreCase(WEAPON_STATS_STAGE)) {
			tempWeapon = weapon;
			openWeaponStatsGUI(p);
			return true;
		} else if(stage.equalsIgnoreCase(WEAPON_FINISH_STAGE)) {
			tempWeapon = weapon;
			openWeaponFinishGUI(p);
			return true;
		}
		return false;
	}
	
}
