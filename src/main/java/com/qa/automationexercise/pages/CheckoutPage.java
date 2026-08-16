package com.qa.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.qa.automationexercise.utils.CartTableReader;

import java.util.List;

public class CheckoutPage extends BasePage {


    public CheckoutPage(WebDriver driver) {
		super(driver);
	}


    private final By cartRows = By.cssSelector("tr[id^='product-']");
   
    private final By rowProductName = By.cssSelector(".cart_description h4 a");
    private final By rowPrice       = By.cssSelector(".cart_price p");
    private final By rowQuantity    = By.cssSelector(".cart_quantity button");
    private final By rowTotal       = By.cssSelector(".cart_total_price");
    
    
    private final By commentBox = By.cssSelector("textarea[name='message']");
    private final By placeOrderButton = By.cssSelector(".check_out");

    public List<CartItem> getOrderSummary() {
        wait.until(ExpectedConditions.presenceOfElementLocated(cartRows));
        List<WebElement> rows = driver.findElements(cartRows);
        List<CartItem> items = CartTableReader.readRows(rows, rowProductName, rowPrice, rowQuantity, rowTotal);
        return items;
    }
    public CheckoutPage addComment(String text) {
    	 WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(commentBox));
         field.clear();
         field.sendKeys(text);
         return this;
    }

    public PaymentPage placeOrder() {
    	wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton)).click();
       return new PaymentPage(driver);
    }
}