package me.amc.rankcraft.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.PermissionList;
import me.amc.rankcraft.mobs.YamlMob;
import me.amc.rankcraft.utils.RCUtils;

public class SpawnCommand extends SubCommand{

	public SpawnCommand() {
		super("spawn");
	}

	@Override
	public void execute(Player p, String[] args) {
		if(MainCore.instance.config.enableCustomMobs) {
			PermissionList pls = MainCore.instance.permList;
			if(args.length == 3 || args.length == 2) {
				if(p.hasPermission(pls.mobSpawn_permission)) {
					YamlMob y = MainCore.instance.rankCraft.getYamlMobFromFileName(args[1]+".yml");
					
					try {
						int times = 1;
						if(args.length == 3)times = Integer.parseInt(args[2]);
						
						for(int i = 0; i < times; i++) {
							if(y.getType().equalsIgnoreCase("ZOMBIE")) {
								Zombie z = (Zombie) p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
								y.makeMob(z);
							} else if(y.getType().equalsIgnoreCase("SKELETON")) {
								Skeleton s = (Skeleton) p.getWorld().spawnEntity(p.getLocation(), EntityType.SKELETON);
								y.makeMob(s);
							}
						}
						
					} catch(NumberFormatException e) {
						p.sendMessage("Use: /rc spawn <mob> <times>");
					}
				} else {
					pls.sendNotPermissionMessage(p);
				}
			}
		} else {
			p.sendMessage(MainCore.instance.language.getDisabledCustomMobs());
		}
	}

	@Override
	public List<String> getTabCompletion(Player p, String[] args) {
		if(args.length == 2) {
			
			List<String> ids = new ArrayList<>();
			for(YamlMob mob : MainCore.instance.rankCraft.mobs) {
				ids.add(RCUtils.getStringWithoutExtension(mob.getFileName()));
			}
			return ids;
			
		} else if(args.length == 3) {
			return Arrays.asList("<times>");
		}
		return null;
	}

}
