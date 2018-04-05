package com.softuni.databasedemo.presenters;

import android.util.Log;

import com.softuni.databasedemo.data.local.bad.DbConnectionManager;
import com.softuni.databasedemo.data.local.bad.ProductRecord;
import com.softuni.databasedemo.ui.MainActivity;

import java.util.List;

public class MainPresenter {

    private ViewListener viewListener;

    public void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public void onScreenShown() {
        List<ProductRecord> allProducts = getData();
        viewListener.showProductsAsList(allProducts);
    }

    private List<ProductRecord> getData() {
        DbConnectionManager dbConnectionManager = new DbConnectionManager(this);
        dbConnectionManager.addProduct("maratonki", 45.34);
        dbConnectionManager.addProduct("pantalon", 15.00);
        return dbConnectionManager.getAllProducts();
    }

    public interface ViewListener {

        void showProductsAsList(List<ProductRecord> allProducts);
    }
}
