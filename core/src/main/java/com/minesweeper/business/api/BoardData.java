package com.minesweeper.business.api;

import javax.annotation.Nonnull;
import java.time.Instant;

public interface BoardData {
    @Nonnull
    String getId();

    @Nonnull
    BoardState getState();

    @Nonnull
    Instant getCreationMoment();
}
