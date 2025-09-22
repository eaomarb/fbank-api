package com.omar.fbank.customer.controller;

import com.omar.fbank.util.TestSetup;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class CreateAccountTest extends TestSetup {
    @Test
    void adminShouldCreateAccount() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .post("/api/customers/a1b2c3d4-1111-4e2b-8f3a-111122223333/accounts")
                .then()
                .statusCode(201)
                .body("$", allOf(
                        hasKey("id"),
                        hasKey("balance"),
                        hasKey("iban"),
                        hasKey("status")
                ))
                .body("balance", is(0));
    }

    @Test
    void customerShouldCreateHisAccount() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .when()
                .post("/api/customers/a1b2c3d4-1111-4e2b-8f3a-111122223333/accounts")
                .then()
                .statusCode(201)
                .body("$", allOf(
                        hasKey("id"),
                        hasKey("balance"),
                        hasKey("iban"),
                        hasKey("status")
                ))
                .body("balance", is(0));
    }

    @Test
    void customerShouldNotCreateAccountForAnotherCustomer() {
        given()
                .header("Authorization", "Bearer " + getCustomer2Token())
                .when()
                .post("/api/customers/a1b2c3d4-1111-4e2b-8f3a-111122223333/accounts")
                .then()
                .statusCode(403);
    }
}
