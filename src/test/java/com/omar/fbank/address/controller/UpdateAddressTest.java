package com.omar.fbank.address.controller;

import com.omar.fbank.address.dto.AddressRequestDto;
import com.omar.fbank.util.TestSetup;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UpdateAddressTest extends TestSetup {

    AddressRequestDto addressRequestDto = new AddressRequestDto(
            "Calle Sevilla",
            "25",
            "2",
            "A",
            "29001",
            "Málaga",
            "Málaga"
    );

    AddressRequestDto invalidAddressRequestDto = new AddressRequestDto(
            "Calle Sevilla",
            "25",
            "2",
            "A",
            "291",
            "Málaga",
            "Málaga"
    );

    @Test
    void adminShouldUpdateAnyAddress() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .body(addressRequestDto)
                .contentType(ContentType.JSON)
                .when()
                .put("/api/addresses/7b9f3c2a-1111-4e2b-8d3a-1a2b3c4d5e6f")
                .then()
                .statusCode(204);
    }

    @Test
    void userShouldUpdateHisAddress() {
        given()
                .header("Authorization", "Bearer " + getCustomer1Token())
                .body(addressRequestDto)
                .contentType(ContentType.JSON)
                .when()
                .put("/api/addresses/7b9f3c2a-1111-4e2b-8d3a-1a2b3c4d5e6f")
                .then()
                .statusCode(204);
    }

    @Test
    void userShouldNotUpdateAnotherCustomerAddress() {
        given()
                .header("Authorization", "Bearer " + getCustomer2Token())
                .body(addressRequestDto)
                .contentType(ContentType.JSON)
                .when()
                .put("/api/addresses/7b9f3c2a-1111-4e2b-8d3a-1a2b3c4d5e6f")
                .then()
                .statusCode(403);
    }

    @Test
    void shouldReturnBadRequestForInvalidAddress() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .body(invalidAddressRequestDto)
                .contentType(ContentType.JSON)
                .when()
                .put("/api/addresses/7b9f3c2a-1111-4e2b-8d3a-1a2b3c4d5e6f")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldReturnNotFoundForNonExistentAddress() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .body(addressRequestDto)
                .contentType(ContentType.JSON)
                .when()
                .put("/api/addresses/99999999-aaaa-bbbb-cccc-111111111111")
                .then()
                .statusCode(404);
    }
}
