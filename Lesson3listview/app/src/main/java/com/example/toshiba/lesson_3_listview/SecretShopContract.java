package com.example.toshiba.lesson_3_listview;

import android.provider.BaseColumns;

public class SecretShopContract {
    private SecretShopContract() {}

    public static class SecretShopEntry implements BaseColumns {
        public static final String TABLE_NAME = "SecretShopItems";
        public static final String FIELD_NAME = "name";
        public static final String FIELD_PRICE = "price";
        public static final String FIELD_IMAGE = "image";
        public static final String FIELD_PICKED = "picked";
    }

    public static String[] getProjection() {
        String[] projection = {
                BaseColumns._ID,
                SecretShopEntry.FIELD_NAME,
                SecretShopEntry.FIELD_PRICE,
                SecretShopEntry.FIELD_IMAGE,
                SecretShopEntry.FIELD_PICKED
        };

        return projection;
    }

}
