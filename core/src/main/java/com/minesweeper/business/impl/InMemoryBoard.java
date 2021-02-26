package com.minesweeper.business.impl;

import com.minesweeper.business.api.Board;
import com.minesweeper.business.api.BoardState;
import com.minesweeper.business.api.Cell;
import com.minesweeper.business.api.CellState;
import com.minesweeper.common.api.dto.BoardDto;
import com.minesweeper.common.api.dto.CellDto;

import javax.annotation.Nonnull;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

public class InMemoryBoard
        implements Board {
    private final String id;
    private final String owner;
    private final InMemoryCell[][] cells;
    private BoardState boardState;
    private int pendingCells;

    public InMemoryBoard(@Nonnull String id,
                         @Nonnull String owner,
                         int width,
                         int height,
                         int mineCount) {
        requireNonNull(id, "Id can't be null");
        requireNonNull(owner, "Owner can't be null");
        checkArgument(width > 0, "Width must be greater than 0");
        checkArgument(height > 0, "Height must be greater than 0");
        checkArgument(mineCount > 0, "Mine count must be greater than 0");
        int boardSize = width * height;
        checkArgument(mineCount <= boardSize, "Mine count must be smaller than board size");

        this.id = id;
        this.owner = owner;
        cells = new InMemoryCell[height][width];
        boardState = BoardState.PLAYING;
        pendingCells = boardSize - mineCount;

        buildRandomCells(mineCount);
    }

    public InMemoryBoard(@Nonnull BoardDto dto) {
        requireNonNull(dto, "DTO can't be null");

        this.id = dto.getId();
        this.owner = dto.getOwner();
        this.pendingCells = dto.getCells().length * dto.getCells()[0].length;
        boardState = BoardState.PLAYING;

        CellDto[][] dtoCells = dto.getCells();
        cells = new InMemoryCell[dtoCells.length][];
        for (int row = 0; row < dtoCells.length; row++) {
            CellDto[] cellsDtoRow = dtoCells[row];
            cells[row] = new InMemoryCell[cellsDtoRow.length];
            for (int column = 0; column < cellsDtoRow.length; column++) {
                CellDto cellDto = cellsDtoRow[column];
                cells[row][column] = new InMemoryCell(cellDto.getState(),
                        cellDto.isHasMine(),
                        column,
                        row);
                if (cellDto.getState() == CellState.CLICKED || cellDto.isHasMine()) {
                    pendingCells--;
                }
            }
        }
        updateStatusIfSolved();
    }

    @Nonnull
    @Override
    public String getId() {
        return id;
    }

    @Nonnull
    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public int getWidth() {
        return cells[0].length;
    }

    @Override
    public int getHeight() {
        return cells.length;
    }

    @Override
    public Cell getCell(int column, int row) {
        checkArgument(column >= 0, "Column must be greater than or equal to 0");
        checkArgument(row >= 0, "Row must be greater than or equal to 0");
        checkArgument(column < getWidth(), "Column must be lower than width");
        checkArgument(row < getHeight(), "Row must be lower than height");
        return cells[row][column];
    }

    @Nonnull
    @Override
    public BoardState getState() {
        return boardState;
    }

    @Override
    public BoardDto toDto(boolean full) {
        int width = getWidth();
        int height = getHeight();

        CellDto[][] cellDtos = new CellDto[height][width];

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                Cell cell = cells[row][column];
                cellDtos[row][column] = new CellDto(cell.getState(),
                        cell.hasMine(),
                        full ? cell.getBorderingMinesCount() : null);
            }
        }

        return new BoardDto(id, owner, cellDtos, full ? boardState : null);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("State: ");
        sb.append(boardState);
        sb.append('\n');
        String bar = "+" + "-".repeat(getWidth() * 2 - 1) + "+\n";
        sb.append(bar);
        for (int row = 0; row < getHeight(); row++) {
            sb.append('|');
            for (int column = 0; column < getWidth(); column++) {
                Cell cell = cells[row][column];
                switch (cell.getState()) {
                    case INITIAL:
                        sb.append('#');
                        break;
                    case RED_FLAG:
                        sb.append('F');
                        break;
                    case QUESTION_MARK:
                        sb.append('?');
                        break;
                    case CLICKED:
                        if (cell.hasMine()) {
                            sb.append('X');
                        } else {
                            int borderingCount = cell.getBorderingMinesCount();
                            sb.append(borderingCount == 0
                                    ? ' '
                                    : Character.forDigit(borderingCount, 10));
                        }
                        break;
                }
                sb.append('|');
            }
            sb.append('\n');
            sb.append(bar);
        }
        return sb.toString();
    }

    private void buildRandomCells(int mineCount) {
        int width = getWidth();
        int height = getHeight();
        int boardSize = width * height;

        // TODO: This looks ugly
        List<Integer> cellPositions = new ArrayList<>(boardSize);
        for (int counter = 0; counter < boardSize; counter++) {
            cellPositions.add(counter);
        }
        Random random = new Random();
        Set<Integer> mines = new HashSet<>(mineCount);
        for (int remainingMines = mineCount; remainingMines > 0; remainingMines--) {
            mines.add(cellPositions.remove((int) (random.nextDouble() * cellPositions.size())));
        }

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                cells[row][column] = new InMemoryCell(CellState.INITIAL,
                        mines.contains(row * width + column),
                        column,
                        row);
            }
        }
    }

    private void updateStatusIfSolved() {
        if (boardState == BoardState.PLAYING && pendingCells == 0) {
            boardState = BoardState.SOLVED;
            for (InMemoryCell[] row : cells) {
                for (InMemoryCell cell : row) {
                    if (cell.hasMine()) {
                        cell.state = CellState.RED_FLAG;
                    }
                }
            }
        }
    }

    private class InMemoryCell
            implements Cell {
        private CellState state;
        private final boolean hasMine;
        private final int column;
        private final int row;
        private int borderingMinesCount = -1;

        private InMemoryCell(CellState state,
                             boolean hasMine,
                             int column,
                             int row) {
            this.state = state;
            this.hasMine = hasMine;
            this.column = column;
            this.row = row;
            updateStatusIfExploded();
        }

        @Nonnull
        @Override
        public CellState getState() {
            return state;
        }

        @Override
        public boolean hasMine() {
            return hasMine;
        }

        @Override
        public int getBorderingMinesCount() {
            if (borderingMinesCount < 0) {
                int count = 0;
                for (Cell cell : getBorderingCells()) {
                    if (cell.hasMine()) {
                        count++;
                    }
                }
                borderingMinesCount = count;
            }
            return borderingMinesCount;
        }

        @Override
        public void click() {
            checkCurrentBoardState(BoardState.PLAYING);
            checkTargetState(CellState.CLICKED);
            state = CellState.CLICKED;
            updateStatusIfExploded();
            if (!hasMine) {
                pendingCells--;
                if (getBorderingMinesCount() == 0) {
                    for (Cell borderingCell : getBorderingCells()) {
                        if (borderingCell.getState() == CellState.INITIAL) {
                            borderingCell.click();
                        }
                    }
                }
                updateStatusIfSolved();
            }
        }

        @Override
        public void redFlag() {
            checkCurrentBoardState(BoardState.PLAYING);
            checkTargetState(CellState.RED_FLAG);
            state = CellState.RED_FLAG;
        }

        @Override
        public void questionMark() {
            checkCurrentBoardState(BoardState.PLAYING);
            checkTargetState(CellState.QUESTION_MARK);
            state = CellState.QUESTION_MARK;
        }

        @Override
        public void initial() {
            checkCurrentBoardState(BoardState.PLAYING);
            checkTargetState(CellState.INITIAL);
            state = CellState.INITIAL;
        }

        private void checkCurrentBoardState(BoardState currentBoardState) {
            checkArgument(currentBoardState == boardState,
                    "Current board state is " + boardState + " and must be " + currentBoardState);
        }

        private void checkTargetState(CellState targetState) {
            checkArgument(targetState.isAllowedSource(state),
                    "State " + state + " can't change to state " + targetState);
        }

        private void checkCurrentState(CellState currentState) {
            checkArgument(currentState == state,
                    "Current state is " + state + " and must be " + currentState);
        }

        private Iterable<Cell> getBorderingCells() {
            List<Cell> borderingCells = new ArrayList<>();
            for (int currentColumn = column - 1; currentColumn <= column + 1; currentColumn++) {
                for (int currentRow = row - 1; currentRow <= row + 1; currentRow++) {
                    if (!(currentColumn == column && currentRow == row)
                            && currentRow >= 0
                            && currentRow < getHeight()
                            && currentColumn >= 0
                            && currentColumn < getWidth()) {
                        borderingCells.add(cells[currentRow][currentColumn]);
                    }
                }
            }

            return borderingCells;
        }

        private void updateStatusIfExploded() {
            if (state == CellState.CLICKED && hasMine) {
                boardState = BoardState.EXPLODED;
            }
        }
    }
}
