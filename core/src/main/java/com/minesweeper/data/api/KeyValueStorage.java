package com.minesweeper.data.api;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface KeyValueStorage {
    @Nonnull
    CompletableFuture<Void> save(@Nonnull String table,
                                 @Nonnull String key,
                                 @Nonnull Object entity);

    @Nonnull
    <T> CompletableFuture<T> read(@Nonnull String table,
                                  @Nonnull String key,
                                  @Nonnull Class<T> entityClass);

    @Nonnull
    <T> CompletableFuture<Set<T>> readSet(@Nonnull String table,
                                          @Nonnull String key,
                                          @Nonnull Class<T> entityClass);

    @Nonnull
    CompletableFuture<Void> delete(@Nonnull String table,
                                   @Nonnull String key);
}
