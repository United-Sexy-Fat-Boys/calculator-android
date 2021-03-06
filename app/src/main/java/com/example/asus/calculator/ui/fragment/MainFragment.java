package com.example.asus.calculator.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.persistent.Category;
import com.example.asus.calculator.tools.adapter.picasso.CircleTransformation;
import com.example.asus.calculator.ui.activity.DishActivity;
import com.example.asus.calculator.ui.activity.SearchActivity;
import com.example.asus.calculator.util.MagicConstants;
import com.squareup.picasso.Picasso;


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
        ImageView imageView = (ImageView) rootView.findViewById(R.id.iv_main_fragment);
        Picasso.with(getActivity())
                .load(R.drawable.main_pic)
                //.resize(600, 600)
                .fit()
                .centerCrop()
                .transform(new CircleTransformation())
                .into(imageView);
        return rootView;
    }

    private class LowerOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), DishActivity.class);
            intent.putExtra(MagicConstants.DISH_ACTIVITY_INTENT_EXTRA, R.id.fragment_category);
            startActivity(intent);
        }
    }

    // FIXME: 10/9/2016 temporary solution
    private class UpperOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            // FIXME: 10/27/2016 remove after testing
            Category category = new Category();
            category.setName("Meat");
            category.setId(3L);
            intent.putExtra("Category", category);
            startActivity(intent);
        }
    }
}
