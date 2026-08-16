package com.qa.automationexercise.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.automationexercise.pages.CartPage;
import com.qa.automationexercise.pages.CheckoutPage;
import com.qa.automationexercise.pages.HomePage;
import com.qa.automationexercise.pages.LoginPage;
import com.qa.automationexercise.pages.PaymentPage;
import com.qa.automationexercise.pages.ProductsPage;

public class PaymentTest extends Basetest {

    // Case 19: Skip a required payment field (CVC) -> native browser
    // validation blocks submission and shows "Please fill in this field."
    @Test
    public void payment_missingCvc_showsValidationMessage() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickSignupLogin();
        loginPage.login("xklclkcdj@gmail.com", "godisgood");

        HomePage homePageAfterLogin = new HomePage(driver);
        ProductsPage productsPage = homePageAfterLogin.clickProducts();
        productsPage.addProductToCart("Blue Top");

        CartPage cartPage = productsPage.clickViewCartFromModal();
        CheckoutPage checkoutPage = cartPage.proceedToCheckoutAsLoggedInUser();
        checkoutPage.addComment("Testing missing CVC validation.");

        PaymentPage paymentPage = checkoutPage.placeOrder();

        // Fill every field EXCEPT CVC.
        paymentPage.enterNameOnCard("Test User")
                   .enterCardNumber("4111111111111111")
                   .enterExpiryMonth("12")
                   .enterExpiryYear("2030");

        // Use attemptPay(), NOT payAndConfirmOrder() -- native validation
        // blocks real navigation, so we must not assume we left this page.
        paymentPage.attemptPay();

        String validationMessage = paymentPage.getCvcValidationMessage();

        Assert.assertFalse(validationMessage.isEmpty(),
            "Expected a native browser validation message for the missing CVC field, but got none.");

        Assert.assertTrue(validationMessage.toLowerCase().contains("fill"),
            "Expected the validation message to mention filling in the field. Actual message: " + validationMessage);
    }
}