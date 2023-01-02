package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import org.apache.log4j.Logger;
import org.example.exceptions.DaoException;
import org.example.exceptions.JdbcDaoRecordExitsException;
import org.example.exceptions.JdbcNameException;
import org.junit.jupiter.api.Test;


public class JdbcSudokuBoardDaoTest {
    @Test
    public void readWriteTest() {
        Logger logger = Logger.getLogger(JdbcSudokuBoardDaoTest.class.getName());
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
        try (JdbcSudokuBoardDao dao = SudokuBoardDaoFactory.getDatabaseDao()) {
            sudokuBoard.solveGame();
            sudokuBoard1.solveGame();

            dao.setName("testBoard");
            dao.write(sudokuBoard);
            assertEquals(sudokuBoard, dao.read());

            dao.setName("testBoard1");
            dao.write(sudokuBoard1);
            assertEquals(sudokuBoard1, dao.read());

        } catch (DaoException e) {
            fail();
        } finally {
            try {
                Files.walk(Paths.get("Database"))
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (Exception e) {
                logger.info("Couldn't delete file");
            }
        }
    }

    @Test
    public void readNameNotSetTest() {
        Logger logger = Logger.getLogger(JdbcSudokuBoardDaoTest.class.getName());
        try (JdbcSudokuBoardDao dao = SudokuBoardDaoFactory.getDatabaseDao()) {

            assertThrows(JdbcNameException.class, () -> {
                dao.read();
            });

        } catch (DaoException e) {
            fail();
        } finally {
            try {
                Files.walk(Paths.get("Database"))
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (Exception e) {
                logger.info("Couldn't delete file");
            }
        }
    }

    @Test
    public void writeNameNotSetTest() {
        Logger logger = Logger.getLogger(JdbcSudokuBoardDaoTest.class.getName());
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        try (JdbcSudokuBoardDao dao = SudokuBoardDaoFactory.getDatabaseDao()) {

            assertThrows(JdbcNameException.class, () -> {
                dao.write(sudokuBoard);
            });

        } catch (DaoException e) {
            fail();
        } finally {
            try {
                Files.walk(Paths.get("Database"))
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (Exception e) {
                logger.info("Couldn't delete file");
            }
        }
    }

    @Test
    public void readOverInstancesTest() {
        Logger logger = Logger.getLogger(JdbcSudokuBoardDaoTest.class.getName());
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        try (JdbcSudokuBoardDao dao = SudokuBoardDaoFactory.getDatabaseDao()) {
            sudokuBoard.solveGame();

            dao.setName("testBoard");
            dao.write(sudokuBoard);

        } catch (DaoException e) {
            fail();
        }

        try (JdbcSudokuBoardDao dao = SudokuBoardDaoFactory.getDatabaseDao()) {
            sudokuBoard.solveGame();

            dao.setName("testBoard");
            assertEquals(sudokuBoard, dao.read());

        } catch (DaoException e) {
            fail();
        } finally {
            try {
                Files.walk(Paths.get("Database"))
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (Exception e) {
                logger.info("Couldn't delete file");
            }
        }
    }

    @Test
    public void writeExistingTest() {
        Logger logger = Logger.getLogger(JdbcSudokuBoardDaoTest.class.getName());
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
        try (JdbcSudokuBoardDao dao = SudokuBoardDaoFactory.getDatabaseDao()) {
            sudokuBoard.solveGame();
            sudokuBoard1.solveGame();

            dao.setName("testBoard");
            dao.write(sudokuBoard);
            assertEquals(sudokuBoard, dao.read());

            assertThrows(JdbcDaoRecordExitsException.class, () -> {
                dao.write(sudokuBoard);
            });

            assertEquals(sudokuBoard, dao.read());

        } catch (DaoException e) {
            fail();
        } finally {
            try {
                Files.walk(Paths.get("Database"))
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (Exception e) {
                logger.info("Couldn't delete file");
            }
        }
    }

    @Test
    public void getNameTest() {
        Logger logger = Logger.getLogger(JdbcSudokuBoardDaoTest.class.getName());
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudokuBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudokuBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());
        try (JdbcSudokuBoardDao dao = SudokuBoardDaoFactory.getDatabaseDao()) {
            sudokuBoard.solveGame();
            sudokuBoard1.solveGame();
            sudokuBoard2.solveGame();

            dao.setName("testBoard");
            dao.write(sudokuBoard);
            dao.setName("testBoard1");
            dao.write(sudokuBoard1);
            dao.setName("testBoard2");
            dao.write(sudokuBoard2);
            ArrayList<String> names = new ArrayList<>();
            names.add("testBoard");
            names.add("testBoard1");
            names.add("testBoard2");

            assertEquals(names, dao.getNames());

        } catch (DaoException e) {
            fail();
        } finally {
            try {
                Files.walk(Paths.get("Database"))
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (Exception e) {
                logger.info("Couldn't delete file");
            }
        }
    }

}
