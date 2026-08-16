package com.qa.automationexercise.base;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.qa.automationexercise.tests.Basetest;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("=================================");
        System.out.println("🚀 TEST EXECUTION STARTED");
        System.out.println("Test Name: " + result.getName());
        System.out.println("=================================");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("=================================");
        System.out.println("✅ TEST EXECUTION PASSED");
        System.out.println("Test Name: " + result.getName());
        System.out.println("=================================");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable cause = result.getThrowable();
        System.out.println("=================================");
        System.out.println("❌ TEST EXECUTION FAILED!");
        System.out.println("Test Name: " + result.getName());
        System.out.println("Exception: " + cause.getClass().getSimpleName() + " - " + cause.getMessage());
        System.out.println("=================================");

        // Reach into the failing test instance to grab its live driver,
        // then capture a screenshot of the browser at the moment of failure.
        Object testInstance = result.getInstance();
        if (testInstance instanceof Basetest) {
            WebDriver driver = ((Basetest) testInstance).driver;
            ScreenshotUtil.capture(driver, result.getName());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("=================================");
        System.out.println("⚠️ TEST EXECUTION SKIPPED, CHECK THE ERROR MESSAGE");
        System.out.println("Test Name: " + result.getName());
        System.out.println("=================================");
    }
}