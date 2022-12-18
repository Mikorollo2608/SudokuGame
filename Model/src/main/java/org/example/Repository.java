package org.example;

import org.apache.log4j.Logger;

public class Repository {

    private static final Logger logger = Logger.getLogger(Repository.class.getName());
    private final SudokuBoard sudokuBoard;

    public Repository(SudokuBoard sudokuBoard) {
        logger.info("Creating new Repository");
        if (sudokuBoard == null) {
            logger.error("Null not allowed");
            throw new IllegalArgumentException("Null value");
        }
        this.sudokuBoard = sudokuBoard;
    }

    public SudokuBoard createInstance() {
        logger.info("Creating new SudokuBoard from Repository");
        try {
            return (SudokuBoard) sudokuBoard.clone();
        } catch (CloneNotSupportedException e) {
            logger.error("Encountered CloneNotSupportedException while creating "
                    + "new SudokuSolver from Repository");
        }
        return null;
    }
}
