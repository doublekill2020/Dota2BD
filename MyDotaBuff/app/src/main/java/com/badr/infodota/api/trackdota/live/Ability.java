package com.badr.infodota.api.trackdota.live;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 15:20
 */
public class Ability implements Serializable {
    @Expose
    @SerializedName("ability_id")
    private long id;
    @Expose
    @SerializedName("ability_level")
    private int level;
    @Expose
    private String name;
    @Expose
    private int[] build;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getBuild() {
        return build;
    }

    public void setBuild(int[] build) {
        this.build = build;
    }
}
