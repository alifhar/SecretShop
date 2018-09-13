package com.example.toshiba.lesson_3_listview;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TroliAdapter extends ArrayAdapter<Goods> {
     public TroliAdapter(Context context, ArrayList<Goods> goods) {
         super(context, 0, goods);
     }

     private static class ViewHolder {
         TextView name;
         ImageView image;
         Button button;
     }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Goods goods = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_troli, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.image = convertView.findViewById(R.id.imageView2);
            viewHolder.button = convertView.findViewById(R.id.cancel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.name.setText(goods.name);
        viewHolder.image.setImageResource(goods.image);

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(goods);
                notifyDataSetChanged();
                GoodsDbHelper myDbHelper = new GoodsDbHelper(v.getContext());
                SQLiteDatabase db = myDbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(SecretShopContract.SecretShopEntry.FIELD_PICKED, 0);

                String[] id = { goods.id };
                db.update(
                        SecretShopContract.SecretShopEntry.TABLE_NAME,
                        values,
                        SecretShopContract.SecretShopEntry._ID + " =?",
                        id
                );
                db.close();
                myDbHelper.close();

            }
        });

        return convertView;
    }}
