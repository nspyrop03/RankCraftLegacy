package me.amc.rankcraft.spells;

import java.util.HashMap;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class AntiPoisonSpell extends Spell {

	protected static HashMap<String, Boolean> antiPoison = new HashMap<>(); // UUID

	public AntiPoisonSpell(int power) {
		super(power, 1,"antipoisonspell");
		//this.setName("§1Anti§2PoisonSpell");
		this.setName(MainCore.instance.itemLang.getSpellName("AntiPoisonSpell"));
		this.setUsage(ClickType.Right_Click);
		this.setManaCost(getPower() * 10);
		this.setDescription(MainCore.instance.itemLang.getAntiPoisonSpellDescription());
		//this.addLores(ChatColor.AQUA+"Use it to defense against"+ChatColor.GREEN+" PoisonSpell");
		this.initItem();
	}

	public static void setAntiPoison(Player p, boolean bool) {
		antiPoison.put(RCUtils.textedUUID(p), bool);
	}

	public static boolean getAntiPoison(Player p) {
		if (inAntiPoison(p)) {
			return antiPoison.get(RCUtils.textedUUID(p));
		} else {
			return false;
		}
	}

	public static boolean hasAntiPoison(Player p) {
		if (getAntiPoison(p) == true) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean inAntiPoison(Player p) {
		return antiPoison.containsKey(RCUtils.textedUUID(p));
	}

	@Override
	public void onRightClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (this.hasMana(p, this.getManaCost())) {
			if (getAntiPoison(p) == false) {
				setAntiPoison(p, true);
			//	p.sendMessage("Your AntiPoison defense has successfully enabled !!!");
				p.sendMessage(MainCore.instance.language.getSpellAntiPoisonActivate());
				MainCore.instance.rankCraft.antiSpellsData.save();
				this.removeItemFromHand(p);
				
				MainCore.instance.rankCraft.callUseSpellEvent(p, this);
				RCUtils.removeAndReloadMana(p, this.getManaCost());
				createParticleSphere(p);
			} else {
			//	p.sendMessage("§2Your AntiPoison defense is already enabled !");
				p.sendMessage(MainCore.instance.language.getSpellAntiPoisonEnabled());
			}
		} else {
			this.sendNotEnoughManaMsg(p);
		}
		//createParticleSphere(p);
	}

	private void createParticleSphere(Player p) {
		World w = p.getWorld();
		int R = 1;
		float x;
		float y;
		float z;
		float centreX = (float)p.getLocation().add(0, 1, 0).getX();
		float centreY = (float)p.getLocation().add(0, 1, 0).getY();
		float centreZ = (float)p.getLocation().add(0, 1, 0).getZ();
		x = centreX;
		z = centreZ;
		y = centreY;
		
		for(int phi = 0; phi <= 360; phi+=10) {
			x = (float) (centreX + R * Math.sin(phi));
			//z = (float) (centreZ + R * Math.cos(phi));
			z = (float) (centreZ + R * Math.sin(phi));
			y = (float) (centreY + R * Math.cos(phi));
			
			
			DustOptions d = new DustOptions(Color.fromRGB(50, 204, 39), 1.2f);
			
			w.spawnParticle(Particle.REDSTONE, new Location(w, x, y, centreZ, p.getLocation().getYaw(),p.getLocation().getPitch()), 5, d);
			w.spawnParticle(Particle.REDSTONE, new Location(w, centreX, y, z, p.getLocation().getYaw(),p.getLocation().getPitch()), 5, d);
			//ParticleEffect.REDSTONE.display(new OrdinaryColor(0,0,255), new Location(w, x, y, centreZ));
		}
		
	}
	
}
