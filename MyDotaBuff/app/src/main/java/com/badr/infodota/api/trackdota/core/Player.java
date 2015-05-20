package com.badr.infodota.api.trackdota.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 17:46
 */
public class Player implements Serializable {
    @Expose
    @SerializedName("hero_id")
    private long heroId;
    @Expose
    private String name;
    @Expose
    private long team;
    @Expose
    @SerializedName("account_id")
    private long accountId;

    public long getHeroId() {
        return heroId;
    }

    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTeam() {
        return team;
    }

    public void setTeam(long team) {
        this.team = team;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return name;
    }
}
