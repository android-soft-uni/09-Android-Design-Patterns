package com.softuni.databasedemo.data.local.better.records;


import android.content.ContentValues;
import android.database.Cursor;

import static com.softuni.databasedemo.data.local.better.daos.ProductsDao.COLUMN_ADDRESS;
import static com.softuni.databasedemo.data.local.better.daos.ProductsDao.COLUMN_NAME;
import static com.softuni.databasedemo.data.local.better.daos.ProductsDao.COLUMN_SERIAL_NUMBER;
import static com.softuni.databasedemo.data.local.better.daos.ProductsDao.COLUMN_TYPE;

public class ProductRecord extends BaseRecord<ProductRecord> {

    private String name;
    private String address;
    private String type;
    private String serial;

    @Override
    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_SERIAL_NUMBER, serial);
        return values;
    }

    @Override
    protected ProductRecord parseFields(Cursor cursor) {
        setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
        setAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
        setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
        setSerial(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SERIAL_NUMBER)));
        return this;
    }

    @Override
    public ProductRecord fromCursor(Cursor cursor) {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
