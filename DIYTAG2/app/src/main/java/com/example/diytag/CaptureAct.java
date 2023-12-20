package com.example.diytag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.journeyapps.barcodescanner.CaptureActivity;

public class CaptureAct extends CaptureActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Any additional setup code here, if needed
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }
}
