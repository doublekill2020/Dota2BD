package com.badr.infodota.api.truepicker;

/**
 * User: ABadretdinov
 * Date: 20.02.14
 * Time: 17:19
 */
public class Counter {
    private String hero;
    private String name;
    private int score;

    public Counter() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
