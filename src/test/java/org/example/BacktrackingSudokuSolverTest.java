package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BacktrackingSudokuSolverTest {
    @Test
    void toStringTest() {
        BacktrackingSudokuSolver backSolver = new BacktrackingSudokuSolver();
        String testString = "BacktrackingSudokuSolver{name=BacktrackingSudokuSolver}";

        assertEquals(testString, backSolver.toString());
    }
}
