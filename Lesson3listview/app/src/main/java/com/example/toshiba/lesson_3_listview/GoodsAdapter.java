package com.example.toshiba.lesson_3_listview;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class GoodsAdapter extends ArrayAdapter<Goods> {
    private Context list;
    public GoodsAdapter(Context context, ArrayList<Goods> goods) {
        super(context, 0, goods);
        list = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Goods goods = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_goods, parent, false);
            Button button = convertView.findViewById(R.id.pick);
            Goods item = getItem(position);
            if (item.picked) {
                Drawable drawable = convertView.getResources().getDrawable(R.drawable.delete, null);
                button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
                button.setBackgroundTintList(convertView.getResources().getColorStateList(R.color.warning, null));
            } else {
                Drawable drawable = convertView.getResources().getDrawable(R.drawable.ok, null);
                button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
                button.setBackgroundTintList(convertView.getResources().getColorStateList(R.color.okay, null));
            }
        }
        else {
            Button button = convertView.findViewById(R.id.pick);
            Goods item = getItem(position);
            if (item.picked) {
                Drawable drawable = convertView.getResources().getDrawable(R.drawable.delete, null);
                button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
                button.setBackgroundTintList(convertView.getResources().getColorStateList(R.color.warning, null));
            } else {
                Drawable drawable = convertView.getResources().getDrawable(R.drawable.ok, null);
                button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
                button.setBackgroundTintList(convertView.getResources().getColorStateList(R.color.okay, null));
            }
        }

        final Button btnPick = convertView.findViewById(R.id.pick);
        btnPick.setTag(position);
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Goods item = getItem(position);
                int changeTotalPrice;
                if (!item.picked) {
                    Drawable drawable = v.getResources().getDrawable(R.drawable.delete, null);
                    btnPick.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
                    btnPick.setBackgroundTintList(v.getResources().getColorStateList(R.color.warning, null));
                    item.picked = true;
                    changeTotalPrice = item.price;

                    GoodsDbHelper myDbHelper = new GoodsDbHelper(v.getContext());
                    SQLiteDatabase db = myDbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(SecretShopContract.SecretShopEntry.FIELD_PICKED, 1);

                    String[] id = { item.id };
                    db.update(
                            SecretShopContract.SecretShopEntry.TABLE_NAME,
                            values,
                            SecretShopContract.SecretShopEntry._ID + " =?",
                            id
                    );

                } else {
                    Drawable drawable = v.getResources().getDrawable(R.drawable.ok, null);
                    btnPick.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
                    btnPick.setBackgroundTintList(v.getResources().getColorStateList(R.color.okay, null));
                    item.picked = false;
                    changeTotalPrice = -item.price;

                    GoodsDbHelper myDbHelper = new GoodsDbHelper(v.getContext());
                    SQLiteDatabase db = myDbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(SecretShopContract.SecretShopEntry.FIELD_PICKED, 0);

                    String[] id = { item.id };
                    db.update(
                            SecretShopContract.SecretShopEntry.TABLE_NAME,
                            values,
                            SecretShopContract.SecretShopEntry._ID + " =?",
                            id
                    );
                }
                if (list instanceof ListActivity) {
                    ((ListActivity)list).CheckSelected(changeTotalPrice);
                }
            }
        });

        Button btnDetail = convertView.findViewById(R.id.detail);
        btnDetail.setTag(position);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), DetailActivity.class);
                int position = (int) v.getTag();
                Goods item = getItem(position);
                intent.putExtra("name", item.name);
                intent.putExtra("price", item.price);
                intent.putExtra("image", item.image);
                intent.putExtra("picked", item.picked);
                v.getContext().startActivity(intent);
            }
        });

        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        // Populate the data into the template view using the data object
        tvName.setText(goods.name);

        DecimalFormatSymbols format = new DecimalFormatSymbols();
        format.setCurrencySymbol("Rp. ");
        format.setGroupingSeparator('.');
        format.setMonetaryDecimalSeparator(',');
        DecimalFormat idr = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        idr.setDecimalFormatSymbols(format);

        tvPrice.setText(idr.format(goods.price));
        // Return the completed view to render on screen
        return convertView;
    }


}
