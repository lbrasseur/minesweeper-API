package com.minesweeper.data.impl;

import com.minesweeper.common.api.dto.BoardDto;
import com.minesweeper.data.api.BoardDao;
import com.minesweeper.data.api.KeyValueStorage;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

@Component
public class KeyValueBoardDao
        implements BoardDao {
    private static final String TABLE = "Board";
    private static final String USER_BOARD_TABLE = "UserBoard";
    private final KeyValueStorage storage;

    @Inject
    public KeyValueBoardDao(@Nonnull KeyValueStorage storage) {
        this.storage = requireNonNull(storage);
    }

    @Nonnull
    @Override
    public CompletableFuture<Void> save(@Nonnull BoardDto board) {
        requireNonNull(board);

        String owner = board.getOwner();
        String boardId = board.getId();

        return storage.save(TABLE, compose(owner, boardId), board)
                .thenCompose(savedBoard -> userBoards(owner)
                        .thenCompose(boards -> {
                            boards.add(boardId);
                            return storage.save(USER_BOARD_TABLE, owner, boards);
                        }));
    }

    @Nonnull
    @Override
    public CompletableFuture<BoardDto> read(@Nonnull String owner,
                                            @Nonnull String boardId) {
        requireNonNull(owner);
        requireNonNull(boardId);

        return storage.read(TABLE,
                compose(owner, boardId),
                BoardDto.class);
    }

    @Nonnull
    @Override
    public CompletableFuture<List<BoardDto>> find(@Nonnull String owner) {
        requireNonNull(owner);

        return userBoards(owner).thenCompose(keys -> {
            List<BoardDto> boards = new ArrayList<>(keys.size());
            List<CompletableFuture<Void>> futures = new ArrayList<>(keys.size());

            for (String key : keys) {
                futures.add(storage.read(TABLE, compose(owner, key), BoardDto.class)
                        .thenAccept(boards::add));
            }

            return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                    .thenApply(dummy -> boards);
        });
    }

    @Nonnull
    @Override
    public CompletableFuture<Void> delete(@Nonnull String owner,
                                          @Nonnull String boardId) {
        requireNonNull(owner);
        requireNonNull(boardId);

        return storage.delete(TABLE, compose(owner, boardId))
                .thenCompose(savedBoard -> userBoards(owner)
                        .thenCompose(boards -> {
                            boards.remove(boardId);
                            return storage.save(USER_BOARD_TABLE, owner, boards);
                        }));
    }

    private String compose(String owner,
                           String boardId) {
        return owner + "-" + boardId;
    }

    private CompletableFuture<Set<String>> userBoards(String owner) {
        return storage.readSet(USER_BOARD_TABLE, owner, String.class)
                .exceptionally(e -> new HashSet<>());
    }
}
