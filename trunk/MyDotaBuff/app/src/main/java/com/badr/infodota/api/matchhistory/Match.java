package com.badr.infodota.api.matchhistory;

import com.badr.infodota.api.matchdetails.Player;

import java.io.Serializable;
import java.util.List;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 14:58
 */
public class Match implements Serializable {
    private long match_id;
    //the match's sequence number - the order in which matches are recorded
    private long match_seq_num;
    // date in UTC seconds since Jan 1, 1970 (unix time format)
    private long start_time;
    /*
    * 8 = 1 vs 1
    * 7 = Ranking matchmaking
    * 6 = Solo Queue
    * 5 = Team match.
    * 4 = Co-op with bots.
    * 3 = Tutorial.
    * 2 = Tournament.
    * 1 = Practice.
    * 0 = Public matchmaking.
    * -1 = Invalid.
    * */
    private int lobby_type;

    private List<Player> players;

    public Match() {
        super();
    }

    public long getMatch_id() {
        return match_id;
    }

    public void setMatch_id(long match_id) {
        this.match_id = match_id;
    }

    public long getMatch_seq_num() {
        return match_seq_num;
    }

    public void setMatch_seq_num(long match_seq_num) {
        this.match_seq_num = match_seq_num;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public int getLobby_type() {
        return lobby_type;
    }

    public void setLobby_type(int lobby_type) {
        this.lobby_type = lobby_type;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
