package com.qa.automationexercise.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.automationexercise.pages.CartItem;
import com.qa.automationexercise.pages.CartPage;
import com.qa.automationexercise.pages.CheckoutPage;
import com.qa.automationexercise.pages.HomePage;
import com.qa.automationexercise.pages.LoginPage;
import com.qa.automationexercise.pages.ProductsPage;

public class CartTest extends Basetest {

    // Case 10: Add one product -> product appears in cart
    @Test
    public void addToCart_oneProduct_appearsInCart() {
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.clickProducts();

        productsPage.addProductToCart("Blue Top");

        // addProductToCart() triggers the "Added!" modal, which contains its
        // own "View Cart" link -- clickViewCartFromModal() is the correct
        // path here, not clickCart(), since the modal is already open.
        CartPage cartPage = productsPage.clickViewCartFromModal();

        List<CartItem> items = cartPage.getCartItems();

        boolean containsBlueTop = items.stream()
            .anyMatch(item -> item.getProductName().equals("Blue Top"));

        Assert.assertTrue(containsBlueTop,
            "Expected 'Blue Top' to appear in the cart after adding it, but it was not found.");
    }

    // Case 11: Add multiple products -> all appear in cart, previous ones remain
    @Test
    public void addToCart_multipleProducts_allAppearAndAccumulate() {
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.clickProducts();

        productsPage.addProductToCart("Blue Top");
        productsPage.closeAddedModal();
        productsPage.addProductToCart("Men Tshirt");

        CartPage cartPage = productsPage.clickViewCartFromModal();
        List<CartItem> items = cartPage.getCartItems();

        boolean hasBlueTop = items.stream().anyMatch(item -> item.getProductName().equals("Blue Top"));
        boolean hasMenTshirt = items.stream().anyMatch(item -> item.getProductName().equals("Men Tshirt"));

        Assert.assertTrue(hasBlueTop,
            "Expected 'Blue Top' to still be in the cart after adding a second product, but it was missing.");
        Assert.assertTrue(hasMenTshirt,
            "Expected 'Men Tshirt' to appear in the cart, but it was not found.");
        Assert.assertEquals(items.size(), 2,
            "Expected exactly 2 items in the cart after adding 2 products, but found " + items.size());
    }

    // Case 12: Remove a product -> removed product no longer shown
    @Test
    public void removeFromCart_product_noLongerShown() {
        HomePage homePage = new HomePage(driver);
        ProductsPage productsPage = homePage.clickProducts();

        productsPage.addProductToCart("Blue Top");
        productsPage.closeAddedModal();
        productsPage.addProductToCart("Men Tshirt");

        CartPage cartPage = productsPage.clickViewCartFromModal();

        List<CartItem> beforeRemoval = cartPage.getCartItems();
        Assert.assertEquals(beforeRemoval.size(), 2,
            "Expected 2 items in the cart before removal, but found " + beforeRemoval.size());

        cartPage.removeProduct("Blue Top");

        List<CartItem> afterRemoval = cartPage.getCartItems();

        boolean stillHasBlueTop = afterRemoval.stream()
            .anyMatch(item -> item.getProductName().equals("Blue Top"));
        boolean stillHasMenTshirt = afterRemoval.stream()
            .anyMatch(item -> item.getProductName().equals("Men Tshirt"));

        Assert.assertFalse(stillHasBlueTop,
            "Expected 'Blue Top' to be removed from the cart, but it was still present.");
        Assert.assertTrue(stillHasMenTshirt,
            "Expected 'Men Tshirt' to remain in the cart after removing a different product, but it was missing.");
    }

    // Case 13: View empty cart -> appropriate empty-state message/behavior
    @Test
    public void viewCart_whenEmpty_showsEmptyMessage() {
        HomePage homePage = new HomePage(driver);

        CartPage cartPage = homePage.clickCart();

        Assert.assertTrue(cartPage.isCartEmpty(),
            "Expected the cart to show an empty-cart message when nothing has been added, but it did not.");
    }

    // Case 16 (relocated from "Payment Info"): Proceed to checkout -> the
    // order summary shown at checkout matches what's actually in the cart.
    @Test
    public void checkoutSummary_matchesCartItems() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickSignupLogin();
        loginPage.login("xklclkcdj@gmail.com", "godisgood");

        HomePage homePageAfterLogin = new HomePage(driver);
        ProductsPage productsPage = homePageAfterLogin.clickProducts();

        productsPage.addProductToCart("Blue Top");
        productsPage.addProductToCart("Men Tshirt");

        CartPage cartPage = productsPage.clickViewCartFromModal();

        // Snapshot what's actually in the cart BEFORE moving to checkout.
        List<CartItem> cartItems = cartPage.getCartItems();

        CheckoutPage checkoutPage = cartPage.proceedToCheckoutAsLoggedInUser();

        // Snapshot what checkout's order summary displays.
        List<CartItem> orderSummary = checkoutPage.getOrderSummary();

        // First check: same number of line items. If this fails, no point
        // comparing individual products -- something fundamental is wrong
        // (an item got dropped or duplicated between cart and checkout).
        Assert.assertEquals(orderSummary.size(), cartItems.size(),
            "Expected checkout order summary to show the same number of items as the cart. "
                + "Cart had " + cartItems.size() + ", checkout showed " + orderSummary.size());

        // Second check: every product name in the cart also appears in the
        // order summary. Using anyMatch per cart item, same pattern as the
        // add-to-cart assertions -- just run in a loop instead of checking
        // a single fixed name.
        for (CartItem cartItem : cartItems) {
            boolean foundInSummary = orderSummary.stream()
                .anyMatch(summaryItem -> summaryItem.getProductName().equals(cartItem.getProductName()));

            Assert.assertTrue(foundInSummary,
                "Expected '" + cartItem.getProductName() + "' from the cart to also appear "
                    + "in the checkout order summary, but it was missing.");
        }
    }
}