package com.minesweeper.common.api.dto;

import com.minesweeper.business.api.CellState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellDtoTests {
    @Test
    void testCreation() {
        CellDto dto = new CellDto(CellState.CLICKED, true, 0);
        assertEquals(CellState.CLICKED, dto.getState());
        assertTrue(dto.isHasMine());
        assertEquals(0, dto.getBorderingMines());
    }

    @Test
    void testNullState() {
        Exception thrown = assertThrows(
                NullPointerException.class,
                () -> new CellDto(null, true, 0),
                "NPE expected"
        );
        assertEquals("State can't be null", thrown.getMessage());
    }

    @Test
    void testNegativeBorderingMines() {
        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new CellDto(CellState.INITIAL, true, -1),
                "IllegalArgumentException expected"
        );
        assertEquals("Bordering mines must be greater or equals to 0", thrown.getMessage());
    }

    @Test
    void testBigBorderingMines() {
        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new CellDto(CellState.INITIAL, true, 9),
                "IllegalArgumentException expected"
        );
        assertEquals("Bordering mines must be lower or equals to 8", thrown.getMessage());
    }
}
