package com.badr.infodota.api.guide.custom;

import java.io.Serializable;
import java.util.Map;

/**
 * User: Histler
 * Date: 28.01.14
 */
public class AbilityBuild implements Serializable {
    private Map<String, String> AbilityOrder;
    private Map<String, String> AbilityTooltips;

    public AbilityBuild() {
    }

    public Map<String, String> getAbilityOrder() {
        return AbilityOrder;
    }

    public void setAbilityOrder(Map<String, String> abilityOrder) {
        AbilityOrder = abilityOrder;
    }

    public Map<String, String> getAbilityTooltips() {
        return AbilityTooltips;
    }

    public void setAbilityTooltips(Map<String, String> abilityTooltips) {
        AbilityTooltips = abilityTooltips;
    }
}
