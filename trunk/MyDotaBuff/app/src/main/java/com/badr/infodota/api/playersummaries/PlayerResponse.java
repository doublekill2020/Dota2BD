package com.badr.infodota.api.playersummaries;

import java.util.List;

/**
 * User: Histler
 * Date: 16.04.14
 */
public class PlayerResponse {
    private List<Player> players;

    public PlayerResponse() {
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
