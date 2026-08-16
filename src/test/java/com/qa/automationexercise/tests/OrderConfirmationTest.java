package com.qa.automationexercise.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.automationexercise.pages.CartPage;
import com.qa.automationexercise.pages.CheckoutPage;
import com.qa.automationexercise.pages.HomePage;
import com.qa.automationexercise.pages.LoginPage;
import com.qa.automationexercise.pages.OrderConfirmationPage;
import com.qa.automationexercise.pages.PaymentPage;
import com.qa.automationexercise.pages.ProductsPage;

public class OrderConfirmationTest extends Basetest {

    // Case 20: Successful payment -> confirmation page shows success
    // message + receipt (Download Invoice) is available.
    @Test
    public void orderConfirmation_afterSuccessfulPayment_showsSuccessAndDownloadOption() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickSignupLogin();
        loginPage.login("xklclkcdj@gmail.com", "godisgood");

        HomePage homePageAfterLogin = new HomePage(driver);
        ProductsPage productsPage = homePageAfterLogin.clickProducts();
        productsPage.addProductToCart("Blue Top");

        CartPage cartPage = productsPage.clickViewCartFromModal();
        CheckoutPage checkoutPage = cartPage.proceedToCheckoutAsLoggedInUser();
        checkoutPage.addComment("Order confirmation test.");

        PaymentPage paymentPage = checkoutPage.placeOrder();
        paymentPage.enterNameOnCard("Test User")
                   .enterCardNumber("4111111111111111")
                   .enterCVC("123")
                   .enterExpiryMonth("12")
                   .enterExpiryYear("2030");

        OrderConfirmationPage confirmationPage = paymentPage.payAndConfirmOrder();

        Assert.assertTrue(confirmationPage.isOrderPlacedHeadingMessageDisplayed(),
            "Expected the 'Order Placed!' heading to be displayed on the confirmation page, but it was not.");

        Assert.assertTrue(confirmationPage.isOrderConfirmationMessageDisplayed(),
            "Expected the order confirmation message to be displayed, but it was not.");

        // If the Download Invoice button isn't genuinely clickable, this
        // line throws (e.g. TimeoutException from elementToBeClickable
        // never resolving) and the test fails there -- no separate
        // assertion needed to prove the button worked.
        confirmationPage.clickDownloadInvoice();
    }
}
