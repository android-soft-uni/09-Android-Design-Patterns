package com.softuni.databasedemo.data.local.better.daos;


import android.content.Context;
import android.database.Cursor;

import com.softuni.databasedemo.data.local.better.records.ProductRecord;

import static com.softuni.databasedemo.data.local.better.DatabaseInitializer.CREATE_TABLE;

public class ProductsDao extends BaseDao<ProductRecord> {
    public static final String TABLE_NAME = "products";

    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_ADDRESS = "ADDRESS";
    public static final String COLUMN_TYPE = "TYPE";
    public static final String COLUMN_SERIAL_NUMBER = "SERIAL_NUMBER";

    public final static String CREATE_TABLE_QUERY = CREATE_TABLE + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_NAME + " TEXT, "
            + COLUMN_ADDRESS + " TEXT, "
            + COLUMN_TYPE + " TEXT, "
            + COLUMN_SERIAL_NUMBER + " TEXT "
            + " );";

    public ProductsDao(Context context) {
        super(context);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public ProductRecord fromCursor(Cursor cursor) {
        return new ProductRecord().fromCursor(cursor);
    }

    public ProductRecord findByType(String type) {
        return findByColumn(COLUMN_TYPE, type);
    }

    public ProductRecord insert(String name, String address, String type, String serialNumber) {
        ProductRecord record = new ProductRecord();
        record.setName(name);
        record.setAddress(address);
        record.setType(type);
        record.setSerial(serialNumber);

        super.insert(record);
        return record;
    }

    public void updateProductRecord(ProductRecord record) {
        update(record);
    }
}
