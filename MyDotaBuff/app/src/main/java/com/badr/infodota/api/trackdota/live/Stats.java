package com.badr.infodota.api.trackdota.live;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 16:04
 */
public class Stats implements Serializable {
    @Expose
    @SerializedName("net_gold")
    private long[] netGold;
    @Expose
    @SerializedName("net_xp")
    private long[] netExp;
    @Expose
    private long[] kills;
    @Expose
    private long[] gpm;
    @Expose
    private long[] xpm;

    public long[] getNetGold() {
        return netGold;
    }

    public void setNetGold(long[] netGold) {
        this.netGold = netGold;
    }

    public long[] getNetExp() {
        return netExp;
    }

    public void setNetExp(long[] netExp) {
        this.netExp = netExp;
    }

    public long[] getKills() {
        return kills;
    }

    public void setKills(long[] kills) {
        this.kills = kills;
    }

    public long[] getGpm() {
        return gpm;
    }

    public void setGpm(long[] gpm) {
        this.gpm = gpm;
    }

    public long[] getXpm() {
        return xpm;
    }

    public void setXpm(long[] xpm) {
        this.xpm = xpm;
    }
}
