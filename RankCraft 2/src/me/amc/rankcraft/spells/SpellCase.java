package me.amc.rankcraft.spells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.utils.CustomItem;

public class SpellCase extends CustomItem {
	
	public enum CaseType {
		Small(5, "§eSmall Spell Case"),
		Medium(7, "§6Medium Spell Case"),
		Large(9, "§9Large Spell Case");
		
		private int lines;
		private String name;
		
		CaseType(int lines, String name) {
			this.lines = lines;
			this.name = name;
		}
		
		public int getLines() {
			return this.lines;
		}
		
		public String getName() {
			return this.name;
		}
		
	}
	
	public static final NamespacedKey DATA_KEY = new NamespacedKey(MainCore.instance, "spellCaseData");
	
	private CaseType caseType;
	private List<String> data = new ArrayList<>();
	private Inventory inv;
	
	//private ItemStack pane = new ItemStack(Material.GLASS_PANE, 1, (short)12);
	private CustomItem pane;
	
	public SpellCase(CaseType type) {
		super(Material.BOOK);
	
		this.caseType = type;
		this.data = new ArrayList<>();
		
		this.setName(caseType.getName());
		
		StringBuilder d_sb = new StringBuilder("");
		d_sb.append("AIR");
		for(int i = 0; i < caseType.getLines()-1; i++) {
			d_sb.append(",AIR");
		}
		
		//this.addLores(ChatColor.GRAY+"Store up to "+type.getLines()+" different spells!");
		this.addLores(MainCore.instance.itemLang.getSpellCaseDescription(type.getLines()));
		//this.addLores(RCUtils.hiddenString("Data:"+d_sb.toString()));
		this.getItemMeta().getPersistentDataContainer().set(DATA_KEY, PersistentDataType.STRING, d_sb.toString());
		
		this.build();
		
		inv = Bukkit.createInventory(null, 9, caseType.getName());
		addPanes();
		
		refreshInv();		
	}
	
	public SpellCase(ItemStack is) {
		super(Material.BOOK);
		if(is.getItemMeta().getDisplayName().equals(CaseType.Small.getName())) {
			this.caseType = CaseType.Small;
		} else if(is.getItemMeta().getDisplayName().equals(CaseType.Medium.getName())) {
			this.caseType = CaseType.Medium;
		} else if(is.getItemMeta().getDisplayName().equals(CaseType.Large.getName())) {
			this.caseType = CaseType.Large;
		} else {
			this.caseType = null;
		}
		
		if(is.hasItemMeta()) {
			ItemMeta meta = is.getItemMeta();
			if(meta.hasLore()) {
				/*
				for(String s : meta.getLore()) {
					s = RCUtils.fromHiddenString(s);
					if(s.startsWith("Data:")) {
						String parts[] = s.split(":");
						String ids[] =  parts[1].split(",");
						for(int i = 0; i < ids.length; i++) {
							data.add(ids[i]);
							
						}
					}
				}
				*/
				if(meta.getPersistentDataContainer().has(DATA_KEY, PersistentDataType.STRING)) {
					String d = meta.getPersistentDataContainer().get(DATA_KEY, PersistentDataType.STRING);
					//System.out.println(d);
					String ids[] =  d.split(",");
					for(int i = 0; i < ids.length; i++) {
						data.add(ids[i]);
						
					}
				}
			}			
		}
		
		inv = Bukkit.createInventory(null, 9, caseType.getName());
		addPanes();
		
		refreshInv();
	}

	public CaseType getCaseType() {
		return this.caseType;
	}

	public void setCaseType(CaseType type) {
		this.caseType = type;
	}

	public List<String> getData() {
		return data;
	}

	/*public void setData(List<String> data) {
		this.data = data;
	}*/

	public void addData(String d) {
		this.data.add(d);
		/*
		for(String lore : this.getLores()) {
			lore = RCUtils.fromHiddenString(lore);
			if(lore.startsWith("Data:")) {
				this.getLores().remove(RCUtils.hiddenString(lore));
				/ *
				String parts[] = lore.split(":");
				String parts2[] = parts[1].split(",");
				for(int i = 0; i < parts2.length; i++) {
					if(parts2[i].equals("AIR")) {
						parts2[i] = d;
					}
				}
				* /
				int freeSlots = caseType.getLines() - data.size();
				String newDataLore = "Data:"+data.get(0);
				StringBuilder sb = new StringBuilder(newDataLore);
				for(int i = 1; i < data.size(); i++) {
					sb.append(","+data.get(i));
				}
				for(int i = 0; i < freeSlots; i++) {
					sb.append(",AIR");
				}
				this.addLores(RCUtils.hiddenString(sb.toString()));
				//System.out.println(sb);
			}
		}
		*/
		
		if(this.getItemMeta().getPersistentDataContainer().has(DATA_KEY, PersistentDataType.STRING)) {
			String dat = this.getItemMeta().getPersistentDataContainer().get(DATA_KEY, PersistentDataType.STRING);
			String parts2[] = dat.split(",");
			for(int i = 0; i < parts2.length; i++) {
				if(parts2[i].equals("AIR")) {
					parts2[i] = d;
				}
			}
			int freeSlots = caseType.getLines() - data.size();
			//String newDataLore = "Data:"+data.get(0);
			String newDataLore = data.get(0);
			StringBuilder sb = new StringBuilder(newDataLore);
			for(int i = 1; i < data.size(); i++) {
				sb.append(","+data.get(i));
			}
			for(int i = 0; i < freeSlots; i++) {
				sb.append(",AIR");
			}
			this.getItemMeta().getPersistentDataContainer().set(DATA_KEY, PersistentDataType.STRING, sb.toString());
		}
		
		this.build();
		
		
		refreshInv();
	}
	
	public void setDataFromList(List<String> d) {
		this.data.clear();
		for(int i = 0; i < d.size(); i++) {
			this.addData(d.get(i));
		}
	}

	public Inventory getInv() {
		return inv;
	}
	
	private void refreshInv() {
		inv.clear();
		addPanes();
		List<Spell> spells = MainCore.instance.rankCraft.spellUtils.spells;
		for(Spell s : spells) {
			for(String st : data) {
				//st = RCUtils.fromHiddenString(st);
				//System.out.println(st);
				if(st.equals("AIR")) continue;
				StringBuilder sb = new StringBuilder("");
				char cl[] = st.toCharArray();
				sb.append(cl[0]).append(cl[1]).append(cl[2]);
				StringBuilder times = new StringBuilder("");
				times.append(cl[3]).append(cl[4]);
				if(s.getId().equals(sb.toString())) {
					for(int i = 0; i < Integer.parseInt(times.toString()); i++) {
						inv.addItem(s.getItem());
					}
				}
			}
		}
	}
	
	private void addPanes() {
		pane = new CustomItem(Material.BROWN_STAINED_GLASS_PANE, " ").build();
		if(caseType == CaseType.Small) {
			inv.setItem(0, pane.getItem());
			inv.setItem(1, pane.getItem());
			inv.setItem(7, pane.getItem());
			inv.setItem(8, pane.getItem());
		} else if(caseType == CaseType.Medium) {
			inv.setItem(0, pane.getItem());
			inv.setItem(8, pane.getItem());
		}
	}
}
