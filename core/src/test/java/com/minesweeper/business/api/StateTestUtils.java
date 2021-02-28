package com.minesweeper.business.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

interface StateTestUtils {
    static <T extends Enum<T>> void testValidState(StateEnum<T> source, StateEnum<T> target) {
        source.checkTargetState(target);
    }

    static <T extends Enum<T>> void testInvalidState(StateEnum<T> source, StateEnum<T> target) {
        Exception thrown = assertThrows(
                IllegalStateException.class,
                () -> source.checkTargetState(target),
                "IllegalStateException expected"
        );
        assertEquals("State " + source + " can't change to state " + target, thrown.getMessage());
    }
}
