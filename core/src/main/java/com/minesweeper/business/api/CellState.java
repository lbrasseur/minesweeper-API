package com.minesweeper.business.api;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.stream.Collectors;

public enum CellState
        implements StateEnum<CellState> {
    INITIAL(2, 3), //0
    CLICKED(0), //1
    QUESTION_MARK(0, 3), //2
    RED_FLAG(0, 2); //3

    private int[] allowedSourceStates;

    CellState(int... allowedSourceStates) {
        this.allowedSourceStates = allowedSourceStates;
    }

    @Nonnull
    @Override
    public CellState state() {
        return this;
    }

    @Nonnull
    @Override
    public CellState state(int ordinal) {
        return CellState.values()[ordinal];
    }

    @Nonnull
    @Override
    public int[] allowedSourceStates() {
        return allowedSourceStates;
    }
}