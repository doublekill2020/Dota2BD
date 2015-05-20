package com.badr.infodota.api.matchdetails;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 22.04.14
 * Time: 13:44
 */
public class PickBan implements Serializable {
    private boolean is_pick;
    private int hero_id;
    private int team;
    private int order;

    public PickBan() {
    }

    public boolean isIs_pick() {
        return is_pick;
    }

    public void setIs_pick(boolean is_pick) {
        this.is_pick = is_pick;
    }

    public int getHero_id() {
        return hero_id;
    }

    public void setHero_id(int hero_id) {
        this.hero_id = hero_id;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
