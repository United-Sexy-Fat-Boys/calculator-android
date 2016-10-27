package com.example.asus.calculator.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.persistent.Category;
import com.example.asus.calculator.tools.adapter.CategoryAdapter;
import com.example.asus.calculator.tools.loader.CategoryLoadTask;
import com.example.asus.calculator.tools.loader.LazyLoader;
import com.example.asus.calculator.tools.loader.ResponseListener;
import com.example.asus.calculator.ui.activity.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.asus.calculator.util.MagicConstants.ERR_MSG;


public class CategoryFragment extends Fragment {
    private static final String LOG_TAG = CategoryFragment.class.getSimpleName();
    private GridView gridView;
    private CategoryAdapter adapter;
    private OnCategoryClickListener categoryListener;

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            categoryListener = (OnCategoryClickListener) context;
        } catch (ClassCastException e) {
            Log.e(LOG_TAG, ERR_MSG, e);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);

        gridView = (GridView) rootView.findViewById(R.id.grid_view_fragment_category);
        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        final List<Category> list = new ArrayList<>();
        adapter = new CategoryAdapter(getContext(), list);
        gridView.setAdapter(adapter);

        gridView.setOnScrollListener(new LazyLoader() {
            @Override
            public void loadMore() {
                int startIndex = adapter.getCount();
                CategoryLoadTask task = new CategoryLoadTask(startIndex, listener);
                task.setProgressBar(progressBar);
                task.execute();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mes = "pos: " + position + " content: " + list.get(position).getName();
                Log.i(LOG_TAG, "Picked category - " + mes);
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra("Category", list.get(position));
                startActivity(intent);
                //categoryListener.onCategoryClick(list.get(position));
            }
        });


        return rootView;
    }

    private ResponseListener<Category> listener = new ResponseListener<Category>() {
        @Override
        public void onResponse(List<Category> list) {
            adapter.addAll(list);
        }
    };
}
