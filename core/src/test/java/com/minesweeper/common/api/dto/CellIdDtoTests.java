package com.minesweeper.common.api.dto;

import com.minesweeper.business.api.CellState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CellIdDtoTests {
    @Test
    void testCreation() {
        CellDto dto = new CellDto(CellState.CLICKED, true, null);
        assertEquals(CellState.CLICKED, dto.getState());
        assertEquals(true, dto.isHasMine());
    }

    @Test
    void testNullState() {
        Exception thrown = assertThrows(
                NullPointerException.class,
                () -> new CellDto(null, true, null),
                "NPE expected"
        );
        assertEquals("State can't be null", thrown.getMessage());
    }
}
