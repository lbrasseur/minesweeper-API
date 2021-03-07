package com.minesweeper.data.api;

import com.minesweeper.common.api.dto.UserDto;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public interface UserDao {
    @Nonnull
    CompletableFuture<UserDto> read(@Nonnull String username);
}
