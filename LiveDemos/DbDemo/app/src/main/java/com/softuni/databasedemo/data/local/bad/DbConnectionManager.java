package com.softuni.databasedemo.data.local.bad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbConnectionManager extends SQLiteOpenHelper {

    private final static String DB_NAME = "shop.db";
    private final static int DB_VERSION = 1;

    private final static String TABLE_NAME_PRODUCTS = "products";
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_NAME = "name";
    private final static String COLUMN_PRICE = "price";

    private final static String CREATE_PRODUCTS_TABLE =
            "CREATE TABLE " + TABLE_NAME_PRODUCTS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_PRICE + " REAL);";

    public DbConnectionManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCTS);
        onCreate(sqLiteDatabase);
    }

    public void addProduct(String productName, double price) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, productName);
        contentValues.put(COLUMN_PRICE, price);
        getWritableDatabase().insert(TABLE_NAME_PRODUCTS, null, contentValues);
    }

    public List<ProductRecord> getAllProducts() {
        List<ProductRecord> products = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(
                TABLE_NAME_PRODUCTS,
                null,
                null,
                null,
                null,
                null,
                COLUMN_ID + " DESC"
        );
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
            products.add(new ProductRecord(id, name, price));
        }
        return products;
    }
}
