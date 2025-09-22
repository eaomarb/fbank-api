package com.omar.fbank.customeraccount.controller;

import com.omar.fbank.util.TestSetup;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DeleteCustomerAccountTest extends TestSetup {

    @Test
    void userShouldDeleteOwnCustomerAccount() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .when()
                .delete("/api/customersAccounts/649e5b14-0add-482b-951a-e8be5652548f")
                .then()
                .statusCode(204);
    }

    @Test
    void adminShouldDeleteCustomerAccount() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .delete("/api/customersAccounts/649e5b14-0add-482b-951a-e8be5652548f")
                .then()
                .statusCode(204);
    }

    @Test
    void userShouldNotDeleteAccountWithBalance() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .when()
                .delete("/api/customersAccounts/e1f2a3b4-1111-4a2b-8d3a-111122223335")
                .then()
                .statusCode(409);
    }
}