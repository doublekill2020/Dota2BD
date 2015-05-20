package com.badr.infodota.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.badr.infodota.api.abilities.Ability;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABadretdinov
 * 25.12.2014
 * 18:37
 */
public class AbilityDao extends GeneralDaoImpl<Ability> {
    public static final String TABLE_NAME = "abilities";

    public static final String COLUMN_IDLIST = "idlist";
    public static final String COLUMN_NAMELIST = "namelist";
    public static final String COLUMN_HERO_ID = "heroId";
    public static final String COLUMN_DOTA_ID = "dotaId";

    private static final String CREATE_TABLE_QUERY = "( "
            + COLUMN_ID + " integer not null, "
            + COLUMN_IDLIST + " text default null,"
            + COLUMN_NAMELIST + " text default null,"
            + COLUMN_HERO_ID + " integer default null,"
            + COLUMN_DOTA_ID + " text default null);";

    private static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_IDLIST,
            COLUMN_NAMELIST,
            COLUMN_HERO_ID,
            COLUMN_DOTA_ID
    };

    @Override
    protected String getNoTableNameDataBaseCreateQuery() {
        return CREATE_TABLE_QUERY;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getAllColumns() {
        return ALL_COLUMNS;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("drop table if exists "+getTableName());
        onCreate(database);
    }

    @Override
    public Ability cursorToEntity(Cursor cursor, int index) {
        Ability entity = new Ability();
        int i = index;
        entity.setId(cursor.getLong(i));
        i++;
        entity.setWasId(cursor.getString(i));
        i++;
        entity.setWasName(cursor.getString(i));
        i++;
        entity.setHeroId(cursor.getLong(i));
        i++;
        entity.setName(cursor.getString(i));
        return entity;
    }

    @Override
    protected ContentValues entityToContentValues(Ability entity) {
        ContentValues values = super.entityToContentValues(entity);
        if (!TextUtils.isEmpty(entity.getWasId())) {
            values.put(COLUMN_IDLIST, entity.getWasId());
        } else {
            values.putNull(COLUMN_IDLIST);
        }
        if (!TextUtils.isEmpty(entity.getWasName())) {
            values.put(COLUMN_NAMELIST, entity.getWasName());
        } else {
            values.putNull(COLUMN_NAMELIST);
        }
        if (entity.getHeroId() != 0) {
            values.put(COLUMN_HERO_ID, entity.getHeroId());
        }
        if (!TextUtils.isEmpty(entity.getName())) {
            values.put(COLUMN_DOTA_ID, entity.getName());
        } else {
            values.putNull(COLUMN_DOTA_ID);
        }
        return values;
    }

    @Override
    public void saveOrUpdate(SQLiteDatabase database, Ability entity) {
        if (!TextUtils.isEmpty(entity.getWasId())) {
            String[] ids = entity.getWasId().split(",");
            String query = "";
            int size = ids.length;
            for (int i = 0; i < size; i++) {
                query += '?';
                if (i != size - 1) {
                    query += ',';
                }
            }
            database.delete(getTableName(), COLUMN_ID + " in (" + query + ")", ids);
        }
        super.saveOrUpdate(database, entity);
    }

    @Override
    public Ability getById(SQLiteDatabase database, long id) {
        Cursor cursor = database.query(
                getTableName()
                , getAllColumns(), COLUMN_ID + " = ? or " + COLUMN_IDLIST + " like ?"
                , new String[]{String.valueOf(id), "%" + id + "%"}, null, null, getDefaultOrderColumns()
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

    public List<String> getStringAbilities(SQLiteDatabase database, long heroId) {
        Cursor cursor = database.query(getTableName(), new String[]{COLUMN_DOTA_ID}, COLUMN_HERO_ID + "=?", new String[]{String.valueOf(heroId)}, null, null, null);
        try {
            List<String> abilities = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    String dotaId = cursor.getString(cursor.getColumnIndex(COLUMN_DOTA_ID));
                    abilities.add(dotaId);
                } while (cursor.moveToNext());
            }
            return abilities;
        } finally {
            cursor.close();
        }
    }

    public List<Ability> getEntities(SQLiteDatabase database, long heroId) {
        Cursor cursor = database.query(getTableName(), getAllColumns(), COLUMN_HERO_ID + "=?", new String[]{String.valueOf(heroId)}, null, null, null);
        try {
            List<Ability> abilities = new ArrayList<Ability>();
            if (cursor.moveToFirst()) {
                do {
                    Ability ability = cursorToEntity(cursor, 0);
                    abilities.add(ability);
                } while (cursor.moveToNext());
            }
            return abilities;
        } finally {
            cursor.close();
        }
    }

    public List<Ability> getNotThisHeroEntities(SQLiteDatabase database, long heroId) {
        Cursor cursor = database.query(getTableName(), getAllColumns(), COLUMN_HERO_ID + "<>? and " + COLUMN_ID + "<>5002", new String[]{String.valueOf(heroId)}, null, null, getDefaultOrderColumns());
        try {
            List<Ability> abilities = new ArrayList<Ability>();
            if (cursor.moveToFirst()) {
                do {
                    Ability ability = cursorToEntity(cursor, 0);
                    abilities.add(ability);
                } while (cursor.moveToNext());
            }
            return abilities;
        } finally {
            cursor.close();
        }
    }

    public List<Ability> getEntitiesByList(SQLiteDatabase database, List<Long> inGameList) {

        int inGameListSize = inGameList.size();
        String[] whereArgs = new String[inGameListSize * 2];
        StringBuilder qAsStr = new StringBuilder("");
        StringBuilder likeAsStr = new StringBuilder("");

        for (int i = 0; i < inGameListSize; i++) {
            whereArgs[i] = String.valueOf(inGameList.get(i));
            whereArgs[i + inGameListSize] = "%" + inGameList.get(i) + "%";
            qAsStr.append('?');
            likeAsStr.append(" or " + COLUMN_IDLIST + " like ?");
            if (i != inGameListSize - 1) {
                qAsStr.append(',');
            }
        }
        Cursor cursor = database.query(getTableName(), getAllColumns(), COLUMN_ID + " in (" + qAsStr + ") " + likeAsStr, whereArgs, null, null, getDefaultOrderColumns());
        try {
            List<Ability> abilities = new ArrayList<Ability>();
            if (cursor.moveToFirst()) {
                do {
                    Ability ability = cursorToEntity(cursor, 0);
                    abilities.add(ability);
                } while (cursor.moveToNext());
            }
            return abilities;
        } finally {
            cursor.close();
        }
    }
}
