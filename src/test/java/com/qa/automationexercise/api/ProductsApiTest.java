package com.qa.automationexercise.api;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class ProductsApiTest extends BaseApiTest {

    @Test
    public void getProductsList_returns200AndProductsData() {
        given()
            .when()
                .get("https://automationexercise.com/api/productsList")
            .then()
                .statusCode(200)
                .body("responseCode", equalTo(200))
                .body("products.size()", greaterThan(0));
    }
}