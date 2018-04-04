package com.softuni.databasedemo;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.softuni.databasedemo.data.local.best.AppDatabase;
import com.softuni.databasedemo.data.local.best.User;
import com.softuni.databasedemo.data.local.better.daos.ProductsDao;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProductsDao productsDao = new ProductsDao(this);
        productsDao.insert("asd", "asd12", "asd23", "asd34");
        productsDao.findAll();
        productsDao.findByType("asd23");

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        db.userDao().insertAll(new User());
    }
}
