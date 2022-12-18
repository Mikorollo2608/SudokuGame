package org.example;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SudokuBoardListener implements PropertyChangeListener {

    private SudokuBoard sudokuBoard;
    private boolean isCorrect = true;

    public SudokuBoardListener(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
    }

    public boolean check() {
        return isCorrect;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.isCorrect = sudokuBoard.checkBoard();
    }
}
