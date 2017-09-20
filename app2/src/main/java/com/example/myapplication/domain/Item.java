package com.example.myapplication.domain;

/**
 * Created by Administrator on 2017-09-20.
 */

public abstract class Item {

    public String name;
    public int viewType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
