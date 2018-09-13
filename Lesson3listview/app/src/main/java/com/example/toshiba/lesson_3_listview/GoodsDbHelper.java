package com.example.toshiba.lesson_3_listview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GoodsDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SecretShop.db";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ SecretShopContract.SecretShopEntry.TABLE_NAME + "(" +
            SecretShopContract.SecretShopEntry._ID + " INTEGER PRIMARY KEY, " +
            SecretShopContract.SecretShopEntry.FIELD_NAME + " TEXT," +
            SecretShopContract.SecretShopEntry.FIELD_PRICE + " INTEGER," +
            SecretShopContract.SecretShopEntry.FIELD_IMAGE + " INTEGER," +
            SecretShopContract.SecretShopEntry.FIELD_PICKED + " INTEGER DEFAULT 0)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + SecretShopContract.SecretShopEntry.TABLE_NAME;


    public GoodsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDownGrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
