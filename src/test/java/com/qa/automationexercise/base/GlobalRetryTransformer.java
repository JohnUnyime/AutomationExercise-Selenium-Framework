package com.qa.automationexercise.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class GlobalRetryTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                           Constructor testConstructor, Method testMethod) {
        // TestNG calls this once for every @Test method, right before it runs.
        // Setting the retry analyzer here means every test in the project gets
        // RetryAnalyzer attached automatically -- no need to add
        // @Test(retryAnalyzer = RetryAnalyzer.class) to each test method by hand.
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
