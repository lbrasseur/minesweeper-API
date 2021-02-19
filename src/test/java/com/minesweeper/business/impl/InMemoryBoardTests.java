package com.minesweeper.business.impl;

import com.minesweeper.business.api.Board;
import com.minesweeper.business.api.BoardState;
import com.minesweeper.business.api.Cell;
import com.minesweeper.business.api.CellState;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InMemoryBoardTests {
    @Test
    void testBasicCreation() {
        final String id = "myId";
        final String owner = "anOwner";
        final int width = 11;
        final int height = 7;
        final int mineCount = 5;
        Board board = new InMemoryBoard(id,
                owner,
                width,
                height,
                mineCount);

        assertEquals(id, board.getId());
        assertEquals(owner, board.getOwner());
        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
        assertEquals(BoardState.PLAYING, board.getState());

        int mines = 0;
        for (int column = 0; column < board.getWidth(); column++) {
            for (int row = 0; row < board.getHeight(); row++) {
                Cell cell = board.getCell(column, row);
                if (cell.hasMine()) {
                    mines++;
                }
            }
        }
        assertEquals(mineCount, mines);
    }

    @Test
    void testNullId() {
        Exception thrown = assertThrows(
                NullPointerException.class,
                () -> new InMemoryBoard(null,
                        "owner",
                        11,
                        7,
                        5),
                "NPE expected"
        );
        assertEquals("Id can't be null", thrown.getMessage());
    }

    @Test
    void testNullOwner() {
        Exception thrown = assertThrows(
                NullPointerException.class,
                () -> new InMemoryBoard("id",
                        null,
                        11,
                        7,
                        5),
                "NPE expected"
        );
        assertEquals("Owner can't be null", thrown.getMessage());
    }

    @Test
    void testNegativeWidth() {
        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new InMemoryBoard("id",
                        "owner",
                        -1,
                        7,
                        5),
                "IllegalArgumentException expected"
        );
        assertEquals("Width must be greater than 0", thrown.getMessage());
    }

    @Test
    void testNegativeHeight() {
        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new InMemoryBoard("id",
                        "owner",
                        11,
                        -1,
                        5),
                "IllegalArgumentException expected"
        );
        assertEquals("Height must be greater than 0", thrown.getMessage());
    }

    @Test
    void testNegativeMineCount() {
        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new InMemoryBoard("id",
                        "owner",
                        11,
                        7,
                        -1),
                "IllegalArgumentException expected"
        );
        assertEquals("Mine count must be greater than 0", thrown.getMessage());
    }

    @Test
    void testMineCountBiggerThanBoardSize() {
        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new InMemoryBoard("id",
                        "owner",
                        11,
                        7,
                        11 * 7 + 1),
                "IllegalArgumentException expected"
        );
        assertEquals("Mine count must be smaller than board size", thrown.getMessage());
    }

    @Test
    void testMineCountEqualToBoardSize() {
        // You can't win!
        final int width = 11;
        final int height = 7;
        final int mineCount = width * height;
        Board board = new InMemoryBoard("id",
                "owner",
                width,
                height,
                mineCount);
        int mines = 0;
        for (int column = 0; column < board.getWidth(); column++) {
            for (int row = 0; row < board.getHeight(); row++) {
                Cell cell = board.getCell(column, row);
                if (cell.hasMine()) {
                    mines++;
                }
            }
        }
        assertEquals(mineCount, mines);
    }

    @Test
    void testGetValidCell() {
        final int width = 11;
        final int height = 7;
        Board board = new InMemoryBoard("id",
                "owner",
                width,
                height,
                5);

        Random random = new Random();
        Cell cell = board.getCell((int) random.nextDouble() * width,
                (int) random.nextDouble() * height);

        assertEquals(CellState.INITIAL, cell.getState());
    }

    @Test
    void testGetCellNegativeColumn() {
        final int width = 11;
        final int height = 7;

        Board board = new InMemoryBoard("id",
                "owner",
                width,
                height,
                5);

        final Random random = new Random();
        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> board.getCell(-1,
                        (int) random.nextDouble() * height),
                "IllegalArgumentException expected"
        );
        assertEquals("Column must be greater than or equal to 0", thrown.getMessage());
    }

    @Test
    void testGetCellNegativeRow() {
        final int width = 11;
        final int height = 7;

        Board board = new InMemoryBoard("id",
                "owner",
                width,
                height,
                5);

        final Random random = new Random();
        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> board.getCell((int) random.nextDouble() * width,
                        -1),
                "IllegalArgumentException expected"
        );
        assertEquals("Row must be greater than or equal to 0", thrown.getMessage());
    }

    @Test
    void testGetCellColumnTooBig() {
        final int width = 11;
        final int height = 7;

        Board board = new InMemoryBoard("id",
                "owner",
                width,
                height,
                5);

        final Random random = new Random();
        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> board.getCell(width + 1,
                        (int) random.nextDouble() * height),
                "IllegalArgumentException expected"
        );
        assertEquals("Column must be lower than width", thrown.getMessage());
    }

    @Test
    void testGetCellRowTooBig() {
        final int width = 11;
        final int height = 7;

        Board board = new InMemoryBoard("id",
                "owner",
                width,
                height,
                5);

        final Random random = new Random();
        Exception thrown = assertThrows(
                IllegalArgumentException.class,
                () -> board.getCell((int) random.nextDouble() * width,
                        height + 1),
                "IllegalArgumentException expected"
        );
        assertEquals("Row must be lower than height", thrown.getMessage());
    }
}
