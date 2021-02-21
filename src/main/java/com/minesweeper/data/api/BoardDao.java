package com.minesweeper.data.api;

import com.minesweeper.common.api.dto.BoardDto;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public interface BoardDao {
    @Nonnull
    CompletableFuture<Void> saveBoard(@Nonnull BoardDto board);

    @Nonnull
    CompletableFuture<BoardDto> readBoard(@Nonnull String boardId);
}
