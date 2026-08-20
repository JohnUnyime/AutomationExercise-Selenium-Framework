package com.qa.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private final By loginEmailField    = By.cssSelector("input[data-qa='login-email']");
    private final By loginPasswordField = By.cssSelector("input[data-qa='login-password']");
    private final By loginButton        = By.cssSelector("button[data-qa='login-button']");
    private final By signupNameField    = By.cssSelector("input[data-qa='signup-name']");
    private final By signupEmailField   = By.cssSelector("input[data-qa='signup-email']");
    private final By signupButton       = By.cssSelector("button[data-qa='signup-button']");
    private final By loginErrorMessage  = By.xpath("//p[contains(text(),'incorrect')]");

    // Matches a stable fragment of the real signup error text
    // ("Email Address already exist!"), same partial-match pattern as
    // loginErrorMessage, rather than the full sentence.
    private final By signupErrorMessage = By.xpath("//p[contains(text(),'already exist')]");

    public LoginPage enterLoginEmail(String email) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(loginEmailField));
        field.clear();
        field.sendKeys(email);
        return this;
    }

    public LoginPage enterLoginPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(loginPasswordField));
        field.clear();
        field.sendKeys(password);
        return this;
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public LoginPage login(String email, String password) {
        enterLoginEmail(email);
        enterLoginPassword(password);
        clickLoginButton();
        return this;
    }

    public LoginPage enterSignupName(String name) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(signupNameField));
        field.clear();
        field.sendKeys(name);
        return this;
    }

    public LoginPage enterSignupEmail(String email) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(signupEmailField));
        field.clear();
        field.sendKeys(email);
        return this;
    }

    public void clickSignupButton() {
        wait.until(ExpectedConditions.elementToBeClickable(signupButton)).click();
    }

    /**
     * Use this for the SUCCESS case (new, unused email). The site navigates
     * away from /login to the account-info step on success, so this returns
     * SignupPage.
'     */
    public SignupPage signup(String name, String email) {
        enterSignupName(name);
        enterSignupEmail(email);
        clickSignupButton();
        return new SignupPage(driver);
    }

    /**
     * Use this for the FAILURE case (e.g. duplicate email). On failure the
     * site does NOT navigate away -- it stays on /login and shows
     * signupErrorMessage. Returning `this` (still LoginPage) reflects that
     * reality, instead of lying with a SignupPage type when no navigation
     * actually happened.
     */
    public LoginPage attemptSignup(String name, String email) {
        enterSignupName(name);
        enterSignupEmail(email);
        clickSignupButton();
        return this;
    }

    public boolean isLoginErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loginErrorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getLoginErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginErrorMessage)).getText();
    }

    public boolean isSignupErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(signupErrorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getSignupErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(signupErrorMessage)).getText();
    }

    public boolean isAt() {
        try {
            return wait.until(ExpectedConditions.urlContains("/login"));
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isAtSignup() {
        try {
            return wait.until(ExpectedConditions.urlContains("/signup"));
        } catch (Exception e) {
            return false;
        }
    }
}