package com.example.view;

import static org.example.SudokuBoardDaoFactory.getDatabaseDao;
import static org.example.SudokuBoardDaoFactory.getFileDao;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.BacktrackingSudokuSolver;
import org.example.Dao;
import org.example.JdbcSudokuBoardDao;
import org.example.SudokuBoard;
import org.example.SudokuBoardListener;
import org.example.exceptions.CloningException;
import org.example.exceptions.DaoException;


public class DisplaySudokuController {

    private SudokuBoard sudokuBoard;

    private SudokuBoard originalSudokuBoard;

    private SudokuBoard readSudokuBoard;

    private SudokuBoardListener sudokuBoardListener;
    private TextField[] textFields;

    private Difficulty chosenLevel;

    private ResourceBundle bundle;
    @FXML
    private GridPane grid;

    @FXML
    private Label badNumberText;

    @FXML
    private TextArea path;

    @FXML
    private TextArea nameForDatabaseWrite;

    public DisplaySudokuController() {
        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoardListener = new SudokuBoardListener(sudokuBoard);
        textFields = new TextField[81];
        bundle = ResourceBundle.getBundle("com.example.view.MyBundle");
    }

    void setReadSudokuBoard(SudokuBoard readSudokuBoard) {
        this.readSudokuBoard = readSudokuBoard;
    }

    public void display(Difficulty lvl, boolean fromFile) {
        if (fromFile) {
            sudokuBoard = readSudokuBoard;
        } else {
            sudokuBoard.solveGame();
            chosenLevel = lvl;
            chosenLevel.removeFields(sudokuBoard);
            try {
                originalSudokuBoard = (SudokuBoard) sudokuBoard.clone();
            } catch (CloningException e) {
                throw new RuntimeException(e);
            }
        }
        sudokuBoard.addPropertyChangeListener(sudokuBoardListener);

        int i = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                textFields[i] = new TextField();
                textFields[i].setAlignment(Pos.CENTER);
                textFields[i].setFont(Font.font("Verdana", FontWeight.BOLD, 36));
                if (sudokuBoard.get(row, col) == 0) {
                    textFields[i].setText("");

                    UnaryOperator<TextFormatter.Change> sudokuFieldFormat = change -> {
                        String newText = change.getControlNewText();
                        if (newText.matches("[1-9]?")) {
                            return change;
                        }
                        return null;
                    };

                    textFields[i].setTextFormatter(new TextFormatter<String>(sudokuFieldFormat));

                    final int indexRow = row;
                    final int indexCol = col;

                    textFields[i].textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!Objects.equals(newValue, "")) {
                            sudokuBoard.set(indexRow,indexCol,Integer.parseInt(newValue));
                            if (!sudokuBoardListener.check()) {
                                badNumberText.setText(bundle.getString("warning")
                                        + " [" + indexRow + ", " + indexCol + "]");
                            } else {
                                badNumberText.setText("");
                            }
                        }
                    });

                } else {
                    textFields[i].setEditable(false);
                    textFields[i].setText(Integer.toString(sudokuBoard.get(row, col)));
                }
                grid.add(textFields[i], col, row, 1, 1);
                i++;
            }
        }
        sudokuBoard.addPropertyChangeListener((observable) -> {
                int x = 0;
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        if (sudokuBoard.get(row, col) != 0) {
                            textFields[x].setText(Integer.toString(sudokuBoard.get(row, col)));
                        }
                        x++;
                    }
                }
        });
    }

    public void saveToFile() throws Exception {
        try (Dao<SudokuBoard> sudokuBoardDao = getFileDao(path.getText());
             Dao<SudokuBoard> originalSudokuBoardDao = getFileDao("Original_" + path.getText())
        ) {
            if (sudokuBoard != null && sudokuBoardDao != null) {
                sudokuBoardDao.write(sudokuBoard);
                if (originalSudokuBoard != null && originalSudokuBoardDao != null) {
                    originalSudokuBoardDao.write(originalSudokuBoard);
                }
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToDatabase() throws Exception {
        try (JdbcSudokuBoardDao sudokuBoardDao = getDatabaseDao()) {
            if (sudokuBoard != null && sudokuBoardDao != null) {
                sudokuBoardDao.setName(nameForDatabaseWrite.getText());
                sudokuBoardDao.write(sudokuBoard);
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public void pressedReturnButton(ActionEvent event) throws IOException {
        SceneController sceneController = new SceneController();
        sceneController.loadNewView(event);
    }

    public void setField() {
        System.err.println(sudokuBoard.get(1,1));
        sudokuBoard.set(0,0,7);
        sudokuBoard.set(8,8,7);
        System.err.println(sudokuBoard.get(1,1));
    }
}
