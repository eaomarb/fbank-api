package com.omar.fbank.transaction.controller;

import com.omar.fbank.util.TestSetup;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetTransactionsTest extends TestSetup {

    @Test
    void adminShouldGetAllTransactions() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .get("/api/transactions")
                .then()
                .statusCode(200);
    }

    @Test
    void customerShouldNotGetAllTransactions() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .when()
                .get("/api/transactions")
                .then()
                .statusCode(403);
    }
}
