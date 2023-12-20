package com.example.diytag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PinNumberActivity extends AppCompatActivity {
    private EditText pinNumberEditText;
    private Button updatePinButton;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_number);

        getWindow().setStatusBarColor(ContextCompat.getColor(PinNumberActivity.this, R.color.light_black));

        pinNumberEditText = findViewById(R.id.pin_num);
        pinNumberEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)}); // Limitahan sa 6 digits

        updatePinButton = findViewById(R.id.savebtn);

        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("id", -1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://diy-pos.000webhostapp.com/php_scripts/updateUserPin.php/") // Palitan ang base URL base sa iyong server
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        updatePinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kunin ang bagong pin number mula sa EditText
                String newPin = pinNumberEditText.getText().toString();

                if (newPin.length() != 6) {
                    Toast.makeText(PinNumberActivity.this, "Please enter a 6-digit pin number", Toast.LENGTH_SHORT).show();
                } else {
                    // Tawagin ang API endpoint para mag-update ng pin number
                    updatePinNumber(user_id, Integer.parseInt(newPin));
                }
            }
        });
    }

    // Method para tawagin ang API endpoint para mag-update ng pin number
    private void updatePinNumber(int user_id, int newPin) {
        Call<Void> call = apiService.updateUserPin(user_id, newPin);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Update successful message
                    Toast.makeText(PinNumberActivity.this, "Pin number updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // Update failed message
                    Toast.makeText(PinNumberActivity.this, "Failed to update pin number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle ng error
                Toast.makeText(PinNumberActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
