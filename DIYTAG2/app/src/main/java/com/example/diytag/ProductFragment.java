package com.example.diytag;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

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

public class ProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductViewAdapter adapter;
    private List<Product> productList;
    private EditText productSearchEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        recyclerView = view.findViewById(R.id.rcylrview);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        productSearchEditText = view.findViewById(R.id.productsearch);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://diy-pos.000webhostapp.com/php_scripts/get_products.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Product>> call = apiService.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productList = response.body();
                    adapter = new ProductViewAdapter(productList, getContext());
                    recyclerView.setAdapter(adapter);

                    // Set the click listener here
                    adapter.setOnItemClickListener(new ProductViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Product product) {
                            // Handle item click, for example, open ProductDisplayActivity
                            Intent intent = new Intent(getActivity(), ProductDisplayActivity.class);
                            intent.putExtra("image", product.getImageUrl());
                            intent.putExtra("description", product.getDescription());
                            intent.putExtra("amount", product.getAmount());
                            intent.putExtra("qty", product.getQuantity());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                // Handle ng error
            }
        });

        productSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter the list based on the search text
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;
    }

    private void filter(String text) {
        List<Product> filteredList = new ArrayList<>();

        for (Product product : productList) {
            if (product.getDescription().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(product);
            }
        }

        adapter.filterList(filteredList);
    }
}
