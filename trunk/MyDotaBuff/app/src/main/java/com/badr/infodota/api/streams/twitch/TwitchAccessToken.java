package com.badr.infodota.api.streams.twitch;

/**
 * User: Histler
 * Date: 27.02.14
 */
public class TwitchAccessToken {
    private String token;
    private String sig;

    public TwitchAccessToken() {
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
