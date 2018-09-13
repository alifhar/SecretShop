package com.example.toshiba.lesson_3_listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        firstAction();
    }

    public void firstAction() {
        TextView name = findViewById(R.id.name);
        TextView price = findViewById(R.id.price);
        ImageView imageView = findViewById(R.id.imageView);

        String dataName;
        int dataPrice;
        int image;
        dataName = getIntent().getExtras().getString("name");
        name.setText(dataName);
        dataPrice = getIntent().getExtras().getInt("price");

        DecimalFormatSymbols format = new DecimalFormatSymbols();
        format.setCurrencySymbol("Rp. ");
        format.setGroupingSeparator('.');
        format.setMonetaryDecimalSeparator(',');
        DecimalFormat idr = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        idr.setDecimalFormatSymbols(format);

        price.setText(idr.format(dataPrice));

        image = getIntent().getExtras().getInt("image");
        imageView.setImageDrawable(getResources().getDrawable(image, null));

    }
}
