package com.softuni.databasedemo.data.local.better;

import com.softuni.databasedemo.data.local.better.records.ProductRecord;

/**
 * Created by teodo on 4/4/2018.
 */

public interface ProductsRepository {

    public ProductRecord findByType(String type);

    public ProductRecord insert(String name, String address, String type, String serialNumber);

    public void updateProductRecord(ProductRecord record);
}
