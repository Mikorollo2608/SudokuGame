package org.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SudokuBoard sudokuBoard = new SudokuBoard();
        sudokuBoard.fillBoard();
        for (int i = 0; i < 9; i++) {
            System.out.println(sudokuBoard.getSquare(0, i));
        }
        System.out.println("Hello ");
    }
}