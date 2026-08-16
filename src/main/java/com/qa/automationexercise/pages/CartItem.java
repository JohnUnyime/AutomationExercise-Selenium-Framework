package com.qa.automationexercise.pages;

public class CartItem {

    private final String productName;
    private final String price;
    private final String quantity;
    private final String total;

    public CartItem(String productName, String price, String quantity, String total) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTotal() {
        return total;
    }
}
