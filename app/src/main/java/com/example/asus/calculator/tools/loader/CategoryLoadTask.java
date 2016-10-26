package com.example.asus.calculator.tools.loader;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.asus.calculator.model.persistent.Category;
import com.example.asus.calculator.tools.db.DBHelperFactory;
import com.example.asus.calculator.util.MagicConstants;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;


public class CategoryLoadTask extends AsyncTask<Void, Void, List<Category>> {
    private static final long COUNT = 6;

    private long startIndex;
    private ResponseListener<Category> listener;
    private ProgressBar progressBar;

    public CategoryLoadTask(long startIndex, ResponseListener<Category> listener) {
        this.startIndex = startIndex;
        this.listener = listener;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected List<Category> doInBackground(Void... params) {
        Log.i(MagicConstants.LOG_TAG, "Loading categories...");

        List<Category> list = null;
        try {
            Dao<Category, Long> dao = DBHelperFactory.getHelper().getCategoryDao();
            QueryBuilder<Category, Long> queryBuilder = dao.queryBuilder();
            queryBuilder.limit(COUNT).offset(startIndex);
            list = dao.query(queryBuilder.prepare());
        } catch (SQLException e) {
            Log.e(MagicConstants.LOG_TAG, "Loading categories due to onScroll");
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<Category> categories) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        listener.onResponse(categories);
    }
}
