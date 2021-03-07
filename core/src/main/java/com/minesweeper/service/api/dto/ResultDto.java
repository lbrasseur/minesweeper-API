package com.minesweeper.service.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultDto<T> {
    public static <X> ResultDto<X> fromPayload(@Nullable X payload) {
        return new ResultDto<>(null, payload);
    }

    public static <X> ResultDto<X> fromError(@Nonnull String error) {
        requireNonNull(error);
        return new ResultDto<>(error, null);
    }

    public static <X> ResultDto<X> fromThrowable(@Nonnull Throwable exception) {
        requireNonNull(exception);
        exception.printStackTrace();
        return fromError(exception.getMessage());
    }

    @Nullable
    private final String error;
    @Nullable
    private final T payload;

    public ResultDto(@Nullable String error,
                     @Nullable T payload) {
        this.error = error;
        this.payload = payload;
    }

    @Nullable
    public String getError() {
        return error;
    }

    @Nullable
    public T getPayload() {
        return payload;
    }
}
