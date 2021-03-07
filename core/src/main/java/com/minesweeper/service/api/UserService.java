package com.minesweeper.service.api;

import com.minesweeper.common.api.dto.UserDto;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    @Nonnull
    CompletableFuture<String> create(@Nonnull UserDto dto);
}
