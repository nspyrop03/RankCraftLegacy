package me.amc.rankcraft.spells;

import java.util.HashMap;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class AntiHungerSpell extends Spell {

	protected static HashMap<String, Boolean> antiHunger = new HashMap<>(); // UUID

	public AntiHungerSpell(int power) {
		super(power, 1,"antihungerspell");
		//this.setName("§1Anti§6HungerSpell");
		this.setName(MainCore.instance.itemLang.getSpellName("AntiHungerSpell"));
		this.setUsage(ClickType.Right_Click);
		this.setManaCost(getPower() * 10);
		this.setDescription(MainCore.instance.itemLang.getAntiHungerSpellDescription());
		//this.addLores(ChatColor.AQUA+"Use it to defense against"+ChatColor.GOLD+" HungerSpell");
		this.initItem();
	}

	public static void setAntiHunger(Player p, boolean bool) {
		antiHunger.put(RCUtils.textedUUID(p), bool);
	}

	public static boolean getAntiHunger(Player p) {
		if (inAntiHunger(p)) {
			return antiHunger.get(RCUtils.textedUUID(p));
		} else {
			return false;
		}
	}

	public static boolean hasAntiHunger(Player p) {
		if (getAntiHunger(p) == true) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean inAntiHunger(Player p) {
		return antiHunger.containsKey(RCUtils.textedUUID(p));
	}

	@Override
	public void onRightClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (this.hasMana(p, this.getManaCost())) {
			if (getAntiHunger(p) == false) {
				setAntiHunger(p, true);
			//	p.sendMessage("Your AntiHunger defense has successfully enabled !!!");
				p.sendMessage(MainCore.instance.language.getSpellAntiHungerActivate());
				MainCore.instance.rankCraft.antiSpellsData.save();
				this.removeItemFromHand(p);
				
				MainCore.instance.rankCraft.callUseSpellEvent(p, this);
				RCUtils.removeAndReloadMana(p, this.getManaCost());
				
				createParticleSphere(p);
			} else {
			//	p.sendMessage("§2Your AntiHunger defense is already enabled !");
				p.sendMessage(MainCore.instance.language.getSpellAntiHungerEnabled());
			}
		} else {
			this.sendNotEnoughManaMsg(p);
		}
		/*
		BukkitRunnable br = new BukkitRunnable() {
			int time = 0;
			@Override
			public void run() {
				time++;
				createParticleSphere(p);				
				if(time>= 10)
					this.cancel();
			}
		};
		br.runTaskTimer(MainCore.instance, 0, 1);
		 */
		//createParticleSphere(p);
	}
	
	private void createParticleSphere(Player p) {
		World w = p.getWorld();
		int R = 1;
		float x;
		float y;
		float z;
		float z2;
		float centreX = (float)p.getLocation().add(0, 1, 0).getX();
		float centreY = (float)p.getLocation().add(0, 1, 0).getY();
		float centreZ = (float)p.getLocation().add(0, 1, 0).getZ();
		x = centreX;
		z = centreZ;
		y = centreY;
		z2 = centreZ;
		for(int phi = 0; phi <= 360; phi+=10) {
			x = (float) (centreX + R * Math.sin(phi));
			z2 = (float) (centreZ + R * Math.cos(phi));
			z = (float) (centreZ + R * Math.sin(phi));
			y = (float) (centreY + R * Math.cos(phi));
		
			
			DustOptions d = new DustOptions(Color.fromRGB(235, 180, 52), 1.2f);
			
			w.spawnParticle(Particle.REDSTONE, new Location(w, x, y, centreZ), 5, d);
			w.spawnParticle(Particle.REDSTONE, new Location(w, centreX, y, z), 5, d);
			w.spawnParticle(Particle.REDSTONE, new Location(w, x, centreY, z2), 5, d);
			//ParticleEffect.REDSTONE.display(new OrdinaryColor(0,0,255), new Location(w, x, y, centreZ));
		}
		/*
		float z2 = centreZ-1.8f;
		for(int phi = 0; phi <= 360; phi+=10) {
			x = (float) (centreX + R * Math.sin(phi));
			y = (float) (centreY + R * Math.cos(phi));
			z2 += 0.05;
			w.spawnParticle(particle, new Location(w, x, y, z2), 2);
		}
		*/
	}
}
