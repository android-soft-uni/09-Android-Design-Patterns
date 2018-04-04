package com.softuni.databasedemo.data.local.better;


import android.database.Cursor;

import com.softuni.databasedemo.data.local.better.records.BaseRecord;

public interface CursorConverter<T extends BaseRecord> {
    T fromCursor(Cursor cursor);
}