package com.minesweeper.business.api;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public enum CellState {
    INITIAL(2, 3), //0
    CLICKED(0), //1
    QUESTION_MARK(0, 3), //2
    RED_FLAG(0, 2); //3

    private int[] allowedSourceStates;

    CellState(int... allowedSourceStates) {
        this.allowedSourceStates = allowedSourceStates;
    }

    public boolean isAllowedSource(@Nonnull CellState sourceState) {
        requireNonNull(sourceState);
        for (int allowedState : allowedSourceStates) {
            if (sourceState == values()[allowedState]) {
                return true;
            }
        }
        return false;
    }
}