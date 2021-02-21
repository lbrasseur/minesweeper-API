package com.minesweeper.service.api;

import com.minesweeper.common.api.dto.BoardDto;
import com.minesweeper.service.api.dto.ClickCellDto;
import com.minesweeper.service.api.dto.CreateBoardDto;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

/**
 * This is not used, right now, but it could be
 * helpful when creating a client (for example, Retrofit).
 * Of course it should be put into a separated project.
 */
public interface BoardService {
    CompletableFuture<BoardDto> create(@Nonnull CreateBoardDto dto);

    CompletableFuture<BoardDto> click(@Nonnull ClickCellDto dto);
}
