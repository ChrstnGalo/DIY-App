package com.example.diytag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyProfileActivity extends AppCompatActivity {

    private ShapeableImageView myprofileImageView;
    private TextView mpusernameTextView;
    private TextView mpemailTextView;
    private TextView mpgenderTextView;
    private TextView mproleTextView;
    private TextView mpdatecreatedTextView;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getWindow().setStatusBarColor(ContextCompat.getColor(MyProfileActivity.this, R.color.light_black));

        myprofileImageView = findViewById(R.id.myprofile);
        mpusernameTextView = findViewById(R.id.username);
        mpemailTextView = findViewById(R.id.email);
        mpgenderTextView = findViewById(R.id.gender);
        mproleTextView = findViewById(R.id.role);
        mpdatecreatedTextView = findViewById(R.id.datecreated);

        ImageView arrowImageView = findViewById(R.id.myprofilearrowback);
        arrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to MeFragment
                onBackPressed();
            }
        });

        // Load user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("id", -1);


        // Retrofit instance initialization inside Fragment
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://diy-pos.000webhostapp.com/php_scripts/get_myProfile.php/") // Palitan ang base URL base sa iyong server
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        getUserProfile(user_id);

    }

    private void getUserProfile(int userId) {
        Call<MyProfile> call = apiService.getUserProfile(userId);
        call.enqueue(new Callback<MyProfile>() {
            @Override
            public void onResponse(Call<MyProfile> call, Response<MyProfile> response) {
                if (response.isSuccessful()) {
                    MyProfile userDetails = response.body();

                    updateUIWithUserDetails(userDetails);
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<MyProfile> call, Throwable t) {
                // Handle request failure
            }
        });
    }

    private void updateUIWithUserDetails(MyProfile userDetails) {
        if (userDetails != null) {
            mpusernameTextView.setText(userDetails.getUsername());
            mpemailTextView.setText(userDetails.getEmail());
            mpgenderTextView.setText(userDetails.getGender());
            mproleTextView.setText(userDetails.getRole());
            mpdatecreatedTextView.setText(userDetails.getDate());

            Picasso.get().load("https://diy-pos.000webhostapp.com/" + userDetails.getImageUrl()).memoryPolicy(MemoryPolicy.NO_CACHE).into(myprofileImageView);
            {
                // Kung walang image URL, ilagay ang default image
                myprofileImageView.setImageResource(R.drawable.default_image);
            }
        }
    }

}
