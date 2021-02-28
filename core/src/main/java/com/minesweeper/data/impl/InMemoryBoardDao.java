package com.minesweeper.data.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minesweeper.common.api.dto.BoardDto;
import com.minesweeper.data.api.BoardDao;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.CompletableFuture.completedFuture;

// TODO: implement persistence according to che chosen platform
@Component
public class InMemoryBoardDao
        implements BoardDao {
    private final ObjectMapper mapper;
    private final Map<String, String> storage;

    public InMemoryBoardDao() {
        mapper = new ObjectMapper();
        storage = new HashMap<>();
    }

    @Override
    public CompletableFuture<Void> save(@Nonnull BoardDto board) {
        requireNonNull(board);

        storage.put(board.getId(), dtoToString(board));
        return completedFuture(null);
    }

    @Nonnull
    @Override
    public CompletableFuture<BoardDto> read(@Nonnull String boardId) {
        requireNonNull(boardId);

        String data = storage.get(boardId);
        checkArgument(data != null, "Board with id " + boardId + " not found");

        return completedFuture(stringToDto(data));
    }

    @Nonnull
    @Override
    public CompletableFuture<List<BoardDto>> find() {
        return completedFuture(storage.values()
                .stream()
                .map(this::stringToDto)
                .collect(Collectors.toList()));
    }

    @Nonnull
    @Override
    public CompletableFuture<Void> delete(@Nonnull String boardId) {
        requireNonNull(boardId);

        storage.remove(boardId);
        return completedFuture(null);
    }

    private String dtoToString(BoardDto board) {
        try {
            return mapper.writeValueAsString(board);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    private BoardDto stringToDto(String board) {
        try {
            return mapper.readValue(board, BoardDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
