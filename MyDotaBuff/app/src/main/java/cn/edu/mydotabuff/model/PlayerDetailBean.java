package cn.edu.mydotabuff.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerDetailBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	private String account_id;
	private int hero_id;
	private int item_0;
	private int item_1;
	private int item_2;
	private int item_3;
	private int item_4;
	private int item_5;
	private int kills;
	private int deaths;
	private int assists;
	private int leaver_status;
	private int gold;
	private int last_hits;
	private int denies;
	private int gold_per_min;
	private int xp_per_min;
	private int gold_spent;
	private int hero_damage;
	private int tower_damage;
	private int hero_healing;
	private int level;
	private ArrayList<AbilityBean> ability_upgrades;
	private PlayerInfoBean playerInfoBeans;

	public PlayerDetailBean(String account_id, int hero_id, int item_0,
			int item_1, int item_2, int item_3, int item_4, int item_5,
			int kills, int deaths, int assists, int leaver_status, int gold,
			int last_hits, int denies, int gold_per_min, int xp_per_min,
			int gold_spent, int hero_damage, int tower_damage,
			int hero_healing, int level,
			ArrayList<AbilityBean> ability_upgrades,
			PlayerInfoBean playerInfoBeans) {
		super();
		this.account_id = account_id;
		this.hero_id = hero_id;
		this.item_0 = item_0;
		this.item_1 = item_1;
		this.item_2 = item_2;
		this.item_3 = item_3;
		this.item_4 = item_4;
		this.item_5 = item_5;
		this.kills = kills;
		this.deaths = deaths;
		this.assists = assists;
		this.leaver_status = leaver_status;
		this.gold = gold;
		this.last_hits = last_hits;
		this.denies = denies;
		this.gold_per_min = gold_per_min;
		this.xp_per_min = xp_per_min;
		this.gold_spent = gold_spent;
		this.hero_damage = hero_damage;
		this.tower_damage = tower_damage;
		this.hero_healing = hero_healing;
		this.level = level;
		this.ability_upgrades = ability_upgrades;
		this.playerInfoBeans = playerInfoBeans;
	}

	public PlayerInfoBean getPlayerInfoBeans() {
		return playerInfoBeans;
	}

	public void setPlayerInfoBeans(PlayerInfoBean playerInfoBeans) {
		this.playerInfoBeans = playerInfoBeans;
	}

	public PlayerDetailBean() {
	}

	public PlayerDetailBean(String account_id, int hero_id, int item_0,
			int item_1, int item_2, int item_3, int item_4, int item_5,
			int kills, int deaths, int assists, int leaver_status, int gold,
			int last_hits, int denies, int gold_per_min, int xp_per_min,
			int gold_spent, int hero_damage, int tower_damage,
			int hero_healing, int level, ArrayList<AbilityBean> ability_upgrades) {
		super();
		this.account_id = account_id;
		this.hero_id = hero_id;
		this.item_0 = item_0;
		this.item_1 = item_1;
		this.item_2 = item_2;
		this.item_3 = item_3;
		this.item_4 = item_4;
		this.item_5 = item_5;
		this.kills = kills;
		this.deaths = deaths;
		this.assists = assists;
		this.leaver_status = leaver_status;
		this.gold = gold;
		this.last_hits = last_hits;
		this.denies = denies;
		this.gold_per_min = gold_per_min;
		this.xp_per_min = xp_per_min;
		this.gold_spent = gold_spent;
		this.hero_damage = hero_damage;
		this.tower_damage = tower_damage;
		this.hero_healing = hero_healing;
		this.level = level;
		this.ability_upgrades = ability_upgrades;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public int getItem_0() {
		return item_0;
	}

	public void setItem_0(int item_0) {
		this.item_0 = item_0;
	}

	public int getItem_1() {
		return item_1;
	}

	public void setItem_1(int item_1) {
		this.item_1 = item_1;
	}

	public int getItem_2() {
		return item_2;
	}

	public void setItem_2(int item_2) {
		this.item_2 = item_2;
	}

	public int getItem_3() {
		return item_3;
	}

	public void setItem_3(int item_3) {
		this.item_3 = item_3;
	}

	public int getItem_4() {
		return item_4;
	}

	public void setItem_4(int item_4) {
		this.item_4 = item_4;
	}

	public int getItem_5() {
		return item_5;
	}

	public void setItem_5(int item_5) {
		this.item_5 = item_5;
	}

	public int getLeaver_status() {
		return leaver_status;
	}

	public void setLeaver_status(int leaver_status) {
		this.leaver_status = leaver_status;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getLast_hits() {
		return last_hits;
	}

	public void setLast_hits(int last_hits) {
		this.last_hits = last_hits;
	}

	public int getDenies() {
		return denies;
	}

	public void setDenies(int denies) {
		this.denies = denies;
	}

	public int getGold_spent() {
		return gold_spent;
	}

	public void setGold_spent(int gold_spent) {
		this.gold_spent = gold_spent;
	}

	public int getHero_damage() {
		return hero_damage;
	}

	public void setHero_damage(int hero_damage) {
		this.hero_damage = hero_damage;
	}

	public int getTower_damage() {
		return tower_damage;
	}

	public void setTower_damage(int tower_damage) {
		this.tower_damage = tower_damage;
	}

	public int getHero_healing() {
		return hero_healing;
	}

	public void setHero_healing(int hero_healing) {
		this.hero_healing = hero_healing;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public ArrayList<AbilityBean> getAbility_upgrades() {
		return ability_upgrades;
	}

	public void setAbility_upgrades(ArrayList<AbilityBean> ability_upgrades) {
		this.ability_upgrades = ability_upgrades;
	}

	public int getHero_id() {
		return hero_id;
	}

	public void setHero_id(int hero_id) {
		this.hero_id = hero_id;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}

	public int getGold_per_min() {
		return gold_per_min;
	}

	public void setGold_per_min(int gold_per_min) {
		this.gold_per_min = gold_per_min;
	}

	public int getXp_per_min() {
		return xp_per_min;
	}

	public void setXp_per_min(int xp_per_min) {
		this.xp_per_min = xp_per_min;
	}

}
