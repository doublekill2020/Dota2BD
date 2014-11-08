package cn.edu.mydotabuff.bean;

import java.io.Serializable;
import java.util.List;

public class HeroMatchStatistics implements Serializable{
	private static final long serialVersionUID = 11081535L;
	private String heroName; //英雄名称
	private String matchID; //比赛Id
	private String matchType; //比赛类型
	private String whatTime; // 比赛时间
	private String result; // 比赛结果
	private double KDA; // KDA
	private double Kill; // kill
	private double Death;// death
	private double Assists; //assists
	private String level; // level
	private  List<String> itemsImgURI; // 物品图片地址
	public String getHeroName() {
		return heroName;
	}
	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}
	public String getMatchID() {
		return matchID;
	}
	public void setMatchID(String matchID) {
		this.matchID = matchID;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getWhatTime() {
		return whatTime;
	}
	public void setWhatTime(String whatTime) {
		this.whatTime = whatTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public double getKDA() {
		return KDA;
	}
	public void setKDA(double kDA) {
		KDA = kDA;
	}
	public double getKill() {
		return Kill;
	}
	public void setKill(double kill) {
		Kill = kill;
	}
	public double getDeath() {
		return Death;
	}
	public void setDeath(double death) {
		Death = death;
	}
	public double getAssists() {
		return Assists;
	}
	public void setAssists(double assists) {
		Assists = assists;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public List<String> getItemsImgURI() {
		return itemsImgURI;
	}
	public void setItemsImgURI(List<String> itemsImgURI) {
		this.itemsImgURI = itemsImgURI;
	}
	@Override
	public String toString() {
		return "HeroMatchStatistics [heroName=" + heroName + ", matchID="
				+ matchID + ", matchType=" + matchType + ", whatTime="
				+ whatTime + ", result=" + result + ", KDA=" + KDA + ", Kill="
				+ Kill + ", Death=" + Death + ", Assists=" + Assists
				+ ", level=" + level + ", itemsImgURI=" + itemsImgURI + "]";
	}
	public HeroMatchStatistics(String heroName, String matchID,
			String matchType, String whatTime, String result, double kDA,
			double kill, double death, double assists, String level,
			List<String> itemsImgURI) {
		super();
		this.heroName = heroName;
		this.matchID = matchID;
		this.matchType = matchType;
		this.whatTime = whatTime;
		this.result = result;
		KDA = kDA;
		Kill = kill;
		Death = death;
		Assists = assists;
		this.level = level;
		this.itemsImgURI = itemsImgURI;
	}
	public HeroMatchStatistics() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

}
