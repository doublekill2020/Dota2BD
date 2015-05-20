package com.badr.infodota.api.trackdota.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 14:49
 */
public class EnhancedMatch implements Serializable {
    @Expose
    private int tier;
    @Expose
    private long id;
    @Expose
    private List<EnhancedGame> games;
    @Expose
    @SerializedName("image")
    private boolean hasImage;
    @Expose
    private String url;
    @Expose
    private String name;

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<EnhancedGame> getGames() {
        return games;
    }

    public void setGames(List<EnhancedGame> games) {
        this.games = games;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
