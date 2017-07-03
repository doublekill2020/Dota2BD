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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public int getPlayer_slot() {
        return player_slot;
    }

    public void setPlayer_slot(int player_slot) {
        this.player_slot = player_slot;
    }

    public boolean isRadiant_win() {
        return radiant_win;
    }

    public void setRadiant_win(boolean radiant_win) {
        this.radiant_win = radiant_win;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getGame_mode() {
        return game_mode;
    }

    public void setGame_mode(int game_mode) {
        this.game_mode = game_mode;
    }

    public int getLobby_type() {
        return lobby_type;
    }

    public void setLobby_type(int lobby_type) {
        this.lobby_type = lobby_type;
    }

    public int getHero_id() {
        return hero_id;
    }

    public void setHero_id(int hero_id) {
        this.hero_id = hero_id;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getXp_per_min() {
        return xp_per_min;
    }

    public void setXp_per_min(int xp_per_min) {
        this.xp_per_min = xp_per_min;
    }

    public int getGold_per_min() {
        return gold_per_min;
    }

    public void setGold_per_min(int gold_per_min) {
        this.gold_per_min = gold_per_min;
    }

    public int getHero_damage() {
        return hero_damage;
    }

    public void setHero_damage(int hero_damage) {
        this.hero_damage = hero_damage;
    }

    public int getTower_damage() {
        return tower_damage;
    }

    public void setTower_damage(int tower_damage) {
        this.tower_damage = tower_damage;
    }

    public int getHero_healing() {
        return hero_healing;
    }

    public void setHero_healing(int hero_healing) {
        this.hero_healing = hero_healing;
    }

    public int getLast_hits() {
        return last_hits;
    }

    public void setLast_hits(int last_hits) {
        this.last_hits = last_hits;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }
}
