package org.example;

import java.util.Arrays;
import java.util.List;

class Main {
    public static void main(String[] args) {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();

        System.out.println(sudokuBoard.toString());
    }
}
