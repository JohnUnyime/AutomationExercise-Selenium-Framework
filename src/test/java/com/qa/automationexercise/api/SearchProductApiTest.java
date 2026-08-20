package com.qa.automationexercise.api;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class SearchProductApiTest extends BaseApiTest {

    @Test
    public void searchProduct_withValidTerm_returns200AndMatchingProducts() {
        given()
            .formParam("search_product", "top")
        .when()
            .post("https://automationexercise.com/api/searchProduct")
        .then()
            .statusCode(200)
            .body("responseCode", equalTo(200))
            .body("products.size()", greaterThan(0));
    }
}