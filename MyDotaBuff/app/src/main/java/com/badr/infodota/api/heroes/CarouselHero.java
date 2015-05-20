package com.badr.infodota.api.heroes;

import java.util.ArrayList;

/**
 * User: ABadretdinov
 * Date: 15.04.14
 * Time: 15:10
 */
public class CarouselHero extends Hero {
    private String dotaId;
    private int PrimaryStat;
    private String[] skills;

    public CarouselHero() {
    }

    public CarouselHero(Hero hero) {
        setId(hero.getId());
        setName(hero.getName());
        setLocalizedName(hero.getLocalizedName());
    }

    public void setName(String name) {
        super.setName(name);
        this.dotaId = name.split("npc_dota_hero_")[1];
    }

    public int getPrimaryStat() {
        return PrimaryStat;
    }

    public void setPrimaryStat(int primaryStat) {
        PrimaryStat = primaryStat;
    }

    @Override
    public String getDotaId() {
        return dotaId;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarouselHero hero = (CarouselHero) o;

        if (getId() != hero.getId()) return false;
        if (dotaId != null ? !dotaId.equals(hero.dotaId) : hero.dotaId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dotaId != null ? dotaId.hashCode() : 0;
        result = 31 * result + (int) getId();
        return result;
    }
    public static class List extends ArrayList<CarouselHero>{

    }
}
