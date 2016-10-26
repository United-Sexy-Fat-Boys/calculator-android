package com.example.asus.calculator.tools.loader;

import com.example.asus.calculator.model.persistent.Entity;

import java.util.List;

public interface ResponseListener<T extends Entity> {
    void onResponse(List<T> list);
}
