package me.amc.rankcraft.quests2;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.quests2.Quest.QuestType;
import me.amc.rankcraft.utils.RCUtils;

public class QuestEvents implements Listener {

	@EventHandler
	public void onBreakBlock(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Material m = e.getBlock().getType();

		for (Quest q : MainCore.instance.rankCraft.quests2) {
			if (q.getType() == QuestType.BreakBlocks) {
				if (q.hmTaken().get(RCUtils.textedUUID(p)) == true) {
					if (m == q.getBreakBlocksType()) {
						q.hmBlocksBroken().put(RCUtils.textedUUID(p),
								q.hmBlocksBroken().get(RCUtils.textedUUID(p)) + 1);
						QuestSaver.save(MainCore.instance, q);
						//p.sendMessage(q.getId() + " +1 " + q.getBreakBlocksType() + " block broken");
					}
				}
			}
		}

	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Material m = e.getBlockPlaced().getType();

		for (Quest q : MainCore.instance.rankCraft.quests2) {
			if (q.getType() == QuestType.PlaceBlocks) {
				if (q.hmTaken().get(RCUtils.textedUUID(p)) == true) {
					if (m == q.getPlaceBlocksType()) {
						q.hmBlocksPlaced().put(RCUtils.textedUUID(p),
								q.hmBlocksPlaced().get(RCUtils.textedUUID(p)) + 1);
						QuestSaver.save(MainCore.instance, q);
						//p.sendMessage(q.getId() + " +1 " + q.getPlaceBlocksType() + " block placed");
					}
				}
			}
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		LivingEntity dead = e.getEntity();
		LivingEntity killer = dead.getKiller();

		if (killer instanceof Player) {

			Player p = (Player) killer;

			for (Quest q : MainCore.instance.rankCraft.quests2) {
				if (q.getType() == QuestType.KillMobs) {
					if (q.hmTaken().get(RCUtils.textedUUID(p)) == true) {
						if (dead.getType() == q.getKillEntityType()) {
							q.hmMobsKilled().put(RCUtils.textedUUID(p),
									q.hmMobsKilled().get(RCUtils.textedUUID(p)) + 1);
							QuestSaver.save(MainCore.instance, q);
							//p.sendMessage(q.getId() + " +1 " + q.getKillEntityType() + " mob killed");
						}
					}
				}
			}

			if (dead instanceof Player) {

				//Player d = (Player) dead;

				for (Quest q : MainCore.instance.rankCraft.quests2) {
					if (q.getType() == QuestType.KillPlayers) {
						if (q.hmTaken().get(RCUtils.textedUUID(p)) == true) {

							q.hmPlayersKilled().put(RCUtils.textedUUID(p),
									q.hmPlayersKilled().get(RCUtils.textedUUID(p)) + 1);
							QuestSaver.save(MainCore.instance, q);

							//p.sendMessage(q.getId() + " +1 " + d.getName() + "player killed");
						}
					}
				}
			}
		}
	}

	public static void checkFinishedQuest(Quest q, Player p) {
		switch (q.getType()) {
		case BreakBlocks:
			if (q.hmBlocksBroken().get(RCUtils.textedUUID(p)) >= q.getBreakBlockAmount()) {
			//	p.sendMessage("Congratulations " + p.getName() + " you have finished " + q.getName() + " quest !!!");
				p.sendMessage(MainCore.instance.language.getFinishQuest(p, q.getName()));
				q.hmCompletedTimes().put(RCUtils.textedUUID(p), q.hmCompletedTimes().get(RCUtils.textedUUID(p))+1);
				if(q.getReward() != null) {
					RCUtils.giveReward(q.getReward(), p);
				}
				QuestSaver.resetQuest(q, p);
			}
			break;
			
		case KillMobs:
			if (q.hmMobsKilled().get(RCUtils.textedUUID(p)) >= q.getKillEntityAmount()) {
			//	p.sendMessage("Congratulations " + p.getName() + " you have finished " + q.getName() + " quest !!!");
				p.sendMessage(MainCore.instance.language.getFinishQuest(p, q.getName()));
				q.hmCompletedTimes().put(RCUtils.textedUUID(p), q.hmCompletedTimes().get(RCUtils.textedUUID(p))+1);
				if(q.getReward() != null) {
					RCUtils.giveReward(q.getReward(), p);
				}
				QuestSaver.resetQuest(q, p);
			}
			break;
			
		case KillPlayers:
			if (q.hmPlayersKilled().get(RCUtils.textedUUID(p)) >= q.getKillPlayerAmount()) {
			//	p.sendMessage("Congratulations " + p.getName() + " you have finished " + q.getName() + " quest !!!");
				p.sendMessage(MainCore.instance.language.getFinishQuest(p, q.getName()));
				q.hmCompletedTimes().put(RCUtils.textedUUID(p), q.hmCompletedTimes().get(RCUtils.textedUUID(p))+1);
				if(q.getReward() != null) {
					RCUtils.giveReward(q.getReward(), p);
				}
				QuestSaver.resetQuest(q, p);
			}
			break;
			
		case PlaceBlocks:
			// p.sendMessage(q.hmBlocksPlaced().get(RCUtils.textedUUID(p))+"/"+q.getPlaceBlockAmount());
			if (q.hmBlocksPlaced().get(RCUtils.textedUUID(p)) >= q.getPlaceBlockAmount()) {
			//	p.sendMessage("Congratulations " + p.getName() + " you have finished " + q.getName() + " quest !!!");
				p.sendMessage(MainCore.instance.language.getFinishQuest(p, q.getName()));
				q.hmCompletedTimes().put(RCUtils.textedUUID(p), q.hmCompletedTimes().get(RCUtils.textedUUID(p))+1);
				if(q.getReward() != null) {
					RCUtils.giveReward(q.getReward(), p);
				}
				QuestSaver.resetQuest(q, p);
			}
			break;
			
		default:
			break;
			
		}
	}

}
