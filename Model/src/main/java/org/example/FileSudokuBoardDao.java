package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.log4j.Logger;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private static final Logger logger = Logger.getLogger(FileSudokuBoardDao.class.getName());

    private final File file;

    FileSudokuBoardDao(String path) {
        file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            logger.error("Encountered IOException while opening a file");
        }
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream inObj = new ObjectInputStream(fileIn)) {
            sudokuBoard = (SudokuBoard) inObj.readObject();
        } catch (IOException e) {
            logger.error("Encountered IOException while reading a file");
        } catch (Exception e) {
            logger.error("Encountered unknown exception while reading a file");
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj) {
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream outObj = new ObjectOutputStream(fileOut)) {
            outObj.writeObject(obj);
        } catch (Exception e) {
            logger.error("Encountered IOException while writing to a file");
        }
    }

    @Override
    public void close() {
    }
}
