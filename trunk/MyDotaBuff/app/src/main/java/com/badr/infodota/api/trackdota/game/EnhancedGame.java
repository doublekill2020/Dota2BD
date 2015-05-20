package com.badr.infodota.api.trackdota.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 14:32
 */
public class EnhancedGame extends Game {
    @Expose
    @SerializedName("radiant_series_wins")
    private int radiantWins;
    @Expose
    @SerializedName("dire_series_wins")
    private int direWins;
    /*
    * 0 - bo1
    * 1 - bo3
    * 2 - bo5
    * 3 - bo7?
    * where is bo2?
    *
    * */
    @Expose
    @SerializedName("series_type")
    private int seriesType;
    /*
    * 1- currently in pick&ban
    * 2 - waiting for creep spawn
    * 3 - in progress
    * 4 - finished
    * */
    @Expose
    private int status;
    @Expose
    @SerializedName("diff_gold")
    private long[] goldDifferences;
    @Expose
    @SerializedName("diff_xp")
    private long[] expDifferences;
    @Expose
    @SerializedName("barracks_state")
    private long barracks_state;
    @Expose
    @SerializedName("tower_state")
    private long towerState;
    @Expose
    private boolean started;
    @Expose
    @SerializedName("stream_providers")
    private String[] streamProviders;

    public int getRadiantWins() {
        return radiantWins;
    }

    public void setRadiantWins(int radiantWins) {
        this.radiantWins = radiantWins;
    }

    public int getDireWins() {
        return direWins;
    }

    public void setDireWins(int direWins) {
        this.direWins = direWins;
    }

    public int getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(int seriesType) {
        this.seriesType = seriesType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long[] getGoldDifferences() {
        return goldDifferences;
    }

    public void setGoldDifferences(long[] goldDifferences) {
        this.goldDifferences = goldDifferences;
    }

    public long[] getExpDifferences() {
        return expDifferences;
    }

    public void setExpDifferences(long[] expDifferences) {
        this.expDifferences = expDifferences;
    }

    public long getBarracks_state() {
        return barracks_state;
    }

    public void setBarracks_state(long barracks_state) {
        this.barracks_state = barracks_state;
    }

    public long getTowerState() {
        return towerState;
    }

    public void setTowerState(long towerState) {
        this.towerState = towerState;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public String[] getStreamProviders() {
        return streamProviders;
    }

    public void setStreamProviders(String[] streamProviders) {
        this.streamProviders = streamProviders;
    }
}
