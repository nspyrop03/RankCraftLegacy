package me.amc.rankcraft.spells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class LightningSpell extends Spell {

	private List<Integer> bullets = new ArrayList<Integer>();
	private List<Player> no_damage = new ArrayList<Player>();

	public LightningSpell(int power) {
		super(power, 3, "lightningspell");

		//this.setName("§2LightningSpell");
		//System.out.println(MainCore.instance.itemLang == null);
		this.setName(MainCore.instance.itemLang.getSpellName("LightningSpell"));
		//this.setName(MainCore.instance.itemLang.getLightningSpellName());
		this.setUsage(ClickType.Right_Click);
		this.setManaCost(power * 10);
		this.setDescription(MainCore.instance.itemLang.getLightningSpellDescription(getPower()));
		//this.addLores("MaxPower: " + this.getMaxPower(), "Power: " + this.getPower());
		//this.addLores(ChatColor.AQUA+"Throws a bottle which strikes "+getPower()+" lightnings");
		
		this.initItem();

	}

	@Override
	public void onRightClick(PlayerInteractEvent event) {

		Player p = event.getPlayer();

		if (this.hasMana(p, getManaCost())) {
			ThrownPotion tp = p.launchProjectile(ThrownPotion.class);
			bullets.add(tp.getEntityId());
			this.removeItemFromHand(p);
			
			MainCore.instance.rankCraft.callUseSpellEvent(p, this);
			RCUtils.removeAndReloadMana(p, getManaCost());
		} else {
			this.sendNotEnoughManaMsg(p);
		}

	}

	@EventHandler
	public void onPotionHit(ProjectileHitEvent e) {
		if (e.getEntity() instanceof ThrownPotion) {
			int id = e.getEntity().getEntityId();
			if (bullets.contains((Integer) id)) {

				bullets.remove((Integer) id);

				for (int i = 0; i < getPower(); i++) {
					e.getEntity().getWorld().strikeLightning(e.getEntity().getLocation());
				}

				if (e.getEntity().getShooter() instanceof Player) {

					Player p = (Player) e.getEntity().getShooter();
					no_damage.add(p);

				}

			}
		}
	}

	@EventHandler
	public void onEntityHit(EntityDamageByEntityEvent e) {

		if (e.getDamager() instanceof LightningStrike) {
			if (e.getEntity() instanceof Player) {

				Player p = (Player) e.getEntity();

				if (no_damage.contains(p)) {
					e.setDamage(0);
					no_damage.remove(p);

				} else {
					e.setDamage(e.getDamage());
				}

			}
		}
	}

}
