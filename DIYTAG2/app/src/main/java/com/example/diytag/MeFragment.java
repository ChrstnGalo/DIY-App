package com.example.diytag;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MeFragment extends Fragment {

    private ShapeableImageView accprofileImageView;
    private TextView usernameTextView;
    private ApiService apiService;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        // Initialize accprofileImageView and usernameTextView
        accprofileImageView = view.findViewById(R.id.accprofile);
        usernameTextView = view.findViewById(R.id.Meusername);

        MaterialButton signoutbtn = view.findViewById(R.id.signoutbtn);
        signoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignOutConfirmationDialog();
            }
        });

        // Navigate to MyProfileActivity
        TextView myProfileText = view.findViewById(R.id.myProfileText);
        ImageView arrowProf = view.findViewById(R.id.arrowprof);

        myProfileText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(intent);
            }
        });
        arrowProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyProfileActivity.class);
                startActivity(intent);
            }
        });

        // Navigate to MyQrActivity
        TextView myQrText = view.findViewById(R.id.QrText);
        ImageView arrowQr = view.findViewById(R.id.arrowqr);

        myQrText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyQrActivity.class);
                startActivity(intent);
            }
        });
        arrowQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyQrActivity.class);
                startActivity(intent);
            }
        });

        // Navigate to MyRFIDActivity
        TextView myRfidText = view.findViewById(R.id.rfid);
        ImageView arrowRfid = view.findViewById(R.id.rfidarrow);

        myRfidText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyRfidCardActivity.class);
                startActivity(intent);
            }
        });
        arrowRfid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyRfidCardActivity.class);
                startActivity(intent);
            }
        });

        // Navigate to MyPinNumberActivity
        TextView myPinnumText = view.findViewById(R.id.pinnum);
        ImageView arrowPinnum = view.findViewById(R.id.pinarrow);

        myPinnumText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PinNumberActivity.class);
                startActivity(intent);
            }
        });
        arrowPinnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PinNumberActivity.class);
                startActivity(intent);
            }
        });

        // Get user info from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("id", -1);
        // Retrofit instance initialization inside Fragment
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://diy-pos.000webhostapp.com/php_scripts/get_UsernameImage.php/") // Palitan ang base URL base sa iyong server
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        getUserDetailsFromServer(user_id);

        return view;
    }

    private void getUserDetailsFromServer(int userId) {
        Call<UsernameImage> call = apiService.getUserDetails(userId);
        call.enqueue(new Callback<UsernameImage>() {
            @Override
            public void onResponse(Call<UsernameImage> call, Response<UsernameImage> response) {
                if (response.isSuccessful()) {
                    UsernameImage userDetails = response.body();

                    updateUIWithUserDetails(userDetails);
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<UsernameImage> call, Throwable t) {
                // Handle request failure
            }
        });
    }
    private void updateUIWithUserDetails(UsernameImage userDetails) {
        if (userDetails != null) {
            usernameTextView.setText(userDetails.getUsername());

            Picasso.get().load("https://diy-pos.000webhostapp.com/" + userDetails.getImageUrl()).memoryPolicy(MemoryPolicy.NO_CACHE).into(accprofileImageView);
            {
                // Kung walang image URL, ilagay ang default image
                accprofileImageView.setImageResource(R.drawable.default_image);
            }
        }
    }


    // Iba pang mga method na kasama sa fragment

    private void showSignOutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm Sign Out")
                .setMessage("Are you sure you want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        performSignOut();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void performSignOut() {
        // Check if user is logged in
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // If logged in, update the login status to false
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.apply();
        }

        // Redirect to MainActivity or LoginActivity after sign out
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }
}