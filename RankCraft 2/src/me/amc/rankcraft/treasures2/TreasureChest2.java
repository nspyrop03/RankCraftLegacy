package me.amc.rankcraft.treasures2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.damage.DamageSystem;
import me.amc.rankcraft.rpgitem.RpgItem;
import me.amc.rankcraft.utils.CustomItem;
import me.amc.rankcraft.utils.RCUtils;

public class TreasureChest2 extends CustomItem {

	public enum Rarity {
		COMMON(ChatColor.GREEN, "Common"),
		RARE(ChatColor.BLUE, "Rare"),
		EPIC(ChatColor.GOLD, "Epic");
		
		private ChatColor color;
		private String name;
		
		Rarity(ChatColor color, String name) {
			this.color = color;
			this.name = name;
		}
		
		ChatColor getColor() {
			return this.color;
		}
		
		String getName() {
			return this.name;
		}
	}
	
	private File f;
	private Rarity rarity;
	//private String fileName;
	
	private String id;
	
	public TreasureChest2(String name, String fileName, Rarity r) {
		super(Material.CHEST);
		
		this.id = fileName;
		this.rarity = r;
		
		this.setName(name.replace('_', ' ').replace('&', '§'));
		this.addLores(ChatColor.DARK_GRAY+"Id: "+fileName, ChatColor.DARK_GRAY+"Rarity: "+r.getColor()+r.getName());
		
		
		f = new File(RCUtils.TREASURES2_DIRECTORY+"/"+fileName+".yml");
		if(!f.exists()) {
			try {
				f.createNewFile();
				
				FileWriter writer = new FileWriter(f);
				writer.write("Name: '"+name+"' \n");
				writer.write("Rarity: '"+r.getName()+"'");
				writer.flush();
				writer.close();
				
			} catch (IOException e) {
				MainCore.instance.sendError("[Treasures2] Something went wrong!");
			}
			
		}
	}

	public void giveToPlayer(Player p, int amount) {
		for(int i = 0; i < amount; i++) {
			p.getInventory().addItem(this.build().getItem());
		}
		//System.out.println("Called giveToPlayer!!!");
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemStack> getTreasureItems() {

		try {
			//File f = new File(RCUtils.TREASURES2_DIRECTORY+"/"+fileName+".yml");
			FileConfiguration c = YamlConfiguration.loadConfiguration(f);
			List<ItemStack> content = ((List<ItemStack>) c.get("items"));
			
			return content;
		} catch(Exception ex) {
			MainCore.instance.sendError("Something went wrong!!!");
			return null;
		}
		

	}

	public Rarity getRarity() {
		return rarity;
	}

	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}
	
	public void openForPlayer(Player p) {
		
		Random r = new Random();
		RCUtils.removeItemFromHand(p);
		
		boolean change = true;
		
		if(areAllItemsAir()) {
			//p.sendMessage(ChatColor.DARK_RED+"[ERROR] Empty treasure!");
			p.sendMessage(MainCore.instance.language.getTreasures2EmptyTreasure());
			return;
		}
		
		ItemStack item  = getTreasureItems().get(r.nextInt(getTreasureItems().size()));
		while(item.getType() == Material.AIR) {
			item  = getTreasureItems().get(r.nextInt(getTreasureItems().size()));
			//System.out.println("Changing...!");
		}
		if(item.hasItemMeta()) {
			if(item.getItemMeta().hasLore()) {
				for(String line : item.getItemMeta().getLore()) {
					if(line.equals(ChatColor.GOLD+"Lootable")) {
						change = false;
					}
				}
			}
		}
	
		if(item.getAmount() > 1 && change) {
			item.setAmount(r.nextInt(item.getAmount())+1);
		}
		
		//CustomItem cItem = new CustomItem(item);
		
		if(item.hasItemMeta() && RCUtils.isToolOrArmor(item) && change) {
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
				
				rItem.setLevelToUse(DamageSystem.getMinLevelOfRpgItem(item));
				
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
		}
		
		p.getInventory().addItem(item);
		
		/*
		String looted;
		if(item.hasItemMeta()) 
			looted = item.getItemMeta().getDisplayName();
		else 
			looted = item.getType().toString();
		*/
		//p.sendMessage("You looted: "+looted+" x "+item.getAmount());
		
	}
	
	public File getFile() {
		return f;
	}
	
	private boolean areAllItemsAir() {
		for(ItemStack i : getTreasureItems()) {
			if(i.getType() != Material.AIR) {
				return false;
			}
		}
		return true;
	}
	
	public String getId() {
		return this.id;
	}
}
