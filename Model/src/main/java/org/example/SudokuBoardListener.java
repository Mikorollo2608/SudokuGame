package org.example;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.apache.log4j.Logger;

public class SudokuBoardListener implements PropertyChangeListener {

    private static final Logger logger = Logger.getLogger(SudokuBoardListener.class.getName());
    private SudokuBoard sudokuBoard;
    private boolean isCorrect = true;

    public SudokuBoardListener(SudokuBoard sudokuBoard) {
        logger.info("Creating new SudokuBoardListener");
        this.sudokuBoard = sudokuBoard;
    }

    public boolean check() {
        logger.info("Checking");
        return isCorrect;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.isCorrect = sudokuBoard.checkBoard();
    }
}
