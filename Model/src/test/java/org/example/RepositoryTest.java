package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class RepositoryTest {
    @Test
    public void constructorNullTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            Repository repository = new Repository(null);
        });
    }

    @Test
    public void createInstanceTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        Repository repository = new Repository(sudokuBoard);
        SudokuBoard sudokuBoard1 = repository.createInstance();
        assertEquals(sudokuBoard, sudokuBoard1);
        sudokuBoard1.set(1, 1, 0);
        assertNotEquals(sudokuBoard, sudokuBoard1);
    }

}
