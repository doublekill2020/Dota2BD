package cn.edu.mydotabuff.common.bean;

import java.io.Serializable;

public class HerosSatistics implements Serializable{

	private static final long serialVersionUID = 11081115L;
	private int heroID;
	private String thisHeroDataUri;// 英雄数据合集
	private String heroName; // 英雄名字
	private int useTimes; // 使用次数
	private double Winning; // 胜率
	private double KDA; // KDA
	private double Kill; // 场均杀人数
	private double Death;// 场均死亡数
	private double Assists; // 场均助攻数
	private double allKAD; // 所有人KDA
	private double gold_PerMin; // 金钱每分钟
	private double xp_PerMin; // 经验每分钟
	public int getHeroID() {
		return heroID;
	}
	public void setHeroID(int heroID) {
		this.heroID = heroID;
	}
	public String getThisHeroDataUri() {
		return thisHeroDataUri;
	}
	public void setThisHeroDataUri(String thisHeroDataUri) {
		this.thisHeroDataUri = thisHeroDataUri;
	}
	public String getHeroName() {
		return heroName;
	}
	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}
	public int getUseTimes() {
		return useTimes;
	}
	public void setUseTimes(int useTimes) {
		this.useTimes = useTimes;
	}
	public double getWinning() {
		return Winning;
	}
	public void setWinning(double winning) {
		Winning = winning;
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
	public double getAllKAD() {
		return allKAD;
	}
	public void setAllKAD(double allKAD) {
		this.allKAD = allKAD;
	}
	public double getGold_PerMin() {
		return gold_PerMin;
	}
	public void setGold_PerMin(double gold_PerMin) {
		this.gold_PerMin = gold_PerMin;
	}
	public double getXp_PerMin() {
		return xp_PerMin;
	}
	public void setXp_PerMin(double xp_PerMin) {
		this.xp_PerMin = xp_PerMin;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(Assists);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(Death);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(KDA);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(Kill);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(Winning);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(allKAD);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(gold_PerMin);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + heroID;
		result = prime * result
				+ ((heroName == null) ? 0 : heroName.hashCode());
		result = prime * result
				+ ((thisHeroDataUri == null) ? 0 : thisHeroDataUri.hashCode());
		result = prime * result + useTimes;
		temp = Double.doubleToLongBits(xp_PerMin);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HerosSatistics other = (HerosSatistics) obj;
		if (Double.doubleToLongBits(Assists) != Double
				.doubleToLongBits(other.Assists))
			return false;
		if (Double.doubleToLongBits(Death) != Double
				.doubleToLongBits(other.Death))
			return false;
		if (Double.doubleToLongBits(KDA) != Double.doubleToLongBits(other.KDA))
			return false;
		if (Double.doubleToLongBits(Kill) != Double
				.doubleToLongBits(other.Kill))
			return false;
		if (Double.doubleToLongBits(Winning) != Double
				.doubleToLongBits(other.Winning))
			return false;
		if (Double.doubleToLongBits(allKAD) != Double
				.doubleToLongBits(other.allKAD))
			return false;
		if (Double.doubleToLongBits(gold_PerMin) != Double
				.doubleToLongBits(other.gold_PerMin))
			return false;
		if (heroID != other.heroID)
			return false;
		if (heroName == null) {
			if (other.heroName != null)
				return false;
		} else if (!heroName.equals(other.heroName))
			return false;
		if (thisHeroDataUri == null) {
			if (other.thisHeroDataUri != null)
				return false;
		} else if (!thisHeroDataUri.equals(other.thisHeroDataUri))
			return false;
		if (useTimes != other.useTimes)
			return false;
		if (Double.doubleToLongBits(xp_PerMin) != Double
				.doubleToLongBits(other.xp_PerMin))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "HerosSatistics [heroID=" + heroID + ", thisHeroDataUri="
				+ thisHeroDataUri + ", heroName=" + heroName + ", useTimes="
				+ useTimes + ", Winning=" + Winning + ", KDA=" + KDA
				+ ", Kill=" + Kill + ", Death=" + Death + ", Assists="
				+ Assists + ", allKAD=" + allKAD + ", gold_PerMin="
				+ gold_PerMin + ", xp_PerMin=" + xp_PerMin + "]";
	}
	public HerosSatistics(int heroID, String thisHeroDataUri, String heroName,
			int useTimes, double winning, double kDA, double kill,
			double death, double assists, double allKAD, double gold_PerMin,
			double xp_PerMin) {
		super();
		this.heroID = heroID;
		this.thisHeroDataUri = thisHeroDataUri;
		this.heroName = heroName;
		this.useTimes = useTimes;
		Winning = winning;
		KDA = kDA;
		Kill = kill;
		Death = death;
		Assists = assists;
		this.allKAD = allKAD;
		this.gold_PerMin = gold_PerMin;
		this.xp_PerMin = xp_PerMin;
	}
	public HerosSatistics() {
		super();
		// TODO Auto-generated constructor stub
	}

	

}
