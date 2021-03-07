package com.minesweeper.spring.service;

import com.minesweeper.business.api.Board;
import com.minesweeper.business.api.BoardManager;
import com.minesweeper.business.api.UserManager;
import com.minesweeper.common.api.dto.BoardDto;
import com.minesweeper.service.api.BoardService;
import com.minesweeper.service.api.dto.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.minesweeper.spring.service.ServiceUtils.toResult;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.CompletableFuture.completedFuture;

@RestController
public class BoardServiceImpl
        implements BoardService {
    private static final String BEARER_PREFIX = "Bearer ";
    private final BoardManager boardManager;
    private final UserManager userManager;

    @Inject
    public BoardServiceImpl(@Nonnull BoardManager boardManager,
                            @Nonnull UserManager userManager) {
        this.boardManager = requireNonNull(boardManager);
        this.userManager = requireNonNull(userManager);
    }

    @Override
    @Nonnull
    @PostMapping("/create")
    public CompletableFuture<ResultDto<BoardDto>> create(@RequestBody @Nonnull CreateBoardDto dto,
                                                         @RequestHeader(HttpHeaders.AUTHORIZATION) @Nullable String authorization) {
        requireNonNull(dto);

        return validate(authorization, username -> toDto(boardManager.create(username,
                dto.getWidth(),
                dto.getHeight(),
                dto.getMines())));
    }

    @Nonnull
    @Override
    @PostMapping("/pause")
    public CompletableFuture<ResultDto<BoardDto>> pause(@RequestBody @Nonnull BoardIdDto dto,
                                                        @RequestHeader(HttpHeaders.AUTHORIZATION) @Nullable String authorization) {
        requireNonNull(dto);

        return validate(authorization, username -> toDto(boardManager.pause(username,
                dto.getBoardId())));
    }

    @Nonnull
    @Override
    @PostMapping("/resume")
    public CompletableFuture<ResultDto<BoardDto>> resume(@RequestBody @Nonnull BoardIdDto dto,
                                                         @RequestHeader(HttpHeaders.AUTHORIZATION) @Nullable String authorization) {
        requireNonNull(dto);

        return validate(authorization, username -> toDto(boardManager.resume(username,
                dto.getBoardId())));
    }

    @Nonnull
    @Override
    @GetMapping("/find")
    public CompletableFuture<ResultDto<List<BoardDataDto>>> find(@RequestHeader(HttpHeaders.AUTHORIZATION) @Nullable String authorization) {
        return validate(authorization, username -> boardManager.find(username)
                .thenApply(boards -> boards.stream()
                        .map(boardDate -> new BoardDataDto(boardDate.getId(),
                                boardDate.getState(),
                                boardDate.getCreationMoment().toString()))
                        .collect(Collectors.toList())));
    }

    @Nonnull
    @Override
    @PostMapping("/delete")
    public CompletableFuture<ResultDto<Void>> delete(@RequestBody @Nonnull BoardIdDto dto,
                                                     @RequestHeader(HttpHeaders.AUTHORIZATION) @Nullable String authorization) {
        requireNonNull(dto);

        return validate(authorization, username -> boardManager.delete(username,
                dto.getBoardId()));
    }

    @Override
    @Nonnull
    @PostMapping("/click")
    public CompletableFuture<ResultDto<BoardDto>> click(@RequestBody @Nonnull CellIdDto dto,
                                                        @RequestHeader(HttpHeaders.AUTHORIZATION) @Nullable String authorization) {
        requireNonNull(dto);

        return validate(authorization, username -> toDto(boardManager.click(username,
                dto.getBoardId(),
                dto.getColumn(),
                dto.getRow())));
    }

    @Override
    @Nonnull
    @PostMapping("/redFlag")
    public CompletableFuture<ResultDto<BoardDto>> redFlag(@RequestBody @Nonnull CellIdDto dto,
                                                          @RequestHeader(HttpHeaders.AUTHORIZATION) @Nullable String authorization) {
        requireNonNull(dto);

        return validate(authorization, username -> toDto(boardManager.redFlag(username,
                dto.getBoardId(),
                dto.getColumn(),
                dto.getRow())));
    }

    @Override
    @Nonnull
    @PostMapping("/questionMark")
    public CompletableFuture<ResultDto<BoardDto>> questionMark(@RequestBody @Nonnull CellIdDto dto,
                                                               @RequestHeader(HttpHeaders.AUTHORIZATION) @Nullable String authorization) {
        requireNonNull(dto);

        return validate(authorization, username -> toDto(boardManager.questionMark(username,
                dto.getBoardId(),
                dto.getColumn(),
                dto.getRow())));
    }

    @Override
    @Nonnull
    @PostMapping("/initial")
    public CompletableFuture<ResultDto<BoardDto>> initial(@RequestBody @Nonnull CellIdDto dto,
                                                          @RequestHeader(HttpHeaders.AUTHORIZATION) @Nullable String authorization) {
        requireNonNull(dto);

        return validate(authorization, username -> toDto(boardManager.initial(username,
                dto.getBoardId(),
                dto.getColumn(),
                dto.getRow())));
    }

    private CompletableFuture<BoardDto> toDto(CompletableFuture<Board> boardFuture) {
        return boardFuture.thenApply(Board::toDto);
    }

    private <T> CompletableFuture<ResultDto<T>> validate(@Nullable String authorization,
                                                         Function<String, CompletableFuture<T>> callback) {
        try {
            String username = userManager.getUser(token(authorization));
            return toResult(callback.apply(username));
        } catch (IllegalArgumentException e) {
            return completedFuture(ResultDto.fromError("Authorization error"));
        }
    }

    private String token(@Nullable String authorization) {
        if (authorization != null && authorization.startsWith(BEARER_PREFIX)) {
            return authorization.substring(BEARER_PREFIX.length());
        } else {
            return "";
        }
    }
}
