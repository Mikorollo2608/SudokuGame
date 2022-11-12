package org.example;

import java.util.Arrays;
import java.util.List;

public class SudokuBoard {
    private List<SudokuField> board = Arrays.asList(new SudokuField[81]);

    private SudokuSolver sudokuSolver;

    public SudokuBoard(SudokuSolver newSudokuSolver) {
        sudokuSolver = newSudokuSolver;

        //initialize the board with 0
        for (int i = 0; i < 81; i++) {
            board.set(i, new SudokuField());
            board.get(i).setFieldValue(0);
        }
    }

    /**
     * Getter to a single cell on the board.
     *
     * @param row Row of the board.
     * @param col Column of the board.
     * @return Asked for cell
     */
    public int get(int row, int col) {
        return board.get(row * 9 + col).getFieldValue();
    }

    public void set(int row, int col, int val) {
        board.get(row * 9 + col).setFieldValue(val);
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public SudokuRow getRow(int row) {
        SudokuRow sudokuRow = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            sudokuRow.setContents(board.get(row * 9 + i), i);
        }
        return sudokuRow;
    }

    public SudokuColumn getColumn(int column) {
        SudokuColumn sudokuColumn = new SudokuColumn();
        for (int i = 0; i < 9; i++) {
            sudokuColumn.setContents(board.get(i * 9 + column), i);
        }
        return sudokuColumn;
    }

    public SudokuBox getBox(int row, int column) {
        SudokuBox sudokuBox = new SudokuBox();
        int index = 0;
        for (int i = row - row % 3; i < (row - row % 3) + 3; i++) {
            for (int j = column - column % 3; j < (column - column % 3) + 3; j++) {
                sudokuBox.setContents(board.get(i * 9 + j), index);
                index++;
            }
        }
        return sudokuBox;
    }

    boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            if (!getRow(i).verify()) {
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!getColumn(i).verify()) {
                return false;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!getBox(3 * i, 3 * j).verify()) {
                    return false;
                }
            }
        }
        return true;
    }

}