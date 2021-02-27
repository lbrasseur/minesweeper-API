package com.minesweeper.business.impl;

import com.minesweeper.business.api.Board;
import com.minesweeper.business.api.BoardManager;
import com.minesweeper.business.api.Cell;
import com.minesweeper.business.api.IdGenerator;
import com.minesweeper.data.api.BoardDao;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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
        return saveBoard(new InMemoryBoard(idGenerator.generateId(),
                owner,
                width,
                height,
                mines));
    }

    @Nonnull
    @Override
    public CompletableFuture<Board> pauseBoard(@Nonnull String boardId) {
        requireNonNull(boardId);
        return processBoard(boardId, Board::pause);
    }

    @Nonnull
    @Override
    public CompletableFuture<Board> resumeBoard(@Nonnull String boardId) {
        requireNonNull(boardId);
        return processBoard(boardId, Board::resume);
    }

    @Nonnull
    @Override
    public CompletableFuture<Board> click(@Nonnull String boardId,
                                          int column,
                                          int row) {
        requireNonNull(boardId);
        return processCell(boardId, column, row, Cell::click);
    }

    @Nonnull
    @Override
    public CompletableFuture<Board> redFlag(@Nonnull String boardId,
                                            int column,
                                            int row) {
        return processCell(boardId, column, row, Cell::redFlag);
    }

    @Nonnull
    @Override
    public CompletableFuture<Board> questionMark(@Nonnull String boardId, int column, int row) {
        return processCell(boardId, column, row, Cell::questionMark);
    }

    @Nonnull
    @Override
    public CompletableFuture<Board> initial(@Nonnull String boardId, int column, int row) {
        return processCell(boardId, column, row, Cell::initial);
    }

    private CompletableFuture<Board> getBoard(@Nonnull String boardId) {
        requireNonNull(boardId);
        return boardDao.readBoard(boardId)
                .thenApply(InMemoryBoard::new);
    }

    private CompletableFuture<Board> processCell(String boardId,
                                                 int column,
                                                 int row,
                                                 Consumer<Cell> cellConsumer) {
        return processBoard(boardId,
                board -> cellConsumer.accept(board.getCell(column, row)));
    }

    private CompletableFuture<Board> processBoard(String boardId,
                                                  Consumer<Board> boardConsumerConsumer) {
        return getBoard(boardId)
                .thenCompose(board -> {
                    boardConsumerConsumer.accept(board);
                    return saveBoard(board);
                });
    }

    private CompletableFuture<Board> saveBoard(Board board) {
        return boardDao.saveBoard(board.toDto())
                .thenApply(dummy -> board);
    }
}
