package com.example.asus.calculator.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.persistent.Category;
import com.example.asus.calculator.tools.fragment.factory.FragmentFactory;
import com.example.asus.calculator.ui.fragment.CategoryFragment;

import static com.example.asus.calculator.util.MagicConstants.LOG_TAG;


public class DishActivity extends AppCompatActivity implements CategoryFragment.OnCategoryClickListener {
    private FragmentFactory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        factory = FragmentFactory.getInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_top, factory.getFragment(R.id.fragment_navigation));
        transaction.replace(R.id.frame_low, factory.getFragment(R.id.fragment_category));
        transaction.commit();
    }

    @Override
    public void onCategoryClick(Category category) {
        // TODO: 28.09.2016 transaction to call product fragment
        Log.d(LOG_TAG, "IN ACTIVITY: " + category.getName());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_low, factory.getFragment(R.id.fragment_product));
        transaction.commit();
    }
}
