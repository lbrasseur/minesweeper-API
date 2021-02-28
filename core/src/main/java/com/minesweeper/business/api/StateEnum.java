package com.minesweeper.business.api;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

interface StateEnum<T extends Enum<T>> {
    default void checkTargetState(@Nonnull StateEnum<T> targetState) {
        requireNonNull(targetState);
        checkState(isAllowedTarget(targetState),
                "State " + this + " can't change to state " + targetState);
    }

    int ordinal();

    @Nonnull
    int[] allowedTargetStates();

    private boolean isAllowedTarget(StateEnum<T> targetState) {
        for (int allowedState : allowedTargetStates()) {
            if (targetState.ordinal() == allowedState) {
                return true;
            }
        }
        return false;
    }
}
