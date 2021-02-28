package com.minesweeper.business.impl;

import com.minesweeper.business.api.BoardData;
import com.minesweeper.business.api.BoardState;

import javax.annotation.Nonnull;
import java.time.Instant;

import static java.util.Objects.requireNonNull;

class BoardDataImpl implements BoardData {
    @Nonnull
    private final String id;
    @Nonnull
    private final BoardState state;
    @Nonnull
    private final Instant creationMoment;

    BoardDataImpl(@Nonnull String id,
                  @Nonnull BoardState state,
                  @Nonnull Instant creationMoment) {
        this.id = requireNonNull(id);
        this.state = requireNonNull(state);
        this.creationMoment = requireNonNull(creationMoment);
    }

    @Override
    @Nonnull
    public String getId() {
        return id;
    }

    @Override
    @Nonnull
    public BoardState getState() {
        return state;
    }

    @Override
    @Nonnull
    public Instant getCreationMoment() {
        return creationMoment;
    }
}
