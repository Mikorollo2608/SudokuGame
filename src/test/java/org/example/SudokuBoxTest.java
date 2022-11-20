package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
            board.get(i).setFieldValue(i);
            sudokuBox.setContents(board.get(i), i);
        }

        assertEquals("SudokuBox: [0] Value: 0 [1] Value: 1 [2] Value: 2 [3] Value: 3 [4] Value: 4"
                + " [5] Value: 5 [6] Value: 6 [7] Value: 7 [8] Value: 8 ", sudokuBox.toString());

        SudokuBox sudokuBox1 = new SudokuBox();

        assertEquals("SudokuBox: [0] null [1] null [2] null [3] null [4] null "
                + "[5] null [6] null [7] null [8] null ",sudokuBox1.toString());
    }
}
