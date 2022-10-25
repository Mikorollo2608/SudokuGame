package org.example;

public class Main {
    public static void main(String[] args) {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
    }
}