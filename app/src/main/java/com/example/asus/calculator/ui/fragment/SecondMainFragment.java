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
import com.example.asus.calculator.tools.db.DBHelperFactory;
import com.j256.ormlite.dao.Dao;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import static com.example.asus.calculator.util.MagicConstants.LOG_TAG;


public class SecondMainFragment extends Fragment {
    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_second, container, false);

        button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dao<Category, Long> dao = DBHelperFactory.getHelper().getCategoryDao();
                    dao.executeRaw("DELETE FROM category");
                    InputStream is = getResources().openRawResource(R.raw.insert_category);
                    DataInputStream in = new DataInputStream(is);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        dao.updateRaw(strLine);
                    }
                    in.close();
                    Log.i(LOG_TAG, "Created category");
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return rootView;
    }
}
