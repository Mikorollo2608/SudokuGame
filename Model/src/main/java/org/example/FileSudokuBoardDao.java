package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.example.exceptions.DaoException;
import org.example.exceptions.IoDaoException;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private static final Logger logger = Logger.getLogger(Repository.class.getName());

    ResourceBundle resourceBundle = ResourceBundle.getBundle("ExceptionsMessages");

    private final File file;

    FileSudokuBoardDao(String path) throws DaoException {
        file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new IoDaoException(resourceBundle.getString("IoDaoExceptionOpening"),
                    e.getCause());
        } catch (Exception e) {
            throw new DaoException(resourceBundle.getString("DaoExceptionUnknown"),
                    e.getCause());
        }
    }

    @Override
    public SudokuBoard read() throws DaoException {

        logger.info("Reading from file" + file.getName());

        //SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudokuBoard;

        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream inObj = new ObjectInputStream(fileIn)) {
            sudokuBoard = (SudokuBoard) inObj.readObject();
        } catch (IOException e) {
            throw new IoDaoException(resourceBundle.getString("IoDaoExceptionReading"),
                    e.getCause());
        } catch (Exception e) {
            throw new DaoException(resourceBundle.getString("DaoExceptionUnknown"),
                    e.getCause());
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard obj) throws DaoException {
        logger.info("Saving to file" + file.getName());
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream outObj = new ObjectOutputStream(fileOut)) {
            outObj.writeObject(obj);
        } catch (IOException e) {
            throw new IoDaoException(resourceBundle.getString("IoDaoExceptionWriting"),
                    e.getCause());
        } catch (Exception e) {
            throw new DaoException(resourceBundle.getString("DaoExceptionUnknown"),
                    e.getCause());
        }
    }

    @Override
    public void close() {
    }
}
