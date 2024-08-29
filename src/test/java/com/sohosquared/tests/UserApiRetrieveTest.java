package com.sohosquared.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserApiRetrieveTest {

    @BeforeAll
    public static void setup() {
        // Set the base URI for RestAssured
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void testRetrieveUserWithValidId() {
        // First, create a user to retrieve
        String requestBody = "{ \"firstName\": \"Alice\", \"lastName\": \"Johnson\", \"email\": \"alice.johnson@example.com\" }";
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

        // Retrieve the created user with a valid ID
        Response retrieveValidUserResponse = given()
                .when()
                .get("/api/v1/users/" + userId);

        System.out.println("Retrieve Valid User Response: " + retrieveValidUserResponse.asString());
        retrieveValidUserResponse.then()
                .statusCode(200)
                .body("firstName", equalTo("Alice"))
                .body("lastName", equalTo("Johnson"))
                .body("email", equalTo("alice.johnson@example.com"));
    }

    @Test
    public void testRetrieveUserWithInvalidIdFormat() {
        // Attempt to retrieve a user with an invalid ID format
        Response retrieveInvalidUserResponse = given()
                .when()
                .get("/api/v1/users/invalid-id");

        System.out.println("Retrieve User with Invalid ID Format Response: " + retrieveInvalidUserResponse.asString());
        retrieveInvalidUserResponse.then()
                .statusCode(400) // Expected 400 Bad Request for invalid ID format
                .body("error", equalTo("Invalid user ID format"));
    }

    @Test
    public void testRetrieveUserWithNonExistingId() {
        // Attempt to retrieve a user with a valid format but non-existing ID
        String nonExistingUserId = "123e4567-e89b-12d3-a456-426614174000"; // UUID format but non-existing

        Response retrieveNonExistingUserResponse = given()
                .when()
                .get("/api/v1/users/" + nonExistingUserId);

        System.out.println("Retrieve User with Non-Existing ID Response: " + retrieveNonExistingUserResponse.asString());
        retrieveNonExistingUserResponse.then()
                .statusCode(404) // Expected 404 Not Found for non-existing user ID
                .body("error", equalTo("User not found"));
    }
}
