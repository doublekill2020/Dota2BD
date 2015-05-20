package com.badr.infodota.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by ABadretdinov
 * 25.12.2014
 * 11:31
 */
public interface GeneralDao<E> extends CreateTableDao {
    String getTableName();

    String[] getAllColumns();

    E cursorToEntity(Cursor cursor, int index);

    long save(SQLiteDatabase database, E entity);

    int update(SQLiteDatabase database, E entity);

    void saveOrUpdate(SQLiteDatabase database, E entity);

    E getById(SQLiteDatabase database, long id);

    void delete(SQLiteDatabase database, E entity);

    List<E> getAllEntities(SQLiteDatabase database);

    boolean hasEntities(SQLiteDatabase database);

    String getDefaultOrderColumns();
}
