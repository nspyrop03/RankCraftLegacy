package me.amc.rankcraft.mobs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.gui.GUI;
import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.RCUtils;

public class MobCreator implements Listener {

	private GUI selectTypeGUI;
	private GUI addArmorsGUI;
	private GUI addDropsGUI;
	private GUI canSpawnGUI;
	private GUI abilitiesGUI;
	private GUI biomesGUI;
	private GUI finishGUI;
	
	public String selectTypeTitle = "Select Mob Type";
	public String addArmorsTitle = "Add Armors";
	public String addDropsTitle = "Add Drops";
	public String canSpawnTitle = "Default spawn";
	public String abilitiesTitle = "Add Abilities";
	public String biomesTitle = "Add Biome";
	public String finishTitle = "Finish Mob";
	
	public final String SELECT_TYPE_STAGE = "TYPE";
	public final String ADD_ARMORS_STAGE = "ARMOR";
	public final String ADD_DROPS_STAGE = "DROPS";
	public final String CAN_SPAWN_STAGE = "SPAWN";
	public final String ABILITIES_STAGE = "ABILITIES";
	public final String BIOMES_STAGE = "BIOMES";
	public final String FINISH_STAGE = "FINISH";
	
	public String[] stages = {SELECT_TYPE_STAGE, ADD_ARMORS_STAGE, ADD_DROPS_STAGE, CAN_SPAWN_STAGE, ABILITIES_STAGE, BIOMES_STAGE, FINISH_STAGE};
	
	private CustomItem selectTypeInfo;
	private CustomItem selectZombie;
	private CustomItem selectSkeleton;
	private CustomItem zombieGlass;
	private CustomItem skeletonGlass;
	private ItemStack redGlass = new ItemStack(Material.RED_STAINED_GLASS_PANE);
	private ItemStack greenGlass = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
	private ItemStack brownGlass = new ItemStack(Material.BROWN_STAINED_GLASS_PANE);
	private CustomItem addArmorsInfo;
	private CustomItem weaponInfo;
	private CustomItem helmetInfo;
	private CustomItem chestInfo;
	private CustomItem legsInfo;
	private CustomItem bootsInfo;
	public int helmetSlot = 27;
	public int chestSlot = 2+27;
	public int legsSlot = 4+27;
	public int bootsSlot = 6+27;
	private int weaponSlot = 8+27;
	private CustomItem addDropsInfo;
	public int startDropSlot = 9;
	public int endDropSlot = 35;
	private CustomItem canSpawnInfo;
	private CustomItem canSpawnItem;
	private CustomItem cannotSpawnItem;
	private CustomItem trueGlass;
	private CustomItem falseGlass;
	private CustomItem abilitiesInfo;
	private CustomItem iceTouch;
	private CustomItem zeusHit;
	private CustomItem poisonSting;
	private CustomItem hiddenPower;
	private CustomItem minionsDefence;
	private CustomItem webAttack;
	private CustomItem monsterFist;
	private List<ItemStack> abilityItems = new ArrayList<>();
	private CustomItem biomesInfo;
	private CustomItem allBiomes;
	private List<CustomItem> biomeItems = new ArrayList<>();
	private CustomItem finishInfo;
	private CustomItem finishedItem;
	private CustomItem notFinishedItem;
	private CustomItem finishedGlass;
	private CustomItem notFinishedGlass;
	
	public MobCreator() {
		Bukkit.getServer().getPluginManager().registerEvents(this, MainCore.instance); 
	}
	
	private YamlMob tempMob;
	
	public void startMobCreator(Player p, YamlMob y) {
		openSelectType(p, y);
	}
	
	public void openSelectType(Player p, YamlMob y) {
		initSelectType(y);
		tempMob = y;
		selectTypeGUI = new GUI(selectTypeTitle, p, 9*6);
		selectTypeGUI.addItem(selectTypeInfo.getItem(), 4+9);
		selectTypeGUI.addItem(selectZombie.getItem(), 2+27);
		selectTypeGUI.addItem(selectSkeleton.getItem(), 6+27);
		selectTypeGUI.addItem(zombieGlass.getItem(), 2+36);
		selectTypeGUI.addItem(skeletonGlass.getItem(), 6+36);
		selectTypeGUI.addItem(selectTypeGUI.next.getItem(), 9*6-1);
		selectTypeGUI.openInventory();
	}
	
	private void initSelectType(YamlMob y){
		selectTypeInfo = new CustomItem(Material.EMERALD, ChatColor.GREEN+"Info");
		selectTypeInfo.addLores(ChatColor.GOLD+"Select the type of the mob");
		selectTypeInfo.build();		
		selectZombie = new CustomItem(Material.ZOMBIE_HEAD, ChatColor.DARK_GREEN+"Zombie").build((y.getType().equalsIgnoreCase("zombie")));
		selectSkeleton = new CustomItem(Material.SKELETON_SKULL, ChatColor.GRAY+"Skeleton").build(y.getType().equalsIgnoreCase("skeleton"));
		if(y.getType().equalsIgnoreCase("zombie")) {
			zombieGlass = new CustomItem(greenGlass);
			zombieGlass.setName(ChatColor.GREEN+"Selected");
			zombieGlass.build();
			skeletonGlass = new CustomItem(redGlass);
			skeletonGlass.setName(ChatColor.RED+"Not Selected");
			skeletonGlass.build();
		} else {
			skeletonGlass = new CustomItem(greenGlass);
			skeletonGlass.setName(ChatColor.GREEN+"Selected");
			skeletonGlass.build();
			zombieGlass = new CustomItem(redGlass);
			zombieGlass.setName(ChatColor.RED+"Not Selected");
			zombieGlass.build();
		}
	}
	
	public void openAddArmors(Player p, YamlMob y) {
		initAddArmors(y);
		tempMob = y;
		addArmorsGUI = new GUI(addArmorsTitle, p, 9*6);
		for(int i = 0; i < 9*6; i++) {
			if(i == 4) {
				addArmorsGUI.addItem(addArmorsInfo.getItem(), 4);
				continue;
			}
			if(i == helmetSlot) {
				if(y.getHelmet() != null)addArmorsGUI.addItem(y.getHelmet(), helmetSlot);
				continue;
			}
			if(i == chestSlot){
				if(y.getChestplate() != null)addArmorsGUI.addItem(y.getChestplate(), chestSlot);
				continue;
			} 
			if(i == legsSlot){
				if(y.getLeggings() != null) addArmorsGUI.addItem(y.getLeggings(), legsSlot);
				continue;
			}
			if(i == bootsSlot){
				if(y.getBoots() != null)addArmorsGUI.addItem(y.getBoots(), bootsSlot);
				continue;
			}
			if(i == weaponSlot) {
				if(y.getHand() != null)addArmorsGUI.addItem(y.getHand(), weaponSlot);
				continue;
			}
			if(i == helmetSlot-9) {
				addArmorsGUI.addItem(helmetInfo.getItem(), helmetSlot-9);
				continue;
			}
			if(i == chestSlot-9) {
				addArmorsGUI.addItem(chestInfo.getItem(), chestSlot-9);
				continue;
			}
			if(i == legsSlot-9) {
				addArmorsGUI.addItem(legsInfo.getItem(), legsSlot-9);
				continue;
			}
			if(i == bootsSlot-9) {
				addArmorsGUI.addItem(bootsInfo.getItem(), bootsSlot-9);
				continue;
			}
			if(i == weaponSlot-9) {
				addArmorsGUI.addItem(weaponInfo.getItem(), weaponSlot-9);
				continue;
			}
			addArmorsGUI.addItem(brownGlass, i);
		}
		addArmorsGUI.addItem(addArmorsGUI.previous.getItem(), addArmorsGUI.getSize()-9);
		addArmorsGUI.addItem(addArmorsGUI.next.getItem(), addArmorsGUI.getSize()-1);
		addArmorsGUI.openInventory();
	}
	
	private void initAddArmors(YamlMob y) {
		addArmorsInfo = new CustomItem(Material.EMERALD, ChatColor.GREEN+"Info");
		addArmorsInfo.addLores(ChatColor.GOLD+"Add sword and armors to your mob");
		addArmorsInfo.build();
		weaponInfo = new CustomItem(Material.STONE_SWORD, ChatColor.GREEN+"Weapon Slot", ChatColor.GOLD+"Add mob's weapon").buildWithNoFlags();
		helmetInfo = new CustomItem(Material.LEATHER_HELMET, ChatColor.GREEN+"Helmet Slot", ChatColor.GOLD+"Add mob's helmet").buildWithNoFlags();
		chestInfo = new CustomItem(Material.LEATHER_CHESTPLATE, ChatColor.GREEN+"Chestplate Slot", ChatColor.GOLD+"Add mob's chestplate").buildWithNoFlags();
		legsInfo = new CustomItem(Material.LEATHER_LEGGINGS, ChatColor.GREEN+"Leggings Slot", ChatColor.GOLD+"Add mob's leggings").buildWithNoFlags();
		bootsInfo = new CustomItem(Material.LEATHER_BOOTS, ChatColor.GREEN+"Boots Slot", ChatColor.GOLD+"Add mob's boots").buildWithNoFlags();
	}
	
	public void openAddDrops(Player p, YamlMob y) {
		initAddDrops();
		tempMob = y;
		addDropsGUI = new GUI(addDropsTitle, p, 9*6);
		for(int i = 0; i < 9; i++) addDropsGUI.addItem(brownGlass, i);
		for(int i = 36; i < addDropsGUI.getSize(); i++) addDropsGUI.addItem(brownGlass, i);
		
		
		if(y.getDrops()!=null) 
			for(int i = 0; i < y.getDrops().size(); i++) 
				addDropsGUI.addItem(y.getDrops().get(i), i+startDropSlot);
		
		
		addDropsGUI.addItem(addDropsInfo.getItem(), 4);
		addDropsGUI.addItem(addDropsGUI.previous.getItem(), addDropsGUI.getSize()-9);
		addDropsGUI.addItem(addDropsGUI.next.getItem(), addDropsGUI.getSize()-1);
		addDropsGUI.openInventory();
	}
	
	private void initAddDrops() {
		addDropsInfo = new CustomItem(Material.EMERALD, ChatColor.GREEN+"Info", ChatColor.GOLD+"Add mob's drops").buildWithNoFlags();
	}
	
	public void openCanSpawn(Player p, YamlMob y) {
		initCanSpawn(y);
		tempMob = y;
		canSpawnGUI = new GUI(canSpawnTitle, p, 9*6);
		canSpawnGUI.addItem(canSpawnInfo.getItem(), 4+9);
		canSpawnGUI.addItem(canSpawnItem.getItem(), 2+27);
		canSpawnGUI.addItem(cannotSpawnItem.getItem(), 6+27);
		canSpawnGUI.addItem(trueGlass.getItem(), 2+36);
		canSpawnGUI.addItem(falseGlass.getItem(), 6+36);
		canSpawnGUI.addItem(canSpawnGUI.next.getItem(), canSpawnGUI.getSize()-1);
		canSpawnGUI.addItem(canSpawnGUI.previous.getItem(), canSpawnGUI.getSize()-9);
		canSpawnGUI.openInventory();
		
	}
	
	private void initCanSpawn(YamlMob y) {
		canSpawnInfo = new CustomItem(Material.EMERALD, ChatColor.GREEN+"Info", ChatColor.GOLD+"Select whether the mob");
		canSpawnInfo.addLores(ChatColor.GOLD+"can spawn by default");
		canSpawnInfo.buildWithNoFlags();
		canSpawnItem = new CustomItem(Material.EMERALD_BLOCK, ChatColor.DARK_GREEN+"TRUE").build(y.isDefaultSpawn());
		cannotSpawnItem = new CustomItem(Material.REDSTONE_BLOCK, ChatColor.DARK_RED+"FALSE").build(!y.isDefaultSpawn());
		if(y.isDefaultSpawn()) {
			trueGlass = new CustomItem(greenGlass);
			trueGlass.setName(ChatColor.GREEN+"Selected");
			trueGlass.build();
			falseGlass = new CustomItem(redGlass);
			falseGlass.setName(ChatColor.RED+"Not Selected");
			falseGlass.build();
		} else {
			falseGlass = new CustomItem(greenGlass);
			falseGlass.setName(ChatColor.GREEN+"Selected");
			falseGlass.build();
			trueGlass = new CustomItem(redGlass);
			trueGlass.setName(ChatColor.RED+"Not Selected");
			trueGlass.build();
		}
	}
	
	public void openAddAbilityGUI(Player p, YamlMob y) {
		initAddAbility(y);
		tempMob = y;
		abilitiesGUI = new GUI(abilitiesTitle, p, 9*6);
		for(int i = 0; i < 9; i++) abilitiesGUI.addItem(brownGlass, i);
		for(int i = 45; i < 54; i++) abilitiesGUI.addItem(brownGlass, i);
		for(int i = 0; i < 6; i++) {
			abilitiesGUI.addItem(brownGlass, i*9);
			abilitiesGUI.addItem(brownGlass, i*9 + 8);
		}
		abilitiesGUI.addItem(abilitiesInfo.getItem(), 4);
		int startSlot = 10;
		List<Integer> forbiddenSlots = new ArrayList<Integer>(Arrays.asList(17, 26, 35, 44));
		for(int i = 0; i < abilityItems.size(); i++) {
			abilitiesGUI.addItem(abilityItems.get(i), startSlot);
			startSlot+=1;
			if(forbiddenSlots.contains(startSlot)) startSlot+=2;
		}
		abilitiesGUI.addItem(abilitiesGUI.previous.getItem(), abilitiesGUI.getSize()-9);
		abilitiesGUI.addItem(abilitiesGUI.next.getItem(), abilitiesGUI.getSize()-1);
		abilitiesGUI.openInventory();
	}
	
	private void initAddAbility(YamlMob y) {
		abilityItems.clear();
		abilitiesInfo = new CustomItem(Material.EMERALD, ChatColor.GREEN+"Info", ChatColor.GOLD+"Add/Remove abilities to/from your mob");
		abilitiesInfo.addLores(ChatColor.GOLD+"Left-Click to add/remove the ability", ChatColor.GOLD+"Shift+Left-Click to decrease the chances", ChatColor.GOLD+"Shift+Right-Click to increase the chances");
		abilitiesInfo.buildWithNoFlags();
		iceTouch = new CustomItem(Material.SNOWBALL, ChatColor.WHITE+"Ice Touch", ChatColor.AQUA+"Attacking Ability");
		iceTouch.addLores(ChatColor.AQUA+"Slows down the player");
		if(y.hasAbility(RCUtils.ICE_TOUCH)) iceTouch.addLores(ChatColor.GOLD+"Chances: "+y.getAbilityChances(RCUtils.ICE_TOUCH));
		iceTouch.build(y.hasAbility(RCUtils.ICE_TOUCH));
		abilityItems.add(iceTouch.getItem());
		zeusHit = new CustomItem(Material.REDSTONE, ChatColor.RED+"Zeus Hit", ChatColor.AQUA+"Attacking Ability");
		zeusHit.addLores(ChatColor.AQUA+"Strikes a lightning at the position of the player");
		if(y.hasAbility(RCUtils.ZEUS_HIT)) zeusHit.addLores(ChatColor.GOLD+"Chances: "+y.getAbilityChances(RCUtils.ZEUS_HIT));
		zeusHit.build(y.hasAbility(RCUtils.ZEUS_HIT));
		abilityItems.add(zeusHit.getItem());
		poisonSting = new CustomItem(Material.IRON_SWORD, ChatColor.DARK_GREEN+"Poison Sting", ChatColor.AQUA+"Attacking Ability");
		poisonSting.addLores(ChatColor.AQUA+"Poisons the player");
		if(y.hasAbility(RCUtils.POISON_STING)) poisonSting.addLores(ChatColor.GOLD+"Chances: "+y.getAbilityChances(RCUtils.POISON_STING));
		poisonSting.build(y.hasAbility(RCUtils.POISON_STING));
		abilityItems.add(poisonSting.getItem());
		hiddenPower = new CustomItem(Material.LEATHER_CHESTPLATE, ChatColor.DARK_GRAY+"Hidden Power", ChatColor.AQUA+"Defensive Ability");
		hiddenPower.addLores(ChatColor.AQUA+"Makes the mob invisible");
		if(y.hasAbility(RCUtils.HIDDEN_POWER)) hiddenPower.addLores(ChatColor.GOLD+"Chances: "+y.getAbilityChances(RCUtils.HIDDEN_POWER));
		hiddenPower.build(y.hasAbility(RCUtils.HIDDEN_POWER));
		abilityItems.add(hiddenPower.getItem());
		minionsDefence = new CustomItem(Material.ZOMBIE_SPAWN_EGG, ChatColor.YELLOW+"Minions Defence", ChatColor.AQUA+"Defensive Ability");
		minionsDefence.addLores(ChatColor.AQUA+"Spawns minions of the mob");
		if(y.hasAbility(RCUtils.MINIONS_DEFENCE)) minionsDefence.addLores(ChatColor.GOLD+"Chances: "+y.getAbilityChances(RCUtils.MINIONS_DEFENCE));
		minionsDefence.build(y.hasAbility(RCUtils.MINIONS_DEFENCE));
		abilityItems.add(minionsDefence.getItem());
		webAttack = new CustomItem(Material.COBWEB, ChatColor.WHITE+"Web Attack", ChatColor.AQUA+"Attacking Ability");
		webAttack.addLores(ChatColor.AQUA+"Throws cobweb");
		if(y.hasAbility(RCUtils.WEB_ATTACK)) webAttack.addLores(ChatColor.GOLD+"Chances: "+y.getAbilityChances(RCUtils.WEB_ATTACK));
		webAttack.build(y.hasAbility(RCUtils.WEB_ATTACK));
		abilityItems.add(webAttack.getItem());
		monsterFist = new CustomItem(Material.IRON_AXE, ChatColor.GREEN+"Monster Fist", ChatColor.AQUA+"Attacking Ability");
		monsterFist.addLores(ChatColor.AQUA+"Launches the player into the air...");
		if(y.hasAbility(RCUtils.MONSTER_FIST)) monsterFist.addLores(ChatColor.GOLD+"Chances: "+y.getAbilityChances(RCUtils.MONSTER_FIST));
		monsterFist.build(y.hasAbility(RCUtils.MONSTER_FIST));
		abilityItems.add(monsterFist.getItem());
	}
	private int currentPage = 1;
	public void openAddBiomeGUI(Player p, YamlMob y, int page) {
		initAddBiome(y);
		tempMob = y;
		currentPage = page;
		biomesGUI = new GUI(biomesTitle, p, 9*6);
		biomesGUI.addItem(biomesInfo.getItem(), 4);
		if(page == 1) { // 0 - 35
			biomesGUI.addItem(allBiomes.getItem(), 49);
			int startSlot = 9;
			for(int i = 0; i < 36; i++) {
				biomesGUI.addItem(biomeItems.get(i).getItem(), startSlot);
				startSlot += 1;
			}
		} else if(page == 2) {
			int start = 9;
			for(int i = 36; i < 72; i++) {
				biomesGUI.addItem(biomeItems.get(i).getItem(), start);
				start+=1;
			}
		}
		biomesGUI.addItem(biomesGUI.previous.getItem(), biomesGUI.getSize()-9);
		biomesGUI.addItem(biomesGUI.next.getItem(), biomesGUI.getSize()-1);
		biomesGUI.openInventory();
	}
	
	private void initAddBiome(YamlMob y) {
		biomeItems.clear();
		biomesInfo = new CustomItem(Material.EMERALD, ChatColor.GREEN+"Info", ChatColor.GOLD+"Add/remove biomes to/from your mob").buildWithNoFlags();
		for(Biome b : Biome.values()) {
			if(b==null || b.equals(Biome.THE_VOID) || b.equals(Biome.WARM_OCEAN) || b.equals(Biome.WOODED_BADLANDS_PLATEAU)) continue;
			CustomItem item = new CustomItem(Material.DIRT, b.name()).build(y.getStringBiomes().contains(b.name().toUpperCase()));
			biomeItems.add(item);
		}
		//System.out.println(biomeItems.size());
		boolean b = y.getStringBiomes().contains("ALL") || y.getStringBiomes().isEmpty();
		allBiomes = new CustomItem(Material.DIAMOND, ChatColor.BLUE+"All Biomes").build(b);
	}
	
	public void openFinishGUI(Player p, YamlMob y) {
		initFinish(y);
		tempMob = y;
		finishGUI = new GUI(finishTitle, p, 9*6);
		finishGUI.addItem(finishInfo.getItem(), 4+9);
		finishGUI.addItem(finishedItem.getItem(), 2+27);
		finishGUI.addItem(notFinishedItem.getItem(), 6+27);
		finishGUI.addItem(finishedGlass.getItem(), 2+36);
		finishGUI.addItem(notFinishedGlass.getItem(), 6+36);
		finishGUI.addItem(finishGUI.close.getItem(), finishGUI.getSize()-1);
		finishGUI.addItem(finishGUI.previous.getItem(), finishGUI.getSize()-9);
		finishGUI.openInventory();
	}
	
	private void initFinish(YamlMob y) {
		finishInfo = new CustomItem(Material.EMERALD, ChatColor.GREEN+"Info", ChatColor.GOLD+"Finished?").buildWithNoFlags();
		finishedItem = new CustomItem(Material.EMERALD_BLOCK, ChatColor.GREEN+"Finished!").build(y.isFinished());
		notFinishedItem = new CustomItem(Material.REDSTONE_BLOCK, ChatColor.RED+"Not Finished!").build(!y.isFinished());
		if(y.isFinished()) {
			finishedGlass = new CustomItem(greenGlass, ChatColor.GREEN+"YES!").build();
			notFinishedGlass = new CustomItem(redGlass, ChatColor.RED+"NO!").build();
		} else {
			notFinishedGlass = new CustomItem(greenGlass, ChatColor.GREEN+"YES!").build();
			finishedGlass = new CustomItem(redGlass, ChatColor.RED+"NO!").build();
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getView().getTitle().equals(selectTypeTitle)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			Player p = (Player) e.getWhoClicked();
			ItemStack item = e.getCurrentItem();
			if(selectTypeGUI.checkItemTypeAndName(item, selectZombie.getName(), selectZombie.getType())) {
				tempMob.setType("zombie");
				tempMob.getC().set("Type", "zombie");
				tempMob.save();
				reloadYamlMobs();
				updateSelectTypeGUI(p);
			}
			
			if(selectTypeGUI.checkItemTypeAndName(item, selectSkeleton.getName(), selectSkeleton.getType())) {
				tempMob.setType("skeleton");
				tempMob.getC().set("Type", "skeleton");
				tempMob.save();
				reloadYamlMobs();
				updateSelectTypeGUI(p);
			}
			
			if(selectTypeGUI.checkItemTypeAndName(item, selectTypeGUI.next.getName(), selectTypeGUI.next.getType())) {
				updateAddArmorsGUI(p);
			}
		}
		
		if(e.getView().getTitle().equals(addArmorsTitle)) {
			/*
			if((e.getRawSlot() != weaponSlot || e.getRawSlot() != helmetSlot 
					|| e.getRawSlot() != chestSlot || e.getRawSlot() != legsSlot || e.getRawSlot() != bootsSlot) 
					&& (e.getCurrentItem() == null ||e.getCurrentItem().getType() == Material.AIR)) {
				return;
			}
			*/
			if(e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
				if(e.getRawSlot() == bootsSlot && !isBoots(e.getCursor())) {
					//System.out.println("cancel "+e.getCursor().getType());
					e.setCancelled(true);
					return;
				}
				if(e.getRawSlot() == helmetSlot && !isHelmet(e.getCursor())) {
					//System.out.println("cancel "+e.getCursor().getType());
					e.setCancelled(true);
					return;
				}
				if(e.getRawSlot() == chestSlot && !isChest(e.getCursor())) {
					//System.out.println("cancel "+e.getCursor().getType());
					e.setCancelled(true);
					return;
				}
				if(e.getRawSlot() == legsSlot && !isLegs(e.getCursor())) {
					//System.out.println("cancel "+e.getCursor().getType());
					e.setCancelled(true);
					return;
				}
			}
			
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
				Player p = (Player) e.getWhoClicked();
				ItemStack item = e.getCurrentItem();
				
				if(item.isSimilar(brownGlass)
						|| addArmorsGUI.checkItemTypeAndName(item, helmetInfo.getName(), helmetInfo.getType()) 
						|| addArmorsGUI.checkItemTypeAndName(item, chestInfo.getName(), chestInfo.getType())
						|| addArmorsGUI.checkItemTypeAndName(item, legsInfo.getName(), legsInfo.getType())
						|| addArmorsGUI.checkItemTypeAndName(item, bootsInfo.getName(), bootsInfo.getType())
						|| addArmorsGUI.checkItemTypeAndName(item, weaponInfo.getName(), weaponInfo.getType())
						|| addArmorsGUI.checkItemTypeAndName(item, addArmorsInfo.getName(), addArmorsInfo.getType())
						|| (e.getClick() == ClickType.SHIFT_LEFT && e.getRawSlot() >= 54)) {
					e.setCancelled(true);
					return;
				}
				
				if(addArmorsGUI.checkItemTypeAndName(item, addArmorsGUI.previous.getName(), addArmorsGUI.previous.getType())) {
					updateSelectTypeGUI(p);
				}
				
				if(addArmorsGUI.checkItemTypeAndName(item, addArmorsGUI.next.getName(), addArmorsGUI.next.getType())) {
					updateAddDropsGUI(p);
				}
			}
			//System.out.println(e.getRawSlot());
			/*
			if(e.getRawSlot() == bootsSlot ) {
				System.out.println(e.getCursor().getType());
			}
			*/
			
			
		}
		
		if(e.getView().getTitle().equals(addDropsTitle)) {
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
				Player p = (Player) e.getWhoClicked();
				ItemStack item = e.getCurrentItem();
				if(item.isSimilar(brownGlass) || addDropsGUI.checkItemTypeAndName(item, addDropsInfo.getName(), addDropsInfo.getType())) {
					e.setCancelled(true);
					return;
				}
				
				if(addDropsGUI.checkItemTypeAndName(item, addDropsGUI.previous.getName(), addDropsGUI.previous.getType())) {
					updateAddArmorsGUI(p);
				}
				
				if(addDropsGUI.checkItemTypeAndName(item, addDropsGUI.next.getName(), addArmorsGUI.next.getType())) {
					updateCanSpawnGUI(p);
				}
			}
		}
		
		if(e.getView().getTitle().equals(canSpawnTitle)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			
			Player p = (Player) e.getWhoClicked();
			ItemStack item = e.getCurrentItem();
			if(canSpawnGUI.checkItemTypeAndName(item, canSpawnItem.getName(), canSpawnItem.getType())) {
				tempMob.setDefaultSpawn(true);
				tempMob.getC().set("DefaultSpawn", tempMob.isDefaultSpawn());
				tempMob.save();
				reloadYamlMobs();
				updateCanSpawnGUI(p);
			}
			
			if(canSpawnGUI.checkItemTypeAndName(item, cannotSpawnItem.getName(), cannotSpawnItem.getType())) {
				tempMob.setDefaultSpawn(false);
				tempMob.getC().set("DefaultSpawn", tempMob.isDefaultSpawn());
				tempMob.save();
				reloadYamlMobs();
				updateCanSpawnGUI(p);
			}
			
			if(canSpawnGUI.checkItemTypeAndName(item, canSpawnGUI.previous.getName(), canSpawnGUI.previous.getType())) {
				updateAddDropsGUI(p);
			}
			
			if(canSpawnGUI.checkItemTypeAndName(item, canSpawnGUI.next.getName(), canSpawnGUI.next.getType())) {
				updateAddAbilityGUI(p);
			}
		}
		
		if(e.getView().getTitle().equals(abilitiesTitle)) {
			e.setCancelled(true);
			if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			Player p = (Player) e.getWhoClicked();
			ItemStack item = e.getCurrentItem();
			//System.out.println("abilities ok "+item.getItemMeta().getDisplayName()+" "+item.getType());
			if(isSame(item, iceTouch) || isSame(item, zeusHit) || isSame(item, poisonSting) || isSame(item, hiddenPower) || isSame(item, minionsDefence) 
					|| isSame(item, webAttack) || isSame(item, monsterFist)) {
				
				String a = "null";
				if(isSame(item, iceTouch)) a = RCUtils.ICE_TOUCH;
				else if(isSame(item, zeusHit)) a = RCUtils.ZEUS_HIT;
				else if(isSame(item, poisonSting)) a = RCUtils.POISON_STING;
				else if(isSame(item, hiddenPower)) a = RCUtils.HIDDEN_POWER;
				else if(isSame(item, minionsDefence)) a = RCUtils.MINIONS_DEFENCE;
				else if(isSame(item, webAttack)) a = RCUtils.WEB_ATTACK;
				else if(isSame(item, monsterFist)) a = RCUtils.MONSTER_FIST;
				
				if(e.getClick() == ClickType.LEFT) {
					if(tempMob.hasAbility(a)) {
						tempMob.getAbilities().remove(a+":"+tempMob.getAbilityChances(a));
						tempMob.getC().set("Abilities", tempMob.getAbilities());
						tempMob.save();
						reloadYamlMobs();
						updateAddAbilityGUI(p);
					} else {
						tempMob.addAbility(a+":10");
						tempMob.getC().set("Abilities", tempMob.getAbilities());
						tempMob.save();
						//System.out.println("added");
						reloadYamlMobs();
						updateAddAbilityGUI(p);
					}
					
				} else if(e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
					if(tempMob.hasAbility(a)) {
						int modifier = 1;
						if(e.getClick() == ClickType.SHIFT_LEFT) modifier *= -1;
						int chances = tempMob.getAbilityChances(a);
						tempMob.getAbilities().remove(a+":"+chances);
						chances+=5*modifier;
						if(chances < 0) chances = 0;
						if(chances > 100) chances = 100;
						tempMob.addAbility(a+":"+chances);
						tempMob.getC().set("Abilities", tempMob.getAbilities());
						tempMob.save();
						reloadYamlMobs();
						updateAddAbilityGUI(p);
					}
				}
			}
			
			if(abilitiesGUI.checkItemTypeAndName(item, abilitiesGUI.previous.getName(), abilitiesGUI.previous.getType())) {
				updateCanSpawnGUI(p);
			}
			
			if(abilitiesGUI.checkItemTypeAndName(item, abilitiesGUI.next.getName(), abilitiesGUI.next.getType())) {
				updateAddBiomeGUI(p, 1);
			}
		}
		
		if(e.getView().getTitle().equals(biomesTitle)) {
			e.setCancelled(true);
			if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			Player p = (Player) e.getWhoClicked();
			ItemStack item = e.getCurrentItem();
			
			if(isSame(item, allBiomes)) {
				if(tempMob.getStringBiomes().isEmpty()) return;
				if(tempMob.getStringBiomes().contains("ALL")) {
					tempMob.getStringBiomes().remove("ALL");
					tempMob.getC().set("Biomes", tempMob.getStringBiomes());
					tempMob.save();
					reloadYamlMobs();
					updateAddBiomeGUI(p, currentPage);
				} else {
					tempMob.addBiome("ALL");
					tempMob.getC().set("Biomes", tempMob.getStringBiomes());
					tempMob.save();
					reloadYamlMobs();
					updateAddBiomeGUI(p, currentPage);
				}
			}
			
			//for(CustomItem i : biomeItems) {
			for(int i = 0; i < biomeItems.size(); i++) {
				if(isSame(item, biomeItems.get(i))) {
					String biome = RCUtils.stripColor(biomeItems.get(i).getName()).toUpperCase();
					if(tempMob.getStringBiomes().contains(biome)) { // remove it
						tempMob.getStringBiomes().remove(biome);
						tempMob.getC().set("Biomes", tempMob.getStringBiomes());
						tempMob.save();
						reloadYamlMobs();
						updateAddBiomeGUI(p, currentPage);
					} else { // add 
						tempMob.addBiome(biome);
						tempMob.getC().set("Biomes", tempMob.getStringBiomes());
						tempMob.save();
						reloadYamlMobs();
						updateAddBiomeGUI(p, currentPage);
					}
				}
			}
			//}
			
			if(biomesGUI.checkItemTypeAndName(item, biomesGUI.previous.getName(), biomesGUI.previous.getType())) {
				if(currentPage == 1) {
					updateAddAbilityGUI(p);
				} else {
					updateAddBiomeGUI(p, 1);
				}
			}
			if(biomesGUI.checkItemTypeAndName(item, biomesGUI.next.getName(), biomesGUI.next.getType())) {
				if(currentPage == 1) {
					updateAddBiomeGUI(p, 2);
				} else {
					updateFinishGUI(p);
				}
			}
		}
		
		if(e.getView().getTitle().equals(finishTitle)) {
			e.setCancelled(true);
			if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
				return;
			}
			Player p = (Player) e.getWhoClicked();
			ItemStack item = e.getCurrentItem();
			
			if(finishGUI.checkItemTypeAndName(item, finishGUI.previous.getName(), finishGUI.previous.getType())) {
				updateAddBiomeGUI(p, 2);
			}
			
			if(finishGUI.checkItemTypeAndName(item, finishGUI.close.getName(), finishGUI.close.getType())) {
				p.closeInventory();
			}
			
			if(isSame(item, finishedItem)) {
				if(!tempMob.isFinished()) {
					tempMob.setFinished(true);
					tempMob.getC().set("Finished", tempMob.isFinished());
					tempMob.save();
					reloadYamlMobs();
					updateFinishGUI(p);
				}
			}
			
			if(isSame(item, notFinishedItem)) {
				if(tempMob.isFinished()) {
					tempMob.setFinished(false);
					tempMob.getC().set("Finished", tempMob.isFinished());
					tempMob.save();
					reloadYamlMobs();
					updateFinishGUI(p);
				}
			}
		}
 	}
	
	private boolean isSame(ItemStack item, CustomItem compareItem) {
		if(!item.hasItemMeta()) return false;
		if(!item.getItemMeta().hasDisplayName()) return false;
		if(!(item.getType() == compareItem.getType())) return false;
		
		if(RCUtils.stripColor(item.getItemMeta().getDisplayName()).equals(RCUtils.stripColor(compareItem.getName()))) {
			return true;
		} else {
			return false;
		}
	}
	
	@EventHandler
	public void onCloseInv(InventoryCloseEvent e) {
		if(e.getView().getTitle().equals(addArmorsTitle)) {
			if(tempMob != null) {
				//System.out.println("hey");
				if(e.getInventory().getItem(helmetSlot) != null) {
					//System.out.println("Found helmet!"+e.getInventory().getItem(helmetSlot).getType());
					tempMob.setHelmet(e.getInventory().getItem(helmetSlot));
					tempMob.getC().set("Inventory.helmet", tempMob.getHelmet());
				} else {
					tempMob.setHelmet(new ItemStack(Material.AIR));
					tempMob.getC().set("Inventory.helmet", tempMob.getHelmet());
				}
				if(e.getInventory().getItem(chestSlot) != null) {
					//System.out.println("Found chest!");
					tempMob.setChestplate(e.getInventory().getItem(chestSlot));
					tempMob.getC().set("Inventory.chestplate", tempMob.getChestplate());
				}else {
					tempMob.setChestplate(new ItemStack(Material.AIR));
					tempMob.getC().set("Inventory.chestplate", tempMob.getChestplate());
				}
				if(e.getInventory().getItem(legsSlot) != null) {
					//System.out.println("Found legs!");
					tempMob.setLeggings(e.getInventory().getItem(legsSlot));
					tempMob.getC().set("Inventory.leggings", tempMob.getLeggings());
				}else {
					tempMob.setLeggings(new ItemStack(Material.AIR));
					tempMob.getC().set("Inventory.leggings", tempMob.getLeggings());
				}
				if(e.getInventory().getItem(bootsSlot) != null) {
					//System.out.println("Found boots!");
					tempMob.setBoots(e.getInventory().getItem(bootsSlot));
					tempMob.getC().set("Inventory.boots", tempMob.getBoots());
				}else {
					tempMob.setBoots(new ItemStack(Material.AIR));
					tempMob.getC().set("Inventory.boots", tempMob.getBoots());
				}
				if(e.getInventory().getItem(weaponSlot) != null) {
					//System.out.println("Found weapon!");
					tempMob.setHand(e.getInventory().getItem(weaponSlot));
					tempMob.getC().set("Inventory.hand", tempMob.getHand());
				}else {
					tempMob.setHand(new ItemStack(Material.AIR));
					tempMob.getC().set("Inventory.hand", tempMob.getHand());
				}
				tempMob.save();
				reloadYamlMobs();
			}
		}
		
		if(e.getView().getTitle().equals(addDropsTitle)) {
			if(tempMob != null) {
				List<ItemStack> drops = new ArrayList<>();
				for(int i = startDropSlot; i < endDropSlot; i++) {
					if(e.getInventory().getItem(i)!=null) drops.add(e.getInventory().getItem(i));
				}
				tempMob.setDrops(drops);
				tempMob.getC().set("Drops", tempMob.getDrops());
				tempMob.save();
				reloadYamlMobs();
			}
		}
 	}
	
	private void updateSelectTypeGUI(Player p) {
		p.closeInventory();
		openSelectType(p, tempMob);
	}
	
	private void updateAddArmorsGUI(Player p) {
		p.closeInventory();
		openAddArmors(p, tempMob);
	}
	
	private void updateAddDropsGUI(Player p) {
		p.closeInventory();
		openAddDrops(p, tempMob);
	}
	
	private void updateCanSpawnGUI(Player p) {
		p.closeInventory();
		openCanSpawn(p, tempMob);
	}
	
	private void updateAddAbilityGUI(Player p) {
		p.closeInventory();
		openAddAbilityGUI(p, tempMob);
	}
	
	private void updateAddBiomeGUI(Player p, int page) {
		p.closeInventory();
		openAddBiomeGUI(p, tempMob, page);
	}
	
	private void updateFinishGUI(Player p) {
		p.closeInventory();
		openFinishGUI(p, tempMob);
	}
 	
	private void reloadYamlMobs() {
		MainCore.instance.rankCraft.reloadYamlMobFiles();
		MainCore.instance.rankCraft.makeYamlMobLists();
	}
	
	public boolean isBoots(ItemStack item) {
		if(item == null) return false;
		if(item.getType() == Material.CHAINMAIL_BOOTS ||
				item.getType() == Material.DIAMOND_BOOTS ||
				item.getType() == Material.GOLDEN_BOOTS ||
				item.getType() == Material.IRON_BOOTS ||
				item.getType() == Material.LEATHER_BOOTS)
			return true;
		return false;
	}	
	
	public boolean isChest(ItemStack item) {
		if(item == null) return false;
		if(item.getType() == Material.CHAINMAIL_CHESTPLATE ||
				item.getType() == Material.DIAMOND_CHESTPLATE ||
				item.getType() == Material.GOLDEN_CHESTPLATE ||
				item.getType() == Material.IRON_CHESTPLATE ||
				item.getType() == Material.LEATHER_CHESTPLATE)
			return true;
		return false;
	}
	
	public boolean isLegs(ItemStack item) {
		if(item == null) return false;
		if(item.getType() == Material.CHAINMAIL_LEGGINGS ||
				item.getType() == Material.DIAMOND_LEGGINGS ||
				item.getType() == Material.GOLDEN_LEGGINGS ||
				item.getType() == Material.IRON_LEGGINGS ||
				item.getType() == Material.LEATHER_LEGGINGS)
			return true;
		return false;
	}
	
	public boolean isHelmet(ItemStack item){
		if(item == null) return false;
		if(item.getType() == Material.CHAINMAIL_HELMET ||
				item.getType() == Material.DIAMOND_HELMET ||
				item.getType() == Material.GOLDEN_HELMET ||
				item.getType() == Material.IRON_HELMET ||
				item.getType() == Material.LEATHER_HELMET
				|| item.getType() == Material.SKELETON_SKULL
				|| item.getType() == Material.WITHER_SKELETON_SKULL
				|| item.getType() == Material.CREEPER_HEAD
				|| item.getType() == Material.DRAGON_HEAD
				|| item.getType() == Material.PLAYER_HEAD
				|| item.getType() == Material.ZOMBIE_HEAD)
			return true;
		return false;
	}
	
	public boolean openStage(String stage, Player p, YamlMob y) {
		if(stage.equalsIgnoreCase(SELECT_TYPE_STAGE)) {
			openSelectType(p, y);
			return true;
		} else if(stage.equalsIgnoreCase(ADD_ARMORS_STAGE)) {
			openAddArmors(p, y);
			return true;
		}else if(stage.equalsIgnoreCase(ADD_DROPS_STAGE)) {
			openAddDrops(p, y);
			return true;
		}else if(stage.equalsIgnoreCase(CAN_SPAWN_STAGE)) {
			openCanSpawn(p, y);
			return true;
		}else if(stage.equalsIgnoreCase(ABILITIES_STAGE)) {
			openAddAbilityGUI(p, y);
			return true;
		}else if(stage.equalsIgnoreCase(BIOMES_STAGE)) {
			openAddBiomeGUI(p, y, 1);
			return true;
		}else if(stage.equalsIgnoreCase(FINISH_STAGE)) {
			openFinishGUI(p, y);
			return true;
		}
		return false;
	}
}
	
