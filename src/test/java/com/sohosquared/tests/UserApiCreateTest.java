package com.sohosquared.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserApiCreateTest {

    @BeforeAll
    public static void setup() {
        // Set the base URI for RestAssured
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    public void testCreateUserWithValidData() {
        String requestBody = "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/users");

        System.out.println("Create User with Valid Data Response: " + response.asString());
        response.then()
                .statusCode(201);  // Expected 201 Created for valid input
    }

    @Test
    public void testCreateUserWithMissingFirstName() {
        String requestBody = "{ \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/users");

        System.out.println("Create User with Missing First Name Response: " + response.asString());
        response.then()
                .statusCode(400) // Expected 400 Bad Request for missing firstName
                .body("error", equalTo("First name is required"));
    }

    @Test
    public void testCreateUserWithFirstNameTooLong() {
        String requestBody = "{ \"firstName\": \"" + "A".repeat(33) + "\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/users");

        System.out.println("Create User with First Name Too Long Response: " + response.asString());
        response.then()
                .statusCode(400) // Expected 400 Bad Request for firstName exceeding 32 characters
                .body("error", equalTo("First name cannot exceed 32 characters"));
    }

    @Test
    public void testCreateUserWithMissingLastName() {
        String requestBody = "{ \"firstName\": \"John\", \"email\": \"john.doe@example.com\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/users");

        System.out.println("Create User with Missing Last Name Response: " + response.asString());
        response.then()
                .statusCode(400) // Expected 400 Bad Request for missing lastName
                .body("error", equalTo("Last name is required"));
    }

    @Test
    public void testCreateUserWithLastNameTooLong() {
        String requestBody = "{ \"firstName\": \"John\", \"lastName\": \"" + "B".repeat(65) + "\", \"email\": \"john.doe@example.com\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/users");

        System.out.println("Create User with Last Name Too Long Response: " + response.asString());
        response.then()
                .statusCode(400) // Expected 400 Bad Request for lastName exceeding 64 characters
                .body("error", equalTo("Last name cannot exceed 64 characters"));
    }

    @Test
    public void testCreateUserWithMissingEmail() {
        String requestBody = "{ \"firstName\": \"John\", \"lastName\": \"Doe\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/users");

        System.out.println("Create User with Missing Email Response: " + response.asString());
        response.then()
                .statusCode(400) // Expected 400 Bad Request for missing email
                .body("error", equalTo("Email is required"));
    }

    @Test
    public void testCreateUserWithEmailTooLong() {
        String email = "a".repeat(256) + "@example.com";
        String requestBody = "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"" + email + "\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/users");

        System.out.println("Create User with Email Too Long Response: " + response.asString());
        response.then()
                .statusCode(400) // Expected 400 Bad Request for email exceeding 255 characters
                .body("error", equalTo("Email cannot exceed 255 characters"));
    }

    @Test
    public void testCreateUserWithInvalidEmailFormat() {
        String requestBody = "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"invalid-email-format\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/users");

        System.out.println("Create User with Invalid Email Format Response: " + response.asString());
        response.then()
                .statusCode(400) // Expected 400 Bad Request for invalid email format
                .body("error", equalTo("Invalid email format"));
    }
}
