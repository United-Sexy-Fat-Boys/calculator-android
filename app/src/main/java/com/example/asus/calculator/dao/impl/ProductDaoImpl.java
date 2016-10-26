package com.example.asus.calculator.dao.impl;

import com.example.asus.calculator.dao.ProductDao;
import com.example.asus.calculator.model.persistent.Product;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;


public class ProductDaoImpl extends BaseDaoImpl<Product, Long> implements ProductDao {
    public ProductDaoImpl(Class<Product> dataClass) throws SQLException {
        super(dataClass);
    }

    public ProductDaoImpl(ConnectionSource connectionSource, Class<Product> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ProductDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Product> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
