package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.Test;



class SudokuStructureTest {

    @Test
    void setContentsTest() {
        SudokuStructure sudokuStructure = new SudokuStructure();

        SudokuField[] board = new SudokuField[9];

        //Create table with shuffled numbers from range (0;9>
        Random random = new Random();

        int[] tab = new int[9];
        for (int i = 0; i < 9; i++) {
            while (tab[i] == 0) {
                int temp = random.nextInt(9) + 1;
                int j = 0;
                for (; j < i; j++) {
                    if (temp == tab[j]) {
                        break;
                    }
                }
                if (j == i) {
                    tab[i] = temp;
                }
            }
        }

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

        //Create table with shuffled numbers from range (0;9>, without repetitions
        Random random = new Random();

        int[] tabTrue = new int[9];
        for (int i = 0; i < 9; i++) {
            while (tabTrue[i] == 0) {
                int temp = random.nextInt(9) + 1;
                int j = 0;
                for (; j < i; j++) {
                    if (temp == tabTrue[j]) {
                        break;
                    }
                }
                if (j == i) {
                    tabTrue[i] = temp;
                }
            }
        }

        //Create table with shuffled numbers from range (0;9>, with one repetition
        int[] tabFalse = new int[9];
        for (int i = 0; i < 9; i++) {
            while (tabFalse[i] == 0) {
                int temp = random.nextInt(9) + 1;
                int j = 0;
                for (; j < i; j++) {
                    if (temp == tabFalse[j]) {
                        break;
                    }
                }
                if (j == i) {
                    tabFalse[i] = temp;
                }
            }
        }
        tabFalse[3] = tabFalse[4];

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