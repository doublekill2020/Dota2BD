package com.badr.infodota.api.items;

import com.badr.infodota.util.HasId;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: Histler
 * Date: 18.01.14
 */
public class Item implements Comparable, HasId {

    private long id;
    private String dotaId;
    private String dname;

    private String qual;
    private String img;
    private int cost;
    private String desc;
    private String notes;
    private String attrib;
    private Object mc;
    private Object cd;
    private String lore;
    private String[] components;
    private boolean created;
    private String type;

    public Item() {
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getQual() {
        return qual;
    }

    public void setQual(String qual) {
        this.qual = qual;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAttrib() {
        return attrib;
    }

    public void setAttrib(String attrib) {
        this.attrib = attrib;
    }

    public Object getMc() {
        return mc;
    }

    public void setMc(Object mc) {
        this.mc = mc;
    }

    public Object getCd() {
        return cd;
    }

    public void setCd(Object cd) {
        this.cd = cd;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String[] getComponents() {
        return components;
    }

    public void setComponents(String[] components) {
        this.components = components;
    }

    public boolean isCreated() {
        return created;
    }

    public void setCreated(boolean created) {
        this.created = created;
    }

    public String getDotaId() {
        return dotaId;
    }

    public void setDotaId(String dotaId) {
        this.dotaId = dotaId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(Object another) {
        if (!(another instanceof Item)) {
            return 1;
        }
        Item anItem = (Item) another;
        if (anItem.equals(this))
            return 0;
        return anItem.getDotaId().compareTo(getDotaId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (dname != null ? !dname.equals(item.dname) : item.dname != null) return false;
        if (!dotaId.equals(item.dotaId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + dotaId.hashCode();
        result = 31 * result + (dname != null ? dname.hashCode() : 0);
        return result;
    }
    public static class List extends ArrayList<Item>{
        public List(Collection<? extends Item> collection) {
            super(collection);
        }
    }
}
