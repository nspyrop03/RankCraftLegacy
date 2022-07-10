package me.amc.rankcraft.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.classes.ArcherClass;
import me.amc.rankcraft.classes.GladiatorClass;
import me.amc.rankcraft.classes.MysteriousClass;
import me.amc.rankcraft.classes.NinjaClass;
import me.amc.rankcraft.classes.RpgClass;
import me.amc.rankcraft.classes.RpgClassData;
import me.amc.rankcraft.classes.WizardClass;
import me.amc.rankcraft.damage.DamageSystem;
import me.amc.rankcraft.mobs.YamlMob;
import me.amc.rankcraft.packs.Pack;
import me.amc.rankcraft.quests2.Quest;
import me.amc.rankcraft.quests2.QuestSaver;
import me.amc.rankcraft.rpgitem.Rarity;
import me.amc.rankcraft.rpgitem.RpgItem;
import me.amc.rankcraft.rpgitem.YamlItem;
import me.amc.rankcraft.spells.Spell;
import me.amc.rankcraft.spells.SpellCase.CaseType;
import me.amc.rankcraft.stats.Gold;
import me.amc.rankcraft.stats.Stats;

public class RCUtils {

	public static final File STATS_DIRECTORY = new File("plugins/RankCraft/stats");
	public static final File SKILLS_DIRECTORY = new File("plugins/RankCraft/skills");
	//public static final File TREASURES_DIRECTORY = new File("plugins/RankCraft/treasures");
	public static final File WEAPONS_DIRECTORY = new File("plugins/RankCraft/weapons");
	public static final File PACKS_DIRECTORY = new File("plugins/RankCraft/packs");
	public static final File SPELLS_DB_DIRECTORY = new File("plugins/RankCraft/spells_database");
	public static final File QUESTS_DIRECTORY = new File("plugins/RankCraft/quests");
	public static final File ITEMS_DB_DIRECTORY = new File("plugins/RankCraft/items_database");
	public static final File BACKPACK_DIRECTORY = new File("plugins/RankCraft/backpacks");
	public static final File RPGCLASSES_DIRECTORY = new File("plugins/RankCraft/classes");
	public static final File TREASURES2_DIRECTORY = new File("plugins/RankCraft/treasures2");
	public static final File LEVEL_REWARDS_DIRECTORY = new File("plugins/RankCraft/levels");	
	public static final File ARMORS_DIRECTORY = new File("plugins/RankCraft/armors");
	
	public static final String NORMAL_TREASURE_NAME = ChatColor.GREEN + "NormalTreasure";
	public static final String SUPER_TREASURE_NAME = ChatColor.GOLD + "SuperTreasure";
	public static final String ULTRA_TREASURE_NAME = ChatColor.DARK_BLUE + "UltraTreasure";

	public static final String XPCRYSTAL_SMALL_NAME = ChatColor.GOLD+"Small XpCrystal";
	public static final String XPCRYSTAL_MEDIUM_NAME = ChatColor.GOLD+"Medium XpCrystal";
	public static final String XPCRYSTAL_LARGE_NAME = ChatColor.GOLD+"Large XpCrystal";
	public static final String MANACRYSTAL_SMALL_NAME = ChatColor.BLUE+"Small ManaCrystal";
	public static final String MANACRYSTAL_MEDIUM_NAME = ChatColor.BLUE+"Medium ManaCrystal";
	public static final String MANACRYSTAL_LARGE_NAME = ChatColor.BLUE+"Large ManaCrystal";
	
	public static final String ZEUS_HIT = "zeus_hit";
	public static final String ICE_TOUCH = "ice_touch";
	public static final String POISON_STING = "poison_sting";
	public static final String HIDDEN_POWER = "hidden_power";
	public static final String MINIONS_DEFENCE = "minions_defence";
	public static final String WEB_ATTACK = "web_attack";
	public static final String MONSTER_FIST = "monster_fist";

	public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final String DATE_PREFIX = "Date: ";
	
	public static final String SEARCH_CRITERIA_ALL = "ALL";
	public static final String SEARCH_CRITERIA_SAMETYPE = "SAMETYPE";
	public static final String SEARCH_CRITERIA_SAMEMOB = "SAMEMOB";
	
	// Replaced with configurable Prefix from the language file
	//public static final String PREFIX = ChatColor.DARK_RED+"[RankCraft]"+ChatColor.WHITE+" ";
	
	
	public static String textedUUID(Player p) {
		return p.getUniqueId().toString();
	}

	public static void removeItemFromHand(Player p) {
		ItemStack hand = p.getInventory().getItemInMainHand();
		int amount = hand.getAmount();
		if (amount <= 1) {
			hand.setAmount(0);
		} else {
			hand.setAmount(amount - 1);
		}
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static void buyPack(Player p, Pack pack) {
		if (MainCore.instance.rankCraft.gold.hasGoldAmount(p, pack.getCost())) {
			MainCore.instance.rankCraft.gold.removeGold(p, pack.getCost());
			p.getInventory().addItem(pack.getItem().build().getItem());
		} else {
		//	p.sendMessage(ChatColor.RED + "You don't have enough gold!");
			p.sendMessage(MainCore.instance.language.getNotEnoughGold());
		}
	}

	public static void removeAndReloadMana(Player p, float cost) {
		MainCore.instance.rankCraft.stats.removeMana(p, cost);
		MainCore.instance.scoreboard.updateBoard(p);
	}

	public static float secondsToTicks(float seconds) {
		int first = 1;
		int sec = 20;
		float result;

		result = (sec * seconds) / first;

		return result;
	}
	
	public static void giveReward(Reward r, Player p) {
		Stats s = MainCore.instance.rankCraft.stats;
		Gold g = MainCore.instance.rankCraft.gold;
		
		s.addXp(p, (int)r.getXp());
		g.addGold(p, r.getGold());
	//	p.sendMessage("+ "+r.getXp()+" xp");
	//	p.sendMessage("+ "+r.getGold()+" gold");
		p.sendMessage(MainCore.instance.language.getRewardMessage(r.getXp(), r.getGold()));
		s.checkLevelAndRank(p);
		
		if(r.getItem() != null)
			p.getInventory().addItem(r.getItem().getItem());
		
	}
	
	public static void giveQuest(Quest q, Player p) {
		if(!q.hmTaken().get(textedUUID(p))) {
			if(q.hmSecondsToReTake().get(textedUUID(p)) <= 0) {
				q.hmTaken().put(RCUtils.textedUUID(p), true);
			//	p.sendMessage("Quest " + q.getName() + " started !");
				p.sendMessage(MainCore.instance.language.getQuestStart(q.getName()));
				QuestSaver.save(MainCore.instance, q);
			} else {
			//	p.sendMessage(ChatColor.DARK_RED+"You must wait "+q.hmSecondsToReTake().get(textedUUID(p))+" seconds until you take this quest again !");
				p.sendMessage(MainCore.instance.language.getSecondsToReTakeQuest(q.hmSecondsToReTake().get(RCUtils.textedUUID(p))));
			}
		} else {
		//	p.sendMessage(ChatColor.DARK_RED+"You have already taken this quest !!!");
			p.sendMessage(MainCore.instance.language.getQuestTaken());
		}
	}
	
	//@SuppressWarnings("rawtypes")
	/*
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void checkDataContainerAndCopy(ItemMeta toCheck, ItemMeta parent, NamespacedKey key, PersistentDataType type) {
		if(parent.getPersistentDataContainer().has(key, type))
			toCheck.getPersistentDataContainer().set(key, type, parent.getPersistentDataContainer().get(key, type));
	}
	*/
	//HashMap<NamespacedKey, PersistentDataType> keyMap = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public static RpgItem getRpgItemFromNormal(ItemStack item, boolean toBuild) {
		if(DamageSystem.isRpgItem(item)) {
			RpgItem r = new RpgItem(item.getType(), item.getItemMeta().getDisplayName());
			
			for(NamespacedKey key : MainCore.instance.rankCraft.specialKeyMap.keySet()) {
				//System.out.println("hello? checking for "+key.getKey());
				if(item.getItemMeta().getPersistentDataContainer().has(key, MainCore.instance.rankCraft.specialKeyMap.get(key))) {
					//System.out.println("Adding "+key.getKey());
					
					r.getMeta().getPersistentDataContainer().set(key, MainCore.instance.rankCraft.specialKeyMap.get(key), 
							item.getItemMeta().getPersistentDataContainer().get(key, 
									MainCore.instance.rankCraft.specialKeyMap.get(key)));
				}
			}
			
			String min = "";
			String max = "";
			//String rare = "";
			String luck = "";
			String lvl = "";
			Rarity rare = Rarity.RARE;
			
			
			for(String s : item.getItemMeta().getLore()) {
				/*
				if(s.startsWith("MinDamage")) {
					min = s.split(": ")[1];
				}
				else if(s.startsWith("MaxDamage")) {
					max = s.split(": ")[1];
				}
				else if(s.startsWith("CriticalLuck")) {
					luck = s.split(": ")[1];
				}
				else if(s.startsWith("Rarity")) {
					rare = s.split(": ")[1];
				}
				else if(s.startsWith("MinLevel")) {
					lvl = s.split(": ")[1];
				}
				else {
		//			System.out.println("else...!");
					r.addLores(s);
				}
				*/
				s = stripColor(s);
				if(s.startsWith("Stats: ")) {
					String[] line = s.split(": ");
					String[] stats = line[1].split(",");
					min = stats[0];
					max = stats[1];
					luck = stats[2];
					lvl = stats[3];
				}
				
				
			}
			
			
			
			
			for(Enchantment e : item.getItemMeta().getEnchants().keySet()) {
				r.enchant(e, item.getItemMeta().getEnchantLevel(e));
			}
			
			try {
				r.setMinDamage(Double.parseDouble(min));
				r.setMaxDamage(Double.parseDouble(max));
				r.setRarity(rare);
				r.setCriticalLuck(Integer.parseInt(luck));
				r.setLevelToUse(Integer.parseInt(lvl));
			} catch(Exception ex) {
				System.out.println("Something went wrong!");
			}
			if(toBuild)r.build();
			
			return r;
		} else {
			return null;
		}
	}
	
	public static YamlMob getYamlMobFromEntity(LivingEntity e) {
		if(e instanceof Zombie || e instanceof Skeleton) {
			
			String name = stripColor(e.getCustomName());
			if(name != null) {
				for(YamlMob y : MainCore.instance.rankCraft.mobs) {
					//if(stripColor(y.getDisplayName()).equalsIgnoreCase(stripColor(name))) {
					//	return y;
					//}
					String yamlName = stripColor(y.getDisplayName());
					yamlName = yamlName.split("HP:")[0];
					if(name.startsWith(yamlName)) {
						//System.out.println("starts");
						return y;
					}
				}
			}
		} 
			
	//	System.out.println("Cannot find YamlMob! (Returns: NULL)");
		return null;
		
	}
	
	public static void addGladiatorXp(Player p, double xp) {
		double currentXp = RpgClassData.getGladiatorXp(p);
		GladiatorClass g = MainCore.instance.rankCraft.gladiatorClass;
		
		if(currentXp + xp < g.getXpToLevelUp(g.getMaxLevel())) {
			RpgClassData.setGladiatorXp(p, currentXp + xp);
		} else {
			RpgClassData.setGladiatorXp(p, g.getXpToLevelUp(g.getMaxLevel()) - 1);
		}
	}
	
	public static void addArcherXp(Player p, double xp) {
		double currentXp = RpgClassData.getArcherXp(p);
		ArcherClass a = MainCore.instance.rankCraft.archerClass;
		
		if(currentXp + xp < a.getXpToLevelUp(a.getMaxLevel())) {
			RpgClassData.setArcherXp(p, currentXp + xp);
		} else {
			RpgClassData.setArcherXp(p, a.getXpToLevelUp(a.getMaxLevel()) - 1);
		}
	}
	
	public static void addNinjaXp(Player p, double xp) {
		double currentXp = RpgClassData.getNinjaXp(p);
		NinjaClass a = MainCore.instance.rankCraft.ninjaClass;
		
		if(currentXp + xp < a.getXpToLevelUp(a.getMaxLevel())) {
			RpgClassData.setNinjaXp(p, currentXp + xp);
		} else {
			RpgClassData.setNinjaXp(p, a.getXpToLevelUp(a.getMaxLevel()) - 1);
		}
	}
	
	public static void addWizardXp(Player p, double xp) {
		double currentXp = RpgClassData.getWizardXp(p);
		WizardClass a = MainCore.instance.rankCraft.wizardClass;
		
		if(currentXp + xp < a.getXpToLevelUp(a.getMaxLevel())) {
			RpgClassData.setWizardXp(p, currentXp + xp);
		} else {
			RpgClassData.setWizardXp(p, a.getXpToLevelUp(a.getMaxLevel()) - 1);
		}
	}
	
	public static void addMysteriousXp(Player p, double xp) {
		double currentXp = RpgClassData.getMysteriousXp(p);
		MysteriousClass a = MainCore.instance.rankCraft.mysteriousClass;
		
		if(currentXp + xp < a.getXpToLevelUp(a.getMaxLevel())) {
			RpgClassData.setMysteriousXp(p, currentXp + xp);
		} else {
			RpgClassData.setMysteriousXp(p, a.getXpToLevelUp(a.getMaxLevel()) - 1);
		}
	}
	
	public static boolean isValidRpgClassId(String id) {
		List<String> ids = new ArrayList<>();
		
		for(RpgClass r : MainCore.instance.rankCraft.rpgClasses) {
			ids.add(r.getId());
		}
		
		if(ids.contains(id)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public static RpgClass getRpgClassById(String id) {
		if(isValidRpgClassId(id)) {
			for(RpgClass r : MainCore.instance.rankCraft.rpgClasses) {
				if(r.getId().equals(id)) {
					return r;
				} 
			}
		} 
		
		return null;
	}
	
	public static boolean isWorldEnabled(World world) {
		if(MainCore.instance.config.enableWorldList) {
			boolean enabled;
			
			if(MainCore.instance.config.enableOnWorlds.contains(world.getName())) {
				enabled = true;
			} else {
				enabled = false;
			}
			
		//	System.out.println("EnabledWorld ("+world.getName()+"): "+enabled);
			
			return enabled;
		} else {
			return true;
		}
	}
	
	public static boolean isPlayerWorldEnabled(Player p) {
		if(MainCore.instance.config.enableWorldList) {			
			if(!MainCore.instance.config.enableOnWorlds.contains(p.getLocation().getWorld().getName())) {
				//p.sendMessage("This action is disabled in this world!");
				p.sendMessage(MainCore.instance.language.getActionNotAllowedInWorld());
				return false;
			}			
		}		
		return true;		
	}
	
	
	public static String stripColor(String input) {
		return ChatColor.stripColor(input);
	}
	
	public static boolean isToolOrArmor(ItemStack item) {
		switch(item.getType()) {
		case WOODEN_AXE:
		case WOODEN_PICKAXE:
		case WOODEN_SWORD:
		case WOODEN_SHOVEL:
		case WOODEN_HOE:
		case GOLDEN_AXE:
		case GOLDEN_PICKAXE:
		case GOLDEN_SWORD:
		case GOLDEN_SHOVEL:
		case GOLDEN_HOE:
		case IRON_AXE:
		case IRON_PICKAXE:
		case IRON_SWORD:
		case IRON_SHOVEL:
		case IRON_HOE:
		case DIAMOND_AXE:
		case DIAMOND_PICKAXE:
		case DIAMOND_SWORD:
		case DIAMOND_SHOVEL:
		case DIAMOND_HOE:
		case STONE_AXE:
		case STONE_PICKAXE:
		case STONE_SWORD:
		case STONE_SHOVEL:
		case STONE_HOE:
		case BOW:
		case LEATHER_HELMET:
		case LEATHER_CHESTPLATE:
		case LEATHER_LEGGINGS:
		case LEATHER_BOOTS:
		case GOLDEN_HELMET:
		case GOLDEN_CHESTPLATE:
		case GOLDEN_LEGGINGS:
		case GOLDEN_BOOTS:
		case IRON_HELMET:
		case IRON_CHESTPLATE:
		case IRON_LEGGINGS:
		case IRON_BOOTS:
		case DIAMOND_HELMET:
		case DIAMOND_CHESTPLATE:
		case DIAMOND_LEGGINGS:
		case DIAMOND_BOOTS:
		case CHAINMAIL_HELMET:
		case CHAINMAIL_CHESTPLATE:
		case CHAINMAIL_LEGGINGS:
		case CHAINMAIL_BOOTS:
			return true;
		default:
			return false;		
		}
	}
	
	public static YamlItem getYamlItemByFileName(String fileName) {
		for(File f : WEAPONS_DIRECTORY.listFiles()) {
			if(f.getName().equalsIgnoreCase(fileName+".weapon")) return new YamlItem(fileName);
		}
		return null;
	}
	
	public static boolean hasAbilityFuelLore(ItemStack item) {
		for(String line : item.getItemMeta().getLore()) {
			if(line.startsWith(ChatColor.AQUA+"AbilityFuel: ")) return true;
		}
		return false;
	}
	
	public static boolean hasDurabilityLore(ItemStack item) {
		for(String line : item.getItemMeta().getLore()) {
			if(line.startsWith(ChatColor.AQUA+"Durability: ")) return true;
		}
		return false;
	}
	/*
	public static boolean isSpell(ItemStack item) {
		if(item != null) {
			if(item.getType() == Material.PAPER) {
				if(item.hasItemMeta()) {
					ItemMeta meta = item.getItemMeta();
					if(meta.hasDisplayName()) {
						String name = meta.getDisplayName();
						for(Spell s : MainCore.instance.rankCraft.spellUtils.spells) {
							if(s.getName().equalsIgnoreCase(name)) 
								return true;
						}
						
					}
				}
				
			}
		}
		return false;
	}	
	
	public static Spell getSpellFromItemStack(ItemStack item) {
		if(isSpell(item)) {
			for(Spell s : MainCore.instance.rankCraft.spellUtils.spells) {
				if(item.getItemMeta().getDisplayName().equalsIgnoreCase(s.getName())) 
					return s;
			}
		}
		return null;
	}
	*/
	
	public static boolean isSpell(ItemStack item) {
		if(item != null)
			if(item.getType() == Material.PAPER)
				if(item.hasItemMeta())
					if(item.getItemMeta().getPersistentDataContainer().has(Spell.NAME_ID_KEY, PersistentDataType.STRING))
						return true;
		
		return false;
	}
	
	public static Spell getSpellFromItemStack(ItemStack item) {
		if(isSpell(item))
			for(Spell s : MainCore.instance.rankCraft.spellUtils.spells)
				if(item.getItemMeta().getPersistentDataContainer().get(Spell.NAME_ID_KEY, PersistentDataType.STRING).equals(s.getNameId()))
					return s;
		
		return null;
	}
	
	public static String getAmountForID(int amount) {
		if(amount < 10) {
			return "0"+amount;
		} else {
			return ""+amount;
		}
	}
	
	public static CaseType getCaseTypeFromItemStack(ItemStack item) {
		if(item.getType() == Material.BOOK) {
			if(item.hasItemMeta()) {
				ItemMeta meta = item.getItemMeta();
				if(meta.hasDisplayName()) {
					String name = meta.getDisplayName();
					if(name.equals(CaseType.Small.getName())) {
						return CaseType.Small;
					} else if(name.equals(CaseType.Medium.getName())) {
						return CaseType.Medium;
					} else if(name.equals(CaseType.Large.getName())) {
						return CaseType.Large;
					}
				}
			}
		}
		return null;
	}
	
	public static boolean isPlayerNearby(Location loc, int range) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			double x0 = p.getLocation().getX();
			double y0 = p.getLocation().getY();
			double z0 = p.getLocation().getZ();
			double x1 = loc.getX();
			double y1 = loc.getY();
			double z1 = loc.getZ();
			double distance = Math.sqrt(Math.pow(x1-x0, 2)+Math.pow(y1-y0, 2)+Math.pow(z1-z0, 2));
			if(distance <= range) {
				return true;
			}
		}
		return false;
	}
	
	public static NamespacedKey getKeyForRecipe(String name) {
		return new NamespacedKey(MainCore.instance, "rankcraft_"+name);
	}
	
	public static String getStringWithoutExtension (String str) {
        // Handle null case specially.

        if (str == null) return null;

        // Get position of last '.'.

        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.

        if (pos == -1) return str;

        // Otherwise return the string, up to the dot.

        return str.substring(0, pos);
    }
	
	public static List<String> getOnlinePlayerNames() {	
		List<String> names = new ArrayList<>();
		for(Player pl : Bukkit.getOnlinePlayers())
			names.add(pl.getName());
		return names;
	}
	
	/*
	public static String hiddenString(String s) {
        String hidden = "";
        for (char c : s.toCharArray()) hidden += ChatColor.COLOR_CHAR+""+c;
        return hidden;
    }
	
	public static String getHiddenStringFromBuilder(String s) {
		StringBuilder builder = new StringBuilder();

		for(char c : s.toCharArray()){
		  builder.append(ChatColor.COLOR_CHAR).append(c);
		}

		return builder.toString();
	}
	
	public static String fromHiddenString(String s) {
		return s.replaceAll("§", "");
	}
	
	public static String decodeHiddenString(String s) {
		return s.replaceAll(String.valueOf(ChatColor.COLOR_CHAR), "");
	}
	*/
	public static Class<?> getNMSClass(String clazz) {
        try {
            return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static void sendPacket(Player player, Object packet) {
	    try {
	      Method getHandle = player.getClass().getMethod("getHandle", new Class[0]);
	      Object nmsPlayer = getHandle.invoke(player, new Object[0]);
	      Field pConnectionField = nmsPlayer.getClass().getField("playerConnection");
	      Object pConnection = pConnectionField.get(nmsPlayer);
	      Method sendMethod = pConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") });
	      sendMethod.invoke(pConnection, new Object[] { packet });
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	
	public static boolean areTypeAndNameSame(ItemStack item, RpgItem rpgItem) {
		if(item.hasItemMeta() && item.getType().equals(rpgItem.getMaterial())) 
			if(item.getItemMeta().hasDisplayName()) 
				if(item.getItemMeta().getDisplayName().equals(rpgItem.getDisplayName())) 
					return true;
		return false;
	}
	/*
	public static final List<String> LEGACY_SPELL_NAMES = Arrays.asList("AntiHungerSpell", "AntiPoisonSpell", 
			"BombSpell", "DeathTouchSpell", "EarthTameSpell", "FarmSpell", 
			"FeedSpell", "HealSpell", "HeartsSpell", "HungerSpell", "LightningSpell", "PickPocketSpell", 
			"PoisonSpell", "ShadowSpell", "SpiderSpell");
	
	public static boolean isLegacySpell(ItemStack item) {
		if(item.getType() == Material.PAPER) {
			if(item.hasItemMeta()) {
				if(item.getItemMeta().hasDisplayName()) {
					//System.out.println(stripColor(item.getItemMeta().getDisplayName()));
					String name = stripColor(item.getItemMeta().getDisplayName());
					name = name.substring(0, name.length()-6);
					//System.out.println(name+"?");
					if(LEGACY_SPELL_NAMES.contains(name)) {
						//System.out.println("we're inside "+item.getItemMeta().getPersistentDataContainer().has(Spell.NAME_ID_KEY, PersistentDataType.STRING));
						if(!item.getItemMeta().getPersistentDataContainer().has(Spell.NAME_ID_KEY, PersistentDataType.STRING)) {
							//System.out.println("returning true");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public static Spell getLegacySpell(ItemStack item) {
		if(isLegacySpell(item)) {
			for(String s : LEGACY_SPELL_NAMES) {
				String name = stripColor(item.getItemMeta().getDisplayName());
				name = name.substring(0, name.length()-6);
				if(s.equals(name)) {
					return MainCore.instance.rankCraft.spellUtils.getSpellByNameId(name.toLowerCase());
				}
			}
		}
		
		return null;
	}
	*/
	
}
