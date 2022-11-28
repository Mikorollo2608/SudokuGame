package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SudokuColumnTest {
    @Test
    void toStringTest() {
        SudokuColumn sudokuColumn = new SudokuColumn();

        List<SudokuField> board = Arrays.asList(new SudokuField[9]);

        for (int i = 0; i < 9; i++) {
            board.set(i, new SudokuField());
            board.get(i).setFieldValue(i + 1);
            sudokuColumn.setContents(board.get(i), i);
        }

        assertEquals("SudokuColumn{contents=[SudokuField{value=1}, SudokuField{value=2}, "
                + "SudokuField{value=3}, SudokuField{value=4}, SudokuField{value=5}, "
                + "SudokuField{value=6}, SudokuField{value=7}, SudokuField{value=8},"
                + " SudokuField{value=9}]}", sudokuColumn.toString());

        SudokuColumn sudokuColumn1 = new SudokuColumn();

        assertEquals("SudokuColumn{contents=[null, null, null, null, null, null, null, null,"
                + " null]}", sudokuColumn1.toString());
    }

    @Test
    void equalsTest() {

        SudokuColumn sudokuColumn = new SudokuColumn();
        SudokuColumn sudokuColumn1 = new SudokuColumn();

        List<SudokuField> board = Arrays.asList(new SudokuField[9]);

        for (int i = 0; i < 9; i++) {
            board.set(i, new SudokuField());
            board.get(i).setFieldValue(i + 1);
            sudokuColumn.setContents(board.get(i), i);
            sudokuColumn1.setContents(board.get(i), i);
        }

        assertTrue(sudokuColumn.equals(sudokuColumn));
        assertTrue(sudokuColumn.equals(sudokuColumn1));
        assertFalse(sudokuColumn.equals(null));
    }

}
