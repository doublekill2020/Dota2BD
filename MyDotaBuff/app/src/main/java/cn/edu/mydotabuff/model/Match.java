package cn.edu.mydotabuff.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tinker on 2017/6/29.
 */

public class Match extends RealmObject {
    //为accout_id+match_id组合
    @PrimaryKey
    public String id;
    public String match_id;
    public int player_slot;
    public boolean radiant_win;
    public long duration;
    public int game_mode;
    public int lobby_type;
    public int hero_id;
    public long start_time;
    public String version;
    public int kills;
    public int deaths;
    public int assists;
    public String skill;
    public int xp_per_min;
    public int gold_per_min;
    public int hero_damage;
    public int tower_damage;
    public int hero_healing;
    public int last_hits;
    public String account_id;
}
