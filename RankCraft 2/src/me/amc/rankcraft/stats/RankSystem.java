package me.amc.rankcraft.stats;

import java.io.File;
import java.util.HashMap;
import java.util.TreeMap;

import me.amc.rankcraft.yaml.YamlParser;

public class RankSystem {

	private HashMap<Integer, String> ranks = new HashMap<>();
	private String fileName = "ranks.yml";
	private YamlParser y;
	
	private TreeMap<Integer, String> sortedRanks = new TreeMap<>();
	
	public RankSystem() {
		reload();
	}
	
	private void reload() {
		y = new YamlParser(new File("plugins/RankCraft"), fileName);
		
		for(String s : y.getConfig().getStringList("Ranks")) {
			String[] parts = s.split(":");
			ranks.put(Integer.parseInt(parts[1]), parts[0].replace('&', '§'));
		}
		
		sortedRanks = getSortedByKey();
	}
	
	public int getRanksSize() {
		return ranks.size();
	}
	
	public String getRankBasedOnLevel(int level) {
		String rank = "none";
		boolean choseRank = false;
		
		if(level > 0) {
			for(int i = 0; i < sortedRanks.size(); i++) {
				if(level < (int)sortedRanks.keySet().toArray()[i]) {
					if(i!=0) {
						rank = sortedRanks.get((int)sortedRanks.keySet().toArray()[i-1]);
						choseRank = true;
						break;
					}
				}
			}
		} else {
			if(((int)sortedRanks.keySet().toArray()[0]) == 0) 
				rank = sortedRanks.get(0);
			choseRank = true;
		}
		
		if(!choseRank) { // Player has the last Rank
			rank = sortedRanks.get((int)sortedRanks.keySet().toArray()[sortedRanks.size()-1]);
		}
		
		return rank;
	}
	
	private TreeMap<Integer, String> getSortedByKey() 
    { 
        // TreeMap to store values of HashMap 
        TreeMap<Integer, String> sorted = new TreeMap<>(); 
        // Copy all data from hashMap into TreeMap 
        sorted.putAll(ranks); 
        return sorted;
    } 
	
}
