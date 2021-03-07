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
    public CompletableFuture<Board> pause(@Nonnull String owner,
                                          @Nonnull String boardId) {
        requireNonNull(owner);
        requireNonNull(boardId);
        return processBoard(owner, boardId, Board::pause);
    }

    @Nonnull
    @Override
    public CompletableFuture<Board> resume(@Nonnull String owner,
                                           @Nonnull String boardId) {
        requireNonNull(owner);
        requireNonNull(boardId);
        return processBoard(owner, boardId, Board::resume);
    }

    @Nonnull
    @Override
    public CompletableFuture<List<BoardData>> find(@Nonnull String owner) {
        requireNonNull(owner);

        return boardDao.find(owner)
                .thenApply(dtos -> dtos.stream()
                        .map(dto -> new BoardDataImpl(dto.getId(),
                                dto.getState(),
                                Instant.ofEpochSecond(dto.getCreationMoment())))
                        .collect(Collectors.toList()));
    }

    @Nonnull
    @Override
    public CompletableFuture<Void> delete(@Nonnull String owner,
                                          @Nonnull String boardId) {
        requireNonNull(owner);
        requireNonNull(boardId);

        return boardDao.delete(owner, boardId);
    }

    @Nonnull
    @Override
    public CompletableFuture<Board> click(@Nonnull String owner,
                                          @Nonnull String boardId,
                                          int column,
                                          int row) {
        requireNonNull(owner);
        requireNonNull(boardId);

        return processCell(owner,
                boardId,
                column,
                row,
                Cell::click);
    }

    @Nonnull
    @Override
    public CompletableFuture<Board> redFlag(@Nonnull String owner,
                                            @Nonnull String boardId,
                                            int column,
                                            int row) {
        requireNonNull(owner);
        requireNonNull(boardId);

        return processCell(owner,
                boardId,
                column,
                row,
                Cell::redFlag);
    }

    @Nonnull
    @Override
    public CompletableFuture<Board> questionMark(@Nonnull String owner,
                                                 @Nonnull String boardId,
                                                 int column,
                                                 int row) {
        requireNonNull(owner);
        requireNonNull(boardId);

        return processCell(owner,
                boardId,
                column,
                row,
                Cell::questionMark);
    }

    @Nonnull
    @Override
    public CompletableFuture<Board> initial(@Nonnull String owner,
                                            @Nonnull String boardId,
                                            int column,
                                            int row) {
        requireNonNull(owner);
        requireNonNull(boardId);

        return processCell(owner,
                boardId,
                column,
                row,
                Cell::initial);
    }

    private CompletableFuture<Board> getBoard(@Nonnull String owner,
                                              @Nonnull String boardId) {
        return boardDao.read(owner, boardId)
                .thenApply(InMemoryBoard::new);
    }

    private CompletableFuture<Board> processCell(String owner,
                                                 String boardId,
                                                 int column,
                                                 int row,
                                                 Consumer<Cell> cellConsumer) {
        return processBoard(owner,
                boardId,
                board -> cellConsumer.accept(board.getCell(column, row)));
    }

    private CompletableFuture<Board> processBoard(String owner,
                                                  String boardId,
                                                  Consumer<Board> boardConsumerConsumer) {
        return getBoard(owner, boardId)
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
