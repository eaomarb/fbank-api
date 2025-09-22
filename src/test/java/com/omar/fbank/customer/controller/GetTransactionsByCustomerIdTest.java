package com.omar.fbank.customer.controller;

import com.omar.fbank.util.TestSetup;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetTransactionsByCustomerIdTest extends TestSetup {
    @Test
    void adminShouldGetTransactionsOfAnyCustomer() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .get("/api/customers/a1b2c3d4-1111-4e2b-8f3a-111122223333/transactions")
                .then()
                .statusCode(200);
    }

    @Test
    void userShouldGetHisTransactions() {
        given()
                .header("Authorization", "Bearer " + getCustomer2Token())
                .when()
                .get("/api/customers/b2c3d4e5-2222-4f5b-9c6d-222233334444/transactions")
                .then()
                .statusCode(200);
    }

    @Test
    void userShouldNotGetTransactionsOfAnotherCustomer() {
        given()
                .header("Authorization", "Bearer " + getCustomer3Token())
                .when()
                .get("/api/customers/a1b2c3d4-1111-4e2b-8f3a-111122223333/transactions")
                .then()
                .statusCode(403);
    }

    @Test
    void shouldReturnNotFoundForNonExistentCustomer() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .get("/api/customers/99999999-aaaa-bbbb-cccc-111111111111/transactions")
                .then()
                .statusCode(404);
    }

}
