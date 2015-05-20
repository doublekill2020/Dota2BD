package com.badr.infodota.api.trackdota.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 14:08
 */
public class Team implements Serializable {
    @Expose
    @SerializedName("team_id")
    private long id;
    @Expose
    @SerializedName("team_tag")
    private String tag;
    @Expose
    @SerializedName("team_name")
    private String name;
    @Expose
    private String country;
    @Expose
    @SerializedName("has_logo")
    private boolean hasLogo;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isHasLogo() {
        return hasLogo;
    }

    public void setHasLogo(boolean hasLogo) {
        this.hasLogo = hasLogo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
