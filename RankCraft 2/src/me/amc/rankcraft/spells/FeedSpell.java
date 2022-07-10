package me.amc.rankcraft.spells;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class FeedSpell extends Spell {

	public FeedSpell(int power) {
		super(power, 5, "feedspell");

		//this.setName("§6FeedSpell");
		this.setName(MainCore.instance.itemLang.getSpellName("FeedSpell"));
		this.setUsage(ClickType.Right_Click);
		this.setManaCost(getPower() * 10);
		this.setDescription(MainCore.instance.itemLang.getFeedSpellDescription(getPower()));
		//this.addLores("MaxPower: " + this.getMaxPower(), "Power: " + this.getPower());
		//this.addLores(ChatColor.AQUA+"Fills "+getPower()+" drumsticks");
		this.initItem();

	}

	@Override
	public void onRightClick(PlayerInteractEvent event) {
		Player p = event.getPlayer();

		if (this.hasMana(p, this.getManaCost())) {
			if (p.getFoodLevel() < 20) {
				p.setFoodLevel(p.getFoodLevel() + getPower() * 2);
				this.removeItemFromHand(p);

				MainCore.instance.rankCraft.callUseSpellEvent(p, this);
				RCUtils.removeAndReloadMana(p, this.getManaCost());
			} else {
			//	p.sendMessage("You have all your drumsticks filled !");
				p.sendMessage(MainCore.instance.language.getSpellFilledDrumsticks());
			}
		} else {
			this.sendNotEnoughManaMsg(p);
		}

	}

}
