package com.omar.fbank.customer.controller;

import com.omar.fbank.address.dto.AddressRequestDto;
import com.omar.fbank.customer.dto.CustomerRequestDto;
import com.omar.fbank.util.TestSetup;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateCustomerTest extends TestSetup {
    AddressRequestDto addressRequestDto = new AddressRequestDto(
            "Calle Mayor",
            "10",
            "1º",
            "C",
            "29001",
            "Málaga",
            "Málaga"
    );

    CustomerRequestDto customerRequestDto = new CustomerRequestDto(
            "Laura",
            "Jiménez Moreno",
            "45133992B",
            addressRequestDto,
            31,
            "678123456"
    );

    AddressRequestDto addressRequestDto2 = new AddressRequestDto(
            "Calle Mayor",
            "11",
            "1º",
            "C",
            "29001",
            "Málaga",
            "Málaga"
    );

    CustomerRequestDto customerRequestDto2 = new CustomerRequestDto(
            "Laura",
            "Jiménez Moreno",
            "16681983Z",
            addressRequestDto2,
            31,
            "678123457"
    );

    @Test
    void adminShouldCreateCustomer() {
        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .body(customerRequestDto)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/customers/aaaaaaaa-3333-4444-8888-cccccccc3333") // User 3 id (Laura), not admin
                .then()
                .statusCode(201)
                .body("$", allOf(
                        hasKey("id"),
                        hasKey("name"),
                        hasKey("lastName"),
                        hasKey("documentId"),
                        hasKey("address"),
                        hasKey("age"),
                        hasKey("phone"),
                        hasKey("user")
                ))
                .body("documentId", is("45133992B"))
                .body("address.streetName", is("Calle Mayor"))
                .body("user.id", is("aaaaaaaa-3333-4444-8888-cccccccc3333"));
    }

    @Test
    void userShouldCreateHisCustomer() {
        given()
                .header("Authorization", "Bearer " + getCustomer3Token())
                .body(customerRequestDto)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/customers/aaaaaaaa-3333-4444-8888-cccccccc3333") // User 3 id (Laura)
                .then()
                .statusCode(201)
                .body("$", allOf(
                        hasKey("id"),
                        hasKey("name"),
                        hasKey("lastName"),
                        hasKey("documentId"),
                        hasKey("address"),
                        hasKey("age"),
                        hasKey("phone"),
                        hasKey("user")
                ))
                .body("documentId", is("45133992B"))
                .body("address.streetName", is("Calle Mayor"))
                .body("user.id", is("aaaaaaaa-3333-4444-8888-cccccccc3333"));
    }

    @Test
    void userShouldNotCreateCustomerForAnotherUser() {
        given()
                .header("Authorization", "Bearer " + getCustomer2Token())
                .body(customerRequestDto)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/customers/aaaaaaaa-3333-4444-8888-cccccccc3333") // User 3 id (Laura)
                .then()
                .statusCode(403);
    }

    @Test
    void userShouldNotCreateTwoCustomers() {
        given()
                .header("Authorization", "Bearer " + getCustomer3Token())
                .body(customerRequestDto)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/customers/aaaaaaaa-3333-4444-8888-cccccccc3333") // User 3 id (Laura)
                .then()
                .statusCode(201)
                .body("$", allOf(
                        hasKey("id"),
                        hasKey("name"),
                        hasKey("lastName"),
                        hasKey("documentId"),
                        hasKey("address"),
                        hasKey("age"),
                        hasKey("phone"),
                        hasKey("user")
                ))
                .body("documentId", is("45133992B"))
                .body("address.streetName", is("Calle Mayor"))
                .body("user.id", is("aaaaaaaa-3333-4444-8888-cccccccc3333"));

        given()
                .header("Authorization", "Bearer " + getCustomer3Token())
                .body(customerRequestDto2)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/customers/aaaaaaaa-3333-4444-8888-cccccccc3333") // User 3 id (Laura)
                .then()
                .statusCode(400);
    }
}