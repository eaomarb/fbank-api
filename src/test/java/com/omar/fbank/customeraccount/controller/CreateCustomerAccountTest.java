package com.omar.fbank.customeraccount.controller;

import com.omar.fbank.util.TestSetup;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CreateCustomerAccountTest extends TestSetup {
    @Test
    void adminShouldCreateCustomerAccount() {


        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .post("/api/customersAccounts/c1d2e3f4-1111-4a2b-8d3a-111122223333/b2c3d4e5-2222-4f5b-9c6d-222233334444")
                .then()
                .statusCode(204);
    }

    @Test
    void userShouldCreateCustomerAccount() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .when()
                .post("/api/customersAccounts/c1d2e3f4-1111-4a2b-8d3a-111122223333/b2c3d4e5-2222-4f5b-9c6d-222233334444")
                .then()
                .statusCode(204);
    }

    @Test
    void userShouldNotCreateCustomerAccountForOtherCustomer() {
        given()
                .header("Authorization", "Bearer " + getCustomer2Token())
                .when()
                .post("/api/customersAccounts/c1d2e3f4-1111-4a2b-8d3a-111122223333/aaaaaaaa-3333-4444-8888-cccccccc3333")
                .then()
                .statusCode(403);
    }
}
