package me.amc.rankcraft.lang;

import java.util.ArrayList;
import java.util.List;

public class GuiLanguage {

	private LangYaml y;
	
	public GuiLanguage(String fileName) {
		y = new LangYaml(fileName, "gui_lang");
	}
	
	public String getBPShopTitle() {
		return y.getMessage("BpShopTitle");
	}
	
	public String getUpgradeBpItem() {
		return y.getMessage("UpgradeBpItem");
	}
	
	public String getMaxBpItem() {
		return y.getMessage("MaxBpItem");
	}
	
	public String getBronzePackTitle() {
		return y.getMessage("BronzePacksShopTitle");
	}
	
	public String getSilverPackTitle() {
		return y.getMessage("SilverPacksShopTitle");
	}
	
	public String getGoldPackTitle() {
		return y.getMessage("GoldPacksShopTitle");
	}
	
	public String getPackShopTitle() {
		return y.getMessage("PacksShopTitle");
	}
	
	public String getBuyBronze() {
		return y.getMessage("ItemBuyBronze");
	}
	
	public String getBuySilver() {
		return y.getMessage("ItemBuySilver");
	}
	
	public String getBuyGold() {
		return y.getMessage("ItemBuyGold");
	}
	
	public String getAchievementsTitle() {
		return y.getMessage("AchievementsGuiTitle");
	}
	
	public String getMobStatsGuiTitle() {
		return y.getMessage("MobStatsGuiTitle");
	}
	
	public String getItemMobHead(String mob, int kills) {
		String s = y.getMessage("ItemMobHead");
		s = s.replace("{Mob}", mob);
		s = s.replace("{Kills}", ""+kills);
		return s;
	}
	
	public String getGeneralShopTitle() {
		return y.getMessage("GeneralShopTitle");
	}
	
	public String getExit() {
		return y.getMessage("ItemExit");
	}
	
	public String getYourGold(float gold) {
		String s = y.getMessage("ItemGold");
		s = s.replace("{Gold}", ""+gold);
		return s;
	}
	
	public String getRefresh(int seconds) {
		String s = y.getMessage("ItemRefresh");
		s = s.replace("{Seconds}", ""+seconds);
		return s;
	}
	
	public String getSold() {
		return y.getMessage("ItemSold");
	}
	
	public String getSkillsTitle() {
		return y.getMessage("SkillsGuiTitle");
	}
	
	public String getSkillsName(String type, float gold) {
		String s = y.getMessage("ItemUpgrade"+type+".Name");
		s = s.replace("{Gold}", ""+gold);
		return s;
	}
	
	public String getSkillsLore(String type, int lvl) {
		String s = y.getMessage("ItemUpgrade"+type+".Lore");
		s = s.replace("{Lvl}", ""+lvl);
		return s;
	}
	
	public String getStatsTitle() {
		return y.getMessage("StatsGuiTitle");
	}
	
	public String getXp(float xp) {
		String s = y.getMessage("ItemXp");
		s = s.replace("{Xp}", ""+xp);
		return s;
	}
	
	public String getLevel(int lvl) {
		String s = y.getMessage("ItemLevel");
		s = s.replace("{Lvl}", ""+lvl);
		return s;
	}
	
	public String getRank(String rank) {
		String s = y.getMessage("ItemRank");
		s = s.replace("{Rank}", rank);
		return s;
	}
	
	public String getBP(int bp) {
		String s = y.getMessage("ItemBlocksPlaced");
		s = s.replace("{bp}", ""+bp);
		return s;
	}
	
	public String getBB(int bb)  {
		String s = y.getMessage("ItemBlocksBroken");
		s = s.replace("{bb}", ""+bb);
		return s;
	}
	
	public String getQuestsTitle() {
		return y.getMessage("QuestsMenuTitle");
	}
	
	public String getPlaceTitleQ() {
		return y.getMessage("PlaceBlocksQuestsTitle");
	}
	
	public String getBreakTitleQ() {
		return y.getMessage("BreakBlocksQuestsTitle");
	}
	
	public String getKillPTitleQ() {
		return y.getMessage("KillPlayersQuestsTitle");
	}
	
	public String getKillMTitleQ() {
		return y.getMessage("KillMobsQuestsTitle");
	}
	
	public String getBackToMenu() {
		return y.getMessage("ItemBackToMenu");
	}
	
	public String getAchievementCompleted() {
		return y.getMessage("AchievementCompleted");
	}
	
	public String getAchievementKillPlayers(int players) {
		String s = y.getMessage("AchievementKillPlayers");
		s = s.replace("{Players}", ""+players);
		return s;
	}
	
	public String getAchievementKillMobs(int mobs, String type) {
		String s = y.getMessage("AchievementKillMobs");
		s = s.replace("{Mobs}", ""+mobs);
		s = s.replace("{Type}", type);
		return s;
	}
	
	public String getAchievementPlaceBlocks(int blocks, String type) {
		String s = y.getMessage("AchievementPlaceBlocks");
		s = s.replace("{Blocks}", ""+blocks);
		s = s.replace("{Type}", type);
		return s;
	}
	
	public String getAchievementBreakBlocks(int blocks, String type) {
		String s = y.getMessage("AchievementBreakBlocks");
		s = s.replace("{Blocks}", ""+blocks);
		s = s.replace("{Type}", type);
		return s;
	}
	
	public List<String> getAchievementRewardLore(double xp, double gold) {
		List<String> r = y.getConfig().getStringList("AchievementRewardsLore");
		List<String> f = new ArrayList<>();
		for(String s : r) {
			s = s.replace("{Xp}", ""+xp);
			s = s.replace("{Gold}", ""+gold);
			s = s.replace('&', '§');
			f.add(s);
		}
		return f;
	}
	
	public String getAchievementExcalibur() {
		return y.getMessage("AchievementExcalibur");
	}
	
	public String getAchievementTreasure(int chests) {
		String s = y.getMessage("AchievementTreasure");
		s = s.replace("{Treasure}", ""+chests);
		return s;
	}
	
	public String getAchievementUseSpells(int spells) {
		String s = y.getMessage("AchievementUseSpells");
		s = s.replace("{Spells}", ""+spells);
		return s;
	}
	
	public String getAchievementMysterious() {
		return y.getMessage("AchievementMysterious");
	}
	
	public String getAchievementBlockBreaker(int blocks) {
		String s = y.getMessage("AchievementBlockBreaker");
		s = s.replace("{Blocks}", ""+blocks);
		return s;
	}
	
	public String getAchievementSmith(int items) {
		return y.getMessage("AchievementSmith").replace("{Items}", ""+items);
	}
	
}
