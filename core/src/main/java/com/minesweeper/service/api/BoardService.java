package com.minesweeper.service.api;

import com.minesweeper.common.api.dto.BoardDto;
import com.minesweeper.service.api.dto.*;

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
    CompletableFuture<ResultDto<BoardDto>> create(@Nonnull CreateBoardDto dto);

    @Nonnull
    CompletableFuture<ResultDto<BoardDto>> pause(@Nonnull BoardIdDto dto);

    @Nonnull
    CompletableFuture<ResultDto<BoardDto>> resume(@Nonnull BoardIdDto dto);

    @Nonnull
    CompletableFuture<ResultDto<List<BoardDataDto>>> find();

    @Nonnull
    CompletableFuture<ResultDto<Void>> delete(@Nonnull BoardIdDto dto);

    @Nonnull
    CompletableFuture<ResultDto<BoardDto>> click(@Nonnull CellIdDto dto);

    @Nonnull
    CompletableFuture<ResultDto<BoardDto>> redFlag(@Nonnull CellIdDto dto);

    @Nonnull
    CompletableFuture<ResultDto<BoardDto>> questionMark(@Nonnull CellIdDto dto);

    @Nonnull
    CompletableFuture<ResultDto<BoardDto>> initial(@Nonnull CellIdDto dto);
}
