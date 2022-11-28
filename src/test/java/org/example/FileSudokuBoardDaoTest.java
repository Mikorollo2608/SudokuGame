package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;


public class FileSudokuBoardDaoTest {
    @Test
    void readWriteSuccessfulTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> sudokuBoardDao = sudokuBoardDaoFactory.getFileDao("testBoard.txt");

        sudokuBoard.solveGame();
        sudokuBoardDao.write(sudokuBoard);

        SudokuBoard sudokuBoard1 = sudokuBoardDao.read();
        assertTrue(sudokuBoard1.equals(sudokuBoard));

        try {
            Files.deleteIfExists(Paths.get("testBoard.txt"));
        } catch (NoSuchFileException e) {
            System.err.format("No such file");
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    @Test
    void readIoExceptionTest() {
        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> sudokuBoardDao = sudokuBoardDaoFactory.getFileDao("NotExisitingFile.txt");
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        assertTrue(sudokuBoard.equals(sudokuBoardDao.read()));
    }

    @Test
    void readOtherExceptionTest() {
        try (FileOutputStream file = new FileOutputStream(new File("testBoard.txt"))) {
            String obj = "test";
            ObjectOutputStream outObj = new ObjectOutputStream(file);
            outObj.writeObject(obj);
        } catch (IOException e) {
            System.out.println("IOException!");
        }

        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> sudokuBoardDao = sudokuBoardDaoFactory.getFileDao("testBoard.txt");
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        assertTrue(sudokuBoard.equals(sudokuBoardDao.read()));

        try {
            Files.deleteIfExists(Paths.get("testBoard.txt"));
        } catch (NoSuchFileException e) {
            System.err.format("No such file");
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Test
    void writeIoExceptionTest() {
        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> sudokuBoardDao = sudokuBoardDaoFactory.getFileDao("!@#%$^(&*");
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoardDao.write(sudokuBoard);
        try {
            File file = new File("!@#%$^(&*");
            assertTrue(!file.exists());
        } catch (Exception e) {
            System.out.println("Exception!");
        }
    }
}
