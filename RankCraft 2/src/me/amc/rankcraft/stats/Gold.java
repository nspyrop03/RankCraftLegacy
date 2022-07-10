package me.amc.rankcraft.stats;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class Gold {

	private HashMap<String, Float> gold = new HashMap<>();
	
	public void setGold(Player p, float amt) {
		if(!MainCore.instance.config.useVault) {
			gold.put(RCUtils.textedUUID(p), amt);
			save();
			MainCore.instance.scoreboard.updateBoard(p);
		} else {
			MainCore.instance.sendError("An error occured while trying to set the player's money!");
		}
	}
	
	public float getGold(Player p) {
		if(!MainCore.instance.config.useVault) {
			return gold.get(RCUtils.textedUUID(p));
		} else {
			return (float)MainCore.instance.econ.getBalance(p);
		}
	}
	
	public boolean hasGoldAmount(Player p, float amt) {
		if(!MainCore.instance.config.useVault) {
			return getGold(p) >= amt;
		} else {
			return MainCore.instance.econ.has(p, amt);
		}
	}
	
	public void addGold(Player p, float amt) {
		if(!MainCore.instance.config.useVault) {
			setGold(p, getGold(p) + amt);
		} else {
	//		EconomyResponse r = MainCore.instance.econ.depositPlayer(p, amt);
			MainCore.instance.econ.depositPlayer(p, amt);
    //        if(r.transactionSuccess()) {
     //           p.sendMessage(String.format("You were given %s and now have %s", MainCore.instance.econ.format(r.amount), econ.format(r.balance)));
     //       } else {
     //           sender.sendMessage(String.format("An error occured: %s", r.errorMessage));
     //       }
		}
	}
	
	public void removeGold(Player p, float amt) {
		if(!MainCore.instance.config.useVault) {
			setGold(p, getGold(p) - amt);
		} else {
			MainCore.instance.econ.withdrawPlayer(p, amt);
		}
	}
	
	public void save() {
		
		try {
			
			YamlConfiguration stats = new YamlConfiguration();
			stats.createSection("gold", gold);
			stats.save(new File(RCUtils.STATS_DIRECTORY, "gold.yml"));
			
		} catch(Exception ex) {
			MainCore.instance.getLogger().log(Level.SEVERE, "[GoldSaver] Save Failed!", ex);
		}
		
	}
	
	public void load() {
		try {
		
			File stats = new File(RCUtils.STATS_DIRECTORY, "gold.yml");
			if(stats.exists()) {
				
				YamlConfiguration config = new YamlConfiguration();
				config.load(stats);
				
				ConfigurationSection sec = config.getConfigurationSection("gold");
				
				
				for(String key : sec.getKeys(false)) {
					gold.put(key, (float) sec.getInt(key));
				}
				
			} 
			
			
		} catch(Exception ex) {
			MainCore.instance.getLogger().log(Level.SEVERE, "[GoldLoader] Load Failed!", ex);
		}
	}
	
	
	public ItemStack toItem(float amt) {
		//ItemStack stack = new ItemStack(Material.GOLD_NUGGET, 1);
		
		Material type = MainCore.instance.config.currencyItemType;
		ItemStack stack = new ItemStack(type, 1);
		
		ItemMeta meta = stack.getItemMeta();
		List<String> lore = new ArrayList<String>();
		
		//lores.add("Rightclick to get "+amt+" gold !");
		lore.addAll(MainCore.instance.config.getCurrencyItemLore((float)RCUtils.round(amt, 2)));
		
		meta.setLore(lore);
		
		//meta.setDisplayName("§eGold : "+amt);
		meta.setDisplayName(MainCore.instance.config.currencyName+" : "+amt);
		
		meta.setCustomModelData(1903170001);
		
		stack.setItemMeta(meta);
		return stack;
	}
	
	public Float getGoldFromItem(ItemStack stack) {
		String name = stack.getItemMeta().getDisplayName();
		String[] parts = name.split(" : ");
		float amt = Float.parseFloat(parts[1]);
		return amt;
	}
	
	public void initForPlayer(Player p) {
		
		if(!gold.containsKey(RCUtils.textedUUID(p))) {
			setGold(p, 100.0F);
		}
		
	}
	
	public boolean areAllOk(Player p) {
		if(gold.containsKey(RCUtils.textedUUID(p))) {
			return true;
		} else {
			return false;
		}
	}
}
