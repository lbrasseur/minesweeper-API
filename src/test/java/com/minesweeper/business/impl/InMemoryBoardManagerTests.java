package com.minesweeper.business.impl;

import com.google.gson.GsonBuilder;
import com.minesweeper.business.api.Board;
import com.minesweeper.business.api.BoardManager;
import com.minesweeper.business.api.IdGenerator;

public class InMemoryBoardManagerTests {
    public static void main(String[] args) {
        IdGenerator idGenerator = new RandomIdGenerator();
        BoardManager boardManager = new InMemoryBoardManager(idGenerator);

        Board board = boardManager.createBoard("me", 10, 5, 5);

        System.out.println(board);

        board.getCell(3, 2).click();

        System.out.println(board);

//        board.getCell(0, 0).click();
//
//        System.out.println(board);

//
//
//        GsonBuilder gson = new GsonBuilder();
//        gson.registerTypeAdapter(InMemoryBoard.InMemoryCell.class, new InMemoryCellInstanceCreator());

    }
}
