package com.example.asus.calculator.tools.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.asus.calculator.R;
import com.example.asus.calculator.model.ProductModel;

import java.util.List;

public class ProductRecycleAdapter extends RecyclerView.Adapter<ProductRecycleAdapter.ViewHolder> {
    private List<ProductModel> list;
    private Context context;

    public ProductRecycleAdapter(List<ProductModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, null, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ProductModel model = list.get(position);
        holder.tvName.setText(model.getName());
        String text = String.format("%s %s", model.getCalories(),
                context.getResources().getString(R.string.textView_secondary_list_product));
        holder.tvCalorie.setText(text);
        holder.checkBox.setChecked(model.isChecked());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setChecked(!model.isChecked());
                holder.checkBox.setChecked(model.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<ProductModel> newList) {
        list.addAll(newList);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvCalorie;
        CheckBox checkBox;

        ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_product_name);
            tvCalorie = (TextView) view.findViewById(R.id.tv_calorie);
            checkBox = (CheckBox) view.findViewById(R.id.cb_product_odd);
        }
    }
}
