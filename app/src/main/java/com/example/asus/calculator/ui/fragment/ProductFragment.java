package com.example.asus.calculator.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.persistent.Product;
import com.example.asus.calculator.tools.adapter.ProductAdapter;
import com.example.asus.calculator.util.ModelUtil;

import java.util.ArrayList;


public class ProductFragment extends ListFragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ArrayList<Product> list = new ArrayList<>();
        Product product = new Product();
        product.setName("milk");
        product.setCalories(123);
        list.add(product);

        product = new Product();
        product.setName("cacao");
        product.setCalories(12);
        list.add(product);

        product = new Product();
        product.setName("cabbage");
        product.setCalories(175);
        list.add(product);

        product = new Product();
        product.setName("coffee");
        product.setCalories(1652);
        list.add(product);

        product = new Product();
        product.setName("watermelon");
        product.setCalories(765);
        list.add(product);

        product = new Product();
        product.setName("Product with giant consistency of calorie");
        product.setCalories(123);
        list.add(product);

        product = new Product();
        product.setName("banana");
        product.setCalories(12);
        list.add(product);

        product = new Product();
        product.setName("potato");
        product.setCalories(78256858);
        list.add(product);

        product = new Product();
        product.setName("Carrot");
        product.setCalories(1);
        list.add(product);


        ProductAdapter adapter = new ProductAdapter(getContext(), ModelUtil.copyList(list));
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
