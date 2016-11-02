package com.example.asus.calculator.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.tools.adapter.ProductAdapter;

import java.util.List;

import static com.example.asus.calculator.util.MagicConstants.SEARCH_ACTIVITY_CHECKED_PRODUCTS;


public class ProductFragment extends ListFragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = this.getArguments();
        List<ProductModel> productList = (List<ProductModel>) bundle.getSerializable(SEARCH_ACTIVITY_CHECKED_PRODUCTS);
        ProductAdapter adapter = new ProductAdapter(getContext(), productList);
        setListAdapter(adapter);
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product, container, false);

        return rootView;
    }
}
