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
}
