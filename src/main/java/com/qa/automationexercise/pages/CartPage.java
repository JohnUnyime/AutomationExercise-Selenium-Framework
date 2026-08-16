package com.qa.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.qa.automationexercise.utils.CartTableReader;

import java.util.List;

public class CartPage extends BasePage {


    public CartPage(WebDriver driver) {
		super(driver);
	}


    // Selects every cart row, regardless of its specific product id --
    // matches the "starts with" pattern shared by all rows: id="product-2", id="product-5", etc.
    private final By cartRows = By.cssSelector("tr[id^='product-']");

    // Scoped WITHIN a single row (used inside the loop in getCartItems()),
    // not searched against the whole page.
    private final By rowProductName = By.cssSelector(".cart_description h4 a");
    private final By rowPrice       = By.cssSelector(".cart_price p");
    private final By rowQuantity    = By.cssSelector(".cart_quantity button");
    private final By rowTotal       = By.cssSelector(".cart_total_price");

    // NOTE: verify this against the real page if not already confirmed --
    // empty-cart state on this site typically renders this id.
    private final By emptyCartMessage = By.id("empty_cart");

    private final By checkoutButton         = By.cssSelector(".check_out");
    private final By checkoutModal          = By.id("checkoutModal");
    private final By checkoutModalLoginLink = By.cssSelector("#checkoutModal .modal-body a");
    private final By continueOnCartButton   = By.cssSelector(".close-checkout-modal");


    /**
     * Reads every row in the cart into a List<CartItem>.
     * findElement() called ON a WebElement (a single row), rather than on
     * `driver`, scopes the search to inside that one row only -- guaranteeing
     * name/price/quantity/total all belong to the same product.
     */
    public List<CartItem> getCartItems() {
        wait.until(ExpectedConditions.presenceOfElementLocated(cartRows));
        List<WebElement> rows = driver.findElements(cartRows);
        List<CartItem> items = CartTableReader.readRows(rows, rowProductName, rowPrice, rowQuantity, rowTotal);
        return items;
    }

    /**
     * Removes the product with the given name from the cart.
     * Scoped XPath: finds the <tr> whose description cell contains a link
     * with this exact product name, then clicks the delete icon that is a
     * descendant of that SAME <tr>. No URL change, so returns this. 
     */
    public CartPage removeProduct(String productName) {
        By scopedDelete = By.xpath(
            "//tr[td[@class='cart_description']//a[normalize-space()='" + productName + "']]" +
            "//a[contains(@class,'cart_quantity_delete')]"
        );

        // A SECOND, separate locator -- representing the whole <tr> row for
        // this product, not just the delete link inside it.
        By scopedRow = By.xpath(
            "//tr[td[@class='cart_description']//a[normalize-space()='" + productName + "']]"
        );

        // Wait #1 (already existed): confirms the delete link is ready to be
        // clicked, THEN clicks it.
        wait.until(ExpectedConditions.elementToBeClickable(scopedDelete)).click();

        // Wait #2 (new): confirms the click's EFFECT actually finished --
        // the row genuinely disappearing from the DOM -- before this method
        // returns control back to the test.
        wait.until(ExpectedConditions.invisibilityOfElementLocated(scopedRow));

        return this;
    }
    /**
     * Checks whether the cart is currently empty, based on the empty-cart
     * message's visibility. Same try/catch pattern as isAt() -- a timeout
     * (message never appears) is caught and converted to false.
     */
    public boolean isCartEmpty() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartMessage)) != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clicks "Proceed to Checkout" as a GUEST. The login/register modal
     * appears; no URL change, so this returns `this`.
     */
    public CartPage proceedToCheckoutAsGuest() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutModal));
        return this;
    }

    /**
     * Clicks the "Register / Login" link inside the checkout modal.
     */
    public LoginPage continueToLoginFromModal() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutModalLoginLink)).click();
        return new LoginPage(driver);
    }

    /**
     * Clicks "Continue On Cart" inside the modal, closing it and staying
     * on the cart page.
     */
    public CartPage returnToCartFromModal() {
        wait.until(ExpectedConditions.elementToBeClickable(continueOnCartButton)).click();
        return this;
    }

    /**
     * Clicks "Proceed to Checkout" as an already-LOGGED-IN user -- skips
     * the modal entirely, URL changes, lands directly on CheckoutPage.
     */
    public CheckoutPage proceedToCheckoutAsLoggedInUser() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
        return new CheckoutPage(driver);
    }
}