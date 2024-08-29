package com.sohosquared.domain.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.io.Serializable;

public sealed interface UsersRestV1Requests extends Serializable {
    String firstName();

    String lastName();

    String email();

    @Schema(name = "UsersV1Create", description = "User create request")
    record Create(
            @NotBlank(message = "firstName cannot be blank")
            @Size(max = 64, message = "firstName cannot be longer than 64 characters")
            @NotNull(message = "firstName is required")
            String firstName,
            @NotBlank(message = "lastName cannot be blank")
            @Size(max = 64, message = "lastName cannot be longer than 64 characters")
            @NotNull(message = "lastName is required")
            String lastName,
            @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
            @NotBlank(message = "email cannot be blank")
            @Size(max = 255, message = "email cannot be longer than 255 characters")
            @NotNull(message = "email is required")
            String email
    ) implements UsersRestV1Requests {
    }

    @Schema(name = "UsersV1Update", description = "User update request")
    record Update(
            @NotBlank(message = "firstName cannot be blank")
            @Size(max = 64, message = "firstName cannot be longer than 64 characters")
            @NotNull(message = "firstName is required")
            String firstName,
            @NotBlank(message = "lastName cannot be blank")
            @Size(max = 64, message = "lastName cannot be longer than 64 characters")
            @NotNull(message = "lastName is required")
            String lastName,
            @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
            @NotBlank(message = "email cannot be blank")
            @Size(max = 255, message = "email cannot be longer than 255 characters")
            @NotNull(message = "email is required")
            String email
    ) implements UsersRestV1Requests {
    }
}