package com.badr.infodota.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.badr.infodota.api.items.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABadretdinov
 * 25.12.2014
 * 12:49
 */
public class ItemDao extends GeneralDaoImpl<Item> {

    public static final String TABLE_NAME = "items";

    public static final String COLUMN_DOTA_ID = "dota_id";
    public static final String COLUMN_DNAME = "dname";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_TYPE = "type";

    private static final String CREATE_TABLE_QUERY = "( "
            + COLUMN_ID + " integer primary key, "
            + COLUMN_DNAME + " text not null, "
            + COLUMN_COST + " integer default null, "
            + COLUMN_TYPE + " text default null, "
            + COLUMN_DOTA_ID + " text not null);";

    private static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_DNAME,
            COLUMN_COST,
            COLUMN_TYPE,
            COLUMN_DOTA_ID
    };

    public static final String ITEMS_FROM_MAPPER_TABLE_NAME = "items_from";

    public static final String COLUMN_ITEM_ID = "item_id";
    public static final String COLUMN_NEED_ID = "need_id";

    private static final String CREATE_ITEMS_FROM_MAPPER_TABLE_QUERY =
            " (" +
                    COLUMN_ITEM_ID + " integer not null, " +
                    COLUMN_NEED_ID + " integer not null " +
                    ");";

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
    public Item cursorToEntity(Cursor cursor, int index) {
        Item entity = new Item();
        int i = index;
        entity.setId(cursor.getLong(i));
        i++;
        entity.setDname(cursor.getString(i));
        i++;
        entity.setCost(cursor.getInt(i));
        i++;
        entity.setType(cursor.getString(i));
        i++;
        entity.setDotaId(cursor.getString(i));
        return entity;
    }

    @Override
    protected ContentValues entityToContentValues(Item entity) {
        ContentValues values = super.entityToContentValues(entity);
        if (!TextUtils.isEmpty(entity.getDname())) {
            values.put(COLUMN_DNAME, entity.getDname());
        } else {
            values.putNull(COLUMN_DNAME);
        }
        values.put(COLUMN_COST, entity.getCost());

        if (!TextUtils.isEmpty(entity.getType())) {
            values.put(COLUMN_TYPE, entity.getType());
        } else {
            values.putNull(COLUMN_TYPE);
        }
        if (!TextUtils.isEmpty(entity.getDotaId())) {
            values.put(COLUMN_DOTA_ID, entity.getDotaId());
        } else {
            values.putNull(COLUMN_DOTA_ID);
        }
        return values;
    }

    @Override
    public String getDefaultOrderColumns() {
        return COLUMN_DNAME;
    }

    public List<Item> getEntities(SQLiteDatabase database, String type) {
        if (TextUtils.isEmpty(type)) {
            return getAllEntities(database);
        }
        Cursor cursor = database.query(getTableName(), getAllColumns(), COLUMN_TYPE + "=?", new String[]{type}, null, null, getDefaultOrderColumns());
        List<Item> entities = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    Item entity = cursorToEntity(cursor, 0);
                    entities.add(entity);
                } while (cursor.moveToNext());
            }
            return entities;
        } finally {
            cursor.close();
        }
    }

    public Item getByDotaId(SQLiteDatabase database, String dotaId) {
        Cursor cursor = database.query(getTableName(), getAllColumns(), COLUMN_DOTA_ID + "=?", new String[]{dotaId}, null, null, getDefaultOrderColumns());
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
    public void onCreate(SQLiteDatabase database) {
        super.onCreate(database);
        database.execSQL(
                "create table if not exists " +
                        ITEMS_FROM_MAPPER_TABLE_NAME +
                        " " +
                        CREATE_ITEMS_FROM_MAPPER_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("drop table if exists "+getTableName());
        database.execSQL("drop table if exists "+ITEMS_FROM_MAPPER_TABLE_NAME);
        onCreate(database);
    }

    public void bindItems(SQLiteDatabase database, Item item) {
        unbindItems(database, item);
        if (item.getComponents() != null) {
            for (String from : item.getComponents()) {
                long fromId = 0;
                if (!from.startsWith("recipe")) {
                    Item possibleItem = getByDotaId(database, from);
                    if (possibleItem != null) {
                        fromId = possibleItem.getId();
                    }
                }
                ContentValues values = new ContentValues();
                values.put(COLUMN_ITEM_ID, item.getId());
                values.put(COLUMN_NEED_ID, fromId);
                database.insert(ITEMS_FROM_MAPPER_TABLE_NAME, null, values);
            }
        }
    }

    public void unbindItems(SQLiteDatabase database, Item item) {
        database.delete(ITEMS_FROM_MAPPER_TABLE_NAME, COLUMN_ITEM_ID + "=?", new String[]{String.valueOf(item.getId())});
    }

    public List<Item> getComplexItems(SQLiteDatabase database) {
        Cursor cursor = database.query(true, ITEMS_FROM_MAPPER_TABLE_NAME, new String[]{COLUMN_ITEM_ID}, null, null, null, null, null, null);
        List<Item> items = new ArrayList<Item>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    Item item = getById(database, cursor.getLong(cursor.getColumnIndex(COLUMN_ITEM_ID)));
                    if (!items.contains(item)) {
                        items.add(item);
                    }
                    items.add(item);
                } while (cursor.moveToNext());
            }
            return items;
        } finally {
            cursor.close();
        }
    }

    //items from this
    public List<Item> getChildItems(SQLiteDatabase database, Item parent) {
        Cursor cursor = database.query(
                ITEMS_FROM_MAPPER_TABLE_NAME,
                new String[]{COLUMN_NEED_ID},
                COLUMN_ITEM_ID + "=?",
                new String[]{String.valueOf(parent.getId())}, null, null, null, null);
        List<Item> items = new ArrayList<Item>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    Item item = getById(database, cursor.getLong(cursor.getColumnIndex(COLUMN_NEED_ID)));
                    items.add(item);
                } while (cursor.moveToNext());
            }
            return items;
        } finally {
            cursor.close();
        }
    }

    //items to this
    public List<Item> getParentItems(SQLiteDatabase database, Item child) {
        List<Item> items = new ArrayList<Item>();
        Cursor cursor = database.query(
                true,
                ITEMS_FROM_MAPPER_TABLE_NAME,
                new String[]{COLUMN_ITEM_ID},
                COLUMN_NEED_ID + "=?",
                new String[]{String.valueOf(child.getId())}, null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Item item=getById(database, cursor.getLong(cursor.getColumnIndex(COLUMN_ITEM_ID)));
                    if (!items.contains(item)) {
                        items.add(item);
                    }
                } while (cursor.moveToNext());
            }
            return items;
        } finally {
            cursor.close();
        }
    }
}
