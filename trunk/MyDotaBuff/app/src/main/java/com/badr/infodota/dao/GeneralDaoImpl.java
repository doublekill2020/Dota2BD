package com.badr.infodota.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.badr.infodota.util.HasId;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABadretdinov
 * 25.12.2014
 * 11:35
 */
public abstract class GeneralDaoImpl<E extends HasId> implements GeneralDao<E> {
    public static final String COLUMN_ID = "_id";

    @Override
    public long save(SQLiteDatabase database, E entity) {
        ContentValues values = entityToContentValues(entity);
        return database.insert(getTableName(), null, values);
    }

    @Override
    public int update(SQLiteDatabase database, E entity) {
        ContentValues values = entityToContentValues(entity);
        return database.update(getTableName(), values, COLUMN_ID + " = ?", new String[]{String.valueOf(entity.getId())});
    }

    @Override
    public void saveOrUpdate(SQLiteDatabase database, E entity) {
        E entityFromDB = getById(database, entity.getId());
        if (entityFromDB == null) {
            save(database, entity);
        } else {
            update(database, entity);
        }
    }

    @Override
    public E getById(SQLiteDatabase database, long id) {
        Cursor cursor = database.query(
                getTableName()
                , getAllColumns(), COLUMN_ID + " = ?"
                , new String[]{String.valueOf(id)}, null, null, getDefaultOrderColumns()
        );
        try {
            if (cursor.moveToFirst()) {
                return cursorToEntity(cursor, 0);
            }
            return null;
        } finally {
            cursor.close();
        }
    }

    @Override
    public void delete(SQLiteDatabase database, E entity) {
        database.delete(getTableName(), COLUMN_ID + " =? ", new String[]{String.valueOf(entity.getId())});
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(
                "create table if not exists " +
                        getTableName() +
                        " " +
                        getNoTableNameDataBaseCreateQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
    }

    protected ContentValues entityToContentValues(E entity) {
        ContentValues values = new ContentValues();
        if (entity.getId() != 0) {
            values.put(COLUMN_ID, entity.getId());
        }

        return values;
    }

    protected abstract String getNoTableNameDataBaseCreateQuery();


    @Override
    public List<E> getAllEntities(SQLiteDatabase database) {
        Cursor cursor = database.query(getTableName(), getAllColumns(), null, null, null, null, getDefaultOrderColumns());
        try {
            List<E> entities = new ArrayList<E>(cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    E entity = cursorToEntity(cursor, 0);
                    entities.add(entity);
                } while (cursor.moveToNext());
            }
            return entities;
        } finally {
            cursor.close();
        }
    }

    @Override
    public boolean hasEntities(SQLiteDatabase database) {
        Cursor cursor = database.query(getTableName(), new String[]{COLUMN_ID}, null, null, null, null, null);
        boolean has = false;
        try {
            has = cursor.moveToFirst();
        } finally {
            cursor.close();
        }
        return has;
    }

    @Override
    public String getDefaultOrderColumns() {
        return COLUMN_ID;
    }
}
