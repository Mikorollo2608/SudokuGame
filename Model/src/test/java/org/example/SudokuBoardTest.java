package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
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

        List<Integer> testArray = Arrays.asList(new Integer[81]);
        int row = 0;
        int col = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testArray.set(i * 9 + j, sudokuBoard.get(i, j));
            }
        }

        //testing rows
        while (row != 9) {
            for (int i = 0; i < 9; i++) {
                for (int j = i + 1; j < 9; j++) {
                    assertNotEquals(testArray.get(row * 9 + j), testArray.get(row * 9 + i));
                }
            }
            row++;
        }

        //testing columns
        while (col != 9) {
            for (int i = 0; i < 9; i++) {
                for (int j = i + 1; j < 9; j++) {
                    assertNotEquals(testArray.get(j * 9 + col), testArray.get(i * 9 + col));
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
                    tmp = testArray.get(i * 9 + j);
                    testArray.set(i * 9 + j, 0);
                    for (int a = i; a < row + 3; a++) {
                        for (int b = j; b < col + 3; b++) {
                            assertNotEquals(tmp, testArray.get(a * 9 + b));
                        }
                    }
                    testArray.set(i * 9 + j, tmp);
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

        List<Integer> testArray2 = Arrays.asList(new Integer[81]);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testArray2.set(i * 9 + j, sudokuBoard.get(i, j));
            }
        }

        //assertFalse(testArray.equals(testArray2));
        assertNotEquals(testArray, testArray2);

        //testing 2 different layouts for 2 consecutive calls fillBoard for 2 different objects
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard.set(i, j, 0);
            }
        }

        sudokuBoard.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testArray2.set(i * 9 + j, sudokuBoard.get(i, j));
            }
        }

        SudokuBoard sudokuBoard1 = new SudokuBoard(backtrackingSudokuSolver);
        List<Integer> testArray3 = Arrays.asList(new Integer[81]);

        sudokuBoard1.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testArray3.set(i * 9 + j, sudokuBoard1.get(i, j));
            }
        }

        //assertFalse(testArray2.equals(testArray3));
        assertNotEquals(testArray2, testArray3);

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

        List<Integer> testArray = Arrays.asList(new Integer[81]);
        int row = 0;
        int col = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testArray.set(i * 9 + j, sudokuBoard.get(i, j));
            }
        }

        //testing rows
        while (row != 9) {
            for (int i = 0; i < 9; i++) {
                for (int j = i + 1; j < 9; j++) {
                    assertNotEquals(testArray.get(row * 9 + j), testArray.get(row * 9 + i));
                }
            }
            row++;
        }

        //testing columns
        while (col != 9) {
            for (int i = 0; i < 9; i++) {
                for (int j = i + 1; j < 9; j++) {
                    assertNotEquals(testArray.get(j * 9 + col), testArray.get(i * 9 + col));
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
                    tmp = testArray.get(i * 9 + j);
                    testArray.set(i * 9 + j, 0);
                    for (int a = i; a < row + 3; a++) {
                        for (int b = j; b < col + 3; b++) {
                            assertNotEquals(tmp, testArray.get(a * 9 + b));
                        }
                    }
                    testArray.set(i * 9 + j, tmp);
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

        List<SudokuField> values = Arrays.asList(new SudokuField[9]);

        int row = 0;

        while (row != 9) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    values.set(j, new SudokuField());
                    values.get(j).setFieldValue(sudokuBoard.get(row, j));
                }
                assertNotNull(sudokuBoard.getRow(row));
                assertEquals(sudokuBoard.getRow(row).get(i).getFieldValue(),
                        values.get(i).getFieldValue());
            }
            row++;
        }
    }

    @Test
    void getColumnTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        List<SudokuField> values = Arrays.asList(new SudokuField[9]);

        int col = 0;
        while (col != 9) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    values.set(j, new SudokuField());
                    values.get(j).setFieldValue(sudokuBoard.get(j, col));
                }
                assertNotNull(sudokuBoard.getColumn(col));
                assertEquals(sudokuBoard.getColumn(col).get(i).getFieldValue(),
                        values.get(i).getFieldValue());
            }
            col++;
        }
    }

    @Test
    void getBoxTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(backtrackingSudokuSolver);
        sudokuBoard.solveGame();

        List<SudokuField> values = Arrays.asList(new SudokuField[9]);

        for (int startRow = 0; startRow != 9; startRow += 3) {
            for (int startColumn = 0; startColumn != 9; startColumn += 3) {
                int counter = 0;
                for (int i = startRow; i < startRow + 3; i++) {
                    for (int j = startColumn; j < startColumn + 3; j++) {
                        values.set(counter, new SudokuField());
                        values.get(counter).setFieldValue(sudokuBoard.get(i, j));
                        assertNotNull(sudokuBoard.getBox(startRow, startColumn));
                        assertEquals(sudokuBoard.getBox(startRow, startColumn)
                                .get(counter).getFieldValue(), values.get(counter).getFieldValue());
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
            if (sudokuBoard.get(0, i) == sudokuBoard.get(1, 1)) {
                sudokuBoard.set(0, i, 0);
            }
            if (sudokuBoard.get(i, 0) == sudokuBoard.get(1, 1)) {
                sudokuBoard.set(i, 0, 0);
            }
        }

        assertFalse(sudokuBoard.checkBoard());
    }

    @Test
    void toStringTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        String testString = "SudokuBoard{\n";
        for (int j = 0; j < 9; j++) {
            for (int k = 0; k < 9; k++) {
                testString = testString.concat(Integer.toString(sudokuBoard.get(j, k)));
                testString = testString.concat(" ");
                if (k == 2 || k == 5) {
                    testString = testString.concat("| ");
                }
            }
            testString = testString.concat("\n");
            if (j == 2 || j == 5) {
                testString = testString.concat("---------------------\n");
            }
        }
        testString = testString.concat("}");

        assertEquals(testString, sudokuBoard.toString());

        sudokuBoard.solveGame();
        String testString2 = "SudokuBoard{\n";
        for (int j = 0; j < 9; j++) {
            for (int k = 0; k < 9; k++) {
                testString2 = testString2.concat(Integer.toString(sudokuBoard.get(j, k)));
                testString2 = testString2.concat(" ");
                if (k == 2 || k == 5) {
                    testString2 = testString2.concat("| ");
                }
            }
            testString2 = testString2.concat("\n");
            if (j == 2 || j == 5) {
                testString2 = testString2.concat("---------------------\n");
            }
        }
        testString2 = testString2.concat("}");

        assertEquals(testString2, sudokuBoard.toString());
    }

    @Test
    void equalsSameObject() {
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
        assertTrue(sudokuBoard1.equals(sudokuBoard1));
    }

    @Test
    void equalsNull() {
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
        assertFalse(sudokuBoard1.equals(null));
    }

    @Test
    void equalsDifferentType() {
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuField field = new SudokuField();

        assertNotEquals(field, null);
        assertFalse(sudokuBoard1.equals(field));
    }

    @Test
    void equalsTwoObjectsSameTypeDiffValues() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(solver);
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver);

        sudokuBoard1.solveGame();
        sudokuBoard2.solveGame();

        assertFalse(sudokuBoard1.equals(sudokuBoard2));
    }

    @Test
    void equalsTwoObjectsSameTypeSameValues() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(solver);
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver);

        sudokuBoard1.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard2.set(i, j,sudokuBoard1.get(i, j));
            }
        }


        assertTrue(sudokuBoard1.equals(sudokuBoard2));
    }

    @Test
    void equalsTwoObjectsSameTypeEmpty() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(solver);
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver);

        sudokuBoard1.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard2.set(i, j,sudokuBoard1.get(i, j));
            }
        }


        assertTrue(sudokuBoard1.equals(sudokuBoard2));
    }

    @Test
    void hashCodeTestSameObject() {
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());

        assertEquals(sudokuBoard1.hashCode(), sudokuBoard1.hashCode());
    }

    @Test
    void hashCodeDiffType() {
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuField field = new SudokuField();

        assertEquals(sudokuBoard1.hashCode(), sudokuBoard1.hashCode());
    }

    @Test
    void hashCodeTestEmptyObjects() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(solver);
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver);

        assertTrue(sudokuBoard1.equals(sudokuBoard2));
        assertEquals(sudokuBoard1.hashCode(), sudokuBoard2.hashCode());
    }

    @Test
    void hashCodeTestSameTypeDiffValues() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(solver);
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver);

        sudokuBoard1.solveGame();
        sudokuBoard2.solveGame();

        assertFalse(sudokuBoard1.equals(sudokuBoard2));
        assertNotEquals(sudokuBoard1.hashCode(), sudokuBoard2.hashCode());
    }

    @Test
    void hashCodeSameTypeSameValues() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard1 = new SudokuBoard(solver);
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver);

        sudokuBoard1.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard2.set(i, j,sudokuBoard1.get(i, j));
            }
        }


        assertTrue(sudokuBoard1.equals(sudokuBoard2));
        assertEquals(sudokuBoard1.hashCode(), sudokuBoard2.hashCode());
    }

    @Test
    void cloneTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);

        try {
            SudokuBoard sudokuBoardClone = (SudokuBoard) sudokuBoard.clone();

            assertNotNull(sudokuBoardClone);
            assertNotSame(sudokuBoard, sudokuBoardClone);
            assertEquals(sudokuBoard.getClass(), sudokuBoardClone.getClass());
            assertEquals(sudokuBoard, sudokuBoardClone);

            sudokuBoardClone.solveGame();

            assertNotEquals(sudokuBoard, sudokuBoardClone);

        } catch (CloneNotSupportedException e) {
            System.err.println(e);
        }
    }
}
