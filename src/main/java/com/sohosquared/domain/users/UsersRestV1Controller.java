package com.sohosquared.domain.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Tag(name = "v1/users")
@RequestMapping("/api/v1/users")
public interface UsersRestV1Controller {
    @Operation(
            summary = "Create a user",
            description = "Creates a user with provided parameters",
            operationId = "createUser",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User successfully created",
                            headers = {
                                    @Header(name = "location",
                                            description = "URI of newly created user",
                                            schema = @Schema(
                                                    implementation = URI.class
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "User creation failed due to bad request"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "User creation failed due to internal server error"
                    ),
            }
    )
    @PostMapping("")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    Mono<ResponseEntity<Void>> create(
            final ServerHttpRequest serverHttpRequest,
            @Valid @RequestBody final UsersRestV1Requests.Create request
    );

    @Operation(
            summary = "Retrieve a user",
            description = "Retrieves a user with given id",
            operationId = "getUser",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User successfully retrieved",
                            content = @Content(
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User does not exist"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "User retrieval failed due to internal server error"
                    )
            }
    )
    @GetMapping("/{userId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    Mono<ResponseEntity<User>> get(
            final ServerHttpRequest serverHttpRequest,
            @PathVariable final UUID userId
    );

    @Operation(
            summary = "Update a user",
            description = "Updates a user with given id",
            operationId = "updateUser",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "User successfully updated"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "User update failed due to bad request"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User does not exist"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "User update failed due to internal server error"
                    )
            }
    )
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<ResponseEntity<Void>> put(
            final ServerHttpRequest serverHttpRequest,
            @PathVariable final UUID userId,
            @Valid @RequestBody final UsersRestV1Requests.Update request
    );

    @Operation(
            summary = "Delete a user",
            description = "Deletes a user with given id",
            operationId = "deleteUser",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "User successfully deleted"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "User deletion failed due to bad request",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User does not exist",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "User deletion failed due to internal server error"
                    )
            }
    )
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<ResponseEntity<Void>> delete(
            final ServerHttpRequest serverHttpRequest,
            @PathVariable final UUID userId
    );
}