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
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.model.persistent.Category;
import com.example.asus.calculator.model.persistent.Product;
import com.example.asus.calculator.tools.adapter.ProductAdapter;
import com.example.asus.calculator.tools.adapter.SuggestionsProductAdapter;
import com.example.asus.calculator.tools.handler.SwitchButtonHandler;
import com.example.asus.calculator.tools.loader.LazyLoader;
import com.example.asus.calculator.tools.loader.ProductLoadTask;
import com.example.asus.calculator.tools.loader.ResponseListener;
import com.example.asus.calculator.tools.loader.SuggestionProductLoader;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends ListActivity implements NavigationView.OnNavigationItemSelectedListener,
        SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = SearchActivity.class.getSimpleName();
    private static final String CATEGORY_EXTRA = "Category";

    private DrawerLayout drawerLayout;
    private NavigationView drawer;
    private ProductAdapter adapter;
    private SuggestionsProductAdapter suggestionsAdapter;
    private FloatingActionButton floatButton;
    private SearchView searchView;

    private String mCurFilter;
    private String querySubmit;
    private Category category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Log.i(LOG_TAG, "search Activity created");
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer = (NavigationView) findViewById(R.id.navigation_view);
        drawer.setNavigationItemSelectedListener(this);

        SwitchButtonHandler handler = new SwitchButtonHandler();
        handler.addSwitchCompat((SwitchCompat) drawer.getMenu().findItem(R.id.switch_high).getActionView());
        handler.addSwitchCompat((SwitchCompat) drawer.getMenu().findItem(R.id.switch_medium).getActionView());
        handler.addSwitchCompat((SwitchCompat) drawer.getMenu().findItem(R.id.switch_low).getActionView());
        handler.listenCalorificSwitches();

        List<ProductModel> list = new ArrayList<>();
        adapter = new ProductAdapter(this, list);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        getListView().setOnScrollListener(new LazyLoader() {
            @Override
            public void loadMore(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (totalItemCount > 0) {
                    Log.i(LOG_TAG, "loadMore()");
                    ProductLoadTask task = new ProductLoadTask(adapter.getCount(), lazyListener);
                    task.execute(querySubmit, Long.toString(category.getId()));
                }
            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        suggestionsAdapter = new SuggestionsProductAdapter(this, null, true);
        searchView.setSuggestionsAdapter(suggestionsAdapter);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = suggestionsAdapter.getCursor();
                cursor.moveToPosition(position);
                String value = cursor.getString(cursor.getColumnIndex(Product.NAME));
                searchView.setQuery(value, true);
                return true;
            }
        });

        floatButton = (FloatingActionButton) findViewById(R.id.floating_button_product);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DishActivity.class);
                intent.putExtra(DishActivity.DISH_ACTIVITY_EXTRA, R.id.fragment_product);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra(CATEGORY_EXTRA);
        Log.i(LOG_TAG, "received category -> id: " + category.getId() + ", name: " + category.getName());
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
            searchView.setQuery(query, true);
            Log.d(LOG_TAG, "query from search: " + query);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.i(LOG_TAG, "onNavigationItemSelected()");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(LOG_TAG, "onQueryTextSubmit: " + query);
        searchView.clearFocus();
        querySubmit = query;
        ProductLoadTask task = new ProductLoadTask(0, listener);
        task.execute(query, Long.toString(category.getId()));
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(LOG_TAG, "onQueryTextChange: " + newText);
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

    private ResponseListener<ProductModel> listener = new ResponseListener<ProductModel>() {
        @Override
        public void onResponse(List<ProductModel> list) {
            adapter.clear();
            adapter.addAll(list);
        }
    };

    private ResponseListener<ProductModel> lazyListener = new ResponseListener<ProductModel>() {
        @Override
        public void onResponse(List<ProductModel> list) {
            adapter.addAll(list);
        }
    };


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        SuggestionProductLoader loader = new SuggestionProductLoader(this);
        loader.setProductName(mCurFilter);
        loader.setCategory_id(Long.toString(category.getId()));
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
