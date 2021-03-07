package com.minesweeper.data.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.minesweeper.business.api.HashManager;
import com.minesweeper.business.api.UserManager;
import com.minesweeper.data.api.UserDao;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

@Component
public class JwtUserManager
        implements UserManager {
    private static final String ISSUER = "minesweeper";
    private final UserDao userDao;
    private final HashManager hashManager;

    @Inject
    public JwtUserManager(@Nonnull UserDao userDao,
                          @Nonnull HashManager hashManager) {
        this.userDao = requireNonNull(userDao);
        this.hashManager = requireNonNull(hashManager);
    }

    @Nonnull
    @Override
    public CompletableFuture<String> login(@Nonnull String username,
                                           @Nonnull String password) {
        requireNonNull(username);
        requireNonNull(password);
        return userDao.read(username)
                .thenApply(user -> {
                    if (!user.getPassword().equals(hashManager.hash(password))) {
                        throw new IllegalArgumentException();
                    }
                    return JWT.create()
                            .withSubject(user.getUsername())
                            .withIssuer(ISSUER)
                            .sign(algorithm());
                });
    }

    @Nonnull
    @Override
    public String getUser(@Nonnull String token) {
        requireNonNull(token);

        JWTVerifier verifier = JWT.require(algorithm())
                .withIssuer(ISSUER)
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256("superSecretPassword!");
    }
}
