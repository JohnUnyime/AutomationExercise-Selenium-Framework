package com.qa.automationexercise.api;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {

    @BeforeClass
    public void setupParser() {
        RestAssured.registerParser("text/html", Parser.JSON);
    }
}
