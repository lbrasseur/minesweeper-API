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

    // TODO: setting this parameter seems odd... but allows reusing the DTO for both UI and storage
    BoardDto toDto(boolean full);
}
