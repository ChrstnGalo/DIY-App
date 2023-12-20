package com.example.diytag;

public class TransactionItem {
    private String paymentTopUp;
    private String date;
    private String receipt;

    public TransactionItem(String paymentTopUp, String date, String receipt) {
        this.paymentTopUp = paymentTopUp;
        this.date = date;
        this.receipt = receipt;
    }

    public String getPaymentTopUp() {
        return paymentTopUp;
    }

    public String getDate() {
        return date;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setPaymentTopUp(String paymentTopUp) {
        this.paymentTopUp = paymentTopUp;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }
}

