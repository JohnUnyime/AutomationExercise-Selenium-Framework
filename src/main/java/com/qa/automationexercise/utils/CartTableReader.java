package com.qa.automationexercise.utils;

import com.qa.automationexercise.pages.CartItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartTableReader {

    public static List<CartItem> readRows(
            List<WebElement> rows,
            By nameLocator,
            By priceLocator,
            By quantityLocator,
            By totalLocator) {

        List<CartItem> items = new ArrayList<>();

        for (WebElement row : rows) {
            String name = row.findElement(nameLocator).getText();
            String price = row.findElement(priceLocator).getText();
            String quantity = row.findElement(quantityLocator).getText();
            String total = row.findElement(totalLocator).getText();
            items.add(new CartItem(name, price, quantity, total));
        }

        return items;
    }
}
