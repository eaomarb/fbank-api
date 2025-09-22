package com.omar.fbank.transaction.controller;

import com.omar.fbank.util.TestSetup;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


public class GetTransactionByIdTest extends TestSetup {
    @Test
    void adminShouldGetTransactionById() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .get("/api/transactions/20354d7a-e4fe-47af-8ff6-187bca92f3f9")
                .then()
                .statusCode(200)
                .body("id", is("20354d7a-e4fe-47af-8ff6-187bca92f3f9"))
                .body("amount", is(50f));
    }

    @Test
    void userShouldGetOwnTransaction() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .when()
                .get("/api/transactions/20354d7a-e4fe-47af-8ff6-187bca92f3f9")
                .then()
                .statusCode(200)
                .body("id", is("20354d7a-e4fe-47af-8ff6-187bca92f3f9"))
                .body("amount", is(50f));
    }

    @Test
    void userShouldNotGetOthersTransaction() {
        given()
                .header("Authorization", "Bearer " + getCustomer2Token())
                .when()
                .get("/api/transactions/20354d7a-e4fe-47af-8ff6-187bca92f3f9")
                .then()
                .statusCode(403);
    }
}
