package com.badr.infodota.api.cosmetics.player;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 16:52
 */
public class Attribute {
    private long defindex;
    private Object value;
    private float float_value;

    public Attribute() {
    }

    public long getDefindex() {
        return defindex;
    }

    public void setDefindex(long defindex) {
        this.defindex = defindex;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public float getFloat_value() {
        return float_value;
    }

    public void setFloat_value(float float_value) {
        this.float_value = float_value;
    }
}
