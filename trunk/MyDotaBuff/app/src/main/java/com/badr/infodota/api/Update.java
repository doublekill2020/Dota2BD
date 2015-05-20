package com.badr.infodota.api;

/**
 * Created by Badr on 17.02.2015.
 */
public class Update {
    private int versionCode;
    private MultiLanguageString message;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public MultiLanguageString getMessage() {
        return message;
    }

    public void setMessage(MultiLanguageString message) {
        this.message = message;
    }
}
