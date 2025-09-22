package com.omar.fbank.address.controller;

import com.omar.fbank.util.TestSetup;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetAddressesTest extends TestSetup {

    @Test
    void adminShouldGetAddresses() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .get("/api/addresses")
                .then()
                .statusCode(200);
    }

    @Test
    void userShouldNotGetAddresses() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .when()
                .get("/api/addresses")
                .then()
                .statusCode(403);
    }
}
