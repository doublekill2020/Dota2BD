package com.badr.infodota.api.cosmetics.storeUrl;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ABadretdinov
 * 30.12.2014
 * 14:30
 */
public class Result {
    private int status;
    @SerializedName("items_game_url")
    private String url;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
