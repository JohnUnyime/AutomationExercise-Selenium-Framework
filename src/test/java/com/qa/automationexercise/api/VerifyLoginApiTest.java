package com.qa.automationexercise.api;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class VerifyLoginApiTest extends BaseApiTest {

    @Test
    public void verifyLogin_withValidCredentials_returnsUserExists() {
        given()
            .formParam("email", "xklclkcdj@gmail.com")
            .formParam("password", "godisgood")
        .when()
            .post("https://automationexercise.com/api/verifyLogin")
        .then()
            .statusCode(200)
            .body("responseCode", equalTo(200))
            .body("message", equalTo("User exists!"));
    }
}