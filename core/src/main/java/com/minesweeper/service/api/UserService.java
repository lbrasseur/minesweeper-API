package com.minesweeper.service.api;

import com.minesweeper.common.api.dto.UserDto;
import com.minesweeper.service.api.dto.ResultDto;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    @Nonnull
    CompletableFuture<ResultDto<String>> create(@Nonnull UserDto dto);
}
