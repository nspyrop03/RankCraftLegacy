package me.amc.rankcraft.spells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class EarthTameSpell extends Spell {
	
	private List<Material> forbidden = new ArrayList<>();

	public EarthTameSpell(int power) {
		super(power, 5,"earthtamespell");
		//this.setName(ChatColor.DARK_GREEN+"Earth"+ChatColor.GOLD+"TameSpell");
		this.setName(MainCore.instance.itemLang.getSpellName("EarthTameSpell"));
		this.setUsage(ClickType.Left_Click);
		this.setManaCost(getPower() * 30);
		this.setDescription(MainCore.instance.itemLang.getEarhtTameSpellDescription(getPower()*getPower()));
		//this.addLores("MaxPower: "+this.getMaxPower(), "Power: "+this.getPower());
		//this.addLores(ChatColor.AQUA+"Triggers fround blocks to", ChatColor.AQUA+"move according to its level!");
		this.initItem();
	
		initList();
	}
	
	private void initList() {
		forbidden.add(Material.AIR);
		forbidden.add(Material.LAVA);
		forbidden.add(Material.WATER);
		//forbidden.add(Material.LEGACY_WATER_LILY);
		//forbidden.add(Material.LEGACY_STATIONARY_WATER);
		//forbidden.add(Material.LEGACY_STATIONARY_LAVA);
	}

	@Override
	public void onLeftClick(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if(event.getAction() == Action.LEFT_CLICK_AIR) return;
		event.setCancelled(true);
		
		Block b = event.getClickedBlock();
		
		/* A line of blocks
		if(b.getLocation().getBlockY() < p.getLocation().getY()) {
			for(int i = 1; i <= getPower(); i++) {
				
				b = b.getLocation().add(getCardinalDirection(p)).getBlock();
				b.getLocation().add(0, i, 0).getBlock().setType(Material.DIRT);
				
			}
		}
		*/
		//List<Block> affectedBlocks = new ArrayList<>();
		if(this.hasMana(p, getManaCost())) {
			if(b.getLocation().getBlockY() < p.getLocation().getY()) {
				Block[][] affectedBlocks = new Block[getPower()][getPower()];
				
				for(int i = 0; i < getPower(); i++) {
					for(int j = i; j < getPower(); j++) {
						//affectedBlocks.add(b.getLocation().add(getCardinalDirection(p, j, j)).add(0, -i, 0).getBlock());
						affectedBlocks[i][j] = b.getLocation().add(getCardinalDirection(p, j, j)).add(0, -i, 0).getBlock();
						
					}
				}
				/*
				for(int i = 0; i < affectedBlocks.size(); i++) {
					Block bl = affectedBlocks.get(i);
					Material m = bl.getType();
					System.out.println(bl.getY());
					bl.setType(Material.AIR);
					bl.getLocation().add(0, getPower(), 0).getBlock().setType(m);
				}
				*/
				for(int i = 0; i < getPower(); i++) {
					for(int j = i; j < getPower(); j++) {
						Block bl = affectedBlocks[i][j];
						Material m = bl.getType();
						bl.setType(Material.AIR);
						Block toMake = bl.getLocation().add(0,i*2+1,0).getBlock();
						if(!forbidden.contains(m)) toMake.setType(m);
					}
				}
			}
			
			this.removeItemFromHand(p);
			
			MainCore.instance.rankCraft.callUseSpellEvent(p, this);
			RCUtils.removeAndReloadMana(p, this.getManaCost());
			
		} else {
			this.sendNotEnoughManaMsg(p);
		}
		/*============[ Creates a path towards the selected block :O ]============
		//List<Block> bls = p.getLineOfSight(Set<Material>null, 10);
		List<Block> bls = p.getLineOfSight((Set<Material>)null, getPower());
		bls.add(event.getClickedBlock());
		for(Block b : bls) {
			//if(b.getLocation().equals(event.getClickedBlock().getLocation())) {
				p.sendMessage("Your Y: "+p.getLocation().getBlockY()+" - Block's Y: "+b.getLocation().getBlockY());
			
				if(b.getLocation().getBlockY() <= p.getLocation().getBlockY()) {
					p.sendMessage("Transforming: {"+b.getLocation().getX()+", "+b.getLocation().getY()+", "+b.getLocation().getZ()+"} of type: "+b.getType());
					b.setType(Material.STONE);
				}
			//}
		}
		===================================================*/ 
		
		/*
		if(this.hasMana(p, getManaCost())) {
			
			Block b = event.getClickedBlock();
			if(b.getLocation().getBlockY() < p.getLocation().getY()) {
				for(int i = 1; i <= getPower(); i++) {
					//Vector dir = p.getEyeLocation().getDirection();
					//Block block = Bukkit.getWorld(p.getWorld().getName()).getBlockAt(dir.toLocation(p.getWorld()));
					b.getLocation().add(i,i,0).getBlock().setType(b.getType());
				}
				
				this.removeItemFromHand(p);
				
				MainCore.instance.rankCraft.callUseSpellEvent(p, this);
				RCUtils.removeAndReloadMana(p, this.getManaCost());
			}
			
			
		} else {
			this.sendNotEnoughManaMsg(p);
		}
		*/
	}
	
	private Vector getCardinalDirection(Player player, int x, int z) {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
         if (0 <= rotation && rotation < 22.5) { // west
        	//player.sendMessage("N");
        	//return new Vector(0,0,-1);
        	return new Vector(-x,0,0);
        } else if (22.5 <= rotation && rotation < 67.5) { // north-west
            //return "NE";
        	//player.sendMessage("NE");
        	//return new Vector(1,0,-1);
        	return new Vector(-x,0,-z);
        } else if (67.5 <= rotation && rotation < 112.5) { // north
            //return "E";
        	//player.sendMessage("E");
        	//return new Vector(1,0,0);
        	return new Vector(0,0,-z);
        } else if (112.5 <= rotation && rotation < 157.5) { // north-east
            //return "SE";
        	//player.sendMessage("SE");
        	//return new Vector(1,0,1);
        	return new Vector(x,0,-z);
        } else if (157.5 <= rotation && rotation < 202.5) { // east
            //return "S";
        	//player.sendMessage("S");
        	//return new Vector(0,0,1);
        	return new Vector(x,0,0);
        } else if (202.5 <= rotation && rotation < 247.5) { // south-east
            //return "SW";
        	//player.sendMessage("SW");
        	//return new Vector(-1,0,1);
        	return new Vector(x,0,z);
        } else if (247.5 <= rotation && rotation < 292.5) { // south
            //return "W";
        	//player.sendMessage("W");
        	//return new Vector(-1,0,0);
        	return new Vector(0,0,z);
        } else if (292.5 <= rotation && rotation < 337.5) { // south-west
            //return "NW";
        	//player.sendMessage("NW");
        	//return new Vector(-1,0,-1);
        	return new Vector(-x,0,z);
        } else if (337.5 <= rotation && rotation < 360.0) { // ~ west
            //return "N";
        	//player.sendMessage("N2");
        	//return new Vector(0,0,-1);
        	return new Vector(-x,0,0);
        } else {
        	player.sendMessage("null");
        	return null;
        }
    }
	
}
