package com.badr.infodota.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.badr.infodota.api.heroes.HeroStats;

/**
 * Created by ABadretdinov
 * 25.12.2014
 * 17:05
 */
public class HeroStatsDao extends GeneralDaoImpl<HeroStats> {
    public static final String TABLE_NAME = "hero_stats";

    public static final String COLUMN_PATCH = "patch";
    /*
    * 0- radiant
    * 1- dire
    * */
    public static final String COLUMN_ALIGNMENT = "alignment";
    public static final String COLUMN_MOVESPEED = "movespeed";
    public static final String COLUMN_MAX_DMG = "max_dmg";
    public static final String COLUMN_MIN_DMG = "min_dmg";
    public static final String COLUMN_HP = "hp";
    public static final String COLUMN_MANA = "mana";
    public static final String COLUMN_HP_REGEN = "hp_regen";
    public static final String COLUMN_MANA_REGEN = "mana_regen";
    public static final String COLUMN_ARMOR = "armor";
    public static final String COLUMN_RANGE = "range";
    public static final String COLUMN_PROJECTILE_SPEED = "projectile_speed";
    public static final String COLUMN_BASE_STR = "base_str";
    public static final String COLUMN_BASE_AGI = "base_agi";
    public static final String COLUMN_BASE_INT = "base_int";
    public static final String COLUMN_STR_GAIN = "str_gain";
    public static final String COLUMN_AGI_GAIN = "agi_gain";
    public static final String COLUMN_INT_GAIN = "int_gain";
    public static final String COLUMN_PRIMARY_STAT = "primary_stat";
    public static final String COLUMN_BASE_ATTACK_TIME = "base_attack_time";
    public static final String COLUMN_DAY_VISION = "day_vision";
    public static final String COLUMN_NIGHT_VISION = "night_vision";
    public static final String COLUMN_ATTACK_POINT = "attack_point";
    public static final String COLUMN_ATTACK_SWING = "attack_swing";
    public static final String COLUMN_CAST_POINT = "cast_point";
    public static final String COLUMN_CAST_SWING = "cast_swing";
    public static final String COLUMN_TURNRATE = "turnrate";
    public static final String COLUMN_LEGS = "legs";
    public static final String COLUMN_ROLES = "roles";

    private static final String CREATE_TABLE_QUERY = " ( " +
            COLUMN_ID + " integer not null, " +
            COLUMN_PATCH + " text default null," +
            COLUMN_ROLES + " text default null," +
            COLUMN_ALIGNMENT + " integer default 0," +
            COLUMN_MOVESPEED + " integer default 0," +
            COLUMN_MAX_DMG + " integer default 0," +
            COLUMN_MIN_DMG + " integer default 0," +
            COLUMN_HP + " integer default 0," +
            COLUMN_MANA + " integer default 0," +
            COLUMN_HP_REGEN + " real default 0," +
            COLUMN_MANA_REGEN + " real default 0," +
            COLUMN_ARMOR + " real default 0," +
            COLUMN_RANGE + " integer default 0," +
            COLUMN_PROJECTILE_SPEED + " integer default 0," +
            COLUMN_BASE_STR + " integer default 0," +
            COLUMN_BASE_AGI + " integer default 0," +
            COLUMN_BASE_INT + " integer default 0," +
            COLUMN_STR_GAIN + " real default 0," +
            COLUMN_AGI_GAIN + " real default 0," +
            COLUMN_INT_GAIN + " real default 0," +
            COLUMN_PRIMARY_STAT + " integer default 0," +
            COLUMN_BASE_ATTACK_TIME + " real default 0," +
            COLUMN_DAY_VISION + " integer default 0," +
            COLUMN_NIGHT_VISION + " integer default 0," +
            COLUMN_ATTACK_POINT + " real default 0," +
            COLUMN_ATTACK_SWING + " real default 0," +
            COLUMN_CAST_POINT + " real default 0," +
            COLUMN_CAST_SWING + " real default 0," +
            COLUMN_TURNRATE + " integer default 0," +
            COLUMN_LEGS + " integer default 0);";

    private static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_PATCH,
            COLUMN_ROLES,
            COLUMN_ALIGNMENT,
            COLUMN_MOVESPEED,
            COLUMN_MAX_DMG,
            COLUMN_MIN_DMG,
            COLUMN_HP,
            COLUMN_MANA,
            COLUMN_HP_REGEN,
            COLUMN_MANA_REGEN,
            COLUMN_ARMOR,
            COLUMN_RANGE,
            COLUMN_PROJECTILE_SPEED,
            COLUMN_BASE_STR,
            COLUMN_BASE_AGI,
            COLUMN_BASE_INT,
            COLUMN_STR_GAIN,
            COLUMN_AGI_GAIN,
            COLUMN_INT_GAIN,
            COLUMN_PRIMARY_STAT,
            COLUMN_BASE_ATTACK_TIME,
            COLUMN_DAY_VISION,
            COLUMN_NIGHT_VISION,
            COLUMN_ATTACK_POINT,
            COLUMN_ATTACK_SWING,
            COLUMN_CAST_POINT,
            COLUMN_CAST_SWING,
            COLUMN_TURNRATE,
            COLUMN_LEGS
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
    public HeroStats cursorToEntity(Cursor cursor, int index) {
        HeroStats entity = new HeroStats();
        int i = index;
        entity.setId(cursor.getLong(i));
        i++;
        entity.setPatch(cursor.getString(i));
        i++;
        entity.setRoles(rolesFromString(cursor.getString(i)));
        i++;
        entity.setAlignment(cursor.getInt(i));
        i++;
        entity.setMovespeed(cursor.getInt(i));
        i++;
        entity.setMaxDmg(cursor.getInt(i));
        i++;
        entity.setMinDmg(cursor.getInt(i));
        i++;
        entity.setHP(cursor.getInt(i));
        i++;
        entity.setMana(cursor.getInt(i));
        i++;
        entity.setHPRegen(cursor.getFloat(i));
        i++;
        entity.setManaRegen(cursor.getFloat(i));
        i++;
        entity.setArmor(cursor.getFloat(i));
        i++;
        entity.setRange(cursor.getInt(i));
        i++;
        entity.setProjectileSpeed(cursor.getInt(i));
        i++;
        entity.setBaseStr(cursor.getInt(i));
        i++;
        entity.setBaseAgi(cursor.getInt(i));
        i++;
        entity.setBaseInt(cursor.getInt(i));
        i++;
        entity.setStrGain(cursor.getFloat(i));
        i++;
        entity.setAgiGain(cursor.getFloat(i));
        i++;
        entity.setIntGain(cursor.getFloat(i));
        i++;
        entity.setPrimaryStat(cursor.getInt(i));
        i++;
        entity.setBaseAttackTime(cursor.getFloat(i));
        i++;
        entity.setDayVision(cursor.getInt(i));
        i++;
        entity.setNightVision(cursor.getInt(i));
        i++;
        entity.setAttackPoint(cursor.getFloat(i));
        i++;
        entity.setAttackSwing(cursor.getFloat(i));
        i++;
        entity.setCastPoint(cursor.getFloat(i));
        i++;
        entity.setCastSwing(cursor.getFloat(i));
        i++;
        entity.setTurnrate(cursor.getFloat(i));
        i++;
        entity.setLegs(cursor.getInt(i));

        return entity;
    }

    @Override
    protected ContentValues entityToContentValues(HeroStats entity) {
        ContentValues values = super.entityToContentValues(entity);
        values.put(COLUMN_PATCH, entity.getPatch());
        values.put(COLUMN_ALIGNMENT, entity.getAlignment());
        values.put(COLUMN_MOVESPEED, entity.getMovespeed());
        values.put(COLUMN_MAX_DMG, entity.getMaxDmg());
        values.put(COLUMN_MIN_DMG, entity.getMinDmg());
        values.put(COLUMN_HP, entity.getHP());
        values.put(COLUMN_MANA, entity.getMana());
        values.put(COLUMN_HP_REGEN, entity.getHPRegen());
        values.put(COLUMN_MANA_REGEN, entity.getManaRegen());
        values.put(COLUMN_ARMOR, entity.getArmor());
        values.put(COLUMN_RANGE, entity.getRange());
        values.put(COLUMN_PROJECTILE_SPEED, entity.getProjectileSpeed());
        values.put(COLUMN_BASE_STR, entity.getBaseStr());
        values.put(COLUMN_BASE_AGI, entity.getBaseAgi());
        values.put(COLUMN_BASE_INT, entity.getBaseInt());
        values.put(COLUMN_STR_GAIN, entity.getStrGain());
        values.put(COLUMN_AGI_GAIN, entity.getAgiGain());
        values.put(COLUMN_INT_GAIN, entity.getIntGain());
        values.put(COLUMN_PRIMARY_STAT, entity.getPrimaryStat());
        values.put(COLUMN_BASE_ATTACK_TIME, entity.getBaseAttackTime());
        values.put(COLUMN_DAY_VISION, entity.getDayVision());
        values.put(COLUMN_NIGHT_VISION, entity.getNightVision());
        values.put(COLUMN_ATTACK_POINT, entity.getAttackPoint());
        values.put(COLUMN_ATTACK_SWING, entity.getAttackSwing());
        values.put(COLUMN_CAST_POINT, entity.getCastPoint());
        values.put(COLUMN_CAST_SWING, entity.getCastSwing());
        values.put(COLUMN_TURNRATE, entity.getTurnrate());
        values.put(COLUMN_LEGS, entity.getLegs());
        if (entity.getRoles() != null) {
            StringBuilder resultRoles = new StringBuilder("");
            for (String role : entity.getRoles()) {
                resultRoles.append(role).append(",");
            }
            if (!TextUtils.isEmpty(resultRoles)) {
                resultRoles.substring(0, resultRoles.length() - 1);
                values.put(COLUMN_ROLES, resultRoles.toString());
            }
        }
        return values;
    }

    public HeroStats getShortHeroStats(SQLiteDatabase database, long id) {
        Cursor cursor = database.query(getTableName(), new String[]{COLUMN_ROLES, COLUMN_PRIMARY_STAT}, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        try {
            HeroStats entity = null;
            if (cursor.moveToFirst()) {
                entity = new HeroStats();
                entity.setRoles(rolesFromString(cursor.getString(cursor.getColumnIndex(COLUMN_ROLES))));
                entity.setPrimaryStat(cursor.getInt(cursor.getColumnIndex(COLUMN_PRIMARY_STAT)));
            }
            return entity;
        } finally {
            cursor.close();
        }
    }

    private String[] rolesFromString(String before) {
        return TextUtils.isEmpty(before) ? null : before.split(",");
    }
}
