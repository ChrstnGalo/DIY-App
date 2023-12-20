package com.example.diytag;

import com.google.gson.annotations.SerializedName;

public class DiscountProduct {
    @SerializedName("description")
    private String description;

    @SerializedName("qty")
    private String qty;

    @SerializedName("amount")
    private String amount;

    @SerializedName("image")
    private String image;
    @SerializedName("discounted_price")
    private String discounted_price;

    // Getter at Setter para sa description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter at Setter para sa qty
    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    // Getter at Setter para sa amount
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    // Getter at Setter para sa image
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Getter at Setter para sa discounted_price
    public String getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(String discounted_price) {
        this.discounted_price = discounted_price;
    }
}

