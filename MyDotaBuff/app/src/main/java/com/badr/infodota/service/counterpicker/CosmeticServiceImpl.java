package com.badr.infodota.service.counterpicker;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.api.cosmetics.player.PlayerCosmeticItem;
import com.badr.infodota.api.cosmetics.price.PricesResult;
import com.badr.infodota.api.cosmetics.store.StoreResult;
import com.badr.infodota.util.FileUtils;
import com.badr.infodota.util.TrackUtils;

import java.io.File;
import java.util.List;

import cn.edu.mydotabuff.R;

/**
 * User: ABadretdinov
 * Date: 02.04.14
 * Time: 13:11
 */
public class CosmeticServiceImpl implements CosmeticService{

    @Override
    public Pair<StoreResult, String> getUpdatedCosmeticItems(Context context) {
        try {
            String language = context.getString(R.string.language);
            language = language.substring(0, 2);
            StoreResult result = BeanContainer.getInstance().getSteamService().getCosmeticItems(language);
            String message=null;
            if (result== null) {
                message = "Failed to get cosmetic items";
                Log.e(CosmeticServiceImpl.class.getName(), message);
            } else {
                File externalFilesDir = FileUtils.externalFileDir(context);
                FileUtils.saveJsonFile(externalFilesDir.getAbsolutePath() + File.separator + "store" + File.separator + "storeItems.json",
                        result);
            }
            return Pair.create(result,message);
        } catch (Exception e) {
            String message = "Failed to get cosmetic items, cause: " + e.getMessage();
            Log.e(CosmeticServiceImpl.class.getName(), message, e);
            return Pair.create(null, message);
        }
    }

    @Override
    public Pair<StoreResult, String> getCosmeticItems(Context context) {
        StoreResult storeResult = null;
        String message = null;
        try {
            File externalFilesDir = FileUtils.externalFileDir(context);
            String fileName = externalFilesDir.getAbsolutePath() + File.separator + "store" + File.separator + "storeItems.json";
            if (new File(fileName).exists()) {
                storeResult = FileUtils.entityFromFile(
                        fileName,
                        StoreResult.class);
            }
        } catch (Exception e) {
            message = e.getLocalizedMessage();
            Log.e(CosmeticServiceImpl.class.getName(), message, e);
        }
        return Pair.create(storeResult, message);
    }

    @Override
    public Pair<PricesResult, String> getCosmeticItemsPrices(Context context) {
        PricesResult pricesResult = null;
        String message = null;
        try {
            File externalFilesDir = FileUtils.externalFileDir(context);
            String fileName = externalFilesDir.getAbsolutePath() + File.separator + "store" + File.separator + "storePrices.json";
            if (new File(fileName).exists()) {
                pricesResult = FileUtils.entityFromFile(
                        fileName,
                        PricesResult.class);
            }
        } catch (Exception e) {
            message = e.getMessage();
            Log.e(CosmeticServiceImpl.class.getName(), message, e);
        }
        return Pair.create(pricesResult, message);
    }

    @Override
    public Pair<PricesResult, String> getUpdatedCosmeticItemsPrices(Context context) {
        try {
            PricesResult result = BeanContainer.getInstance().getSteamService().getCosmeticItemsPrices();
            String message=null;
            if (result == null) {
                message = "Failed to get cosmetic item prices";
                Log.e(CosmeticServiceImpl.class.getName(), message);
            } else {
                File externalFilesDir = FileUtils.externalFileDir(context);
                FileUtils.saveJsonFile(externalFilesDir.getAbsolutePath() + File.separator + "store" + File.separator + "storePrices.json",
                        result);
            }
            return Pair.create(result,message);
        } catch (Exception e) {
            String message = "Failed to get cosmetic item prices, cause: " + e.getMessage();
            Log.e(CosmeticServiceImpl.class.getName(), message, e);
            return Pair.create(null, message);
        }
    }

    @Override
    public Pair<List<PlayerCosmeticItem>, String> getPlayersCosmeticItems(Context context, long steam32Id) {
        try {
            List<PlayerCosmeticItem> result = BeanContainer.getInstance().getSteamService().getPlayerCosmeticItems(TrackUtils.steam32to64(steam32Id));
            String message=null;
            if (result == null) {
                message = "Failed to get cosmetic items for player";
                Log.e(CosmeticServiceImpl.class.getName(), message);
            }
            return Pair.create(result,message);
        } catch (Exception e) {
            String message = "Failed to get cosmetic items for player, cause: " + e.getMessage();
            Log.e(CosmeticServiceImpl.class.getName(), message, e);
            return Pair.create(null, message);
        }
    }
}
