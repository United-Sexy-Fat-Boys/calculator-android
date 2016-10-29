package com.example.asus.calculator.model;

import com.example.asus.calculator.model.persistent.Product;

public class ProductModel {
    private Product product = new Product();
    private boolean isChecked;

    public ProductModel() {
    }

    public ProductModel(Product product) {
        this.product.setId(product.getId());
        this.product.setName(product.getName());
        this.product.setCalories(product.getCalories());
        this.product.setCategory(product.getCategory());
    }

    public long getId() {
        return product.getId();
    }

    public void setId(long id) {
        product.setId(id);
    }

    public String getName() {
        return product.getName();
    }

    public void setName(String name) {
        product.setName(name);
    }

    public long getCalories() {
        return product.getCalories();
    }

    public void setCalories(long calories) {
        product.setCalories(calories);
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
