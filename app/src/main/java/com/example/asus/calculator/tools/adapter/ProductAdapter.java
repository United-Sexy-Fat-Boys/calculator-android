package com.example.asus.calculator.tools.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.persistent.Product;

import java.util.List;


public class ProductAdapter extends ArrayAdapter<Product> {
    private List<Product> list;
    private LayoutInflater inflater;

    public ProductAdapter(Context context, List<Product> list) {
        super(context, 0, list);
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_product, null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_product_name);
            holder.tvCalorie = (TextView) convertView.findViewById(R.id.tv_calorie);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_product_odd);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(list.get(position).getName());
        holder.tvCalorie.setText(list.get(position).getCalories() + " cal");
        holder.checkBox.setChecked(false);
        return convertView;
    }

    private static class ViewHolder {
        TextView tvName;
        TextView tvCalorie;
        CheckBox checkBox;
    }
}
