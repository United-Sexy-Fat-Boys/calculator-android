package com.example.asus.calculator.ui.activity;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.persistent.Product;
import com.example.asus.calculator.tools.adapter.ProductAdapter;
import com.example.asus.calculator.tools.adapter.SuggestionsProductAdapter;
import com.example.asus.calculator.tools.loader.ProductLoadTask;
import com.example.asus.calculator.tools.loader.ResponseListener;
import com.example.asus.calculator.tools.loader.SuggestionProductLoader;
import com.example.asus.calculator.util.MagicConstants;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends ListActivity implements NavigationView.OnNavigationItemSelectedListener,
        SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = SearchActivity.class.getSimpleName();

    private DrawerLayout drawerLayout;
    private NavigationView drawer;
    private ProductAdapter adapter;
    private SuggestionsProductAdapter suggestionsAdapter;
    private String mCurFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Log.i(MagicConstants.LOG_TAG, "search Activity created");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer = (NavigationView) findViewById(R.id.navigation_view);
        drawer.setNavigationItemSelectedListener(this);

        List<Product> list = new ArrayList<>();
        adapter = new ProductAdapter(this, list);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
       /* searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });*/

        suggestionsAdapter = new SuggestionsProductAdapter(this, null, true);
        searchView.setSuggestionsAdapter(suggestionsAdapter);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.d(MagicConstants.LOG_TAG, "query from search: " + query);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(MagicConstants.LOG_TAG, "onQueryTextSubmit: " + query);
        ProductLoadTask task = new ProductLoadTask(0, listener);
        task.execute(query, "3");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(MagicConstants.LOG_TAG, "onQueryTextChange: " + newText);
        String newFilter = !TextUtils.isEmpty(newText) ? newText : null;
        if (mCurFilter == null && newFilter == null) {
            return true;
        }

        if (mCurFilter != null && mCurFilter.equals(newFilter)) {
            return true;
        }
        mCurFilter = newFilter;

        getLoaderManager().restartLoader(0, null, this);
        return true;
    }

    private ResponseListener<Product> listener = new ResponseListener<Product>() {
        @Override
        public void onResponse(List<Product> list) {
            adapter.clear();
            adapter.addAll(list);
        }
    };


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        SuggestionProductLoader loader = new SuggestionProductLoader(this);
        loader.setProductName(mCurFilter);
        // FIXME: 10/27/2016 handle intent from category fragment
        loader.setCategory_id("3");
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        suggestionsAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        suggestionsAdapter.swapCursor(null);
    }
}
