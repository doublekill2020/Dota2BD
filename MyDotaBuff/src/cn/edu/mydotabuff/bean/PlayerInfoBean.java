package cn.edu.mydotabuff.bean;

public class PlayerInfoBean {
	private String steamid;
	private int communityState;//社交关系
	private String name;
	private String lastlogooff;//最后登录
	private String mediumIcon;
	private int state;//在线状态 
	private String timecreated;
	
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
	
}
