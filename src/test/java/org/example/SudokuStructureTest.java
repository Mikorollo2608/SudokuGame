package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;


class SudokuStructureTest {

    @Test
    void setContentsTest() {
        SudokuStructure sudokuStructure = new SudokuStructure();

        SudokuField[] board = new SudokuField[9];

        //Create list with shuffled numbers from range (0;9>
        List<Integer> tab = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(tab);

        for (int i = 0; i < 9; i++) {
            board[i] = new SudokuField();
            board[i].setFieldValue(tab.get(i));
            sudokuStructure.setContents(board[i], i);
        }


        for (int i = 0; i < 9; i++) {
            assertEquals(sudokuStructure.get(i).getFieldValue(), tab.get(i));
        }

    }


    @Test
    void verifyTest() {
        SudokuStructure sudokuStructure = new SudokuStructure();

        SudokuField[] board = new SudokuField[9];

        //Create list with shuffled numbers from range (0;9>, without repetitions
        List<Integer> tabTrue = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(tabTrue);

        //Create list with shuffled numbers from range (0;9>, with one repetition
        List<Integer> tabFalse = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(tabFalse);
        tabFalse.set(3, tabFalse.get(4));

        for (int i = 0; i < 9; i++) {
            board[i] = new SudokuField();
            board[i].setFieldValue(tabTrue.get(i));
            sudokuStructure.setContents(board[i], i);
        }

        assertTrue(sudokuStructure.verify());

        for (int i = 0; i < 9; i++) {
            board[i].setFieldValue(tabFalse.get(i));
            sudokuStructure.setContents(board[i], i);
        }

        assertFalse(sudokuStructure.verify());

    }

    @Test
    void toStringTest() {
        SudokuStructure sudokuStructure = new SudokuStructure();

        List<SudokuField> board = Arrays.asList(new SudokuField[9]);

        for (int i = 0; i < 9; i++) {
            board.set(i, new SudokuField());
            board.get(i).setFieldValue(i + 1);
            sudokuStructure.setContents(board.get(i), i);
        }

        assertEquals("SudokuStructure{contents=[SudokuField{value=1}, SudokuField{value=2}, "
                + "SudokuField{value=3}, SudokuField{value=4}, SudokuField{value=5}, "
                + "SudokuField{value=6}, SudokuField{value=7}, SudokuField{value=8},"
                + " SudokuField{value=9}]}", sudokuStructure.toString());


        SudokuStructure sudokuStructure1 = new SudokuStructure();

        assertEquals("SudokuStructure{contents=[null, null, null, null, null, null, null, null,"
                + " null]}", sudokuStructure1.toString());
    }

    @Test
    void equalsTest() {

        SudokuStructure sudokuStructure = new SudokuStructure();
        SudokuStructure sudokuStructure1 = new SudokuStructure();

        List<SudokuField> board = Arrays.asList(new SudokuField[9]);

        for (int i = 0; i < 9; i++) {
            board.set(i, new SudokuField());
            board.get(i).setFieldValue(i + 1);
            sudokuStructure.setContents(board.get(i), i);
            sudokuStructure1.setContents(board.get(i), i);
        }

        assertTrue(sudokuStructure.equals(sudokuStructure));
        assertTrue(sudokuStructure.equals(sudokuStructure1));
        assertFalse(sudokuStructure.equals(null));
    }

    @Test
    void hashCodeTest() {
        SudokuStructure sudokuStructure = new SudokuStructure();
        SudokuStructure sudokuStructure1 = new SudokuStructure();

        List<SudokuField> board = Arrays.asList(new SudokuField[9]);

        for (int i = 0; i < 9; i++) {
            board.set(i, new SudokuField());
            board.get(i).setFieldValue(i + 1);
            sudokuStructure.setContents(board.get(i), i);
            sudokuStructure1.setContents(board.get(i), i);
        }

        int hashCode = sudokuStructure.hashCode();

        assertEquals(sudokuStructure.hashCode(), hashCode);
        assertEquals(sudokuStructure.hashCode(), sudokuStructure1.hashCode());


        SudokuField sudokuField = new SudokuField();
        sudokuField.setFieldValue(8);
        sudokuStructure.setContents(sudokuField,1);

        assertNotEquals(sudokuStructure.hashCode(), hashCode);
    }

}