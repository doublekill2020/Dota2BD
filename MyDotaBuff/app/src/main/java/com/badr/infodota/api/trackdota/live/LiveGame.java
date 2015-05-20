package com.badr.infodota.api.trackdota.live;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 16:22
 */
public class LiveGame implements Serializable {
    @Expose
    @SerializedName("is_paused")
    private boolean isPaused;

    @Expose
    @SerializedName("api_downtime")
    private long apiDowntime;

    @Expose
    @SerializedName("v")
    private long version;

    @Expose
    private long spectators;
    /*
        * 1- currently in pick&ban
        * 2 - waiting for creep spawn
        * 3 - in progress
        * 4 - finished
        * */
    @Expose
    private int status;

    @Expose
    private long duration;

    @Expose
    @SerializedName("tower_state")
    private int towerState;

    @Expose
    @SerializedName("barracks_state")
    private int barracksState;

    @Expose
    private long updated;

    @Expose
    @SerializedName("roshan_respawn_timer")
    private long roshanRespawnTimer;

    @Expose
    private LiveTeam dire;
    @Expose
    private LiveTeam radiant;
    @Expose
    private List<LogEvent> log;

    @Expose
    private Map<String,long[]> stats;

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public long getApiDowntime() {
        return apiDowntime;
    }

    public void setApiDowntime(long apiDowntime) {
        this.apiDowntime = apiDowntime;
    }

    public long getSpectators() {
        return spectators;
    }

    public void setSpectators(long spectators) {
        this.spectators = spectators;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTowerState() {
        return towerState;
    }

    public void setTowerState(int towerState) {
        this.towerState = towerState;
    }

    public int getBarracksState() {
        return barracksState;
    }

    public void setBarracksState(int barracksState) {
        this.barracksState = barracksState;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getRoshanRespawnTimer() {
        return roshanRespawnTimer;
    }

    public void setRoshanRespawnTimer(long roshanRespawnTimer) {
        this.roshanRespawnTimer = roshanRespawnTimer;
    }

    public LiveTeam getDire() {
        return dire;
    }

    public void setDire(LiveTeam dire) {
        this.dire = dire;
    }

    public LiveTeam getRadiant() {
        return radiant;
    }

    public void setRadiant(LiveTeam radiant) {
        this.radiant = radiant;
    }

    public List<LogEvent> getLog() {
        return log;
    }

    public void setLog(List<LogEvent> log) {
        this.log = log;
    }

    public Map<String, long[]> getStats() {
        return stats;
    }

    public void setStats(Map<String, long[]> stats) {
        this.stats = stats;
    }
}
