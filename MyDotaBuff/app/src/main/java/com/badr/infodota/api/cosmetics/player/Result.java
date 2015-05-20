package com.badr.infodota.api.cosmetics.player;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 17:46
 */
public class Result {
    private int status;
    private long num_backpack_slots;
    private List<PlayerCosmeticItem> items;

    public Result() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getNum_backpack_slots() {
        return num_backpack_slots;
    }

    public void setNum_backpack_slots(long num_backpack_slots) {
        this.num_backpack_slots = num_backpack_slots;
    }

    public List<PlayerCosmeticItem> getItems() {
        return items;
    }

    public void setItems(List<PlayerCosmeticItem> items) {
        this.items = items;
    }
}
