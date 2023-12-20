package com.example.diytag;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class QRScanner extends AppCompatActivity {

    ActivityResultLauncher<ScanOptions> barLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null) {
                String qrContent = result.getContents();

                if (qrContent.startsWith("http://") || qrContent.startsWith("https://")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(qrContent));
                    startActivity(browserIntent);
                } else if (isNumeric(qrContent)) {
                    int userId = Integer.parseInt(qrContent);
                    // Ngayon, maaari mong gamitin ang userId para gawin ang anumang kailangan mo.
                } else {
                    // Iba pang mga hakbang batay sa kung ano ang nilalaman ng QR code.
                }
            }
        });

        scanCode();
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    public boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
