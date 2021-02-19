package com.minesweeper.common.api.dto;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

public class BoardDto {
    @Nonnull
    private final String id;
    @Nonnull
    private final String owner;
    @Nonnull
    private final CellDto[][] cells;

    public BoardDto(@Nonnull String id,
                    @Nonnull String owner,
                    @Nonnull CellDto[][] cells) {
        requireNonNull(id, "Id can't be null");
        requireNonNull(owner, "Owner can't be null");
        requireNonNull(cells, "Cells can't be null");
        checkArgument(cells.length > 0, "Cell rows must be greater than 0");
        int columnCount = cells[0].length;
        checkArgument(columnCount > 0, "Cell columns must be greater than 0");
        for (CellDto[] row : cells) {
            requireNonNull(row, "Cell rows can't be null");
            checkArgument(row.length == columnCount, "All the rows bust have the same column count");
            for (CellDto cell : row) {
                requireNonNull(cell, "Cells can't be null");
            }
        }

        this.id = id;
        this.owner = owner;
        this.cells = cells;
    }

    @Nonnull
    public String getId() {
        return id;
    }

    @Nonnull
    public String getOwner() {
        return owner;
    }

    @Nonnull
    public CellDto[][] getCells() {
        return cells;
    }
}
