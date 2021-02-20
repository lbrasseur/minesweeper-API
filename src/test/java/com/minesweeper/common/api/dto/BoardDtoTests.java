package com.minesweeper.common.api.dto;

import com.minesweeper.business.api.CellState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardDtoTests {
    @Test
    void testCreation() {
        final String id = "id";
        final String owner = "owner";

        BoardDto dto = new BoardDto(id,
                owner,
                buildCells(5, 3));
        assertEquals(id, dto.getId());
        assertEquals(owner, dto.getOwner());
    }

    @Test
    void testNullId() {
        Exception thrown = assertThrows(
                NullPointerException.class,
                () -> new BoardDto(null,
                        "owner",
                        buildCells(5, 3)),
                "NPE expected"
        );
        assertEquals("Id can't be null", thrown.getMessage());
    }

    @Test
    void testNullOwner() {
        Exception thrown = assertThrows(
                NullPointerException.class,
                () -> new BoardDto("id",
                        null,
                        buildCells(5, 3)),
                "NPE expected"
        );
        assertEquals("Owner can't be null", thrown.getMessage());
    }

    @Test
    void testNullCells() {
        Exception thrown = assertThrows(
                NullPointerException.class,
                () -> new BoardDto("id",
                        "owner",
                        null),
                "NPE expected"
        );
        assertEquals("Cells can't be null", thrown.getMessage());
    }

    @Test
    void testNullRow() {
        final CellDto[][] cells = buildCells(5, 3);
        cells[2] = null;
        Exception thrown = assertThrows(
                NullPointerException.class,
                () -> new BoardDto("id",
                        "owner",
                        cells),
                "NPE expected"
        );
        assertEquals("A cell row can't be null", thrown.getMessage());
    }

    @Test
    void testNullCell() {
        final CellDto[][] cells = buildCells(5, 3);
        cells[2][1] = null;
        Exception thrown = assertThrows(
                NullPointerException.class,
                () -> new BoardDto("id",
                        "owner",
                        cells),
                "NPE expected"
        );
        assertEquals("A cell can't be null", thrown.getMessage());
    }

    @Test
    void testRowSize() {
        final CellDto[][] cells = buildCells(5, 3);
        cells[2] = new CellDto[1];
        cells[2][0] = new CellDto(CellState.INITIAL, false);
        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new BoardDto("id",
                        "owner",
                        cells),
                "NPE expected"
        );
        assertEquals("All the rows must have the same column count", thrown.getMessage());
    }

    @Test
    void testZeroColumns() {
        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new BoardDto("id",
                        "owner",
                        buildCells(0, 3)),
                "NPE expected"
        );
        assertEquals("Cell columns must be greater than 0", thrown.getMessage());
    }

    @Test
    void testZeroRows() {
        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new BoardDto("id",
                        "owner",
                        buildCells(5, 0)),
                "NPE expected"
        );
        assertEquals("Cell rows must be greater than 0", thrown.getMessage());
    }

    private CellDto[][] buildCells(int width, int height) {
        CellDto[][] cells = new CellDto[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                cells[row][column] = new CellDto(CellState.INITIAL, false);
            }
        }
        return cells;
    }
}
