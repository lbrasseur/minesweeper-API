package com.minesweeper.business.impl;

import com.minesweeper.business.api.Board;
import com.minesweeper.business.api.BoardManager;
import com.minesweeper.business.api.IdGenerator;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import static java.util.Objects.requireNonNull;

public class InMemoryBoardManager
        implements BoardManager {
    private final IdGenerator idGenerator;

    @Inject
    public InMemoryBoardManager(IdGenerator idGenerator) {
        this.idGenerator = requireNonNull(idGenerator);
    }

    @Nonnull
    @Override
    public Board createBoard(@Nonnull String owner,
                             int width,
                             int height,
                             int mines) {
        return new InMemoryBoard(idGenerator.generateId(),
                owner,
                width,
                height,
                mines);
    }

    @Nonnull
    @Override
    public Board getBoard(@Nonnull String boardId) {
        throw new UnsupportedOperationException("Implement this!!");
    }
}
