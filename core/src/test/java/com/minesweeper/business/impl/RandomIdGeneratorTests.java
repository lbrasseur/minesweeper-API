package com.minesweeper.business.impl;

import com.minesweeper.business.api.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RandomIdGeneratorTests {
    private IdGenerator idGenerator;

    @BeforeEach
    void beforeEach() {
        idGenerator = new RandomIdGenerator();
    }

    @Test
    void testNullness() {
        assertNotNull(idGenerator.generateId());
    }

    @Test
    void testLength() {
        assertEquals(64, idGenerator.generateId().length());
    }

    @Test
    void testUniqueness() {
        Set<String> ids = new HashSet<>();
        for (int n = 0; n < 10000; n++) {
            String id = idGenerator.generateId();
            assertFalse(ids.contains(id));
            ids.add(id);
        }
    }
}
