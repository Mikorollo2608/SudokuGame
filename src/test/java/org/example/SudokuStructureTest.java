package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class SudokuStructureTest {

    @Test
    void setContentsTest() {
        SudokuStructure sudokuStructure = new SudokuStructure();

        SudokuField[] board = new SudokuField[9];
        int[] tab = {1, 9, 3, 4, 7, 6, 5, 8, 2};

        for (int i = 0; i < 9; i++) {
            board[i] = new SudokuField();
            board[i].setFieldValue(tab[i]);
            sudokuStructure.setContents(board[i], i);
        }


        for (int i = 0; i < 9; i++) {
            assertEquals(sudokuStructure.get(i).getFieldValue(), tab[i]);
        }

    }


    @Test
    void verifyTest() {
        SudokuStructure sudokuStructure = new SudokuStructure();

        SudokuField[] board = new SudokuField[9];
        int[] tabTrue = {1, 9, 3, 4, 7, 6, 5, 8, 2};
        int[] tabFalse = {1, 9, 3, 4, 6, 6, 5, 8, 2};

        for (int i = 0; i < 9; i++) {
            board[i] = new SudokuField();
            board[i].setFieldValue(tabTrue[i]);
            sudokuStructure.setContents(board[i], i);
        }

        assertTrue(sudokuStructure.verify());

        for (int i = 0; i < 9; i++) {
            board[i].setFieldValue(tabFalse[i]);
            sudokuStructure.setContents(board[i], i);
        }

        assertFalse(sudokuStructure.verify());

    }
}