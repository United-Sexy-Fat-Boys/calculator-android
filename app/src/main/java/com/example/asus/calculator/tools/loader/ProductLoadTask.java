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
    private static final String LOG_TAG = "ProductLoadTask";
    public static final long COUNT = 10;

    private long startIndex;
    private ResponseListener<Product> listener;

    /**
     * constructor for {@link ProductLoadTask}
     * @param startIndex - loading items, starting from specified index
     * @param listener - class, that handles returning {@link List} from AsyncTask
     */
    public ProductLoadTask(long startIndex, ResponseListener<Product> listener) {
        this.startIndex = startIndex;
        this.listener = listener;
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
        Log.i(LOG_TAG, "Loading categories...");
        String productName = params[0];
        String category_id = params[1];

        List<Product> list = null;
        try {
            Dao<Product, Long> dao = DBHelperFactory.getHelper().getProductDao();
            QueryBuilder<Product, Long> queryBuilder = dao.queryBuilder();

            queryBuilder.limit(COUNT).offset(startIndex).where().eq(Product.CATEGORY_ID, category_id).and()
                    .like(Product.NAME, String.format("%%%s%%", productName));

            list = dao.query(queryBuilder.prepare());
        } catch (SQLException e) {
            Log.e(MagicConstants.LOG_TAG, "Loading categories due calling onScroll");
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<Product> products) {
        listener.onResponse(products);
    }
}
