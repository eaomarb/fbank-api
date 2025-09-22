package com.omar.fbank.customeraccount.controller;

import com.omar.fbank.util.TestSetup;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetCustomerAccountsByCustomerIdTest extends TestSetup {

    @Test
    void userShouldGetHisCustomerAccountsByCustomerId() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .when()
                .get("/api/customersAccounts/customer/a1b2c3d4-1111-4e2b-8f3a-111122223333")
                .then()
                .statusCode(200);
    }

    @Test
    void userShouldNotGetAnotherCustomersAccountsByCustomerId() {
        given()
                .header("Authorization", "Bearer " + getCustomer2Token())
                .when()
                .get("/api/customersAccounts/customer/a1b2c3d4-1111-4e2b-8f3a-111122223333")
                .then()
                .statusCode(403);
    }
}
