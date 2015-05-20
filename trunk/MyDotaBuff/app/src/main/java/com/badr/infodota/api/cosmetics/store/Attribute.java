package com.badr.infodota.api.cosmetics.store;

import com.google.gson.annotations.SerializedName;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 12:25
 */
public class Attribute {
    private String name;
    @SerializedName("class")
    private String class_;
    private int value;

    public Attribute() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
