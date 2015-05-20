package com.badr.infodota.api.truepicker;

/**
 * User: Histler
 * Date: 01.09.14
 */
public class TruepickerResponse {
    private String recommendsforteama;
    private String recommendsforteamb;

    public TruepickerResponse() {
    }

    public String getRecommendsforteama() {
        return recommendsforteama;
    }

    public void setRecommendsforteama(String recommendsforteama) {
        this.recommendsforteama = recommendsforteama;
    }

    public String getRecommendsforteamb() {
        return recommendsforteamb;
    }

    public void setRecommendsforteamb(String recommendsforteamb) {
        this.recommendsforteamb = recommendsforteamb;
    }
}
