package com.qa.automationexercise.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.automationexercise.pages.HomePage;
import com.qa.automationexercise.pages.ProductsPage;

public class SmokeTest extends Basetest {

    @Test
    public void searchReturnsResults() {
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.clickProducts();

        productsPage.searchProduct("dress");

        List<String> results = productsPage.getDisplayedProductNames();
        Assert.assertFalse(results.isEmpty(), "Expected search results, but got none");
    }
}