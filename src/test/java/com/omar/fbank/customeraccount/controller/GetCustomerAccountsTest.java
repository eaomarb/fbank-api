package com.omar.fbank.customeraccount.controller;

import com.omar.fbank.util.TestSetup;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class GetCustomerAccountsTest extends TestSetup {

    @Test
    void adminShouldGetCustomerAccounts() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .get("/api/customersAccounts")
                .then()
                .statusCode(200);
    }

    @Test
    void userShouldGetHisCustomerAccountById() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .when()
                .get("/api/customersAccounts/e1f2a3b4-1111-4a2b-8d3a-111122223335")
                .then()
                .statusCode(200)
                .body("id", is("e1f2a3b4-1111-4a2b-8d3a-111122223335"));
    }

    @Test
    void userShouldNotGetAnotherUsersCustomerAccountById() {
        given()
                .header("Authorization", "Bearer " + getCustomer2Token())
                .when()
                .get("/api/customersAccounts/e1f2a3b4-1111-4a2b-8d3a-111122223335")
                .then()
                .statusCode(403);
    }
}
