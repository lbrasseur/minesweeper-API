package com.minesweeper.data.impl;

import com.google.common.collect.Lists;
import com.minesweeper.common.api.dto.BoardDto;
import com.minesweeper.data.api.BoardDao;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.CompletableFuture.completedFuture;

@Component
public class InMemoryBoardDao
        implements BoardDao {
    private final Map<String, BoardDto> storage;

    public InMemoryBoardDao() {
        storage = new HashMap<>();
    }

    @Nonnull
    @Override
    public CompletableFuture<Void> save(@Nonnull BoardDto board) {
        requireNonNull(board);

        storage.put(board.getId(), board);
        return completedFuture(null);
    }

    @Nonnull
    @Override
    public CompletableFuture<BoardDto> read(@Nonnull String boardId) {
        requireNonNull(boardId);

        BoardDto board = storage.get(boardId);
        checkArgument(board != null, "Board with id " + boardId + " not found");

        return completedFuture(board);
    }

    @Nonnull
    @Override
    public CompletableFuture<List<BoardDto>> find() {
        return completedFuture(Lists.newArrayList(storage.values()));
    }

    @Nonnull
    @Override
    public CompletableFuture<Void> delete(@Nonnull String boardId) {
        requireNonNull(boardId);

        storage.remove(boardId);
        return completedFuture(null);
    }
}
