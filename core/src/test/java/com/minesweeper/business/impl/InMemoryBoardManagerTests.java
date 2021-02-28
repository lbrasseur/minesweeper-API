package com.minesweeper.business.impl;

import com.minesweeper.business.api.Board;
import com.minesweeper.business.api.BoardState;
import com.minesweeper.business.api.Cell;
import com.minesweeper.business.api.IdGenerator;
import com.minesweeper.common.api.dto.BoardDto;
import com.minesweeper.data.api.BoardDao;
import org.easymock.EasyMockExtension;
import org.easymock.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(EasyMockExtension.class)
public class InMemoryBoardManagerTests {
    @Mock
    private IdGenerator idGenerator;

    @Mock
    private BoardDao boardDao;

    private InMemoryBoardManager boardManager;

    @BeforeEach
    void beforeEach() {
        boardManager = new InMemoryBoardManager(idGenerator,
                boardDao);
    }

    @Test
    void testCreateBoard() throws ExecutionException, InterruptedException {
        final String id = "myId";
        final String owner = "anOwner";
        final int width = 11;
        final int height = 7;
        final int mineCount = 5;

        // Expectations
        expect(idGenerator.generateId())
                .andReturn(id);
        expect(boardDao.save(isA(BoardDto.class)))
                .andReturn(CompletableFuture.completedFuture(null));

        replay(idGenerator);
        replay(boardDao);

        // Execution
        Board board = boardManager.create(owner, width, height, mineCount).get();

        // Assertions
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

        // Verification
        verify(idGenerator);
        verify(boardDao);
    }

    @Test
    void testPauseBoard() throws ExecutionException, InterruptedException {
        final String id = "myId";
        final CompletableFuture<BoardDto> readFuture = new CompletableFuture<>();

        // Expectations
        expect(idGenerator.generateId())
                .andReturn(id);
        expect(boardDao.save(isA(BoardDto.class)))
                .andReturn(CompletableFuture.completedFuture(null))
                .times(2);
        expect(boardDao.read(id))
                .andReturn(readFuture);

        replay(idGenerator);
        replay(boardDao);

        // Execution
        Board original = boardManager.create("anOwner", 11, 7, 5).get();
        readFuture.complete(original.toDto());
        Board board = boardManager.pause(id).get();

        // Assertions
        assertEquals(BoardState.PAUSED, board.getState());

        // Verification
        verify(idGenerator);
        verify(boardDao);
    }

    @Test
    void testResumeBoard() throws ExecutionException, InterruptedException {
        final String id = "myId";
        final CompletableFuture<BoardDto> readFuture = new CompletableFuture<>();
        final CompletableFuture<BoardDto> readFuture2 = new CompletableFuture<>();

        // Expectations
        expect(idGenerator.generateId())
                .andReturn(id);
        expect(boardDao.save(isA(BoardDto.class)))
                .andReturn(CompletableFuture.completedFuture(null))
                .times(3);
        expect(boardDao.read(id))
                .andReturn(readFuture);
        expect(boardDao.read(id))
                .andReturn(readFuture2);

        replay(idGenerator);
        replay(boardDao);

        // Execution
        Board board1 = boardManager.create("anOwner", 11, 7, 5).get();
        readFuture.complete(board1.toDto());
        Board board2 = boardManager.pause(id).get();
        readFuture2.complete(board2.toDto());
        Board board = boardManager.resume(id).get();

        // Assertions
        assertEquals(BoardState.PLAYING, board.getState());

        // Verification
        verify(idGenerator);
        verify(boardDao);
    }
}
