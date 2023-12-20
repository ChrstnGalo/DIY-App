package com.example.diytag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class MyQrActivity extends AppCompatActivity {

    private ShapeableImageView myQrImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qr);

        getWindow().setStatusBarColor(ContextCompat.getColor(MyQrActivity.this, R.color.light_black));

        myQrImageView = findViewById(R.id.accQrImage);

        ImageView arrowImageView = findViewById(R.id.myqrarrowback);

        arrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to MeFragment
                onBackPressed();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String qrimage = "https://diy-pos.000webhostapp.com/" + sharedPreferences.getString("qr_image", ""); // Palitan ang example.com base sa iyong base URL at idagdag ang larawan mula sa SharedPreferences

        Picasso.get().load(qrimage).memoryPolicy(MemoryPolicy.NO_CACHE).into(myQrImageView);
        {
            myQrImageView.setImageResource(R.drawable.default_image); // I-update mo ang default_image base sa pangalan ng iyong default image resource
        }
    }
}