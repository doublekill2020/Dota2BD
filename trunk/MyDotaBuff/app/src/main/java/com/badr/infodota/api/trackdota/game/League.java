package com.badr.infodota.api.trackdota.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 14:10
 */
public class League implements Serializable {
    @Expose
    private long id;
    @Expose
    private String url;
    @Expose
    @SerializedName("image")
    private boolean hasImage;
    @Expose
    private String name;
    @Expose
    private long tier;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTier() {
        return tier;
    }

    public void setTier(long tier) {
        this.tier = tier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        League league = (League) o;

        if (id != league.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
