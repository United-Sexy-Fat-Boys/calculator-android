package com.example.asus.calculator.model;

public class ProductModel {
    private String name;
    private String calorie;
    private boolean isChecked;

    public ProductModel() {
    }

    // FIXME: 10/5/2016 remove
    public ProductModel(String name, String calorie) {
        this.name = name;
        this.calorie = calorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
