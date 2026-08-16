package com.qa.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage extends BasePage {


    public ProductsPage(WebDriver driver) {
		super(driver);
	}


    private final By searchProductField = By.id("search_product");
    private final By searchButton       = By.id("submit_search");
    private final By productInfoBlocks  = By.cssSelector(".productinfo.text-center");
    private final By productNames       = By.cssSelector(".productinfo.text-center p");
    private final By viewCartModalLink 	= By.cssSelector("#cartModal a[href='/view_cart']");
    private final By continueShoppingButton = By.cssSelector(".close-modal");

    
    public ProductsPage closeAddedModal() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton)).click();
        return this;
    }

    
    /**
     * Types the given keyword into the search box and submits the search.
     * No URL change happens as a result of typing/searching on this site's
     * search flow staying on /products, so this stays on ProductsPage (returns this).
     */
    public ProductsPage searchProduct(String keyword) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(searchProductField));
        field.clear();
        field.sendKeys(keyword);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        return this;
    }

    /**
     * Reads the names of every product currently displayed in the grid.
     * Waits for at least one product block to be present first, so this
     * doesn't run against a page that hasn't finished rendering results yet
     * (e.g. immediately after searchProduct()).
     */
    public List<String> getDisplayedProductNames() {
        wait.until(ExpectedConditions.presenceOfElementLocated(productInfoBlocks));
        List<WebElement> elements = driver.findElements(productNames);
        return elements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns how many products are currently displayed.
     * Derived from the same list getDisplayedProductNames() reads, rather than
     * a separate "X Products Found" heading -- that heading isn't confirmed to
     * exist on this site's /products page. If you find a real count element,
]     * swap this to read it directly instead.
     */
    public int getProductCount() {
        return getDisplayedProductNames().size();
    }

    /**
     * Adds the product with the given exact visible name to the cart.
     * Scoped XPath: finds the .productinfo container that has a <p> child
     * matching productName exactly, then clicks the add-to-cart link that is
     * a sibling within that SAME container -- not the first "Add to cart"
     * link on the whole page. This is what ties the click to the right product.
     * No URL change occurs (a modal appears over the same page), so this
     * returns `this`, consistent with searchProduct().
     */
    public ProductsPage addProductToCart(String productName) {
        By scopedAddToCart = By.xpath(
            "//div[@class='productinfo text-center'][p[normalize-space()='" + productName + "']]" +
            "//a[contains(@class,'add-to-cart')]"
        );
        wait.until(ExpectedConditions.elementToBeClickable(scopedAddToCart)).click();
        return this;
    }
    
    public CartPage clickViewCartFromModal() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCartModalLink)).click();
        return new CartPage(driver);
    }

 // Add these two fields alongside your existing By fields at the top of the class:

   

    // Add these two methods, following the same shape as searchProduct():
    public ProductsPage clickBrand(String brandName) {
        By brandLocator = By.xpath("//a[@href='/brand_products/" + brandName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(brandLocator)).click();
        return this;
    }
    public ProductsPage clickCategory(String parentGroup, int categoryId) {
        By accordionToggle = By.cssSelector("a[href='#" + parentGroup + "']");
        wait.until(ExpectedConditions.elementToBeClickable(accordionToggle)).click();

        By categoryLink = By.xpath("//a[@href='/category_products/" + categoryId + "']");
        wait.until(ExpectedConditions.elementToBeClickable(categoryLink)).click();

        return this;
    }    
}
