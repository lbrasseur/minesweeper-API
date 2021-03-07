package com.minesweeper.business.api;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;

public interface HashManager {
    @Nonnull
    default String hash(@Nonnull String data) {
        return hash(data.getBytes(StandardCharsets.UTF_8));
    }

    @Nonnull
    String hash(@Nonnull byte[] data);
}
