package com.qa.automationexercise.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qa.automationexercise.base.DriverFactory;
import com.qa.automationexercise.utils.ConfigReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.qa.automationexercise.base.TestListener;


@Listeners(TestListener.class)
public class Basetest {

    // public (not protected) so TestListener, which lives in a different
    // package and is NOT a subclass of Basetest, can still reach this field
    // to grab a screenshot when a test fails.
    public WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "headless"})
    public void setUp(@Optional("chrome") String browser,
                       @Optional("false") String headlessParam) {
        String resolvedBrowser = System.getProperty("browser", browser);
        boolean resolvedHeadless = Boolean.parseBoolean(
                System.getProperty("headless", headlessParam));
        driver = DriverFactory.initDriver(resolvedBrowser, resolvedHeadless);
        blockAdDomains();
        driver.get(ConfigReader.getBaseUrl());
    }

    /**
     * Blocks known ad-serving domains at the network level using Chrome DevTools
     * Protocol, before any navigation happens. Confirmed root cause of flaky
     * TimeoutException / ElementClickInterceptedException failures on
     * automationexercise.com (Google ad stack + Flashtalking).
     */
    protected void blockAdDomains() {
        if (!(driver instanceof ChromeDriver)) {
            return; // ad-blocking via CDP is Chrome-specific; skip for other browsers
        }
        ChromeDriver chromeDriver = (ChromeDriver) driver;
        Map<String, Object> params = new HashMap<>();
        params.put("urls", List.of(
            "*doubleclick.net*",
            "*googlesyndication.com*",
            "*googleadservices.com*",
            "*flashtalking.com*"
            // ... rest of your existing method unchanged
        ));
        // ... whatever comes after, unchanged
    
        chromeDriver.executeCdpCommand("Network.enable", new HashMap<>());
        chromeDriver.executeCdpCommand("Network.setBlockedURLs", params);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}