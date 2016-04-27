package com.cl.qrcode.provider;

import java.util.ArrayList;
import java.util.List;

public class CreateInfo extends BaseData{
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private ArrayList<Item> items = new ArrayList<>();

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItems(List<Item> items) {
        this.items.addAll(items);
    }
}
