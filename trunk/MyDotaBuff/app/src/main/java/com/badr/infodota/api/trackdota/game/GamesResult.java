package com.badr.infodota.api.trackdota.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 14:52
 */
public class GamesResult implements Serializable {
    @Expose
    @SerializedName("finished_matches")
    private List<Game> finishedGames;
    @Expose
    @SerializedName("enhanced_matches")
    private List<EnhancedMatch> enhancedMatches;
    @Expose
    @SerializedName("recent_matches")
    private List<Game> recentGames;

    @Expose
    @SerializedName("api_downtime")
    private long apiDowntime;
    //not needed
    @Expose
    private String notice;
    @Expose
    private long version;

    public List<Game> getFinishedGames() {
        return finishedGames;
    }

    public void setFinishedGames(List<Game> finishedGames) {
        this.finishedGames = finishedGames;
    }

    public List<EnhancedMatch> getEnhancedMatches() {
        return enhancedMatches;
    }

    public void setEnhancedMatches(List<EnhancedMatch> enhancedMatches) {
        this.enhancedMatches = enhancedMatches;
    }

    public List<Game> getRecentGames() {
        return recentGames;
    }

    public void setRecentGames(List<Game> recentGames) {
        this.recentGames = recentGames;
    }

    public long getApiDowntime() {
        return apiDowntime;
    }

    public void setApiDowntime(long apiDowntime) {
        this.apiDowntime = apiDowntime;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
