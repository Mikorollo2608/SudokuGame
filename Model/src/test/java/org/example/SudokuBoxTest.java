package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;


public class SudokuBoxTest {
    @Test
    void toStringTest() {
        SudokuBox sudokuBox = new SudokuBox();

        List<SudokuField> board = Arrays.asList(new SudokuField[9]);

        for (int i = 0; i < 9; i++) {
            board.set(i, new SudokuField());
            board.get(i).setFieldValue(i + 1);
            sudokuBox.setContents(board.get(i), i);
        }

        assertEquals("SudokuBox{contents=[SudokuField{value=1}, SudokuField{value=2}, "
                + "SudokuField{value=3}, SudokuField{value=4}, SudokuField{value=5}, "
                + "SudokuField{value=6}, SudokuField{value=7}, SudokuField{value=8},"
                + " SudokuField{value=9}]}", sudokuBox.toString());

        SudokuBox sudokuBox1 = new SudokuBox();

        assertEquals("SudokuBox{contents=[null, null, null, null, null, null, null, null,"
                + " null]}", sudokuBox1.toString());
    }

    @Test
    void equalsTest() {

        SudokuBox sudokuBox = new SudokuBox();
        SudokuBox sudokuBox1 = new SudokuBox();
        SudokuColumn sudokuColumn = new SudokuColumn();

        List<SudokuField> board = Arrays.asList(new SudokuField[9]);

        for (int i = 0; i < 9; i++) {
            board.set(i, new SudokuField());
            board.get(i).setFieldValue(i + 1);
            sudokuBox.setContents(board.get(i), i);
            sudokuBox1.setContents(board.get(i), i);
            sudokuColumn.setContents(board.get(i), i);
        }

        assertTrue(sudokuBox.equals(sudokuBox));
        assertTrue(sudokuBox.equals(sudokuBox1));
        assertFalse(sudokuBox.equals(null));
    }
}
