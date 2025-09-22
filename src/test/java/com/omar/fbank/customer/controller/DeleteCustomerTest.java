package com.omar.fbank.customer.controller;

import com.omar.fbank.util.TestSetup;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DeleteCustomerTest extends TestSetup {
    @Test
    void adminShouldDeleteAnyCustomer() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .delete("/api/customers/a1b2c3d4-1111-4e2b-8f3a-111122223333")
                .then()
                .statusCode(204);
    }

    @Test
    void userShouldDeleteHisCustomer() {
        given()
                .header("Authorization", "Bearer " + getCustomer2Token())
                .when()
                .delete("/api/customers/b2c3d4e5-2222-4f5b-9c6d-222233334444")
                .then()
                .statusCode(204);
    }

    @Test
    void userShouldNotDeleteAnotherUsersCustomer() {
        given()
                .header("Authorization", "Bearer " + getCustomer3Token())
                .when()
                .delete("/api/customers/a1b2c3d4-1111-4e2b-8f3a-111122223333")
                .then()
                .statusCode(403);
    }

    @Test
    void shouldReturnNotFoundForNonExistentCustomer() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .delete("/api/customers/99999999-aaaa-bbbb-cccc-111111111111")
                .then()
                .statusCode(404);
    }

}
