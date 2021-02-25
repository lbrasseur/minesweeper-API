package com.minesweeper.service.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class CellIdDto {
    @Nonnull
    private final String boardId;
    private final int column;
    private final int row;

    @JsonCreator
    public CellIdDto(@JsonProperty("boardId") @Nonnull String boardId,
                     @JsonProperty("column") int column,
                     @JsonProperty("row") int row) {
        this.boardId = requireNonNull(boardId);
        this.column = column;
        this.row = row;
    }

    @Nonnull
    public String getBoardId() {
        return boardId;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
