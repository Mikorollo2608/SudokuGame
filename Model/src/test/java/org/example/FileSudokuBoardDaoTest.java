package org.example;

import static org.example.SudokuBoardDaoFactory.getFileDao;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        try (Dao<SudokuBoard> sudokuBoardDao = getFileDao("testBoard.txt")) {
            SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

            sudokuBoard.solveGame();
            sudokuBoardDao.write(sudokuBoard);

            SudokuBoard sudokuBoard1 = sudokuBoardDao.read();
            assertEquals(sudokuBoard1, sudokuBoard);

            Files.deleteIfExists(Paths.get("testBoard.txt"));
        } catch (NoSuchFileException e) {
            System.err.format("No such file");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Test
    void readIoExceptionTest() {
        try (Dao<SudokuBoard> sudokuBoardDao = getFileDao("NotExistingFile.txt")) {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        assertEquals(sudokuBoard, sudokuBoardDao.read());

        Files.deleteIfExists(Paths.get("testBoard.txt"));
        } catch (NoSuchFileException e) {
        System.err.format("No such file");
        } catch (Exception e) {
        System.err.println(e);
        }
    }

    @Test
    void readOtherExceptionTest() {
        try (FileOutputStream file = new FileOutputStream(new File("testBoard.txt"));
             Dao<SudokuBoard> sudokuBoardDao = getFileDao("testBoard.txt")) {
            String obj = "test";
            ObjectOutputStream outObj = new ObjectOutputStream(file);
            outObj.writeObject(obj);

        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        assertEquals(sudokuBoard, sudokuBoardDao.read());

        } catch (IOException e) {
            System.out.println("IOException!");
        } catch (Exception e) {
            System.out.println("Exception!");
        }

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
        try (Dao<SudokuBoard> sudokuBoardDao = getFileDao("!@#%$^(&*")) {
            SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
            sudokuBoardDao.write(sudokuBoard);
            File file = new File("!@#%$^(&*");
            assertFalse(file.exists());
        } catch (Exception e) {
            System.out.println("Exception!");
        }
    }
}
