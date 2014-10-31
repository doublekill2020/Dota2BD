package cn.edu.mydotabuff.util;

import java.lang.reflect.Method;

import cn.edu.mydotabuff.hero.HeroDetailActivity;
import cn.edu.mydotabuff.item.ItemsDetailActivity;
import cn.edu.mydotabuff.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.TextUtils;
import android.text.Html.ImageGetter;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cn.edu.mydotabuff.entity.HeroItem;
import cn.edu.mydotabuff.entity.ItemsItem;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Utils
 * 
 * @author tupunco
 * 
 */
public final class Utils {
    private final static String s_ItemsImage_Format = "assets://items_images/%s_lg.jpg";
    private final static String s_HeroImage_Format = "assets://heroes_images/%s_full.jpg";
    // private final static String s_HeroIcon_Format =
    // "assets://heroes_icons/%s_icon.jpg";
    private final static String s_AbilitiesImage_Format = "assets://abilities_images/%s_hp1.jpg";

    /**
     * get Hero image url
     * 
     * @param keyName
     * @return
     */
    public static String getHeroImageUri(String keyName) {
        return String.format(s_HeroImage_Format, keyName);
    }

    /**
     * get Hero icon url
     * 
     * @param keyName
     * @return
     */
    // public static String getHeroIconUri(String keyName) {
    // return String.format(s_HeroIcon_Format, keyName);
    // }

    /**
     * get items image url
     * 
     * @param keyName
     * @return
     */
    public static String getItemsImageUri(String keyName) {
        return String.format(s_ItemsImage_Format, keyName);
    }

    /**
     * 
     * @param keyName
     * @return
     */
    public static String getAbilitiesImageUri(String keyName) {
        return String.format(s_AbilitiesImage_Format, keyName);
    }

    /**
     * 
     * @param menu
     */
    public static void configureStarredMenuItem(MenuItem menu) {
        configureStarredMenuItem(menu, false);
    }

    /**
     * 
     * @param menu
     * @param isRecipe
     *            物品页面,当前物品是否是合成卷轴
     */
    public static void configureStarredMenuItem(MenuItem menu, boolean isRecipe) {
        if (menu == null || !menu.isCheckable()) {
            return;
        }
        if (isRecipe) {
            menu.setVisible(false);
            return;
        }

        if (menu.isChecked()) {
            menu.setIcon(R.drawable.ic_action_favorite);
            menu.setTitle(R.string.menu_removefavorite);
        } else {
            menu.setIcon(R.drawable.ic_action_favorite2);
            menu.setTitle(R.string.menu_addfavorite);
        }
    }

    /**
     * start HeroDetail Activity
     * 
     * @param packageContext
     * @param cItem
     */
    public static void startHeroDetailActivity(Context packageContext, HeroItem cItem) {
        if (cItem == null) {
            return;
        }

        startHeroDetailActivity(packageContext, cItem.keyName);
    }

    /**
     * start HeroDetail Activity
     * 
     * @param packageContext
     * @param cItem
     */
    public static void startHeroDetailActivity(Context packageContext, String cItemKeyName) {
        if (packageContext == null || cItemKeyName == null) {
            return;
        }

        final Intent intent = new Intent(packageContext, HeroDetailActivity.class);
        intent.putExtra(HeroDetailActivity.KEY_HERO_DETAIL_KEY_NAME, cItemKeyName);
        packageContext.startActivity(intent);
    }

    /**
     * start ItemsDetail Activity
     * 
     * @param packageContext
     * @param cItem
     */
    public static void startItemsDetailActivity(Context packageContext, ItemsItem cItem) {
        if (cItem == null) {
            return;
        }

        startItemsDetailActivity(packageContext, cItem.keyName, cItem.parent_keyName);
    }

    /**
     * start ItemsDetail Activity
     * 
     * @param packageContext
     * @param cItem
     */
    private static void startItemsDetailActivity(Context packageContext, String cItemKeyName,
            String cItemParentKeyName) {
        if (packageContext == null || cItemKeyName == null) {
            return;
        }

        final Intent intent = new Intent(packageContext, ItemsDetailActivity.class);
        intent.putExtra(ItemsDetailActivity.KEY_ITEMS_DETAIL_KEY_NAME, cItemKeyName);
        if (!TextUtils.isEmpty(cItemParentKeyName)) {
            intent.putExtra(ItemsDetailActivity.KEY_ITEMS_DETAIL_PARENT_KEY_NAME,
                    cItemParentKeyName);
        }
        packageContext.startActivity(intent);
    }

    /**
     * fill Fragment to FragmentActivity
     * 
     * @param fragmentActivity
     * @param cFragment
     */
    public static void fillFragment(FragmentActivity fragmentActivity, Fragment cFragment) {
        if (fragmentActivity == null || cFragment == null) {
            return;
        }

        final FragmentManager fm = fragmentActivity.getSupportFragmentManager();
        if (fm.findFragmentById(android.R.id.content) == null) {
            fm.beginTransaction().add(android.R.id.content, cFragment).commit();
        }
    }

    /**
     * 
     * @return
     */
    public static DisplayImageOptions createDisplayImageOptions() {
        return new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.abs__progress_medium_holo)
                .showImageForEmptyUri(R.drawable.hero_for_empty_url)
                .cacheInMemory()
                .build();
    }

    /**
     * execute AsyncTask
     * 
     * @param task
     */
    @SuppressLint("NewApi")
    public static <Params, Progress, Result> void executeAsyncTask(
            AsyncTask<Params, Progress, Result> loaderTask, Params... params) {
        if (loaderTask == null) {
            return;
        }

        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
            loaderTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            loaderTask.execute(params);
        }
    }

    /**
     * 字符串数组内指定项存在与否
     * 
     * @param collection
     * @param predicate
     * @return
     */
    public static boolean exists(String[] collection, String predicate) {
        return exists(collection, predicate, false);
    }

    /**
     * 字符串数组内指定项存在与否
     * 
     * @param collection
     * @param predicate
     * @param ignoreCase
     * @return
     */
    public static boolean exists(String[] collection, String predicate, boolean ignoreCase) {
        if (collection != null && predicate != null) {
            for (String cItem : collection) {
                if (ignoreCase) {
                    if (predicate.equalsIgnoreCase(cItem))
                        return true;
                } else {
                    if (predicate.equals(cItem))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * 字符串数组内指定项的索引
     * 
     * @param collection
     * @param predicate
     * @return
     */
    public static int indexOf(String[] collection, String predicate) {
        if (collection != null && collection.length > 0 && predicate != null) {
            for (int i = 0; i < collection.length; i++) {
                if (collection[i].equals(predicate)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 英雄/物品 分类菜单项指定值获取
     * 
     * @param cResources
     * @param keys
     * @param values
     * @param value
     * @return
     */
    public static String getMenuValue(Resources cResources, int keys_resId, int values_resId,
            String value) {
        if (cResources == null) {
            return null;
        }

        return getMenuValue(cResources.getStringArray(keys_resId),
                cResources.getStringArray(values_resId), value);
    }

    /**
     * 英雄/物品 分类菜单项指定值获取
     * 
     * @param keys
     * @param values
     * @param value
     * @return
     */
    public static String getMenuValue(String[] keys, String[] values, String value) {
        if (keys == null || values == null || TextUtils.isEmpty(value) || keys.length <= 0
                || keys.length != values.length) {
            return null;
        }
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(value)) {
                return keys[i];
            }
        }
        return null;
    }

    /**
     * 
     * @param activity
     * @param id
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    public static <T extends View> T findById(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }

    /**
     * 
     * @param activity
     * @param id
     * @return
     */
    @SuppressWarnings({ "unchecked" })
    public static <T extends View> T findById(View view, int id) {
        return (T) view.findViewById(id);
    }

    /**
     * bind HtmlTextView value
     * @param text
     * @param fieldValue
     */
    public static void bindHtmlTextView(TextView text, String fieldValue) {
        bindHtmlTextView(text, fieldValue, null);
    }

    /**
     * bind HtmlTextView value
     * @param text
     * @param fieldValue
     * @param cImageGetter
     */
    public static void bindHtmlTextView(TextView text, String fieldValue, ImageGetter cImageGetter) {
        if (!TextUtils.isEmpty(fieldValue)) {
            text.setText(Html.fromHtml(fieldValue, cImageGetter, null));
        } else {
            text.setVisibility(View.GONE);
        }
    }

    /**
     * Meizu-HasSmartBar()
     * FROM: Meizu Smartbar 开发指南
     * 
     * @return
     */
    public static boolean hasSmartBar() {
        try {
            // 新型号可用反射调用Build.hasSmartBar()
            final Method method = Class.forName("android.os.Build").getMethod("hasSmartBar");
            return ((Boolean) method.invoke(null)).booleanValue();
        } catch (Exception e) {
        }

        // 反射不到 Build.hasSmartBar(), 则用 Build.DEVICE 判断
        if (Build.DEVICE.equals("mx2")) {
            return true;
        } else if (Build.DEVICE.equals("mx") || Build.DEVICE.equals("m9")) {
            return false;
        }
        return false;
    }
}
