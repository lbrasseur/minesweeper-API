package com.minesweeper.business.api;

import javax.annotation.Nonnull;

public enum BoardState
        implements StateEnum<BoardState> {
    PLAYING(1, 2, 3), //0
    PAUSED(0), //1
    SOLVED(), //2
    EXPLODED(); //3

    private final int[] allowedTargetStates;

    BoardState(int... allowedTargetStates) {
        this.allowedTargetStates = allowedTargetStates;
    }

    @Nonnull
    @Override
    public int[] allowedTargetStates() {
        return allowedTargetStates;
    }
}
