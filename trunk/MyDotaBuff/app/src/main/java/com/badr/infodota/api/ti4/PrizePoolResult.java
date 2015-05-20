package com.badr.infodota.api.ti4;

/**
 * User: ABadretdinov
 * Date: 14.05.14
 * Time: 18:46
 */
public class PrizePoolResult {
    private long prize_pool;
    private long league_id;

    public PrizePoolResult() {
    }

    public long getPrize_pool() {
        return prize_pool;
    }

    public void setPrize_pool(long prize_pool) {
        this.prize_pool = prize_pool;
    }

    public long getLeague_id() {
        return league_id;
    }

    public void setLeague_id(long league_id) {
        this.league_id = league_id;
    }
}
