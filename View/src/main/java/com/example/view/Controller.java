package com.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label level;

    @FXML
    protected void onEasyButtonClick() {
        level.setText("1");
    }

    @FXML
    protected void onMediumButtonClick() {
        level.setText("2");
    }

    @FXML
    protected void onHardButtonClick() {
        level.setText("3");
    }
}