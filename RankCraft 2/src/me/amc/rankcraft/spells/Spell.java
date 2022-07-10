package me.amc.rankcraft.spells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class Spell implements Listener {

	public static final NamespacedKey NAME_ID_KEY = new NamespacedKey(MainCore.instance, "spellNameId");
	
	private String name = "Empty Spell";
	private List<String> lores = new ArrayList<String>();
	private ItemStack item = new ItemStack(Material.PAPER, 1);
	private ItemMeta meta;
	private float manaCost = 0;
	
	private String description;
	
	public enum ClickType {
		Right_Click, Left_Click, Empty_Click, Entity_Hit
	}

	private ClickType usage = ClickType.Empty_Click;

	private int power;
	private int maxPower;

	private String defaultName = "Lvl.";
	
	private String id;
	
	private String nameId;

	public Spell(int power, int maxPower, String nameId) {
		this.maxPower = maxPower;

		if (power <= maxPower) {
			this.power = power;
		} else {
			this.power = maxPower;
		}

		this.defaultName = "§f" + this.defaultName + this.power;

		this.nameId = nameId+"_"+this.power;
		
	}

	public Spell(int power, String nameId) {
		this(power, 5, nameId);
		this.defaultName = "§f" + this.defaultName + this.power;
		this.nameId = nameId+"_"+this.power;
	}
	
	public String getNameId() {
		return nameId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name + " " + defaultName;
	}

	public ClickType getUsage() {
		return usage;
	}

	public void setUsage(ClickType usage) {
		this.usage = usage;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

	public List<String> getLores() {
		return lores;
	}

	public void setLores(List<String> lores) {
		this.lores = lores;
	}

	public float getManaCost() {
		return manaCost;
	}

	public void setManaCost(float manaCost) {
		this.manaCost = manaCost;
	}

	public boolean hasMana(Player p, float cost) {
		if (MainCore.instance.rankCraft.stats.getMana(p) >= cost) {
			return true;
		} else {
			return false;
		}
	}

	public void addLores(String... strings) {
		for (String s : strings) {
			lores.add(s);
		}
	}

	public void removeLores(String... strings) {
		for (String s : strings) {
			if (lores.contains(s)) {
				lores.remove(s);
			} else {
				return;
			}
		}
	}
	/*
	public void addDefaultLores() {
		addLores("" + usage, "Power: " + power);
	}
	 */
	public int getMaxPower() {
		return maxPower;
	}

	public void setMaxPower(int maxPower) {
		this.maxPower = maxPower;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void initItem() {
		meta = item.getItemMeta();
		meta.setDisplayName(name);
		lores.addAll(MainCore.instance.itemLang.getSpellLore(power, maxPower, manaCost, this.usage.toString(), description));
		//lores.add(ChatColor.BLUE + "Mana Cost: " + this.getManaCost());
		//lores.add(ChatColor.DARK_GRAY+"Usage: "+this.usage.toString());
		
		//lores.add(RCUtils.hiddenString("nameId: "+nameId));
		//lores.add(getHiddenString("nameId: "+nameId));
		//String h = getHiddenString("nameId: "+nameId);
		
		meta.getPersistentDataContainer().set(NAME_ID_KEY, PersistentDataType.STRING, nameId);
		
		//lores.add(h);
		//System.out.println(h +" - "+lores.get(lores.size()-1));
		//lores.add(RCUtils.getHiddenStringFromBuilder("nameId: "+nameId));
		
		//lores.add(HiddenStringUtils.encodeString("nameId: "+nameId));
		
		//lores.add("nameId: "+nameId);
		
		meta.setLore(lores);

		item.setItemMeta(meta);
	}

	public void onRightClick(PlayerInteractEvent event) {
		usage = ClickType.Right_Click;
		this.onClick(event);
	}

	public void onLeftClick(PlayerInteractEvent event) {
		usage = ClickType.Left_Click;
		this.onClick(event);
	}

	public void removeItemFromHand(Player p) {
		ItemStack hand = p.getInventory().getItemInMainHand();
		if(hand == null || hand.getType() == Material.AIR) return;
		int amount = hand.getAmount();
		if (amount == 1) {
			//p.getInventory().setItemInHand(new ItemStack(Material.AIR));
			//p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
			p.getInventory().getItemInMainHand().setAmount(0);
		} else {
			hand.setAmount(amount - 1);
		}
	}

	public void onHitEntity(EntityDamageByEntityEvent e) {
		usage = ClickType.Entity_Hit;
		this.onHit(e);
	}

	public void sendNotEnoughManaMsg(Player p) {
	//	p.sendMessage("§4You do not have enough §1Mana §4to use this spell !!!");
		p.sendMessage(MainCore.instance.language.getSpellNotEnoughMana());
	}

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		
		switch (usage) {
		case Empty_Click:
			if(!RCUtils.isPlayerWorldEnabled(e.getPlayer()))
				return;
			e.getPlayer().sendMessage("" + usage);
			e.getPlayer().sendMessage("This is an empty Spell !!!");
			break;
		case Left_Click:
			if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				Player p = e.getPlayer();
				ItemStack hand = p.getInventory().getItemInMainHand();
				/* Old method based on name
				if (hand.getType() == Material.PAPER) {
					if (!hand.hasItemMeta())
						return;
					if (hand.getItemMeta().getDisplayName().equals(getName())) {
						if(!RCUtils.isPlayerWorldEnabled(e.getPlayer()))
							return;
						if(!MainCore.instance.config.isSpellEnabled(RCUtils.getSpellFromItemStack(hand).getClass().getSimpleName()))
							return;
						
						onLeftClick(e);

					}
				} */
				
				if(isItemLegacySpell(hand)) {
					//System.out.println("legacy hello 1");
					updateLegacySpellItem(p, hand);
								
				}
				// New method based on nameId
				if(isItemThisSpell(hand)) {
					if(!RCUtils.isPlayerWorldEnabled(e.getPlayer()))
						return;
					if(!MainCore.instance.config.isSpellEnabled(RCUtils.getSpellFromItemStack(hand).getClass().getSimpleName()))
						return;
					
					onLeftClick(e);
				}
				
			}
			break;
		case Right_Click:
			if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Player p = e.getPlayer();
				ItemStack hand = p.getInventory().getItemInMainHand();
				/*
				if (hand.getType() == Material.PAPER) {

					if (!hand.hasItemMeta())
						return;
					if (hand.getItemMeta().getDisplayName().equals(getName())) {
						if(!RCUtils.isPlayerWorldEnabled(e.getPlayer()))
							return;
						if(!MainCore.instance.config.isSpellEnabled(RCUtils.getSpellFromItemStack(hand).getClass().getSimpleName()))
							return;
						
						onRightClick(e);

					}
				}
				*/
				
				if(isItemLegacySpell(hand)) {
					//System.out.println("legacy hello 2");
					updateLegacySpellItem(p, hand);
				}
				
				if(isItemThisSpell(hand)) {
					if(!RCUtils.isPlayerWorldEnabled(e.getPlayer()))
						return;
					if(!MainCore.instance.config.isSpellEnabled(RCUtils.getSpellFromItemStack(hand).getClass().getSimpleName()))
						return;
					
					onRightClick(e);
				}
				
			}
			break;
		default:
			break;

		}

		
		
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onHit(EntityDamageByEntityEvent e) {
		
		switch (usage) {
		case Empty_Click:
			break;
		case Entity_Hit:
			if (e.getDamager() instanceof Player) {
				Player p = (Player) e.getDamager();
				ItemStack hand = p.getInventory().getItemInMainHand();
				/*
				if (hand.getType() == Material.PAPER) {
					if (!hand.hasItemMeta()) {
						return;
					}

					if (hand.getItemMeta().getDisplayName().equals(getName())) {
						if(!RCUtils.isPlayerWorldEnabled(p))
							return;
						if(!MainCore.instance.config.isSpellEnabled(RCUtils.getSpellFromItemStack(hand).getClass().getSimpleName()))
							return;
						
						onHitEntity(e);

					}
				}
				*/
				
				if(isItemLegacySpell(hand))  {
					//System.out.println("legacy spell hello?");
					updateLegacySpellItem(p, hand);
				}
				if(isItemThisSpell(hand)) {
					if(!RCUtils.isPlayerWorldEnabled(p))
						return;
					if(!MainCore.instance.config.isSpellEnabled(RCUtils.getSpellFromItemStack(hand).getClass().getSimpleName()))
						return;
					
					onHitEntity(e);
				}
				
			}
			break;
		case Left_Click:
			break;
		case Right_Click:
			break;
		default:
			break;

		}
	}
	
	private void updateLegacySpellItem(Player p, ItemStack old) {
		ItemMeta meta = old.getItemMeta();
		meta.getPersistentDataContainer().set(NAME_ID_KEY, PersistentDataType.STRING, nameId);
		old.setItemMeta(meta);
		//p.getInventory().setItemInMainHand(old);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	private boolean isItemThisSpell(ItemStack item) {
		if(item.getType() == Material.PAPER) 
			if(item.hasItemMeta()) 
				if(item.getItemMeta().getPersistentDataContainer().has(NAME_ID_KEY, PersistentDataType.STRING)) 
					if(item.getItemMeta().getPersistentDataContainer().get(NAME_ID_KEY, PersistentDataType.STRING).equals(nameId)) 
						return true;
		return false;
	}
	
	private boolean isItemLegacySpell(ItemStack item) {
		if(item.getType() == Material.PAPER) {
			if(item.hasItemMeta()) {
				if(item.getItemMeta().hasDisplayName()) {
					String name = RCUtils.stripColor(item.getItemMeta().getDisplayName());
					name = name.substring(0, name.length() - 6);
					name = name.toLowerCase();
					String[] parts = nameId.split("_");
					String nameIdWithoutLevel = parts[0].toLowerCase();
					if(name.equals(nameIdWithoutLevel)) {
						if(!item.getItemMeta().getPersistentDataContainer().has(NAME_ID_KEY, PersistentDataType.STRING)) {
							return true;
						}
					}
				}
			}
		}
		
		return false;			
	}
	
	/*
	private boolean isItemThisSpell(ItemStack item) {
		if(item.getType() == Material.PAPER) {
			if(item.hasItemMeta()) {
				if(item.getItemMeta().getPersistentDataContainer().has(NAME_ID_KEY, PersistentDataType.STRING)) {
					if(item.getItemMeta().getPersistentDataContainer().get(NAME_ID_KEY, PersistentDataType.STRING).equals(nameId)) {
						return true;
					}
				}
				///*
				if(item.getItemMeta().hasLore()) {
					List<String> lore = item.getItemMeta().getLore();
					for(int i = lore.size()-1; i >= 0; i--) {
						//System.out.println(lore.get(i));
						//if(HiddenStringUtils.hasHiddenString(lore.get(i))) {
							//String line = RCUtils.decodeHiddenString(lore.get(i));
							//System.out.println(lore.get(i));
							//String line = HiddenStringUtils.extractHiddenString(lore.get(i));
						String line = getFromHiddenString(lore.get(i));
						
							System.out.println(line +" - "+ lore.get(i));
							if(line.startsWith("nameId:")) {
								String iNameId = line.split(": ")[1];
								//String tNameId = this.nameId+"_"+this.power;
								//System.out.println("t: "+tNameId);
								if(iNameId.equals(nameId)) {
									System.out.println(nameId+" found it!");
									return true;
									
								}
							}
						//}
					}
				}
				//* /
			}
		}
		return false;
	}
	*/
	
}
