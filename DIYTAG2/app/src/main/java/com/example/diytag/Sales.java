package com.example.diytag;

import com.google.gson.annotations.SerializedName;

public class Sales {
    @SerializedName("description")
    private String description;

    @SerializedName("qty")
    private String qty;

    @SerializedName("total")
    private String total;
    @SerializedName("date")
    private String date;

    // Magdagdag ng mga getters para sa receiptNo at date

    public String getDescription() {
        return description;
    }

    public String getQty() {
        return qty;
    }

    public String getTotal() {
        return total;
    }

    public String getDate() {
        return date;
    }
}

