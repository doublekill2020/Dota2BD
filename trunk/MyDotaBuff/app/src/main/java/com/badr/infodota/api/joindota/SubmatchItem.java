package com.badr.infodota.api.joindota;

import java.io.Serializable;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 22.04.14
 * Time: 14:31
 */
public class SubmatchItem implements Serializable {
    private String score;
    private List<String> team1bans;
    private List<String> team1picks;
    private List<String> team1playerNames;
    private List<String> team2bans;
    private List<String> team2picks;
    private List<String> team2playerNames;

    public SubmatchItem() {
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<String> getTeam1bans() {
        return team1bans;
    }

    public void setTeam1bans(List<String> team1bans) {
        this.team1bans = team1bans;
    }

    public List<String> getTeam1picks() {
        return team1picks;
    }

    public void setTeam1picks(List<String> team1picks) {
        this.team1picks = team1picks;
    }

    public List<String> getTeam1playerNames() {
        return team1playerNames;
    }

    public void setTeam1playerNames(List<String> team1playerNames) {
        this.team1playerNames = team1playerNames;
    }

    public List<String> getTeam2bans() {
        return team2bans;
    }

    public void setTeam2bans(List<String> team2bans) {
        this.team2bans = team2bans;
    }

    public List<String> getTeam2picks() {
        return team2picks;
    }

    public void setTeam2picks(List<String> team2picks) {
        this.team2picks = team2picks;
    }

    public List<String> getTeam2playerNames() {
        return team2playerNames;
    }

    public void setTeam2playerNames(List<String> team2playerNames) {
        this.team2playerNames = team2playerNames;
    }
}
