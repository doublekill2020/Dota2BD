package com.badr.infodota.api.matchhistory;

import com.badr.infodota.api.matchdetails.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Badr on 22.03.2015.
 */
public class PlayerMatch implements Serializable{
    private long matchId;
    private int lobbyType;
    private Player player;
    private Date gameTime;

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public int getLobbyType() {
        return lobbyType;
    }

    public void setLobbyType(int lobbyType) {
        this.lobbyType = lobbyType;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Date getGameTime() {
        return gameTime;
    }

    public void setGameTime(Date gameTime) {
        this.gameTime = gameTime;
    }

    public static class List extends ArrayList<PlayerMatch>{}
}
