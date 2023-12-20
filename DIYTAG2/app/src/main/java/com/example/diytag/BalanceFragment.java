package com.example.diytag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BalanceFragment extends Fragment {

    private TextView avlbalanceTextView;
    private TextView totalTextView; // Bagong TextView para sa total
    private ApiService apiServiceReceiptAndDate;
    private TransactionListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        recyclerView = view.findViewById(R.id.recyclerview2);
        avlbalanceTextView = view.findViewById(R.id.AvlblBal);
        totalTextView = view.findViewById(R.id.Total); // Assuming totalTextView is inside fragment_balance layout

        // Get user info from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("id", -1);

        Retrofit retrofitReceiptAndDate = new Retrofit.Builder()
                .baseUrl("https://diy-pos.000webhostapp.com/php_scripts/getReceiptandDate.php/") // Update with your second base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiServiceReceiptAndDate = retrofitReceiptAndDate.create(ApiService.class);

        getUserSalesFromServer(user_id);

        MaterialButton topUpButton = view.findViewById(R.id.Topup);
        topUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopUpActivity.class);
                startActivity(intent);
            }
        });

        Retrofit balanceretrofit = new Retrofit.Builder()
                .baseUrl("https://diy-pos.000webhostapp.com/php_scripts/get_user_balance.php/") // Palitan ng tamang base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiServiceBalance = balanceretrofit.create(ApiService.class);

        Call<String> callbal = apiServiceBalance.getUserBalance(user_id); // Gumawa ng request na may user_id

        callbal.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String balance = response.body(); // Kunin ang balance mula sa response

                    // I-update ang UI para ipakita ang balance, halimbawa sa isang TextView
                    avlbalanceTextView.setText("₱" + balance);
                } else {
                    // Handle errors in response
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Handle network failures or exceptions
            }
        });

        return view;
    }

    private void getUserSalesFromServer(int userId) {
        Call<List<Sales>> call = apiServiceReceiptAndDate.getUserSales(userId);
        call.enqueue(new Callback<List<Sales>>() {
            @Override
            public void onResponse(Call<List<Sales>> call, Response<List<Sales>> response) {
                if (response.isSuccessful()) {
                    List<Sales> userSales = response.body();
                    setSalesToRecyclerView(userSales);
                    updateTotalAmount(); // Call method to update total amount here
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<List<Sales>> call, Throwable t) {
                // Handle request failure
            }
        });
    }

    private void setSalesToRecyclerView(List<Sales> salesList) {
        adapter = new TransactionListAdapter(getContext(), salesList, apiServiceReceiptAndDate);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void updateTotalAmount() {
        if (adapter != null) {
            double totalAmount = adapter.calculateTotalAmount();
            String formattedTotal = "₱" + totalAmount;
            totalTextView.setText(formattedTotal);
        }
    }
}
