package com.minesweeper.business.api;

import javax.annotation.Nonnull;

public enum BoardState
        implements StateEnum<BoardState> {
    PLAYING(1), //0
    PAUSED(0), //1
    SOLVED(0), //2
    EXPLODED(0); //3

    private int[] allowedSourceStates;

    BoardState(int... allowedSourceStates) {
        this.allowedSourceStates = allowedSourceStates;
    }

    @Nonnull
    @Override
    public BoardState state() {
        return this;
    }

    @Nonnull
    @Override
    public BoardState state(int ordinal) {
        return BoardState.values()[ordinal];
    }

    @Nonnull
    @Override
    public int[] allowedSourceStates() {
        return allowedSourceStates;
    }
}
