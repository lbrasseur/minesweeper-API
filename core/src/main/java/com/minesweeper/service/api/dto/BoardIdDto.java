package com.minesweeper.service.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class BoardIdDto {
    @Nonnull
    private final String boardId;

    @JsonCreator
    public BoardIdDto(@JsonProperty("boardId") @Nonnull String boardId) {
        this.boardId = requireNonNull(boardId);
    }

    @Nonnull
    public String getBoardId() {
        return boardId;
    }
}
