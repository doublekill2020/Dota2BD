package com.badr.infodota.api.trackdota.live;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 15:19
 */
public class LivePlayer implements Serializable {
    @Expose
    @SerializedName("hero_id")
    private long heroId;

    @Expose
    @SerializedName("ultimate_cooldown")
    private int ultCooldown;
    /*
    * 0 - not levelled
    * 1 - has ult (mb on cooldown)
    * 3 - no mana
    * */
    @Expose
    @SerializedName("ultimate_state")
    private int ultState;
    @Expose
    @SerializedName("player_slot")
    private int playerSlot;
    @Expose
    private List<Ability> abilities;
    @Expose
    private int level;
    @Expose
    @SerializedName("position_x")
    private long positionX;
    @Expose
    @SerializedName("position_y")
    private long positionY;
    @Expose
    private int kills;
    @Expose
    private int death;
    @Expose
    private int assists;
    @Expose
    @SerializedName("account_id")
    private long accountId;
    @Expose
    @SerializedName("items")
    private long[] itemIds;

    @Expose
    @SerializedName("last_hits")
    private int lastHits;
    @Expose
    private int denies;

    @Expose
    @SerializedName("net_worth")
    private long netWorth;

    @Expose
    private long gold;
    @Expose
    @SerializedName("xp_per_min")
    private int xpm;
    @Expose
    @SerializedName("gold_per_min")
    private int gpm;
    @Expose
    @SerializedName("respawn_timer")
    private long respawnTimer;

    public long getHeroId() {
        return heroId;
    }

    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }

    public int getUltCooldown() {
        return ultCooldown;
    }

    public void setUltCooldown(int ultCooldown) {
        this.ultCooldown = ultCooldown;
    }

    public int getUltState() {
        return ultState;
    }

    public void setUltState(int ultState) {
        this.ultState = ultState;
    }

    public int getPlayerSlot() {
        return playerSlot;
    }

    public void setPlayerSlot(int playerSlot) {
        this.playerSlot = playerSlot;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getPositionX() {
        return positionX;
    }

    public void setPositionX(long positionX) {
        this.positionX = positionX;
    }

    public long getPositionY() {
        return positionY;
    }

    public void setPositionY(long positionY) {
        this.positionY = positionY;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long[] getItemIds() {
        return itemIds;
    }

    public void setItemIds(long[] itemIds) {
        this.itemIds = itemIds;
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

    public long getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(long netWorth) {
        this.netWorth = netWorth;
    }

    public long getGold() {
        return gold;
    }

    public void setGold(long gold) {
        this.gold = gold;
    }

    public int getXpm() {
        return xpm;
    }

    public void setXpm(int xpm) {
        this.xpm = xpm;
    }

    public int getGpm() {
        return gpm;
    }

    public void setGpm(int gpm) {
        this.gpm = gpm;
    }

    public long getRespawnTimer() {
        return respawnTimer;
    }

    public void setRespawnTimer(long respawnTimer) {
        this.respawnTimer = respawnTimer;
    }
}
