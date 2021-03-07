package com.minesweeper.common.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class UserDto {
    @Nonnull
    private final String username;
    @Nonnull
    private final String password;

    @JsonCreator
    public UserDto(@JsonProperty("username") @Nonnull String username,
                   @JsonProperty("password") @Nonnull String password) {
        this.username = requireNonNull(username);
        this.password = requireNonNull(password);
    }

    @Nonnull
    public String getUsername() {
        return username;
    }

    @Nonnull
    public String getPassword() {
        return password;
    }
}
