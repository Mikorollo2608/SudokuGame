package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        List<Integer> tab = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
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
        List<Integer> tabTrue = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        Collections.shuffle(tabTrue);

        //Create list with shuffled numbers from range (0;9>, with one repetition
        List<Integer> tabFalse = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        Collections.shuffle(tabFalse);
        tabFalse.set(3,tabFalse.get(4));

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
}