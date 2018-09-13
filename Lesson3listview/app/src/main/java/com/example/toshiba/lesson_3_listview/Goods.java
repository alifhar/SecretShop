package com.example.toshiba.lesson_3_listview;

import android.graphics.drawable.Drawable;

public class Goods {
    public String id;
    public String name;
    public int price;
    public int image;
    public boolean picked = false;

    public Goods(String name, int price, int image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Goods(String id, String name, int price, int image, boolean picked) {
        this.id = id;
        this.name =name;
        this.price = price;
        this.image = image;
        this.picked = picked;
    }

}
