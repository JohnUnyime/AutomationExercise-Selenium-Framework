package com.qa.automationexercise.base;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 0;
    private static final int MAX_RETRIES = 2;

    @Override
    public boolean retry(ITestResult result) {
        Throwable cause = result.getThrowable();
        boolean isRetryable = cause instanceof TimeoutException
                || cause instanceof ElementClickInterceptedException;

        if (retryCount < MAX_RETRIES && isRetryable) {
            retryCount++;
            System.out.println("Retrying test '" + result.getName() + "' - attempt "
                    + retryCount + " of " + MAX_RETRIES);
            return true;
        }
        return false;
    }
}