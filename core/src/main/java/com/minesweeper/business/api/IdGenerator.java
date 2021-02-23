package com.minesweeper.business.api;

import javax.annotation.Nonnull;

public interface IdGenerator {
    @Nonnull
    String generateId();
}
