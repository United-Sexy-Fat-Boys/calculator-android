package com.example.asus.calculator.tools.loader;

import android.os.AsyncTask;
import android.util.Log;

import com.example.asus.calculator.model.persistent.Product;
import com.example.asus.calculator.tools.db.DBHelperFactory;
import com.example.asus.calculator.util.MagicConstants;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class ProductLoadTask extends AsyncTask<String, Void, List<Product>> {
    public static final long COUNT = 10;

    private long startIndex;
    private ResponseListener<Product> listener;

    public ProductLoadTask(long startIndex, ResponseListener<Product> listener) {
        this.startIndex = startIndex;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * MUST receive two string parameters: name of product and id of category,
     * that need to search
     *
     * @param params [0] - productName, [1] - category_id
     * @return list with result
     */
    @Override
    protected List<Product> doInBackground(String... params) {
        Log.i(MagicConstants.LOG_TAG, "Loading categories...");
        String productName = params[0];
        List<Product> list = null;
        try {
            Dao<Product, Long> dao = DBHelperFactory.getHelper().getProductDao();
            QueryBuilder<Product, Long> queryBuilder = dao.queryBuilder();
            // FIXME: 10/25/2016 change to normal category
            queryBuilder.limit(COUNT).offset(startIndex).where().eq(Product.CATEGORY_ID, 3);
            list = dao.query(queryBuilder.prepare());
        } catch (SQLException e) {
            Log.e(MagicConstants.LOG_TAG, "Loading categories due to onScroll");
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<Product> products) {
        listener.onResponse(products);
    }
}
