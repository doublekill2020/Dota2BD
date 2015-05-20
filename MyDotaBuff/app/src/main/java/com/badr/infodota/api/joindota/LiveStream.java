package com.badr.infodota.api.joindota;

import java.io.Serializable;

/**
 * User: ABadretdinov
 * Date: 22.04.14
 * Time: 16:54
 */
public class LiveStream implements Serializable {
    private String name;
    private String url;
    private String channelName;
    private String language;
    private String viewers;
    private String status;

    public LiveStream() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getViewers() {
        return viewers;
    }

    public void setViewers(String viewers) {
        this.viewers = viewers;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
