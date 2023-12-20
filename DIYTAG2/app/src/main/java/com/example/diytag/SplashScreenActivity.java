package com.example.diytag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setStatusBarColor(ContextCompat.getColor(SplashScreenActivity.this, R.color.light_black));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Check if user is logged in
                SharedPreferences sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                Intent intent;
                if (isLoggedIn) {
                    // Redirect to HomeActivity or DashboardActivity if user is logged in
                    intent = new Intent(SplashScreenActivity.this, HomePageActivity.class); // Palitan ang HomeActivity base sa pangalan ng activity na dapat puntahan pag naka-log in
                } else {
                    // Redirect to LoginActivity if user is not logged in
                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                }

                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
