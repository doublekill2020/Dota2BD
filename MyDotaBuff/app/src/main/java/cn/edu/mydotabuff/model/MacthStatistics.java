package cn.edu.mydotabuff.model;

import java.io.Serializable;

public class MacthStatistics implements Serializable{

	private static final long serialVersionUID = 10302051L;
	//全部比赛比赛: 231胜率: 48.48%KDA: 2.4

	private String Type;
	private String PlayTimes;
	private String Winning;
	private String KAD;
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getPlayTimes() {
		return PlayTimes;
	}
	public void setPlayTimes(String playTimes) {
		PlayTimes = playTimes;
	}
	public String getWinning() {
		return Winning;
	}
	public void setWinning(String winning) {
		Winning = winning;
	}
	public String getKAD() {
		return KAD;
	}
	public void setKAD(String kAD) {
		KAD = kAD;
	}
	@Override
	public String toString() {
		return "MacthStatistics [Type=" + Type + ", PlayTimes=" + PlayTimes
				+ ", Winning=" + Winning + ", KAD=" + KAD + "]";
	}
	public MacthStatistics() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MacthStatistics(String type, String playTimes, String winning,
			String kAD) {
		super();
		Type = type;
		PlayTimes = playTimes;
		Winning = winning;
		KAD = kAD;
	}
	
	
}
