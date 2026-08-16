package com.qa.automationexercise.tests;

import com.qa.automationexercise.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PurchaseFlowTest extends Basetest {

    @Test
    public void loggedInUser_completesPurchase_seesOrderConfirmation() {

        // Browser starts on the homepage (Basetest.setUp() already did driver.get(...)).
        HomePage homePage = new HomePage(driver);

        // Click "Signup / Login" -> browser navigates to /login -> get a LoginPage.
        LoginPage loginPage = homePage.clickSignupLogin();

        // Submit real credentials -> on success, the SITE redirects to the homepage.
        // login() returns `this` (still typed LoginPage), since that return type
        // was chosen for the FAILED-login case, where you stay on /login.
        loginPage.login("xklclkcdj@gmail.com", "godisgood");

        // The browser is now on the homepage again, but our Java object is still
        // typed LoginPage. Same driver, same browser window -- just need a new
        // object of the right type to call HomePage-specific methods.
        HomePage homePageAfterLogin = new HomePage(driver);

        // Click "Products" -> browser navigates to /products -> get a ProductsPage.
        ProductsPage productsPage = homePageAfterLogin.clickProducts();

        // Search for a product. searchProduct() returns `this` (still ProductsPage),
        // since searching doesn't navigate anywhere -- same results page, new results.
        productsPage.searchProduct("Top");

        // Add a specific product to the cart, by its exact displayed name.
        // addProductToCart() also returns `this` -- the "Added!" modal appears
        // over the same page, no URL change.
        productsPage.addProductToCart("Blue Top");

        // Click "View Cart" INSIDE that modal -> browser navigates to /view_cart
        // -> get a CartPage.
        CartPage cartPage = productsPage.clickViewCartFromModal();

        // Proceed to checkout as an already-logged-in user -- skips the guest
        // modal entirely, browser navigates to /checkout -> get a CheckoutPage.
        CheckoutPage checkoutPage = cartPage.proceedToCheckoutAsLoggedInUser();

        // Add an order comment. Returns `this` (still CheckoutPage) -- typing
        // into a textarea doesn't navigate anywhere.
        checkoutPage.addComment("Please deliver in the afternoon.");

        // Click "Place Order" -> browser navigates to /payment -> get a PaymentPage.
        PaymentPage paymentPage = checkoutPage.placeOrder();

        // Fill in the five payment fields. Each enterX() returns `this`
        // (still PaymentPage), so these chain directly off one another.
        paymentPage.enterNameOnCard("Test User")
                   .enterCardNumber("4111111111111111")
                   .enterCVC("123")
                   .enterExpiryMonth("12")
                   .enterExpiryYear("2030");

        // Click "Pay and Confirm Order" -> browser navigates to the confirmation
        // page -> get an OrderConfirmationPage.
        OrderConfirmationPage confirmationPage = paymentPage.payAndConfirmOrder();

        // Final assertion: the confirmation page actually shows "Order Placed!"
        String heading = confirmationPage.getOrderPlacedHeadingMessage();
        // NOTE: double check this is really the right getter name on
        // OrderConfirmationPage for the "Order Placed!" heading -- go verify
        // against your own file, the method might be named differently there.

        Assert.assertEquals(heading, "ORDER PLACED!",
                "Expected the order confirmation heading to read 'Order Placed!' after completing checkout.");
    }
}