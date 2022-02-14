package cn.edu.mydotabuff;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;
import cn.edu.mydotabuff.common.db.RealmManager;

public class DotaApplication extends MultiDexApplication {
    private static DotaApplication mInstance = null;
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = DotaApplication.this;
        context = mInstance.getApplicationContext();
        BGASwipeBackManager.getInstance().init(this);
        RealmManager.initRealm(this);
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
                //.tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
//                        .build());
        //LocationSDKManager.getInstance().addAndUse(new DefaultLocationImpl());

        //RongIM.init(this, "25wehl3uw6q5w", R.drawable.ic_launcher);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Logger.e(e, "tag");
            }
        });
    }

    public static DotaApplication getApplication() {
        return mInstance;
    }

    /**
     * 检测网络连接
     */
    public boolean isNetworkAvailabe() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        }
        return false;
    }

    public <T> void saveData(T data, LocalDataType type) {
        String key = "";
        String name = "";
        switch (type) {
            case MATCHES:
                name = "matches";
                key = "matches";
                break;
            case HERO_USED_LIST:
                name = "hero_used_list";
                key = "hero_used_list";
                break;
            case PLAYER_INFO:
                name = "player_info";
                key = "player_info";
                break;
            case PLAYER_DETAIL_INFO:
                name = "player_detail_info";
                key = "player_detail_info";
                break;
            case BOARDS:
                name = "boards";
                key = "boards";
                break;
            default:
                break;
        }
        SharedPreferences mSharedPreferences = getSharedPreferences(name,
                Context.MODE_PRIVATE);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(data);
            String personBase64 = new String(Base64.encodeBase64(baos
                    .toByteArray()));
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(key, personBase64);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getData(LocalDataType type) {
        String key = "";
        String name = "";
        switch (type) {
            case MATCHES:
                name = "matches";
                key = "matches";
                break;
            case HERO_USED_LIST:
                name = "hero_used_list";
                key = "hero_used_list";
                break;
            case PLAYER_INFO:
                name = "player_info";
                key = "player_info";
                break;
            case PLAYER_DETAIL_INFO:
                name = "player_detail_info";
                key = "player_detail_info";
                break;
            case BOARDS:
                name = "boards";
                key = "boards";
                break;
            default:
                break;
        }
        T data = null;
        try {
            SharedPreferences mSharedPreferences = getSharedPreferences(name,
                    Context.MODE_PRIVATE);
            String personBase64 = mSharedPreferences.getString(key, "");
            byte[] base64Bytes = Base64.decodeBase64(personBase64.getBytes());
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            data = (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public void destoryData(LocalDataType type) {
        String key = "";
        String name = "";
        switch (type) {
            case MATCHES:
                name = "matches";
                key = "matches";
                break;
            case HERO_USED_LIST:
                name = "hero_used_list";
                key = "hero_used_list";
                break;
            case PLAYER_INFO:
                name = "player_info";
                key = "player_info";
                break;
            case PLAYER_DETAIL_INFO:
                name = "player_detail_info";
                key = "player_detail_info";
                break;
            case BOARDS:
                name = "boards";
                key = "boards";
                break;
            default:
                break;
        }
        SharedPreferences mSharedPreferences = getSharedPreferences(name,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, "");
        editor.commit();
    }

    public enum LocalDataType {
        MATCHES, HERO_USED_LIST, PLAYER_INFO, BOARDS, PLAYER_DETAIL_INFO;
    }
}
