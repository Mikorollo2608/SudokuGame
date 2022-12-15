package com.example.view;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.BacktrackingSudokuSolver;
import org.example.SudokuBoard;

public class DisplaySudokuController {

    private SudokuBoard sudokuBoard;

    private SudokuTextField[] textFields;

    private Difficulty chosenLevel;
    @FXML
    private GridPane grid;

    public DisplaySudokuController() {
        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        textFields = new SudokuTextField[81];
    }

    public void display(Difficulty lvl) {
        sudokuBoard.solveGame();
        chosenLevel = lvl;
        chosenLevel.removeFields(sudokuBoard);
        int i = 0;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                textFields[i] = new SudokuTextField();
                textFields[i].setAlignment(Pos.CENTER);
                textFields[i].setFont(Font.font("Verdana", FontWeight.BOLD, 36));
                if (sudokuBoard.get(row, col) == 0) {
                    textFields[i].setText("");

                    textFields[i].textProperty().addListener((observable, oldValue, newValue) -> {
                        System.out.println("textfield changed from " + oldValue + " to " + newValue);
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

}
