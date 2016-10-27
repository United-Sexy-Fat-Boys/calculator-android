package com.example.asus.calculator.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.persistent.Category;
import com.example.asus.calculator.model.persistent.Product;
import com.example.asus.calculator.tools.db.DBHelperFactory;
import com.j256.ormlite.dao.Dao;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import static com.example.asus.calculator.util.MagicConstants.LOG_TAG;

//SUPER BAD CODE!!!!
public class SecondMainFragment extends Fragment {
    private Button btnCategories;
    private Button btnProducts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_second, container, false);

        btnCategories = (Button) rootView.findViewById(R.id.button_load_categories);
        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dao<Category, Long> dao = DBHelperFactory.getHelper().getCategoryDao();
                    load(dao, R.raw.insert_category);
                    Log.i(LOG_TAG, "Created category");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        btnProducts = (Button) rootView.findViewById(R.id.button_load_products);
        btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dao<Product, Long> dao = DBHelperFactory.getHelper().getProductDao();
                    load(dao, R.raw.insert_product);
                    Log.i(LOG_TAG, "Created product");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        return rootView;
    }

    private <T, ID> void load(Dao<T, ID> dao, int resId) throws SQLException {
        dao.executeRaw("DELETE FROM " + dao.getTableName());
        InputStream is = getResources().openRawResource(resId);
        DataInputStream in = new DataInputStream(is);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        try {
            while ((strLine = br.readLine()) != null) {
                dao.updateRaw(strLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}