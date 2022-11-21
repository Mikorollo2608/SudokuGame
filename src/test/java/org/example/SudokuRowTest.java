package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;


public class SudokuRowTest {
    @Test
    void toStringTest() {
        SudokuRow sudokuRow = new SudokuRow();

        List<SudokuField> board = Arrays.asList(new SudokuField[9]);

        for (int i = 0; i < 9; i++) {
            board.set(i, new SudokuField());
            board.get(i).setFieldValue(i + 1);
            sudokuRow.setContents(board.get(i), i);
        }

        assertEquals("SudokuRow{contents=[SudokuField{value=1}, SudokuField{value=2}, "
                + "SudokuField{value=3}, SudokuField{value=4}, SudokuField{value=5}, "
                + "SudokuField{value=6}, SudokuField{value=7}, SudokuField{value=8},"
                + " SudokuField{value=9}]}", sudokuRow.toString());

        SudokuRow sudokuRow1 = new SudokuRow();

        assertEquals("SudokuRow{contents=[null, null, null, null, null, null, null, null,"
                + " null]}", sudokuRow1.toString());
    }

    @Test
    void equalsTest() {

        SudokuRow sudokuRow = new SudokuRow();
        SudokuRow sudokuRow1 = new SudokuRow();

        List<SudokuField> board = Arrays.asList(new SudokuField[9]);

        for (int i = 0; i < 9; i++) {
            board.set(i, new SudokuField());
            board.get(i).setFieldValue(i + 1);
            sudokuRow.setContents(board.get(i), i);
            sudokuRow1.setContents(board.get(i), i);
        }

        assertTrue(sudokuRow.equals(sudokuRow));
        assertTrue(sudokuRow.equals(sudokuRow1));
        assertFalse(sudokuRow.equals(null));
    }

}
