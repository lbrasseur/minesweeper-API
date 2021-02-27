package com.minesweeper.common.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minesweeper.business.api.BoardState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

public class BoardDto {
    @Nonnull
    private final String id;
    @Nonnull
    private final String owner;
    @Nonnull
    private final BoardState state;
    @Nonnull
    private final CellDto[][] cells;
    private final long creationMoment;
    @Nullable
    private final Long lastPlayingMoment;
    private final long playingTime;

    @JsonCreator
    public BoardDto(@JsonProperty("id") @Nonnull String id,
                    @JsonProperty("owner") @Nonnull String owner,
                    @JsonProperty("state") @Nonnull BoardState state,
                    @JsonProperty("creationMoment") long creationMoment,
                    @JsonProperty("lastPlayingMoment") Long lastPlayingMoment,
                    @JsonProperty("playingTime") long playingTime,
                    @JsonProperty("cells") @Nonnull CellDto[][] cells) {
        requireNonNull(id, "Id can't be null");
        requireNonNull(owner, "Owner can't be null");
        requireNonNull(state, "State can't be null");
        requireNonNull(cells, "Cells can't be null");
        checkArgument(cells.length > 0, "Cell rows must be greater than 0");
        int columnCount = cells[0].length;
        checkArgument(columnCount > 0, "Cell columns must be greater than 0");
        for (CellDto[] row : cells) {
            requireNonNull(row, "A cell row can't be null");
            checkArgument(row.length == columnCount, "All the rows must have the same column count");
            for (CellDto cell : row) {
                requireNonNull(cell, "A cell can't be null");
            }
        }

        this.id = id;
        this.owner = owner;
        this.cells = cells;
        this.creationMoment = creationMoment;
        this.lastPlayingMoment = lastPlayingMoment;
        this.playingTime = playingTime;
        this.state = state;
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
    public BoardState getState() {
        return state;
    }

    @Nonnull
    public CellDto[][] getCells() {
        return cells;
    }

    public long getCreationMoment() {
        return creationMoment;
    }

    @Nullable
    public Long getLastPlayingMoment() {
        return lastPlayingMoment;
    }

    public long getPlayingTime() {
        return playingTime;
    }
}
