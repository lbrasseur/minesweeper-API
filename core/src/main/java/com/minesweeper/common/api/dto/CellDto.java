package com.minesweeper.common.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minesweeper.business.api.CellState;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

public class CellDto {
    @Nonnull
    private final CellState state;
    private final boolean hasMine;
    private final int borderingMines;

    @JsonCreator
    public CellDto(@JsonProperty("state") @Nonnull CellState state,
                   @JsonProperty("hasMine") boolean hasMine,
                   @JsonProperty("borderingMines") int borderingMines) {
        requireNonNull(state, "State can't be null");
        checkArgument(borderingMines >= 0, "Bordering mines must be greater or equals to 0");
        checkArgument(borderingMines <= 8, "Bordering mines must be lower or equals to 8");
        this.state = state;
        this.hasMine = hasMine;
        this.borderingMines = borderingMines;
    }

    @Nonnull
    public CellState getState() {
        return state;
    }

    public boolean isHasMine() {
        return hasMine;
    }

    public int getBorderingMines() {
        return borderingMines;
    }
}
