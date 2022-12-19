package org.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SudokuBoardListenerTest {

    private SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    private SudokuBoardListener sbl = new SudokuBoardListener(sudokuBoard);

    @Test
    void checkTest() {
        assertNotNull(sudokuBoard);
        assertNotNull(sbl);

        sudokuBoard.addPropertyChangeListener(sbl);

        assertTrue(sbl.check());

        sudokuBoard.solveGame();
        assertTrue(sbl.check());

        final int tmp1 = sudokuBoard.get(1,1);
        final int tmp2 = sudokuBoard.get(1,2);
        sudokuBoard.set(1,1,1);
        sudokuBoard.set(1,2,1);
        assertFalse(sbl.check());

        sudokuBoard.set(1,1,tmp1);
        sudokuBoard.set(1,2,tmp2);
        assertTrue(sbl.check());
    }

}