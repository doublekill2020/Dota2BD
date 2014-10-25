package cn.edu.mydotabuff.bean;

public class HerosSatistics {
	private String thisHeroData;//英雄数据合集
	private String heroName; //英雄名字
	private int useTimes; // 使用次数
	private Double Winning; // 胜率
	private Double KDA; // KDA
	private Double Kill; // 场均杀人数
	private Double Death;//场均死亡数
	private Double Assists; //场均助攻数
	private Double allKAD; //所有人KDA
	private Double gold_PerMin; // 金钱每分钟
	private Double xp_PerMin; //经验每分钟
	public String getThisHeroData() {
		return thisHeroData;
	}
	public void setThisHeroData(String thisHeroData) {
		this.thisHeroData = thisHeroData;
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
	public Double getWinning() {
		return Winning;
	}
	public void setWinning(Double winning) {
		Winning = winning;
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
	public Double getAllKAD() {
		return allKAD;
	}
	public void setAllKAD(Double allKAD) {
		this.allKAD = allKAD;
	}
	public Double getGold_PerMin() {
		return gold_PerMin;
	}
	public void setGold_PerMin(Double gold_PerMin) {
		this.gold_PerMin = gold_PerMin;
	}
	public Double getXp_PerMin() {
		return xp_PerMin;
	}
	public void setXp_PerMin(Double xp_PerMin) {
		this.xp_PerMin = xp_PerMin;
	}
	@Override
	public String toString() {
		return "HerosSatistics [thisHeroData=" + thisHeroData + ", heroName="
				+ heroName + ", useTimes=" + useTimes + ", Winning=" + Winning
				+ ", KDA=" + KDA + ", Kill=" + Kill + ", Death=" + Death
				+ ", Assists=" + Assists + ", allKAD=" + allKAD
				+ ", gold_PerMin=" + gold_PerMin + ", xp_PerMin=" + xp_PerMin
				+ "]";
	}
	public HerosSatistics(String thisHeroData, String heroName, int useTimes,
			Double winning, Double kDA, Double kill, Double death,
			Double assists, Double allKAD, Double gold_PerMin, Double xp_PerMin) {
		super();
		this.thisHeroData = thisHeroData;
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
