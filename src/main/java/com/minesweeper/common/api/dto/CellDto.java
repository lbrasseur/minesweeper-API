package com.minesweeper.common.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minesweeper.business.api.CellState;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class CellDto {
    @Nonnull
    private final CellState state;
    private final boolean hasMine;

    @JsonCreator
    public CellDto(@JsonProperty("state") @Nonnull CellState state,
                   @JsonProperty("hasMine") boolean hasMine) {
        this.state = requireNonNull(state, "State can't be null");
        this.hasMine = hasMine;
    }

    @Nonnull
    public CellState getState() {
        return state;
    }

    public boolean isHasMine() {
        return hasMine;
    }
}
