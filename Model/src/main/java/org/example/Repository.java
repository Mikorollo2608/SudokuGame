package org.example;

import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.example.exceptions.CloningException;
import org.example.exceptions.NullArgumentException;


public class Repository {

    ResourceBundle resourceBundle = ResourceBundle.getBundle("ExceptionsMessages");
    private static final Logger logger = Logger.getLogger(Repository.class.getName());
    private final SudokuBoard sudokuBoard;

    public Repository(SudokuBoard sudokuBoard) throws NullArgumentException {
        if (sudokuBoard == null) {
            throw new NullArgumentException(resourceBundle.getString("NullArgumentException"));
        }
        this.sudokuBoard = sudokuBoard;
    }

    public SudokuBoard createInstance() throws CloningException {
        try {
            return (SudokuBoard) sudokuBoard.clone();
        } catch (CloneNotSupportedException e) {
            logger.error("Encountered CloneNotSupportedException while creating "
                    + "new SudokuSolver from Repository");
            throw new CloningException(resourceBundle.getString("CloningExceptionSudokuBoard"));
        }
    }
}
