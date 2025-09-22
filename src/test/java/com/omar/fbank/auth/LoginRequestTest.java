package com.omar.fbank.auth;

import com.omar.fbank.util.TestSetup;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class LoginRequestTest extends TestSetup {
    String token;

    @Test
    void shouldReturnTokenWhenLoginWithValidUser() {
        LoginRequest loginRequest = new LoginRequest(
                "carlos.garcia@mail.com",
                "carlos"
        );

        Response response = given()
                .contentType("application/json")
                .body(loginRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .extract().response();

        token = response.jsonPath().getString("token");
    }

    @Test
    void shouldReturn404WhenLoginWithNonExistentUser() {
        LoginRequest loginRequest = new LoginRequest(
                "fake@email.com",
                "fakepassword"
        );

        given()
                .contentType("application/json")
                .body(loginRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(404)
                .extract().response();
    }

    @Test
    void shouldReturn403WhenLoginWithInvalidCredentialsAndExistingUser() {
        LoginRequest loginRequest = new LoginRequest(
                "carlos.garcia@mail.com",
                "notcarlospassword"
        );

        given()
                .contentType("application/json")
                .body(loginRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(403)
                .extract().response();
    }
}