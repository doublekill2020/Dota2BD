package com.badr.infodota.api.cosmetics.store;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 18:04
 */
public class ItemSet {
    private String item_set;
    private String name;
    private String store_bundle;
    private List<String> items;

    public ItemSet() {
    }

    public ItemSet(String item_set, String store_bundle) {
        this.item_set = item_set;
        this.store_bundle = store_bundle;
    }

    public String getItem_set() {
        return item_set;
    }

    public void setItem_set(String item_set) {
        this.item_set = item_set;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore_bundle() {
        return store_bundle;
    }

    public void setStore_bundle(String store_bundle) {
        this.store_bundle = store_bundle;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemSet)) return false;

        ItemSet itemSet = (ItemSet) o;
        /*if(itemSet.getItem_set()!=null &&item_set!=null &&!itemSet.getItem_set().equals(item_set)) return false;
		if(itemSet.getStore_bundle()!=null&&store_bundle!=null &&!itemSet.getStore_bundle().equals(store_bundle)) return false;
		return true;*/

        if ((itemSet.getItem_set() != null && item_set != null && itemSet.getItem_set().equals(item_set))
                || (itemSet.getStore_bundle() != null && store_bundle != null && itemSet.getStore_bundle().equals(store_bundle)))
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        int result = item_set != null ? item_set.hashCode() : 0;
        result = 31 * result + (store_bundle != null ? store_bundle.hashCode() : 0);
        return result;
    }
}
