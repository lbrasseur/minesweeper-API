package com.minesweeper.business.api;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BoardManager {
    @Nonnull
    CompletableFuture<Board> create(@Nonnull String owner,
                                    int width,
                                    int height,
                                    int mines);

    @Nonnull
    CompletableFuture<Board> pause(@Nonnull String owner,
                                   @Nonnull String boardId);

    @Nonnull
    CompletableFuture<Board> resume(@Nonnull String owner,
                                    @Nonnull String boardId);

    @Nonnull
    CompletableFuture<List<BoardData>> find(@Nonnull String owner);

    @Nonnull
    CompletableFuture<Void> delete(@Nonnull String owner,
                                   @Nonnull String boardId);

    @Nonnull
    CompletableFuture<Board> click(@Nonnull String owner,
                                   @Nonnull String boardId,
                                   int column,
                                   int row);

    @Nonnull
    CompletableFuture<Board> redFlag(@Nonnull String owner,
                                     @Nonnull String boardId,
                                     int column,
                                     int row);

    @Nonnull
    CompletableFuture<Board> questionMark(@Nonnull String owner,
                                          @Nonnull String boardId,
                                          int column,
                                          int row);

    @Nonnull
    CompletableFuture<Board> initial(@Nonnull String owner,
                                     @Nonnull String boardId,
                                     int column,
                                     int row);
}
