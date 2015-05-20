package com.badr.infodota.api.abilities;

import com.badr.infodota.util.HasId;

/**
 * User: Histler
 * Date: 22.01.14
 */
public class Ability implements Comparable, HasId {
    private String name;
    private long id;
    private String wasId;
    private String wasName;
    private long heroId;

    public Ability() {
    }

    public String getWasId() {
        return wasId;
    }

    public void setWasId(String wasId) {
        this.wasId = wasId;
    }

    public String getWasName() {
        return wasName;
    }

    public void setWasName(String wasName) {
        this.wasName = wasName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getHeroId() {
        return heroId;
    }

    public void setHeroId(long heroId) {
        this.heroId = heroId;
    }

    @Override
    public int compareTo(Object another) {
        if (another == null || !(another instanceof Ability)) {
            return 1;
        }
        return (int) (id - ((Ability) another).getId());
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ability)) return false;

        Ability ability = (Ability) o;

        if (id == ability.getId()) return true;
        if (name != null && ability.getName() != null && name.equals(ability.getName()))
            return true;
        if (wasName != null && ability.getName() != null) {
            if (wasName.contains(ability.getName())) {
                return true;
            }
        }
        if (ability.getWasName() != null && name != null) {
            if (ability.getWasName().contains(name)) {
                return true;
            }
        }

        String abilityId = String.valueOf(ability.getId());
        if (wasId != null && abilityId != null && ability.getId() != 0) {
            if (wasId.contains(abilityId)) {
                return true;
            }
        }

        abilityId = String.valueOf(id);
        if (ability.getWasId() != null && abilityId != null && id != 0) {
            if (ability.getWasId().contains(abilityId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
