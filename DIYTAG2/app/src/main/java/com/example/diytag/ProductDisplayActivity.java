package com.example.diytag;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

public class ProductDisplayActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;
    private TextView productQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);

        getWindow().setStatusBarColor(ContextCompat.getColor(ProductDisplayActivity.this, R.color.light_black));

        ImageView arrowImageView = findViewById(R.id.viewproductrrowback);
        arrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to MeFragment
                onBackPressed();
            }
        });

        productImage = findViewById(R.id.pimage);
        productName = findViewById(R.id.pname);
        productPrice = findViewById(R.id.pprice);
        productQuantity = findViewById(R.id.pquantity);

        // Get data from Intent
        String image = getIntent().getStringExtra("image");
        String name = getIntent().getStringExtra("description");
        String amount = getIntent().getStringExtra("amount");
        String qty = getIntent().getStringExtra("qty");

        // Set data to views
        Picasso.get().load( "https://diy-pos.000webhostapp.com/"+ image).into(productImage);
        productName.setText(name);
        productPrice.setText("Price: ₱" + amount);
        productQuantity.setText("Quantity: " + qty);
    }
}