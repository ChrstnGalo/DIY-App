package com.example.diytag;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;
    private ProgressDialog progressDialog;

    private void saveUserData(boolean isLoggedIn, String rfid, String username, String password, String imageUrl, String email, String role, String gender, String date, String balance, String qr_image, int id) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("image_url", imageUrl);
        editor.putString("email", email);
        editor.putString("role", role);
        editor.putString("gender", gender);
        editor.putString("date", date);
        editor.putString("balance", balance);
        editor.putInt("id", id);
        editor.putString("qr_image", qr_image);
        editor.putString("password", password);
        editor.putString("rfid", rfid);
        editor.putBoolean("isLoggedIn", isLoggedIn);


        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.light_black));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://diy-pos.000webhostapp.com/php_scripts/get_users.php/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        apiService = retrofit.create(ApiService.class);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(false);

        Button loginButton = findViewById(R.id.loginbtn);
        EditText emailEditText = findViewById(R.id.login_email);
        EditText passwordEditText = findViewById(R.id.login_password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (!isValidEmail(email)) {
                    Toast.makeText(MainActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.show();

                Call<List<User>> call = apiService.getUsers();
                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        progressDialog.dismiss();

                        if (response.isSuccessful()) {
                            List<User> users = response.body();
                            boolean emailFound = false;
                            boolean passwordCorrect = false;
                            User foundUser = null;

                            for (User user : users) {
                                if (user.getEmail().equals(email)) {
                                    emailFound = true;
                                    foundUser = user;
                                    if (user.getPassword().equals(password)) {
                                        passwordCorrect = true;
                                    }
                                    break;
                                }
                            }
                            if (emailFound) {
                                if (passwordCorrect) {
                                    saveUserData(true, foundUser.getRfid(), foundUser.getUsername(), foundUser.getPassword(), foundUser.getImage(), foundUser.getEmail(), foundUser.getRole(), foundUser.getGender(), foundUser.getDate(), foundUser.getBalance(), foundUser.getQr_image(), foundUser.getId());
                                    Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
