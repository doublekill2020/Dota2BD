package com.badr.infodota.api.guide.custom;

import java.io.Serializable;

/**
 * User: Histler
 * Date: 28.01.14
 */
public class Guide implements Serializable {
    private String Hero;
    private String Title;
    private String GuideRevision;
    private String FileVersion;
    private com.badr.infodota.api.guide.custom.ItemBuild ItemBuild;
    private com.badr.infodota.api.guide.custom.AbilityBuild AbilityBuild;

    public Guide() {
    }

    public String getHero() {
        return Hero;
    }

    public void setHero(String hero) {
        Hero = hero;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getGuideRevision() {
        return GuideRevision;
    }

    public void setGuideRevision(String guideRevision) {
        GuideRevision = guideRevision;
    }

    public String getFileVersion() {
        return FileVersion;
    }

    public void setFileVersion(String fileVersion) {
        FileVersion = fileVersion;
    }

    public com.badr.infodota.api.guide.custom.ItemBuild getItemBuild() {
        return ItemBuild;
    }

    public void setItemBuild(com.badr.infodota.api.guide.custom.ItemBuild itemBuild) {
        ItemBuild = itemBuild;
    }

    public com.badr.infodota.api.guide.custom.AbilityBuild getAbilityBuild() {
        return AbilityBuild;
    }

    public void setAbilityBuild(com.badr.infodota.api.guide.custom.AbilityBuild abilityBuild) {
        AbilityBuild = abilityBuild;
    }
}
