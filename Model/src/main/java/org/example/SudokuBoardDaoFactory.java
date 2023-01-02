package org.example;


import org.apache.log4j.Logger;
import org.example.exceptions.DaoException;

public class SudokuBoardDaoFactory {
    private static final Logger logger = Logger.getLogger(Repository.class.getName());

    public static FileSudokuBoardDao getFileDao(String fileName) {
        try {
            return new FileSudokuBoardDao(fileName);
        } catch (DaoException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static JdbcSudokuBoardDao getDatabaseDao() {
        try {
            return new JdbcSudokuBoardDao();
        } catch (DaoException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}

