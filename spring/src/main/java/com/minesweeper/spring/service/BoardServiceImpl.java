package com.minesweeper.spring.service;

import com.minesweeper.business.api.Board;
import com.minesweeper.business.api.BoardManager;
import com.minesweeper.common.api.dto.BoardDto;
import com.minesweeper.service.api.BoardService;
import com.minesweeper.service.api.dto.BoardIdDto;
import com.minesweeper.service.api.dto.CellIdDto;
import com.minesweeper.service.api.dto.CreateBoardDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNull;

@RestController
public class BoardServiceImpl
        implements BoardService {
    private final BoardManager boardManager;

    @Inject
    public BoardServiceImpl(@Nonnull BoardManager boardManager) {
        this.boardManager = requireNonNull(boardManager);
    }

    @Override
    @Nonnull
    @PostMapping("/create")
    public CompletableFuture<BoardDto> create(@RequestBody @Nonnull CreateBoardDto dto) {
        requireNonNull(dto);
        // TODO: Get the owner from some context information,
        // like JWT token or whatever is used for authentication
        return toDto(boardManager.createBoard("owner",
                dto.getWidth(),
                dto.getHeight(),
                dto.getMines()));
    }

    @Nonnull
    @Override
    @PostMapping("/pause")
    public CompletableFuture<BoardDto> pause(@RequestBody @Nonnull BoardIdDto dto) {
        requireNonNull(dto);

        return toDto(boardManager.pauseBoard(dto.getBoardId()));
    }

    @Nonnull
    @Override
    @PostMapping("/resume")
    public CompletableFuture<BoardDto> resume(@RequestBody @Nonnull BoardIdDto dto) {
        requireNonNull(dto);

        return toDto(boardManager.resumeBoard(dto.getBoardId()));
    }

    @Override
    @Nonnull
    @PostMapping("/click")
    public CompletableFuture<BoardDto> click(@RequestBody @Nonnull CellIdDto dto) {
        requireNonNull(dto);

        return toDto(boardManager.click(dto.getBoardId(),
                dto.getColumn(),
                dto.getRow()));
    }

    @Override
    @Nonnull
    @PostMapping("/redFlag")
    public CompletableFuture<BoardDto> redFlag(@RequestBody @Nonnull CellIdDto dto) {
        requireNonNull(dto);

        return toDto(boardManager.redFlag(dto.getBoardId(),
                dto.getColumn(),
                dto.getRow()));
    }

    @Override
    @Nonnull
    @PostMapping("/questionMark")
    public CompletableFuture<BoardDto> questionMark(@RequestBody @Nonnull CellIdDto dto) {
        requireNonNull(dto);

        return toDto(boardManager.questionMark(dto.getBoardId(),
                dto.getColumn(),
                dto.getRow()));
    }

    @Override
    @Nonnull
    @PostMapping("/initial")
    public CompletableFuture<BoardDto> initial(@RequestBody @Nonnull CellIdDto dto) {
        requireNonNull(dto);

        return toDto(boardManager.initial(dto.getBoardId(),
                dto.getColumn(),
                dto.getRow()));
    }

    private CompletableFuture<BoardDto> toDto(CompletableFuture<Board> boardFuture) {
        return boardFuture.thenApply(Board::toDto);
    }
}