package com.badr.infodota.api.matchdetails;

import com.badr.infodota.util.HasId;

/**
 * Created by ABadretdinov
 * 26.12.2014
 * 18:25
 */
public class Team implements HasId {
    private long id;
    private long teamLogoId;
    private String logo;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getTeamLogoId() {
        return teamLogoId;
    }

    public void setTeamLogoId(long teamLogoId) {
        this.teamLogoId = teamLogoId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
