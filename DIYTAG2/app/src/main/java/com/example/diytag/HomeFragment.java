package com.example.diytag;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<DiscountProduct> productList;
    private EditText productSearchEditText;
    private TextView balanceTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        balanceTextView = view.findViewById(R.id.BalanceAvlbl);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("id", -1);

        productSearchEditText = view.findViewById(R.id.searchbar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://diy-pos.000webhostapp.com/php_scripts/get_discounted_products.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<DiscountProduct>> call = apiService.getDiscountedProducts();

        call.enqueue(new Callback<List<DiscountProduct>>() {
            @Override
            public void onResponse(Call<List<DiscountProduct>> call, Response<List<DiscountProduct>> response) {
                if (response.isSuccessful()) {
                    productList = response.body();
                    adapter = new RecyclerViewAdapter(productList, getContext());
                    recyclerView.setAdapter(adapter);

                    adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(DiscountProduct product) {
                            // Handle item click, for example, open SaleProductDisplayActivity
                            Intent intent = new Intent(getActivity(), SaleProductDisplayActivity.class);
                            intent.putExtra("image", product.getImage());
                            intent.putExtra("description", product.getDescription());
                            intent.putExtra("discounted_price", product.getDiscounted_price());
                            intent.putExtra("qty", product.getQty());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<DiscountProduct>> call, Throwable t) {
                // Handle error
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
                    balanceTextView.setText("₱" + balance);
                } else {
                    // Handle errors in response
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Handle network failures or exceptions
            }
        });

        productSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return view;
    }

    private void filter(String text) {
        List<DiscountProduct> filteredList = new ArrayList<>();

        for (DiscountProduct product : productList) {
            if (product.getDescription().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(product);
            }
        }

        adapter.filterList(filteredList);
    }
}

