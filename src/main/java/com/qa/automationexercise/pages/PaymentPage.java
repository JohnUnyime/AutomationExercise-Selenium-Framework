package com.qa.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PaymentPage extends BasePage {
    public PaymentPage(WebDriver driver) {
		super(driver);
	}
    private final By nameOnCardField  = By.cssSelector("[data-qa='name-on-card']");
    private final By cardNumberField  = By.cssSelector("[data-qa='card-number']");
    private final By cvcField         = By.cssSelector("[data-qa='cvc']");
    private final By expiryMonthField = By.cssSelector("[data-qa='expiry-month']");
    private final By expiryYearField  = By.cssSelector("[data-qa='expiry-year']");
    private final By payButton        = By.cssSelector("[data-qa='pay-button']");
    private final By orderPlacementMessage = By.xpath("//p[contains(text(),'successfully!')]");

    public PaymentPage enterNameOnCard(String cardName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(nameOnCardField));
        field.clear();
        field.sendKeys(cardName);
        return this;
    }
    public PaymentPage enterCardNumber(String cardNumber) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(cardNumberField));
        field.clear();
        field.sendKeys(cardNumber);
        return this;
    }
    public PaymentPage enterCVC(String cvc) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(cvcField));
        field.clear();
        field.sendKeys(cvc);
        return this;
    }
    public PaymentPage enterExpiryMonth(String expiryMonth) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(expiryMonthField));
        field.clear();
        field.sendKeys(expiryMonth);
        return this;
    }
    public PaymentPage enterExpiryYear(String expiryYear) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(expiryYearField));
        field.clear();
        field.sendKeys(expiryYear);
        return this;
    }

    /**
     * Clicks "Pay and Confirm Order" and assumes success -- navigates to
     * OrderConfirmationPage. Use this for the happy-path flow only.
     */
    public OrderConfirmationPage payAndConfirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(payButton)).click();
        return new OrderConfirmationPage(driver);
    }

    /**
     * Clicks "Pay and Confirm Order" WITHOUT assuming navigation happens.
     * Use this when a required field (e.g. CVC) is deliberately left blank --
     * the browser's native HTML5 validation blocks the actual form
     * submission and shows a tooltip instead, so we never really leave this
     * page. Returns `this` (still PaymentPage) to reflect that reality,
     * same reasoning as LoginPage.attemptSignup() vs signup().
     */
    public PaymentPage attemptPay() {
        wait.until(ExpectedConditions.elementToBeClickable(payButton)).click();
        return this;
    }

    /**
     * Reads the CVC field's native HTML5 validation message directly off
     * the DOM element via JavaScript. Selenium's normal API (WebDriverWait /
     * ExpectedConditions) cannot see or interact with the native browser
     * tooltip itself -- it's rendered by the browser chrome, not the page's
     * DOM. But the underlying `validationMessage` property that populates
     * that tooltip IS a real, readable property on the element, exposed by
     * the HTML5 Constraint Validation API. Executing a small JS snippet
     * against the element lets us read it directly, bypassing the need to
     * "see" the tooltip at all.
     */
    public String getCvcValidationMessage() {
        WebElement field = driver.findElement(cvcField);
        return (String) ((JavascriptExecutor) driver)
            .executeScript("return arguments[0].validationMessage;", field);
    }

    public boolean isOrderSuccessMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(orderPlacementMessage)) != null;
        } catch (Exception e) {
            return false;
        }
    }
    public String getOrderPlacementMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderPlacementMessage)).getText();
    }
}