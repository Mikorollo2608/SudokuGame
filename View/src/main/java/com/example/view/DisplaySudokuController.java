package com.example.view;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.BacktrackingSudokuSolver;
import org.example.Difficulty;
import org.example.SudokuBoard;

public class DisplaySudokuController {

    private SudokuBoard sudokuBoard;

    private TextField[] textFields;

    private Difficulty chosenLevel;
    @FXML
    private GridPane grid;

    public DisplaySudokuController() {
        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        textFields = new TextField[81];
    }

    public void display(Difficulty lvl) {
        sudokuBoard.solveGame();
        chosenLevel = lvl;
        chosenLevel.removeFields(sudokuBoard);
        int i = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudokuBoard.get(row, col) == 0) {
                    i++;
                    continue;
                }
                textFields[i] = new TextField();
                textFields[i].setAlignment(Pos.CENTER);
                textFields[i].setFont(Font.font("Verdana", FontWeight.BOLD, 36));
                textFields[i].setEditable(false);
                textFields[i].setText(Integer.toString(sudokuBoard.get(row, col)));
                grid.add(textFields[i], col, row, 1, 1);
                i++;
            }
        }
    }

}
