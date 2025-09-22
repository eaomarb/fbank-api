package com.omar.fbank.customeraccount.controller;

import com.omar.fbank.util.TestSetup;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UpdateCustomerAccountOwnershipTest extends TestSetup {
    @Test
    void adminShouldUpdateOwnership() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .queryParam("isOwner", false)
                .when()
                .patch("/api/customersAccounts/e2f3b4c5-1111-4b2c-8d3b-111122223336/ownership")
                .then()
                .statusCode(204);
    }
    @Test
    void userShouldUpdateOwnership() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .queryParam("isOwner", false)
                .when()
                .patch("/api/customersAccounts/e2f3b4c5-1111-4b2c-8d3b-111122223336/ownership")
                .then()
                .statusCode(204);
    }

    @Test
    void userShouldNotUpdateAnotherUsersOwnership() {
        given()
                .header("Authorization", "Bearer " + getCustomer2Token())
                .queryParam("isOwner", false)
                .when()
                .patch("/api/customersAccounts/e2f3b4c5-1111-4b2c-8d3b-111122223336/ownership")
                .then()
                .statusCode(403);
    }

    @Test
    void shouldReturnAlreadyCurrentValueException() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .queryParam("isOwner", true)
                .when()
                .patch("/api/customersAccounts/e2f3b4c5-1111-4b2c-8d3b-111122223336/ownership")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldReturnOnlyOneOwnerException() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .queryParam("isOwner", false)
                .when()
                .patch("/api/customersAccounts/e1f2a3b4-1111-4a2b-8d3a-111122223335/ownership")
                .then()
                .statusCode(409);
    }
}