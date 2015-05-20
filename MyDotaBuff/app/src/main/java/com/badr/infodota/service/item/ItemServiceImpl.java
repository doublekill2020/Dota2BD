package com.badr.infodota.service.item;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.badr.infodota.BeanContainer;
import com.badr.infodota.api.items.Item;
import com.badr.infodota.dao.DatabaseManager;
import com.badr.infodota.dao.ItemDao;

import java.util.List;

/**
 * Created by ABadretdinov
 * 25.12.2014
 * 14:36
 */
public class ItemServiceImpl implements ItemService {
    private ItemDao itemDao;

    @Override
    public void initialize() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        itemDao = beanContainer.getItemDao();
    }

    @Override
    public Item.List getItems(Context context, String filter) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            return new Item.List(itemDao.getEntities(database, filter));
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public List<Item> getAllItems(Context context) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            return itemDao.getAllEntities(database);
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public Item getItemById(Context context, long id) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            return itemDao.getById(database, id);
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public Item getItemByDotaId(Context context, String dotaId) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            return itemDao.getByDotaId(database, dotaId);
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public void saveItem(Context context, Item item) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            itemDao.saveOrUpdate(database, item);
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public void saveFromToItems(Context context, List<Item> items) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        for (Item item : items) {
            SQLiteDatabase database = manager.openDatabase();
            try {
                itemDao.bindItems(database, item);
            } finally {
                manager.closeDatabase();
            }
        }
    }

    @Override
    public List<Item> getComplexItems(Context context) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            return itemDao.getComplexItems(database);
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public List<Item> getItemsFromThis(Context context, Item item) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            return itemDao.getParentItems(database, item);
        } finally {
            manager.closeDatabase();
        }
    }

    @Override
    public List<Item> getItemsToThis(Context context, Item item) {
        DatabaseManager manager = DatabaseManager.getInstance(context);
        SQLiteDatabase database = manager.openDatabase();
        try {
            return itemDao.getChildItems(database, item);
        } finally {
            manager.closeDatabase();
        }
    }
}
