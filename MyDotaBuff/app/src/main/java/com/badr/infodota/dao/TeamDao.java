package com.badr.infodota.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

import com.badr.infodota.api.matchdetails.Team;

/**
 * Created by ABadretdinov
 * 26.12.2014
 * 18:24
 */
public class TeamDao extends GeneralDaoImpl<Team> {
    public static final String TABLE_NAME = "team";

    public static final String COLUMN_LOGO_ID = "logo_id";
    public static final String COLUMN_LOGO = "logo";

    private static final String CREATE_TABLE_QUERY =
            "( " +
                    COLUMN_ID + " integer not null, " +
                    COLUMN_LOGO_ID + " integer default null, " +
                    COLUMN_LOGO + " text default null);";
    private static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_LOGO_ID,
            COLUMN_LOGO
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
    public Team cursorToEntity(Cursor cursor, int index) {
        Team team = new Team();
        int i = index;
        team.setId(cursor.getLong(i));
        i++;
        team.setTeamLogoId(cursor.getLong(i));
        i++;
        team.setLogo(cursor.getString(i));
        return team;
    }

    @Override
    protected ContentValues entityToContentValues(Team entity) {
        ContentValues values = super.entityToContentValues(entity);
        values.put(COLUMN_LOGO_ID, entity.getTeamLogoId());
        if (TextUtils.isEmpty(entity.getLogo())) {
            values.putNull(COLUMN_LOGO);
        } else {
            values.put(COLUMN_LOGO, entity.getLogo());
        }
        return values;
    }
}
