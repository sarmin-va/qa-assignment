package com.sohosquared.domain.users;

import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class UsersRestV1ControllerImpl implements UsersRestV1Controller {
    final Map<UUID, User> users = new HashMap<>();

    public Mono<ResponseEntity<Void>> create(
            final ServerHttpRequest serverHttpRequest,
            final UsersRestV1Requests.Create request
    ) {
        final UUID userId = UUID.randomUUID();

        users.put(userId, new User(
                userId,
                request.firstName(),
                request.lastName(),
                request.email()
        ));

        final URI uri = UriComponentsBuilder.fromUri(serverHttpRequest.getURI()).path("/{id}").buildAndExpand(userId).toUri();

        return Mono.just(ResponseEntity.created(uri).build());
    }

    public Mono<ResponseEntity<User>> get(
            final ServerHttpRequest serverHttpRequest,
            final UUID userId
    ) {
        return Mono.just(ResponseEntity.ok(users.get(userId)));
    }

    public Mono<ResponseEntity<Void>> put(
            final ServerHttpRequest serverHttpRequest,
            final UUID userId,
            final UsersRestV1Requests.Update request
    ) {
        users.put(userId, new User(
                userId,
                request.firstName(),
                request.lastName(),
                request.email()
        ));

        return Mono.just(ResponseEntity.noContent().build());
    }

    public Mono<ResponseEntity<Void>> delete(
            final ServerHttpRequest serverHttpRequest,
            final UUID userId
    ) {
        users.remove(userId);

        return Mono.just(ResponseEntity.noContent().build());
    }
}