package me.amc.rankcraft.packs;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.amc.rankcraft.ConfigHelper;
import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;
import me.amc.rankcraft.yaml.PackYaml;

public class BlockPack extends Pack {

	private PackYaml y;
	private ConfigHelper c;
	
	public BlockPack(PackLevel level) {
		super(PackType.Blocks, level);
		
		y = new PackYaml("blocks");
		c = MainCore.instance.config;
		
		switch(level) {
		case Bronze:
			this.setName("§6BronzeBlockPack");
			this.setCost(c.bronzeBlockPackCost);
			break;
		case Gold:
			this.setName("§eGoldBlockPack");
			this.setCost(c.goldBlockPackCost);
			break;
		case Silver:
			this.setName("§7SilverBlockPack");
			this.setCost(c.silverBlockPackCost);
			break;
		default:
			this.setName("§4Undefined Name !");
			break;
		
		}
	
		switch(level) {
		case Bronze:
			this.setSize(4);
			break;
		case Gold:
			this.setSize(12);
			break;
		case Silver:
			this.setSize(8);
			break;
		default:
			this.setSize(0);
			break;
		
		}
		this.initItem();
		this.getItem().addLores(ChatColor.GREEN+"Size: "+this.getSize());
		
		this.getItem().build();
		
	}
	

	@Override
	public void onRightClickPack(PlayerInteractEvent e) {
		//super.onRightClickPack(e);
		
		Player p = e.getPlayer();
		
		switch(this.getLevel()) {
		case Bronze:
			
			RCUtils.removeItemFromHand(p);
			Random r = new Random();
			
		//	p.sendMessage("§2From this BlockPack you got: ");
			p.sendMessage(MainCore.instance.language.getOpenPack("Block"));
			for(int i = 0; i < this.getSize(); i++) {
				
				Material mat = y.getMaterialList("bronze").get(r.nextInt(y.getMaterialList("bronze").size()));
				int max = y.getMaxAmount("bronze");
				int min = y.getMinAmount("bronze");
				int amt = r.nextInt((max - min) + 1) + min;
				
				p.getInventory().addItem(new ItemStack(mat, amt));
			//	p.sendMessage("§6- "+amt+" "+mat);
				p.sendMessage(MainCore.instance.language.getItemFromPack("Block", amt, ""+mat));
				
			}
			
			break;
		case Gold:
			
			RCUtils.removeItemFromHand(p);
			Random k = new Random();
			
		//	p.sendMessage("§2From this BlockPack you got: ");
			p.sendMessage(MainCore.instance.language.getOpenPack("Block"));
			for(int i = 0; i < this.getSize(); i++) {
				
				Material mat = y.getMaterialList("gold").get(k.nextInt(y.getMaterialList("gold").size()));
				int max = y.getMaxAmount("gold");
				int min = y.getMinAmount("gold");
				int amt = k.nextInt((max - min) + 1) + min;
				
				p.getInventory().addItem(new ItemStack(mat, amt));
			//	p.sendMessage("§6- "+amt+" "+mat);
				p.sendMessage(MainCore.instance.language.getItemFromPack("Block", amt, ""+mat));
				
			}
			
			break;
		case Silver:
			
			RCUtils.removeItemFromHand(p);
			Random j = new Random();
			
	//		p.sendMessage("§2From this BlockPack you got: ");
			p.sendMessage(MainCore.instance.language.getOpenPack("Block"));
			for(int i = 0; i < this.getSize(); i++) {
			
				Material mat = y.getMaterialList("silver").get(j.nextInt(y.getMaterialList("silver").size()));
				int max = y.getMaxAmount("silver");
				int min = y.getMinAmount("silver");
				int amt = j.nextInt((max - min) + 1) + min;
				
				p.getInventory().addItem(new ItemStack(mat, amt));
		//		p.sendMessage("§6- "+amt+" "+mat);
				p.sendMessage(MainCore.instance.language.getItemFromPack("Block", amt, ""+mat));
				
			}
			
			break;
		default:
			System.out.println("[Error] BlockPack list not initialized!!!");
			break;
		
		}
		
		
	}
	
}
