package com.softuni.databasedemo.data.local;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.softuni.databasedemo.data.local.bad.ProductRecord;
import com.softuni.databasedemo.data.local.best.AppDatabase;
import com.softuni.databasedemo.data.local.better.ProductsRepository;
import com.softuni.databasedemo.data.local.better.ProductsService;

/**
 * Created by teodo on 4/4/2018.
 */

@Module
public class DataModule {

    @Singleton
    @Provides
    public AppDatabase provideAppDatabase(Application application) {
        return Room.databaseBuilder(application,
                AppDatabase.class, "database-name").build();
    }

    @Provides
    public ProductsRepository getProductsRepository() {
        return new ProductsService();
    }
}
