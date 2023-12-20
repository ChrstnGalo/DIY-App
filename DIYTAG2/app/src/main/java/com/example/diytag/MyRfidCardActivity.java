package com.example.diytag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRfidCardActivity extends AppCompatActivity {

    private ApiService apiService;
    private TextView myusernameTextView;
    private TextView mydatecreatedTextView;
    private TextView myrfidTextView;
    private TextView mynameTextView;
    private TextView myrfidnumTextView;
    private TextView mydateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rfid_card);

        getWindow().setStatusBarColor(ContextCompat.getColor(MyRfidCardActivity.this, R.color.light_black));

        myusernameTextView = findViewById(R.id.name);
        mydatecreatedTextView = findViewById(R.id.date);
        myrfidTextView = findViewById(R.id.rfidnum);

        mynameTextView = findViewById(R.id.user_name);
        myrfidnumTextView = findViewById(R.id.rfid_num);
        mydateTextView = findViewById(R.id.date_created);

        ImageView arrowImageView = findViewById(R.id.myrfidarrowback);
        arrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to MeFragment
                onBackPressed();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("id", -1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://diy-pos.000webhostapp.com/php_scripts/get_rfid_num.php/") // Palitan ang base URL base sa iyong server
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        getUserRfid(user_id);
    }

    private void getUserRfid(int userId) {
        Call<MyRfid> call = apiService.getUserRfid(userId);
        call.enqueue(new Callback<MyRfid>() {
            @Override
            public void onResponse(Call<MyRfid> call, Response<MyRfid> response) {
                if (response.isSuccessful()) {
                    MyRfid userDetails = response.body();

                    updateUIWithUserDetails(userDetails);
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<MyRfid> call, Throwable t) {
                // Handle request failure
            }
        });
    }

    private void updateUIWithUserDetails(MyRfid userDetails) {
        if (userDetails != null) {
            myusernameTextView.setText(userDetails.getUsername());
            myrfidTextView.setText(userDetails.getRfid());
            mydatecreatedTextView.setText(userDetails.getDate());

            mynameTextView.setText(userDetails.getUsername());
            myrfidnumTextView.setText(userDetails.getRfid());
            mydateTextView.setText(userDetails.getDate());
        }
    }
}