package com.omar.fbank.user.controller;

import com.omar.fbank.user.dto.UserRequestDto;
import com.omar.fbank.util.TestSetup;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UpdateUserTest extends TestSetup {

    @Test
    void adminShouldUpdateAnyUser() {
        UserRequestDto updateRequest = new UserRequestDto(
                "carlos.updated@mail.com",
                "Carlos Updated",
                "newPassword123"
        );

        given()
                .header("Authorization", "Bearer " + getAdminToken())
                .body(updateRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/users/1c9e3f6a-3b1a-4d5f-b7a5-123456789abc")
                .then()
                .statusCode(204);
    }

    @Test
    void userShouldUpdateOwnData() {
        UserRequestDto updateRequest = new UserRequestDto(
                "laura.updated@mail.com",
                "Laura Updated",
                "laura2"
        );

        given()
                .header("Authorization", "Bearer " + getCustomer3Token())
                .body(updateRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/users/aaaaaaaa-3333-4444-8888-cccccccc3333")
                .then()
                .statusCode(204);
    }

    @Test
    void userShouldNotUpdateOtherUser() {
        UserRequestDto updateRequest = new UserRequestDto(
                "carlos.updated@mail.com",
                "Carlos Updated",
                "newPassword123"
        );

        given()
                .header("Authorization", "Bearer " + getCustomer3Token())
                .body(updateRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/users/1c9e3f6a-3b1a-4d5f-b7a5-123456789abc")
                .then()
                .statusCode(403);
    }

}
