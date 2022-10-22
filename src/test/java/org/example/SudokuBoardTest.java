package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class SudokuBoardTest {

    @Test
    void setTest() {
        SudokuBoard sudokuBoard = new SudokuBoard();
        Random random = new Random();
        int testValue = random.nextInt(9) + 1;
        int row = random.nextInt(9);
        int column = random.nextInt(9);

        sudokuBoard.set(row, column, testValue);
        assertEquals(sudokuBoard.get(row, column), testValue);
    }

    @Test
    void fillBoardEmptyTest() {
        SudokuBoard sudokuBoard = new SudokuBoard();
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

        //testing 2 different layouts for 2 consecutive calls of fillBoard on different objects
        SudokuBoard sudokuBoard1 = new SudokuBoard();
        sudokuBoard.solveGame();
        int[][] testArray2 = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testArray2[i][j] = sudokuBoard1.get(i, j);
            }
        }

        assertFalse(Arrays.deepEquals(testArray, testArray2));

        //testing 2 different layouts for 2 consecutive calls fillBoard for 2 different objects

        sudokuBoard.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testArray2[i][j] = sudokuBoard.get(i, j);
            }
        }

        SudokuBoard sudokuBoard2 = new SudokuBoard();
        int[][] testArray3 = new int[9][9];

        sudokuBoard2.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testArray3[i][j] = sudokuBoard2.get(i, j);
            }
        }

        assertFalse(Arrays.deepEquals(testArray2, testArray3));

    }

    @Test
    void fillBoardPartiallyFilledTest() {
        SudokuBoard sudokuBoard = new SudokuBoard();
        Random random = new Random();
        int testRow = random.nextInt(9);
        int testColumn = random.nextInt(9);
        int testValue = random.nextInt(9) + 1;

        sudokuBoard.set(testRow, testColumn, testValue);

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

        //check if the testValue set before is still the same
        assertEquals(sudokuBoard.get(testRow, testColumn), testValue);
    }

}