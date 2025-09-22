package com.omar.fbank.address.controller;

import com.omar.fbank.util.TestSetup;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class GetAddressByIdTest extends TestSetup {

    @Test
    void adminShouldGetAnyAddress() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .get("/api/addresses/7b9f3c2a-1111-4e2b-8d3a-1a2b3c4d5e6f")
                .then()
                .statusCode(200)
                .body("id", is("7b9f3c2a-1111-4e2b-8d3a-1a2b3c4d5e6f"));
    }

    @Test
    void userShouldGetHisAddress() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .when()
                .get("/api/addresses/7b9f3c2a-1111-4e2b-8d3a-1a2b3c4d5e6f")
                .then()
                .statusCode(200)
                .body("id", is("7b9f3c2a-1111-4e2b-8d3a-1a2b3c4d5e6f"));
    }

    @Test
    void userShouldNotGetAnotherUsersAddress() {
        given()
                .header("Authorization", "Bearer " + getCustomer2Token())
                .when()
                .get("/api/addresses/7b9f3c2a-1111-4e2b-8d3a-1a2b3c4d5e6f")
                .then()
                .statusCode(403);
    }

    @Test
    void shouldReturnNotFoundForNonExistentAddress() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .get("/api/addresses/99999999-aaaa-bbbb-cccc-111111111111")
                .then()
                .statusCode(404);
    }
}
