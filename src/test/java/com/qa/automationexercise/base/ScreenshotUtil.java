package com.qa.automationexercise.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    private static final String SCREENSHOT_DIR = "screenshots";

    public static void capture(WebDriver driver, String testName) {
        try {
            // 1. Cast driver to TakesScreenshot and grab the screenshot as a temp file
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // 2. Make sure the destination folder exists (creates it if missing)
            Path dirPath = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            // 3. Build a unique filename: testName + timestamp, so repeated
            // failures for the same test don't overwrite each other
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + "_" + timestamp + ".png";
            Path destPath = dirPath.resolve(fileName);

            // 4. Copy the temp file to the real destination
            Files.copy(srcFile.toPath(), destPath);

            System.out.println("Screenshot saved: " + destPath.toAbsolutePath());

        } catch (IOException e) {
            // Don't let a screenshot failure crash the actual test run --
            // just log it and move on.
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}