package com.example.asus.calculator.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.asus.calculator.R;
import com.example.asus.calculator.tools.fragment.factory.FragmentFactory;
import com.example.asus.calculator.util.MagicConstants;


public class DishActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        FragmentFactory factory = FragmentFactory.getInstance();
        Intent intent = getIntent();
        int fragmentId = intent.getIntExtra(MagicConstants.DISH_ACTIVITY_INTENT_EXTRA, R.id.fragment_category);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_top, factory.getFragment(R.id.fragment_navigation));
        transaction.replace(R.id.frame_low, factory.getFragment(fragmentId));
        transaction.commit();
    }
}
