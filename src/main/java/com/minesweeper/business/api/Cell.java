package com.minesweeper.business.api;

import javax.annotation.Nonnull;

public interface Cell {
    @Nonnull
    CellState getState();

    boolean hasMine();

    int getBorderingMinesCount();

    void click();

    void redFlag();

    void questionMark();

    void initial();
}
