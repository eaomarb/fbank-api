package com.omar.fbank.customer.controller;

import com.omar.fbank.util.TestSetup;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetCustomersTest extends TestSetup {
    @Test
    void adminShouldReturnCustomers() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .get("/api/customers")
                .then()
                .statusCode(200);
    }

    @Test void customerShoulNotReturnCustomers() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .when()
                .get("/api/customers")
                .then()
                .statusCode(403);
    }
}
