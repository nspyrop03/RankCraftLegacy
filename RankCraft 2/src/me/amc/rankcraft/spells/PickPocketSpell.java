package me.amc.rankcraft.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.lang.Language;
import me.amc.rankcraft.utils.RCUtils;

public class PickPocketSpell extends Spell {

	private Language lang = MainCore.instance.language;
	
	public PickPocketSpell(int power) {
		super(power, 1,"pickpocketspell");
		//this.setName("§5PickPocketSpell");
		this.setName(MainCore.instance.itemLang.getSpellName("PickPocketSpell"));
		this.setUsage(ClickType.Entity_Hit);
		this.setManaCost(this.getPower() * 50);
		this.setDescription(MainCore.instance.itemLang.getPickPocketSpellDescription(getPower()));
		//this.addLores("MaxPower: "+this.getPower(), "Power: "+this.getPower());
		//this.addLores(ChatColor.AQUA+"Hit a player to steal",ChatColor.AQUA+"a random item from him");
		
		this.initItem();
	}
	
	@Override
	public void onHitEntity(EntityDamageByEntityEvent e) {
		Player p = (Player) e.getDamager();
		
		if(e.getEntity() instanceof Player) {
			Player p2 = (Player) e.getEntity();
			
			if(this.hasMana(p, getManaCost())) {
				
				List<ItemStack> items = new ArrayList<>();
				for(ItemStack i1 : p2.getInventory().getContents()) {
					if(i1 != null && i1.getType() != Material.AIR) items.add(i1);
				}
				for(ItemStack i2 : p2.getInventory().getArmorContents()) {
					if(i2 != null && i2.getType() != Material.AIR) items.add(i2);
				}
				
				if(items.size() <= 0) {
					//p.sendMessage(MainCore.instance.language.getPrefix()+" Oups! It seems that "+p2.getName()+" does not have any items!");
					p.sendMessage(lang.getEmptyPlayer(p2.getName()));
				} else {
				
					Random random = new Random();
					ItemStack stolen = items.get(random.nextInt(items.size()));
					
					String name = stolen.getType().toString();
					
					if(stolen.hasItemMeta()) {
						if(stolen.getItemMeta().hasDisplayName()) {
							name = stolen.getItemMeta().getDisplayName();
						}
					}
					
					p.getInventory().addItem(stolen);
					p2.getInventory().remove(stolen);
					//p.sendMessage(MainCore.instance.language.getPrefix()+"You successfully stole "+name+" from "+p2.getName()+"!");
					p.sendMessage(lang.getYouStole(p2.getName(), name));
					//p2.sendMessage(MainCore.instance.language.getPrefix()+p.getName()+" stole "+name+" from you!");
					p2.sendMessage(lang.getStolenFromYou(p.getName(), name));
				}
				
				this.removeItemFromHand(p);
				MainCore.instance.rankCraft.callUseSpellEvent(p, this);
				RCUtils.removeAndReloadMana(p, this.getManaCost());
				
			} else {
				this.sendNotEnoughManaMsg(p);
			}
			
		} else {
			//p.sendMessage(MainCore.instance.language.getPrefix()+" §4You can use this spell only on players!");
			p.sendMessage(lang.getUseOnPlayer());
		}
		
	}
}
