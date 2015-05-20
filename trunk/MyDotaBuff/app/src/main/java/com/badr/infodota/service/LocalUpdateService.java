package com.badr.infodota.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.badr.infodota.dao.DatabaseManager;
import com.badr.infodota.dao.Helper;

/**
 * Created by Badr on 27.12.2014.
 */
public class LocalUpdateService {
    public void update(Context context, String sql, int version) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            database.execSQL(sql);
            ContentValues values = new ContentValues();
            values.put("version", version);
            database.update("updated_version", values, null, null);
        } finally {
            manager.closeDatabase();
        }
    }


    public void setUpdated(Context context) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("version", Helper.DATABASE_VERSION);
            database.update("updated_version", values, null, null);
        } finally {
            manager.closeDatabase();
        }
    }

    public int getVersion(Context context) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        Cursor cursor = database.query("updated_version", new String[]{"version"}, null, null, null, null, null);
        try {
            int version = 0;
            if (cursor.moveToFirst()) {
                version = cursor.getInt(0);
            }

            return version;
        } finally {
            cursor.close();
            manager.closeDatabase();
        }
    }

}
