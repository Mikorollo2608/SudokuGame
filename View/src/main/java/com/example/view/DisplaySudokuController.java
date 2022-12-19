package com.example.view;

import static org.example.SudokuBoardDaoFactory.getFileDao;

import java.io.IOException;
import java.util.Objects;
import java.util.function.UnaryOperator;
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
import org.example.SudokuBoard;
import org.example.SudokuBoardListener;


public class DisplaySudokuController {

    private SudokuBoard sudokuBoard;

    private SudokuBoard readSudokuBoard;

    private SudokuBoardListener sudokuBoardListener;
    private TextField[] textFields;

    private Difficulty chosenLevel;
    @FXML
    private GridPane grid;

    @FXML
    private Label badNumberText;

    @FXML
    private TextArea path;

    public DisplaySudokuController() {
        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoardListener = new SudokuBoardListener(sudokuBoard);
        textFields = new TextField[81];
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
                    ///TODO better displaying information

                    textFields[i].textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!Objects.equals(newValue, "")) {
                            sudokuBoard.set(indexRow,indexCol,Integer.parseInt(newValue));
                            if (!sudokuBoardListener.check()) {
                                badNumberText.setText("This value doesn't fit here! "
                                        + "[" + indexRow + ", " + indexCol + "]");
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
    }

    public void saveToFile() {
        try (Dao<SudokuBoard> sudokuBoardDao = getFileDao(path.getText())) {
            sudokuBoardDao.write(sudokuBoard);
        } catch (Exception e) {
            System.err.println("Exception!");
        }
    }

}
