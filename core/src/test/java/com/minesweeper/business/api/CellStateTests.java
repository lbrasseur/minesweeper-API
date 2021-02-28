package com.minesweeper.business.api;

import org.junit.jupiter.api.Test;

import static com.minesweeper.business.api.StateTestUtils.testInvalidState;
import static com.minesweeper.business.api.StateTestUtils.testValidState;

public class CellStateTests {
    @Test
    void testValidTargetStates() {
        testValidState(CellState.INITIAL, CellState.CLICKED);
        testValidState(CellState.INITIAL, CellState.RED_FLAG);
        testValidState(CellState.INITIAL, CellState.QUESTION_MARK);

        testValidState(CellState.RED_FLAG, CellState.INITIAL);
        testValidState(CellState.RED_FLAG, CellState.QUESTION_MARK);

        testValidState(CellState.QUESTION_MARK, CellState.INITIAL);
        testValidState(CellState.QUESTION_MARK, CellState.RED_FLAG);
    }

    @Test
    void testInvalidTargetStates() {
        testInvalidState(CellState.RED_FLAG, CellState.CLICKED);
        testInvalidState(CellState.RED_FLAG, CellState.RED_FLAG);

        testInvalidState(CellState.QUESTION_MARK, CellState.CLICKED);
        testInvalidState(CellState.QUESTION_MARK, CellState.QUESTION_MARK);

        testInvalidState(CellState.CLICKED, CellState.INITIAL);
        testInvalidState(CellState.CLICKED, CellState.RED_FLAG);
        testInvalidState(CellState.CLICKED, CellState.QUESTION_MARK);
        testInvalidState(CellState.CLICKED, CellState.CLICKED);
    }
}
