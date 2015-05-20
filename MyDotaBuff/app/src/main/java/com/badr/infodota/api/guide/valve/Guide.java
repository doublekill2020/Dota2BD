package com.badr.infodota.api.guide.valve;

import com.badr.infodota.api.guide.GuideItems;

/**
 * User: Histler
 * Date: 19.01.14
 */
public class Guide {
    private String author;
    private String hero;
    private String Title;
    private GuideItems Items;

    public Guide() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getHero() {
        return hero;
    }

    public void setHero(String hero) {
        this.hero = hero;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public GuideItems getItems() {
        return Items;
    }

    public void setItems(GuideItems items) {
        Items = items;
    }
}
