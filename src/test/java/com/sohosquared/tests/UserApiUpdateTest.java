package com.sohosquared.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserApiUpdateTest {

    @BeforeAll
    public static void setup() {
        // Set the base URI for RestAssured
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void testUpdateUserWithValidData() {
        // First, create a user to update
        String requestBody = "{ \"firstName\": \"Charlie\", \"lastName\": \"Brown\", \"email\": \"charlie.brown@example.com\" }";
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

        // Update the created user's first name and last name (valid update)
        String updateRequestBody = "{ \"firstName\": \"Chuck\", \"lastName\": \"Blond\" }";
        Response updateResponse = given()
                .contentType(ContentType.JSON)
                .body(updateRequestBody)
                .when()
                .put("/api/v1/users/" + userId);

        System.out.println("Update User Response: " + updateResponse.asString());
        updateResponse.then().statusCode(204); // Expected 204 No Content on successful update
    }

    @Test
    public void testUpdateUserWithInvalidUserId() {
        // Attempt to update a user with an invalid userId
        String updateRequestBody = "{ \"firstName\": \"Chuck\", \"lastName\": \"Blond\" }";
        Response updateInvalidUserIdResponse = given()
                .contentType(ContentType.JSON)
                .body(updateRequestBody)
                .when()
                .put("/api/v1/users/invalid-id");

        System.out.println("Update User with Invalid ID Response: " + updateInvalidUserIdResponse.asString());
        updateInvalidUserIdResponse.then()
                .statusCode(400) // Expected 400 Bad Request for invalid userId format
                .body("error", equalTo("Invalid user ID format"));
    }

    @Test
    public void testUpdateUserWithNonExistingUserId() {
        // Attempt to update a user with a valid but non-existing userId
        String nonExistingUserId = "123e4567-e89b-12d3-a456-426614174000"; // UUID format but non-existing
        String updateRequestBody = "{ \"firstName\": \"Chuck\", \"lastName\": \"Blond\" }";

        Response updateNonExistingUserResponse = given()
                .contentType(ContentType.JSON)
                .body(updateRequestBody)
                .when()
                .put("/api/v1/users/" + nonExistingUserId);

        System.out.println("Update User with Non-Existing ID Response: " + updateNonExistingUserResponse.asString());
        updateNonExistingUserResponse.then()
                .statusCode(404) // Expected 404 Not Found for non-existing userId
                .body("error", equalTo("User not found"));
    }

    @Test
    public void testUpdateUserEmailNotAllowed() {
        // First, create a user to test email update restriction
        String requestBody = "{ \"firstName\": \"David\", \"lastName\": \"Smith\", \"email\": \"david.smith@example.com\" }";
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

        // Attempt to update the user's email (which should not be allowed)
        String invalidUpdateRequestBody = "{ \"firstName\": \"David\", \"lastName\": \"Smith\", \"email\": \"new.email@example.com\" }";
        Response updateInvalidEmailResponse = given()
                .contentType(ContentType.JSON)
                .body(invalidUpdateRequestBody)
                .when()
                .put("/api/v1/users/" + userId);

        System.out.println("Update User with Invalid Email Response: " + updateInvalidEmailResponse.asString());
        updateInvalidEmailResponse.then()
                .statusCode(400) // Expected 400 Bad Request for attempting to update email
                .body("error", equalTo("Email field cannot be updated"));
    }
}
