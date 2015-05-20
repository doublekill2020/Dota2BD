package com.badr.infodota.api.trackdota.core;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 17:44
 */
public class BanPick implements Serializable {
    @Expose
    @SerializedName("hero_id")
    private long heroId;

    public long getHeroId() {
        return heroId;
    }

    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }
}
