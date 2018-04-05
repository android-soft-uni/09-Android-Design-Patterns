package com.softuni.databasedemo.data.local.better.records;

import android.content.ContentValues;
import android.database.Cursor;

import com.softuni.databasedemo.data.local.better.daos.UsersDao;

import static com.softuni.databasedemo.data.local.better.daos.UsersDao.COLUMN_EMAIL;

public class UserRecord extends BaseRecord<UserRecord> {

    private String email;
    private double wallet;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    @Override
    public UserRecord fromCursor(Cursor cursor) {
        return null;
    }

    @Override
    public ContentValues getValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(UsersDao.COLUMN_WALLET, wallet);
        return contentValues;
    }

    @Override
    protected UserRecord parseFields(Cursor cursor) {
        UserRecord userRecord = new UserRecord();
        userRecord.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
        return userRecord;
    }
}
