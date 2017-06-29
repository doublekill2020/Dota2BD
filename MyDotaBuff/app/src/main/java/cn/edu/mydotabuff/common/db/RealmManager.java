package cn.edu.mydotabuff.common.db;

import android.content.Context;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by nevermore on 2017/6/28 0028.
 */

public class RealmManager {
    //TODO 当有新增数据库表时，需要在这里添加
    public static Class[] mTables = new Class[]{

    };
    private static final int VERSION = 1;

    public static void initRealm(Context context) {
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("ones.ai.realm")
                .schemaVersion(VERSION)
                .migration(getMigration())
                .build();
        Realm.setDefaultConfiguration(config);
    }

    //处理数据库版本升级
    private static RealmMigration getMigration() {
        return new RealmMigration() {
            @Override
            public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                RealmSchema schema = realm.getSchema();
                if (oldVersion == 1) {

                }
            }
        };
    }
    public static void closeRealm(Realm realm) {
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }
}
