package com.example.diytag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.gson.GsonBuilder;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.OrderRequest;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckoutActivity extends AppCompatActivity {
    PaymentButtonContainer paymentButtonContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        getWindow().setStatusBarColor(ContextCompat.getColor(CheckoutActivity.this, R.color.dark_green));

        // Setup Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://diy-pos.000webhostapp.com/php_scripts/update_balance.php/") // Palitan ang base URL ayon sa iyong endpoint
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        ApiService apiUpdateBalance = retrofit.create(ApiService.class);

        paymentButtonContainer = findViewById(R.id.payment_button_container); // Replace with the actual ID from your layout
        Intent intent = getIntent();
        String paymentAmount = intent.getStringExtra("paymentAmount");

        double new_balance = Double.parseDouble(paymentAmount);
        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        int user_id = sharedPreferences.getInt("id", -1); // -1 ang default value kung walang user ID na nakuha

        paymentButtonContainer.setup(
                new CreateOrder() {
                    @Override
                    public void create(@NotNull CreateOrderActions createOrderActions) {
                        ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                        purchaseUnits.add(
                                new PurchaseUnit.Builder()
                                        .amount(
                                                new Amount.Builder()
                                                        .currencyCode(CurrencyCode.PHP)
                                                        .value(paymentAmount)  // Set the dynamic payment amount
                                                        .build()
                                        )
                                        .build()
                        );
                        OrderRequest order = new OrderRequest(
                                OrderIntent.CAPTURE,
                                new AppContext.Builder()
                                        .userAction(UserAction.PAY_NOW)
                                        .build(),
                                purchaseUnits
                        );
                        createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                    }
                },
                // Sa loob ng onApprove method
                new OnApprove() {
                    @Override
                    public void onApprove(@NotNull Approval approval) {
                        approval.getOrderActions().capture(new OnCaptureComplete() {
                            @Override
                            public void onCaptureComplete(@NotNull CaptureOrderResult result) {
                                Log.i("CaptureOrder", String.format("CaptureOrderResult: %s", result));

                                // Update balance here
                                Call<String> call = apiUpdateBalance.updateBalance(user_id, new_balance);

                                call.enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if (response.isSuccessful()) {
                                            // Update was successful
                                            String result = response.body();
                                            Log.d("Update Balance", "Success: " + result);
                                        } else {
                                            // Update failed
                                            Log.e("Update Balance", "Error: " + response.message());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        // Handle failure
                                        Log.e("Update Balance", "Failure: " + t.getMessage());
                                    }
                                });
                                // Example: Update the user's balance using SharedPreferences
                                String balanceStr = sharedPreferences.getString("balance", "0");

                                // Convert the balance to a double
                                double currentBalance = Double.parseDouble(balanceStr);

                                // Add the purchase amount to the current balance
                                currentBalance += new_balance;

                                // Convert the updated balance back to a string
                                String updatedBalance = Double.toString(currentBalance);

                                // Update the balance in SharedPreferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("balance", updatedBalance);
                                editor.apply();
                            }
                        });
                    }
                });
    }
}