package com.minesweeper.data.impl;

import com.minesweeper.business.api.HashManager;
import com.minesweeper.common.api.dto.UserDto;
import com.minesweeper.data.api.UserDao;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Component
public class KeyValueUserDao
        implements UserDao {
    private static final String TABLE = "User";
    private final Map<String, UserDto> storage;

    @Inject
    public KeyValueUserDao(@Nonnull HashManager hashManager) {
        requireNonNull(hashManager);
        storage = new HashMap<>();
        createTestUser("admin", "hello", hashManager);
        createTestUser("cacho", "deicas", hashManager);
    }

    @Nonnull
    @Override
    public CompletableFuture<UserDto> read(@Nonnull String username) {
        requireNonNull(username);
        return supplyAsync(() -> {
            UserDto userDto = storage.get(username);
            checkArgument(userDto != null, "User " + username + " not found");

            return userDto;
        });
    }

    private void createTestUser(String username,
                                String password,
                                HashManager hashManager) {

        storage.put(username, new UserDto(username,
                hashManager.hash(password)));
    }
}
