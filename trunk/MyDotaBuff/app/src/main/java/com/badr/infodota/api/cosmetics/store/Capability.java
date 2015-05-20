package com.badr.infodota.api.cosmetics.store;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 12:22
 */
public class Capability {
    private boolean can_craft_mark;
    private boolean can_be_restored;
    private boolean strange_parts;
    private boolean paintable_unusual;
    private boolean autograph;
    private boolean can_consume;

    public Capability() {
    }

    public boolean isCan_craft_mark() {
        return can_craft_mark;
    }

    public void setCan_craft_mark(boolean can_craft_mark) {
        this.can_craft_mark = can_craft_mark;
    }

    public boolean isCan_be_restored() {
        return can_be_restored;
    }

    public void setCan_be_restored(boolean can_be_restored) {
        this.can_be_restored = can_be_restored;
    }

    public boolean isStrange_parts() {
        return strange_parts;
    }

    public void setStrange_parts(boolean strange_parts) {
        this.strange_parts = strange_parts;
    }

    public boolean isPaintable_unusual() {
        return paintable_unusual;
    }

    public void setPaintable_unusual(boolean paintable_unusual) {
        this.paintable_unusual = paintable_unusual;
    }

    public boolean isAutograph() {
        return autograph;
    }

    public void setAutograph(boolean autograph) {
        this.autograph = autograph;
    }

    public boolean isCan_consume() {
        return can_consume;
    }

    public void setCan_consume(boolean can_consume) {
        this.can_consume = can_consume;
    }
}
