package com.sohosquared.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserApiDeleteTest {

    @BeforeAll
    public static void setup() {
        // Set the base URI for RestAssured
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void testDeleteUserWithValidId() {
        // First, create a user to delete
        String requestBody = "{ \"firstName\": \"Eve\", \"lastName\": \"Stone\", \"email\": \"eve.stone@example.com\" }";
        Response createResponse = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/users");

        System.out.println("Create User Response: " + createResponse.asString());
        createResponse.then().statusCode(201);

        // Correctly extract userId from 'location' header
        String locationHeader = createResponse.header("location");
        String userId = locationHeader.substring(locationHeader.lastIndexOf("/") + 1);
        System.out.println("Extracted User ID: " + userId);

        // Delete the created user with a valid ID
        Response deleteResponse = given()
                .when()
                .delete("/api/v1/users/" + userId);

        System.out.println("Delete User Response: " + deleteResponse.asString());
        deleteResponse.then().statusCode(204); // Expected 204 No Content on successful deletion
    }

    @Test
    public void testDeleteUserWithInvalidId() {
        // Attempt to delete a user with an invalid ID format
        Response deleteInvalidUserResponse = given()
                .when()
                .delete("/api/v1/users/invalid-id");

        System.out.println("Delete User with Invalid ID Response: " + deleteInvalidUserResponse.asString());
        deleteInvalidUserResponse.then()
                .statusCode(400) // Expected 400 Bad Request for invalid ID format
                .body("error", equalTo("Invalid user ID format"));
    }

    @Test
    public void testDeleteUserWithNonExistingId() {
        // Attempt to delete a user with a valid format but non-existing ID
        String nonExistingUserId = "123e4567-e89b-12d3-a456-426614174000"; // UUID format but non-existing

        Response deleteNonExistingUserResponse = given()
                .when()
                .delete("/api/v1/users/" + nonExistingUserId);

        System.out.println("Delete User with Non-Existing ID Response: " + deleteNonExistingUserResponse.asString());
        deleteNonExistingUserResponse.then()
                .statusCode(404) // Expected 404 Not Found for non-existing user ID
                .body("error", equalTo("User not found"));
    }
}
