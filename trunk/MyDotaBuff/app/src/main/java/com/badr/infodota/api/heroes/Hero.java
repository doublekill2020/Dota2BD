package com.badr.infodota.api.heroes;

import com.badr.infodota.util.HasId;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * User: ABadretdinov
 * Date: 28.08.13
 * Time: 16:31
 */
public class Hero implements HasId, Comparable,Serializable {
    //the hero's in-game "code name"
    private String name;
    //the hero's numeric ID
    private long id;
    // the hero's text name (language specific result - this field is not present if no language is specified)
    @SerializedName("localized_name")
    private String localizedName;

    private HeroStats stats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getDotaId() {
        return name.split("npc_dota_hero_")[1];
    }

    public HeroStats getStats() {
        return stats;
    }

    public void setStats(HeroStats stats) {
        this.stats = stats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hero hero = (Hero) o;

        if (id != hero.id) return false;
        if (name != null ? !name.equals(hero.name) : hero.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (int) id;
        return result;
    }

    @Override
    public String toString() {
        return localizedName;
    }

    @Override
    public int compareTo(Object another) {
        if (another instanceof Hero) {
            Hero hero = (Hero) another;
            return getLocalizedName().compareTo(hero.getLocalizedName());
        }
        return -1;
    }
    public static class List extends ArrayList<Hero>{
        public List(Collection<? extends Hero> collection) {
            super(collection);
        }
    }
}
