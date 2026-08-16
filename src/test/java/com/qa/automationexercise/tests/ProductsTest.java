package com.qa.automationexercise.tests;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.automationexercise.pages.HomePage;
import com.qa.automationexercise.pages.ProductsPage;
import com.qa.automationexercise.testdata.Brand;
import com.qa.automationexercise.testdata.Category;

public class ProductsTest extends Basetest {

    @DataProvider(name = "brandsData")
    public Object[][] brandsData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream jsonStream = getClass().getResourceAsStream("/testdata/brands.json");
        Brand[] brands = mapper.readValue(jsonStream, Brand[].class);

        Object[][] data = new Object[brands.length][1];
        for (int i = 0; i < brands.length; i++) {
            data[i][0] = brands[i];
        }
        return data;
    }

    @DataProvider(name = "categoriesData")
    public Object[][] categoriesData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream jsonStream = getClass().getResourceAsStream("/testdata/categories.json");
        Category[] categories = mapper.readValue(jsonStream, Category[].class);

        Object[][] data = new Object[categories.length][1];
        for (int i = 0; i < categories.length; i++) {
            data[i][0] = categories[i];
        }
        return data;
    }

    // Case 8: Select brand -> only that brand's products displayed
    @Test(dataProvider = "brandsData")
    public void brandFilter_showsResults(Brand brand) {
        String brandName = brand.getBrandName();

        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.clickBrand(brandName);

        List<String> results = productsPage.getDisplayedProductNames();
        Assert.assertFalse(results.isEmpty(),
            "Expected " + brandName + " brand filter to show at least one product, but the list was empty.");

        Assert.assertTrue(driver.getCurrentUrl().contains("/brand_products/" + brandName),
            "Expected URL to contain '/brand_products/" + brandName + "' after clicking the " + brandName
                + " brand link. Actual URL: " + driver.getCurrentUrl());
    }

    // Select category (via accordion group + category id) -> only that category's products displayed
    @Test(dataProvider = "categoriesData")
    public void categoryFilter_showsResults(Category category) {
        String parentGroup = category.getParentGroup();
        String categoryName = category.getCategoryName();
        int categoryId = category.getCategoryId();

        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.clickProducts();
        productsPage.clickCategory(parentGroup, categoryId);

        List<String> results = productsPage.getDisplayedProductNames();
        Assert.assertFalse(results.isEmpty(),
            "Expected " + categoryName + " category to show at least one product, but the list was empty.");

        Assert.assertTrue(driver.getCurrentUrl().contains("/category_products/" + categoryId),
            "Expected URL to contain '/category_products/" + categoryId + "' after clicking the " + categoryName
                + " category. Actual URL: " + driver.getCurrentUrl());
    }
}