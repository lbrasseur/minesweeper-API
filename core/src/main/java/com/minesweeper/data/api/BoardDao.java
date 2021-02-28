package com.minesweeper.data.api;

import com.minesweeper.common.api.dto.BoardDto;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BoardDao {
    @Nonnull
    CompletableFuture<Void> save(@Nonnull BoardDto board);

    @Nonnull
    CompletableFuture<BoardDto> read(@Nonnull String boardId);

    @Nonnull
    CompletableFuture<List<BoardDto>> find();

    @Nonnull
    CompletableFuture<Void> delete(@Nonnull String boardId);
}
