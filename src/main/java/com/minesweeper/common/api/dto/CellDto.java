package com.minesweeper.common.api.dto;

import com.minesweeper.business.api.CellState;

import javax.annotation.Nonnull;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class CellDto {
    @Nonnull
    private final CellState state;
    private final boolean hasMine;

    public CellDto(@Nonnull CellState state,
                   boolean hasMine) {
        this.state = requireNonNull(state);
        this.hasMine = hasMine;
    }

    @Nonnull
    public CellState getState() {
        return state;
    }

    public boolean isHasMine() {
        return hasMine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellDto cellDto = (CellDto) o;
        return hasMine == cellDto.hasMine && state == cellDto.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, hasMine);
    }
}
