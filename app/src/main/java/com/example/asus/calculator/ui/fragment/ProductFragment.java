package com.example.asus.calculator.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.tools.adapter.ProductPickedAdapter;
import com.example.asus.calculator.ui.activity.DishActivity;

import java.io.Serializable;
import java.util.List;

import static com.example.asus.calculator.util.MagicConstants.DISH_ACTIVITY_INTENT_EXTRA;
import static com.example.asus.calculator.util.MagicConstants.SEARCH_ACTIVITY_CHECKED_PRODUCTS;


public class ProductFragment extends ListFragment implements View.OnClickListener {
    private static final String LOG_TAG = ProductFragment.class.getSimpleName();

    private Button btnAdd;
    private List<ProductModel> productList;
    private ProductPickedAdapter adapter;

    public ProductFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = this.getArguments();
        productList = (List<ProductModel>) bundle.getSerializable(SEARCH_ACTIVITY_CHECKED_PRODUCTS);
        if (productList != null) {
            Log.i(LOG_TAG, "amount of products: " + productList.size());
        }

        adapter = new ProductPickedAdapter(getContext(), productList);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product, container, false);

        btnAdd = (Button) rootView.findViewById(R.id.btn_add_product);
        btnAdd.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnAdd.getId()) {
            Intent intent = new Intent(getActivity(), DishActivity.class);
            intent.putExtra(DISH_ACTIVITY_INTENT_EXTRA, R.id.fragment_category);
            intent.putExtra(SEARCH_ACTIVITY_CHECKED_PRODUCTS, (Serializable) productList);
            startActivity(intent);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_product, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_remove_products:
                Log.i(LOG_TAG, "menu item clicked: " + item.getItemId());
                adapter.removeChecked();
                adapter.notifyDataSetChanged();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
