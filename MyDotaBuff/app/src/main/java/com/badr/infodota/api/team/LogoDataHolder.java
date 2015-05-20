package com.badr.infodota.api.team;

/**
 * User: ABadretdinov
 * Date: 15.05.14
 * Time: 17:01
 */
public class LogoDataHolder {
    private LogoData data;

    public LogoDataHolder() {
    }

    public LogoData getData() {
        return data;
    }

    public void setData(LogoData data) {
        this.data = data;
    }

    public String getUrl() {
        if (data != null) {
            return data.getUrl();
        }
        return null;
    }
}
