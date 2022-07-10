package me.amc.rankcraft.leaderboards;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.amc.rankcraft.MainCore;
import me.amc.rankcraft.lang.Language;

public class Leaderboard {

	public enum LeaderboardType {
		Level,
		SpellsUsed,
		BlocksBroken,
		BlocksPlaced;
	}
	
	private String title;
	private LeaderboardType type;
	private List<Member> leaderboardMembers = new ArrayList<>();
	
	public Leaderboard(String title, LeaderboardType type, List<Member> leaderboardMembers) {
		this.title = title;
		this.type = type;
		this.leaderboardMembers = leaderboardMembers;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Member> getLeaderboardMembers() {
		return leaderboardMembers;
	}

	public void setLeaderboardMembers(List<Member> leaderboardMembers) {
		this.leaderboardMembers = leaderboardMembers;
	}
	
	public void bubbleSort() {
		boolean flag = true;
		Member temp;
		int j;
		
		while(flag) {
			
			flag = false;
			for(j = 0; j < leaderboardMembers.size() - 1; j++) {
				if(type == LeaderboardType.Level) {
					if(leaderboardMembers.get(j).getLevel() < leaderboardMembers.get(j + 1).getLevel()) {
						temp = leaderboardMembers.get(j);
						leaderboardMembers.set(j, leaderboardMembers.get(j + 1));
						leaderboardMembers.set(j + 1, temp);
						flag = true;
					}
				}else if(type == LeaderboardType.BlocksBroken) {
					if(leaderboardMembers.get(j).getBb() < leaderboardMembers.get(j + 1).getBb()) {
						temp = leaderboardMembers.get(j);
						leaderboardMembers.set(j, leaderboardMembers.get(j + 1));
						leaderboardMembers.set(j + 1, temp);
						flag = true;
					}
				}else if(type == LeaderboardType.BlocksPlaced) {
					if(leaderboardMembers.get(j).getBp() < leaderboardMembers.get(j + 1).getBp()) {
						temp = leaderboardMembers.get(j);
						leaderboardMembers.set(j, leaderboardMembers.get(j + 1));
						leaderboardMembers.set(j + 1, temp);
						flag = true;
					}
				}else if(type == LeaderboardType.SpellsUsed) {
					if(leaderboardMembers.get(j).getSu() < leaderboardMembers.get(j + 1).getSu()) {
						temp = leaderboardMembers.get(j);
						leaderboardMembers.set(j, leaderboardMembers.get(j + 1));
						leaderboardMembers.set(j + 1, temp);
						flag = true;
					}
				}
			}
			
		}
	}
	
	public void update() {
		leaderboardMembers.clear();
		// More info on Leaderboard Classes
	}
	
	public void sendLeaderboard(Player p) {		
		Language lang = MainCore.instance.language;
		Integer ranking = (Integer) null;
		
		p.sendMessage(title);
		if(leaderboardMembers.size() >= 10) {
			for(int i = 0; i < 10; i++) {
				
			//	p.sendMessage(i+1+") "+Bukkit.getOfflinePlayer(UUID.fromString(leaderboardMembers.get(i).getUuid())).getName()+" "+getScore(leaderboardMembers.get(i)));

				p.sendMessage(lang.getBasicLeaderboardLine(""+(i+1), Bukkit.getOfflinePlayer(UUID.fromString(leaderboardMembers.get(i).getUuid())).getName(), ""+getScore(leaderboardMembers.get(i))));
				
				if(leaderboardMembers.get(i).getUuid().equals(p.getUniqueId().toString())) {
					ranking = i+1;
				}
				
			}
		} else {
			for(int i = 0; i < leaderboardMembers.size(); i++) {
				
			//	p.sendMessage(i+1+") "+Bukkit.getOfflinePlayer(UUID.fromString(leaderboardMembers.get(i).getUuid())).getName()+" "+getScore(leaderboardMembers.get(i)));
				
				p.sendMessage(lang.getBasicLeaderboardLine(""+(i+1), Bukkit.getOfflinePlayer(UUID.fromString(leaderboardMembers.get(i).getUuid())).getName(), ""+getScore(leaderboardMembers.get(i))));
				
				if(leaderboardMembers.get(i).getUuid().equals(p.getUniqueId().toString())) {
					ranking = i+1;
				}
				
			}
			for(int j = leaderboardMembers.size(); j < 10; j++) {
			//	p.sendMessage(j+1+") NULL");
				p.sendMessage(lang.getBasicLeaderboardLine(""+(j+1), lang.getNULL_Line(), ""));
		
			}
		}
	//	p.sendMessage(ChatColor.GOLD+"Your ranking: "+ranking);
		p.sendMessage(lang.getRankingLine(""+ranking));
	
		
		
	}
	
	private int getScore(Member member) {
		int score = 0;
		
		switch(type) {
		case BlocksBroken:
			score = member.getBb();
			break;
		case BlocksPlaced:
			score = member.getBp();
			break;
		case Level:
			score = member.getLevel();
			break;
		case SpellsUsed:
			score = member.getSu();
			break;
		default:
			break;
		
		}
		
		return score;
	}
	
	public class Member {
		
		private int level;
		private int bb; // blocks broken
		private int bp; // blocks placed
		private int su; // spells used
		private String uuid;
		
		public Member(String uuid, int level, int bb, int bp, int su) {
			this.level = level;
			this.bb = bb;
			this.bp = bp;
			this.su = su;
			this.uuid = uuid;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public int getBb() {
			return bb;
		}

		public void setBb(int bb) {
			this.bb = bb;
		}

		public int getBp() {
			return bp;
		}

		public void setBp(int bp) {
			this.bp = bp;
		}

		public int getSu() {
			return su;
		}

		public void setSu(int su) {
			this.su = su;
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		
		
		
	}
}
