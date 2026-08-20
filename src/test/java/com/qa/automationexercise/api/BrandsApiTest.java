package com.qa.automationexercise.api;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;

public class BrandsApiTest {

    @BeforeClass
    public void setupParser() {
        RestAssured.registerParser("text/html", Parser.JSON);
    }

    @Test
    public void getBrandsList_returns200AndKnownBrands() {
        given()
            .when()
                .get("https://automationexercise.com/api/brandsList")
            .then()
                .statusCode(200)
                .body("responseCode", equalTo(200))
                .body("brands.size()", greaterThan(0))
                .body("brands.brand", hasItem("Polo"))
                .body("brands.brand", hasItem("H&M"));
    }
}