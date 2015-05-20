package com.badr.infodota.api.matchhistory;

/**
 * Created by Badr on 22.03.2015.
 */
public class PlayerMatchResult {
    private PlayerMatch.List playerMatches;
    private long totalMatches;
    private int status;
    private String statusDetails;

    public String getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(String statusDetails) {
        this.statusDetails = statusDetails;
    }

    public PlayerMatch.List getPlayerMatches() {
        return playerMatches;
    }

    public void setPlayerMatches(PlayerMatch.List playerMatches) {
        this.playerMatches = playerMatches;
    }

    public long getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(long totalMatches) {
        this.totalMatches = totalMatches;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
