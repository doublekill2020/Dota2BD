package cn.edu.mydotabuff.bean;

public class BoardBean {
	private String updateTime;
	private int rank;
	private String name;
	private int solo_mmr;
	public BoardBean(String updateTime, int rank, String name, int solo_mmr) {
		super();
		this.updateTime = updateTime;
		this.rank = rank;
		this.name = name;
		this.solo_mmr = solo_mmr;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSolo_mmr() {
		return solo_mmr;
	}
	public void setSolo_mmr(int solo_mmr) {
		this.solo_mmr = solo_mmr;
	}
	
}
