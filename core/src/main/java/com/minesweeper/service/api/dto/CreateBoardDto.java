package com.minesweeper.service.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateBoardDto {
    private final int width;
    private final int height;
    private final int mines;

    @JsonCreator
    public CreateBoardDto(@JsonProperty("width") int width,
                          @JsonProperty("height") int height,
                          @JsonProperty("mines") int mines) {
        this.width = width;
        this.height = height;
        this.mines = mines;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMines() {
        return mines;
    }
}
