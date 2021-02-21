package com.minesweeper.business.api;

import com.minesweeper.common.api.dto.BoardDto;

import javax.annotation.Nonnull;

public interface Board {
    @Nonnull
    String getId();

    @Nonnull
    String getOwner();

    int getWidth();

    int getHeight();

    Cell getCell(int column, int row);

    @Nonnull
    BoardState getState();

    BoardDto toDto();
}
