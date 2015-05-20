package com.badr.infodota.api.trackdota.core;

import com.badr.infodota.api.trackdota.game.League;
import com.badr.infodota.api.trackdota.game.Team;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 18:06
 */
public class CoreResult implements Serializable {
    @Expose
    @SerializedName("api_downtime")
    private long apiDowntime;
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
    @SerializedName("series_type")
    private int seriesType;

    @Expose
    @SerializedName("time_started")
    private long startTime;

    @Expose
    private long duration;

    @Expose
    private long id;

    @Expose
    @SerializedName("radiant_team")
    private Team radiant;

    @Expose
    @SerializedName("dire_team")
    private Team dire;

    @Expose
    @SerializedName("radiant_bans")
    private List<BanPick> radiantBans;

    @Expose
    @SerializedName("radiant_picks")
    private List<BanPick> radiantPicks;

    @Expose
    @SerializedName("dire_bans")
    private List<BanPick> direBans;

    @Expose
    @SerializedName("dire_picks")
    private List<BanPick> direPicks;

    @Expose
    @SerializedName("radiant_series_wins")
    private int radiantWins;

    @Expose
    @SerializedName("dire_series_wins")
    private int direWins;

    @Expose
    private League league;

    @Expose
    private List<Player> players;

    @Expose
    private boolean started;
    @Expose
    @SerializedName("league_tier")
    private int leagueTier;


    public long getApiDowntime() {
        return apiDowntime;
    }

    public void setApiDowntime(long apiDowntime) {
        this.apiDowntime = apiDowntime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Team getRadiant() {
        return radiant;
    }

    public void setRadiant(Team radiant) {
        this.radiant = radiant;
    }

    public Team getDire() {
        return dire;
    }

    public void setDire(Team dire) {
        this.dire = dire;
    }

    public List<BanPick> getRadiantBans() {
        return radiantBans;
    }

    public void setRadiantBans(List<BanPick> radiantBans) {
        this.radiantBans = radiantBans;
    }

    public List<BanPick> getRadiantPicks() {
        return radiantPicks;
    }

    public void setRadiantPicks(List<BanPick> radiantPicks) {
        this.radiantPicks = radiantPicks;
    }

    public List<BanPick> getDireBans() {
        return direBans;
    }

    public void setDireBans(List<BanPick> direBans) {
        this.direBans = direBans;
    }

    public List<BanPick> getDirePicks() {
        return direPicks;
    }

    public void setDirePicks(List<BanPick> direPicks) {
        this.direPicks = direPicks;
    }

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

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public long getSpectators() {
        return spectators;
    }

    public void setSpectators(long spectators) {
        this.spectators = spectators;
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public int getLeagueTier() {
        return leagueTier;
    }

    public void setLeagueTier(int leagueTier) {
        this.leagueTier = leagueTier;
    }

}
