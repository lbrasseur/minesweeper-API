package com.minesweeper.spring.service;

import com.minesweeper.service.api.dto.ResultDto;

import java.util.concurrent.CompletableFuture;

public final class ServiceUtils {
    private ServiceUtils() {
    }

    static <T> CompletableFuture<ResultDto<T>> toResult(CompletableFuture<T> future) {
        return future
                .thenApply(ResultDto::fromPayload)
                .exceptionally(ResultDto::fromThrowable);
    }
}
