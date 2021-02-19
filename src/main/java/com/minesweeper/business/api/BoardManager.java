package com.minesweeper.business.api;

import javax.annotation.Nonnull;

public interface BoardManager {
    @Nonnull
    Board createBoard(@Nonnull String owner,
                      int width,
                      int height,
                      int mines);

    @Nonnull
    Board getBoard(@Nonnull String boardId);
}
