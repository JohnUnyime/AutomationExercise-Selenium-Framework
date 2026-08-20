package com.qa.automationexercise.reqres;

import org.testng.annotations.Test;
import com.qa.automationexercise.utils.ConfigReader;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReqresUserApiTest {

    @Test
    public void updateUser_withPut_returnsUpdatedName() {
        given()
            .header("x-api-key", ConfigReader.getReqresApiKey())
            .contentType("application/json")
            .body("{ \"name\": \"morpheus\", \"job\": \"zion resident\" }")
        .when()
            .put("https://reqres.in/api/users/2")
        .then()
            .statusCode(200)
            .body("name", equalTo("morpheus"));
    }

    @Test
    public void deleteUser_returns204() {
        given()
            .header("x-api-key", ConfigReader.getReqresApiKey())
        .when()
            .delete("https://reqres.in/api/users/2")
        .then()
            .statusCode(204);
    }
}