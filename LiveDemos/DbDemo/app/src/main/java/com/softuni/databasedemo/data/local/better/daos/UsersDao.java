package com.softuni.databasedemo.data.local.better.daos;


import android.content.Context;
import android.database.Cursor;

import com.softuni.databasedemo.data.local.better.records.UserRecord;

public class UsersDao extends BaseDao<UserRecord> {

    public static final java.lang.String TABLE_NAME = "users";
    public static final java.lang.String COLUMN_EMAIL = "email";
    public static final java.lang.String COLUMN_WALLET = "wallet";

    public static final java.lang.String CREATE_TABLE_QUERY = "CREATE TABLE " +
            TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_EMAIL+ " TEXT," +
            COLUMN_WALLET + " REAL);";

    public UsersDao(Context context) {
        super(context);
    }

    @Override
    public UserRecord fromCursor(Cursor cursor) {
        return new UserRecord().fromCursor(cursor);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
