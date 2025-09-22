package com.omar.fbank.customer.controller;

import com.omar.fbank.address.dto.AddressRequestDto;
import com.omar.fbank.customer.dto.CustomerRequestDto;
import com.omar.fbank.util.TestSetup;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UpdateCustomerTest extends TestSetup {

    AddressRequestDto addressRequestDto1 = new AddressRequestDto(
            "Avenida de Andalucía",
            "45",
            "3º",
            "B",
            "29010",
            "Málaga",
            "Málaga"
    );

    CustomerRequestDto customerRequestDto1 = new CustomerRequestDto(
            "Carlos",
            "García López",
            "12345678Z",
            addressRequestDto1,
            35,
            "612345679"
    );

    AddressRequestDto addressRequestDto2 = new AddressRequestDto(
            "Calle Gran Vía",
            "12",
            "2º",
            "A",
            "29005",
            "Málaga",
            "Málaga"
    );

    CustomerRequestDto customerRequestDto2 = new CustomerRequestDto(
            "Lucía",
            "Fernández Martínez",
            "87654321X",
            addressRequestDto2,
            30,
            "698765433"
    );

    CustomerRequestDto customerRequestDto3 = new CustomerRequestDto(
            "Lucía",
            "Fernández Martínez",
            "87654321Q",
            addressRequestDto2,
            30,
            "69876543"
    );

    @Test
    void adminShouldUpdateAnyCustomer() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .body(customerRequestDto1)
                .contentType(ContentType.JSON)
                .when()
                .put("/api/customers/a1b2c3d4-1111-4e2b-8f3a-111122223333")
                .then()
                .statusCode(204);
    }

    @Test
    void userShouldUpdateHisCustomer() {
        given()
                .header("Authorization", "Bearer " + getCustomer2Token())
                .body(customerRequestDto2)
                .contentType(ContentType.JSON)
                .when()
                .put("/api/customers/b2c3d4e5-2222-4f5b-9c6d-222233334444")
                .then()
                .statusCode(204);
    }

    @Test
    void userShouldNotUpdateAnotherUsersCustomer() {
        given()
                .header("Authorization", "Bearer " + getCustomer3Token())
                .body(customerRequestDto1)
                .contentType(ContentType.JSON)
                .when()
                .put("/api/customers/a1b2c3d4-1111-4e2b-8f3a-111122223333")
                .then()
                .statusCode(403);
    }

    @Test
    void shouldReturnNotFoundForNonExistentCustomer() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .body(customerRequestDto1)
                .contentType(ContentType.JSON)
                .when()
                .put("/api/customers/99999999-aaaa-bbbb-cccc-111111111111")
                .then()
                .statusCode(404);
    }

    @Test
    void shouldReturnBadRequestForInvalidData() {
        given()
                .header("Authorization", "Bearer " + getCustomer2Token())
                .body(customerRequestDto3)
                .contentType(ContentType.JSON)
                .when()
                .put("/api/customers/b2c3d4e5-2222-4f5b-9c6d-222233334444")
                .then()
                .statusCode(400);
    }

}
