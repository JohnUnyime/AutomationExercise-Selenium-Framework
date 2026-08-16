package com.qa.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BasePage {


    public HomePage(WebDriver driver) {
		super(driver);
	}



    private final By womenDressLink = By.linkText("Dress");
    private final By productNames = By.cssSelector(".product-overlay p");
    private final By signupLoginLink = By.cssSelector("a[href='/login']");
    private final By productsNavLink = By.cssSelector("a[href='/products']"); 
    private final By logoutLink = By.cssSelector("a[href='/logout']");
    private final By cartLink = By.cssSelector("a[href='/view_cart']");
    

    public List<String> getDisplayedProductNames() {
        List<WebElement> elements = driver.findElements(productNames);
        return elements.stream()
                .map(el -> el.getText())
                .collect(Collectors.toList());
    }
	
	public HomePage clickWomenDress() {
		   wait.until(ExpectedConditions.elementToBeClickable(womenDressLink)).click();
		   return this;
		}
	
	public HomePage clickBrand(String brandName) {
	    By brandLink = By.cssSelector("a[href='/brand_products/" + brandName + "']");
	    wait.until(ExpectedConditions.elementToBeClickable(brandLink)).click();
	    return this;
		
		}

	public LoginPage clickSignupLogin() {
	    wait.until(ExpectedConditions.elementToBeClickable(signupLoginLink)).click();
	    return new LoginPage(driver);
	}
	
	public ProductsPage clickProducts() {
	    wait.until(ExpectedConditions.elementToBeClickable(productsNavLink)).click();
	    return new ProductsPage(driver);
	}

	public boolean isLogoutLinkDisplayed() {
		try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink)).isDisplayed();
        } catch (Exception e) {
		return false;
        }
	}

	

	public CartPage clickCart() {
		  wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
	    return new CartPage(driver);
	}
}

