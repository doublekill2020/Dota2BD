package com.badr.infodota.api.guide;

/**
 * User: ABadretdinov
 * Date: 28.01.14
 * Time: 12:57
 */
public class TitleOnly {
    private String Title;

    public TitleOnly() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    @Override
    public String toString() {
        return Title;
    }
}
