package com.minesweeper.business.api;

import org.junit.jupiter.api.Test;

import static com.minesweeper.business.api.StateTestUtils.testInvalidState;
import static com.minesweeper.business.api.StateTestUtils.testValidState;

public class BoardStateTests {
    @Test
    void testValidTargetStates() {
        testValidState(BoardState.PLAYING, BoardState.PAUSED);
        testValidState(BoardState.PLAYING, BoardState.SOLVED);
        testValidState(BoardState.PLAYING, BoardState.EXPLODED);

        testValidState(BoardState.PAUSED, BoardState.PLAYING);
    }

    @Test
    void testInvalidTargetStates() {
        testInvalidState(BoardState.SOLVED, BoardState.PLAYING);
        testInvalidState(BoardState.SOLVED, BoardState.PAUSED);
        testInvalidState(BoardState.SOLVED, BoardState.EXPLODED);
        testInvalidState(BoardState.SOLVED, BoardState.SOLVED);

        testInvalidState(BoardState.EXPLODED, BoardState.PLAYING);
        testInvalidState(BoardState.EXPLODED, BoardState.PAUSED);
        testInvalidState(BoardState.EXPLODED, BoardState.SOLVED);
        testInvalidState(BoardState.EXPLODED, BoardState.EXPLODED);

        testInvalidState(BoardState.PAUSED, BoardState.EXPLODED);
        testInvalidState(BoardState.PAUSED, BoardState.SOLVED);
        testInvalidState(BoardState.PAUSED, BoardState.PAUSED);
    }
}
