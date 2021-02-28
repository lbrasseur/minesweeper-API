package com.minesweeper.business.impl;

import com.minesweeper.business.api.*;
import com.minesweeper.data.api.BoardDao;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
    public CompletableFuture<Board> create(@Nonnull String owner,
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
    public CompletableFuture<Board> pause(@Nonnull String boardId) {
        requireNonNull(boardId);
        return processBoard(boardId, Board::pause);
    }

    @Nonnull
    @Override
    public CompletableFuture<Board> resume(@Nonnull String boardId) {
        requireNonNull(boardId);
        return processBoard(boardId, Board::resume);
    }

    @Nonnull
    @Override
    public CompletableFuture<List<BoardData>> find() {
        return boardDao.find()
                .thenApply(dtos -> dtos.stream()
                        .map(dto -> new BoardDataImpl(dto.getId(),
                                dto.getState(),
                                Instant.ofEpochSecond(dto.getCreationMoment())))
                        .collect(Collectors.toList()));
    }

    @Nonnull
    @Override
    public CompletableFuture<Void> delete(@Nonnull String boardId) {
        requireNonNull(boardId);
        return boardDao.delete(boardId);
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
        return boardDao.read(boardId)
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
        return boardDao.save(board.toDto())
                .thenApply(dummy -> board);
    }
}
