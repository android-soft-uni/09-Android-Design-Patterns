package com.softuni.databasedemo.data.local.bad;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "app.db";

    public static final String TABLE_NAME = "products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_PRICE = "price";

    private static final java.lang.String SQL_CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_NAME_TITLE + " TEXT," +
            COLUMN_NAME_PRICE + " REAL)";

    private static final java.lang.String SQL_DELETE_TABLE_PRODUCTS = "DROP TABLE IF EXISTS " + TABLE_NAME;;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE_PRODUCTS);
        onCreate(db);
    }

    public long add(String name, double price) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE, name);
        values.put(COLUMN_NAME_PRICE, price);

        return db.insert(TABLE_NAME, null, values);
    }

    public String getProductNameById(int id) {
        Cursor cursor = getWritableDatabase().query(
                TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                COLUMN_ID + " ?",              // The columns for the WHERE clause
                new String[] {String.valueOf(id)},          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                "ASC"               // The sort order
        );
        if(cursor.moveToNext()) {
            String itemName = cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_NAME_TITLE));
            cursor.close();
            return itemName;
        }
        return null;
    }
}
