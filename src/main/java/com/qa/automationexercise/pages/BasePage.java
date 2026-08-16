package com.qa.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    // TODO: locator for the "Logout" link. No class/id on it, so this
    // needs XPath -- same normalize-space() pattern you've used before.
    private final By logoutLink = By.xpath("//a[contains(@href,'logout')]");
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Checks whether the user is currently logged in, based on whether
     * the "Logout" link is visible in the shared header. Same isAt()-style
     * try/catch pattern -- a timeout (Logout never appears) is caught and
     * converted to false.
     * TODO
     */
    public boolean isLoggedIn() {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink)) != null;
            } catch (Exception e) {
                return false;
            }
    }
}
