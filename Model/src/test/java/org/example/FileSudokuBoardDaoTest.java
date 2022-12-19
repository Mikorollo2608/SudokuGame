package org.example;

import static org.example.SudokuBoardDaoFactory.getFileDao;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.log4j.Logger;
import org.example.exceptions.DaoException;
import org.example.exceptions.IoDaoException;
import org.junit.jupiter.api.Test;


public class FileSudokuBoardDaoTest {
    @Test
    void readWriteSuccessfulTest() {
        Logger logger = Logger.getLogger(FileSudokuBoardDaoTest.class.getName());

        try (Dao<SudokuBoard> sudokuBoardDao = getFileDao("testBoard.txt")) {
            SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

            sudokuBoard.solveGame();
            sudokuBoardDao.write(sudokuBoard);

            SudokuBoard sudokuBoard1 = sudokuBoardDao.read();
            assertEquals(sudokuBoard1, sudokuBoard);

        } catch (Exception e) {
            fail("No exception was excpected");
        }

        try {
            Files.deleteIfExists(Paths.get("testBoard.txt"));
        } catch (IOException e) {
            logger.info("Error during deleting file");
        }
    }

    @Test
    void readIoExceptionTest() {
        Logger logger = Logger.getLogger(FileSudokuBoardDaoTest.class.getName());

        assertThrows(IoDaoException.class, () -> {
            try (Dao<SudokuBoard> sudokuBoardDao = getFileDao("NotExistingFile.txt")) {
                SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
                assertEquals(sudokuBoard, sudokuBoardDao.read());

            }
        });

        try {
            Files.deleteIfExists(Paths.get("NotExistingFile.txt"));
        } catch (Exception e) {
            logger.info("Error during deleting file");
        }
    }

    @Test
    void readOtherExceptionTest() {
        Logger logger = Logger.getLogger(FileSudokuBoardDaoTest.class.getName());

        assertThrows(DaoException.class, () -> {
            try (FileOutputStream file = new FileOutputStream(new File("testBoard.txt"));
                 Dao<SudokuBoard> sudokuBoardDao = getFileDao("testBoard.txt")) {
                String obj = "test";
                ObjectOutputStream outObj = new ObjectOutputStream(file);
                outObj.writeObject(obj);

                SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
                assertEquals(sudokuBoard, sudokuBoardDao.read());
            }
        });

        try {
            Files.deleteIfExists(Paths.get("testBoard.txt"));
        } catch (IOException e) {
            logger.info("Error during deleting file");
        }
    }

    @Test
    void constructorIoDaoExceptionTest() {
        assertThrows(IoDaoException.class, () -> {
            FileSudokuBoardDao fileSudokuBoardDao = new FileSudokuBoardDao("!@#%$^(&*");
        });
    }

    @Test
    void sudokuBoardDaoFactoryTest() {
        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        assertNotNull(sudokuBoardDaoFactory);
    }
}
