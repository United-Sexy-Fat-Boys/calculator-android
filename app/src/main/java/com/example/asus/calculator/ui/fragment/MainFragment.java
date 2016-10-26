package com.example.asus.calculator.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.asus.calculator.R;
import com.example.asus.calculator.ui.activity.DishActivity;
import com.example.asus.calculator.ui.activity.SearchActivity;


public class MainFragment extends Fragment {
    private Button btnLower;
    private Button btnUpper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        btnLower = (Button) rootView.findViewById(R.id.btnLower);
        btnUpper = (Button) rootView.findViewById(R.id.btnUpper);

        btnLower.setOnClickListener(new LowerOnClickListener());
        btnUpper.setOnClickListener(new UpperOnClickListener());
        return rootView;
    }

    private class LowerOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), DishActivity.class);
            startActivity(intent);
        }
    }

    // FIXME: 10/9/2016 temporary solution
    private class UpperOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivity(intent);
        }
    }
}
