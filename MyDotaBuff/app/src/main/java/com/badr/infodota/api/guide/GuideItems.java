package com.badr.infodota.api.guide;

import java.io.Serializable;

/**
 * User: Histler
 * Date: 19.01.14
 */
public class GuideItems implements Serializable {
    private String[] startingItems;
    private String[] startingItems_Secondary;
    private String[] earlyGame;
    private String[] earlyGame_Secondary;
    private String[] coreItems;
    private String[] coreItems_Secondary;
    private String[] luxury;

    public GuideItems() {
    }

    public String[] getStartingItems_Secondary() {
        return startingItems_Secondary;
    }

    public void setStartingItems_Secondary(String[] startingItems_Secondary) {
        this.startingItems_Secondary = startingItems_Secondary;
    }

    public String[] getEarlyGame_Secondary() {
        return earlyGame_Secondary;
    }

    public void setEarlyGame_Secondary(String[] earlyGame_Secondary) {
        this.earlyGame_Secondary = earlyGame_Secondary;
    }

    public String[] getCoreItems_Secondary() {
        return coreItems_Secondary;
    }

    public void setCoreItems_Secondary(String[] coreItems_Secondary) {
        this.coreItems_Secondary = coreItems_Secondary;
    }

    public String[] getStartingItems() {
        return startingItems;
    }

    public void setStartingItems(String[] startingItems) {
        this.startingItems = startingItems;
    }

    public String[] getEarlyGame() {
        return earlyGame;
    }

    public void setEarlyGame(String[] earlyGame) {
        this.earlyGame = earlyGame;
    }

    public String[] getCoreItems() {
        return coreItems;
    }

    public void setCoreItems(String[] coreItems) {
        this.coreItems = coreItems;
    }

    public String[] getLuxury() {
        return luxury;
    }

    public void setLuxury(String[] luxury) {
        this.luxury = luxury;
    }
}
