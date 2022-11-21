package org.example;

class Main {
    public static void main(String[] args) {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();

        System.out.println(solver.hashCode());

        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();

        System.out.println(sudokuBoard.toString());
        System.out.println(sudokuBoard.getBox(2,5).toString());
        System.out.println(sudokuBoard.getRow(8).toString());
        System.out.println(sudokuBoard.getColumn(8).toString());

        SudokuField field = new SudokuField();
        field.setFieldValue(1);

        System.out.println(field);
    }
}