package com.minesweeper.business.api;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public interface BoardManager {
    @Nonnull
    CompletableFuture<Board> createBoard(@Nonnull String owner,
                                         int width,
                                         int height,
                                         int mines);

    @Nonnull
    CompletableFuture<Board> getBoard(@Nonnull String boardId);

    @Nonnull
    CompletableFuture<Board> click(@Nonnull String boardId, int column, int row);
}
