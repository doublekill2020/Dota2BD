package com.badr.infodota.api.heroes;

import java.util.ArrayList;

/**
 * Created by ABadretdinov
 * 26.12.2014
 * 15:20
 */
public class TruepickerHero extends Hero {
    private long tpId;

    public TruepickerHero(Hero hero) {
        setId(hero.getId());
        setName(hero.getName());
        setLocalizedName(hero.getLocalizedName());
    }

    public TruepickerHero() {
    }

    public long getTpId() {
        return tpId;
    }

    public void setTpId(long tpId) {
        this.tpId = tpId;
    }

    public static class List extends ArrayList<TruepickerHero>{

    }
}
