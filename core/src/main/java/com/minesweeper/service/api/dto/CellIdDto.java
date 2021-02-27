package com.minesweeper.service.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;

public class CellIdDto extends BoardIdDto {
    private final int column;
    private final int row;

    @JsonCreator
    public CellIdDto(@JsonProperty("boardId") @Nonnull String boardId,
                     @JsonProperty("column") int column,
                     @JsonProperty("row") int row) {
        super(boardId);
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
