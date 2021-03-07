package com.minesweeper.business.impl;

import com.google.common.hash.Hashing;
import com.minesweeper.business.api.HashManager;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

@Component
public class Sha256HashManager
        implements HashManager {
    @Nonnull
    @Override
    public String hash(@Nonnull byte[] data) {
        requireNonNull(data);
        return Hashing.sha256()
                .hashBytes(data)
                .toString();
    }
}
