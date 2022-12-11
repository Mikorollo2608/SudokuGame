package com.example.view;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.BacktrackingSudokuSolver;
import org.example.SudokuBoard;

public class PrintSudokuController {

    private SudokuBoard sudokuBoard;

    private TextField[] textFields;

    @FXML
    private GridPane grid;

    public PrintSudokuController() {
        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        textFields = new TextField[81];
    }

    @FXML
    private void initialize() {
        int i = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                textFields[i] = new TextField();
                textFields[i].setAlignment(Pos.CENTER);
                textFields[i].setFont(Font.font("Verdana", FontWeight.BOLD, 36));
                textFields[i].setText(Integer.toString(sudokuBoard.get(row, col)));
                grid.add(textFields[i], col, row, 1, 1);
                i++;
            }
        }
    }

}
