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
import static java.util.concurrent.CompletableFuture.completedFuture;

@Component
public class InMemoryUserDao
        implements UserDao {
    private final Map<String, UserDto> storage;

    @Inject
    public InMemoryUserDao(@Nonnull HashManager hashManager) {
        requireNonNull(hashManager);
        storage = new HashMap<>();
        createTestUser("a", "s", hashManager);
    }

    @Nonnull
    @Override
    public CompletableFuture<UserDto> read(@Nonnull String username) {
        requireNonNull(username);

        UserDto userDto = storage.get(username);
        checkArgument(userDto != null, "User " + username + " not found");

        return completedFuture(userDto);
    }

    private void createTestUser(String username,
                                String password,
                                HashManager hashManager) {

        storage.put(username, new UserDto(username,
                hashManager.hash(password)));
    }
}
