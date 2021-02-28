package com.minesweeper.service.api;

import com.minesweeper.common.api.dto.BoardDto;
import com.minesweeper.service.api.dto.BoardDataDto;
import com.minesweeper.service.api.dto.BoardIdDto;
import com.minesweeper.service.api.dto.CellIdDto;
import com.minesweeper.service.api.dto.CreateBoardDto;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This is not used, right now, but it could be
 * helpful when creating a client (for example, Retrofit).
 * Of course it should be put into a separated project.
 */
public interface BoardService {
    @Nonnull
    CompletableFuture<BoardDto> create(@Nonnull CreateBoardDto dto);

    @Nonnull
    CompletableFuture<BoardDto> pause(@Nonnull BoardIdDto dto);

    @Nonnull
    CompletableFuture<BoardDto> resume(@Nonnull BoardIdDto dto);

    @Nonnull
    CompletableFuture<List<BoardDataDto>> find();

    @Nonnull
    CompletableFuture<Void> delete(@Nonnull BoardIdDto dto);

    @Nonnull
    CompletableFuture<BoardDto> click(@Nonnull CellIdDto dto);

    @Nonnull
    CompletableFuture<BoardDto> redFlag(@Nonnull CellIdDto dto);

    @Nonnull
    CompletableFuture<BoardDto> questionMark(@Nonnull CellIdDto dto);

    @Nonnull
    CompletableFuture<BoardDto> initial(@Nonnull CellIdDto dto);
}
