package com.softuni.databasedemo.data.local.better.records;


import android.content.ContentValues;
import android.database.Cursor;

import com.softuni.databasedemo.data.local.better.CursorConverter;

import java.io.Serializable;

public abstract class BaseRecord<T extends BaseRecord<T>> implements Serializable, CursorConverter<T> {
    private static final long serialVersionUID = 3020448186749167086L;

    private Long id;

    public abstract ContentValues getValues();

    protected abstract T parseFields(Cursor cursor);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
