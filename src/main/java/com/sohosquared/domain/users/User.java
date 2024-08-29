package com.sohosquared.domain.users;

import java.util.UUID;

public record User(
        UUID id,
        String firstName,
        String lastName,
        String email
) {
}
