package com.badr.infodota.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ABadretdinov
 * 25.12.2014
 * 11:27
 */
public class DatabaseManager {
    private static DatabaseManager instance;
    private static Helper mHelper;
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager();
            mHelper = new Helper(context);
        }
    }

    public static synchronized DatabaseManager getInstance(Context context) {
        if (instance == null) {
            initializeInstance(context);
        }
        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        if (mOpenCounter.incrementAndGet() == 1) {
            mDatabase = mHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        if (mOpenCounter.decrementAndGet() == 0) {
            mDatabase.close();
        }
    }
}
