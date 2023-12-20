package com.example.diytag;

import android.app.Application;

import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;
import com.squareup.picasso.BuildConfig;

public class Applications extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PayPalCheckout.setConfig(new CheckoutConfig(
                this,
                "ASlg17N1xRKLOclBDxJ4oomx2ee9mwuKZGDRujxK0bXXQnrdoxVq8Vp3V5sfNKrtFZPCqBhtjwnAjL6o",
                Environment.SANDBOX,
                CurrencyCode.USD,
                UserAction.PAY_NOW,
                "com.example.diytag://paypalpay"

        ));
    }
}