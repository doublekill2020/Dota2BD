package com.badr.infodota.api.cosmetics.price;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 16:24
 */
public class ItemPrice {
    private Map<String, Long> prices;
    private String name;
    private String date;
    @SerializedName("class")
    private List<ItemClass> itemClass;
    private String classid;

    public ItemPrice() {
    }

    public ItemPrice(String name) {
        this.name = name;
    }

    public Map<String, Long> getPrices() {
        return prices;
    }

    public void setPrices(Map<String, Long> prices) {
        this.prices = prices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ItemClass> getItemClass() {
        return itemClass;
    }

    public void setItemClass(List<ItemClass> itemClass) {
        this.itemClass = itemClass;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemPrice)) return false;

        ItemPrice itemPrice = (ItemPrice) o;

        return !(name != null ? !name.equals(itemPrice.name) : itemPrice.name != null);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
