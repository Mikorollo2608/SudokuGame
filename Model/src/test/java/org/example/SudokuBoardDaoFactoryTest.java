package org.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class SudokuBoardDaoFactoryTest {
    @Test
    void getFileDao() {
        FileSudokuBoardDao fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao("text.txt");
        assertNotNull(fileSudokuBoardDao);
    }

    @Test
    void getDatabaseDao() {
        JdbcSudokuBoardDao jdbcSudokuBoardDao = SudokuBoardDaoFactory.getDatabaseDao();
        assertNotNull(jdbcSudokuBoardDao);
    }
}
