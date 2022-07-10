package me.amc.rankcraft.achievements;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import me.amc.rankcraft.ConfigHelper;
import me.amc.rankcraft.MainCore;

public class AchievementsList {
	
	public AchievementKillZombies killZombies1;
	public AchievementKillZombies killZombies2;
	public AchievementKillZombies killZombies3;
	
	public AchievementFindTreasures luckyMan1;
	public AchievementFindTreasures luckyMan2;
	public AchievementFindTreasures luckyMan3;
	public AchievementFindTreasures luckyMan4;
	
	public AchievementUseSpells wizard1;
	public AchievementUseSpells wizard2;
	public AchievementUseSpells wizard3;
	public AchievementUseSpells wizard4;
	
	public AchievementMaterialBreaker stoneDestoyer1;
	public AchievementMaterialBreaker stoneDestoyer2;
	public AchievementMaterialBreaker stoneDestoyer3;
	public AchievementMaterialBreaker stoneDestoyer4;
	public AchievementMaterialBreaker stoneDestoyer5;
	
	public AchievementMaterialBreaker ironMiner1;
	public AchievementMaterialBreaker ironMiner2;
	public AchievementMaterialBreaker ironMiner3;
	public AchievementMaterialBreaker ironMiner4;
	public AchievementMaterialBreaker ironMiner5;
	
	public AchievementBlockBreaker blockBreaker1;
	public AchievementBlockBreaker blockBreaker2;
	public AchievementBlockBreaker blockBreaker3;
	public AchievementBlockBreaker blockBreaker4;
	public AchievementBlockBreaker blockBreaker5;
	
	public AchievementMobKiller skeletonKiller1;
	public AchievementMobKiller skeletonKiller2;
	public AchievementMobKiller skeletonKiller3;
	
	public AchievementMobKiller spiderKiller1;
	public AchievementMobKiller spiderKiller2;
	public AchievementMobKiller spiderKiller3;
	
	public AchievementMobKiller endermanKiller1;
	public AchievementMobKiller endermanKiller2;
	public AchievementMobKiller endermanKiller3;
	
	public AchievementMobKiller creeperKiller1;
	public AchievementMobKiller creeperKiller2;
	public AchievementMobKiller creeperKiller3;
	
	public AchievementExcaliburMaster excaliburMaster;
	
	public AchievementMysteryMaster mysteryMaster;
	
	public AchievementSmith smith1;
	public AchievementSmith smith2;
	public AchievementSmith smith3;
	
	public AchievementsList() {
		make();
	}
	
	private void make() {
		
		ConfigHelper c = MainCore.instance.config;
		
		killZombies1 = new AchievementKillZombies("KillZombies I", 1, c.getRewardForAchievement("KillZombies", 1));
		killZombies2 = new AchievementKillZombies("KillZombies II", 50, c.getRewardForAchievement("KillZombies", 2));
		killZombies3 = new AchievementKillZombies("KillZombies III", 100, c.getRewardForAchievement("KillZombies", 3));
		
		luckyMan1 = new AchievementFindTreasures("LuckyMan I", 10, c.getRewardForAchievement("LuckyMan",1));
		luckyMan2 = new AchievementFindTreasures("LuckyMan II", 50, c.getRewardForAchievement("LuckyMan", 2));
		luckyMan3 = new AchievementFindTreasures("LuckyMan III", 100, c.getRewardForAchievement("LuckyMan", 3));
		luckyMan4 = new AchievementFindTreasures("LuckyMan IV", 500, c.getRewardForAchievement("LuckyMan", 4));
		
		wizard1 = new AchievementUseSpells("Wizard I", 10, c.getRewardForAchievement("Wizard", 1));
		wizard2 = new AchievementUseSpells("Wizard II", 30, c.getRewardForAchievement("Wizard", 2));
		wizard3 = new AchievementUseSpells("Wizard III", 50, c.getRewardForAchievement("Wizard", 3));
		wizard4 = new AchievementUseSpells("Wizard IV", 100, c.getRewardForAchievement("Wizard", 4));
		
		stoneDestoyer1 = new AchievementMaterialBreaker("StoneDestroyer I", 100, Material.STONE, c.getRewardForAchievement("StoneDestroyer", 1));
		stoneDestoyer2 = new AchievementMaterialBreaker("StoneDestroyer II", 1000, Material.STONE, c.getRewardForAchievement("StoneDestroyer", 2));
		stoneDestoyer3 = new AchievementMaterialBreaker("StoneDestroyer III", 5000, Material.STONE, c.getRewardForAchievement("StoneDestroyer", 3));
		stoneDestoyer4 = new AchievementMaterialBreaker("StoneDestroyer IV", 10000, Material.STONE, c.getRewardForAchievement("StoneDestroyer", 4));
		stoneDestoyer5 = new AchievementMaterialBreaker("StoneDestroyer V", 50000, Material.STONE, c.getRewardForAchievement("StoneDestroyer", 5));
		
		ironMiner1 = new AchievementMaterialBreaker("IronMiner I", 100, Material.IRON_ORE, c.getRewardForAchievement("IronMiner", 1));
		ironMiner2 = new AchievementMaterialBreaker("IronMiner II", 200, Material.IRON_ORE, c.getRewardForAchievement("IronMiner", 2));
		ironMiner3 = new AchievementMaterialBreaker("IronMiner III", 500, Material.IRON_ORE, c.getRewardForAchievement("IronMiner", 3));
		ironMiner4 = new AchievementMaterialBreaker("IronMiner IV", 750, Material.IRON_ORE, c.getRewardForAchievement("IronMiner", 4));
		ironMiner5 = new AchievementMaterialBreaker("IronMiner V", 1000, Material.IRON_ORE, c.getRewardForAchievement("IronMiner", 5));
		
		blockBreaker1 = new AchievementBlockBreaker("BlockBreaker I", 1000, c.getRewardForAchievement("BlockBreaker", 1));
		blockBreaker2 = new AchievementBlockBreaker("BlockBreaker II", 2500, c.getRewardForAchievement("BlockBreaker", 2));
		blockBreaker3 = new AchievementBlockBreaker("BlockBreaker III", 5000, c.getRewardForAchievement("BlockBreaker", 3));
		blockBreaker4 = new AchievementBlockBreaker("BlockBreaker IV", 10000, c.getRewardForAchievement("BlockBreaker", 4));
		blockBreaker5 = new AchievementBlockBreaker("BlockBreaker V", 100000, c.getRewardForAchievement("BlockBreaker", 5));
		
		skeletonKiller1 = new AchievementMobKiller("SkeletonKiller I", EntityType.SKELETON, 1, c.getRewardForAchievement("SkeletonKiller", 1));
		skeletonKiller2 = new AchievementMobKiller("SkeletonKiller II", EntityType.SKELETON, 50, c.getRewardForAchievement("SkeletonKiller", 2));
		skeletonKiller3 = new AchievementMobKiller("SkeletonKiller III", EntityType.SKELETON, 100, c.getRewardForAchievement("SkeletonKiller", 3));
		
		spiderKiller1 = new AchievementMobKiller("SpiderKiller I", EntityType.SPIDER, 1, c.getRewardForAchievement("SpiderKiller", 1));
		spiderKiller2 = new AchievementMobKiller("SpiderKiller II", EntityType.SPIDER, 50, c.getRewardForAchievement("SpiderKiller", 2));
		spiderKiller3 = new AchievementMobKiller("SpiderKiller III", EntityType.SPIDER, 100, c.getRewardForAchievement("SpiderKiller", 3));
		
		endermanKiller1 = new AchievementMobKiller("EndermanKiller I", EntityType.ENDERMAN, 1, c.getRewardForAchievement("EndermanKiller", 1));
		endermanKiller2 = new AchievementMobKiller("EndermanKiller II", EntityType.ENDERMAN, 50, c.getRewardForAchievement("EndermanKiller", 2));
		endermanKiller3 = new AchievementMobKiller("EndermanKiller III", EntityType.ENDERMAN, 100, c.getRewardForAchievement("EndermanKiller", 3));
		
		creeperKiller1 = new AchievementMobKiller("CreeperKiller I", EntityType.CREEPER, 1, c.getRewardForAchievement("CreeperKiller", 1));
		creeperKiller2 = new AchievementMobKiller("CreeperKiller II", EntityType.CREEPER, 50, c.getRewardForAchievement("CreeperKiller", 2));
		creeperKiller3 = new AchievementMobKiller("CreeperKiller III", EntityType.CREEPER, 100, c.getRewardForAchievement("CreeperKiller", 3));
		
		excaliburMaster = new AchievementExcaliburMaster("ExcaliburMaster", 1, c.getRewardForAchievement("ExcaliburMaster", 1));
		
		mysteryMaster = new AchievementMysteryMaster("MysteryMaster", 1, c.getRewardForAchievement("MysteryMaster", 1));
		
		smith1 = new AchievementSmith("Smith I", 10, c.getRewardForAchievement("Smith", 1));
		smith2 = new AchievementSmith("Smith II", 30, c.getRewardForAchievement("Smith", 2));
		smith3 = new AchievementSmith("Smith III", 50, c.getRewardForAchievement("Smith", 3));
		
	}
	
}
