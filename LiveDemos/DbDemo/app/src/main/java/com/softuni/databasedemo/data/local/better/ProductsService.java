package com.softuni.databasedemo.data.local.better;

import com.softuni.databasedemo.data.local.better.records.ProductRecord;

public class ProductsService implements ProductsRepository {

    @Override
    public ProductRecord findByType(String type) {
        return new ProductRecord();
    }

    @Override
    public ProductRecord insert(String name, String address, String type, String serialNumber) {
        ProductRecord record = new ProductRecord();
        return record;
    }

    @Override
    public void updateProductRecord(ProductRecord record) {

    }
}


