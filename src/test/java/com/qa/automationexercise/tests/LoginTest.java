package com.qa.automationexercise.tests;

import java.io.IOException;
import java.io.InputStream;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.automationexercise.pages.AccountInfo;
import com.qa.automationexercise.pages.HomePage;
import com.qa.automationexercise.pages.LoginPage;
import com.qa.automationexercise.pages.SignupPage;
import com.qa.automationexercise.pages.SignupSuccessPage;
import com.qa.automationexercise.testdata.InvalidLogin;

public class LoginTest extends Basetest {

    @DataProvider(name = "invalidLoginsData")
    public Object[][] invalidLoginsData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream jsonStream = getClass().getResourceAsStream("/testdata/invalidLogins.json");
        InvalidLogin[] invalidLogins = mapper.readValue(jsonStream, InvalidLogin[].class);

        Object[][] data = new Object[invalidLogins.length][1];
        for (int i = 0; i < invalidLogins.length; i++) {
            data[i][0] = invalidLogins[i];
        }
        return data;
    }

    // Case 1: Register with valid new details -> account created successfully
    @Test
    public void signup_withNewEmail_createsAccount() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickSignupLogin();

        String uniqueEmail = "newuser_" + System.currentTimeMillis() + "@test.com";

        SignupPage signupPage = loginPage.signup("Test User", uniqueEmail);

        AccountInfo info = new AccountInfo(
            "Mr", "Password123", "10", "May", "1995", true, true,
            "Test", "User", "TestCo", "123 Test Street", "",
            "India", "Rivers", "Port Harcourt", "500001", "08000000000"
        );

        SignupSuccessPage successPage = signupPage.createAccount(info);

        Assert.assertTrue(successPage.isAccountCreatedHeadingMessageDisplayed(),
            "Expected the 'Account Created!' heading to be displayed after a successful signup, but it was not.");
    }

    // Case 2: Register with an already-used email -> error shown
    @Test
    public void signup_withAlreadyUsedEmail_showsError() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickSignupLogin();

        loginPage.attemptSignup("Test User", "xklclkcdj@gmail.com");

        Assert.assertTrue(loginPage.isSignupErrorDisplayed(),
            "Expected a signup error message for an already-used email, but none appeared.");

        Assert.assertTrue(loginPage.isAtSignup(),
            "Expected to remain on the /signup page after a failed signup, but URL changed.");
    }

    // Case 3: Login with valid credentials -> succeeds
    @Test
    public void login_validCredentials_showsSuccess() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickSignupLogin();

        loginPage.login("xklclkcdj@gmail.com", "godisgood");
        HomePage homePageAfterLogin = new HomePage(driver);

        Assert.assertTrue(homePageAfterLogin.isLogoutLinkDisplayed(),
            "Expected to see a Logout link after a successful login, but it was not displayed.");

        Assert.assertFalse(loginPage.isAt(),
            "Expected to navigate away from /login after a successful login, but URL still contains /login.");
    }

    // Cases 4 & 5: Login with invalid credentials (wrong password / unregistered email) -> error shown
    @Test(dataProvider = "invalidLoginsData")
    public void login_withInvalidCredentials_showsError(InvalidLogin invalidLogin) {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickSignupLogin();

        String email = invalidLogin.getResolvedEmail();
        String password = invalidLogin.getPassword();

        loginPage.login(email, password);

        Assert.assertTrue(loginPage.isLoginErrorDisplayed(),
            "Expected a login error message for scenario '" + invalidLogin.getScenario()
                + "', but none appeared.");

        Assert.assertTrue(loginPage.isAt(),
            "Expected to remain on the /login page after a failed login (scenario: "
                + invalidLogin.getScenario() + "), but URL changed.");
    }
}