package com.example.asus.calculator.util;

import com.example.asus.calculator.model.ProductModel;
import com.example.asus.calculator.model.persistent.Product;

import java.util.ArrayList;
import java.util.List;

public class ModelUtil {
    public static List<ProductModel> copyList(List<Product> list) {
        if (list != null) {
            List<ProductModel> modelList = new ArrayList<>(list.size());
            for (Product product : list) {
                modelList.add(new ProductModel(product));
            }

            return modelList;
        }
        return null;
    }

    public static List<ProductModel> getComparedList(List<ProductModel> old, List<ProductModel> fresh) {
        for (int i = 0; i < fresh.size(); i++) {
            boolean isEquals = false;
            for (int j = 0; j < old.size(); j++) {
                if (old.get(j).getId() == fresh.get(i).getId()) {
                    isEquals = true;
                }
            }

            if (!isEquals) {
                old.add(fresh.get(i));
            }
        }
        return old;
    }
}
