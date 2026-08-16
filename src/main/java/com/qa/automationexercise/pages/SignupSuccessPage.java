package com.qa.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class SignupSuccessPage extends BasePage {


    public SignupSuccessPage(WebDriver driver) {
		super(driver);
	}
    
    private final By accountCreatedHeading    = By.xpath("//b[normalize-space()='Account Created!']");
    private final By congratulationsText      = By.xpath("//p[normalize-space()='Congratulations! Your new account has been successfully created!']");
    private final By welcomeText = By.xpath("//p[normalize-space()='You can now take advantage of member privileges to enhance your online shopping experience with us.']");
    private final By continueButton        = By.cssSelector("[data-qa='continue-button']");
    
    
    public boolean isAccountCreatedHeadingMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(accountCreatedHeading)) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public String getAccountCreatedHeadingMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(accountCreatedHeading)).getText();
    }
    
    public boolean isCongratulationsMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(congratulationsText)) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCongratulationsMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(congratulationsText)).getText();
    }
    
    public boolean isWelcomeMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeText)) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public String getWelcomeMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeText)).getText();
    }

    public HomePage clickContinue() {
    	wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        return new HomePage(driver);
    }

}