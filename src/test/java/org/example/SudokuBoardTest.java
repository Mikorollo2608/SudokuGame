package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class SudokuBoardTest {

    @Test
    void setTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        Random random = new Random();
        int testValue = random.nextInt(9) + 1;
        int row = random.nextInt(9);
        int column = random.nextInt(9);

        sudokuBoard.set(row, column, testValue);
        assertEquals(sudokuBoard.get(row, column), testValue);
    }

    @Test
    void fillBoardEmptyTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        int[][] testArray = new int[9][9];
        int row = 0;
        int col = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testArray[i][j] = sudokuBoard.get(i, j);
            }
        }

        //testing rows
        while (row != 9) {
            for (int i = 0; i < 9; i++) {
                for (int j = i + 1; j < 9; j++) {
                    assertNotEquals(testArray[row][j], testArray[row][i]);
                }
            }
            row++;
        }

        //testing columns
        while (col != 9) {
            for (int i = 0; i < 9; i++) {
                for (int j = i + 1; j < 9; j++) {
                    assertNotEquals(testArray[j][col], testArray[i][col]);
                }
            }
            col++;
        }

        //testing boxes
        row = col = 0;
        int tmp;

        while (row < 9) {
            for (int i = row; i < row + 3; i++) {
                for (int j = col; j < col + 3; j++) {
                    tmp = testArray[i][j];
                    testArray[i][j] = 0;
                    for (int a = i; a < row + 3; a++) {
                        for (int b = j; b < col + 3; b++) {
                            assertNotEquals(tmp, testArray[a][b]);
                        }
                    }
                    testArray[i][j] = tmp;
                }
            }
            col += 3;
            if (col == 9) {
                col = 0;
                row += 3;
            }
        }

        //testing 2 different layouts for 2 consecutive calls of fillBoard on the same object
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard.set(i, j, 0);
            }
        }

        sudokuBoard.solveGame();

        int[][] testArray2 = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testArray2[i][j] = sudokuBoard.get(i, j);
            }
        }

        assertFalse(Arrays.deepEquals(testArray, testArray2));

        //testing 2 different layouts for 2 consecutive calls fillBoard for 2 different objects
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard.set(i, j, 0);
            }
        }

        sudokuBoard.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testArray2[i][j] = sudokuBoard.get(i, j);
            }
        }

        SudokuBoard sudokuBoard1 = new SudokuBoard(backtrackingSudokuSolver);
        int[][] testArray3 = new int[9][9];

        sudokuBoard1.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testArray3[i][j] = sudokuBoard1.get(i, j);
            }
        }

        assertFalse(Arrays.deepEquals(testArray2, testArray3));

    }

    @Test
    void fillBoardPartiallyFilledTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        Random random = new Random();
        int testRow = random.nextInt(9);
        int testColumn = random.nextInt(8);
        int testValue = random.nextInt(9) + 1;
        int testValue1 = random.nextInt(9) + 1;

        while (testValue1 == testValue) {
            testValue1 = random.nextInt(9) + 1;
        }

        sudokuBoard.set(testRow, testColumn, testValue);
        sudokuBoard.set(testRow, 8, testValue1);

        sudokuBoard.solveGame();

        int[][] testArray = new int[9][9];
        int row = 0;
        int col = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testArray[i][j] = sudokuBoard.get(i, j);
            }
        }

        //testing rows
        while (row != 9) {
            for (int i = 0; i < 9; i++) {
                for (int j = i + 1; j < 9; j++) {
                    assertNotEquals(testArray[row][j], testArray[row][i]);
                }
            }
            row++;
        }

        //testing columns
        while (col != 9) {
            for (int i = 0; i < 9; i++) {
                for (int j = i + 1; j < 9; j++) {
                    assertNotEquals(testArray[j][col], testArray[i][col]);
                }
            }
            col++;
        }

        //testing boxes
        row = col = 0;
        int tmp;

        while (row < 9) {
            for (int i = row; i < row + 3; i++) {
                for (int j = col; j < col + 3; j++) {
                    tmp = testArray[i][j];
                    testArray[i][j] = 0;
                    for (int a = i; a < row + 3; a++) {
                        for (int b = j; b < col + 3; b++) {
                            assertNotEquals(tmp, testArray[a][b]);
                        }
                    }
                    testArray[i][j] = tmp;
                }
            }
            col += 3;
            if (col == 9) {
                col = 0;
                row += 3;
            }
        }

        //check if the testValue and testValue1 set before is still the same
        assertEquals(sudokuBoard.get(testRow, testColumn), testValue);
        assertEquals(sudokuBoard.get(testRow, 8), testValue1);
    }

    @Test
    void getRowTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        SudokuField[] values = new SudokuField[9];

        int row = 0;

        while (row != 9) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    values[j] = new SudokuField();
                    values[j].setFieldValue(sudokuBoard.get(row, j));
                }
                assertEquals(sudokuBoard.getRow(row).get(i).getFieldValue(),
                        values[i].getFieldValue());
            }
            row++;
        }
    }

    @Test
    void getColumnTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        SudokuField[] values = new SudokuField[9];

        int col = 0;

        while (col != 9) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    values[j] = new SudokuField();
                    values[j].setFieldValue(sudokuBoard.get(j, col));
                }
                assertEquals(sudokuBoard.getColumn(col).get(i).getFieldValue(),
                        values[i].getFieldValue());
            }
            col++;
        }
    }

    @Test
    void getBoxTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        SudokuField[] values = new SudokuField[9];

        for (int startRow = 0; startRow != 9; startRow += 3) {
            for (int startColumn = 0; startColumn != 9; startColumn += 3) {
                int counter = 0;
                for (int i = startRow; i < startRow + 3; i++) {
                    for (int j = startColumn; j < startColumn + 3; j++) {
                        values[counter] = new SudokuField();
                        values[counter].setFieldValue(sudokuBoard.get(i, j));
                        assertEquals(sudokuBoard.getBox(startRow, startColumn)
                                .get(counter).getFieldValue(), values[counter].getFieldValue());
                        counter++;
                    }
                }
            }
        }
    }

    @Test
    void checkBoardTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);

        //Good solve
        sudokuBoard.solveGame();
        assertTrue(sudokuBoard.checkBoard());

        //Clean board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard.set(i, j, 0);
            }
        }

        Random random = new Random();
        int testValue = random.nextInt(9) + 1;

        //Bad row
        sudokuBoard.solveGame();
        sudokuBoard.set(1, 1, testValue);
        sudokuBoard.set(1, 2, testValue);
        assertFalse(sudokuBoard.checkBoard());

        //Clean board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard.set(i, j, 0);
            }
        }
        //Bad column
        sudokuBoard.solveGame();
        int testValue2 = random.nextInt(9) + 1;
        if (testValue2 == sudokuBoard.get(0, 0)) {
            testValue2++;
            if (testValue2 == 9) {
                testValue2 = 1;
            }
        }
        sudokuBoard.set(0, 0, testValue2);
        for (int i = 1; i < 9; i++) {
            if (sudokuBoard.get(0, i) == testValue2) {
                sudokuBoard.set(0, i, 0);
            }
        }
        assertFalse(sudokuBoard.checkBoard());

        //Clean board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard.set(i, j, 0);
            }
        }
        //Bad box
        sudokuBoard.solveGame();
        sudokuBoard.set(0, 0, sudokuBoard.get(1, 1));
        for (int i = 1; i < 9; i++) {
            if (sudokuBoard.get(0, i) ==  sudokuBoard.get(1, 1)) {
                sudokuBoard.set(0, i, 0);
            }
            if (sudokuBoard.get(i, 0) ==  sudokuBoard.get(1, 1)) {
                sudokuBoard.set(i, 0, 0);
            }
        }

        assertFalse(sudokuBoard.checkBoard());
    }
}