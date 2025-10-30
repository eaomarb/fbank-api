package com.omar.fbank.customer.controller;

import com.omar.fbank.util.TestSetup;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GetCustomerByUserIdTest extends TestSetup {
    @Test
    void adminShouldGetCustomerByUserId() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .when()
                .get("/api/customers/user/1c9e3f6a-3b1a-4d5f-b7a5-123456789abc")
                .then()
                .statusCode(200)
                .body("id", equalTo("a1b2c3d4-1111-4e2b-8f3a-111122223333"));
    }

    @Test
    void shouldAllowCustomerToGetOwnCustomer() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .when()
                .get("/api/customers/user/1c9e3f6a-3b1a-4d5f-b7a5-123456789abc")
                .then()
                .statusCode(200)
                .body("id", equalTo("a1b2c3d4-1111-4e2b-8f3a-111122223333"));
    }

    @Test
    void shouldForbidCustomerToGetOtherCustomer() {
        given()
                .header("Authorization", "Bearer " + getCustomer2Token())
                .when()
                .get("/api/customers/user/1c9e3f6a-3b1a-4d5f-b7a5-123456789abc")
                .then()
                .statusCode(403);
    }
}
