package org.example;

public class Repository {
    private final SudokuBoard sudokuBoard;

    public Repository(SudokuBoard sudokuBoard) {
        if (sudokuBoard == null) {
            throw new IllegalArgumentException("Null value");
        }
        this.sudokuBoard = sudokuBoard;
    }

    public SudokuBoard createInstance() {
        try {
            return (SudokuBoard) sudokuBoard.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
