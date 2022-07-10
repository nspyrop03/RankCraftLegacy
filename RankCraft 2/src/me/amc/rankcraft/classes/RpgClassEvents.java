package me.amc.rankcraft.classes;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.customevents.PlayerUseSpellEvent;
import me.amc.rankcraft.utils.RCUtils;
import me.amc.rankcraft.yaml.MobsYaml;

public class RpgClassEvents /* implements Listener */ {

	// public RpgClassEvents() {
	// Bukkit.getServer().getPluginManager().registerEvents(this,
	// MainCore.instance);
	// }

	// @EventHandler
	public void gladiatorEvent(EntityDeathEvent e) {
		LivingEntity dead = e.getEntity();
		if (dead.getKiller() instanceof Player) {
			Player killer = dead.getKiller();

			if (RpgClassData.getCurrentClass(killer).equals(MainCore.instance.rankCraft.gladiatorClass.getId())) {

				// System.out.println("called");

				String deadMob = dead.getType().toString();
				deadMob = deadMob.toLowerCase();

				String finishedDeadMob = deadMob.substring(0, 1).toUpperCase() + deadMob.substring(1);
				// System.out.println(finishedDeadMob);

				MobsYaml y = MainCore.instance.rankCraft.mobsYaml;
				switch (finishedDeadMob) {
				case "Cow":
					RCUtils.addGladiatorXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Creeper":
					RCUtils.addGladiatorXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Enderman":
					RCUtils.addGladiatorXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Ghast":
					RCUtils.addGladiatorXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Horse":
					RCUtils.addGladiatorXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Pig":
					RCUtils.addGladiatorXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Rabbit":
					RCUtils.addGladiatorXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Skeleton":
					RCUtils.addGladiatorXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Slime":
					RCUtils.addGladiatorXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Spider":
					RCUtils.addGladiatorXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Villager":
					RCUtils.addGladiatorXp(killer, y.getXP(finishedDeadMob.toString()) * 2);
					break;
				case "Zombie":
					RCUtils.addGladiatorXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Chicken":
					RCUtils.addGladiatorXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Sheep":
					RCUtils.addGladiatorXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				default:
					break;

				}

			}

			if (RpgClassData.getCurrentClass(killer).equals(MainCore.instance.rankCraft.mysteriousClass.getId())) {

				// System.out.println("called");

				String deadMob = dead.getType().toString();
				deadMob = deadMob.toLowerCase();

				String finishedDeadMob = deadMob.substring(0, 1).toUpperCase() + deadMob.substring(1);
				// System.out.println(finishedDeadMob);

				MobsYaml y = MainCore.instance.rankCraft.mobsYaml;
				switch (finishedDeadMob) {
				case "Cow":
					RCUtils.addMysteriousXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Creeper":
					RCUtils.addMysteriousXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Enderman":
					RCUtils.addMysteriousXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Ghast":
					RCUtils.addMysteriousXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Horse":
					RCUtils.addMysteriousXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Pig":
					RCUtils.addMysteriousXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Rabbit":
					RCUtils.addMysteriousXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Skeleton":
					RCUtils.addMysteriousXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Slime":
					RCUtils.addMysteriousXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Spider":
					RCUtils.addMysteriousXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Villager":
					RCUtils.addMysteriousXp(killer, y.getXP(finishedDeadMob.toString()) * 2);
					break;
				case "Zombie":
					RCUtils.addMysteriousXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Chicken":
					RCUtils.addMysteriousXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Sheep":
					RCUtils.addMysteriousXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				default:
					break;

				}

			}
		}
	}

	public void archerEvent(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Arrow) {
			Arrow a = (Arrow) e.getDamager();

			if (a.getShooter() instanceof Player) {
				Player p = (Player) a.getShooter();

				if (RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.archerClass.getId())) {

					String deadMob = e.getEntity().getType().toString();
					deadMob = deadMob.toLowerCase();

					String finishedDeadMob = deadMob.substring(0, 1).toUpperCase() + deadMob.substring(1);

					MobsYaml y = MainCore.instance.rankCraft.mobsYaml;
					switch (finishedDeadMob) {
					case "Cow":
						RCUtils.addArcherXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Creeper":
						RCUtils.addArcherXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Enderman":
						RCUtils.addArcherXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Ghast":
						RCUtils.addArcherXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Horse":
						RCUtils.addArcherXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Pig":
						RCUtils.addArcherXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Rabbit":
						RCUtils.addArcherXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Skeleton":
						RCUtils.addArcherXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Slime":
						RCUtils.addArcherXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Spider":
						RCUtils.addArcherXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Villager":
						RCUtils.addArcherXp(p, y.getXP(finishedDeadMob.toString()) * 4);
						break;
					case "Zombie":
						RCUtils.addArcherXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Chicken":
						RCUtils.addArcherXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Sheep":
						RCUtils.addArcherXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					default:
						break;

					}

				}

				if (RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.mysteriousClass.getId())) {

					String deadMob = e.getEntity().getType().toString();
					deadMob = deadMob.toLowerCase();

					String finishedDeadMob = deadMob.substring(0, 1).toUpperCase() + deadMob.substring(1);

					MobsYaml y = MainCore.instance.rankCraft.mobsYaml;
					switch (finishedDeadMob) {
					case "Cow":
						RCUtils.addMysteriousXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Creeper":
						RCUtils.addMysteriousXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Enderman":
						RCUtils.addMysteriousXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Ghast":
						RCUtils.addMysteriousXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Horse":
						RCUtils.addMysteriousXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Pig":
						RCUtils.addMysteriousXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Rabbit":
						RCUtils.addMysteriousXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Skeleton":
						RCUtils.addMysteriousXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Slime":
						RCUtils.addMysteriousXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Spider":
						RCUtils.addMysteriousXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Villager":
						RCUtils.addMysteriousXp(p, y.getXP(finishedDeadMob.toString()) * 4);
						break;
					case "Zombie":
						RCUtils.addMysteriousXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Chicken":
						RCUtils.addMysteriousXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					case "Sheep":
						RCUtils.addMysteriousXp(p, y.getXP(finishedDeadMob.toString()) / 4);
						break;
					default:
						break;

					}

				}
			}

		}
	}

	public void ninjaEvent(EntityDeathEvent e) {
		LivingEntity dead = e.getEntity();
		if (dead.getKiller() instanceof Player) {
			Player killer = dead.getKiller();

			if (RpgClassData.getCurrentClass(killer).equals(MainCore.instance.rankCraft.ninjaClass.getId())) {

				// System.out.println("called");

				String deadMob = dead.getType().toString();
				deadMob = deadMob.toLowerCase();

				String finishedDeadMob = deadMob.substring(0, 1).toUpperCase() + deadMob.substring(1);
				// System.out.println(finishedDeadMob);

				MobsYaml y = MainCore.instance.rankCraft.mobsYaml;
				switch (finishedDeadMob) {
				case "Cow":
					RCUtils.addNinjaXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Creeper":
					RCUtils.addNinjaXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Enderman":
					RCUtils.addNinjaXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Ghast":
					RCUtils.addNinjaXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Horse":
					RCUtils.addNinjaXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Pig":
					RCUtils.addNinjaXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Rabbit":
					RCUtils.addNinjaXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Skeleton":
					RCUtils.addNinjaXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Slime":
					RCUtils.addNinjaXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Spider":
					RCUtils.addNinjaXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Villager":
					RCUtils.addNinjaXp(killer, y.getXP(finishedDeadMob.toString()) * 2);
					break;
				case "Zombie":
					RCUtils.addNinjaXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Chicken":
					RCUtils.addNinjaXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				case "Sheep":
					RCUtils.addNinjaXp(killer, y.getXP(finishedDeadMob.toString()) / 2);
					break;
				default:
					break;

				}

			}

		}
	}

	public void wizardEvent(PlayerUseSpellEvent e) {
		Player p = e.getPlayer();

		if (RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.wizardClass.getId())) {

			RCUtils.addWizardXp(p, e.getSpell().getPower() * (MainCore.instance.rankCraft.wizardClass
					.getPotionEffect(RpgClassData.getWizardLevel(p)).getAmplifier() + 1));

		}

		if (RpgClassData.getCurrentClass(p).equals(MainCore.instance.rankCraft.mysteriousClass.getId())) {

			RCUtils.addMysteriousXp(p, e.getSpell().getPower() * (MainCore.instance.rankCraft.mysteriousClass
					.getSpecialEffects(RpgClassData.getMysteriousLevel(p)).get(0).getAmplifier() + 1));

		}

	}
}
