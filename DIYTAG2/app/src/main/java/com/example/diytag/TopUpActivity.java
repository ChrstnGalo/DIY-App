package com.example.diytag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class TopUpActivity extends AppCompatActivity {
    private EditText enterAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        getWindow().setStatusBarColor(ContextCompat.getColor(TopUpActivity.this, R.color.light_black));

        ImageView arrowImageView = findViewById(R.id.topuparrowback);
        arrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to MeFragment
                onBackPressed();
            }
        });

        // Get reference of EditText
        enterAmount = findViewById(R.id.enteramount);
        Button cashin = findViewById(R.id.paypal_cashin);

        cashin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the payment amount from the enterAmount EditText
                String paymentAmount = enterAmount.getText().toString();

                if (!paymentAmount.isEmpty()) {
                    // Create an Intent to navigate to the CheckoutActivity and pass the payment amount
                    Intent intent = new Intent(TopUpActivity.this, CheckoutActivity.class);
                    intent.putExtra("paymentAmount", paymentAmount);
                    startActivity(intent);
                } else {
                    // Show a toast message prompting the user to enter an amount
                    Toast.makeText(TopUpActivity.this, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // List of TextViews containing top-up amounts
        List<TextView> coinTextViews = new ArrayList<>();
        coinTextViews.add(findViewById(R.id.coins));
        coinTextViews.add(findViewById(R.id.coins2));
        coinTextViews.add(findViewById(R.id.coins3));
        coinTextViews.add(findViewById(R.id.coins4));
        coinTextViews.add(findViewById(R.id.coins5));
        coinTextViews.add(findViewById(R.id.coins6));

        // Loop through all TextViews and add an OnClickListener
        for (final TextView coinTextView : coinTextViews) {
            coinTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enterAmount.setText(coinTextView.getText().toString());
                }
            });

        }
    }
}
