package cn.edu.mydotabuff.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class PlayerInfoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1029L;
	private String steamid;
	private int communityState;// 社交关系
	private String name;
	private String lastlogooff;// 最后登录
	private String mediumIcon;
	private int state;// 在线状态
	private String timecreated;

	private boolean isLoadWebData = false;//代表是否获取过jsonup数据
	private String winStreak;
	private String loseStreak;
	private ArrayList<BestRecord> beans;

	private ArrayList<MacthStatistics> list;
	private boolean isLoadStatistics = false;//代表是否获取过全部比赛统计数据
	
	
	public ArrayList<MacthStatistics> getList() {
		return list;
	}

	public void setList(ArrayList<MacthStatistics> list) {
		this.list = list;
	}

	public boolean isLoadMap() {
		return isLoadStatistics;
	}

	public void setLoadMap(boolean isLoadStatistics) {
		this.isLoadStatistics = isLoadStatistics;
	}

	public boolean isLoadWebData() {
		return isLoadWebData;
	}

	public void setLoadWebData(boolean isLoadWebData) {
		this.isLoadWebData = isLoadWebData;
	}

	public String getWinStreak() {
		return winStreak;
	}

	public void setWinStreak(String winStreak) {
		this.winStreak = winStreak;
	}

	public String getLoseStreak() {
		return loseStreak;
	}

	public void setLoseStreak(String loseStreak) {
		this.loseStreak = loseStreak;
	}

	public ArrayList<BestRecord> getBeans() {
		return beans;
	}

	public void setBeans(ArrayList<BestRecord> beans) {
		this.beans = beans;
	}

	public PlayerInfoBean(String steamid, int communityState, String name,
			String lastlogooff, String mediumIcon, int state, String timecreated) {
		super();
		this.steamid = steamid;
		this.communityState = communityState;
		this.name = name;
		this.lastlogooff = lastlogooff;
		this.mediumIcon = mediumIcon;
		this.state = state;
		this.timecreated = timecreated;
	}

	public String getSteamid() {
		return steamid;
	}

	public void setSteamid(String steamid) {
		this.steamid = steamid;
	}

	public int getCommunityState() {
		return communityState;
	}

	public void setCommunityState(int communityState) {
		this.communityState = communityState;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastlogooff() {
		return lastlogooff;
	}

	public void setLastlogooff(String lastlogooff) {
		this.lastlogooff = lastlogooff;
	}

	public String getMediumIcon() {
		return mediumIcon;
	}

	public void setMediumIcon(String mediumIcon) {
		this.mediumIcon = mediumIcon;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTimecreated() {
		return timecreated;
	}

	public void setTimecreated(String timecreated) {
		this.timecreated = timecreated;
	}

	public PlayerInfoBean(int communityState, String name, String lastlogooff,
			String mediumIcon, int state, String timecreated) {
		super();
		this.communityState = communityState;
		this.name = name;
		this.lastlogooff = lastlogooff;
		this.mediumIcon = mediumIcon;
		this.state = state;
		this.timecreated = timecreated;
	}

	public PlayerInfoBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "PlayerInfoBean [steamid=" + steamid + ", communityState="
				+ communityState + ", name=" + name + ", lastlogooff="
				+ lastlogooff + ", mediumIcon=" + mediumIcon + ", state="
				+ state + ", timecreated=" + timecreated + ", winStreak="
				+ winStreak + ", loseStreak=" + loseStreak + ", beans=" + beans
				+ "]";
	}

}
