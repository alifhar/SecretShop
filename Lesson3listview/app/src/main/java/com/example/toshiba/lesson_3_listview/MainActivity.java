package com.example.toshiba.lesson_3_listview;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatabase();
    }

    public void goToList(View view) {
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(intent);
    }

    public void goToTroli(View view) {
        Intent intent = new Intent(getApplicationContext(), TroliActivity.class);
        startActivity(intent);
    }

    public void initDatabase() {
        GoodsDbHelper myDbHelper = new GoodsDbHelper(getApplicationContext());
        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                SecretShopContract.SecretShopEntry.TABLE_NAME,
                SecretShopContract.getProjection(),
                null,
                null,
                null,
                null,
                null
        );

        if(!cursor.moveToNext()) {
            db = myDbHelper.getWritableDatabase();

            Goods[] goods = new Goods[] {
                    new Goods("Acer Swift", 6100000, R.drawable.acersiwft),
                    new Goods("Aspire 15", 5600000, R.drawable.aspire15),
                    new Goods("Asus 17.3 Gaming Laptop", 1299000, R.drawable.asusgaming),
                    new Goods("Asus Special Red", 7320000, R.drawable.asusred),
                    new Goods("Dell Chromebook", 5100000, R.drawable.chromebook),
                    new Goods("Dell XPS", 7800000, R.drawable.dellxps),
                    new Goods("HP Stream", 5811000, R.drawable.hpstream),
                    new Goods("Apple Macbook Air", 8990000, R.drawable.macbookair),
                    new Goods("Apple Pixelbook", 9299000, R.drawable.pixelbook),
                    new Goods("Asus Vivobook", 9500000, R.drawable.vivobook)
            };

            for (Goods item : goods){
                ContentValues values = putValue(item.name, item.price, item.image);
                db.insert(SecretShopContract.SecretShopEntry.TABLE_NAME, null, values);
            }
        }
        db.close();
        myDbHelper.close();
    }

    public ContentValues putValue(String name, int price, int image) {
        ContentValues values = new ContentValues();
        values.put(SecretShopContract.SecretShopEntry.FIELD_NAME, name);
        values.put(SecretShopContract.SecretShopEntry.FIELD_PRICE, price);
        values.put(SecretShopContract.SecretShopEntry.FIELD_IMAGE, image);
        return  values;
    }
}
