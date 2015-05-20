package com.badr.infodota.api.trackdota.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 14:16
 */
public class Game implements Serializable {
    @Expose
    private long id;
    @Expose
    private long spectators;
    @Expose
    private long streams;
    @Expose
    private League league;
    /*in seconds*/
    @Expose
    private long duration;
    @Expose
    @SerializedName("time_started")
    private long startTime;
    @Expose
    @SerializedName("radiant_score")
    private int radiantKills;
    @Expose
    @SerializedName("radiant_team")
    private Team radiant;
    @Expose
    @SerializedName("dire_score")
    private int direKills;
    @Expose
    @SerializedName("dire_team")
    private Team dire;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSpectators() {
        return spectators;
    }

    public void setSpectators(long spectators) {
        this.spectators = spectators;
    }

    public long getStreams() {
        return streams;
    }

    public void setStreams(long streams) {
        this.streams = streams;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getRadiantKills() {
        return radiantKills;
    }

    public void setRadiantKills(int radiantKills) {
        this.radiantKills = radiantKills;
    }

    public Team getRadiant() {
        return radiant;
    }

    public void setRadiant(Team radiant) {
        this.radiant = radiant;
    }

    public int getDireKills() {
        return direKills;
    }

    public void setDireKills(int direKills) {
        this.direKills = direKills;
    }

    public Team getDire() {
        return dire;
    }

    public void setDire(Team dire) {
        this.dire = dire;
    }
}
