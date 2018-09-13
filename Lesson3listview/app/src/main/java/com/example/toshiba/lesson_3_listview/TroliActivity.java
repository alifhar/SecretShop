package com.example.toshiba.lesson_3_listview;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class TroliActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troli);

        ArrayList<Goods> arrayOfItems = new ArrayList<Goods>();
        TroliAdapter adapter = new TroliAdapter(this, arrayOfItems);
        ListView listView = findViewById(R.id.lvTroli);
        listView.setAdapter(adapter);

        Log.d("cek", "Masuk");

        GoodsDbHelper myDbHelper = new GoodsDbHelper(getApplicationContext());
        SQLiteDatabase db = myDbHelper.getReadableDatabase();

        String[] condition = { "1" };
        Cursor cursor = db.query(
                SecretShopContract.SecretShopEntry.TABLE_NAME,
                SecretShopContract.getProjection(),
                SecretShopContract.SecretShopEntry.FIELD_PICKED + "=?",
                condition,
                null,
                null,
                null
        );

        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(SecretShopContract.SecretShopEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(SecretShopContract.SecretShopEntry.FIELD_NAME));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow(SecretShopContract.SecretShopEntry.FIELD_PRICE));
            int image = cursor.getInt(cursor.getColumnIndexOrThrow(SecretShopContract.SecretShopEntry.FIELD_IMAGE));
            boolean picked = (cursor.getInt(cursor.getColumnIndexOrThrow(SecretShopContract.SecretShopEntry.FIELD_PICKED)) == 1);

            adapter.add(new Goods(id, name, price, image, picked));
        }

        db.close();
        myDbHelper.close();
    }
}
