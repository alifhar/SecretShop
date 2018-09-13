package com.example.toshiba.lesson_3_listview;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    int cost=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayList<Goods> arrayOfItems = new ArrayList<Goods>();
        GoodsAdapter adapter = new GoodsAdapter(this, arrayOfItems);
        ListView listView = findViewById(R.id.lvItems);
        listView.setAdapter(adapter);

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

        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(SecretShopContract.SecretShopEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(SecretShopContract.SecretShopEntry.FIELD_NAME));
            int price = cursor.getInt(cursor.getColumnIndexOrThrow(SecretShopContract.SecretShopEntry.FIELD_PRICE));
            int image = cursor.getInt(cursor.getColumnIndexOrThrow(SecretShopContract.SecretShopEntry.FIELD_IMAGE));
            boolean picked = (cursor.getInt(cursor.getColumnIndexOrThrow(SecretShopContract.SecretShopEntry.FIELD_PICKED)) == 1);

            if (picked) {
                CheckSelected(price);
            }
            adapter.add(new Goods(id, name, price, image, picked));
        }

        db.close();
        cursor.close();
        myDbHelper.close();
    }

    public void CheckSelected(int change) {
        GoodsDbHelper myDbHelper = new GoodsDbHelper(getApplicationContext());
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        String items = String.valueOf(DatabaseUtils.queryNumEntries(db, SecretShopContract.SecretShopEntry.TABLE_NAME, SecretShopContract.SecretShopEntry.FIELD_PICKED + "=1"));
        db.close();
        TextView textView = findViewById(R.id.selected);
        if(items.equals("0"))
            textView.setText("");
        else if (items.equals("1"))
            textView.setText(items + " item selected");
        else
            textView.setText(items + " items selected");

        cost += change;
        TextView totalPrice = findViewById(R.id.totalPrice);

        DecimalFormatSymbols format = new DecimalFormatSymbols();
        format.setCurrencySymbol("Rp. ");
        format.setGroupingSeparator('.');
        format.setMonetaryDecimalSeparator(',');
        DecimalFormat idr = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        idr.setDecimalFormatSymbols(format);

        if(cost==0)
            totalPrice.setText("");
        else
            totalPrice.setText("Total Price : "+idr.format(cost));
    }

}
