package com.minesweeper.business.impl;

import com.google.common.hash.Hashing;
import com.minesweeper.business.api.HashManager;
import com.minesweeper.business.api.IdGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.nio.ByteBuffer;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Component
public class RandomIdGenerator implements IdGenerator {
    private final HashManager hashManager;

    @Inject
    public RandomIdGenerator(@Nonnull HashManager hashManager) {
        this.hashManager = requireNonNull(hashManager);
    }

    @Nonnull
    @Override
    public String generateId() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return Hashing.sha256()
                .hashBytes(bb.array())
                .toString();
    }
}
