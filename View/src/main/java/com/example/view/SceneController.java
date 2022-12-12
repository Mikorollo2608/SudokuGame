package com.example.view;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Difficulty chosenLevel;

    public void switchToSudokuBoardScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sudokuBoardScene.fxml"));
        root = loader.load();

        DisplaySudokuController displaySudokuController = loader.getController();
        displaySudokuController.display(chosenLevel);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void pressedEasyButton(ActionEvent event) throws IOException {
        chosenLevel = Difficulty.EASY;
        switchToSudokuBoardScene(event);
    }

    public void pressedMediumButton(ActionEvent event) throws IOException {
        chosenLevel = Difficulty.MEDIUM;
        switchToSudokuBoardScene(event);
    }

    public void pressedHardButton(ActionEvent event) throws IOException {
        chosenLevel = Difficulty.HARD;
        switchToSudokuBoardScene(event);
    }

}