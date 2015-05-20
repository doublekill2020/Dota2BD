package com.badr.infodota.api.cosmetics.store;

import java.io.Serializable;
import java.util.Map;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 12:20
 */
public class CosmeticItem implements Serializable {
    private String name;
    private long defindex;
    private String item_class;
    private String item_type_name;
    private String item_name;
    //private boolean propep_name;
    private int item_quality;
    private String image_inventory;
    private String item_set;
    //private int min_ilevel;
    //private int max_ilevel;
    private String image_url;
    private String image_url_large;
    private String item_description;
    private Map<String, Long> prices;
    //или map из boolean?
    //private Capability capabilities;
    //private Tool tool;
    //private List<Attribute> attributes;

    public CosmeticItem() {
    }

    public CosmeticItem(long defindex) {
        this.defindex = defindex;
    }

    public CosmeticItem(String name) {
        this.name = name;
    }

    public String getItem_class() {
        return item_class;
    }

    public void setItem_class(String item_class) {
        this.item_class = item_class;
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

    public long getDefindex() {
        return defindex;
    }

    public void setDefindex(long defindex) {
        this.defindex = defindex;
    }

    public String getItem_type_name() {
        return item_type_name;
    }

    public void setItem_type_name(String item_type_name) {
        this.item_type_name = item_type_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_quality() {
        return item_quality;
    }

    public void setItem_quality(int item_quality) {
        this.item_quality = item_quality;
    }

    public String getImage_inventory() {
        return image_inventory;
    }

    public void setImage_inventory(String image_inventory) {
        this.image_inventory = image_inventory;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_url_large() {
        return image_url_large;
    }

    public void setImage_url_large(String image_url_large) {
        this.image_url_large = image_url_large;
    }

    public String getItem_set() {
        return item_set;
    }

    public void setItem_set(String item_set) {
        this.item_set = item_set;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CosmeticItem)) return false;

        CosmeticItem item = (CosmeticItem) o;
        if ((item.getName() != null && name != null && item.getName().equals(name))
                || (item.getDefindex() != 0 && defindex != 0 && item.getDefindex() == defindex))
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
