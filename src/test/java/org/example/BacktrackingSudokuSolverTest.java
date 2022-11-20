package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BacktrackingSudokuSolverTest {
    @Test
    void toStringTest() {
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        assertEquals("BacktrackingSudokuSolver", backtrackingSudokuSolver.toString());
    }
}
