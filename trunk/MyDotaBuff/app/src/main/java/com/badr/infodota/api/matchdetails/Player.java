package com.badr.infodota.api.matchdetails;

import com.badr.infodota.api.AbilityUpgrade;
import com.badr.infodota.api.matchhistory.ShortPlayer;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 14:57
 */
public class Player extends ShortPlayer implements Serializable {
    public static final long HIDDEN_ID = 4294967295L;
    //the numeric ID of the item that player finished with in their top-left slot.
    @SerializedName("item_0")
    private int item0;
    private String item0dotaId;
    //top-center
    @SerializedName("item_1")
    private int item1;
    private String item1dotaId;
    //top-right
    @SerializedName("item_2")
    private int item2;
    private String item2dotaId;
    //bottom-left
    @SerializedName("item_3")
    private int item3;
    private String item3dotaId;
    //bottom-center
    @SerializedName("item_4")
    private int item4;
    private String item4dotaId;
    //bottom-right
    @SerializedName("item_5")
    private int item5;
    private String item5dotaId;
    private int kills;
    private int deaths;
    private int assists;
    /*
    * NULL - player is a bot.
    * 3 - по ходу не пикнул.
    * 2 - player abandoned game.
    * 1 - player left game after the game has become safe to leave.
    * 0 - Player stayed for the entire match.
    * */
    @SerializedName("leaver_status")
    private Integer leaverStatus;
    //the amount of gold the player had left at the end of the match
    private long gold;
    @SerializedName("last_hits")
    private int lastHits;
    private int denies;
    @SerializedName("xp_per_min")
    private int xpPerMin;
    @SerializedName("gold_per_min")
    private int goldPerMin;
    @SerializedName("gold_spent")
    private long goldSpent;
    @SerializedName("hero_damage")
    private long heroDamage;
    @SerializedName("tower_damage")
    private long towerDamage;
    @SerializedName("hero_healing")
    private long heroHealing;
    // the player's final level
    private long level;
    //an array detailing the order in which a player's ability points were spent.
    @SerializedName("ability_upgrades")
    private List<AbilityUpgrade> abilityUpgrades;

    @SerializedName("additional_units")
    private List<AdditionalUnit> additionalUnits;

    private Long respawnTimer;

    private Integer ultState;

    private Integer ultCooldown;

    private Long netWorth;

    public Long getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(Long netWorth) {
        this.netWorth = netWorth;
    }

    public Integer getUltCooldown() {
        return ultCooldown;
    }

    public void setUltCooldown(Integer ultCooldown) {
        this.ultCooldown = ultCooldown;
    }

    public Integer getUltState() {
        return ultState;
    }

    public void setUltState(Integer ultState) {
        this.ultState = ultState;
    }

    public Long getRespawnTimer() {
        return respawnTimer;
    }

    public void setRespawnTimer(Long respawnTimer) {
        this.respawnTimer = respawnTimer;
    }

    public Player() {
        super();
    }

    public int getItem0() {
        return item0;
    }

    public void setItem0(int item0) {
        this.item0 = item0;
    }

    public int getItem1() {
        return item1;
    }

    public void setItem1(int item1) {
        this.item1 = item1;
    }

    public int getItem2() {
        return item2;
    }

    public void setItem2(int item2) {
        this.item2 = item2;
    }

    public int getItem3() {
        return item3;
    }

    public void setItem3(int item3) {
        this.item3 = item3;
    }

    public int getItem4() {
        return item4;
    }

    public void setItem4(int item4) {
        this.item4 = item4;
    }

    public int getItem5() {
        return item5;
    }

    public void setItem5(int item5) {
        this.item5 = item5;
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

    public Integer getLeaverStatus() {
        return leaverStatus;
    }

    public void setLeaverStatus(Integer leaverStatus) {
        this.leaverStatus = leaverStatus;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public int getLastHits() {
        return lastHits;
    }

    public void setLastHits(int lastHits) {
        this.lastHits = lastHits;
    }

    public int getDenies() {
        return denies;
    }

    public void setDenies(int denies) {
        this.denies = denies;
    }

    public int getGoldPerMin() {
        return goldPerMin;
    }

    public void setGoldPerMin(int goldPerMin) {
        this.goldPerMin = goldPerMin;
    }

    public int getXpPerMin() {
        return xpPerMin;
    }

    public void setXpPerMin(int xpPerMin) {
        this.xpPerMin = xpPerMin;
    }

    public long getGoldSpent() {
        return goldSpent;
    }

    public void setGoldSpent(long goldSpent) {
        this.goldSpent = goldSpent;
    }

    public long getHeroDamage() {
        return heroDamage;
    }

    public void setHeroDamage(long heroDamage) {
        this.heroDamage = heroDamage;
    }

    public long getTowerDamage() {
        return towerDamage;
    }

    public void setTowerDamage(long towerDamage) {
        this.towerDamage = towerDamage;
    }

    public long getHeroHealing() {
        return heroHealing;
    }

    public void setHeroHealing(long heroHealing) {
        this.heroHealing = heroHealing;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public List<AbilityUpgrade> getAbilityUpgrades() {
        return abilityUpgrades;
    }

    public void setAbilityUpgrades(List<AbilityUpgrade> abilityUpgrades) {
        this.abilityUpgrades = abilityUpgrades;
    }

    public List<AdditionalUnit> getAdditionalUnits() {
        return additionalUnits;
    }

    public void setAdditionalUnits(List<AdditionalUnit> additionalUnits) {
        this.additionalUnits = additionalUnits;
    }

    public String getItem0dotaId() {
        return item0dotaId;
    }

    public void setItem0dotaId(String item0dotaId) {
        this.item0dotaId = item0dotaId;
    }

    public String getItem1dotaId() {
        return item1dotaId;
    }

    public void setItem1dotaId(String item1dotaId) {
        this.item1dotaId = item1dotaId;
    }

    public String getItem2dotaId() {
        return item2dotaId;
    }

    public void setItem2dotaId(String item2dotaId) {
        this.item2dotaId = item2dotaId;
    }

    public String getItem3dotaId() {
        return item3dotaId;
    }

    public void setItem3dotaId(String item3dotaId) {
        this.item3dotaId = item3dotaId;
    }

    public String getItem4dotaId() {
        return item4dotaId;
    }

    public void setItem4dotaId(String item4dotaId) {
        this.item4dotaId = item4dotaId;
    }

    public String getItem5dotaId() {
        return item5dotaId;
    }

    public void setItem5dotaId(String item5dotaId) {
        this.item5dotaId = item5dotaId;
    }
}
