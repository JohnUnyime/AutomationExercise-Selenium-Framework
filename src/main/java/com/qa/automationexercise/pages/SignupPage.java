package com.qa.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class SignupPage extends BasePage {

    private final By passwordField    = By.cssSelector("[data-qa='password']");
    private final By daysDropdown     = By.cssSelector("[data-qa='days']");
    private final By monthsDropdown   = By.cssSelector("[data-qa='months']");
    private final By yearsDropdown    = By.cssSelector("[data-qa='years']");
    private final By newsletterCheckbox = By.id("newsletter");
    private final By optinCheckbox      = By.id("optin");
    private final By firstNameField   = By.cssSelector("[data-qa='first_name']");
    private final By lastNameField    = By.cssSelector("[data-qa='last_name']");
    private final By companyField     = By.cssSelector("[data-qa='company']");
    private final By address1Field    = By.cssSelector("[data-qa='address']");
    private final By address2Field    = By.cssSelector("[data-qa='address2']");
    private final By countryDropdown  = By.cssSelector("[data-qa='country']");
    private final By stateField       = By.cssSelector("[data-qa='state']");
    private final By cityField        = By.cssSelector("[data-qa='city']");
    private final By zipcodeField     = By.cssSelector("[data-qa='zipcode']");
    private final By mobileNumberField = By.cssSelector("[data-qa='mobile_number']");
    private final By createAccountButton = By.cssSelector("[data-qa='create-account']");

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Selects the "Mr" or "Mrs" title radio. One dynamic locator built from
     * the argument, same idea as addProductToCart()'s scoped XPath -- here
     * it's a CSS attribute selector filled in at call time via String.format.
     */
    public SignupPage enterTitle(String title) {
        By dynamicTitleLocator = By.cssSelector(
            "input[name='title'][value='" + title + "']"
        );
        wait.until(ExpectedConditions.elementToBeClickable(dynamicTitleLocator)).click();
        return this;
    }

    public SignupPage enterPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        field.clear();
        field.sendKeys(password);
        return this;
    }

    public SignupPage selectDay(String day) {
        Select dropdown = new Select(driver.findElement(daysDropdown));
        dropdown.selectByVisibleText(day);
        return this;
    }

    public SignupPage selectMonth(String month) {
        Select dropdown = new Select(driver.findElement(monthsDropdown));
        dropdown.selectByVisibleText(month);
        return this;
    }

    public SignupPage selectYear(String year) {
        Select dropdown = new Select(driver.findElement(yearsDropdown));
        dropdown.selectByVisibleText(year);
        return this;
    }

    public SignupPage checkNewsletter() {
        WebElement checkbox = driver.findElement(newsletterCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        return this;
    }

    public SignupPage checkOptin() {
        WebElement checkbox = driver.findElement(optinCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        return this;
    }

    public SignupPage enterFirstName(String firstName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
        field.clear();
        field.sendKeys(firstName);
        return this;
    }

    public SignupPage enterLastName(String lastName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField));
        field.clear();
        field.sendKeys(lastName);
        return this;
    }

    public SignupPage enterCompany(String company) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(companyField));
        field.clear();
        field.sendKeys(company);
        return this;
    }

    public SignupPage enterAddress1(String address1) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(address1Field));
        field.clear();
        field.sendKeys(address1);
        return this;
    }

    public SignupPage enterAddress2(String address2) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(address2Field));
        field.clear();
        field.sendKeys(address2);
        return this;
    }

    public SignupPage selectCountry(String country) {
        Select dropdown = new Select(driver.findElement(countryDropdown));
        dropdown.selectByVisibleText(country);
        return this;
    }

    public SignupPage enterState(String state) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(stateField));
        field.clear();
        field.sendKeys(state);
        return this;
    }

    public SignupPage enterCity(String city) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(cityField));
        field.clear();
        field.sendKeys(city);
        return this;
    }

    public SignupPage enterZipcode(String zipcode) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(zipcodeField));
        field.clear();
        field.sendKeys(zipcode);
        return this;
    }

    public SignupPage enterMobileNumber(String mobileNumber) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileNumberField));
        field.clear();
        field.sendKeys(mobileNumber);
        return this;
    }

    /**
     * Composes every field method above using an AccountInfo object,
     * same "one big method calls the small ones" pattern as login().
     * TODO: after filling every field, click createAccountButton and
     * return the confirmation page. What should this method return,
     * and what's the constructor call for it?
     */
    public SignupSuccessPage createAccount(AccountInfo info) {
        enterTitle(info.getTitle());
        enterPassword(info.getPassword());
        selectDay(info.getDay());
        selectMonth(info.getMonth());
        selectYear(info.getYear());

        if (info.isNewsletter()) {
            checkNewsletter();
        }
        if (info.isOptin()) {
            checkOptin();
        }

        enterFirstName(info.getFirstName());
        enterLastName(info.getLastName());
        enterCompany(info.getCompany());
        enterAddress1(info.getAddress1());
        enterAddress2(info.getAddress2());
        selectCountry(info.getCountry());
        enterState(info.getState());
        enterCity(info.getCity());
        enterZipcode(info.getZipcode());
         
    enterMobileNumber(info.getMobileNumber());

    wait.until(ExpectedConditions.elementToBeClickable(createAccountButton)).click();
    return new SignupSuccessPage(driver);
}

	
    }		
