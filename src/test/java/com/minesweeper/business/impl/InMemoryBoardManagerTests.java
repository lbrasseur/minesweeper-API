package com.minesweeper.business.impl;

import com.minesweeper.business.api.Board;
import com.minesweeper.business.api.BoardManager;
import com.minesweeper.data.impl.InMemoryBoardDao;

import java.util.concurrent.ExecutionException;

public class InMemoryBoardManagerTests {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        BoardManager boardManager = new InMemoryBoardManager(new RandomIdGenerator(),
                new InMemoryBoardDao());

        Board board = boardManager.createBoard("me", 10, 5, 5).get();

        System.out.println(board);

        board.getCell(3, 2).click();

        System.out.println(board);
    }
}
