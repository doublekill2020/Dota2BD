package cn.edu.mydotabuff.bean;

import java.util.List;

public class HeroMatchStatistics {
	private String heroName; //Ӣ������
	private String matchID; //����Id
	private String matchType; //��������
	private String whatTime; //����ʱ��
	private String result; // �������
	private Double KDA; // KDA
	private Double Kill; // ����ɱ����
	private Double Death;//��������
	private Double Assists; //��������
	private String level; //��������
	private  List<String> itemsImgURI; //��ƷͼƬ��ַ;
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
	public Double getKDA() {
		return KDA;
	}
	public void setKDA(Double kDA) {
		KDA = kDA;
	}
	public Double getKill() {
		return Kill;
	}
	public void setKill(Double kill) {
		Kill = kill;
	}
	public Double getDeath() {
		return Death;
	}
	public void setDeath(Double death) {
		Death = death;
	}
	public Double getAssists() {
		return Assists;
	}
	public void setAssists(Double assists) {
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
			String matchType, String whatTime, String result, Double kDA,
			Double kill, Double death, Double assists, String level,
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
