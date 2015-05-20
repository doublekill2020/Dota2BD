package com.badr.infodota.api.cosmetics.price;

import java.util.List;

/**
 * User: ABadretdinov
 * Date: 31.03.14
 * Time: 16:27
 */
public class Result {
    private boolean success;
    private List<ItemPrice> assets;

    public Result() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ItemPrice> getAssets() {
        return assets;
    }

    public void setAssets(List<ItemPrice> assets) {
        this.assets = assets;
    }
}
