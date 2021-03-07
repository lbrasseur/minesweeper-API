package com.minesweeper.data.impl;

import com.minesweeper.data.api.KeyValueStorage;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Component
@Singleton
public class InMemoryKeyValueStorage implements KeyValueStorage {
    private final Map<String, Map<String, Object>> storage;

    public InMemoryKeyValueStorage() {
        this.storage = new HashMap<>();
    }

    @Nonnull
    @Override
    public CompletableFuture<Void> save(@Nonnull String table,
                                        @Nonnull String key,
                                        @Nonnull Object entity) {
        requireNonNull(table);
        requireNonNull(key);
        requireNonNull(entity);

        return supplyAsync(() -> {
            table(table).put(key, entity);
            return null;
        });
    }

    @Nonnull
    @Override
    public <T> CompletableFuture<T> read(@Nonnull String table,
                                         @Nonnull String key,
                                         @Nonnull Class<T> entityClass) {
        requireNonNull(table);
        requireNonNull(key);
        requireNonNull(entityClass);

        return supplyAsync(() -> {
            T result = (T) table(table).get(key);
            checkArgument(result != null, table + " with key " + key + " not found");
            return result;
        });
    }

    @Nonnull
    @Override
    public <T> CompletableFuture<Set<T>> readSet(@Nonnull String table,
                                                 @Nonnull String key,
                                                 @Nonnull Class<T> entityClass) {
        requireNonNull(table);
        requireNonNull(key);
        requireNonNull(entityClass);

        return supplyAsync(() -> {
            Set<T> result = (Set<T>) table(table).get(key);
            checkArgument(result != null, table + " with key " + key + " not found");
            return result;
        });
    }

    @Nonnull
    @Override
    public CompletableFuture<Void> delete(@Nonnull String table,
                                          @Nonnull String key) {
        requireNonNull(table);
        requireNonNull(key);

        return supplyAsync(() -> {
            table(table).remove(key);
            return null;
        });
    }

    private Map<String, Object> table(String table) {
        return storage.computeIfAbsent(table, ignored -> new HashMap<>());
    }
}
