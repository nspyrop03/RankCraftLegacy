package me.amc.rankcraft.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.damage.DamageSystem;
import me.amc.rankcraft.rpgitem.CrystalSize;
import me.amc.rankcraft.rpgitem.ManaCrystal;
import me.amc.rankcraft.rpgitem.RpgItem;
import me.amc.rankcraft.rpgitem.XpCrystal;
import me.amc.rankcraft.rpgitem.YamlItem;
import me.amc.rankcraft.spells.SpellCase;
import me.amc.rankcraft.spells.SpellCase.CaseType;
import me.amc.rankcraft.stats.Gold;
import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.RCUtils;
import me.amc.rankcraft.yaml.ShopItemsYaml;
import me.amc.rankcraft.yaml.YamlParser;

public class RandomItemsShop implements Listener {

	private GUI gui;
	private ItemStack brownGlass = new ItemStack(Material.GLASS_PANE, 1, (short)12);
	private ItemStack blackGlass = new ItemStack(Material.GLASS_PANE, 1, (short)4);
//	private List<ItemStack> items = new ArrayList<>();
	private CustomItem exitDoor;
	private CustomItem myGold;
	
//	public String title = "General Shop";
	public String title = MainCore.instance.guiLang.getGeneralShopTitle();
	
	private List<Integer> availableSlots = getItemSlots();
	//private HashMap<ItemStack, Integer> hashItems = new HashMap<>();
	private List<SlotItem> slotItems = new ArrayList<>();
	
	private YamlParser y;
	private boolean needRefresh;
	private int nextRefresh; //seconds (300sec = 5min)
	
	private CustomItem showSeconds;
	
	public RandomItemsShop() {
		MainCore.instance.getServer().getPluginManager().registerEvents(this, MainCore.instance);
		
		y = new YamlParser(new File("plugins/RankCraft"), "general_shop.yml");
		needRefresh = y.getConfig().getBoolean("Refresh");
		nextRefresh = y.getConfig().getInt("NextRefresh"); //seconds
	}
	
	public void open(Player p) {
		gui = new GUI(title, p, 9*6);
		initItems(p);
		
		for(int i = gui.getSize()-8; i < gui.getSize()-1; i++) {
			gui.addItem(brownGlass, i);
		}
		for(int i = 0; i < 9; i++) {
			gui.addItem(brownGlass, i);
		}
		for(int i = 0; i < gui.getSize()-9; i+=9) {
			gui.addItem(brownGlass, i);
		}
		for(int i = 8; i < gui.getSize()-1; i+=9) {
			gui.addItem(brownGlass, i);
		}
		
		gui.addItem(exitDoor.getItem(), gui.getSize()-1);
		gui.addItem(myGold.getItem(), gui.getSize()-9);
		gui.addItem(showSeconds.getItem(), 0);
		
	//	for(int i : slotItems) {
		//	gui.addItem(new CustomItem(Material.PAPER).build().getItem(), i);
	//	}
		needRefresh = y.getConfig().getBoolean("Refresh");
		if(needRefresh) {
			initShop();
			y.getConfig().set("Refresh", false);
			try {
				y.getConfig().save(new File("plugins/RankCraft/general_shop.yml"));
			} catch (IOException e) {
				System.out.println("[Error] Cannot save general_shop.yml file!!!");
			}
		} else {
			int k = 0;
			for(int slot : getItemSlots()) {
				try {
					if(k <= getItemSlots().size()) {
		//				System.out.println("k: "+k+" size: "+getItemSlots().size());
					if(getShopItems().get(k) == null) {
						//gui.addItem(getShopItems().get(k), slot);
						continue;
					} else {
						gui.addItem(getShopItems().get(k), slot);
					}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				k++;
			}
		}
	/*	
		for(int i : availableSlots) {
			if(gui.getInventory().getContents()[i] == null) {
				CustomItem ct = new CustomItem(blackGlass);
				ct.setName("Unavailable!");
				ct.build();
				gui.addItem(ct.getItem(), i);
			}
		}
	*/
		for(int k : availableSlots) {
			if(!gui.getItemsHM().containsKey(k)) {
				gui.addItem(blackGlass, k);
			}
		}
		
		gui.openInventory();
	}
	
	@SuppressWarnings("deprecation")
	private void initItems(Player p) {
	//	exitDoor = new CustomItem(Material.DARK_OAK_DOOR_ITEM, ChatColor.GREEN+"Exit ->").build();
		exitDoor = new CustomItem(Material.DARK_OAK_DOOR, MainCore.instance.guiLang.getExit()).build();
	//	myGold = new CustomItem(Material.GOLD_INGOT, ChatColor.YELLOW+"Your Gold: "+MainCore.instance.rankCraft.gold.getGold(p)).build();
		myGold = new CustomItem(Material.GOLD_INGOT, MainCore.instance.guiLang.getYourGold(MainCore.instance.rankCraft.gold.getGold(p))).build();
	//	showSeconds = new CustomItem(Material.SIGN, ChatColor.YELLOW+"The shop refreshes in "+ChatColor.RED+nextRefresh+ChatColor.YELLOW+" seconds!").build();
		showSeconds = new CustomItem(Material.LEGACY_SIGN, MainCore.instance.guiLang.getRefresh(nextRefresh)).build();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) {

		if(!e.getView().getTitle().equalsIgnoreCase(title)) {
			return;
		}

		e.setCancelled(true);
		
		if(e.getCurrentItem()==null || e.getCurrentItem().getType()==Material.AIR || 
				e.getCurrentItem() == brownGlass || e.getCurrentItem() == blackGlass ||
				e.getCurrentItem().getType() == Material.LEGACY_SIGN || 
				e.getCurrentItem().getType() == Material.DARK_OAK_DOOR ||
				!e.getCurrentItem().getItemMeta().hasLore()) {
	//		System.out.println("false");
			if(e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.DARK_OAK_DOOR ) {
				((Player) e.getWhoClicked()).closeInventory();
			}
			return;
		}
		
		Player p = (Player) e.getWhoClicked();
		ItemStack clicked = e.getCurrentItem();
	//	CustomItem item = new CustomItem(clicked).build();
		boolean tradeable = false;
		Gold gold = MainCore.instance.rankCraft.gold;
		float cost = 0;
	//	System.out.println(clicked.getItemMeta().getLore());
		for(String s : clicked.getItemMeta().getLore()) {
			String lore = ChatColor.stripColor(s);
	//		System.out.println(lore);
			if(lore.startsWith("Cost")) {
	//			System.out.println("Starts: true");
				tradeable = true;
				String[] parts = lore.split(": ");
				cost = Float.parseFloat(parts[1]);
			}
		}
		
		if(tradeable) {
			
			if(gold.hasGoldAmount(p, cost)) {
				gold.removeGold(p, cost);
				
				for(int i = 0; i < slotItems.size(); i++) {
					if(slotItems.get(i).getSlot() == e.getSlot()) {
					//	slotItems.set(i, new SlotItem(new CustomItem(Material.BARRIER,ChatColor.RED+"Sold").build().getItem(), i));
						slotItems.set(i, new SlotItem(new CustomItem(Material.BARRIER,MainCore.instance.guiLang.getSold()).build().getItem(), i));
				//		System.out.println("removed: "+item2.getStack());
						try {
							saveInventory();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
						for(Player p2 : Bukkit.getOnlinePlayers()) {
							if(p2.getOpenInventory().getTitle().equals(title)) {
								p2.closeInventory();
								open(p2);
							}
						}
					}
				}
				
			//	CustomItem bItem = new CustomItem(clicked);
			//	for(int i = 0; i < bItem.getLores().size(); i++){
			//		bItem.removeIfStart(ChatColor.GOLD+"Cost");
			//	}
			/*
				System.out.println(bItem.getLores());
				for(int i = 0; i < bItem.getLores().size(); i++) {
					String s = bItem.getLores().get(i);
					String lore = ChatColor.stripColor(s);
					if(lore.startsWith("Cost")) {
						System.out.println("ok cost is removing");
						bItem.getLores().remove(s);
					}
				}
				
				bItem.build();
			*/
				/*
				ItemMeta meta = clicked.getItemMeta();
				for(int i = 0; i < meta.getLore().size(); i++) {
					String s = meta.getLore().get(i);
					String lore = ChatColor.stripColor(s);
					if(lore.startsWith("Cost")) {
						meta.getLore().remove(s);
					}
				}
				clicked.setItemMeta(meta);
				*/
				CustomItem bItem = new CustomItem(clicked);
				List<String> lore = bItem.getLores();
		//		System.out.println(lore);
				List<String> loreNew = new ArrayList<String>();
				for(int i = 0; i < lore.size(); i++) {
					String s = lore.get(i);
					if(s.startsWith(ChatColor.GOLD+"Cost")) {
		//				System.out.println("starts removing");
						lore.remove(s);
		//				System.out.println(lore);
					} else {
						loreNew.add(s);
					}
				}
		//		System.out.println(lore);
		//		bItem.getLores().clear();
				bItem.setLores(loreNew);
		//		System.out.println(bItem.getLores());
		//		System.out.println(bItem.build().getLores());
				ItemStack give = bItem.build().getItem();
				ItemMeta meta = give.getItemMeta();
				meta.setLore(loreNew);
				give.setItemMeta(meta);
		//		System.out.println(give.getItemMeta().getLore());
		//		System.out.println(bItem.getLores());
		//		System.out.println(bItem.build().getLores());
				p.getInventory().addItem(give);
			//	p.sendMessage(ChatColor.DARK_GREEN+"You have successfully bought this item!");
				p.sendMessage(MainCore.instance.language.getBoughtItem());
				/*
				for(SlotItem item2 : slotItems) {
					if(item2.getSlot() == e.getSlot()) {
						slotItems.remove(item2);
					//	slotItems.set(
						System.out.println("removed: "+item2.getStack());
						try {
							saveInventory();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			*/
				
				
			}
		}
		
	}
	
	private List<Integer> getItemSlots() {
		List<Integer> slots = new ArrayList<>();
	//	System.out.println("ok?!");
		for(int i = 1; i < 4+1; i++) {
			for(int j = 1; j < 7+1; j++) {
				slots.add(i * 9 + j);
	//			System.out.println(i * 9 + j);
			}
		}
		
		return slots;
	}
	
	/*
	private ItemStack getRandomItem() {
		// Spells, Weapons, Crystals, Food, Tools
		ItemStack stack;
		Random r = new Random();
		int rNum = r.nextInt(101);
		
		float cost;
		
		if(rNum <= 20) {
			CrystalSize size;
			int rNum2 = r.nextInt(101);
			if(rNum2 <= 33) {
				size = CrystalSize.Small;
				cost = 50;
			} else if(rNum2 >= 34 && rNum2 <= 66) {
				size = CrystalSize.Medium;
				cost = 120;
			} else {
				size = CrystalSize.Large;
				cost = 200;
			}
			if(r.nextInt(101) <= 50) {
				XpCrystal xpCrystal = new XpCrystal(size, ChatColor.GOLD+size.toString()+" XpCrystal");
				stack = xpCrystal.getItem().build().getItem();
			} else {
				ManaCrystal manaCrystal = new ManaCrystal(size, ChatColor.BLUE+size.toString()+" ManaCrystal");
				stack = manaCrystal.getItem().build().getItem();
			}
		} else if(rNum >= 21 && rNum <= 40) {
			List<Spell> spells = MainCore.instance.rankCraft.spellUtils.spells;
			Spell s = spells.get(r.nextInt(spells.size()));	
			stack = s.getItem();
			cost = r.nextInt(1000)+1;
		} else {//else if(rNum >= 41 && rNum <= 70) {
			List<CustomItem> foods = new ArrayList<>();
			foods.add(new CustomItem(Material.BREAD, r.nextInt(64)+1).build());
			foods.add(new CustomItem(Material.COOKED_BEEF, r.nextInt(64)+1).build());
			foods.add(new CustomItem(Material.BAKED_POTATO, r.nextInt(64)+1).build());
			foods.add(new CustomItem(Material.COOKED_FISH, r.nextInt(64)+1).build());
			
			stack = foods.get(r.nextInt(foods.size())).getItem();
			cost = r.nextInt(200)+1;
		}
	//	else if(rNum >= 61 && rNum <= 80) {
	//  Armors!!!		
	//	} 
		else {
			ChestType type;
			
			switch(r.nextInt(3)) {
			case 0:
				type = ChestType.Normal;
				cost = r.nextInt(200)+1;
				break;
			case 1:
				type = ChestType.Super;
				cost = r.nextInt(500)+100;
				break;
			case 2:
				type = ChestType.Ultra;
				cost = r.nextInt(1000)+200;
				break;
			default:
				type = ChestType.Normal;
				cost = r.nextInt(200)+1;
				System.out.println("normal");
				break;
			}
			
			TreasureItem ti;
			int rNumType = r.nextInt(4);
			switch (rNumType) {
			case 0:
				ti = new TreasureItem(TreasureItem.ItemType.Axe);
				stack = ti.getRandomItem(type);
				break;
			case 1:
				ti = new TreasureItem(TreasureItem.ItemType.Sword);
				stack = ti.getRandomItem(type);
				break;
			case 2:
				ti = new TreasureItem(TreasureItem.ItemType.Bow);
				stack = ti.getRandomItem(type);
				break;
			case 3:
				ti = new TreasureItem(TreasureItem.ItemType.Pickaxe);
				stack = ti.getRandomItem(type);
				break;
			default:
				ti = new TreasureItem(TreasureItem.ItemType.Sword);
				stack = ti.getRandomItem(type);
				System.out.println("default tool = sword");
				System.out.println(rNumType);
				break;
			}
		}
		
		CustomItem finalStack = new CustomItem(stack);
		//finalStack.addLores(ChatColor.GOLD+"Cost: "+cost);
	//	finalStack.setLores(stack.getItemMeta().getLore());
		finalStack.addLores(ChatColor.GOLD+"Cost: "+cost);
		finalStack.build();
		
		if(stack.getItemMeta().getLore() != null) {
			ItemMeta meta = stack.getItemMeta();
			List<String> lore = stack.getItemMeta().getLore();
			lore.add(ChatColor.GOLD+"Cost: "+cost);
			meta.setLore(lore);
			stack.setItemMeta(meta);
		} else {
			ItemMeta meta = stack.getItemMeta();
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.GOLD+"Cost: "+cost);
			meta.setLore(lore);
			stack.setItemMeta(meta);
		}
	
		return finalStack.getItem();
	}
	*/
	
	public ItemStack getRandomItem2() {
		
		Random r = new Random();
		
		Material m = Material.DIRT;
		int amount = 1;
		int minAmount = 1;
		int maxAmount = 1;
		double cost = 0;
		double minCost = 0;
		double maxCost = 0;
		
		boolean hasName = false;
		String name = "Error";
		
		boolean isCrystal = false;
		CrystalSize cSize = CrystalSize.Small;
		
		boolean isSpell = false;
		int level = 1;
		
		boolean isYamlItem = false;
		String fileName = "test01";
		boolean fixed = false;
		
		boolean isSpellCase = false;
		CaseType caseType = CaseType.Small;
		
		ShopItemsYaml yml = MainCore.instance.rankCraft.shopItemsYaml;
		int rIndex = r.nextInt(yml.getItemsList().size());
		
		String toItem = yml.getItemsList().get(rIndex); 
		String[] parts = toItem.split(",");
		
		if(parts[0].equalsIgnoreCase("MC")) {
			try {
				m = Material.getMaterial(parts[1]);
				minAmount = Integer.parseInt(parts[2]);
				maxAmount = Integer.parseInt(parts[3]);
				minCost = Double.parseDouble(parts[4]);
				maxCost = Double.parseDouble(parts[5]);
			} catch(Exception ex) {
				MainCore.instance.sendError("Invalid parameters on getting a random item!");
			}
			hasName = false;
		} else if(parts[0].equalsIgnoreCase("crystal")) {
			if(parts[1].equalsIgnoreCase("xp")) {
				if(parts[2].equalsIgnoreCase("small")) {
					name = RCUtils.XPCRYSTAL_SMALL_NAME;
					cSize = CrystalSize.Small;
				}
				else if(parts[2].equalsIgnoreCase("medium")) {
					name = RCUtils.XPCRYSTAL_MEDIUM_NAME;
					cSize = CrystalSize.Medium;
				}
				else if(parts[2].equalsIgnoreCase("large")) {
					name = RCUtils.XPCRYSTAL_LARGE_NAME;
					cSize = CrystalSize.Large;
				}
			} else if(parts[1].equalsIgnoreCase("mana")) {
				if(parts[2].equalsIgnoreCase("small")) {
					name = RCUtils.MANACRYSTAL_SMALL_NAME;
					cSize = CrystalSize.Small;
				}
				else if(parts[2].equalsIgnoreCase("medium")) {
					name = RCUtils.MANACRYSTAL_MEDIUM_NAME;
					cSize = CrystalSize.Medium;
				}
				else if(parts[2].equalsIgnoreCase("large")) {
					name = RCUtils.MANACRYSTAL_LARGE_NAME;
					cSize = CrystalSize.Large;
				}
			}
			try {
				minCost = Double.parseDouble(parts[3]);
				maxCost = Double.parseDouble(parts[4]);
			} catch(Exception ex) {
				MainCore.instance.sendError("Invalid parameters on getting a random item!");
			}
			hasName = true;
			isCrystal = true;
		} else if(parts[0].equalsIgnoreCase("spell")) {
			isSpell = true;
			hasName = true;
			name = parts[1];
			try {
				level = Integer.parseInt(parts[2]);
				//name = name+" Lvl."+level; old method of finding spells
				
				// New method of searching for spells with nameId
				name = name+"_"+level;
				
				minCost = Double.parseDouble(parts[3]);
				maxCost = Double.parseDouble(parts[4]);
			} catch(Exception ex) {
				MainCore.instance.sendError("Invalid parameters on getting a random item!");
			}
		} else if(parts[0].equalsIgnoreCase("RC")) {
			isYamlItem = true;
			fileName = parts[1];
			try {
				fixed = Boolean.parseBoolean(parts[2]);
				if(!fixed) {
					hasName = true;
					int rNames = r.nextInt(yml.getConfig().getStringList(parts[5]).size());
					name = yml.getConfig().getStringList(parts[5]).get(rNames).replace('&', '§');
				}
				minCost = Double.parseDouble(parts[3]);
				maxCost = Double.parseDouble(parts[4]);
			}catch(Exception ex) {
				MainCore.instance.sendError("Invalid parameters on getting a random item!");				
			}
		} else if(parts[0].equalsIgnoreCase("spellcase")) {
			isSpellCase = true;
			if(parts[1].equalsIgnoreCase("small")) {
				caseType = CaseType.Small;
			} else if(parts[1].equalsIgnoreCase("medium")) {
				caseType = CaseType.Medium;
			} else if(parts[1].equalsIgnoreCase("large")) {
				caseType = CaseType.Large;
			}
			try {
				minCost = Double.parseDouble(parts[2]);
				maxCost = Double.parseDouble(parts[3]);
			} catch(Exception ex) {
				MainCore.instance.sendError("Invalid parameters on getting a random item!");
			}
		}
		
		amount = r.nextInt((maxAmount - minAmount) + 1) + minAmount;
		cost = minCost + (maxCost - minCost) * r.nextDouble();
		
		CustomItem finalStack;
		
		if(isCrystal) {
			if(parts[1].equalsIgnoreCase("xp")) finalStack = new CustomItem((new XpCrystal(cSize, name)).getItem().build().getItem());
			else finalStack = new CustomItem((new ManaCrystal(cSize, name)).getItem().build().getItem());
			
			if(hasName) finalStack.setName(name);
		} else if(isSpell) {
			finalStack = new CustomItem(MainCore.instance.rankCraft.spellUtils.getSpellByNameId(name).getItem());
		} else if(isYamlItem) {
			if(!fixed) {
				finalStack = new CustomItem(randomizeItem((new YamlItem(fileName)).getBuiltRpgItem().getItem()));
				if(hasName) finalStack.setName(name);
			} else {
				finalStack = new CustomItem((new YamlItem(fileName)).getBuiltRpgItem().getItem());
			}
		} else if(isSpellCase) {
			SpellCase sc = new SpellCase(caseType);
			finalStack = new CustomItem(sc.getItem());
		} else {
			finalStack = new CustomItem(m, amount);
			if(hasName) finalStack.setName(name);
		}
		
		finalStack.removeIfStart(ChatColor.GOLD+"Cost");
		finalStack.addLores(ChatColor.GOLD+"Cost: "+RCUtils.round(cost, 1));
		finalStack.build();
		return finalStack.getItem();
	}
	
	private void makeHashItems() {
		
		for(int i : availableSlots) {
		//	hashItems.put(getRandomItem(), i);
			slotItems.add(new SlotItem(getRandomItem2(), i));
		}
		
	}
	
	public void initShop() {
		makeHashItems();
		
		for(SlotItem item : slotItems) {
			item.place(this.gui);
		}
		File f = new File("plugins/RankCraft","shop_items.yml");
		if(f.exists()) {
			f.delete();
		}
		
		try {
			saveInventory();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveInventory() throws IOException {
		File f = new File("plugins/RankCraft","shop_items.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(f);
		List<ItemStack> items = new ArrayList<>();
		for (SlotItem item : slotItems) {
			items.add(item.getStack());
	//		System.out.println(item.getStack());
		}
		c.set("shop.items", items);
		c.save(f);
	}

	@SuppressWarnings("unchecked")
	public List<ItemStack> getShopItems() throws IOException {

		File f = new File("plugins/RankCraft","shop_items.yml");
		FileConfiguration c = YamlConfiguration.loadConfiguration(f);
		List<ItemStack> content = ((List<ItemStack>) c.get("shop.items"));

		return content;

	}
	
	public void updateNextRefresh() {
		nextRefresh--;
		y.getConfig().set("NextRefresh", nextRefresh);
		try {
			y.getConfig().save(new File("plugins/RankCraft/general_shop.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getNextRefresh() {
		return nextRefresh;
	}
	
	public void setNextRefresh(int sec) {
		nextRefresh = sec;
	}
	
	public void resetNextRefresh() {
		nextRefresh = MainCore.instance.config.shopRefresh;
		y.getConfig().set("NextRefresh", nextRefresh);
		try {
			y.getConfig().save(new File("plugins/RankCraft/general_shop.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setRefreshTrue() {
		needRefresh = true;
		y.getConfig().set("Refresh", needRefresh);
		try {
			y.getConfig().save(new File("plugins/RankCraft/general_shop.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		File f = new File("plugins/RankCraft","shop_items.yml");
		if(f.exists()) {
			f.delete();
		}
		slotItems.clear();
	}
	
	public List<SlotItem> getSlotItemsList() {
		return slotItems;
	}
	
	public class SlotItem {
		
		private ItemStack stack;
		private int slot;
		
		public SlotItem(ItemStack stack, int slot) {
			this.stack = stack;
			this.slot = slot;
		}
		
		public void place(GUI gui) {
			if(gui.getSize() > slot) {
				gui.addItem(stack, slot);
			}
		}

		public ItemStack getStack() {
			return stack;
		}

		public void setStack(ItemStack stack) {
			this.stack = stack;
		}

		public int getSlot() {
			return slot;
		}

		public void setSlot(int slot) {
			this.slot = slot;
		}
		
	}
	
	private ItemStack randomizeItem(ItemStack item) {
		Random r = new Random();
		if(item.hasItemMeta() && RCUtils.isToolOrArmor(item)) {
			ItemMeta meta = item.getItemMeta();
			//p.sendMessage("-> OK Meta...");
			
			if(DamageSystem.isRpgItem(item)) {
				//p.sendMessage(ChatColor.BLUE+"RpgItem detected...");
				RpgItem rItem = new RpgItem(item.getType(), item.getItemMeta().getDisplayName());
				if(DamageSystem.getCriticalLuckFromRpgItem(item) == 0) {
					rItem.setCriticalLuck(0);
				} else {
					rItem.setCriticalLuck(r.nextInt(DamageSystem.getCriticalLuckFromRpgItem(item))+1);
				}
				
				double min = DamageSystem.getMinDamage(item);
				double max = DamageSystem.getMaxDamage(item);
				
				double m1 = min-0.5;
				double m2 = min+0.5;
				
				rItem.setMinDamage(RCUtils.round(m1 + ((m2 - m1) * r.nextDouble()),1));
				if(rItem.getMinDamage() <= 0.0) {
					rItem.setMinDamage(0.1);
				}
				
				double rand = r.nextDouble();
				//p.sendMessage(ChatColor.GOLD+"First: "+(min+(max-min)));
				//p.sendMessage(ChatColor.GOLD+"Second: "+rand);
				
				rItem.setMaxDamage(RCUtils.round(min + ((max - min) * rand),1));
				//p.sendMessage(ChatColor.GOLD+"MaxDamage: "+rItem.getMaxDamage());
				
				rItem.build();
				
				meta.setLore(rItem.getLores());
				item.setItemMeta(meta);
			}
			
			if(meta.hasEnchants()) {
				//p.sendMessage("--> OK Enchants...");
				final Map<Enchantment,Integer> enchants = meta.getEnchants();
				
				List<Enchantment> newEnchants = new ArrayList<Enchantment>(enchants.keySet());
				List<Integer> newPowers = new ArrayList<Integer>(enchants.values());
				
				for(int i = 0; i < newEnchants.size(); i++) {
					meta.removeEnchant(newEnchants.get(i));
					//p.sendMessage((i+1)+") "+newEnchants.get(i).getName());
				}
				//p.sendMessage(ChatColor.DARK_GRAY+"---------------");
				for(int i = 0; i < newPowers.size(); i++) {
					//p.sendMessage((i+1)+") "+newPowers.get(i));
				}
				
				//p.sendMessage(ChatColor.DARK_GRAY+"---------------");
				for(int i = 0; i < r.nextInt(newEnchants.size()+1); i++) {
					int selected = r.nextInt(newEnchants.size());
					//p.sendMessage("Selected: "+selected);
					
					Enchantment ench = newEnchants.get(selected);
					int power = r.nextInt(newPowers.get(selected)+1);
					if(power == 0) power = 1;
					
					//p.sendMessage(ench.getName() +" - "+power+"/"+newPowers.get(selected));
					
					meta.addEnchant(ench, power, true);
					//p.sendMessage(ChatColor.DARK_PURPLE+"Enchanting...");
				}
				//p.sendMessage(ChatColor.DARK_GRAY+"---------------");
				
				//p.sendMessage("EnchantSize: "+meta.getEnchants().size());
				
				item.setItemMeta(meta);
				
				/*
				for(Enchantment enc : meta.getEnchants().keySet()) {
					
				}
				*/
			}
			return item;
		}
		return null;
	}
}
