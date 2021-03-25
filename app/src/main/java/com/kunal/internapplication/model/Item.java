package com.kunal.internapplication.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private String title;
    private List<Bitmap> bitmaps;

    public Item() {
    }

    public Item(String title, List<Bitmap> bitmaps) {
        this.title = title;
        this.bitmaps = bitmaps;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Bitmap> getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(List<Bitmap> bitmaps) {
//        if (this.bitmaps == null)
////            this.bitmaps = new ArrayList<>(bitmaps);
            this.bitmaps = bitmaps;
//        else
//            this.bitmaps.addAll(bitmaps);
    }
}
