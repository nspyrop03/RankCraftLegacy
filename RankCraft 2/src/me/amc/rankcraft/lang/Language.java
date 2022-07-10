package me.amc.rankcraft.lang;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.amc.rankcraft.rpgitem.Rarity;
import me.amc.rankcraft.utils.RCUtils;

public class Language {

	private LangYaml y;
	
	public Language(String fileName) {
		y = new LangYaml(fileName, "lang");
		//System.out.println("Is y null: "+(y==null));
	}
	
	public String getPrefix() {
		return y.getMessage("Prefix");
	}
	
	public String getHungerMessage() { //ok
		return y.getMessageWithPrefix("HungerMessage");
	}
	
	public String getQuestRemainingSec(String qName, int sec) {
		String s = y.getMessageWithPrefix("QuestRemainingSeconds");
		s = s.replace("{Name}", qName);
		s = s.replace("{SecRemaining}", ""+sec);
		return s;
	}
	
	public String getQuestTimeEnd(String qName) {
		String s = y.getMessageWithPrefix("QuestTimeEnd");
		s = s.replace("{Name}", qName);
		return s;
	}
	
	public String getCannotUseWeapon(int minLevel) {
		String s = y.getMessageWithPrefix("CannotUseWeapon");
		s = s.replace("{MinLevel}", ""+minLevel);
		return s;
	}
	
	public String getNoPermissionMessage() {
		return y.getMessageWithPrefix("NoPermissionMessage");
	}
	
	public String getCompleteAchievement(Player p, String aName) {
		String s = y.getMessageWithPrefix("CompleteAchievement");
		s = s.replace("{Player}", p.getName());
		s = s.replace("{Name}", aName);
		return s;
	}
	
	public String getSuccessfulUpgrade() {
		return y.getMessageWithPrefix("SuccessfulUpgrade");
	}
	
	public String getNotEnoughGold() {
		return y.getMessageWithPrefix("NotEnoughGold");
	}
	
	public String getRewardMessage(float xp, float gold) {
		String s = y.getMessageWithPrefix("RewardMessage");
		s = s.replace("{Xp}", ""+xp);
		s = s.replace("{Gold}", ""+gold);
		return s;
	}
	
	public String getFinishQuest(Player p, String qName) {
		String s = y.getMessageWithPrefix("FinishQuest");
		s = s.replace("{Player}", p.getName());
		s = s.replace("{Quest}", qName);
		return s;
	}
	
	public String getSecondsToReTakeQuest(int seconds) {
		String s = y.getMessageWithPrefix("SecondsToReTakeQuest");
		s = s.replace("{Seconds}", ""+seconds);
		return s;
	}
	
	public String getQuestTaken() {
		return y.getMessageWithPrefix("QuestTaken");
	}
	
	public String getWaitForAbility(int seconds) {
		String s = y.getMessageWithPrefix("WaitForAbility");
		s = s.replace("{Seconds}", ""+seconds);
		return s;
	}
	
	public String getKillBoss(Player p, String bName) { //ok
		String s =y.getMessageWithPrefix("PlayerKillBoss");
		s = s.replace("{Player}", p.getName());
		s = s.replace("{Boss}", bName);
		return s;
	}
	
	public String getEarnBossXp(Player p, String bName, float xp) { //ok
		String s = y.getMessageWithPrefix("PlayerEarnBossXp");
		s = s.replace("{Player}", p.getName());
		s = s.replace("{Boss}", bName);
		s = s.replace("{Xp}", ""+xp);
		return s;
	}
	
	public String getSkeletonWizardMove1() { //ok
		return y.getMessageWithPrefix("SkeletonWizardMove1");
	}
	
	public String getSkeletonWizardMove2() { //ok
		return y.getMessageWithPrefix("SkeletonWizardMove2");
	}
	
	public String getSkeletonWizardMove3() { //ok
		return y.getMessageWithPrefix("SkeletonWizardMove3");
	}
	
	public String getZombieMasterMove1() { //ok
		return y.getMessageWithPrefix("ZombieMasterMove1");
	}
	
	public String getZombieMasterMove2() { //ok
		return y.getMessageWithPrefix("ZombieMasterMove2");
	}
	
	public String getEarnMana(float mana) {
		String s = y.getMessageWithPrefix("EarnMana");
		s = s.replace("{Mana}", ""+mana);
		return s;
	}
	
	public String getFullMana() {
		return y.getMessageWithPrefix("FullMana");
	}
	
	public String getEarnXp(float xp) {
		String s = y.getMessageWithPrefix("EarnXp");
		s = s.replace("{Xp}", ""+xp);
		return s;
	}
	
	public String getSpellNotEnoughMana() {
		return y.getMessageWithPrefix("SpellNotEnoughMana");
	}
	
	public String getSpellFilledDrumsticks() {
		return y.getMessageWithPrefix("SpellFilledDrumsticks");
	}
	
	public String getSpellNoMoreHP() {
		return y.getMessageWithPrefix("SpellNoMoreHP");
	}
	
	public String getSpellAntiHungerActivate() {
		return y.getMessageWithPrefix("SpellAntiHungerActivate");
	}
	
	public String getSpellAntiHungerEnabled() {
		return y.getMessageWithPrefix("SpellAntiHungerEnabled");
	}
	
	public String getSpellAntiPoisonActivate() {
		return y.getMessageWithPrefix("SpellAntiPoisonActivate");
	}
	
	public String getSpellAntiPoisonEnabled() {
		return y.getMessageWithPrefix("SpellAntiPoisonEnabled");
	}
	
	public String getSpellAntiHungerDestroy() {
		return y.getMessageWithPrefix("SpellAntiHungerDestroy");
	}
	
	public String getSpellAntiPoisonDestroy() {
		return y.getMessageWithPrefix("SpellAntiPoisonDestroy");
	}
	
	public String getPlayerHadAntiHunger(Player p) {
		String s = y.getMessageWithPrefix("PlayerHadAntiHunger");
		s = s.replace("{Player}", p.getName());
		return s;
	}
	
	public String getPlayerHadAntiPoison(Player p) {
		String s = y.getMessageWithPrefix("PlayerHadAntiPoison");
		s = s.replace("{Player}", p.getName());
		return s;
	}
	
	public List<String> getLevelUpMessages(int oldLvl, int newLvl, float hp, float gold) {
		List<String> msg = y.getConfig().getStringList("LevelUpMessages");
		List<String> formattedMsg = new ArrayList<>();
		for(String s : msg) {
			s = s.replace("{OldLevel}", ""+oldLvl);
			s = s.replace("{NewLevel}", ""+newLvl);
			s = s.replace("{HpIncreasement}", ""+hp);
			s = s.replace("{Gold}", ""+gold);
			s = s.replace('&', '§');
			formattedMsg.add(s);
		}
		return formattedMsg;
	}
	
	public String getQuestStart(String qName) {
		String s = y.getMessageWithPrefix("QuestStart");
		s = s.replace("{Name}", qName);
		return s;
	}
	
	public String getBoughtItem() {
		return y.getMessageWithPrefix("BoughtItem");
	}
	
	public String getShopRefreshed() {
		return y.getMessageWithPrefix("ShopRefreshed");
	}
	
	public String getDeathTitle() {
		return y.getMessage("DeathTitle");
	}
	
	public String getDeathSubtitle() {
		return y.getMessage("DeathSubtitle");
	}
	
	public String getBackpackAfterUpgrade(int newLines) {
		String s = y.getMessageWithPrefix("BpAfterUpgrade");
		s = s.replace("{NewLines}", ""+newLines);
		return s;
	}
	
	public String getOpenPack(String packType) {
		return y.getMessageWithPrefix("Open"+packType+"Pack");
	}
	
	public String getItemFromPack(String packType, int amt, String mat) {
		String s = y.getMessageWithPrefix("ItemFrom"+packType+"Pack");
		s = s.replace("{Amount}", ""+amt);
		s = s.replace("{Material}", mat);
		return s;
	}
	
	public String getArmorsSuc(String mob) {
		String s = y.getMessageWithPrefix("SetArmorsSuccess");
		s = s.replace("{Mob}", mob);
		return s;
	}
	
	public String getDropsSuc(String mob) {
		String s = y.getMessageWithPrefix("SetDropsSuccess");
		s = s.replace("{Mob}", mob);
		return s;
	}
	
	public String getNameSuc(String mob) {
		String s = y.getMessageWithPrefix("SetNameSuccess");
		s = s.replace("{Mob}", mob);
		return s;
	}
	
	public String getLevelSuc(String mob) {
		String s = y.getMessageWithPrefix("SetLevelSuccess");
		s = s.replace("{Mob}", mob);
		return s;
	}
	
	public String getCanSpawnSuc(String mob) {
		String s = y.getMessageWithPrefix("SetCanSpawnSuccess");
		s = s.replace("{Mob}", mob);
		return s;
	}
	
	public String getFinishSuc(String mob) {
		String s = y.getMessageWithPrefix("FinishSuccess");
		s = s.replace("{Mob}", mob);
		return s;
	}
	
	public String getCreateSuc(String mob) {
		String s = y.getMessageWithPrefix("CreateSuccess");
		s = s.replace("{Mob}", mob);
		return s;
	}
	
	public String getDisabledShop() {
		return y.getMessageWithPrefix("ShopDisabled");
	}
	
	public String getDisabledCustomMobs() {
		return y.getMessageWithPrefix("DisabledCustomMobs");
	}
	
	public String getRewardClassPoint(String name) {
		String s = y.getMessageWithPrefix("RewardClassPoint");
		s = s.replace("{Class}", name);
		return s;
	}
	
	public String getLevelUpClass(String name, int oldLevel, int newLevel) {
		String s = y.getMessageWithPrefix("LevelUpClass");
		s = s.replace("{Class}", name);
		s = s.replace("{OldLevel}", ""+oldLevel);
		s = s.replace("{NewLevel}", ""+newLevel);
		return s;
	}
	
	public String getUnlockClass(String name) {
		String s = y.getMessageWithPrefix("UnlockClass");
		s = s.replace("{Class}", name);
		return s;
	}
	
	public String getNotEnoughClassPoints() {
		return y.getMessageWithPrefix("NotEnoughClassPoints");
	}
	
	public String getAlreadyEquippedClass() {
		return y.getMessageWithPrefix("AlreadyEquippedClass");
	}
	
	public String getEquipClass() {
		return y.getMessageWithPrefix("EquipClass");
	}
	
	public String getMysteriousClassCost() {
		return y.getMessageWithPrefix("MysteriousClassCost");
	}
	
	public String getCannotHoldItem(String name) {
		String s = y.getMessageWithPrefix("CannotHoldItem");
		s = s.replace("{Class}", name);
		return s;
	}
	
	public String getSendClassPoints(int classPoints) {
		String s = y.getMessageWithPrefix("SendClassPoints");
		s = s.replace("{ClassPoints}", ""+classPoints);
		return s;
	}
	
	public String getNotEnabledCommandInWorld() {
		return y.getMessageWithPrefix("NotEnabledCommandInWorld");
	}
	
	public String getLevelTitle() {
		/*
		try {
			return y.getMessageWithPrefix("LevelTitle");
		} catch(NullPointerException ex) {
			System.out.println(ex);
			return "";
		}
		*/
		return y.getMessageWithPrefix("LevelTitle");
	}
	
	public String getBPTitle() {
		return y.getMessageWithPrefix("BPTitle");
	}
	
	public String getBBTitle() {
		return y.getMessageWithPrefix("BBTitle");
	}
	
	public String getBasicLeaderboardLine(String ranking, String player, String score) {
		String s = y.getMessageWithPrefix("BasicLeaderboardLine");
		s = s.replace("{Ranking}", ranking);
		s = s.replace("{Player}", player);
		s = s.replace("{Score}", score);
		return s;
	}
	
	public String getNULL_Line() {
		return y.getMessage("NULL_Line");
	}
	
	public String getRankingLine(String ranking) {
		String s = y.getMessageWithPrefix("RankingLine");
		s = s.replace("{Ranking}", ranking);
		return s;
	}
	
	public String getWrongLeaderboard() {
		return y.getMessageWithPrefix("WrongLeaderboard");
	}
	
	public List<String> getRpgItemLore(double minDamage, double maxDamage, int criticalLuck, String rarity, int minLevel) {
		List<String> lore = y.getConfig().getStringList("RpgItemLore");
		List<String> fLore = new ArrayList<>();
		for(String s : lore) {
			s = s.replace('&', '§');
			s = s.replace("{MinDamage}", ""+minDamage);
			s = s.replace("{MaxDamage}", ""+maxDamage);
			s = s.replace("{CriticalLuck}", ""+criticalLuck);
			s = s.replace("{Rarity}", rarity);
			s = s.replace("{MinLevel}", ""+minLevel);
			fLore.add(s);
		}
		return fLore;
	}
	
	public String getEmptyHand() {
		return y.getMessageWithPrefix("EmptyHand");
	}
	
	public String getAlreadyLootable() {
		return y.getMessageWithPrefix("AlreadyLootable");
	}
	
	public String getSecondArgument() {
		return y.getMessageWithPrefix("SecondArgument");
	}
	
	public String getInvalidEnchantment() {
		return y.getMessageWithPrefix("InvalidEnchantment");
	}
	
	public String getTreasures2NotExist() {
		return y.getMessageWithPrefix("Treasures2.NotExist");
	}
	
	public String getTreasures2SameId() {
		return y.getMessageWithPrefix("Treasures2.SameId");
	}
	
	public String getTreasures2Creating() {
		return y.getMessageWithPrefix("Treasures2.Creating");
	}
	
	public String getTreasures2EmptyTreasure() {
		return y.getMessageWithPrefix("Treasures2.EmptyTreasure");
	}
	
	public String getTreasures2FoundTreasure(String name) {
		String s = y.getMessageWithPrefix("Treasures2.FoundTreasure");
		s = s.replace("{Name}", name);
		return s;
	}
	
	public String getTreasures2Complete() {
		return y.getMessageWithPrefix("Treasures2.CompleteCreation");
	}
	
	public String getTreasures2ListTitle() {
		return y.getMessageWithPrefix("Treasures2.List.Title");
	}
	
	public String getTreasures2ListLine(String name, String fileName) {
		String s = y.getMessageWithPrefix("Treasures2.List.Line");
		s = s.replace("{Name}", name);
		s = s.replace("{File}", fileName);
		return s;
	}
	
	public String getSpecificSpellError() {
		return y.getMessageWithPrefix("GiveSpecificSpellError");
	}
	
	public String getSpecificWeaponError() {
		return y.getMessageWithPrefix("GiveSpecificWeaponError");
	}
	
	public String getNoMoreFuel() {
		return y.getMessageWithPrefix("NoMoreFuel");
	}
	
	public String getFullyCharged() {
		return y.getMessageWithPrefix("FullyCharged");
	}

	public String getPlayerNotFound() {
		return y.getMessageWithPrefix("PlayerNotFound");
	}
	
	public String getUseOnPlayer() {
		return y.getMessageWithPrefix("UseOnPlayer");
	}
	
	public String getEmptyPlayer(String suspect) {
		String s = y.getMessageWithPrefix("EmptyPlayer");
		s = s.replace("{Suspect}", suspect);
		return s;
	}
	
	public String getYouStole(String suspect, String item) {
		String s = y.getMessageWithPrefix("YouStole");
		s = s.replace("{Suspect}", suspect);
		s = s.replace("{Item}", item);
		return s;
	}
	
	public String getStolenFromYou(String wizard, String item) {
		String s = y.getMessageWithPrefix("StolenFromYou");
		s = s.replace("{Wizard}", wizard);
		s = s.replace("{Item}", item);
		return s;
	}
	
	public String getAddAbilitySuccess(String a, String m) {
		String s = y.getMessageWithPrefix("AddAbilitySuccess");
		s = s.replace("{Ability}", a);
		s = s.replace("{Mob}", m);
		return s;
	}
	
	public String getRemoveAbilitySuccess(String a, String m) {
		String s = y.getMessageWithPrefix("RemoveAbilitySuccess");
		s = s.replace("{Ability}", a);
		s = s.replace("{Mob}", m);
		return s;
	}
	
	public String getMobNotFound() {
		return y.getMessageWithPrefix("MobNotFound");
	}
	
	public String getSetHPSuccess(double hp) {
		String s = y.getMessageWithPrefix("SetHPSuccess");
		s = s.replace("{HP}", ""+RCUtils.round(hp,1));
		return s;
	}
	
	public String getAddBiomeSuccess(String biome) {
		String s = y.getMessageWithPrefix("AddBiomeSuccess");
		s= s.replace("{Biome}", biome);
		return s;
	}
	
	public String getRemoveBiomeSuccess(String biome) {
		String s = y.getMessageWithPrefix("RemoveBiomeSuccess");
		s= s.replace("{Biome}", biome);
		return s;
	}
	
	public String getBiomeNotFound() {
		return y.getMessageWithPrefix("BiomeNotFound");
	}
	
	public List<String> getRpgArmorLore(double defense, Rarity rarity, int minLevel) {
		List<String> lore = y.getConfig().getStringList("RpgArmorLore");
		List<String> edited = new ArrayList<>();
		for(String s : lore) {
			s = s.replace('&', '§');
			s = s.replace("{Defense}", ""+RCUtils.round(defense, 1));
			s = s.replace("{Rarity}", rarity.getColor()+rarity.name());
			s = s.replace("{MinLevel}", ""+minLevel);
			edited.add(s);
		}
		return edited;
	}
	
	public String getPickupGoldMessage(double gold) {
		String s = y.getMessageWithPrefix("PickupGoldMessage");
		s = s.replace("{Gold}", ""+RCUtils.round(gold, 1));
		return s;
	}
	
	public String getCannotWearArmorMessage() {
		return y.getMessageWithPrefix("CannotWearArmor");
	}
	
	public String getActionNotAllowedInWorld() {
		return y.getMessageWithPrefix("ActionNotAllowedInWorld");
	}
}
