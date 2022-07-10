package me.amc.rankcraft.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.quests2.YamlQuest;

public class SetRewardCommand extends SubCommand {

	public SetRewardCommand() {
		super("setreward");
	}

//	@SuppressWarnings("deprecation")
	@Override
	public void execute(Player p, String[] args) {
		if (args.length == 5) {
			if (p.hasPermission(MainCore.instance.permList.setReward_permission)) {
				// rc setreward <quest> <xp> <gold> <true/false>
				String quest = args[1];
				float xp = Float.parseFloat(args[2]);
				float gold = Float.parseFloat(args[3]);
				boolean hasItem;
				if (args[4].equalsIgnoreCase("true")) {
					hasItem = true;
				} else {
					hasItem = false;
				}

				for (YamlQuest q : MainCore.instance.rankCraft.yamlQuests) {
					if (quest.equalsIgnoreCase(q.getId())) {
						MainCore.instance.rankCraft.quests2.remove(q);
						MainCore.instance.rankCraft.yamlQuests.remove(q);
						q.getConfig().set("Reward", null);

						q.getConfig().set("Reward.xp", xp);
						q.getConfig().set("Reward.gold", gold);

						if (hasItem) {
							q.getConfig().set("Reward.item", p.getInventory().getItemInMainHand());
						}

						try {
							q.getConfig().save(q.getFile());
						} catch (IOException e) {
							e.printStackTrace();
						}

						p.sendMessage("Set the reward for quest with id: " + q.getId());
						MainCore.instance.rankCraft.quests2.add(q);
						MainCore.instance.rankCraft.yamlQuests.add(q);
						p.sendMessage("Quest " + q.getId() + " has successfully reloaded!");
					}
				}
			} else {
				MainCore.instance.permList.sendNotPermissionMessage(p);
			}
		}
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		
		if(args.length == 2) {
			
			List<String> ids = new ArrayList<>();
			for(YamlQuest q : MainCore.instance.rankCraft.yamlQuests) ids.add(q.getId());
			return ids;
			
		} else if(args.length == 3) {
			return Arrays.asList("<xp>");
		} else if(args.length == 4) {
			return Arrays.asList("<gold>");
		} else if(args.length == 5) {
			return Arrays.asList(new String[] {"true", "false"});
		}
		
		return null;
	}
}
