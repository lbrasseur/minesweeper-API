package com.minesweeper.business.api;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

interface StateEnum<T extends Enum<T>> {
    default void checkTargetState(@Nonnull StateEnum<T> targetState) {
        requireNonNull(targetState);
        checkArgument(targetState.isAllowedSource(state()),
                "State " + state() + " can't change to state " + targetState);
    }

    @Nonnull
    T state();

    @Nonnull
    T state(int ordinal);

    @Nonnull
    int[] allowedSourceStates();

    private boolean isAllowedSource(T sourceState) {
        for (int allowedState : allowedSourceStates()) {
            if (sourceState == state(allowedState)) {
                return true;
            }
        }
        return false;
    }
}
