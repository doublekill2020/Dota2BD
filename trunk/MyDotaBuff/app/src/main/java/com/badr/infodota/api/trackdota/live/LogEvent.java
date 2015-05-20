package com.badr.infodota.api.trackdota.live;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ABadretdinov
 * 13.04.2015
 * 16:07
 */
public class LogEvent implements Serializable {
    @Expose
    private long timestamp;
    @Expose
    private long delta;
    @Expose
    private String action;
    @Expose
    @SerializedName("account_id")
    private long accountId;

    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getDelta() {
        return delta;
    }

    public void setDelta(long delta) {
        this.delta = delta;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
