package org.example;

import org.apache.log4j.Logger;

public class SudokuBoardDaoFactory {

    private static final Logger logger = Logger.getLogger(SudokuBoardDaoFactory.class.getName());

    public static Dao<SudokuBoard> getFileDao(String fileName) {
        logger.info("Creating new FileSudokuBoardDao");
        return new FileSudokuBoardDao(fileName);
    }
}
