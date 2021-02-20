package com.minesweeper.common.api.dto;

import com.minesweeper.business.api.CellState;
import com.minesweeper.business.impl.InMemoryBoard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CellDtoTests {
    @Test
    void testCreation() {
        CellDto dto = new CellDto(CellState.CLICKED, true);
        assertEquals(CellState.CLICKED, dto.getState());
        assertEquals(true, dto.isHasMine());
    }

    @Test
    void testNullState() {
        Exception thrown = assertThrows(
                NullPointerException.class,
                () -> new CellDto(null, true),
                "NPE expected"
        );
        assertEquals("State can't be null", thrown.getMessage());
    }
}
