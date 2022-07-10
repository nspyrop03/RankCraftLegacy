package me.amc.rankcraft.yaml;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import me.amc.rankcraft.utils.RCUtils;

public class PackYaml {

	private YamlParser y;
	private FileConfiguration c;
	
	public PackYaml(String fileNameWithoutExt) {
		y = new YamlParser(RCUtils.PACKS_DIRECTORY, fileNameWithoutExt+".pack", "packs");
		c = y.getConfig();
		
	}
	
	public List<Material> getMaterialList(String type) {
		List<Material> mats = new ArrayList<>();
		for(String s : c.getStringList(type+"_pack")) {
			mats.add(Material.valueOf(s));
		}
		return mats;
	}
	
	public int getMinAmount(String type) {
		return c.getInt(type+"_pack_min_amount");
	}
	
	public int getMaxAmount(String type) {
		return c.getInt(type+"_pack_max_amount");
	}
}
