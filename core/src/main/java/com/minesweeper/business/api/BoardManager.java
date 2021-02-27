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
    CompletableFuture<Board> pauseBoard(@Nonnull String boardId);

    @Nonnull
    CompletableFuture<Board> resumeBoard(@Nonnull String boardId);

    @Nonnull
    CompletableFuture<Board> click(@Nonnull String boardId,
                                   int column,
                                   int row);

    @Nonnull
    CompletableFuture<Board> redFlag(@Nonnull String boardId,
                                     int column,
                                     int row);

    @Nonnull
    CompletableFuture<Board> questionMark(@Nonnull String boardId,
                                          int column,
                                          int row);

    @Nonnull
    CompletableFuture<Board> initial(@Nonnull String boardId,
                                     int column,
                                     int row);
}
