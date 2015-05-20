package com.badr.infodota.api.guide.custom;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * User: Histler
 * Date: 28.01.14
 */
public class ItemBuild implements Serializable {
    //private GuideItems Items;
    private Map<String, List<String>> Items;
    private Map<String, String> ItemTooltips;

    public ItemBuild() {
    }

    public Map<String, String> getItemTooltips() {
        return ItemTooltips;
    }

    public void setItemTooltips(Map<String, String> itemTooltips) {
        ItemTooltips = itemTooltips;
    }

    public Map<String, List<String>> getItems() {
        return Items;
    }

    public void setItems(Map<String, List<String>> items) {
        Items = items;
    }
}
