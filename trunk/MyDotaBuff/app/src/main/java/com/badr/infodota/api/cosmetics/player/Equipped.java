package com.badr.infodota.api.cosmetics.player;

import com.google.gson.annotations.SerializedName;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 17:01
 */
public class Equipped {
    @SerializedName("class")
    private int classId;
    private int slot;

    public Equipped() {
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }
}
