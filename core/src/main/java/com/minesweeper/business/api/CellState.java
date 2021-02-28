package com.minesweeper.business.api;

import javax.annotation.Nonnull;

public enum CellState
        implements StateEnum<CellState> {
    INITIAL(1, 2, 3), //0
    CLICKED(), //1
    QUESTION_MARK(0, 3), //2
    RED_FLAG(0, 2); //3

    private final int[] allowedTargetStates;

    CellState(int... allowedTargetStates) {
        this.allowedTargetStates = allowedTargetStates;
    }

    @Nonnull
    @Override
    public int[] allowedTargetStates() {
        return allowedTargetStates;
    }
}