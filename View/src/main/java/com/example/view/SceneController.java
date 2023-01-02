package com.example.view;

import static org.example.SudokuBoardDaoFactory.getDatabaseDao;
import static org.example.SudokuBoardDaoFactory.getFileDao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.example.Dao;
import org.example.JdbcSudokuBoardDao;
import org.example.SudokuBoard;


public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Difficulty chosenLevel;

    private SudokuBoard sudokuBoard;

    private boolean isReadFromFile = false;

    private boolean writtenDisabled = true;

    @FXML
    private TextArea path;

    @FXML
    private Label dev1;
    @FXML
    private Label dev2;

    @FXML
    private ChoiceBox<String> choiceBox;


    public void loadNewView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("levelScene.fxml"));

        ResourceBundle bundle = ResourceBundle.getBundle("com.example.view.MyBundle");
        fxmlLoader.setResources(bundle);

        root = fxmlLoader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle(bundle.getString("title"));
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSudokuBoardScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sudokuBoardScene.fxml"));
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.view.MyBundle");
        loader.setResources(bundle);
        root = loader.load();

        DisplaySudokuController displaySudokuController = loader.getController();
        displaySudokuController.setReadSudokuBoard(sudokuBoard);
        displaySudokuController.display(chosenLevel, isReadFromFile);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

    public void pressedReadButton(ActionEvent event) {
        if (Files.exists(Paths.get(path.getText()))) {
            try (Dao<SudokuBoard> sudokuBoardDao = getFileDao(path.getText())) {
                isReadFromFile = true;
                sudokuBoard = sudokuBoardDao.read();
                switchToSudokuBoardScene(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void pressedPolishLangButton(ActionEvent event) throws IOException {
        Locale.setDefault(new Locale("pl"));
        loadNewView(event);
    }

    public void pressedEnglishLangButton(ActionEvent event) throws IOException {
        Locale.setDefault(new Locale("en"));
        loadNewView(event);
    }

    public void pressedAuthorsButton() {
        ResourceBundle resource = ResourceBundle.getBundle("com.example.view.Resource");
        if (!dev1.getText().isEmpty() && !dev2.getText().isEmpty()) {
            dev1.setText("");
            dev2.setText("");
        } else {
            dev1.setText(resource.getString("dev1"));
            dev2.setText(resource.getString("dev2"));
        }
    }

    public void pressedReadFromDataBaseButton(ActionEvent event) {
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.view.MyBundle");
        String chosen = choiceBox.getValue();
        if (!writtenDisabled && !chosen.equals(bundle.getString("choose"))) {
            try (JdbcSudokuBoardDao sudokuBoardDao = getDatabaseDao()) {
                isReadFromFile = true;
                sudokuBoardDao.setName(choiceBox.getValue());
                sudokuBoard = sudokuBoardDao.read();
                switchToSudokuBoardScene(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void pressedShowBoardsInDatabase() {
        ResourceBundle bundle = ResourceBundle.getBundle("com.example.view.MyBundle");
        try (JdbcSudokuBoardDao sudokuBoardDao = getDatabaseDao()) {
            ArrayList<String> names = sudokuBoardDao.getNames();
            if (!names.isEmpty()) {
                choiceBox.setItems(FXCollections.observableArrayList(names));
                choiceBox.setValue(bundle.getString("choose"));
                writtenDisabled = false;
            } else {
                choiceBox.setValue("noTabsInDB");
                writtenDisabled = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
