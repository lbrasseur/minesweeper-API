package com.minesweeper.business.api;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public interface UserManager {
    /**
     * @return The authentication token
     */
    @Nonnull
    CompletableFuture<String> login(@Nonnull String username,
                                    @Nonnull String password);

    /**
     * @throws IllegalArgumentException if the token is not valid
     */
    @Nonnull
    String getUser(@Nonnull String token) throws IllegalArgumentException;
}
