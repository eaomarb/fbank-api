package com.omar.fbank.transaction.controller;

import com.omar.fbank.transaction.dto.TransferRequestDto;
import com.omar.fbank.util.TestSetup;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class TransferTest extends TestSetup {
    @Test
    void adminShouldTransfer() {
        TransferRequestDto transferRequest = new TransferRequestDto(
                UUID.fromString("c1d2e3f4-1111-4a2b-8d3a-111122223333"),
                new BigDecimal("25.00"),
                "Lucía Fernández",
                "Transfer test",
                "ES2400817533191348763796"
        );

        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .body(transferRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/transactions/transfer")
                .then()
                .statusCode(200)
                .body("accountId", is("c1d2e3f4-1111-4a2b-8d3a-111122223333"))
                .body("amount", is(25f))
                .body("transactionType", is("TRANSFER"))
                .body("transactionStatus", is("COMPLETED"))
                .body("beneficiaryIban", is("ES2400817533191348763796"));
    }

    @Test
    void userShouldTransferFromOwnAccount() {
        TransferRequestDto transferRequest = new TransferRequestDto(
                UUID.fromString("c1d2e3f4-1111-4a2b-8d3a-111122223333"),
                new BigDecimal("25.00"),
                "Lucía Fernández",
                "Transfer test",
                "ES2400817533191348763796"
        );

        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .body(transferRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/transactions/transfer")
                .then()
                .statusCode(200)
                .body("accountId", is("c1d2e3f4-1111-4a2b-8d3a-111122223333"))
                .body("amount", is(25f))
                .body("transactionType", is("TRANSFER"))
                .body("transactionStatus", is("COMPLETED"))
                .body("beneficiaryIban", is("ES2400817533191348763796"));
    }

    @Test
    void userShouldNotTransferFromOthersAccount() {
        TransferRequestDto transferRequest = new TransferRequestDto(
                UUID.fromString("d1e2f3a4-2222-4c2d-9f3c-222233334444"),
                new BigDecimal("25.00"),
                "Carlos García",
                "Transfer forbidden",
                "ES8621006568558637351385"
        );

        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .body(transferRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/transactions/transfer")
                .then()
                .statusCode(403);
    }

    @Test
    void shouldFailTransferFromInactiveAccount() {
        TransferRequestDto transferRequest = new TransferRequestDto(
                UUID.fromString("8ed4b1c5-5710-4124-b2e6-85b5159025b5"),
                new BigDecimal("25.00"),
                "Lucía Fernández",
                "Transfer inactive",
                "ES2400817533191348763796"
        );

        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .body(transferRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/transactions/transfer")
                .then()
                .statusCode(400);
    }
}
