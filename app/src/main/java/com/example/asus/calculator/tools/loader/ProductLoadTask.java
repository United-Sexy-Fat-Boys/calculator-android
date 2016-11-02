package com.example.asus.calculator.tools.loader;

import android.os.AsyncTask;
import android.util.Log;

import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.model.persistent.Product;
import com.example.asus.calculator.tools.db.DBHelperFactory;
import com.example.asus.calculator.util.ModelUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class ProductLoadTask extends AsyncTask<String, Void, List<ProductModel>> {
    private static final String LOG_TAG = ProductLoadTask.class.getSimpleName();
    public static final long COUNT = 10;

    private long startIndex;
    private ResponseListener<ProductModel> listener;

    /**
     * constructor for {@link ProductLoadTask}
     * @param startIndex - loading items, starting from specified index
     * @param listener - class, that handles returning {@link List} from AsyncTask
     */
    public ProductLoadTask(long startIndex, ResponseListener<ProductModel> listener) {
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
    protected List<ProductModel> doInBackground(String... params) {
        Log.i(LOG_TAG, "Loading products...");
        String productName = params[0];
        String category_id = params[1];

        List<ProductModel> listModel = null;
        try {
            Dao<Product, Long> dao = DBHelperFactory.getHelper().getProductDao();
            QueryBuilder<Product, Long> queryBuilder = dao.queryBuilder();

            queryBuilder.limit(COUNT).offset(startIndex).where().eq(Product.CATEGORY_ID, category_id).and()
                    .like(Product.NAME, String.format("%%%s%%", productName));

            List<Product> list = dao.query(queryBuilder.prepare());
            listModel = ModelUtil.copyList(list);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Loading products due calling onScroll");
        }

        return listModel;
    }

    @Override
    protected void onPostExecute(List<ProductModel> products) {
        listener.onResponse(products);
    }
}
