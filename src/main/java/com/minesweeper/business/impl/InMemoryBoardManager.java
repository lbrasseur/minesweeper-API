package com.minesweeper.business.impl;

import com.minesweeper.business.api.Board;
import com.minesweeper.business.api.BoardManager;
import com.minesweeper.business.api.IdGenerator;
import com.minesweeper.data.api.BoardDao;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

@Component
public class InMemoryBoardManager
        implements BoardManager {
    private final IdGenerator idGenerator;
    private final BoardDao boardDao;

    @Inject
    public InMemoryBoardManager(@Nonnull IdGenerator idGenerator,
                                @Nonnull BoardDao boardDao) {
        this.idGenerator = requireNonNull(idGenerator);
        this.boardDao = requireNonNull(boardDao);
    }

    @Nonnull
    @Override
    public CompletableFuture<Board> createBoard(@Nonnull String owner,
                                                int width,
                                                int height,
                                                int mines) {
        requireNonNull(owner);
        Board board = new InMemoryBoard(idGenerator.generateId(),
                owner,
                width,
                height,
                mines);
        System.out.println(board);
        return saveBoard(board);
    }

    @Nonnull
    @Override
    public CompletableFuture<Board> getBoard(@Nonnull String boardId) {
        requireNonNull(boardId);
        return boardDao.readBoard(boardId)
                .thenApply(InMemoryBoard::new);
    }

    @Nonnull
    @Override
    public CompletableFuture<Board> click(@Nonnull String boardId,
                                          int column,
                                          int row) {
        requireNonNull(boardId);
        return getBoard(boardId)
                .thenCompose(board -> {
                    board.getCell(column, row).click();
                    System.out.println(board);
                    return saveBoard(board);
                });
    }

    private CompletableFuture<Board> saveBoard(Board board) {
        return boardDao.saveBoard(board.toDto())
                .thenApply(dummy -> board);
    }
}
