package me.amc.rankcraft.spells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.RCUtils;

public class SpellUtils {

	public List<Spell> spells = new ArrayList<>();
	
	public SpellUtils() {
		registerSpells();
		initEvents();
	}
	
	private void registerSpells() {
		for(int i = 0; i < 3; i++) {
			LightningSpell lSpell = new LightningSpell(i+1); lSpell.setId(getNextFreeId());
			spells.add(lSpell);
			BombSpell bSpell = new BombSpell(i+1); bSpell.setId(getNextFreeId());
			spells.add(bSpell);
			FarmSpell fSpell = new FarmSpell(i+1); fSpell.setId(getNextFreeId());
			spells.add(fSpell);
			HungerSpell hSpell = new HungerSpell(i+1); hSpell.setId(getNextFreeId());
			spells.add(hSpell);
			PoisonSpell pSpell = new PoisonSpell(i+1); pSpell.setId(getNextFreeId());
			spells.add(pSpell);
			ShadowSpell shSpell = new ShadowSpell(i+1); shSpell.setId(getNextFreeId());
			spells.add(shSpell);
		}
		for(int j = 0; j < 5; j++) {
			FeedSpell fSpell = new FeedSpell(j+1); fSpell.setId(getNextFreeId());
			spells.add(fSpell);
			SpiderSpell sSpell = new SpiderSpell(j+1); sSpell.setId(getNextFreeId());
			spells.add(sSpell);
			HealSpell hSpell = new HealSpell(j+1); hSpell.setId(getNextFreeId());
			spells.add(hSpell);
			EarthTameSpell ets = new EarthTameSpell(j+1); ets.setId(getNextFreeId());
			spells.add(ets);
		}
		AntiHungerSpell ahSpell = new AntiHungerSpell(1); ahSpell.setId(getNextFreeId());
		spells.add(ahSpell);
		AntiPoisonSpell apSpell = new AntiPoisonSpell(1); apSpell.setId(getNextFreeId());
		spells.add(apSpell);
		DeathTouchSpell dtSpell = new DeathTouchSpell(1); dtSpell.setId(getNextFreeId());
		spells.add(dtSpell);
		HeartsSpell hSpell = new HeartsSpell(1); hSpell.setId(getNextFreeId());
		spells.add(hSpell);
		PickPocketSpell ppSpell = new PickPocketSpell(1); ppSpell.setId(getNextFreeId());
		spells.add(ppSpell);
		
	}
	
	private void initEvents() {
		for(Spell s : spells) {
			Bukkit.getServer().getPluginManager().registerEvents(s, MainCore.instance);
		}
		Bukkit.getServer().getPluginManager().registerEvents(new SpiderSpellWebEvent(), MainCore.instance);
	}
	
	public Spell getSpellByName(String name) {
		for(Spell s : spells) {
			//System.out.println("-> "+RCUtils.stripColor(s.getName())+" vs. "+name);
			if(RCUtils.stripColor(s.getName()).equalsIgnoreCase(name)) return s;
		}
		return null;
	}
	
	public Spell getSpellByNameId(String nameId) {
		for(Spell s : spells) 
			if(s.getNameId().equalsIgnoreCase(nameId)) 
				return s;
		return null;
	}
	
	public Spell getSpellByNameWithoutLevel(String name) {
		for(Spell s : spells) {
			String[] parts = RCUtils.stripColor(s.getName()).split(" ");
			//System.out.println("-> "+RCUtils.stripColor(s.getName())+" vs. "+name);
			if(parts[0].equalsIgnoreCase(name)) return s;
		}
		return null;
	}
	
	public List<String> getSpellNamesWithoutLevels() {
		List<String> names = new ArrayList<>();
		for(Spell s : spells) {
			String[] parts = RCUtils.stripColor(s.getName()).split(" ");
			names.add(parts[0]);
		}
		return names;
	}
	
	public List<String> getSpellIdsWithoutLevels() {
		List<String> ids = new ArrayList<>();
		for(Spell s : spells) {
			String[] parts = RCUtils.stripColor(s.getNameId()).split("_");
			ids.add(parts[0]);
		}
		return ids;
	}
	
	private String getNextFreeId() {
		String s_size = ""+spells.size();
		char s_a[] = s_size.toCharArray();
		int s_l = s_a.length;
		StringBuilder b = new StringBuilder("");
		int charsToAdd = 3 - s_l;
		for(int i = 0; i < charsToAdd; i++) b.append("0");
		b.append(s_size);
		return b.toString();
	}
	
}
