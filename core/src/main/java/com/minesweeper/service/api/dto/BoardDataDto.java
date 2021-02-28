package com.minesweeper.service.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.minesweeper.business.api.BoardState;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

public class BoardDataDto {
    @Nonnull
    private final String id;
    @Nonnull
    private final BoardState state;
    @Nonnull
    private final String creationMoment;

    @JsonCreator
    public BoardDataDto(@Nonnull String id,
                        @Nonnull BoardState state,
                        @Nonnull String creationMoment) {
        this.id = requireNonNull(id, "Id can't be null");
        this.state = requireNonNull(state, "State can't be null");;
        this.creationMoment = requireNonNull(creationMoment, "Creation moment can't be null");
    }

    @Nonnull
    public String getId() {
        return id;
    }

    @Nonnull
    public BoardState getState() {
        return state;
    }

    @Nonnull
    public String getCreationMoment() {
        return creationMoment;
    }
}
