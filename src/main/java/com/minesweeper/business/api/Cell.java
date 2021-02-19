package com.minesweeper.business.api;

import javax.annotation.Nonnull;

public interface Cell {
    @Nonnull
    CellState getState();

    boolean hasMine();

    int getBorderingMinesCount();

    void click();

    void setRedFlag(boolean active);

    void setQuestionMark(boolean active);
}
