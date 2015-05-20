package com.badr.infodota.api.cosmetics.player;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 17:06
 */
public class PlayerCosmeticItem {
    private long id;
    private long original_id;
    private long defindex;
    private int level;
    private int quality;
    private long inventory;
    private int quantity;
    private List<Equipped> equipped;
    private int style;
    private String custom_desc;
    private List<Attribute> attributes;

    public PlayerCosmeticItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOriginal_id() {
        return original_id;
    }

    public void setOriginal_id(long original_id) {
        this.original_id = original_id;
    }

    public long getDefindex() {
        return defindex;
    }

    public void setDefindex(long defindex) {
        this.defindex = defindex;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public long getInventory() {
        return inventory;
    }

    public void setInventory(long inventory) {
        this.inventory = inventory;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Equipped> getEquipped() {
        return equipped;
    }

    public void setEquipped(List<Equipped> equipped) {
        this.equipped = equipped;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public String getCustom_desc() {
        return custom_desc;
    }

    public void setCustom_desc(String custom_desc) {
        this.custom_desc = custom_desc;
    }
}
