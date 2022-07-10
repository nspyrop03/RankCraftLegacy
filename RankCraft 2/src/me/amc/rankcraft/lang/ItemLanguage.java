package me.amc.rankcraft.lang;

import java.util.ArrayList;
import java.util.List;

import me.amc.rankcraft.utils.RCUtils;

public class ItemLanguage {
	
	private LangYaml y;
	
	public ItemLanguage(String fileName) {
		y = new LangYaml(fileName, "item_lang");
		//System.out.println("item lang file name = "+fileName);
	}
	
	public int getDescriptionLimit() {
		return y.getConfig().getInt("DescriptionLimit");
	}
	
	public List<String> getSpellLore(int level, int maxLevel, float manaCost, String usage, String description) {
		List<String> r = y.getConfig().getStringList("SpellLore");
		List<String> f = new ArrayList<>();
		List<String> moreDescription= new ArrayList<>();
		for(String s : r) {
			s = s.replace("{Level}", ""+level);
			s = s.replace("{MaxLevel}", ""+maxLevel);
			s = s.replace("{ManaCost}", ""+RCUtils.round(manaCost,1));
			s = s.replace("{Usage}", usage);
			if(s.contains("{Description}")) {
				s = s.replace("{Description}", description);
				s = s.replace('§', '&');
				for(int i = getDescriptionLimit()-1; i < s.length(); i++) {
					if(s.charAt(i) == ' ') {
						moreDescription.add(s.substring(0, i).replace('&', '§'));
						moreDescription.add((getPreviousChatCode(s, i)+s.substring(i+1)).replace('&', '§'));
						break;
					}
				}
			}
			s = s.replace('&', '§');
			if(!moreDescription.isEmpty()) {
				for(String st : moreDescription) {
					f.add(st);
				}
				moreDescription.clear();
			} else {
				f.add(s);
			}
		}
		return f;
	}
	
	private String getPreviousChatCode(String rawText, int currentIndex) {
		String cc = "";
		for(int i = currentIndex; i >= 0; i--) {
			if(rawText.charAt(i) == '&') {
				if(i+1 < rawText.length()) {
					cc = rawText.charAt(i)+""+rawText.charAt(i+1);
					break;
				}
			}
		}
		//System.out.println(cc);
		return cc;
	}
	
	public String getSpellName(String name) {
		String path = "SpellInfo."+name+".Name";
		//System.out.println(y.getConfig().contains("SpellInfo"));
		//System.out.println();
		/*
		System.out.println("path = "+path);
		System.out.println("path exists = "+y.getConfig().contains(path));
		System.out.println("half path exists = "+y.getConfig().contains("SpellInfo."+name));
		System.out.println("res = "+y.getConfig().contains("SpellInfo.AntiHungerSpell.Name"));
		//System.out.println(y.getMessage(path) == null);
		System.out.println(y == null);
		
		System.out.println("test-path: "+y.getConfig().getString("TestPath"));
				
		System.out.println("size: "+y.getConfig().getKeys(true).size());
		for(String s : y.getConfig().getKeys(true)) {
			System.out.println(s);
		}
			*/	
		/*
		try {
			return y.getMessage(path);
		} catch(NullPointerException ex) {
			ex.printStackTrace();
		}
		*/
		//return y.getConfig().getString(path);
		return y.getMessage(path);
		//return "path";
	}
	
	/*
	public String getLightningSpellName() {
		return y.getMessage("SpellInfo.LightningSpell.Name");
	}
	*/
	
	public String getAntiHungerSpellDescription() {
		return y.getMessage("SpellInfo.AntiHungerSpell.Description");
	}
	
	public String getAntiPoisonSpellDescription() {
		return y.getMessage("SpellInfo.AntiPoisonSpell.Description");
	}
	
	public String getBombSpellDescription(int level) {
		String s = y.getMessage("SpellInfo.BombSpell.Description");
		s = s.replace("{Level}", ""+level);
		return s;
	}
	
	public String getDeathTouchSellDescription() {
		return y.getMessage("SpellInfo.DeathTouchSpell.Description");
	}
	
	public String getEarhtTameSpellDescription(int blocks) {
		String s = y.getMessage("SpellInfo.EarthTameSpell.Description");
		s = s.replace("{Blocks}", ""+blocks);
		return s;
	}
	
	public String getFarmSpellDescription(int animals) {
		String s = y.getMessage("SpellInfo.FarmSpell.Description");
		s = s.replace("{Animals}", ""+animals);
		return s;
	}
	
	public String getFeedSpellDescription(int drumsticks) {
		String s = y.getMessage("SpellInfo.FeedSpell.Description");
		s = s.replace("{Drumsticks}", ""+drumsticks);
		return s;
	}
	
	public String getHealSpellDescription(float HP) {
		String s = y.getMessage("SpellInfo.HealSpell.Description");
		s = s.replace("{HP}", ""+RCUtils.round(HP, 1));
		return s;
	}
	
	public String getHeartsSpellDescription() {
		return y.getMessage("SpellInfo.HeartsSpell.Description");
	}
	
	public String getHungerSpellDescription() {
		return y.getMessage("SpellInfo.HungerSpell.Description");
	}
	
	public String getLightningSpellDescription(int lightnings) {
		String s = y.getMessage("SpellInfo.LightningSpell.Description");
		s = s.replace("{Lightnings}", ""+lightnings);
		return s;
	}
	
	public String getPickPocketSpellDescription(int items) {
		String s = y.getMessage("SpellInfo.PickPocketSpell.Description");
		s = s.replace("{Items}", ""+items);
		return s;
	}
	
	public String getPoisonSpellDescription() {
		return y.getMessage("SpellInfo.PoisonSpell.Description");
	}
	
	public String getShadowSpellDescription(int duration) {
		String s = y.getMessage("SpellInfo.ShadowSpell.Description");
		s = s.replace("{Duration}", ""+duration);
		return s;
	}
	
	public String getSpiderSpellDescription(int blocks) {
		String s = y.getMessage("SpellInfo.SpiderSpell.Description");
		s = s.replace("{Blocks}", ""+blocks);
		return s;
	}
	
	public String getSpellCaseDescription(int slots) {
		String s = y.getMessage("SpellCaseDescription");
		s = s.replace("{Spells}", ""+slots);
		return s;
	}
	
}
