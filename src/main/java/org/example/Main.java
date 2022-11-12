package org.example;

public class Main {
    public static void main(String[] args) {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        System.out.println(sudokuBoard.get(0,0));
        sudokuBoard.set(0,0,1);
        System.out.println(sudokuBoard.get(0,0));
    }
}