package com.example.diytag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProfileNotificationAdapter notificationAdapter;
    private List<String> notificationList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = view.findViewById(R.id.ProfilechangesView);

        notificationList = new ArrayList<>();

        notificationAdapter = new ProfileNotificationAdapter(notificationList);
        recyclerView.setAdapter(notificationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        checkForUpdates(); // Tawagin ang function para mag-check ng updates
        loadNotifications(); // Load notifications during user login

        ImageView scannerLogo = view.findViewById(R.id.scannerImageView); // Replace with your actual scanner logo ID
        scannerLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to QRScanner activity
                Intent intent = new Intent(getActivity(), QRScanner.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadNotifications() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("notifications", Context.MODE_PRIVATE);
        Set<String> notificationSet = sharedPreferences.getStringSet("notificationSet", new HashSet<>());
        notificationList.addAll(notificationSet);
        notificationAdapter.notifyDataSetChanged();
    }

    // Function to add a new notification to the list and update the adapter
    private void addNotifications(String notificationMessage) {
        notificationAdapter.addNotificationToList(notificationMessage);
        // Save updated notification list to SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("notifications", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> notificationSet = new HashSet<>(notificationList);
        editor.putStringSet("notificationSet", notificationSet);
        editor.apply();
    }

    // Function to delete a notification
    private void deleteNotification(int position) {
        notificationList.remove(position);
        notificationAdapter.notifyItemRemoved(position);

        // Save updated notification list to SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("notifications", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> notificationSet = new HashSet<>(notificationList);
        editor.putStringSet("notificationSet", notificationSet);
        editor.apply();
    }
    // Function para mag-check ng updates sa server
    private void checkForUpdates() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("id", -1);
        String currentUsername = sharedPreferences.getString("username", "");
        String currentEmail = sharedPreferences.getString("email", "");
        String currentPassword = sharedPreferences.getString("password", "");
        String currentImageUrl = sharedPreferences.getString("image_url", "");
        String currentGender = sharedPreferences.getString("gender", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://diy-pos.000webhostapp.com/php_scripts/check_updates.php/") // Palitan ng tamang URL - tanggalin ang endpoint part
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<ServerResponse> call = apiService.checkForUpdates(userId, currentUsername, currentEmail, currentPassword, currentImageUrl, currentGender);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    // Response na matagumpay na naipadala sa server
                    // Ito ay ang tamang lugar upang suriin ang resulta ng response at ipakita ang notification depende dito
                    // Halimbawa:
                    boolean hasUpdates = response.body().isHasUpdates();
                    if (hasUpdates) {
                        // May update, ipakita ang notification
                        String notificationMessage = "May bagong update sa iyong profile!";
                        addNotification(notificationMessage);
                    } else {
                        // Walang update, gawin ang anumang kailangan
                    }
                } else {
                    // Hindi successful ang request papunta sa server
                    // Handle error condition
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // May naganap na error sa network request
                // Handle error condition
            }
        });
    }

    // Method para magdagdag ng bagong notification sa list at i-update ang adapter
    private void addNotification(String notificationMessage) {
        notificationList.add(notificationMessage);
        notificationAdapter.notifyDataSetChanged();
    }
}