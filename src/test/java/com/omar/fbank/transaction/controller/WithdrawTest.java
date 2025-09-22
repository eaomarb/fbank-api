package com.omar.fbank.transaction.controller;

import com.omar.fbank.transaction.dto.WithdrawRequestDto;
import com.omar.fbank.util.TestSetup;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class WithdrawTest extends TestSetup {
    @Test
    void adminShouldWithdraw() {
        WithdrawRequestDto withdrawRequest = new WithdrawRequestDto(
                UUID.fromString("c1d2e3f4-1111-4a2b-8d3a-111122223333"),
                new BigDecimal("50.00"),
                "Withdraw test"
        );

        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .body(withdrawRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/transactions/withdraw")
                .then()
                .statusCode(200)
                .body("accountId", is("c1d2e3f4-1111-4a2b-8d3a-111122223333"))
                .body("amount", is(50f))
                .body("transactionType", is("WITHDRAW"))
                .body("transactionStatus", is("COMPLETED"));
    }

    @Test
    void userShouldWithdrawFromOwnAccount() {
        WithdrawRequestDto withdrawRequest = new WithdrawRequestDto(
                UUID.fromString("c1d2e3f4-1111-4a2b-8d3a-111122223333"),
                new BigDecimal("50.00"),
                "Withdraw test"
        );

        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .body(withdrawRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/transactions/withdraw")
                .then()
                .statusCode(200)
                .body("accountId", is("c1d2e3f4-1111-4a2b-8d3a-111122223333"))
                .body("amount", is(50f))
                .body("transactionType", is("WITHDRAW"))
                .body("transactionStatus", is("COMPLETED"));
    }

    @Test
    void userShouldNotWithdrawFromOthersAccount() {
        WithdrawRequestDto withdrawRequest = new WithdrawRequestDto(
                UUID.fromString("d1e2f3a4-2222-4c2d-9f3c-222233334444"),
                new BigDecimal("30.00"),
                "Withdraw forbidden"
        );

        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .body(withdrawRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/transactions/withdraw")
                .then()
                .statusCode(403);
    }

    @Test
    void shouldFailWithdrawFromInactiveAccount() {
        WithdrawRequestDto withdrawRequest = new WithdrawRequestDto(
                UUID.fromString("8ed4b1c5-5710-4124-b2e6-85b5159025b5"),
                new BigDecimal("20.00"),
                "Withdraw inactive"
        );

        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .body(withdrawRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/transactions/withdraw")
                .then()
                .statusCode(400);
    }
}
