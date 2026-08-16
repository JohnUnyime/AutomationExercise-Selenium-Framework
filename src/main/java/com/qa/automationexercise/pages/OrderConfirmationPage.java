package com.qa.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OrderConfirmationPage extends BasePage {
    public OrderConfirmationPage(WebDriver driver) {
		super(driver);
	}

    private final By orderPlacedHeading    = By.xpath("//b[normalize-space()='Order Placed!']");
    private final By confirmationText      = By.xpath("//p[normalize-space()='Congratulations! Your order has been confirmed!']");

    // NOTE: this locator is a placeholder copied from CartPage/CheckoutPage's
    // "Proceed to Checkout" button (.check_out) -- it is almost certainly
    // WRONG for this page. Verify the real "Download Invoice" button/link on
    // the actual order confirmation page in DevTools and replace this before
    // relying on clickDownloadInvoice().
    private final By downloadInvoiceButton = By.cssSelector("a[href^='/download_invoice']");
    private final By continueButton        = By.cssSelector("[data-qa='continue-button']");

    public boolean isOrderPlacedHeadingMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(orderPlacedHeading)) != null;
        } catch (Exception e) {
            return false;
        }
    }
    public String getOrderPlacedHeadingMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderPlacedHeading)).getText();
    }
    public boolean isOrderConfirmationMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationText)) != null;
        } catch (Exception e) {
            return false;
        }
    }
    public String getOrderConfirmationMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationText)).getText();
    }
    public OrderConfirmationPage clickDownloadInvoice() {
        wait.until(ExpectedConditions.elementToBeClickable(downloadInvoiceButton)).click();
        return this;
    }
    public HomePage clickContinue() {
    	wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        return new HomePage(driver);
    }
}