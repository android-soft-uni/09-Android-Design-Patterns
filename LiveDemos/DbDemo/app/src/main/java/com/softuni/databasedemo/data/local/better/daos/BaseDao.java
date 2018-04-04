package com.softuni.databasedemo.data.local.better.daos;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.softuni.databasedemo.data.local.better.CursorConverter;
import com.softuni.databasedemo.data.local.better.DatabaseInitializer;
import com.softuni.databasedemo.data.local.better.records.BaseRecord;

import java.util.ArrayList;
import java.util.List;

import static com.softuni.databasedemo.data.local.better.DatabaseInitializer.ARGUMENT_MATCHER;

public abstract class BaseDao<T extends BaseRecord> implements CursorConverter<T> {
    private static final String TAG = "BaseDao";

    protected static final String COLUMN_ID = "_ID";

    private DatabaseInitializer dbOpenHelper;

    public BaseDao(Context context) {
        this.dbOpenHelper = DatabaseInitializer.getInstance(context);
    }

    public long insert(T record) {
        record.setId(insert(record.getValues()));
        return record.getId();
    }

    public void insert(List<T> records) {
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        database.beginTransaction();
        for (T record : records) {
            record.setId(insert(record.getValues()));
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void update(T record) {
            update(record.getValues(), COLUMN_ID + "=?", new String[]{
                    String.valueOf(record.getId())
            });
    }

    public void update(List<T> records) {
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        database.beginTransaction();

        for (T record : records) {
            update(record.getValues(), COLUMN_ID + "=?", new String[]{
                    String.valueOf(record.getId()),
            });
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public T findById(long id) {
        try (Cursor query = query(null, COLUMN_ID + ARGUMENT_MATCHER, new String[]{String.valueOf(id)}, null, null, null, null)) {
            if (query != null && query.moveToFirst()) {
                return fromCursor(query);
            }
            return null;
        }
    }

    public List<T> findAll() {
        try (Cursor query = query(null, null, new String[]{}, null, null, null, null)) {
            return listFromCursor(query);
        }
    }

    protected T findByColumn(String columnName, String value) {
        try (Cursor cursor = query(null, columnName + ARGUMENT_MATCHER, new String[]{value}, null, null, null, null)) {
            if (notEmptyCursorMoveToFirst(cursor)) return fromCursor(cursor);
            return null;
        }
    }

    public boolean delete(T record) {
        return delete(COLUMN_ID + ARGUMENT_MATCHER, new String[]{String.valueOf(record.getId())}) > 0;
    }

    public abstract String getTableName();

    public List<T> listFromCursor(Cursor cursor) {
        ArrayList<T> list = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                list.add(fromCursor(cursor));
            } while (cursor.moveToNext());
        }
        return list;
    }

    protected long insert(ContentValues values) {
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        return database.insert(getTableName(), "", values);
    }

    protected int update(ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        return database.update(getTableName(), values, whereClause, whereArgs);
    }

    protected Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        return database.query(getTableName(), columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    protected Cursor rawQuery(String query, String[] queryParams) {
        SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
        return database.rawQuery(query, queryParams);
    }

    protected int delete(String whereClause, String[] whereArgs) {
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        return database.delete(getTableName(), whereClause, whereArgs);
    }

    protected void execSQL(String sql) {
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        database.execSQL(sql);
    }

    protected boolean notEmptyCursor(Cursor cursor) {
        return !isCursorEmpty(cursor);
    }

    protected boolean isCursorEmpty(Cursor cursor) {
        return cursor == null || cursor.getCount() == 0;
    }

    protected boolean notEmptyCursorMoveToFirst(Cursor cursor) {
        boolean notEmpty = notEmptyCursor(cursor);
        if (notEmpty) {
            cursor.moveToFirst();
        }
        return notEmpty;
    }

    protected boolean checkEmptyAndClose(Cursor cursor) {
        if (cursor == null) {
            return true;
        }
        if (cursor.getCount() == 0) {
            cursor.close();
            return true;
        }
        return false;
    }
}
