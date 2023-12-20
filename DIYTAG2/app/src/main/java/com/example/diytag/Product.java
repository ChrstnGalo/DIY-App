package com.example.diytag;

public class Product {
    private String description;
    private String amount;
    private String image;
    private  String qty;

    public Product(String description, String amount, String image, String qty) {
        this.description = description;
        this.amount = amount;
        this.image = image;
        this.qty = qty;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getImageUrl() {
        return image;
    }

    public String getQuantity() {
        return qty;
    }

}

