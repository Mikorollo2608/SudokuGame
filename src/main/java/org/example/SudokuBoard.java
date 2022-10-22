package org.example;

public class SudokuBoard {
    private int[][] board = new int[9][9];

    private BacktrackingSudokuSolver sudokuSolver = new BacktrackingSudokuSolver();

    {
        //initialize the board with 0
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = 0;
            }
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
        return board[row][col];
    }

    public void set(int row, int col, int val) {
        board[row][col] = val;
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }
}
