package com.omar.fbank.user.controller;

import com.omar.fbank.util.TestSetup;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetUsersTest extends TestSetup {

    @Test
    void adminShouldGetUsers() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .get("/api/users")
                .then()
                .statusCode(200);
    }

    @Test
    void customerShouldNotGetUsers() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .when()
                .get("/api/users")
                .then()
                .statusCode(403);
    }
}
