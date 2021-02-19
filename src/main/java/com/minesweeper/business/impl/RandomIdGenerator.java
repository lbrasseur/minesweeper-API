package com.minesweeper.business.impl;

import com.google.common.hash.Hashing;
import com.minesweeper.business.api.IdGenerator;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.util.UUID;

public class RandomIdGenerator implements IdGenerator {
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
